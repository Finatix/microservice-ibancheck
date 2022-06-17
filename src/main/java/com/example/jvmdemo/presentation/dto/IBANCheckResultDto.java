package com.example.jvmdemo.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IBANCheckResultDto {

    String iban;

    boolean isValid;

    String accountNumber;

    String bankCode;
}
