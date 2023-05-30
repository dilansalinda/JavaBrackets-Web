package msv.management.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AuthUserDTO {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("active")
    private boolean active = true;

    @JsonProperty("unlocked")
    private boolean unlocked = true;

    @JsonProperty("roles")
    private List<RoleDTO> roles;
}
