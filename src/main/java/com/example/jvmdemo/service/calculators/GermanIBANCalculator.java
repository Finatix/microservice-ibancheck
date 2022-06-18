package com.example.jvmdemo.service.calculators;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;

import java.math.BigInteger;
import java.util.Random;

public class GermanIBANCalculator implements IBANCalculator {
    public static final String COUNTRY_CODE = "DE";

    public IBANCheckResultDto checkIban(IBANDto ibanDto) {
        if (null == ibanDto.getIban()
                || !ibanDto.getIban().startsWith(COUNTRY_CODE)
                || ibanDto.getIban().length() != 22) {
            throw new IBANWrongException("IBAN entered is not valid: " + ibanDto.getIban());
        }

        return generateNewCheckResult(ibanDto);
    }

    /**
     * IBAN has not to be a valid one, so we don't need to calculate it correctly
     * @return
     */
    public IBANDto generateRandomIban() {
        Random rnd = new Random();
        int accountNumber = rnd.nextInt(999999);
        int bankCodeNumber = rnd.nextInt(99999999);
        int checkCode = calculateCheckDigitFromBban(String.valueOf(bankCodeNumber + accountNumber));

        return new IBANDto(COUNTRY_CODE + checkCode + bankCodeNumber + accountNumber);
    }

    private IBANCheckResultDto generateNewCheckResult(final IBANDto iban) {
        return new IBANCheckResultDto(
                iban.getIban(),
                isCheckDigitCorrect(iban.getIban().substring(4), iban.getIban().substring(2, 4)),
                extractBankCodeFromIBAN(iban.getIban()),
                extractAccountNumberFromIBAN(iban.getIban()));
    }

    private int calculateCheckDigitFromBban(final String bban) {
        String bbanForCalculation = bban + "131400"; // 131400 - CountryCodeNumeric Germany

        BigInteger bbanDigit = new BigInteger(bbanForCalculation);
        BigInteger modulo = bbanDigit.mod(BigInteger.valueOf(97l));
        return  98 - modulo.intValue();
    }

    private boolean isCheckDigitCorrect(final String bban, final String checkDigitParam) {
        return Integer.valueOf(checkDigitParam) == calculateCheckDigitFromBban(bban);
    }

    private String extractBankCodeFromIBAN(final String iban) {
        return iban.substring(4,12);
    }

    private String extractAccountNumberFromIBAN(final String iban) {
        return iban.substring(13);
    }
}
