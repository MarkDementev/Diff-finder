package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Json;

import java.util.Map;

public class Formatter {
    public static final String WRONG_FORMAT_WARNING = "There is unknown output format.\nCheck it!";

    public static String format(Map<String, Object[]> diffMap, String format)
            throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> formByStylish(diffMap);
            case "plain" -> formByPlain(diffMap);
            case "json" -> formByJSON(diffMap);
            default -> throw new RuntimeException(WRONG_FORMAT_WARNING);
        };
    }

    private static String formByStylish(Map<String, Object[]> diffMap) {
        return Stylish.formStylishResult(diffMap);
    }

    private static String formByPlain(Map<String, Object[]> diffMap) {
        return Plain.formPlainResult(diffMap);
    }

    private static String formByJSON(Map<String, Object[]> diffMap) throws JsonProcessingException {
        return Json.formJSONResult(diffMap);
    }
}
