package com.bar.domain.shared;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.bar.domain.exception.IllegalDecimalException;
import com.bar.domain.exception.NegativePriceException;

@ActiveProfiles("test")
public class PriceTests  {

    @Test()
    @DisplayName("""
        When I try to make a negative price
        Then it throws a NegativePriceException
    """)
    void negativeValue() throws Exception {
        assertThrows(NegativePriceException.class, () -> {
            new Price(-1d);
        });
    }

    @Test()
    @DisplayName("""
        When I try to make an Price with more than two decimals
        Then it throws an error with the correct message
    """)
    void moreThanTwoDecimals() throws Exception {
        assertThrows(IllegalDecimalException.class, () -> {
            new Price(3.154d);
        });
    } 
}
