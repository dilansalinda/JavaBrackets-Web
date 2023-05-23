package msv.management.system.service.impl;

import msv.management.system.dto.EntityDTO;
import msv.management.system.repository.EntityDAO;
import msv.management.system.service.IEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService implements IEntityService {

    @Autowired
    private EntityDAO entityDAO;


    @Override
    public List<EntityDTO> insertEntities(List<EntityDTO> entities) {
        return entityDAO.insertEntity(entities);
    }

}
