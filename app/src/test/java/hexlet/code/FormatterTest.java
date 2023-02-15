package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class FormatterTest {
    private static final String WRONG_FORMAT_INPUT = "wrong-format";
    @Test
    public void testWrongFormat() {
        assertThatThrownBy(() -> Differ.generate(DifferTest.FIRST_JSON_PATH_1,
                DifferTest.FIRST_JSON_PATH_2, WRONG_FORMAT_INPUT))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(Formatter.WRONG_FORMAT_WARNING);
    }
}
