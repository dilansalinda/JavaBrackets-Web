package msv.management.system.service.impl;

import msv.management.system.dto.PermissionDTO;
import msv.management.system.dto.Response;
import msv.management.system.repository.RolePermissionDAO;
import msv.management.system.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService implements IRolePermissionService {

    @Autowired
    private final RolePermissionDAO rolepermissionDAO;

    @Autowired
    public RolePermissionService(RolePermissionDAO rolepermissionDAO) {
        this.rolepermissionDAO = rolepermissionDAO;
    }

    @Override
    public PermissionDTO getUserPermissions(String username, int projectId, String role) {
        return rolepermissionDAO.getUserPermissions(username, projectId, role);
    }

    @Override
    public Response upsertUserPermissions(String username, List<String> permissions) {
        return rolepermissionDAO.upsertUserPermissions(username, permissions);
    }
}



