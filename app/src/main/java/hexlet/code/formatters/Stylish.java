package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.Map;

public class Stylish {
    public static String formResultStringByStylish(Map<String, String> keyDifferTypes,
                                                   Map<String, Object> firstFileParsedMap,
                                                   Map<String, Object> secondFileParsedMap) {
        StringBuilder treeMapToOutputString = new StringBuilder("\n{\n");

        for (Map.Entry<String, String> element : keyDifferTypes.entrySet()) {
            String elementKey = element.getKey();
            String elementValue = element.getValue();

            if (elementValue.equals(Differ.KEY_TYPES[0])) {
                treeMapToOutputString
                        .append("   ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(Differ.KEY_TYPES[1])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey));
            } else if (elementValue.equals(Differ.KEY_TYPES[2])) {
                treeMapToOutputString
                        .append(" - ").append(elementKey).append(": ").append(firstFileParsedMap.get(elementKey))
                        .append("\n + ").append(elementKey).append(": ")
                        .append(secondFileParsedMap.get(elementKey));
            } else {
                treeMapToOutputString
                        .append(" + ").append(elementKey).append(": ").append(secondFileParsedMap.get(elementKey));
            }
            treeMapToOutputString.append("\n");
        }
        return treeMapToOutputString.append("}").toString();
    }
}
