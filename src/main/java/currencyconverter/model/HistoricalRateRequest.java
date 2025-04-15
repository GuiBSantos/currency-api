package main.java.currencyconverter.model;

public record HistoricalRateRequest(String currency, int year, int month, int day) {
}
