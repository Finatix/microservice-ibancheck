package com.example.jvmdemo.service;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBANCheckServiceTest {
    IBANCheckService sut = new IBANCheckService();

    @Test
    @DisplayName("Wrong IBAN should be checked as false")
    public void testIsIBANValidWithWrongIBAN() {
        assertFalse(sut.isIBANValid("098DEBBB"));
    }

    @Test
    @DisplayName("Correct IBAN should be checked as true")
    public void testIsIBANValidWithCorrectIBAN() {
        assertTrue(sut.isIBANValid("DE59800530000123456789"));
    }

    @Test
    @DisplayName("Check an IBAN which is valid in check digit and length")
    public void testAValidIBAN() {
        assertTrue(sut.isIBANValid("DE59800530000123456789"));
    }

    @Test
    @DisplayName("Check an IBAN with invalid check digit")
    public void testAnInvalidIBAN() {
        assertFalse(sut.isIBANValid("DE61800530000123456789"));
    }

    @Test
    @DisplayName("Check with null value should throw an exception")
    public void testIsIBANValidWithNullValue() {
        assertThrows(IBANWrongException.class, () -> {
            sut.isIBANValid(null);
        });
    }
}