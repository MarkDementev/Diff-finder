package hexlet.code;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DifferTest {
    private String differTestFirstCorrectString = "\n{\n"
            + " - follow: false\n"
            + "   host: hexlet.io\n"
            + " - proxy: 123.234.53.22\n"
            + " - timeout: 50\n"
            + " + timeout: 20\n"
            + " + verbose: true\n"
            + "}";

    private String differTestSecondCorrectString = "\n{\n"
            + "   A: A\n"
            + "   B: B\n"
            + " - C: true\n"
            + " - D: 1\n"
            + " + D: C\n"
            + " + E: true\n"
            + "}";

    private String differTestFirstEmptyPathCorrectString = "\n{\n"
            + " + follow: false\n"
            + " + host: hexlet.io\n"
            + " + proxy: 123.234.53.22\n"
            + " + timeout: 50\n"
            + "}";

    private String differTestSecondEmptyPathCorrectString = "\n{\n"
            + " - A: A\n"
            + " - B: B\n"
            + " - C: true\n"
            + " - D: 1\n"
            + "}";

    private String differTestBothEmptyPathsCorrectString = "\n{\n}";

    private String differTestFirstInnerCorrectString = "\n{\n"
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

    private String differTestSecondInnerCorrectString = "\n{\n"
            + " - A: {A=value, B=true}\n"
            + " - B: [1, 2]\n"
            + " + B: [2, 2]\n"
            + "   C: {A=123, B=false}\n"
            + "   D: [value1, value2]\n"
            + " + E: [true, false]\n"
            + "}";

    @Test
    public void differTestFirstJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstCorrectString);
    }

    @Test
    public void differTestSecondJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestSecondPath1.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestSecondPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondCorrectString);
    }

    @Test
    public void differTestFirstEmptyPathJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/emptyFile.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstEmptyPathCorrectString);
    }

    @Test
    public void differTestSecondEmptyPathJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestSecondPath1.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/emptyFile.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondEmptyPathCorrectString);
    }

    @Test
    public void differTestBothEmptyPathsJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/emptyFile.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/emptyFile.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestBothEmptyPathsCorrectString);
    }

    @Test
    public void differTestNoFileJSON() {
        String firstPath = "./src/test/resources/fixtures/JSON-files/this file is fantasy.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(firstPath).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void differTestFirstYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures.YAML-files/differTestFirstPath1.yml";
        String secondPath = "./src/test/resources/fixtures.YAML-files/differTestFirstPath2.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstCorrectString);
    }

    @Test
    public void differTestSecondYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures.YAML-files/differTestSecondPath1.yaml";
        String secondPath = "./src/test/resources/fixtures.YAML-files/differTestSecondPath2.yaml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondCorrectString);
    }

    @Test
    public void differTestFirstEmptyPathYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
        String secondPath = "./src/test/resources/fixtures.YAML-files/differTestFirstPath1.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstEmptyPathCorrectString);
    }

    @Test
    public void differTestSecondEmptyPathYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures.YAML-files/differTestSecondPath1.yaml";
        String secondPath = "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondEmptyPathCorrectString);
    }

    @Test
    public void differTestBothEmptyPathsYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
        String secondPath = "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestBothEmptyPathsCorrectString);
    }

    @Test
    public void differTestNoFileYAML() {
        String firstPath = "./src/test/resources/fixtures.YAML-files/this file is fantasy.yml";
        String secondPath = "./src/test/resources/fixtures.YAML-files/differTestFirstPath1.yml";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("'" + Paths.get(firstPath).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void differTestUnknownFormatFirstPath() {
        String firstPath = "./src/test/resources/fixtures.OTH-files/differTestUnknownFormatFirstPath.oth";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void differTestUnknownFormatSecondPath() {
        String firstPath = "./src/test/resources/fixtures.YAML-files/emptyFile.yml";
        String secondPath = "./src/test/resources/fixtures.OTH-files/differTestUnknownFormatFirstPath.oth";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.UNKNOWN_EXTENSION_ERROR);
    }

    @Test
    public void differTestUnknownFormatBothPaths() {
        String firstPath = "./src/test/resources/fixtures.OTH-files/differTestUnknownFormatFirstPath.oth";
        String secondPath = "./src/test/resources/fixtures.OTH-files/differTestUnknownFormatFirstPath.oth";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.UNKNOWN_EXTENSION_ERROR);
    }
    @Test
    public void differTestDifferentFormatFirst() {
        String firstPath = "./src/test/resources/fixtures.YAML-files/differTestFirstPath1.yml";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.DIFFERENT_EXTENSIONS_ERROR);
    }

    @Test
    public void differTestDifferentFormatSecond() {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath2.json";
        String secondPath = "./src/test/resources/fixtures.YAML-files/differTestFirstPath2.yml";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Differ.DIFFERENT_EXTENSIONS_ERROR);
    }

    @Test
    public void differTestFirstInnerJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestFirstInnerJSONPath1.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstInnerJSONPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstInnerCorrectString);
    }

    @Test
    public void differTestSecondInnerJSON() throws Exception {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath1.json";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondInnerCorrectString);
    }
//
//    @Test
//    public void differTestFirstInnerYAML() throws Exception {
//        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestFirstInnerYAMLPath1.json";
//        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstInnerYAMLPath2.json";
//        String result = Differ.generate(firstPath, secondPath);
//        assertThat(result).isEqualTo(differTestFirstInnerCorrectString);
//    }
//
//    @Test
//    public void differTestSecondInnerYAML() throws Exception {
//        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestSecondInnerYAMLPath1.json";
//        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestSecondInnerYAMLPath2.json";
//        String result = Differ.generate(firstPath, secondPath);
//        assertThat(result).isEqualTo(differTestSecondInnerCorrectString);
//    }
}
