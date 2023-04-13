package hexlet.code.formatters;

import hexlet.code.Tree;

import java.util.Map;

public class Stylish {
    public static String formStylishResult(Map<String, Object[]> diffMap) {
        StringBuilder resultString = new StringBuilder("{\n");

        for (Map.Entry<String, Object[]> keyWithTypeAndValue : diffMap.entrySet()) {
            String elementKey = keyWithTypeAndValue.getKey();
            Object[] keyTypeAndValueArray = keyWithTypeAndValue.getValue();
            String keyType = String.valueOf(keyTypeAndValueArray[0]);
            Object value = keyTypeAndValueArray[1];

            switch (keyType) {
                case Tree.UNCHANGED_KEY ->
                        resultString.append("    ").append(elementKey)
                                .append(": ").append(value);
                case Tree.DELETED_KEY ->
                        resultString.append("  - ").append(elementKey)
                                .append(": ").append(value);
                case Tree.UPDATED_KEY ->
                        resultString.append("  - ").append(elementKey)
                                .append(": ").append(value)
                                .append("\n  + ").append(elementKey)
                                .append(": ").append(keyTypeAndValueArray[2]);
                default ->
                        resultString.append("  + ").append(elementKey)
                                .append(": ").append(value);
            }
            resultString.append("\n");
        }
        return resultString.append("}").toString();
    }
}
