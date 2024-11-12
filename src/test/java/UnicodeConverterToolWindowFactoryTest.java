import com.tolgagureli.turkishunicodeconverter.UnicodeConverterToolWindowFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UnicodeConverterToolWindowFactoryTest {

    private UnicodeConverterToolWindowFactory converter;

    @BeforeEach
    public void setUp() {
        // Test için sınıfı oluşturuyoruz
        converter = new UnicodeConverterToolWindowFactory();
    }

    @Test
    public void testConvertToUnicode() {
        String input = "çĞüÖ";
        String expected = "\\u00E7\\u011E\\u00FC\\u00D6";
        String result = converter.convertToUnicode(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testConvertToText() {
        String input = "\\u00E7\\u011E\\u00FC\\u00D6";
        String expected = "çĞüÖ";
        String result = converter.convertToText(input);

        assertThat(result).isEqualTo(expected);
    }
}