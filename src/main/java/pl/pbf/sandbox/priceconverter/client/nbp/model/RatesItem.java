package pl.pbf.sandbox.priceconverter.client.nbp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RatesItem {

	String no;
	BigDecimal mid;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate effectiveDate;
}
