package msv.management.system.controller;

import msv.management.system.dto.PermissionDTO;
import msv.management.system.service.impl.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author rpsperera on 12/1/20
 */

@RestController
@RequestMapping(value = "${server.context}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiIgnore
public class PermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping(value = "/allowed", consumes = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<PermissionDTO> authenticate(@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO userPermissions = rolePermissionService.getUserPermissions((String) permissionDTO.get("username"), (int) permissionDTO.get("projectId"), (String) permissionDTO.get("role"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userPermissions);
    }
}
