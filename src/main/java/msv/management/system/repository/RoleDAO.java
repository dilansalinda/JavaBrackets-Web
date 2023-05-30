package msv.management.system.repository;

import msv.management.system.dto.RoleDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDAO {

    public List<RoleDTO> getAllRoles() {
        return new ArrayList<>();
    }


}
