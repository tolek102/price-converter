package pl.pbf.sandbox.priceconverter;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.tomakehurst.wiremock.core.Options;

import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
class PriceConverterApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void should_get_current_offer() {
        // given

        // when
        final ResponseEntity<PriceConverterResponse> response = restTemplate.exchange(RequestEntity
                        .get(UriComponentsBuilder.fromPath("/convert/{currency}/{price}").build("USD", "1234567.89"))
                        .build(),
                PriceConverterResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPriceConverterResponse());
    }

    private PriceConverterResponse expectedPriceConverterResponse() {

        return PriceConverterResponse.builder()
                .formattedPrice("4 927 160,45 zł")
                .calculatedPrice(BigDecimal.valueOf(4927160.45))
                .build();
    }
}
