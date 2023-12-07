package pl.pbf.sandbox.priceconverter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;

import pl.pbf.sandbox.priceconverter.client.nbp.NbpClient;
import pl.pbf.sandbox.priceconverter.client.nbp.model.NbpResponse;
import pl.pbf.sandbox.priceconverter.client.nbp.model.RatesItem;
import pl.pbf.sandbox.priceconverter.exception.PriceConverterException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@MockitoSettings
public class PriceConverterServiceTest {

    @Mock
    private NbpClient nbpClient;

    @InjectMocks
    private PriceConverterService priceConverterService;

    @Test
    void should_throw_exception_when_missing_average_exchange_rate_on_nbp_response() {
        // given
        final var nbpResponse = NbpResponse.builder().build();
        when(nbpClient.getNbpCurrencyInfo(anyString())).thenReturn(nbpResponse);

        // when
        assertThatThrownBy(() -> priceConverterService.convertPriceToPln("USD", bd(100)))
        // then
                .isInstanceOf(PriceConverterException.class)
                .hasMessage("Missing average exchange rate on response for USD currency")
                .extracting("httpStatus").isEqualTo(HttpStatus.EXPECTATION_FAILED);
    }

    @ParameterizedTest
    @MethodSource("priceAndExpectedCalculatedPriceAndFormattedPrice")
    void should_correctly_calculate_pln_price(final BigDecimal price, final String calculatedPrice, final String formattedPrice) {
        // given
        final var nbpResponse = NbpResponse.builder()
                .rates(List.of(RatesItem.builder()
                        .mid(bd(3.98))
                        .build()))
                .build();
        when(nbpClient.getNbpCurrencyInfo(anyString())).thenReturn(nbpResponse);

        // when
        final var response = priceConverterService.convertPriceToPln("USD", price);

        // then
        final var softAssert = new SoftAssertions();
        softAssert.assertThat(response.getCalculatedPrice().toString()).isEqualTo(calculatedPrice);
        softAssert.assertThat(response.getFormattedPrice()).isEqualTo(formattedPrice);
        softAssert.assertAll();
    }

    public static Stream<Arguments> priceAndExpectedCalculatedPriceAndFormattedPrice() {
        return Stream.of(
                arguments(bd(10), "39.80", "39,80 zł"),
                arguments(bd(100), "398.00", "398,00 zł"),
                arguments(bd(123.45), "491.33", "491,33 zł"),
                arguments(bd(2346.19), "9337.84", "9 337,84 zł"),
                arguments(bd(15000), "59700.00", "59 700,00 zł"),
                arguments(bd(18999), "75616.02", "75 616,02 zł"),
                arguments(bd(88345), "351613.10", "351 613,10 zł"),
                arguments(bd(136999), "545256.02", "545 256,02 zł"),
                arguments(bd(1234567.89), "4913580.20", "4 913 580,20 zł")
        );
    }

    private static BigDecimal bd(final double val) {
        return BigDecimal.valueOf(val);
    }
}

