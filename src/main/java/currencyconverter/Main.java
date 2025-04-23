package currencyconverter;

import currencyconverter.service.CurrencyConverterService;

public class Main {
    public static void main(String[] args) {
        CurrencyConverterService api = new CurrencyConverterService();
        api.startConverterService();
    }
}
