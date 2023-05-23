package msv.management.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("role")
    private String role;
}
