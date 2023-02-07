package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

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
    public void differTestFirst() throws Exception {
        String firstPath = "./src/test/resources/fixtures/differTestFirstPath1.json";
        String secondPath = "./src/test/resources/fixtures/differTestFirstPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstCorrectString);
    }

    @Test
    public void differTestSecond() throws Exception {
        String firstPath = "./src/test/resources/fixtures/differTestSecondPath1.json";
        String secondPath = "./src/test/resources/fixtures/differTestSecondPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondCorrectString);
    }

    @Test
    public void differTestFirstEmptyPath() throws Exception {
        String firstPath = "./src/test/resources/fixtures/emptyFile.json";
        String secondPath = "./src/test/resources/fixtures/differTestFirstPath1.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestFirstEmptyPathCorrectString);
    }

    @Test
    public void differTestSecondEmptyPath() throws Exception {
        String firstPath = "./src/test/resources/fixtures/differTestSecondPath1.json";
        String secondPath = "./src/test/resources/fixtures/emptyFile.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestSecondEmptyPathCorrectString);
    }

    @Test
    public void differTestBothEmptyPaths() throws Exception {
        String firstPath = "./src/test/resources/fixtures/emptyFile.json";
        String secondPath = "./src/test/resources/fixtures/emptyFile.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestBothEmptyPathsCorrectString);
    }

    @Test
    public void differTestNoFile() {
        String firstPath = "./src/test/resources/fixtures/this file is fantasy.json";
        String secondPath = "./src/test/resources/fixtures/differTestFirstPath1.json";
        assertThatException().isThrownBy(() -> Differ.generate(firstPath, secondPath));
    }
}
