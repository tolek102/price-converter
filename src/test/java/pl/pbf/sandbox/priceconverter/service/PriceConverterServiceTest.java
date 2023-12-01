package pl.pbf.sandbox.priceconverter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import pl.pbf.sandbox.priceconverter.client.nbp.NbpClient;
import pl.pbf.sandbox.priceconverter.client.nbp.model.NbpResponse;
import pl.pbf.sandbox.priceconverter.client.nbp.model.RatesItem;
import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@MockitoSettings
public class PriceConverterServiceTest {

    @Mock
    private NbpClient nbpClient;

    @InjectMocks
    private PriceConverterService priceConverterService;

    @ParameterizedTest
    @MethodSource("priceAndExpectedCalculatedPrice")
    void should_correctly_calculate_pln_price(final BigDecimal price, final String calculatedPrice) {
        // given
        final NbpResponse nbpResponse = NbpResponse.builder()
                .rates(List.of(RatesItem.builder()
                        .mid(bd(3.98))
                        .build()))
                .build();
        when(nbpClient.getAverageExchangeRate(anyString())).thenReturn(nbpResponse);

        // when
        final PriceConverterResponse response = priceConverterService.convertPriceToPln("USD", price);

        // then
        assertThat(response.getCalculatedPrice().toString()).isEqualTo(calculatedPrice);
    }

    @Test
    void should_correctly_prepare_formatted_price() {
        // given
        final NbpResponse nbpResponse = NbpResponse.builder()
                .rates(List.of(RatesItem.builder()
                        .mid(bd(3.98))
                        .build()))
                .build();
        when(nbpClient.getAverageExchangeRate(anyString())).thenReturn(nbpResponse);

        // when
        final PriceConverterResponse response = priceConverterService.convertPriceToPln("USD", bd(10.0));

        // then
        assertThat(response.getFormattedPrice()).isEqualTo("39,80 zł");

    }

    public static Stream<Arguments> priceAndExpectedCalculatedPrice() {
        return Stream.of(
                arguments(bd(10), "39.80"),
                arguments(bd(100), "398.00"),
                arguments(bd(123.45), "491.30"),
                arguments(bd(15000), "59700.00"),
                arguments(bd(18999), "75620.00"),
                arguments(bd(88345), "351600.00"),
                arguments(bd(136999), "545300.00")
        );
    }

    private static BigDecimal bd(final double val) {
        return BigDecimal.valueOf(val);
    }
}

