package msv.management.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import msv.management.system.configs.ConfigurationConstants;
import msv.management.system.dto.AuthRequestDTO;
import msv.management.system.dto.AuthUserDTO;
import msv.management.system.dto.Token;
import msv.management.system.repository.AuthDAO;
import msv.management.system.service.IAuthService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class AuthService implements IAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthDAO authDAO;

    @Autowired
    private RestTemplate authRestTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${auth-types}")
    private String[] authTypes;

    @Override
    @Transactional
    public AuthUserDTO upsertUser(AuthUserDTO user) {
        return authDAO.upsertUser(user);
    }

    @Override
    public Optional<AuthUserDTO> getUserByUsername(String username) {
        return authDAO.getUserByUserName(username);
    }

    @Override
    public List<AuthUserDTO> getAllUsers() {
        TypeReference<List<AuthUserDTO>> type = new TypeReference<List<AuthUserDTO>>() {
        };
        ResponseEntity<List> listResponseEntity = authRestTemplate.exchange("/users/active", HttpMethod.GET, null, List.class);
        return new ObjectMapper().convertValue(listResponseEntity.getBody(), type);
    }

    @Override
    public void delete(String username) {
        authDAO.delete(username);
    }

    @Override
    public void unlockUser(String username) {
        authDAO.updateUnlockedStatus(username);
    }

    @Override
    public ResponseEntity<?> authenticate(AuthRequestDTO authRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> authTypesList = Arrays.asList(authTypes);
        JSONObject payload = new JSONObject();
        payload.put("username", authRequestDTO.getUsername());
        payload.put("password", authRequestDTO.getPassword());
        payload.put("authTypes", authTypesList);

        String stringPayload = payload.toString();

        try {
            if (!ConfigurationConstants.getAuthEnabled()) {
                Token token = new Token("dummyToken");
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            HttpEntity<String> adminVerifyPayload = new HttpEntity<>(stringPayload, headers);
            authRestTemplate.exchange("/users/isadmin", HttpMethod.POST, adminVerifyPayload, String.class);

            HttpEntity<String> request = new HttpEntity<>(stringPayload, headers);
            return authRestTemplate.exchange("/security/token", HttpMethod.POST, request, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }

    @Override
    public ResponseEntity<AuthUserDTO> upsertUser2(AuthUserDTO user) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            JSONObject upsertUserPayload = objectMapper.convertValue(user, JSONObject.class);

            HttpEntity<String> request = new HttpEntity<>(upsertUserPayload.toString(), headers);
            ResponseEntity<String> exchange = authRestTemplate.exchange("/users/upsertuser", HttpMethod.POST, request, String.class);
            return new ResponseEntity<>(user, HttpStatus.valueOf(exchange.getStatusCodeValue()));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(null);
        }
    }
}
