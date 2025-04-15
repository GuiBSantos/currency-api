package main.java.currencyconverter.model;

public record PairResponse(String time_last_update_utc, String base_code, String target_code, Double conversion_rate) {
}
