package pl.pbf.sandbox.priceconverter.controller.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceConverterResponse {

    private BigDecimal calculatedPrice;
    private String formattedPrice;
}
