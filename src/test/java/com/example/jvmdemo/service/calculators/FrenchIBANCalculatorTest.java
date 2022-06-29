package com.example.jvmdemo.service.calculators;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrenchIBANCalculatorTest {
    private final FrenchIBANCalculator sut = new FrenchIBANCalculator();

    @Test
    @DisplayName("Test with IBAN value is null")
    void testWithIBANDtoAndIBANisNull() {
        IBANDto dto = new IBANDto(null);
        assertThrows(IBANWrongException.class, () -> sut.checkIban(dto));
    }

    @Test
    @DisplayName("Test with IBAN which has a different country code")
    void testIbanWithDifferentCountryCode() {
        IBANDto dto = new IBANDto("NL12144444444444444444");
        assertThrows(IBANWrongException.class, () -> sut.checkIban(dto));
    }

    @Test
    @DisplayName("Test with IBAN which is to short")
    void testIbanWithLengthToShort() {
        IBANDto dto = new IBANDto("FR1214444444444444444");
        assertThrows(IBANWrongException.class, () -> sut.checkIban(dto));
    }

    @Test
    @DisplayName("Test with IBAN which is to long")
    void testIbanWithLengthToLong() {
        IBANDto dto = new IBANDto("FR121444444444444444445234532");
        assertThrows(IBANWrongException.class, () -> sut.checkIban(dto));
    }

    @Test
    @DisplayName("Test with a valid IBAN Aplhanumeric")
    void testWithValidFrenchIBAN() {
        IBANDto dto = new IBANDto("FR1420041010050500013M02606");
        IBANCheckResultDto actualResult = sut.checkIban(dto);

        assertNotNull(actualResult);
        assertTrue(actualResult.isValid());
    }

}