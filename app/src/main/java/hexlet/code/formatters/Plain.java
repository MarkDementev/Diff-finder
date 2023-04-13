package hexlet.code.formatters;

import hexlet.code.Parser;
import hexlet.code.Tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Plain {
    private static  final String PROPERTY_TEXT = "Property '";
    private static  final String UPDATED_TEXT = "' was updated. From ";
    private static  final String REMOVED_TEXT = "' was removed\n";
    private static  final String UPDATED_TO_TEXT = " to ";
    private static  final String ADDED_TEXT = "' was added with value: ";
    private static  final String COMPLEX_TEXT = "[complex value]";

    public static String formPlainResult(Map<String, Object[]> diffMap) {
        Map<String, Object[]> diffMapWithFormattedValues = formatMapElements(diffMap);
        StringBuilder resultString = new StringBuilder();

        for (Map.Entry<String, Object[]> mapElement : diffMapWithFormattedValues.entrySet()) {
            String fileKey = mapElement.getKey();
            Object[] mapElementValuesArray = mapElement.getValue();
            String diffKey = mapElementValuesArray[0].toString();

            if (diffKey.equals(Tree.DELETED_KEY)) {
                resultString.append(PROPERTY_TEXT).append(fileKey).append(REMOVED_TEXT);
            } else if (diffKey.equals(Tree.UPDATED_KEY)) {
                resultString.append(PROPERTY_TEXT).append(fileKey)
                        .append(UPDATED_TEXT).append(mapElementValuesArray[1])
                        .append(UPDATED_TO_TEXT).append(mapElementValuesArray[2]).append("\n");
            } else if (diffKey.equals(Tree.ADDED_KEY)) {
                resultString.append(PROPERTY_TEXT).append(fileKey)
                        .append(ADDED_TEXT).append(mapElementValuesArray[1]).append("\n");
            }
        }
        return resultString.toString().trim();
    }

    private static Map<String, Object[]> formatMapElements(Map<String, Object[]> diffMap) {
        Map<String, Object[]> diffMapWithFormattedValues = new TreeMap<>();

        for (Map.Entry<String, Object[]> mapElement : diffMap.entrySet()) {
            String mapElementFileKey = mapElement.getKey();
            Object[] mapElementValuesArray = mapElement.getValue();
            String mapElementDiffKey = mapElementValuesArray[0].toString();
            Object[] arrayByAddValues = new Object[3];
            arrayByAddValues[0] = mapElementDiffKey;
            int fromFilesValuesCount = 1;

            if (mapElementDiffKey.equals(Tree.UPDATED_KEY)) {
                fromFilesValuesCount = 2;
            }

            for (int i = 1; i <= fromFilesValuesCount; i++) {
                Object mapElementValue = mapElementValuesArray[i].getClass();

                if (mapElementValue == ArrayList.class || mapElementValue == LinkedHashMap.class) {
                    arrayByAddValues[i] = COMPLEX_TEXT;
                } else if (mapElementValue == String.class
                        && !mapElementValuesArray[i].equals(Parser.NULL_STRING_TEXT)) {
                    arrayByAddValues[i] = "'" + mapElementValuesArray[i] + "'";
                } else {
                    arrayByAddValues[i] = mapElementValuesArray[i];
                }
            }
            diffMapWithFormattedValues.put(mapElementFileKey, arrayByAddValues);
        }
        return diffMapWithFormattedValues;
    }
}
