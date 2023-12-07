package pl.pbf.sandbox.priceconverter.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class CustomErrorResponse {

    private String errorMessage;
    private String url;
    private HttpStatus httpStatus;
    private Integer statusCode;
    private LocalDateTime timestamp;
}
