package msv.management.system.service;

import msv.management.system.dto.AppConfigDTO;

import java.util.List;

public interface IAppConfigService {

    List<AppConfigDTO> upsert(List<AppConfigDTO> configs);

    List<AppConfigDTO> delete(List<AppConfigDTO> configs);

    List<AppConfigDTO> list();

    List<AppConfigDTO> list(String component);

    List<AppConfigDTO> list(String component, String instance);

    AppConfigDTO list(String component, String instance, String key);

}
