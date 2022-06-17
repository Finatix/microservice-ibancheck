package com.example.jvmdemo.service;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;
import com.example.jvmdemo.service.calculators.GermanIBANCalculator;
import com.example.jvmdemo.service.calculators.IBANCalculator;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class IBANCheckService {

    private Map<String, IBANCalculator> ibanCalculators = new HashMap<>();

    public IBANCheckService() {
        ibanCalculators.put(GermanIBANCalculator.COUNTRY_CODE, new GermanIBANCalculator());
    }

    // check only german iban by now
    public IBANCheckResultDto isIBANValid(final String iban) {
        if (null == iban || iban.length() < 4 || !iban.substring(0, 2).matches("[A-Z]{2}")) {
            throw new IBANWrongException("NO valid IBAN given");
        }

        return findIbanCalculatorByCountryCode(iban).checkIban(new IBANDto(iban));
    }

    public List<IBANCheckResultDto> isIBANValidFromList(List<IBANDto> ibanListToCheck) {
        List<IBANCheckResultDto> result = new ArrayList<>();

        ibanListToCheck.forEach(iban -> {
            IBANCalculator calculator = findIbanCalculatorByCountryCode(iban.getIban());
            result.add(calculator.checkIban(iban));
        });

        return result;
    }

    public List<IBANDto> generateTestData(int numberOfIbans) {
        List<IBANDto> result = new ArrayList<>();

        IBANCalculator calculator = findIbanCalculatorByCountryCode("DE");
        for(int i = 0; i < numberOfIbans; i++) {
            result.add(calculator.generateRandomIban());
        }

        return result;
    }

    private IBANCalculator findIbanCalculatorByCountryCode(final String ibanString) {
        String countryCode = ibanString.substring(0, 2);

        if (ibanCalculators.containsKey(countryCode)) {
            return ibanCalculators.get(countryCode);
        }

        throw new IBANWrongException("No IBAN calculator for the given country code found");
    }
}
