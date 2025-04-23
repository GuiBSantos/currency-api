package currencyconverter.model.request;

public record ConversionRequest(String base_code, String target_code, Double amount) {
}
