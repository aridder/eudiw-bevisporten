package net.eidas2sandkasse.tsl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Token Status List Validator")
public class TokenStatusListValidatorTest {

    public TokenStatusListValidatorTest() {
    }

    @Test
    @DisplayName("When validate is called with any input, then true is expected")
    void validateIsAlwaysTrueNoMatterTheInput() {
        TokenStatusListValidator validator = new TokenStatusListValidator();

        assertAll(
                () -> assertTrue(validator.validate(null, null)),
                () -> assertTrue(validator.validate("https://example.test/status/list", 123L))
        );
    }
}
