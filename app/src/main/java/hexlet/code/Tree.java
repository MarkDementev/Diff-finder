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
    private static final String SECOND_MAP_MARK = "THIS_IS_SECOND_MAP_KEY/";

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
            boolean isKeyFromSecondMap = false;
            Object[] intoDiffMapArray = new Object[3];

            if (unitedMapElementKey.contains(SECOND_MAP_MARK)) {
                isKeyFromSecondMap = true;
                pureUnitedMapElementKey = unitedMapElementKey.substring(SECOND_MAP_MARK.length());
            }

            if (!isKeyFromSecondMap) {
                intoDiffMapArray[0] = DELETED_KEY;
                intoDiffMapArray[1] = unitedMapElementValue;
                diffMap.put(unitedMapElementKey, intoDiffMapArray);
            } else if (!diffMap.containsKey(pureUnitedMapElementKey)) {
                intoDiffMapArray[0] = ADDED_KEY;
                intoDiffMapArray[1] = unitedMapElementValue;
                diffMap.put(pureUnitedMapElementKey, intoDiffMapArray);
            } else {
                Object[] valueArrayFromDiffMap = diffMap.get(pureUnitedMapElementKey);
                Object valueFromValueArray = valueArrayFromDiffMap[1];

                if (!unitedMapElementValue.equals(valueFromValueArray)) {
                    intoDiffMapArray[0] = UPDATED_KEY;
                    intoDiffMapArray[1] = valueFromValueArray;
                    intoDiffMapArray[2] = unitedMapElementValue;
                } else {
                    intoDiffMapArray[0] = UNCHANGED_KEY;
                    intoDiffMapArray[1] = unitedMapElementValue;
                }
                diffMap.put(pureUnitedMapElementKey, intoDiffMapArray);
            }
        }
        return diffMap;
    }
}
