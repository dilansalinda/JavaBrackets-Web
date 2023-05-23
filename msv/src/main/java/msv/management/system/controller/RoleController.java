package msv.management.system.controller;

import msv.management.system.dto.RoleDTO;
import msv.management.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "${server.context}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiIgnore
public class RoleController {

    private IRoleService iRoleService;

    @Autowired
    public RoleController(IRoleService iRoleService) {
        this.iRoleService = iRoleService;
    }

    @GetMapping
    public final ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> allRoles = iRoleService.getAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }
}
