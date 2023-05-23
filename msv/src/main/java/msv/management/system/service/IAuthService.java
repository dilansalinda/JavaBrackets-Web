package msv.management.system.service;

import msv.management.system.dto.AuthRequestDTO;
import msv.management.system.dto.AuthUserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IAuthService {
    public AuthUserDTO upsertUser(AuthUserDTO user);

    public Optional<AuthUserDTO> getUserByUsername(String username);

    public List<AuthUserDTO> getAllUsers();

    public void delete(String username);

    public void unlockUser(String username);

    ResponseEntity<?> authenticate(AuthRequestDTO authRequestDTO);

    public ResponseEntity<AuthUserDTO> upsertUser2(AuthUserDTO user);
}
