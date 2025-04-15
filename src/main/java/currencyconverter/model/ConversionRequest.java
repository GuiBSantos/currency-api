package main.java.currencyconverter.model;

public record ConversionRequest(String fromCurrency, String toCurrency, double amount) {
}
