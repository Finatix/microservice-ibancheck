package com.example.jvmdemo.service.calculators;

import com.example.jvmdemo.errorhandling.exception.IBANWrongException;
import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;

import java.math.BigInteger;
import java.sql.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class FrenchIBANCalculator implements IBANCalculator {
    public static final String COUNTRY_CODE = "FR";
    public static final int LENGTH = 27;

    public IBANCheckResultDto checkIban(IBANDto ibanDto) {
        throwIfNull(ibanDto);
        throwIfToShort(ibanDto);
        throwIfToLong(ibanDto);
        throwIfWrongCountryCode(ibanDto);

        return new IBANCheckResultDto(
                ibanDto.getIban(),
                isValid(ibanDto),
                extractAccountNumber(ibanDto),
                extractBankCode(ibanDto)
        );
    }

    private String extractAccountNumber(IBANDto ibanDto){
        return ibanDto.getIban().substring(14,25);
    }

    private String extractBankCode(IBANDto ibanDto){
        return ibanDto.getIban().substring(4,9);
    }
    private boolean isValid(IBANDto ibanDto) {
        BigInteger originalChecksum = new BigInteger(ibanDto.getIban().substring(2,4));

        // ersten vier Ziffern ans Ende
        // letzten zwei ziffern zu 0 setzen
        String rearrangedIban = rearrangeIban(ibanDto);

        // Buchstaben in Zahlen umwandeln
        BigInteger onlyNumbers = new BigInteger(rearrangedIban.codePoints()
                .map(cp -> {return cp >= 65 && cp <=90 ? cp - 55 : cp - 48;})
                .mapToObj(number -> String.valueOf(number))
                .collect(Collectors.joining()));

        //Calculate Checksum
        BigInteger checkSum = BigInteger.valueOf(98).subtract((onlyNumbers.remainder(BigInteger.valueOf(97))));

        return checkSum.equals(originalChecksum);
    }

    private String rearrangeIban(IBANDto ibanDto) {
        String countryCode = ibanDto.getIban().substring(0,2);
        String rest = ibanDto.getIban().substring(4,27);
        return String.join("", rest, countryCode, "00");
    }

    private void throwIfToLong(IBANDto ibanDto){
        if(ibanDto.getIban().length() > LENGTH) throw new IBANWrongException("IBAN to long");
    }

    private void throwIfToShort(IBANDto ibanDto){
        if(ibanDto.getIban().length() < LENGTH) throw new IBANWrongException("IBAN to short");
    }

    private void throwIfNull(IBANDto ibanDto){
        if(ibanDto.getIban() == null) throw new IBANWrongException("Iban is null");
    }

    private void throwIfWrongCountryCode(IBANDto ibanDto) {
        if(!ibanDto.getIban().substring(0,2).equals(COUNTRY_CODE)) throw  new IBANWrongException("Iban Country Code wrong.");
    }

    public IBANDto generateRandomIban() {
        return null;
    }
}
