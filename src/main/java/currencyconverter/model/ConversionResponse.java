package currencyconverter.model;

public record ConversionResponse(String time_last_update_utc, String fromCurrency, String toCurrency, Double conversion_rate, Double conversion_result ,Double amount) {

}
