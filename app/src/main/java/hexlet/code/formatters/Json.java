package hexlet.code.formatters;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    public static String formJSONResult(Map<String, Object[]> diffMap) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> newDiffMapWithoutValues = new TreeMap<>();

        for (Map.Entry<String, Object[]> diffMapElement : diffMap.entrySet()) {
            String diffMapFileKey = diffMapElement.getKey();
            Object[] diffMapValuesArray = diffMapElement.getValue();
            Object toNewMapValue = diffMapValuesArray[0];

            newDiffMapWithoutValues.put(diffMapFileKey, toNewMapValue);
        }
        return mapper.writeValueAsString(newDiffMapWithoutValues);
    }
}
