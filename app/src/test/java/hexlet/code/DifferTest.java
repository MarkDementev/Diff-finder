package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DifferTest {
    public static final String FIRST_JSON_PATH_1 =
            "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
    public static final String FIRST_JSON_PATH_2 =
            "./src/test/resources/fixtures/JSON-files/differTestFirstPath2.json";
    private static final String STYLISH_FORMAT = "stylish";
    private static final String PLAIN_FORMAT = "plain";
    private static final String SECOND_JSON_PATH_1 =
            "./src/test/resources/fixtures/JSON-files/differTestSecondPath1.json";
    private static final String SECOND_JSON_PATH_2 =
            "./src/test/resources/fixtures/JSON-files/differTestSecondPath2.json";
    private static final String EMPTY_JSON_PATH =
            "./src/test/resources/fixtures/JSON-files/emptyFile.json";
    private static final String FILE_DOESNT_EXIST_JSON =
            "./src/test/resources/fixtures/JSON-files/this file is fantasy.json";
    private static final String FIRST_YAML_PATH_1 =
            "./src/test/resources/fixtures.YAML-files/differTestFirstPath1.yml";
    private static final String FIRST_YAML_PATH_2 =
            "./src/test/resources/fixtures.YAML-files/differTestFirstPath2.yml";
    private static final String SECOND_YAML_PATH_1 =
            "./src/test/resources/fixtures.YAML-files/differTestSecondPath1.yaml";
    private static final String SECOND_YAML_PATH_2 =
            "./src/test/resources/fixtures.YAML-files/differTestSecondPath2.yaml";
    private static final String EMPTY_YAML_PATH =
            "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
    private static final String FILE_DOESNT_EXIST_YAML =
            "./src/test/resources/fixtures.YAML-files/this file is fantasy.yml";
    private static final String UNKNOWN_FORMAT_PATH =
            "./src/test/resources/fixtures.OTH-files/differTestUnknownFormatFirstPath.oth";
    private static final String FIRST_INNER_JSON_PATH_1 =
            "./src/test/resources/fixtures/JSON-files/differTestFirstInnerJSONPath1.json";
    private static final String FIRST_INNER_JSON_PATH_2 =
            "./src/test/resources/fixtures/JSON-files/differTestFirstInnerJSONPath2.json";
    private static final String SECOND_INNER_JSON_PATH_1 =
            "./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath1.json";
    private static final String SECOND_INNER_JSON_PATH_2 =
            "./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath2.json";
    private static final String FIRST_INNER_YAML_PATH_1 =
            "./src/test/resources/fixtures.YAML-files/differTestFirstInnerYAMLPath1.yml";
    private static final String FIRST_INNER_YAML_PATH_2 =
            "./src/test/resources/fixtures.YAML-files/differTestFirstInnerYAMLPath2.yml";
    private static final String SECOND_INNER_YAML_PATH_1 =
            "./src/test/resources/fixtures.YAML-files/differTestSecondInnerYAMLPath1.yaml";
    private static final String SECOND_INNER_YAML_PATH_2 =
            "./src/test/resources/fixtures.YAML-files/differTestSecondInnerYAMLPath2.yaml";
    private static final String FIRST_CORRECT_STRING = "\n{\n"
            + " - follow: false\n"
            + "   host: hexlet.io\n"
            + " - proxy: 123.234.53.22\n"
            + " - timeout: 50\n"
            + " + timeout: 20\n"
            + " + verbose: true\n"
            + "}";
    private static final String SECOND_CORRECT_STRING = "\n{\n"
            + "   A: A\n"
            + "   B: B\n"
            + " - C: true\n"
            + " - D: 1\n"
            + " + D: C\n"
            + " + E: true\n"
            + "}";
    private static final String FIRST_EMPTY_PATH_CORRECT_STRING = "\n{\n"
            + " + follow: false\n"
            + " + host: hexlet.io\n"
            + " + proxy: 123.234.53.22\n"
            + " + timeout: 50\n"
            + "}";
    private static final String SECOND_EMPTY_PATH_CORRECT_STRING = "\n{\n"
            + " - A: A\n"
            + " - B: B\n"
            + " - C: true\n"
            + " - D: 1\n"
            + "}";
    private static final String BOTH_EMPTY_PATHS_CORRECT_STRING = "\n{\n}";
    private static final String FIRST_INNER_CORRECT_STRING = "\n{\n"
            + "   chars1: [a, b, c]\n"
            + " - chars2: [d, e, f]\n"
            + " + chars2: false\n"
            + " - checked: false\n"
            + " + checked: true\n"
            + " - default: null\n"
            + " + default: [value1, value2]\n"
            + " - id: 45\n"
            + " + id: null\n"
            + " - key1: value1\n"
            + " + key2: value2\n"
            + "   numbers1: [1, 2, 3, 4]\n"
            + " - numbers2: [2, 3, 4, 5]\n"
            + " + numbers2: [22, 33, 44, 55]\n"
            + " - numbers3: [3, 4, 5]\n"
            + " + numbers4: [4, 5, 6]\n"
            + " + obj1: {nestedKey=value, isNested=true}\n"
            + " - setting1: Some value\n"
            + " + setting1: Another value\n"
            + " - setting2: 200\n"
            + " + setting2: 300\n"
            + " - setting3: true\n"
            + " + setting3: none\n"
            + "}";
    private static final String SECOND_INNER_CORRECT_STRING = "\n{\n"
            + " - A: {A=value, B=true}\n"
            + " - B: [1, 2]\n"
            + " + B: [2, 2]\n"
            + "   C: {A=123, B=false}\n"
            + "   D: [value1, value2]\n"
            + " + E: [true, false]\n"
            + "}";
    private static final String PLAIN_CORRECT_STRING =
            "Property 'follow' was removed\n"
            + "Property 'proxy' was removed\n"
            + "Property 'timeout' was updated. From 50 to 20\n"
            + "Property 'verbose' was added with value: 'true'";
    private static final String INNER_PLAIN_CORRECT_STRING =
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

    @Test
    public void testFirstJSON() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(FIRST_CORRECT_STRING);
    }

    @Test
    public void testSecondJSON() throws Exception {
        String result = Differ.generate(SECOND_JSON_PATH_1, SECOND_JSON_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(SECOND_CORRECT_STRING);
    }

    @Test
    public void testEmptyPathJSON() throws Exception {
        String result = Differ.generate(EMPTY_JSON_PATH, FIRST_JSON_PATH_1, STYLISH_FORMAT);
        assertThat(result).isEqualTo(FIRST_EMPTY_PATH_CORRECT_STRING);
    }

    @Test
    public void testSecondEmptyPathJSON() throws Exception {
        String result = Differ.generate(SECOND_JSON_PATH_1, EMPTY_JSON_PATH, STYLISH_FORMAT);
        assertThat(result).isEqualTo(SECOND_EMPTY_PATH_CORRECT_STRING);
    }

    @Test
    public void testBothEmptyPathsJSON() throws Exception {
        String result = Differ.generate(EMPTY_JSON_PATH, EMPTY_JSON_PATH, STYLISH_FORMAT);
        assertThat(result).isEqualTo(BOTH_EMPTY_PATHS_CORRECT_STRING);
    }

    @Test
    public void testNoFileJSON() {
        assertThatThrownBy(() -> Differ.generate(FILE_DOESNT_EXIST_JSON, FIRST_JSON_PATH_1, STYLISH_FORMAT))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(FILE_DOESNT_EXIST_JSON).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void testFirstYAML() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(FIRST_CORRECT_STRING);
    }

    @Test
    public void testSecondYAML() throws Exception {
        String result = Differ.generate(SECOND_YAML_PATH_1, SECOND_YAML_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(SECOND_CORRECT_STRING);
    }

    @Test
    public void testFirstEmptyPathYAML() throws Exception {
        String result = Differ.generate(EMPTY_YAML_PATH, FIRST_YAML_PATH_1, STYLISH_FORMAT);
        assertThat(result).isEqualTo(FIRST_EMPTY_PATH_CORRECT_STRING);
    }

    @Test
    public void testSecondEmptyPathYAML() throws Exception {
        String result = Differ.generate(SECOND_YAML_PATH_1, EMPTY_YAML_PATH, STYLISH_FORMAT);
        assertThat(result).isEqualTo(SECOND_EMPTY_PATH_CORRECT_STRING);
    }

    @Test
    public void testBothEmptyPathsYAML() throws Exception {
        String result = Differ.generate(EMPTY_YAML_PATH, EMPTY_YAML_PATH, STYLISH_FORMAT);
        assertThat(result).isEqualTo(BOTH_EMPTY_PATHS_CORRECT_STRING);
    }

    @Test
    public void testNoFileYAML() {
        assertThatThrownBy(() -> Differ.generate(FILE_DOESNT_EXIST_YAML, FIRST_YAML_PATH_1, STYLISH_FORMAT))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(FILE_DOESNT_EXIST_YAML).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void testUnknownFormatFirstPath() {
        assertThatThrownBy(() -> Differ.generate(UNKNOWN_FORMAT_PATH, FIRST_JSON_PATH_1, STYLISH_FORMAT))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void testUnknownFormatSecondPath() {
        assertThatThrownBy(() -> Differ.generate(EMPTY_YAML_PATH, UNKNOWN_FORMAT_PATH, STYLISH_FORMAT))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void testUnknownFormatBothPaths() {
        assertThatThrownBy(() -> Differ.generate(UNKNOWN_FORMAT_PATH, UNKNOWN_FORMAT_PATH, STYLISH_FORMAT))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.UNKNOWN_EXTENSION_ERROR);
    }
    @Test
    public void testDifferentFormatFirst() {
        assertThatThrownBy(() -> Differ.generate(FIRST_YAML_PATH_1, FIRST_JSON_PATH_1, STYLISH_FORMAT))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.DIFFERENT_EXTENSIONS_ERROR);
    }

    @Test
    public void testDifferentFormatSecond() {
        assertThatThrownBy(() -> Differ.generate(FIRST_JSON_PATH_2, FIRST_YAML_PATH_2, STYLISH_FORMAT))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.DIFFERENT_EXTENSIONS_ERROR);
    }

    @Test
    public void testFirstInnerJSON() throws Exception {
        String result = Differ.generate(FIRST_INNER_JSON_PATH_1, FIRST_INNER_JSON_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(FIRST_INNER_CORRECT_STRING);
    }

    @Test
    public void testSecondInnerJSON() throws Exception {
        String result = Differ.generate(SECOND_INNER_JSON_PATH_1, SECOND_INNER_JSON_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(SECOND_INNER_CORRECT_STRING);
    }

    @Test
    public void testFirstInnerYAML() throws Exception {
        String result = Differ.generate(FIRST_INNER_YAML_PATH_1, FIRST_INNER_YAML_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(FIRST_INNER_CORRECT_STRING);
    }

    @Test
    public void testSecondInnerYAML() throws Exception {
        String result = Differ.generate(SECOND_INNER_YAML_PATH_1, SECOND_INNER_YAML_PATH_2, STYLISH_FORMAT);
        assertThat(result).isEqualTo(SECOND_INNER_CORRECT_STRING);
    }

    @Test
    public void testJSONPlain() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, PLAIN_FORMAT);
        assertThat(result).isEqualTo(PLAIN_CORRECT_STRING);
    }

    @Test
    public void testYAMLPlain() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, PLAIN_FORMAT);
        assertThat(result).isEqualTo(PLAIN_CORRECT_STRING);
    }

    @Test
    public void testJSONInnerPlain() throws Exception {
        String result = Differ.generate(FIRST_INNER_JSON_PATH_1, FIRST_INNER_JSON_PATH_2, PLAIN_FORMAT);
        assertThat(result).isEqualTo(INNER_PLAIN_CORRECT_STRING);
    }

    @Test
    public void testYAMLInnerPlain() throws Exception {
        String result = Differ.generate(FIRST_INNER_YAML_PATH_1, FIRST_INNER_YAML_PATH_2, PLAIN_FORMAT);
        assertThat(result).isEqualTo(INNER_PLAIN_CORRECT_STRING);
    }
}
