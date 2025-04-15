package main.java.currencyconverter.service;

import main.java.currencyconverter.api.ExchangeRateClient;
import main.java.currencyconverter.model.ConversionRequest;
import main.java.currencyconverter.model.HistoricalRateRequest;

import java.io.IOException;
import java.util.Scanner;

public class CurrencyConverterService {

    public void converterService() {
        System.out.println("==== Conversor de moeda ====");
        while (true) {
            Scanner scanner =  new Scanner(System.in);
            ExchangeRateClient client = new ExchangeRateClient();
            System.out.println(""" 
                    1 - Tabela de Cotação
                    2 - Conversão de Moedas
                    3 - Histórico de Preço
                    4 - Moedas Suportadas
                    5 - Sair
                    
                    Escolha uma opção:
                    """);
            Integer respose = scanner.nextInt();

            switch (respose) {

                case 1:
                    System.out.print("Digite o código da moeda (ex: USD, BRL): ");
                    String currency = scanner.next();
                    HistoricalRateRequest requestStandart = new HistoricalRateRequest(currency, 0, 0, 0);

                    try {
                        String jsonStandart = client.getStandardRate(requestStandart);
                        System.out.println("Resposta da API:\n" + jsonStandart);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Erro ao buscar taxa de cambio: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Digite o código da moeda de origem (ex: USD, BRL): ");
                    String fromCurrency = scanner.next().trim().toUpperCase();

                    System.out.println("Digite o código da moeda de destino (ex: EUR, JPY): ");
                    String toCurrency = scanner.next().trim().toUpperCase();

                    System.out.println("Valor a ser convertido (ex: 32.5, 100.0): ");
                    double amount = Double.parseDouble(scanner.next().trim());

                    ConversionRequest requestPair = new ConversionRequest(fromCurrency, toCurrency, amount);

                    try {
                        String jsonPair = client.getConversionRate(requestPair);
                        System.out.println("Resposta da API:\n" + jsonPair);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Erro ao comparar moedas: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Digite o código da moeda (ex: USD, BRL): ");
                    String currencyHistorial = scanner.next().trim().toUpperCase();

                    System.out.print("Digite o ano para consultarmos: ");
                    int yearHistorical = Integer.parseInt(scanner.next().trim());

                    System.out.print("Digite o mês para consultarmos: ");
                    int monthHistorical = Integer.parseInt(scanner.next().trim());

                    System.out.print("Digite o dia para consultarmos: ");
                    int dayHistorical = Integer.parseInt(scanner.next().trim());

                    HistoricalRateRequest requestHistorical = new HistoricalRateRequest(currencyHistorial, yearHistorical, monthHistorical, dayHistorical);

                    try {
                        String jsonHistorical = client.getHistoricalRate(requestHistorical);
                        System.out.println("Resposta da API:\n" + jsonHistorical);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Erro ao exibir historico de valores: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                    String jsonSupported = client.getSupportedCodes();
                    System.out.println("Resposta da API:\n" + jsonSupported);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Erro ao exibir valores suportados: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }
}
