package msv.management.system.controller;


import io.swagger.annotations.*;
import msv.management.system.dto.AuthRequestDTO;
import msv.management.system.dto.AuthUserDTO;
import msv.management.system.dto.Status;
import msv.management.system.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "${server.context}/auth/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Authentication"})
@ApiIgnore
public class AuthController {
    @Autowired
    private IAuthService authService;

    @ApiOperation(value = "Create/Update Users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    @RequestMapping(method = RequestMethod.GET)
    public final ResponseEntity<List<AuthUserDTO>> getAllUsers() {
        List<AuthUserDTO> result = authService.getAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public final ResponseEntity<AuthUserDTO> getUsersByUsername(@PathVariable(value = "username") String username) {
        Optional<AuthUserDTO> result = authService.getUserByUsername(username);
        if (result.isPresent())
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/unlock", method = RequestMethod.PUT)
    public final ResponseEntity<?> unlockUser(@PathVariable(value = "username") String username) {
//        System.out.println("Username"+ username);
        authService.unlockUser(username);

        HashMap<String, Object> response = new HashMap<>();
        response.put("Status", Status.Pass);
        response.put("Message", "Updated Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<AuthUserDTO> upsertUsers(@ApiParam(value = "User object", required = true) @RequestBody AuthUserDTO user) {
        AuthUserDTO result = authService.upsertUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public final ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) {
        authService.delete(username);
        HashMap<String, Object> response = new HashMap<>();
        response.put("Status", Status.Pass);
        response.put("Message", "Delete Successfully!");


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        ResponseEntity<?> authenticatedResponse = authService.authenticate(authRequestDTO);
        return ResponseEntity
                .status(authenticatedResponse.getStatusCode())
                .body(authenticatedResponse.getBody());
//        return new ResponseEntity<>(authResponse, HttpStatus.valueOf(authResponse.getCode()));
    }
}
