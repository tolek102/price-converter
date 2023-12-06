package pl.pbf.sandbox.priceconverter.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;
import pl.pbf.sandbox.priceconverter.service.PriceConverterService;

@RestController
@RequestMapping("/convert")
@RequiredArgsConstructor
@Tag(name = "Price Converter")
public class PriceConverterController {

    private final PriceConverterService priceConverterService;

    @Operation(summary = "Convert provided price with currency to PLN price")
    @GetMapping("/{currency}/{price}")
    public PriceConverterResponse convertPriceToPln(@PathVariable("currency") final String currency,
            @PathVariable("price") final BigDecimal price) {
        return priceConverterService.convertPriceToPln(currency, price);

    }
}
