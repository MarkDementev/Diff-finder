package hexlet.code.formatters;

import hexlet.code.Parser;
import hexlet.code.Tree;

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

    public static String formPlainResult(Map<String, String> keyDifferTypes,
                                                 Map<String, Object> firstFileParsedMap,
                                                 Map<String, Object> secondFileParsedMap) {
        StringBuilder resultString = new StringBuilder();
        Map<String, Object> firstPreparedMap = formatMapElements(firstFileParsedMap);
        Map<String, Object> secondPreparedMap = formatMapElements(secondFileParsedMap);

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();
            Object firstFileValueByElementKey = firstPreparedMap.get(elementKey);
            Object secondFileValueByElementKey = secondPreparedMap.get(elementKey);

            if (elementValue.equals(Tree.DELETED_KEY)) {
                resultString.append(PROPERTY_TEXT).append(elementKey).append(REMOVED_TEXT);
            } else if (elementValue.equals(Tree.UPDATED_KEY)) {
                resultString.append(PROPERTY_TEXT).append(elementKey)
                        .append(UPDATED_TEXT).append(firstFileValueByElementKey)
                        .append(UPDATED_TO_TEXT).append(secondFileValueByElementKey).append("\n");
            } else if (elementValue.equals(Tree.ADDED_KEY)) {
                resultString.append(PROPERTY_TEXT).append(elementKey)
                        .append(ADDED_TEXT).append(secondFileValueByElementKey).append("\n");
            }
        }
        return resultString.toString().trim();
    }

    private static Map<String, Object> formatMapElements(Map<String, Object> inputMap) {
        for (Map.Entry<String, Object> element : inputMap.entrySet()) {
            Object elementValueClass = element.getValue().getClass();

            if (elementValueClass == ArrayList.class || elementValueClass == LinkedHashMap.class) {
                element.setValue(COMPLEX_TEXT);
            } else if (elementValueClass == String.class && !element.getValue().equals(Parser.NULL_STRING_TEXT)) {
                element.setValue("'" + element.getValue() + "'");
            }
        }
        return inputMap;
    }
}
