package com.example.jvmdemo.presentation.controller.v1;

import com.example.jvmdemo.presentation.dto.IBANCheckResultDto;
import com.example.jvmdemo.presentation.dto.IBANDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckIBANControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Test checkIBAN REST-Endpoint with a valid IBAN")
    public void testCheckIbanWithValidIban() {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/v1/iban/checkIban")
                                .queryParam("ibanToCheck", "DE59800530000123456789")
                                .build()
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(IBANCheckResultDto.class).value(matcher -> assertTrue(matcher.isValid()));
    }

    @Test
    @DisplayName("Test checkIBAN REST-Endpoint with a valid IBAN")
    public void testCheckIbanWithValidIbanSecondTest() {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/v1/iban/checkIban")
                                .queryParam("ibanToCheck", "DE76860555921010001350")
                                .build()
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(IBANCheckResultDto.class).value(matcher -> assertTrue(matcher.isValid()));
    }

    @Test
    @DisplayName("Test checkIBAN REST-Endpoint with an invalid IBAN")
    public void testCheckIbanWithInvalidIban() {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/v1/iban/checkIban")
                                .queryParam("ibanToCheck", "DE60800530000123456789")
                                .build()
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(IBANCheckResultDto.class).value(matcher -> assertFalse(matcher.isValid()));
    }

    @Test
    @DisplayName("Test checkIBAN REST-Endpoint with an invalid IBAN")
    public void testCheckIbanWithInvalidIbanWrongFormat() {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/v1/iban/checkIban")
                                .queryParam("ibanToCheck", "A8391")
                                .build()
                )
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("Test checkIBANList REST-Endpoint with a short list of IBANs")
    public void testWithIBANList() throws IOException {
        // get the example json Data as ObjectList for the request body
        List<IBANDto> testIBANList = Arrays.asList(
                objectMapper.readValue(
                        Paths.get("src/test/resources/ibanCheckTestList.json").toFile(),
                        IBANDto[].class));

        webTestClient.post()
                .uri("/v1/iban/checkIbanList")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(testIBANList))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("Generate 10 Random IBANs")
    public void testGenerateRadomIBANs() {
        webTestClient.get()
                .uri("/v1/iban/generateTestData?numberOfIbans=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}