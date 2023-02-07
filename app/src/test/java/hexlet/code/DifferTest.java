package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {
    //дефолтное - работает OK
    //второй тест - тоже дефолтный придумай OK
    // первого паф нет
    // второго паф нет
    // обоих паф нет
    private String differTestNormalFirstCorrectString = "\n{\n"
            + " - follow: false\n"
            + "   host: hexlet.io\n"
            + " - proxy: 123.234.53.22\n"
            + " - timeout: 50\n"
            + " + timeout: 20\n"
            + " + verbose: true\n"
            + "}";

    private String differTestNormalSecondCorrectString = "\n{\n"
            + "   A: A\n"
            + "   B: B\n"
            + " - C: true\n"
            + " - D: 1\n"
            + " + D: C\n"
            + " + E: true\n"
            + "}";

    @Test
    public void differTestNormalFirst() throws Exception {
        String firstPath = "./src/test/resources/fixtures/differTestNormalFirstPath1.json";
        String secondPath = "./src/test/resources/fixtures/differTestNormalFirstPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestNormalFirstCorrectString);
    }

    @Test
    public void differTestNormalSecond() throws Exception {
        String firstPath = "./src/test/resources/fixtures/differTestNormalSecondPath1.json";
        String secondPath = "./src/test/resources/fixtures/differTestNormalSecondPath2.json";
        String result = Differ.generate(firstPath, secondPath);
        assertThat(result).isEqualTo(differTestNormalSecondCorrectString);
    }
}
