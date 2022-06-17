package com.example.jvmdemo.service.calculators;

import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;

public interface IBANCalculator {

    IBANCheckResultDto checkIban(IBANDto ibanDto);

    IBANDto generateRandomIban();

}
