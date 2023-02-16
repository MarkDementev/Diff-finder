package hexlet.code;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Parser {
    private static final String NO_FILE_EXTENSION_WARNING = "There is no file extension!!!";
    public static Map<String, Object> parseToMap(String filesExtension, String filePath)
            throws NullPointerException, JsonProcessingException {
        return switch (filesExtension) {
            case ".json" -> parseFromJSON(filePath);
            case ".yml" -> parseFromYAML(filePath);
            default -> throw new NullPointerException(NO_FILE_EXTENSION_WARNING);
        };
    }

    private static Map<String, Object> parseFromJSON(String filePath) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jSONMap = mapper.readValue(filePath, new TypeReference<>() { });

        return makeNullsToString(jSONMap);
    }

    private static Map<String, Object> parseFromYAML(String filePath) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<String, Object> yAMLMap = mapper.readValue(filePath, new TypeReference<>() { });

        return makeNullsToString(yAMLMap);
    }

    private static Map<String, Object> makeNullsToString(Map<String, Object> map) {
        if (map == null) {
            return null;
        }

        for (Map.Entry<String, Object> element : map.entrySet()) {
            if (element.getValue() == null) {
                element.setValue(Differ.NULL_STRING_TEXT);
            }
        }
        return map;
    }
}
