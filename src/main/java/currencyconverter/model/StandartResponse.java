package main.java.currencyconverter.model;

public record StandartResponse(String base_code, Double conversion_rates, String time_last_update_utc) {
}
