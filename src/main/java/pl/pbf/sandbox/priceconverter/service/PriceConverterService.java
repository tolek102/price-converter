package pl.pbf.sandbox.priceconverter.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.pbf.sandbox.priceconverter.client.nbp.NbpClient;
import pl.pbf.sandbox.priceconverter.client.nbp.model.NbpResponse;
import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;

@Service
@RequiredArgsConstructor
public class PriceConverterService {

    private final NbpClient nbpClient;

    public PriceConverterResponse convertPriceToPln(final String currency, final BigDecimal price) {
        final NbpResponse averageExchangeRate = nbpClient.getAverageExchangeRate(currency);

        return PriceConverterResponse.builder()
                .convertedPrice(price.multiply(averageExchangeRate.getRates().get(0).getMid()))
                .build();

    }
}
