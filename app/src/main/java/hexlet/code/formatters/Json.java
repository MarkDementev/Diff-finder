package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static final String[] UPDATE_DIFFER_TYPE_JSON_TEXT_ELEMENTS = {"was", "now"};
    public static String formResultStringByJson(Map<String, String> keyDifferTypes,
                                                Map<String, Object> firstFileParsedMap,
                                                Map<String, Object> secondFileParsedMap)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> outputMap = new TreeMap<>();

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();

            if (elementValue.equals(Differ.KEY_TYPES[0]) || elementValue.equals(Differ.KEY_TYPES[1])) {
                outputMap.put(elementKey, Map.of(elementValue, firstFileParsedMap.get(elementKey)));
            } else if (elementValue.equals(Differ.KEY_TYPES[2])) {
                outputMap.put(elementKey, new LinkedHashMap<>(Map.of(
                        UPDATE_DIFFER_TYPE_JSON_TEXT_ELEMENTS[0], firstFileParsedMap.get(elementKey),
                        UPDATE_DIFFER_TYPE_JSON_TEXT_ELEMENTS[1], secondFileParsedMap.get(elementKey))));
            } else {
                outputMap.put(elementKey, Map.of(elementValue, secondFileParsedMap.get(elementKey)));
            }
        }
        return mapper.writeValueAsString(outputMap);
    }
}
