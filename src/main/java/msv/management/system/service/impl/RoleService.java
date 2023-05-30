package msv.management.system.service.impl;

import msv.management.system.dto.RoleDTO;
import msv.management.system.repository.RoleDAO;
import msv.management.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}
