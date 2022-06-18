package com.example.jvmdemo.service.calculators;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import com.example.jvmdemo.presentation.dto.IBANDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GermanIBANCalculatorTest {
    private final GermanIBANCalculator sut = new GermanIBANCalculator();

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
        IBANDto dto = new IBANDto("DE1214444444444444444");
        assertThrows(IBANWrongException.class, () -> sut.checkIban(dto));
    }

    @Test
    @DisplayName("Test with IBAN which is to long")
    void testIbanWithLengthToLong() {
        IBANDto dto = new IBANDto("DE121444444444444444445");
        assertThrows(IBANWrongException.class, () -> sut.checkIban(dto));
    }
}