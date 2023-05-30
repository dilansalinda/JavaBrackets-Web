package msv.management.system.service;

import msv.management.system.dto.EntityDTO;

import java.util.List;

public interface IEntityService {

    List<EntityDTO> insertEntities(List<EntityDTO> entities);

}
