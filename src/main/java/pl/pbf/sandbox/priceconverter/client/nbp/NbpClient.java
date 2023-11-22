package pl.pbf.sandbox.priceconverter.client.nbp;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import pl.pbf.sandbox.priceconverter.client.nbp.model.NbpResponse;
import pl.pbf.sandbox.priceconverter.configuration.ClientsConfiguration;

@Component
@RequiredArgsConstructor
public class NbpClient {

    private static final String AVERAGE_EXCHANGE_RATE_BY_CURRENCY_ENDPOINT = "/api/exchangerates/rates/A/{currency}";
    private final ClientsConfiguration clientsConfiguration;

    public NbpResponse getAverageExchangeRate(final String currency) {
        RestClient restClient = RestClient.create();
        final String baseUrl = clientsConfiguration.getNbp().getUrl();

        final ResponseEntity<NbpResponse> response = restClient.get()
                .uri(baseUrl + AVERAGE_EXCHANGE_RATE_BY_CURRENCY_ENDPOINT, currency)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(NbpResponse.class);

        return response.getBody();
    }
}
