package hexlet.code;

import java.util.Map;
import java.util.TreeMap;

public class Tree {
    public static final String UNCHANGED_KEY = "unchanged";
    public static final String DELETED_KEY = "deleted";
    public static final String UPDATED_KEY = "updated";
    public static final String ADDED_KEY = "added";
    private static final int[] PARSED_MAPS_SERIAL_NUMBERS = {1, 2};

    public static Map<String, String> formKeyDifferMap(Map<String, Object> firstFileParsedMap,
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
                keyDifferTypes.put(firstMapElementKey, UNCHANGED_KEY);
            } else if (!secondFileParsedMap.containsKey(firstMapElementKey)) {
                keyDifferTypes.put(firstMapElementKey, DELETED_KEY);
            } else {
                keyDifferTypes.put(firstMapElementKey, UPDATED_KEY);
            }
        }

        for (String secondMapElementKey : secondFileParsedMap.keySet()) {
            if (!firstFileParsedMap.containsKey(secondMapElementKey)) {
                keyDifferTypes.put(secondMapElementKey, ADDED_KEY);
            }
        }
        return keyDifferTypes;
    }

    private static Map<String, String> formKeyDifferMapWhenOneInputMapNull(Map<String, Object> noNullMap,
                                                                           Map<String, String> keyDifferTypes,
                                                                           int nullMapSerialNumber) {
        for (Map.Entry<String, Object> element : noNullMap.entrySet()) {
            if (nullMapSerialNumber == PARSED_MAPS_SERIAL_NUMBERS[0]) {
                keyDifferTypes.put(element.getKey(), ADDED_KEY);
            } else {
                keyDifferTypes.put(element.getKey(), DELETED_KEY);
            }
        }
        return keyDifferTypes;
    }
}
