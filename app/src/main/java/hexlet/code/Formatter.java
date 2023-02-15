package hexlet.code;

import formatters.Plain;
import formatters.Stylish;

import java.util.Map;

public class Formatter {
    public static String WRONG_FORMAT_WARNING = "There is unknown output format.\nCheck it!";
    public static String useFormatToFormOutputString(Map<String, String> keyDifferTypes,
                                                     Map<String, Object> firstFileParsedMap,
                                                     Map<String, Object> secondFileParsedMap,
                                                     String format) throws Exception {
        return switch (format) {
            case "stylish" -> Stylish.formResultStringByStylish(keyDifferTypes, firstFileParsedMap,
                    secondFileParsedMap);
            case "plain" -> Plain.formResultStringByPlain(keyDifferTypes, firstFileParsedMap,
                    secondFileParsedMap);
            default -> throw new Exception(WRONG_FORMAT_WARNING);
        };
    }
}
