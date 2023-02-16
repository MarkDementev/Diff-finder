package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    public static  final String NULL_STRING_TEXT = "null";
    public static final String BOTH_FILES_EMPTY = "Both files are empty!";
    public static final String DIFFERENT_EXTENSIONS_ERROR = "Files has different filename extensions."
            + "\nEnter paths only with same filename extensions!";
    public static final String UNKNOWN_EXTENSION_ERROR = "There is unknown filename extension.\nCheck input files!";
    public static final String[] KEY_TYPES = {"unchanged", "deleted", "updated", "added"};
    private static final String[] FILE_EXTENSIONS = {".json", ".yml", ".yaml"};
    private static final int[] PARSED_MAPS_SERIAL_NUMBERS = {1, 2};

    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        String filesExtension = findBothFilesExtension(firstFilePath, secondFilePath);
        String firstFileAbsolutePath = isFileExistThenToAbsolutePath(firstFilePath);
        String secondFileAbsolutePath = isFileExistThenToAbsolutePath(secondFilePath);
        Map<String, Object> firstFileParsedMap = Parser.parseToMap(filesExtension, firstFileAbsolutePath);
        Map<String, Object> secondFileParsedMap = Parser.parseToMap(filesExtension, secondFileAbsolutePath);

        if (filesExtension.equals(FILE_EXTENSIONS[0])) {
            if (firstFileParsedMap.size() == 0 && secondFileParsedMap.size() == 0) {
                return BOTH_FILES_EMPTY;
            }
        } else {
            if (firstFileParsedMap == null && secondFileParsedMap == null) {
                return BOTH_FILES_EMPTY;
            }
        }
        Map<String, String> keyDifferTypes = formKeyDifferMap(firstFileParsedMap, secondFileParsedMap);

        return Formatter.useFormatToFormOutputString(keyDifferTypes, firstFileParsedMap, secondFileParsedMap, format);
    }

    private static String findBothFilesExtension(String firstFilePath, String secondFilePath) throws Exception {
        String firstFileExtension = isCorrectExtension(firstFilePath);
        String secondFileExtension = isCorrectExtension(secondFilePath);

        if (!firstFileExtension.equals(secondFileExtension)) {
            throw new Exception(DIFFERENT_EXTENSIONS_ERROR);
        }
        return firstFileExtension;
    }

    private static String isCorrectExtension(String filePath) throws Exception {
        String checkedFilePath = filePath.substring(filePath.lastIndexOf('.'));

        if (checkedFilePath.equals(FILE_EXTENSIONS[1]) || checkedFilePath.equals(FILE_EXTENSIONS[2])) {
            return FILE_EXTENSIONS[1];
        } else if (checkedFilePath.equals(FILE_EXTENSIONS[0])) {
            return FILE_EXTENSIONS[0];
        }
        throw new Exception(UNKNOWN_EXTENSION_ERROR);
    }

    private static String isFileExistThenToAbsolutePath(String filePath) throws IOException {
        Path absoluteFilePath = Paths.get(filePath).toAbsolutePath().normalize();

        if (!Files.exists(absoluteFilePath)) {
            throw new IOException("'" + absoluteFilePath + "' does not exist.\nCheck it!");
        }
        return Files.readString(absoluteFilePath);
    }

    private static Map<String, String> formKeyDifferMap(Map<String, Object> firstFileParsedMap,
                                                        Map<String, Object> secondFileParsedMap) {
        Map<String, String> keyDifferTypes = new TreeMap<>();

        if (firstFileParsedMap == null) {
            return formKeyDifferMapWhenOneInputMapNull(secondFileParsedMap, keyDifferTypes,
                    PARSED_MAPS_SERIAL_NUMBERS[0]);
        } else if (secondFileParsedMap == null) {
            return formKeyDifferMapWhenOneInputMapNull(firstFileParsedMap, keyDifferTypes,
                    PARSED_MAPS_SERIAL_NUMBERS[1]);
        }

        for (Map.Entry<String, Object> firstMapElement : firstFileParsedMap.entrySet()) {
            String firstMapElementKey = firstMapElement.getKey();
            Object secondMapValueByFirstMapElementKey = secondFileParsedMap.get(firstMapElementKey);

            if (secondFileParsedMap.containsKey(firstMapElementKey)
                    && secondMapValueByFirstMapElementKey.equals(firstMapElement.getValue())) {
                keyDifferTypes.put(firstMapElementKey, KEY_TYPES[0]);
            } else if (!secondFileParsedMap.containsKey(firstMapElementKey)) {
                keyDifferTypes.put(firstMapElementKey, KEY_TYPES[1]);
            } else {
                keyDifferTypes.put(firstMapElementKey, KEY_TYPES[2]);
            }
        }

        for (String secondMapElementKey : secondFileParsedMap.keySet()) {
            if (!firstFileParsedMap.containsKey(secondMapElementKey)) {
                keyDifferTypes.put(secondMapElementKey, KEY_TYPES[3]);
            }
        }
        return keyDifferTypes;
    }

    private static Map<String, String> formKeyDifferMapWhenOneInputMapNull(Map<String, Object> noNullMap,
                                                                           Map<String, String> keyDifferTypes,
                                                                           int nullMapSerialNumber) {
        for (Map.Entry<String, Object> element : noNullMap.entrySet()) {
            if (nullMapSerialNumber == PARSED_MAPS_SERIAL_NUMBERS[0]) {
                keyDifferTypes.put(element.getKey(), KEY_TYPES[3]);
            } else {
                keyDifferTypes.put(element.getKey(), KEY_TYPES[1]);
            }
        }
        return keyDifferTypes;
    }
}
