package msv.management.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "property_info", description = "Configurations schema")
public class AppConfigDTO {

    @ApiModelProperty(hidden = true)
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("component")
    private String component;
    @JsonProperty("instance")
    private String instance;
    @JsonProperty("config_key")
    private String configKey;
    @JsonProperty("config_value")
    private String configValue;
    @JsonProperty("description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
