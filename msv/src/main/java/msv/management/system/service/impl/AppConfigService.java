package msv.management.system.service.impl;

import msv.management.system.dto.AppConfigDTO;
import msv.management.system.repository.AppConfigDAO;
import msv.management.system.service.IAppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppConfigService implements IAppConfigService {

    @Autowired
    AppConfigDAO appConfigDAO;


    @Override
    public List<AppConfigDTO> upsert(List<AppConfigDTO> configs) {
        return appConfigDAO.upsert(configs);
    }

    @Override
    public List<AppConfigDTO> delete(List<AppConfigDTO> configs) {
        return appConfigDAO.delete(configs);
    }

    @Override
    public List<AppConfigDTO> list() {
        return appConfigDAO.getConfig();
    }

    @Override
    public List<AppConfigDTO> list(String component) {
        return appConfigDAO.getConfig(component);
    }

    @Override
    public List<AppConfigDTO> list(String component, String instance) {
        return appConfigDAO.getConfig(component, instance);
    }

    @Override
    public AppConfigDTO list(String component, String instance, String key) {
        return appConfigDAO.getConfig(component, instance, key);
    }
}
