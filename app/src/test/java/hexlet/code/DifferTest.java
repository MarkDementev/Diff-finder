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
    private static final String PLAIN_CORRECT_STRING_PATH =
            "./src/test/resources/fixtures.Correct-strings/plain.txt";
    private static final String JSON_CORRECT_STRING_PATH =
            "./src/test/resources/fixtures.Correct-strings/json.txt";
    private static final String EMPTY_CORRECT_STRING_PATH =
            "./src/test/resources/fixtures.Correct-strings/empty_path.txt";
    private static final String DIFFERENT_EXTENSIONS_FIRST_CORRECT_STRING_PATH =
            "./src/test/resources/fixtures.Correct-strings/different_extensions_1.txt";
    private static final String DIFFERENT_EXTENSIONS_SECOND_CORRECT_STRING_PATH =
            "./src/test/resources/fixtures.Correct-strings/different_extensions_2.txt";

    private static String getCorrectResultString(String correctStringPath) throws IOException {
        Path correctStringAbsolutePath = Paths.get(correctStringPath).toAbsolutePath().normalize();
        return Files.readString(correctStringAbsolutePath).trim();
    }

    @Test
    public void testJSONDefault() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2);
        String correctResult = getCorrectResultString(DEFAULT_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testYAMLDefault() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2);
        String correctResult = getCorrectResultString(DEFAULT_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testJSONFormatStylish() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, STYLISH_FORMAT);
        String correctResult = getCorrectResultString(DEFAULT_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testYAMLFormatStylish() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, STYLISH_FORMAT);
        String correctResult = getCorrectResultString(DEFAULT_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testJSONFormatPlain() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, PLAIN_FORMAT);
        String correctResult = getCorrectResultString(PLAIN_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testYAMLFormatPlain() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, PLAIN_FORMAT);
        String correctResult = getCorrectResultString(PLAIN_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testJSONFormatJSON() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_1, FIRST_JSON_PATH_2, JSON_FORMAT);
        String correctResult = getCorrectResultString(JSON_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testYAMLFormatJSON() throws Exception {
        String result = Differ.generate(FIRST_YAML_PATH_1, FIRST_YAML_PATH_2, JSON_FORMAT);
        String correctResult = getCorrectResultString(JSON_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testEmptyJSON() throws Exception {
        String result = Differ.generate(EMPTY_JSON_PATH, FIRST_JSON_PATH_1);
        String correctResult = getCorrectResultString(EMPTY_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testEmptyYAML() throws Exception {
        String result = Differ.generate(EMPTY_YAML_PATH, FIRST_YAML_PATH_1);
        String correctResult = getCorrectResultString(EMPTY_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
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
        String correctResult = getCorrectResultString(DIFFERENT_EXTENSIONS_FIRST_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }

    @Test
    public void testDifferentFormatJSONYAML() throws Exception {
        String result = Differ.generate(FIRST_JSON_PATH_2, FIRST_YAML_PATH_2);
        String correctResult = getCorrectResultString(DIFFERENT_EXTENSIONS_SECOND_CORRECT_STRING_PATH);
        assertThat(result).isEqualTo(correctResult);
    }
}
