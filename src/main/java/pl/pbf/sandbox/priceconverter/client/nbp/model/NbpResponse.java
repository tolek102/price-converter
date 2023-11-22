package pl.pbf.sandbox.priceconverter.client.nbp.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NbpResponse {

    String code;
    List<RatesItem> rates;
    String currency;
    String table;
}
