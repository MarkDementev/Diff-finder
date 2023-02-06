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
        Path absoluteFirstFilePath = Paths.get(firstFilePath).toAbsolutePath().normalize();
        Path absoluteSecondFilePath = Paths.get(secondFilePath).toAbsolutePath().normalize();

        if (!Files.exists(absoluteFirstFilePath)) {
            throw new Exception("'" + absoluteFirstFilePath + "' does not exist.\nBut check both paths!");
        } else if (!Files.exists(absoluteSecondFilePath)) {
            throw new Exception("'" + absoluteFirstFilePath + "' path exist.\n"
                    + "But '" + absoluteSecondFilePath + "' does not exist.\nCheck it!");
        }
        String firstFileToString = Files.readString(absoluteFirstFilePath);
        String secondFileToString = Files.readString(absoluteSecondFilePath);
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder treeMapToOutputString = new StringBuilder();
        Map<String, Object> firstFileMap = mapper.readValue(
                firstFileToString, new TypeReference<>() { });
        Map<String, Object> secondFileMap = mapper.readValue(
                secondFileToString, new TypeReference<>() { });
        Map<String, Object> checkedKeys = new TreeMap<>();

        for (Map.Entry<String, Object> firstMapElement : firstFileMap.entrySet()) {
            String firstMapElementKey = firstMapElement.getKey();
            Object firstMapElementValue = firstMapElement.getValue();

            if (secondFileMap.containsKey(firstMapElementKey)
                    && secondFileMap.get(firstMapElementKey).equals(firstMapElementValue)) {
                checkedKeys.put(firstMapElementKey, KEY_TYPES[0]);
            } else if (!secondFileMap.containsKey(firstMapElementKey)) {
                checkedKeys.put(firstMapElementKey, KEY_TYPES[1]);
            } else if (secondFileMap.containsKey(firstMapElementKey)
                    && !secondFileMap.get(firstMapElementKey).equals(firstMapElementValue)) {
                checkedKeys.put(firstMapElementKey, KEY_TYPES[2]);
            }
        }

        for (String secondMapElementKey : secondFileMap.keySet()) {
            if (!firstFileMap.containsKey(secondMapElementKey)) {
                checkedKeys.put(secondMapElementKey, KEY_TYPES[3]);
            }
        }
        treeMapToOutputString.append("\n").append("{\n");

        for (Map.Entry<String, Object> element : checkedKeys.entrySet()) {
            String elementKey = element.getKey();
            Object elementValue = element.getValue();

            if (elementValue.equals(KEY_TYPES[0])) {
                treeMapToOutputString
                        .append("   ").append(elementKey).append(": ")
                        .append(firstFileMap.get(elementKey)).append("\n");
            } else if (elementValue.equals(KEY_TYPES[1])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ")
                        .append(firstFileMap.get(elementKey)).append("\n");
            } else if (elementValue.equals(KEY_TYPES[2])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ")
                        .append(firstFileMap.get(elementKey)).append("\n")
                        .append(" + ").append(elementKey).append(": ")
                        .append(secondFileMap.get(elementKey)).append("\n");
            } else if (elementValue.equals(KEY_TYPES[3])) {
                treeMapToOutputString
                        .append(" + ").append(elementKey).append(": ")
                        .append(secondFileMap.get(elementKey)).append("\n");
            }
        }
        treeMapToOutputString.append("}");
        return treeMapToOutputString.toString();
    }
}
