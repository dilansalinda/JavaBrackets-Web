package msv.management.system.service;


import msv.management.system.dto.PermissionDTO;
import msv.management.system.dto.Response;

import java.util.List;

public interface IRolePermissionService {

    PermissionDTO getUserPermissions(String username, int projectId, String role);

    Response upsertUserPermissions(String username, List<String> permissions);

}
