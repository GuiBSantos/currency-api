package main.java.currencyconverter.model;

import java.util.Map;

public record StandartResponse(
        String base_code,
        Map<String, Double> conversion_rates,
        String time_last_update_utc
) {}
