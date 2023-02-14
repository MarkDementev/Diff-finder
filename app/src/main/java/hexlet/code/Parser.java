package hexlet.code;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Parser {
    public static Map<String, Object> parseToMap(String filesExtension, String filePath)
            throws NullPointerException, JsonProcessingException {
        return switch (filesExtension) {
            case ".json" -> parseFromJSON(filePath);
            case ".yml" -> parseFromYAML(filePath);
            default -> throw new NullPointerException("There is no file extension!!!");
        };
    }

    public static Map<String, Object> parseFromJSON(String filePath) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(filePath, new TypeReference<>() { });
    }

    public static Map<String, Object> parseFromYAML(String filePath) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(filePath, new TypeReference<>() { });
    }
}
