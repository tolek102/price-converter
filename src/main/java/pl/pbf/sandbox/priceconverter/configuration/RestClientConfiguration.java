package pl.pbf.sandbox.priceconverter.configuration;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class RestClientConfiguration {

    @NonNull
    private String url;
}
