package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Json;

import java.util.Map;

public class Formatter {
    public static final String WRONG_FORMAT_WARNING = "There is unknown output format.\nCheck it!";

    public static String format(Map<String, String> keyDifferTypes,
                                Map<String, Object> firstFileParsedMap,
                                Map<String, Object> secondFileParsedMap,
                                String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> formByStylish(keyDifferTypes, firstFileParsedMap, secondFileParsedMap);
            case "plain" -> formByPlain(keyDifferTypes, firstFileParsedMap, secondFileParsedMap);
            case "json" -> formByJSON(keyDifferTypes);
            default -> throw new RuntimeException(WRONG_FORMAT_WARNING);
        };
    }

    private static String formByStylish(Map<String, String> keyDifferTypes,
                                        Map<String, Object> firstFileParsedMap,
                                        Map<String, Object> secondFileParsedMap) {
        return Stylish.formStylishResult(keyDifferTypes, firstFileParsedMap, secondFileParsedMap);
    }

    private static String formByPlain(Map<String, String> keyDifferTypes,
                                      Map<String, Object> firstFileParsedMap,
                                      Map<String, Object> secondFileParsedMap) {
        return Plain.formPlainResult(keyDifferTypes, firstFileParsedMap, secondFileParsedMap);
    }

    private static String formByJSON(Map<String, String> keyDifferTypes) throws JsonProcessingException {
        return Json.formJSONResult(keyDifferTypes);
    }
}
