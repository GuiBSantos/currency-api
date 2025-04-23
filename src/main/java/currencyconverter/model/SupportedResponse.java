package currencyconverter.model;

import java.util.List;

public record SupportedResponse (List<List<String>> supported_codes){
}

