package main.java.currencyconverter.api;

import main.java.currencyconverter.model.request.ConversionRequest;
import main.java.currencyconverter.model.request.StandartRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateClient {


    public String getStandardRate(StandartRequest request) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/af39660cc6310fe4cf2fba93/latest/" + request.base_code();
        return makeHttpRequest(url);
    }

    public String getConversionRate(ConversionRequest request) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/af39660cc6310fe4cf2fba93/pair/"
                + request.base_code() + "/" + request.target_code() + "/" + request.amount();
        return makeHttpRequest(url);
    }

    public String getSupportedCodes() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/af39660cc6310fe4cf2fba93/codes";
        return makeHttpRequest(url);
    }

    private String makeHttpRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
