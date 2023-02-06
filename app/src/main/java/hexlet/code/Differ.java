package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    private static final String[] KEY_TYPES = {"unchanged", "deleted", "updated", "added"};

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        String firstFileToString = pathCheckThenToString(firstFilePath);
        String secondFileToString = pathCheckThenToString(secondFilePath);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> firstFileParsedMap = mapper.readValue(firstFileToString, new TypeReference<>() { });
        Map<String, Object> secondFileParsedMap = mapper.readValue(secondFileToString, new TypeReference<>() { });
        Map<String, String> keyTypes = formKeyTypesMap(firstFileParsedMap, secondFileParsedMap);

        return formOutputString(keyTypes, firstFileParsedMap, secondFileParsedMap);
    }

    private static String pathCheckThenToString(String filePath) throws Exception {
        Path absoluteFilePath = Paths.get(filePath).toAbsolutePath().normalize();

        if (!Files.exists(absoluteFilePath)) {
            throw new Exception("'" + absoluteFilePath + "' does not exist.\nCheck it!");
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
                        .append("   ").append(elementKey).append(": ")
                        .append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(KEY_TYPES[1])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ")
                        .append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(KEY_TYPES[2])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ")
                        .append(firstFileParsedMap.get(elementKey)).append("\n")
                        .append(" + ").append(elementKey).append(": ")
                        .append(secondFileParsedMap.get(elementKey));
            } else if (elementValue.equals(KEY_TYPES[3])) {
                treeMapToOutputString
                        .append(" + ").append(elementKey).append(": ")
                        .append(secondFileParsedMap.get(elementKey));
            }
            treeMapToOutputString.append("\n");
        }
        return treeMapToOutputString.append("}").toString();
    }
}
