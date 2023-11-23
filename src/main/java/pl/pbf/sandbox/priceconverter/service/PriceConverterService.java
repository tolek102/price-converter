package pl.pbf.sandbox.priceconverter.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Locale;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pbf.sandbox.priceconverter.client.nbp.NbpClient;
import pl.pbf.sandbox.priceconverter.client.nbp.model.NbpResponse;
import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceConverterService {

    private final NbpClient nbpClient;

    public PriceConverterResponse convertPriceToPln(final String currency, final BigDecimal price) {
        final NbpResponse averageExchangeRate = nbpClient.getAverageExchangeRate(currency);
        log.info("NBP response: {}", averageExchangeRate);

        final BigDecimal convertedPrice = convertPrice(price, averageExchangeRate);

        return PriceConverterResponse.builder().calculatedPrice(convertedPrice)
                .formattedPrice(formatPrice(convertedPrice)).build();

    }

    private BigDecimal convertPrice(final BigDecimal price, final NbpResponse averageExchangeRate) {
        return price
                .multiply(averageExchangeRate.getRates().get(0).getMid())
                .round(new MathContext(4))
        // inteliJ somehow don't see class java.math.RoundingMode (java coretto 21.0.1)
        //                .setScale(2, RoundingMode.HALF_EVEN);
                .setScale(2);
    }

    private String formatPrice(final BigDecimal convertedPrice) {
        return String.format(Locale.FRANCE, "%,.2f", convertedPrice)
                .concat(" zł");
    }
}
