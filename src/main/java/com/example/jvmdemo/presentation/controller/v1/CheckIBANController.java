package com.example.jvmdemo.presentation.controller.v1;

import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;
import com.example.jvmdemo.service.IBANCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CheckIBANController {
    private final IBANCheckService ibanCheckService;

    @GetMapping("/iban/checkIban")
    public ResponseEntity<IBANCheckResultDto> checkIban(@RequestParam String ibanToCheck) {
        return ResponseEntity.ok().body(ibanCheckService.isIBANValid(ibanToCheck));
    }

    @PostMapping("/iban/checkIbanList")
    public ResponseEntity<List<IBANCheckResultDto>> checkIbanList(@RequestBody List<IBANDto> ibanListToCheck) {
        return ResponseEntity.ok().body(ibanCheckService.isIBANValidFromList(ibanListToCheck));
    }

    @GetMapping("/iban/generateTestData")
    public ResponseEntity<List<IBANDto>> generateTestData(@RequestParam int numberOfIbans) {
        return ResponseEntity.ok().body(ibanCheckService.generateTestData(numberOfIbans));
    }
}
