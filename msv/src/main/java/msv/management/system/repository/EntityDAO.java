package msv.management.system.repository;

import msv.management.system.dto.EntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class EntityDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<EntityDTO> insertEntity(List<EntityDTO> entities) {
        for (EntityDTO entity : entities) {

            List<Map<String, Object>> count = jdbcTemplate.queryForList("SELECT COUNT(name) AS counts FROM `entity` WHERE name = '" + entity.getName() + "' ");

            if ((long) count.get(0).get("counts") > 0) {

                String sql = "UPDATE `entity` SET `display_name`=?,`description`=? WHERE name=? ";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        connection -> {
                            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(3, entity.getName());
                            ps.setString(1, entity.getDisplayName());
                            ps.setString(2, entity.getDescription());
                            return ps;
                        }, keyHolder);
            } else {

                String sql = "INSERT INTO `entity` (`name`,`display_name`,`description`) VALUES (?,?,?);";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        connection -> {
                            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(1, entity.getName());
                            ps.setString(2, entity.getDisplayName());
                            ps.setString(3, entity.getDescription());
                            return ps;
                        }, keyHolder);

            }

        }

        return entities;
    }
}
