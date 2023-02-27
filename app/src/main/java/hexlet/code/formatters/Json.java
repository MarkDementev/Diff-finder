package hexlet.code.formatters;

import hexlet.code.Tree;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Json {
    private static final String[] UPDATE_DIFFER_TYPE_JSON_TEXT_ELEMENTS = {"was", "now"};

    public static String formResultStringByJson(Map<String, String> keyDifferTypes,
                                                Map<String, Object> firstFileParsedMap,
                                                Map<String, Object> secondFileParsedMap)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> outputMap = new LinkedHashMap<>();

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();

            if (elementValue.equals(Tree.UNCHANGED_KEY) || elementValue.equals(Tree.DELETED_KEY)) {
                outputMap.put(elementKey, Map.of(elementValue, firstFileParsedMap.get(elementKey)));
            } else if (elementValue.equals(Tree.UPDATED_KEY)) {
                outputMap.put(elementKey, formLinkedHashMap(elementKey, firstFileParsedMap, secondFileParsedMap));
            } else {
                outputMap.put(elementKey, Map.of(elementValue, secondFileParsedMap.get(elementKey)));
            }
        }
        return mapper.writeValueAsString(outputMap);
    }

    private static LinkedHashMap<String, Object> formLinkedHashMap(String elementKey,
                                                                   Map<String, Object> firstFileParsedMap,
                                                                   Map<String, Object> secondFileParsedMap) {
        LinkedHashMap<String, Object> whenAddedMiddleMap = new LinkedHashMap<>();
        whenAddedMiddleMap.put(UPDATE_DIFFER_TYPE_JSON_TEXT_ELEMENTS[0], firstFileParsedMap.get(elementKey));
        whenAddedMiddleMap.put(UPDATE_DIFFER_TYPE_JSON_TEXT_ELEMENTS[1], secondFileParsedMap.get(elementKey));
        return whenAddedMiddleMap;
    }
}
