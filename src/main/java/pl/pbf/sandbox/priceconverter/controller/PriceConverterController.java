package pl.pbf.sandbox.priceconverter.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.pbf.sandbox.priceconverter.controller.model.PriceConverterResponse;
import pl.pbf.sandbox.priceconverter.service.PriceConverterService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PriceConverterController {

    private final PriceConverterService priceConverterService;

    @GetMapping
    public PriceConverterResponse convertPriceToPln(@RequestParam("currency") final String currency,
            @RequestParam("price") final BigDecimal price) {
        return priceConverterService.convertPriceToPln(currency, price);

    }
}
