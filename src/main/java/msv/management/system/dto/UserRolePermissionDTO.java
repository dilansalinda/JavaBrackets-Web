package msv.management.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel(value = "UserPermission", description = "User Permission schema")
@Data
public class UserRolePermissionDTO {

    @ApiModelProperty(
            example = "johnvw",
            value = "Unique username of the user")
    private String username;

    @ApiModelProperty(
            example = "[\"user_management\"]",
            value = "Unique username of the user")
    private List<String> permissions;
}
