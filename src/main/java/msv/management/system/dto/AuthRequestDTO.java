package msv.management.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "AuthRequest", description = "An Auth request")
@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
