package pl.pbf.sandbox.priceconverter.client.nbp;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec.ErrorHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pbf.sandbox.priceconverter.client.nbp.model.NbpResponse;
import pl.pbf.sandbox.priceconverter.configuration.ClientsConfiguration;
import pl.pbf.sandbox.priceconverter.exception.PriceConverterException;

@Slf4j
@Component
@RequiredArgsConstructor
public class NbpClient {

    private static final String AVERAGE_EXCHANGE_RATE_BY_CURRENCY_ENDPOINT = "/api/exchangerates/rates/A/{currency}";
    private final ClientsConfiguration clientsConfiguration;

    public NbpResponse getNbpCurrencyInfo(final String currency) {
        final var restClient = RestClient.create();
        final var baseUrl = clientsConfiguration.getNbp().getUrl();

        final var nbpResponse = restClient.get()
                .uri(baseUrl + AVERAGE_EXCHANGE_RATE_BY_CURRENCY_ENDPOINT, currency)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, throwNotFoundException(currency))
                .toEntity(NbpResponse.class);

        return nbpResponse.getBody();
    }

    private ErrorHandler throwNotFoundException(final String currency) {
        return (request, response) -> {
            log.error("Exchange rate for currency {} not found when call {} to uri {}", currency, request.getMethod(), request.getURI());
            throw PriceConverterException.notFound(String.format("Exchange rate for currency %s not found", currency),
                    request.getURI().toString());
        };
    }
}
