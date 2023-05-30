package msv.management.system.repository;

import msv.management.system.dto.AuthRequestDTO;
import msv.management.system.dto.AuthUserDTO;
import msv.management.system.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuthDAO {
    @Autowired
    private JdbcTemplate jdbcSecurity;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserDTO upsertUser(AuthUserDTO user) {
        deleteRoles(user.getUserName());
        String sql = "INSERT INTO user (`username`, `first_name`, `last_name`, `email`,`active`) VALUES (?,?," +
                "?,?,?) ON DUPLICATE KEY UPDATE first_name=values(first_name), last_name=values(last_name), email=values(email)," +
                "  active=values(active);";
        jdbcSecurity.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, user.getUserName());
                    ps.setString(2, user.getFirstName());
                    ps.setString(3, user.getLastName());
                    ps.setString(4, user.getEmail());
                    ps.setBoolean(5, user.isActive());
                    return ps;
                });
        setRoles(user);
        Optional<AuthUserDTO> authUser = getUserByUserName(user.getUserName());
        if (authUser.isPresent() && !Objects.equals(authUser.get().getPassword(), user.getPassword())) {
            String pass = passwordEncoder.encode(user.getPassword());
            String updateSql = "UPDATE user SET password ='" + pass + "' WHERE username = '" + user.getUserName() + "'";
            jdbcSecurity.update(updateSql);
        }
        return user;
    }

    public void setRoles(AuthUserDTO userDTO) {
        String sql = "INSERT INTO user_roles (`role_id`, `username`) VALUES(?, ?)";
        for (RoleDTO role : userDTO.getRoles()) {
            jdbcSecurity.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, role.getId());
                ps.setString(2, userDTO.getUserName());
                return ps;
            });
        }
    }

    public void updateUnlockedStatus(String username) {
        String updateUserLock = "UPDATE user SET unlocked=1 WHERE username = ?";
        String deleteAttempts = "Delete From user_attempts WHERE username = ?";
        jdbcSecurity.update(updateUserLock, username);
        jdbcSecurity.update(deleteAttempts, username);
    }


    public Optional<AuthUserDTO> getUserByUserName(String username) {
        String sql = "SELECT * FROM user WHERE username = ? AND active = 1";
        List<AuthUserDTO> result = jdbcSecurity.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(AuthUserDTO.class));
        if (result != null && !result.isEmpty())
            return Optional.of(result.get(0));
        return Optional.empty();
    }

    public List<AuthUserDTO> getAllUsers() {
        String sql = "Select * From user Where active=true";
        return jdbcSecurity.query(sql, new BeanPropertyRowMapper<>(AuthUserDTO.class));
    }

    public void delete(String username) {
        deleteRoles(username);
        String sql = "UPDATE user SET active =0 WHERE username = '" + username + "'";
        jdbcSecurity.update(sql);
    }

    public boolean isAdmin(AuthRequestDTO authRequestDTO) {
        String sql = "SELECT r.role FROM user u \n" +
                "INNER JOIN user_roles ur ON ur.username = u.username\n" +
                "INNER JOIN role r ON ur.role_id = r.id\n" +
                "WHERE u.username = '" + authRequestDTO.getUsername() + "' AND ur.status='active';";
        List<String> roles = jdbcSecurity.queryForList(sql, String.class);
        return roles.contains("admin");
    }

    private void deleteRoles(String username) {
        String deleteSql = "DELETE FROM user_roles WHERE username = ?";
        jdbcSecurity.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(deleteSql);
            ps.setString(1, username);
            return ps;
        });
    }

}
