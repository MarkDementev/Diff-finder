package hexlet.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Tree {
    public static final String UNCHANGED_KEY = "unchanged";
    public static final String DELETED_KEY = "deleted";
    public static final String UPDATED_KEY = "updated";
    public static final String ADDED_KEY = "added";
    public static final int NEEDED_VALUES_ARRAY_SIZE = 3;
    private static final String SECOND_MAP_MARK = "THIS_IS_SECOND_MAP_KEY/";
    private static final String NO_KEY_WARNING = "There is no differ key!";

    public static Map<String, Object[]> findDiff(Map<String, Object> firstFileParsedMap,
                                                 Map<String, Object> secondFileParsedMap) {
        Map<String, Object> unitedMap = formUnitedMap(firstFileParsedMap, secondFileParsedMap);
        return formDiffMap(unitedMap);
    }

    private static Map<String, Object> formUnitedMap(Map<String, Object> firstFileParsedMap,
                                                     Map<String, Object> secondFileParsedMap) {
        Map<String, Object> unitedMap = Objects.requireNonNullElseGet(firstFileParsedMap, HashMap::new);

        if (secondFileParsedMap != null) {
            for (Map.Entry<String, Object> secondMapElement : secondFileParsedMap.entrySet()) {
                String moddedKeyFromSecondMap = SECOND_MAP_MARK + secondMapElement.getKey();
                Object valueFromSecondMap = secondMapElement.getValue();

                unitedMap.put(moddedKeyFromSecondMap, valueFromSecondMap);
            }
        }
        return unitedMap;
    }

    private static Map<String, Object[]> formDiffMap(Map<String, Object> unitedMap) {
        Map<String, Object[]> diffMap = new TreeMap<>();

        for (Map.Entry<String, Object> unitedMapElement : unitedMap.entrySet()) {
            String unitedMapElementKey = unitedMapElement.getKey();
            Object unitedMapElementValue = unitedMapElement.getValue();
            String pureUnitedMapElementKey = null;
            Object[] intoDiffMapArray;

            if (unitedMapElementKey.contains(SECOND_MAP_MARK)) {
                pureUnitedMapElementKey = unitedMapElementKey.substring(SECOND_MAP_MARK.length());
            }

            if (pureUnitedMapElementKey == null) {
                intoDiffMapArray = formIntoDiffMapArray(DELETED_KEY, unitedMapElementValue, null);
                diffMap.put(unitedMapElementKey, intoDiffMapArray);
            } else if (!diffMap.containsKey(pureUnitedMapElementKey)) {
                intoDiffMapArray = formIntoDiffMapArray(ADDED_KEY, unitedMapElementValue, null);
                diffMap.put(pureUnitedMapElementKey, intoDiffMapArray);
            } else {
                Object[] valueArrayFromDiffMap = diffMap.get(pureUnitedMapElementKey);
                Object valueFromValueArray = valueArrayFromDiffMap[1];

                if (!Differ.isEqual(unitedMapElementValue, valueFromValueArray)) {
                    intoDiffMapArray = formIntoDiffMapArray(UPDATED_KEY, unitedMapElementValue, valueFromValueArray);
                } else {
                    intoDiffMapArray = formIntoDiffMapArray(UNCHANGED_KEY, unitedMapElementValue, valueFromValueArray);
                }
                diffMap.put(pureUnitedMapElementKey, intoDiffMapArray);
            }
        }
        return diffMap;
    }

    private static Object[] formIntoDiffMapArray(String formArrayForKey,
                                                 Object unitedMapElementValue,
                                                 Object valueFromValueArray) {
        Object[] intoDiffMapArray = new Object[NEEDED_VALUES_ARRAY_SIZE];
        intoDiffMapArray[0] = formArrayForKey;

        switch (formArrayForKey) {
            case DELETED_KEY, ADDED_KEY, UNCHANGED_KEY -> {
                intoDiffMapArray[1] = unitedMapElementValue;
            }
            case UPDATED_KEY -> {
                intoDiffMapArray[1] = valueFromValueArray;
                intoDiffMapArray[2] = unitedMapElementValue;
            }
            default -> throw new RuntimeException(NO_KEY_WARNING);
        }
        return intoDiffMapArray;
    }
}
