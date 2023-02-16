package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Json;

import java.util.Map;

public class Formatter {
    public static final String WRONG_FORMAT_WARNING = "There is unknown output format.\nCheck it!";

    public static String useFormatToFormResultString(Map<String, String> keyDifferTypes,
                                                     Map<String, Object> firstFileParsedMap,
                                                     Map<String, Object> secondFileParsedMap,
                                                     String format) throws Exception {
        return switch (format) {
            case "stylish" -> Stylish.formResultStringByStylish(keyDifferTypes, firstFileParsedMap,
                    secondFileParsedMap);
            case "plain" -> Plain.formResultStringByPlain(keyDifferTypes, firstFileParsedMap,
                    secondFileParsedMap);
            case "json" -> Json.formResultStringByJson(keyDifferTypes, firstFileParsedMap,
                    secondFileParsedMap);
            default -> throw new Exception(WRONG_FORMAT_WARNING);
        };
    }
}
