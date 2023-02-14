package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    public static final String DIFFERENT_EXTENSIONS_ERROR = "Files has different filename extensions."
            + "\nEnter paths only with same filename extensions!";
    public static final String UNKNOWN_EXTENSION_ERROR = "There is unknown filename extension.\nCheck input files!";
    private static final String[] KEY_TYPES = {"unchanged", "deleted", "updated", "added"};
    private static final String[] FILE_EXTENSIONS = {".json", ".yml", ".yaml"};

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        String filesExtension = findBothFilesExtension(firstFilePath, secondFilePath);
        String firstFileAbsolutePath = isFileExistThenToAbsolutePath(firstFilePath);
        String secondFileAbsolutePath = isFileExistThenToAbsolutePath(secondFilePath);
        Map<String, Object> firstFileParsedMap = Parser.parseToMap(filesExtension, firstFileAbsolutePath);
        Map<String, Object> secondFileParsedMap = Parser.parseToMap(filesExtension, secondFileAbsolutePath);
        Map<String, String> keyTypes = formKeyTypesMap(firstFileParsedMap, secondFileParsedMap);

        return formOutputString(keyTypes, firstFileParsedMap, secondFileParsedMap);
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

    private static Map<String, String> formKeyTypesMap(Map<String, Object> firstFileParsedMap,
                                                       Map<String, Object> secondFileParsedMap) {
        Map<String, String> keyTypes = new TreeMap<>();

        for (Map.Entry<String, Object> firstMapElement : firstFileParsedMap.entrySet()) {
            String firstMapElementKey = firstMapElement.getKey();
            Object firstMapElementValue = firstMapElement.getValue();

            if (secondFileParsedMap.containsKey(firstMapElementKey)
                    && secondFileParsedMap.get(firstMapElementKey).equals(firstMapElementValue)) {
                keyTypes.put(firstMapElementKey, KEY_TYPES[0]);
            } else if (!secondFileParsedMap.containsKey(firstMapElementKey)) {
                keyTypes.put(firstMapElementKey, KEY_TYPES[1]);
            } else if (secondFileParsedMap.containsKey(firstMapElementKey)
                    && !secondFileParsedMap.get(firstMapElementKey).equals(firstMapElementValue)) {
                keyTypes.put(firstMapElementKey, KEY_TYPES[2]);
            }
        }

        for (String secondMapElementKey : secondFileParsedMap.keySet()) {
            if (!firstFileParsedMap.containsKey(secondMapElementKey)) {
                keyTypes.put(secondMapElementKey, KEY_TYPES[3]);
            }
        }
        return keyTypes;
    }

    private static String formOutputString(Map<String, String> keyTypes, Map<String, Object> firstFileParsedMap,
                                           Map<String, Object> secondFileParsedMap) {
        StringBuilder treeMapToOutputString = new StringBuilder("\n{\n");

        for (Map.Entry<String, String> element : keyTypes.entrySet()) {
            String elementKey = element.getKey();
            Object elementValue = element.getValue();

            if (elementValue.equals(KEY_TYPES[0])) {
                treeMapToOutputString
                        .append("   ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(KEY_TYPES[1])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(KEY_TYPES[2])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey))
                        .append("\n + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            } else if (elementValue.equals(KEY_TYPES[3])) {
                treeMapToOutputString
                        .append(" + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            }

            treeMapToOutputString.append("\n");
        }
        return treeMapToOutputString.append("}").toString();
    }
}
