package main.java.currencyconverter.model;

import java.util.Map;

public record HistoricalResponse(Map<String, Double> conversion_rates, String base_code, Integer day, Integer month, Integer year) {
}
