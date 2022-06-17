package com.example.jvmdemo.service;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class IBANCheckService {

    // check only german iban by now
    public IBANCheckResultDto isIBANValid(final String iban) {
        if (null == iban) {
            throw new IBANWrongException("NO IBAN given");
        }

        if (!iban.startsWith("DE") || iban.length() != 22) {
            throw new IBANWrongException("IBAN entered is not valid: " + iban);
        }

        return generateNewCheckResult(new IBANDto(iban));
    }

    public List<IBANCheckResultDto> isIBANValidFromList(List<IBANDto> ibanListToCheck) {
        List<IBANCheckResultDto> result = new ArrayList<>();
        ibanListToCheck.forEach(iban -> result.add(generateNewCheckResult(iban)));
        return result;
    }

    public List<IBANDto> generateTestData(int numberOfIbans) {
        List<IBANDto> result = new ArrayList<>();

        for(int i = 0; i < numberOfIbans; i++) {
            result.add(generateRandomIban());
        }

        return result;
    }

    private IBANCheckResultDto generateNewCheckResult(final IBANDto iban) {
        return new IBANCheckResultDto(
                iban.getIban(),
                isCheckDigitCorrect(iban.getIban().substring(4), iban.getIban().substring(2, 4)),
                extractBankCodeFromIBAN(iban.getIban()),
                extractAccountNumberFromIBAN(iban.getIban()));
    }

    /**
     * IBAN has not to be a valid one, so we don't need to calculate it correctly
     * @return
     */
    private IBANDto generateRandomIban() {
        Random rnd = new Random();
        int accountNumber = rnd.nextInt(999999);
        int bankCodeNumber = rnd.nextInt(99999999);
        int checkCode = calculateCheckDigitFromBban(String.valueOf(bankCodeNumber + accountNumber));
        String countryCode = "DE";

        return new IBANDto(countryCode + checkCode + bankCodeNumber + accountNumber);
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
        if (null == iban || iban.length() < 12) {
            return "";
        }

        return iban.substring(4,12);
    }

    private String extractAccountNumberFromIBAN(final String iban) {
        if (null == iban || iban.length() < 13) {
            return "";
        }

        return iban.substring(13);
    }
}
