package msv.management.system.repository;

import msv.management.system.dto.AppConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AppConfigDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<AppConfigDTO> upsert(List<AppConfigDTO> configs) {

        for (AppConfigDTO appConfig : configs) {
            AppConfigDTO tempConfig = getConfig(appConfig.getComponent(), appConfig.getInstance(), appConfig.getConfigKey());

            if (tempConfig.getId() != null) {
                appConfig.setId(tempConfig.getId());
                update(appConfig);
            } else {
                insert(appConfig);
            }
        }
        return configs;
    }

    public AppConfigDTO insert(AppConfigDTO appConfig) {

        String sql = "INSERT INTO `property_info` (`component`, `instance`, `config_key`, `config_value`, `description`) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE component=values(component), instance=values(instance), config_key=values(config_key), config_value=values(config_value), description=values(description);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, appConfig.getComponent());
                    ps.setString(2, appConfig.getInstance());
                    ps.setString(3, appConfig.getConfigKey());
                    ps.setString(4, appConfig.getConfigValue());
                    ps.setString(5, appConfig.getDescription());
                    return ps;
                }, keyHolder);
        keyHolder.getKeyList().get(0).forEach((k, v) -> appConfig.setId(((Long) v).intValue()));
        return appConfig;
    }

    public AppConfigDTO update(AppConfigDTO appConfig) {

        String sql = "UPDATE `property_info` SET `component`=?, `instance`=?, `description`=?, `config_key`=?, `config_value`=? WHERE  `component`=? AND `instance`=? AND `config_key`=?;";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, appConfig.getComponent());
                    ps.setString(2, appConfig.getInstance());
                    ps.setString(3, appConfig.getDescription());
                    ps.setString(4, appConfig.getConfigKey());
                    ps.setString(5, appConfig.getConfigValue());
                    ps.setString(6, appConfig.getComponent());
                    ps.setString(7, appConfig.getInstance());
                    ps.setString(8, appConfig.getConfigKey());
                    return ps;
                });

        return appConfig;
    }

    public List<AppConfigDTO> delete(List<AppConfigDTO> configs) {
        for (AppConfigDTO appConfig : configs) {

            String sql = "DELETE FROM `property_info` WHERE `component`=? AND `instance`=? AND `config_key`=?;";

            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, appConfig.getComponent());
                        ps.setString(2, appConfig.getInstance());
                        ps.setString(3, appConfig.getConfigKey());
                        return ps;
                    });
        }
        return configs;
    }

    public List<AppConfigDTO> getConfig() {
        String sql = "SELECT * FROM `property_info`";
        List<AppConfigDTO> configs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AppConfigDTO.class));
        return configs;
    }

    public List<AppConfigDTO> getConfig(String component) {
        String sql = "SELECT * FROM `property_info` WHERE component = '" + component + "'";
        List<AppConfigDTO> configs = jdbcTemplate.query(sql, new Object[]{component}, new BeanPropertyRowMapper<>(AppConfigDTO.class));
        return configs;
    }

    public List<AppConfigDTO> getConfig(String component, String instance) {
        String sql = "SELECT * FROM `property_info` WHERE component = ? AND instance = ?";
        List<AppConfigDTO> configs = jdbcTemplate.query(sql, new Object[]{component, instance}, new BeanPropertyRowMapper<>(AppConfigDTO.class));
        return configs;
    }


    public AppConfigDTO getConfig(String component, String instance, String config_key) {
        String sql = "SELECT * FROM `property_info` WHERE component = ? AND instance= ? AND config_key= ?";
        List<AppConfigDTO> configs = jdbcTemplate.query(sql, new Object[]{component, instance, config_key}, new BeanPropertyRowMapper<>(AppConfigDTO.class));
        AppConfigDTO config = new AppConfigDTO();
        config.setComponent(component);
        config.setInstance(instance);
        config.setConfigKey(config_key);
        config.setConfigValue("0");

        if (!configs.isEmpty()) {
            config = configs.get(0);
        }

        return config;
    }

}
