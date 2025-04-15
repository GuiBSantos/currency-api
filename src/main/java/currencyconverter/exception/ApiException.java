package main.java.currencyconverter.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
