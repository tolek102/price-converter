package pl.pbf.sandbox.priceconverter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pbf.sandbox.priceconverter.client.nbp.NbpClient;
import pl.pbf.sandbox.priceconverter.client.nbp.model.RatesItem;
import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;
import pl.pbf.sandbox.priceconverter.exception.PriceConverterException;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceConverterService {

    private final NbpClient nbpClient;

    public PriceConverterResponse convertPriceToPln(final String currency, final BigDecimal price) {
        final var averageExchangeRate = getAverageExchangeRate(currency);
        log.info("NBP response: {}", averageExchangeRate);

        final var convertedPrice = convertPrice(price, averageExchangeRate);

        return PriceConverterResponse.builder().calculatedPrice(convertedPrice)
                .formattedPrice(formatPrice(convertedPrice)).build();
    }

    private BigDecimal getAverageExchangeRate(final String currency) {
        return Optional.ofNullable(nbpClient.getNbpCurrencyInfo(currency).getRates())
                .orElse(List.of()).stream()
                .map(RatesItem::getMid)
                .findFirst()
                .orElseThrow(() -> new PriceConverterException(
                        String.format("Missing average exchange rate on response for %s currency", currency),
                        HttpStatus.EXPECTATION_FAILED));
    }

    private BigDecimal convertPrice(final BigDecimal price, final BigDecimal averageExchangeRate) {
        return price
                .multiply(averageExchangeRate)
        // inteliJ somehow don't see class java.math.RoundingMode (java coretto 21.0.1)
//                .setScale(2 , RoundingMode.HALF_EVEN);
                .setScale(2 , ROUND_HALF_EVEN);
    }

    private String formatPrice(final BigDecimal convertedPrice) {

        return String.format(Locale.US, "%,.2f", convertedPrice)
                .replace(",", " ")
                .replace(".", ",")
                .concat(" zł");
    }
}
