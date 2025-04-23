package currencyconverter.api;

import currencyconverter.model.request.ConversionRequest;
import currencyconverter.model.request.StandardRequest;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateClient {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("API_KEY");

    public String getStandardRate(StandardRequest request) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + request.base_code();
        return makeHttpRequest(url);
    }

    public String getConversionRate(ConversionRequest request) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/"
                + request.base_code() + "/" + request.target_code() + "/" + request.amount();
        return makeHttpRequest(url);
    }

    public String getSupportedCodes() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/codes";
        return makeHttpRequest(url);
    }

    private String makeHttpRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
