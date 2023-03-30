package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DifferTest {
    public static final String FIRST_JSON_PATH_1 =
            "./src/test/resources/fixtures/JSON-files/jSONPath1.json";
    public static final String FIRST_JSON_PATH_2 =
            "./src/test/resources/fixtures/JSON-files/jSONPath2.json";
    private static final String FIRST_YAML_PATH_1 =
            "./src/test/resources/fixtures.YAML-files/yAMLPath1.yml";
    private static final String FIRST_YAML_PATH_2 =
            "./src/test/resources/fixtures.YAML-files/yAMLPath2.yml";
    private static final String EMPTY_JSON_PATH =
            "./src/test/resources/fixtures/JSON-files/emptyFile.json";
    private static final String EMPTY_YAML_PATH =
            "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
    private static final String FILE_DOESNT_EXIST_JSON =
            "./src/test/resources/fixtures/JSON-files/this-file-is-fantasy.json";
    private static final String FILE_DOESNT_EXIST_YAML =
            "./src/test/resources/fixtures.YAML-files/this-file-is-fantasy.yml";
    private static final String UNKNOWN_FORMAT_PATH =
            "./src/test/resources/fixtures.OTH-files/unknownFormatPath.oth";
    private static final String STYLISH_FORMAT = "stylish";
    private static final String PLAIN_FORMAT = "plain";
    private static final String JSON_FORMAT = "json";
    private static final String DEFAULT_CORRECT_STRING_PATH =
            "./src/test/resources/fixtures.Correct-strings/default.txt";

    private static final String DEFAULT_CORRECT_STRING = """
            {
                chars1: [a, b, c]
              - chars2: [d, e, f]
              + chars2: false
              - checked: false
              + checked: true
              - default: null
              + default: [value1, value2]
              - id: 45
              + id: null
              - key1: value1
              + key2: value2
                numbers1: [1, 2, 3, 4]
              - numbers2: [2, 3, 4, 5]
              + numbers2: [22, 33, 44, 55]
              - numbers3: [3, 4, 5]
              + numbers4: [4, 5, 6]
              + obj1: {nestedKey=value, isNested=true}
              - setting1: Some value
              + setting1: Another value
              - setting2: 200
              + setting2: 300
              - setting3: true
              + setting3: none
            }""";

    private static final String PLAIN_CORRECT_STRING =
            "Property 'chars2' was updated. From [complex value] to false\n"
                    + "Property 'checked' was updated. From false to true\n"
                    + "Property 'default' was updated. From null to [complex value]\n"
                    + "Property 'id' was updated. From 45 to null\n"
                    + "Property 'key1' was removed\n"
                    + "Property 'key2' was added with value: 'value2'\n"
                    + "Property 'numbers2' was updated. From [complex value] to [complex value]\n"
                    + "Property 'numbers3' was removed\n"
                    + "Property 'numbers4' was added with value: [complex value]\n"
                    + "Property 'obj1' was added with value: [complex value]\n"
                    + "Property 'setting1' was updated. From 'Some value' to 'Another value'\n"
                    + "Property 'setting2' was updated. From 200 to 300\n"
                    + "Property 'setting3' was updated. From true to 'none'";
    private static final String JSON_CORRECT_STRING = "{\"chars1\":\"unchanged\","
            + "\"chars2\":\"updated\","
            + "\"checked\":\"updated\","
            + "\"default\":\"updated\","
            + "\"id\":\"updated\","
            + "\"key1\":\"deleted\","
            + "\"key2\":\"added\","
            + "\"numbers1\":\"unchanged\","
            + "\"numbers2\":\"updated\","
            + "\"numbers3\":\"deleted\","
            + "\"numbers4\":\"added\","
            + "\"obj1\":\"added\","
            + "\"setting1\":\"updated\","
            + "\"setting2\":\"updated\","
            + "\"setting3\":\"updated\"}";
    private static final String EMPTY_PATH_CORRECT_STRING = "{\n"
            + "  + chars1: [a, b, c]\n"
            + "  + chars2: [d, e, f]\n"
            + "  + checked: false\n"
            + "  + default: null\n"
            + "  + id: 45\n"
            + "  + key1: value1\n"
            + "  + numbers1: [1, 2, 3, 4]\n"
            + "  + numbers2: [2, 3, 4, 5]\n"
            + "  + numbers3: [3, 4, 5]\n"
            + "  + setting1: Some value\n"
            + "  + setting2: 200\n"
            + "  + setting3: true\n"
            + "}";
    private static final String DIFFERENT_EXTENSIONS_FIRST_CORRECT_STRING = "{\n"
            + "    chars1: [a, b, c]\n"
            + "    chars2: [d, e, f]\n"
            + "    checked: false\n"
            + "    default: null\n"
            + "    id: 45\n"
            + "    key1: value1\n"
            + "    numbers1: [1, 2, 3, 4]\n"
            + "    numbers2: [2, 3, 4, 5]\n"
            + "    numbers3: [3, 4, 5]\n"
            + "    setting1: Some value\n"
            + "    setting2: 200\n"
            + "    setting3: true\n"
            + "}";
    private static final String DIFFERENT_EXTENSIONS_SECOND_CORRECT_STRING = "{\n"
            + "    chars1: [a, b, c]\n"
            + "    chars2: false\n"
            + "    checked: true\n"
            + "    default: [value1, value2]\n"
            + "    id: null\n"
            + "    key2: value2\n"
            + "    numbers1: [1, 2, 3, 4]\n"
            + "    numbers2: [22, 33, 44, 55]\n"
            + "    numbers4: [4, 5, 6]\n"
            + "    obj1: {nestedKey=value, isNested=true}\n"
            + "    setting1: Another value\n"
            + "    setting2: 300\n"
            + "    setting3: none\n"
            + "}";

    private static String getCorrectResultString(String correctStringPath) throws IOException {
        Path correctStringAbsolutePath = Paths.get(correctStringPath).toAbsolutePath().normalize();
        return Files.readString(correctStringAbsolutePath);
    }

    @Test
    public void testJSONDefault() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2);
        //String correctResult = getCorrectResultString(DEFAULT_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(DEFAULT_CORRECT_STRING);
        //assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testYAMLDefault() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2);
        assertThat(result).isEqualTo(DEFAULT_CORRECT_STRING);
    }

    @Test
    public void testJSONFormatStylish() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(DEFAULT_CORRECT_STRING);
    }

    @Test
    public void testYAMLFormatStylish() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(DEFAULT_CORRECT_STRING);
    }

    @Test
    public void testJSONFormatPlain() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, PLAIN_FORMAT);
        assertThat(result).isEqualTo(PLAIN_CORRECT_STRING);
    }

    @Test
    public void testYAMLFormatPlain() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, PLAIN_FORMAT);
        assertThat(result).isEqualTo(PLAIN_CORRECT_STRING);
    }

    @Test
    public void testJSONFormatJSON() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, JSON_FORMAT);
        assertThat(result).isEqualTo(JSON_CORRECT_STRING);
    }

    @Test
    public void testYAMLFormatJSON() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, JSON_FORMAT);
        assertThat(result).isEqualTo(JSON_CORRECT_STRING);
    }

    @Test
    public void testEmptyJSON() throws Exception {
        String result = Differ.generate(EMPTY_JSON_PATH, FIRST_JSON_PATH_1);
        assertThat(result).isEqualTo(EMPTY_PATH_CORRECT_STRING);
    }

    @Test
    public void testEmptyYAML() throws Exception {
        String result = Differ.generate(EMPTY_YAML_PATH, FIRST_YAML_PATH_1);
        assertThat(result).isEqualTo(EMPTY_PATH_CORRECT_STRING);
    }

    @Test
    public void testBothEmptyJSON() {
        assertThatThrownBy(() -> Differ.generate(EMPTY_JSON_PATH, EMPTY_JSON_PATH))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Parser.BOTH_FILES_EMPTY);
    }

    @Test
    public void testBothEmptyYAML() {
        assertThatThrownBy(() -> Differ.generate(EMPTY_YAML_PATH, EMPTY_YAML_PATH))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Parser.BOTH_FILES_EMPTY);
    }

    @Test
    public void testNoFileJSON() {
        assertThatThrownBy(() -> Differ.generate(FILE_DOESNT_EXIST_JSON, FIRST_JSON_PATH_1))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(FILE_DOESNT_EXIST_JSON).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void testNoFileYAML() {
        assertThatThrownBy(() -> Differ.generate(FILE_DOESNT_EXIST_YAML, FIRST_YAML_PATH_1))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(FILE_DOESNT_EXIST_YAML).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void testUnknownFormatFirstPath() {
        assertThatThrownBy(() -> Differ.generate(UNKNOWN_FORMAT_PATH, FIRST_JSON_PATH_1))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Parser.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void testUnknownFormatSecondPath() {
        assertThatThrownBy(() -> Differ.generate(EMPTY_YAML_PATH, UNKNOWN_FORMAT_PATH))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Parser.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void testUnknownFormatBothPaths() {
        assertThatThrownBy(() -> Differ.generate(UNKNOWN_FORMAT_PATH, UNKNOWN_FORMAT_PATH))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Parser.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void testDifferentFormatYAMLJSON() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_JSON_PATH_1);
        assertThat(result).isEqualTo(DIFFERENT_EXTENSIONS_FIRST_CORRECT_STRING);
    }

    @Test
    public void testDifferentFormatJSONYAML() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_2, FIRST_YAML_PATH_2);
        assertThat(result).isEqualTo(DIFFERENT_EXTENSIONS_SECOND_CORRECT_STRING);
    }
}
