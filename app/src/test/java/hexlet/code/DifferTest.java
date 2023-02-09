package hexlet.code;

import org.junit.jupiter.api.Test;

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
                .isInstanceOf(Exception.class)
                .hasMessageContaining("'" + Paths.get(firstPath).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void differTestFirstYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures/YAML-files/differTestFirstPath1.yml";
        String secondPath = "./src/test/resources/fixtures/YAML-files/differTestFirstPath2.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstCorrectString);
    }

    @Test
    public void differTestSecondYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures/YAML-files/differTestSecondPath1.yml";
        String secondPath = "./src/test/resources/fixtures/YAML-files/differTestSecondPath2.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondCorrectString);
    }

    @Test
    public void differTestFirstEmptyPathYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures/YAML-files/emptyFile.yml";
        String secondPath = "./src/test/resources/fixtures/YAML-files/differTestFirstPath1.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstEmptyPathCorrectString);
    }

    @Test
    public void differTestSecondEmptyPathYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures/YAML-files/differTestSecondPath1.yml";
        String secondPath = "./src/test/resources/fixtures/YAML-files/emptyFile.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondEmptyPathCorrectString);
    }

    @Test
    public void differTestBothEmptyPathsYAML() throws Exception {
        String firstPath = "./src/test/resources/fixtures/YAML-files/emptyFile.yml";
        String secondPath = "./src/test/resources/fixtures/YAML-files/emptyFile.yml";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestBothEmptyPathsCorrectString);
    }

    @Test
    public void differTestNoFileYAML() {
        String firstPath = "./src/test/resources/fixtures/YAML-files/this file is fantasy.yml";
        String secondPath = "./src/test/resources/fixtures/YAML-files/differTestFirstPath1.yml";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("'" + Paths.get(firstPath).toAbsolutePath().normalize()
                        + "' does not exist.\nCheck it!");
    }

    @Test
    public void differTestUnknownFormatFirstPath() {
        String firstPath = "./src/test/resources/fixtures/OTH-files/differTestUncknownFormatFirstPath.oth";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("'" + Paths.get(firstPath).toAbsolutePath().normalize()
                        + "' has unknown filename extension.\nCheck it!");
    }

    @Test
    public void differTestUnknownFormatSecondPath() {
        String firstPath = "./src/test/resources/fixtures/YAML-files/emptyFile.yml";
        String secondPath = "./src/test/resources/fixtures/OTH-files/differTestUncknownFormatFirstPath.oth";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("'" + Paths.get(secondPath).toAbsolutePath().normalize()
                        + "' has unknown filename extension.\nCheck it!");
    }

    @Test
    public void differTestUnknownFormatBothPaths() {
        String firstPath = "./src/test/resources/fixtures/OTH-files/differTestUncknownFormatFirstPath.oth";
        String secondPath = "./src/test/resources/fixtures/OTH-files/differTestUncknownFormatFirstPath.oth";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("'" + Paths.get(firstPath).toAbsolutePath().normalize()
                        + "' has unknown filename extension.\nCheck it!");
    }
    @Test
    public void differTestDifferentFormatFirst() {
        String firstPath = "./src/test/resources/fixtures/YAML-files/differTestFirstPath1.yml";
        String secondPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Files has different filename extensions."
                        + "\nEnter paths only with same filename extensions");
    }

    @Test
    public void differTestDifferentFormatSecond() {
        String firstPath = "./src/test/resources/fixtures/JSON-files/differTestFirstPath2.json";
        String secondPath = "./src/test/resources/fixtures/YAML-files/differTestFirstPath2.yml";
        assertThatThrownBy(() -> Differ.generate(firstPath, secondPath))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Files has different filename extensions."
                        + "\nEnter paths only with same filename extensions");
    }
}
