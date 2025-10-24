import com.tolgagureli.turkishunicodeconverter.UnicodeConverterToolWindowFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UnicodeConverterToolWindowFactory
 */
@DisplayName("Turkish Unicode Converter Tests")
class UnicodeConverterToolWindowFactoryTest {

    private UnicodeConverterToolWindowFactory factory;

    @BeforeEach
    void setUp() {
        factory = new UnicodeConverterToolWindowFactory();
    }

    @Test
    @DisplayName("Convert single Turkish character 'ç' to Unicode")
    void testConvertToUnicode_LowercaseCedilla() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00E7", method.invoke(factory, "ç"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'Ç' to Unicode")
    void testConvertToUnicode_UppercaseCedilla() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00C7", method.invoke(factory, "Ç"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'ğ' to Unicode")
    void testConvertToUnicode_LowercaseBreve() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u011F", method.invoke(factory, "ğ"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'Ğ' to Unicode")
    void testConvertToUnicode_UppercaseBreve() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u011E", method.invoke(factory, "Ğ"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'ö' to Unicode")
    void testConvertToUnicode_LowercaseODiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00F6", method.invoke(factory, "ö"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'Ö' to Unicode")
    void testConvertToUnicode_UppercaseODiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00D6", method.invoke(factory, "Ö"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'ş' to Unicode")
    void testConvertToUnicode_LowercaseSCedilla() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u015F", method.invoke(factory, "ş"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'Ş' to Unicode")
    void testConvertToUnicode_UppercaseSCedilla() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u015E", method.invoke(factory, "Ş"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'ü' to Unicode")
    void testConvertToUnicode_LowercaseUDiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00FC", method.invoke(factory, "ü"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'Ü' to Unicode")
    void testConvertToUnicode_UppercaseUDiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00DC", method.invoke(factory, "Ü"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'ı' to Unicode")
    void testConvertToUnicode_LowercaseDotlessI() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u0131", method.invoke(factory, "ı"));
    }

    @Test
    @DisplayName("Convert single Turkish character 'İ' to Unicode")
    void testConvertToUnicode_UppercaseDottedI() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u0130", method.invoke(factory, "İ"));
    }

    @Test
    @DisplayName("Convert multiple Turkish characters to Unicode")
    void testConvertToUnicode_MultipleCharacters() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        assertEquals("\\u00E7\\u00F6\\u015F", method.invoke(factory, "çöş"));
        assertEquals("\\u00C7\\u00D6\\u015E", method.invoke(factory, "ÇÖŞ"));
    }

    @Test
    @DisplayName("Convert mixed text (Turkish + English) to Unicode")
    void testConvertToUnicode_MixedText() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        String result = (String) method.invoke(factory, "Merhaba dünya!");
        assertEquals("Merhaba d\\u00FCnya!", result);
    }

    @Test
    @DisplayName("Convert text without Turkish characters - should remain unchanged")
    void testConvertToUnicode_NoTurkishCharacters() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        String result = (String) method.invoke(factory, "Hello World");
        assertEquals("Hello World", result);
    }

    @Test
    @DisplayName("Convert empty string to Unicode")
    void testConvertToUnicode_EmptyString() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        String result = (String) method.invoke(factory, "");
        assertEquals("", result);
    }

    @Test
    @DisplayName("Convert Turkish sentence to Unicode")
    void testConvertToUnicode_TurkishSentence() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        String sentence = "Şişli'de güzel bir gün.";
        String expected = "\\u015Ei\\u015Fli'de g\\u00FCzel bir g\\u00FCn.";
        assertEquals(expected, method.invoke(factory, sentence));
    }

    @Test
    @DisplayName("Convert Unicode '\\u00E7' to Turkish character")
    void testConvertToText_LowercaseCedilla() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("ç", method.invoke(factory, "\\u00E7"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u00C7' to Turkish character")
    void testConvertToText_UppercaseCedilla() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("Ç", method.invoke(factory, "\\u00C7"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u011F' to Turkish character")
    void testConvertToText_LowercaseBreve() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("ğ", method.invoke(factory, "\\u011F"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u011E' to Turkish character")
    void testConvertToText_UppercaseBreve() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("Ğ", method.invoke(factory, "\\u011E"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u00F6' to Turkish character")
    void testConvertToText_LowercaseODiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("ö", method.invoke(factory, "\\u00F6"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u00D6' to Turkish character")
    void testConvertToText_UppercaseODiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("Ö", method.invoke(factory, "\\u00D6"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u015F' to Turkish character")
    void testConvertToText_LowercaseSCedilla() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("ş", method.invoke(factory, "\\u015F"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u015E' to Turkish character")
    void testConvertToText_UppercaseSCedilla() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("Ş", method.invoke(factory, "\\u015E"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u00FC' to Turkish character")
    void testConvertToText_LowercaseUDiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("ü", method.invoke(factory, "\\u00FC"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u00DC' to Turkish character")
    void testConvertToText_UppercaseUDiaeresis() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("Ü", method.invoke(factory, "\\u00DC"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u0131' to Turkish character")
    void testConvertToText_LowercaseDotlessI() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("ı", method.invoke(factory, "\\u0131"));
    }

    @Test
    @DisplayName("Convert Unicode '\\u0130' to Turkish character")
    void testConvertToText_UppercaseDottedI() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("İ", method.invoke(factory, "\\u0130"));
    }

    @Test
    @DisplayName("Convert multiple Unicode sequences to Turkish")
    void testConvertToText_MultipleUnicode() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        assertEquals("çöş", method.invoke(factory, "\\u00E7\\u00F6\\u015F"));
        assertEquals("ÇÖŞ", method.invoke(factory, "\\u00C7\\u00D6\\u015E"));
    }

    @Test
    @DisplayName("Convert mixed text with Unicode sequences to Turkish")
    void testConvertToText_MixedText() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        String result = (String) method.invoke(factory, "Merhaba d\\u00FCnya!");
        assertEquals("Merhaba dünya!", result);
    }

    @Test
    @DisplayName("Convert text without Unicode sequences - should remain unchanged")
    void testConvertToText_NoUnicodeSequences() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        String result = (String) method.invoke(factory, "Hello World");
        assertEquals("Hello World", result);
    }

    @Test
    @DisplayName("Convert empty string from Unicode")
    void testConvertToText_EmptyString() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        String result = (String) method.invoke(factory, "");
        assertEquals("", result);
    }

    @Test
    @DisplayName("Convert Unicode sentence to Turkish")
    void testConvertToText_UnicodeSentence() throws Exception {
        Method method = getPrivateMethod("convertToText", String.class);
        String unicode = "\\u015Ei\\u015Fli'de g\\u00FCzel bir g\\u00FCn.";
        String expected = "Şişli'de güzel bir gün.";
        assertEquals(expected, method.invoke(factory, unicode));
    }

    @Test
    @DisplayName("Bidirectional conversion - Turkish → Unicode → Turkish")
    void testBidirectionalConversion() throws Exception {
        Method toUnicode = getPrivateMethod("convertToUnicode", String.class);
        Method toText = getPrivateMethod("convertToText", String.class);

        String original = "çğıöşü ÇĞIÖŞÜ";

        String unicode = (String) toUnicode.invoke(factory, original);
        String backToText = (String) toText.invoke(factory, unicode);

        assertEquals(original, backToText);
    }

    @Test
    @DisplayName("Test clipboard copy functionality")
    void testCopyToClipboard() throws Exception {
        Method method = getPrivateMethod("copyToClipboard", String.class);
        String testText = "Test String 123 çöşüğı";

        method.invoke(factory, testText);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String clipboardContent = (String) clipboard.getData(DataFlavor.stringFlavor);
        assertEquals(testText, clipboardContent);
    }

    @Test
    @DisplayName("Performance test with large text (1000 words)")
    void testPerformanceWithLargeText() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);

        StringBuilder largeText = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeText.append("çğıöşü ");
        }

        long startTime = System.nanoTime();
        method.invoke(factory, largeText.toString());
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000;
        assertTrue(durationMs < 1000, "Conversion took too long: " + durationMs + "ms");
    }

    @Test
    @DisplayName("Convert text with numbers and special characters")
    void testConvertWithNumbersAndSpecialChars() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        String input = "123 çöp 456!@# şüğ";
        String expected = "123 \\u00E7\\u00F6p 456!@# \\u015F\\u00FC\\u011F";
        assertEquals(expected, method.invoke(factory, input));
    }

    @Test
    @DisplayName("Factory instance creation")
    void testFactoryCreation() {
        assertNotNull(factory, "Factory should be created successfully");
    }

    @Test
    @DisplayName("Test all Turkish characters coverage")
    void testAllTurkishCharactersCoverage() throws Exception {
        Method method = getPrivateMethod("convertToUnicode", String.class);
        String allChars = "çÇğĞıİöÖşŞüÜ";
        String result = (String) method.invoke(factory, allChars);

        assertFalse(result.contains("ç") || result.contains("Ç"));
        assertFalse(result.contains("ğ") || result.contains("Ğ"));
        assertFalse(result.contains("ı") || result.contains("İ"));
        assertFalse(result.contains("ö") || result.contains("Ö"));
        assertFalse(result.contains("ş") || result.contains("Ş"));
        assertFalse(result.contains("ü") || result.contains("Ü"));
        assertTrue(result.contains("\\u"));
    }

    // Helper method to access private methods via reflection
    private Method getPrivateMethod(String methodName, Class<?>... parameterTypes) throws Exception {
        Method method = UnicodeConverterToolWindowFactory.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;
    }
}