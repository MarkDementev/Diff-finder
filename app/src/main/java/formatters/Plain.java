package formatters;

import hexlet.code.Differ;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Plain {
    public static String formResultStringByPlain(Map<String, String> keyDifferTypes,
                                                     Map<String, Object> firstFileParsedMap,
                                                     Map<String, Object> secondFileParsedMap) {
        if (keyDifferTypes.size() == 0) {
            return "";
        }
        Map<String, Object> firstPreparedMap = formatMapElements(firstFileParsedMap);
        Map<String, Object> secondPreparedMap = formatMapElements(secondFileParsedMap);
        StringBuilder treeMapToOutputString = new StringBuilder();

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();
            Object firstFileValueByElementKey = firstPreparedMap.get(elementKey);
            Object secondFileValueByElementKey = secondPreparedMap.get(elementKey);

            if (elementValue.equals(Differ.KEY_TYPES[1])) {
                treeMapToOutputString.append("Property '").append(elementKey).append("' was removed");
            } else if (elementValue.equals(Differ.KEY_TYPES[2])) {
                treeMapToOutputString.append("Property '").append(elementKey)
                        .append("' was updated. From ").append(firstFileValueByElementKey)
                        .append(" to ").append(secondFileValueByElementKey);
            } else if (elementValue.equals(Differ.KEY_TYPES[3])) {
                treeMapToOutputString.append("Property '").append(elementKey)
                        .append("' was added with value: ").append(secondFileValueByElementKey);
            }

            if (!elementValue.equals(Differ.KEY_TYPES[0])) {
                treeMapToOutputString.append("\n");
            }
        }
        return treeMapToOutputString.toString();
    }

    private static Map<String, Object> formatMapElements(Map<String, Object> inputParsedMap) {
        for (Map.Entry<String, Object> element : inputParsedMap.entrySet()) {
            Object elementValue = element.getValue();

            if (elementValue.getClass() == ArrayList.class || elementValue.getClass() == LinkedHashMap.class) {
                element.setValue("[complex value]");
            } else if (elementValue.getClass() == String.class && !elementValue.equals("null")) {
                element.setValue("'" + elementValue + "'");
            }
        }
        return inputParsedMap;
    }
}
