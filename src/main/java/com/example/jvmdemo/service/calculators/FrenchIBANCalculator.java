package com.example.jvmdemo.service.calculators;

import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;

public class FrenchIBANCalculator implements IBANCalculator {
    public static final String COUNTRY_CODE = "FR";

    public IBANCheckResultDto checkIban(IBANDto ibanDto) {
        return null;
    }

    public IBANDto generateRandomIban() {
        return null;
    }
}
