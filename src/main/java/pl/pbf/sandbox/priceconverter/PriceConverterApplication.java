package pl.pbf.sandbox.priceconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import pl.pbf.sandbox.priceconverter.configuration.ClientsConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({ClientsConfiguration.class})
public class PriceConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceConverterApplication.class, args);
    }
}
