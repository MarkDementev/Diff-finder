package hexlet.code.formatters;

import hexlet.code.Tree;

import java.util.Map;

public class Stylish {
    public static String formResultStringByStylish(Map<String, String> keyDifferTypes,
                                                   Map<String, Object> firstFileParsedMap,
                                                   Map<String, Object> secondFileParsedMap) {
        StringBuilder resultString = new StringBuilder("{\n");

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();

            if (elementValue.equals(Tree.UNCHANGED_KEY)) {
                resultString.append("    ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(Tree.DELETED_KEY)) {
                resultString.append("  - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(Tree.UPDATED_KEY)) {
                resultString.append("  - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey))
                        .append("\n  + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            } else {
                resultString.append("  + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            }
            resultString.append("\n");
        }
        return resultString.append("}").toString();
    }
}
