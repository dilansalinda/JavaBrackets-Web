package msv.management.system.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {


    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }


    private static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper;
    }

    public static <T extends Object> List<T> toList(final String jsonArray, Class<T> clazz) {
        try {
            CollectionType valueType = TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
            return objectMapper().readValue(jsonArray, valueType);
        } catch (IOException e) {
            String msg = "Invalid JSON";
            LOGGER.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public static Map<String, Object> readJsonAsMap(final String data) {
        TypeReference sTypeReference = new TypeReference<HashMap<String, Object>>() {
        };
        try {
            return (Map<String, Object>) objectMapper().readValue(data, sTypeReference);
        } catch (IOException e) {
            String msg = "Invalid JSON";
            LOGGER.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}
