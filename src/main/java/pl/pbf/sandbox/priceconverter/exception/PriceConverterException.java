package pl.pbf.sandbox.priceconverter.exception;

import org.springframework.http.HttpStatus;

public class PriceConverterException extends RuntimeException {

    private String uri;
    private HttpStatus httpStatus;

    public PriceConverterException(final String message, final String uri, final HttpStatus httpStatus) {
        super(message);
        this.uri = uri;
        this.httpStatus = httpStatus;
    }

    public PriceConverterException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public PriceConverterException(final String message) {
        super(message);
    }

    public static PriceConverterException notFound(final String message, final String uri) {
        return new PriceConverterException(message, uri, HttpStatus.NOT_FOUND);
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String  getUri() {
        return this.uri;
    }

    public String getErrorMessage() {
        return this.getMessage();
    }

}
