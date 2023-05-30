package msv.management.system.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
@Configuration
public class ConfigurationConstants {

    private static Boolean AUTH_ENABLED;

    public static Boolean getAuthEnabled() {
        return AUTH_ENABLED;
    }

    @Value("${auth.enabled:false}")
    public void setAuthEnabled(Boolean authenticationEnabled) {
        AUTH_ENABLED = authenticationEnabled;
    }

}
