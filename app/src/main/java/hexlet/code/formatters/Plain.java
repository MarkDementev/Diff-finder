package hexlet.code.formatters;

import hexlet.code.Differ;
import hexlet.code.Parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Plain {
    private static  final String PROPERTY_TEXT = "Property '";
    private static  final String UPDATED_TEXT = "' was updated. From ";
    private static  final String REMOVED_TEXT = "' was removed\n";
    private static  final String UPDATED_TO_TEXT = " to ";
    private static  final String ADDED_TEXT = "' was added with value: ";
    private static  final String COMPLEX_TEXT = "[complex value]";

    public static String formResultStringByPlain(Map<String, String> keyDifferTypes,
                                                     Map<String, Object> firstFileParsedMap,
                                                     Map<String, Object> secondFileParsedMap) {
        Map<String, Object> firstPreparedMap = formatMapElements(firstFileParsedMap);
        Map<String, Object> secondPreparedMap = formatMapElements(secondFileParsedMap);
        StringBuilder treeMapToOutputString = new StringBuilder();

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();
            Object firstFileValueByElementKey = firstPreparedMap.get(elementKey);
            Object secondFileValueByElementKey = secondPreparedMap.get(elementKey);

            if (elementValue.equals(Differ.KEY_TYPES[1])) {
                treeMapToOutputString.append(PROPERTY_TEXT).append(elementKey).append(REMOVED_TEXT);
            } else if (elementValue.equals(Differ.KEY_TYPES[2])) {
                treeMapToOutputString.append(PROPERTY_TEXT).append(elementKey)
                        .append(UPDATED_TEXT).append(firstFileValueByElementKey)
                        .append(UPDATED_TO_TEXT).append(secondFileValueByElementKey).append("\n");
            } else if (elementValue.equals(Differ.KEY_TYPES[3])) {
                treeMapToOutputString.append(PROPERTY_TEXT).append(elementKey)
                        .append(ADDED_TEXT).append(secondFileValueByElementKey).append("\n");
            }
        }
        return treeMapToOutputString.toString();
    }

    private static Map<String, Object> formatMapElements(Map<String, Object> inputParsedMap) {
        for (Map.Entry<String, Object> element : inputParsedMap.entrySet()) {
            Object elementValueClass = element.getValue().getClass();

            if (elementValueClass == ArrayList.class || elementValueClass == LinkedHashMap.class) {
                element.setValue(COMPLEX_TEXT);
            } else if (elementValueClass == String.class && !element.getValue().equals(Differ.NULL_STRING_TEXT)) {
                element.setValue("'" + element.getValue() + "'");
            }
        }
        return inputParsedMap;
    }
}
