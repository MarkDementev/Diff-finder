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
        //Проработка 1-го комментария.
        //Программа выдаёт результат в 3-х форматах. Только для одного не нужно знать, какие изначально были
        //значения у ключей входных файлов - для json. У stylish и plain необходимо использовать значения по ключам
        //из исходных файлов, т.к. они демонстрируются юзеру. Например, если в stylish изменилось значение по ключу
        //(как в демонстрации работы программы в шаге №5), то юзеру показываются старое и новое значения.
        //Для того, чтобы эти значения можно было показать, вместе с keyDifferTypes, где хранятся только статусы ключей
        //(т.е. не менялся/удалён/изменился/добавлен), я передаю в Formatter и спарсенные мапы, откуда достаю значения,
        //если необходимо указывать их при выдаче результатов при использовании определённого формата.
        //Потому использую в Formatter-е firstFileParsedMap и secondFileParsedMap,
        //а не только keyDifferTypes и стиль format.
        //Тем более, ограничений на входные данные в методы Formatter-а в шагах проекта нет.
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
