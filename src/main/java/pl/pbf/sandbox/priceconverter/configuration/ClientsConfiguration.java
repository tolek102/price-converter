package pl.pbf.sandbox.priceconverter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@ConfigurationProperties("pl.pbf")
public class ClientsConfiguration {

    @NonNull
    private RestClientConfiguration nbp;

}
