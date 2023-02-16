package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.Map;

public class Stylish {
    public static String formResultStringByStylish(Map<String, String> keyDifferTypes,
                                                   Map<String, Object> firstFileParsedMap,
                                                   Map<String, Object> secondFileParsedMap) {
        StringBuilder resultString = new StringBuilder("\n{\n");

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();

            if (elementValue.equals(Differ.KEY_TYPES[0])) {
                resultString.append("   ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(Differ.KEY_TYPES[1])) {
                resultString.append(" - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(Differ.KEY_TYPES[2])) {
                resultString.append(" - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey))
                        .append("\n + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            } else {
                resultString.append(" + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            }
            resultString.append("\n");
        }
        return resultString.append("}").toString();
    }
}
