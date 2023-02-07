package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {
    //дефолтное - работает
    //второй тест - тоже дефолтный придумай
    // первого паф нет
    // второго паф нет
    // третьего паф нет
    private String differTestNormalFirstCorrectString = "{\n"
            + "  - follow: false\n"
            + "    host: hexlet.io\n"
            + "  - proxy: 123.234.53.22\n"
            + "  - timeout: 50\n"
            + "  + timeout: 20\n"
            + "  + verbose: true\n"
            + "}";

    private String differTestNormalSecondCorrectString = "{\n"
            + "    A: A\n"
            + "    B: B\n"
            + "  - C: true\n"
            + "  - D: 1\n"
            + "  + D: C\n"
            + "  + E: true\n"
            + "}";

    @Test
    public void differTestNormalFirst() {
        String firstFilePath = ;
        String secondFilePath = ;
        String result = Differ.generate();
        assertThat(result).isEqualTo(differTestNormalFirstCorrectString);
    }

    @Test
    public void differTestNormalSecond() {
        String result = Differ.generate();
        assertThat(result).isEqualTo(differTestNormalSecondCorrectString);
    }
}
