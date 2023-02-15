package formatters;

import hexlet.code.Differ;

import java.util.ArrayList;
import java.util.Map;

public class Plain {
    public static String formResultStringByPlain(Map<String, String> keyDifferTypes,
                                                     Map<String, Object> firstFileParsedMap,
                                                     Map<String, Object> secondFileParsedMap) {
        if (keyDifferTypes.size() == 0) {
            return "";
        }
        Map<String, Object> firstPreparedMap = addComplexValues(firstFileParsedMap);
        Map<String, Object> secondPreparedMap = addComplexValues(secondFileParsedMap);
        StringBuilder treeMapToOutputString = new StringBuilder();

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();
            Object firstFileValueByElementKey = firstPreparedMap.get(elementKey);
            Object secondFileValueByElementKey = secondPreparedMap.get(elementKey);

            if (elementValue.equals(Differ.KEY_TYPES[1])) {
                treeMapToOutputString.append("Property '").append(elementKey).append("' was removed");
            } else if (elementValue.equals(Differ.KEY_TYPES[2])) {
                treeMapToOutputString
                        .append("Property '").append(elementKey)
                        .append("' was updated. From ").append("'").append(firstFileValueByElementKey).append("'")
                        .append(" to ").append("'").append(secondFileValueByElementKey).append("'");
            } else if (elementValue.equals(Differ.KEY_TYPES[3])) {
                treeMapToOutputString
                        .append("Property '").append(elementKey).append("' was added with value: ")
                        .append("'").append(secondFileValueByElementKey).append("'");
            }
            treeMapToOutputString.append("\n");
        }
        return treeMapToOutputString.toString();
    }

    private static Map<String, Object> addComplexValues(Map<String, Object> inputParsedMap) {
        for (Map.Entry<String, Object> element : inputParsedMap.entrySet()) {
            if (element.getValue().getClass() == ArrayList.class) {
                element.setValue("[complex value]");
            }
        }
        return inputParsedMap;
    }
}
