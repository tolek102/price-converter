package pl.pbf.sandbox.priceconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceConverterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomErrorResponse handleNoRecordFoundException(final PriceConverterException ex) {

        return CustomErrorResponse.builder()
                .httpStatus(ex.getHttpStatus())
                .statusCode(ex.getHttpStatus().value())
                .errorMessage(ex.getErrorMessage())
                .url(ex.getUri())
                .build();
    }
}