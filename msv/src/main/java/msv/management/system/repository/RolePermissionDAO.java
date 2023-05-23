package msv.management.system.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import msv.management.system.dto.PermissionDTO;
import msv.management.system.dto.Response;
import msv.management.system.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

@Repository
public class RolePermissionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public PermissionDTO getUserPermissionsPermissions(String username, int projectId, String role) {

        if (Objects.equals(username, "admin")) {
            PermissionDTO permissionDTO = new PermissionDTO();
            permissionDTO.put("form_management", true);
            return permissionDTO;
        }

        String sql = "SELECT p.permission from user u " +
                "INNER JOIN user_allocation ua ON ua.user_id = u.id " +
                "INNER JOIN user_permission urp ON urp.user_id = ua.user_id " +
                "INNER JOIN permission p ON p.id = urp.permission_id " +
                "WHERE (u.username = '" + username + "' AND ua.is_active=1 AND ua.project_id = " + projectId +
                " AND ua.role ='" + role + "')" +
                " OR (u.username = '" + username + "' AND ua.is_active=1 AND ua.project_id = " + projectId +
                " AND ua.role = 'admin') ";

        List<String> permissions = jdbcTemplate.queryForList(sql, String.class);
        Map<String, Object> permMap = new HashMap<>();
        permissions.forEach(permission -> permMap.put(permission, true));

        return objectMapper.convertValue(permMap, PermissionDTO.class);

    }

    public Response upsertUserPermissions(String username, List<String> permissions) {

        String userSql = "SELECT id from user u where u.username ='" + username + "'";
        List<Long> userId = this.jdbcTemplate.queryForList(userSql, Long.class);
        Response response = new Response();
        List<String> messages = new ArrayList<>();
        if (!userId.isEmpty()) {

            this.jdbcTemplate.batchUpdate(
                    "INSERT INTO `user_permission` (`user_id`,`permission_id`) VALUES (?,(SELECT id FROM permission where permission = ?)) ON DUPLICATE KEY UPDATE user_id=values(user_id), permission_id=values(permission_id);",
                    new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, userId.get(0));
                            ps.setString(2, permissions.get(i));
                        }

                        public int getBatchSize() {
                            return permissions.size();
                        }
                    });
            response.setCode(200);
            response.setStatus(Status.Pass);
            messages.add(MessageFormat.format("Successfully inserted permissions {0} for username {1}", String.join(",", permissions), username));
            response.setMessage(messages);
        } else {
            messages.add(MessageFormat.format("Failed to insert permissions {0} for username {1} , username {1} does not exit", String.join(",", permissions), username));
            response.setMessage(messages);
            response.setCode(500);
            response.setStatus(Status.Error);
        }
        return response;
    }

}
