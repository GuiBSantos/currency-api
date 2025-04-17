package main.java.currencyconverter.service;

import com.google.gson.Gson;
import main.java.currencyconverter.api.ExchangeRateClient;
import main.java.currencyconverter.model.ConversionResponse;
import main.java.currencyconverter.model.StandartResponse;
import main.java.currencyconverter.model.SupportedResponse;
import main.java.currencyconverter.model.request.ConversionRequest;
import main.java.currencyconverter.model.request.StandartRequest;
import main.java.currencyconverter.util.Utils;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverterService {

    public void converterService() {
        System.out.println("==== Conversor de moeda ====");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            ExchangeRateClient client = new ExchangeRateClient();

            try {
                System.out.print("""
                        
                        ====== MENU PRINCIPAL ======
                        1 - Tabela de Cotação
                        2 - Conversão de Moedas
                        3 - Moedas Suportadas
                        4 - Sair
                        =============================
                        
                        Escolha uma opção: """);

                Integer respose = scanner.nextInt();

                switch (respose) {
                    case 1 -> handStandartRate(scanner, client);
                    case 2 -> handleCurrencyConversion(scanner, client);
                    case 3 -> handleSupportCodes(client);
                    case 4 -> {
                        System.out.println("Saindo...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("\nOpção inválida, tente novamente.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida. Certifique-se de digitar um número válido.\n");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\nErro inesperado: " + e.getMessage() + "\n");
            }
            System.out.print("""
                    Deseja fazer outra operação?
                    1 - Sim
                    2 - Não
                    
                    Sua escolha: """);

            int continuar = scanner.nextInt();
            if (continuar != 1) {
                System.out.println("Saindo...");
                break;
            }
        }
    }

    private void handStandartRate(Scanner scanner, ExchangeRateClient client) throws IOException, InterruptedException{
        System.out.print("\nDigite o código da moeda (ex: USD, BRL): ");
        String currency = scanner.next().trim().toUpperCase();

        StandartRequest requestStandart = new StandartRequest(currency);

        try {

            String jsonStandart = client.getStandardRate(requestStandart);
            Gson gson = Utils.getGson();
            StandartResponse parsed = gson.fromJson(jsonStandart, StandartResponse.class);

            System.out.println("\n===== TABELA DE COTAÇÃO =====");
            System.out.println("Moeda base: " + parsed.base_code());
            System.out.println("Última atualização: " + parsed.time_last_update_utc());
            System.out.println("Taxa de conversão: " + parsed.conversion_rates());
            System.out.println("==============================\n");

        } catch (IOException | InterruptedException e) {

            System.out.println("Erro ao buscar taxa de cambio: " + e.getMessage());

        }
    }
    private void handleCurrencyConversion(Scanner scanner, ExchangeRateClient client) {
        System.out.println("Digite o código da moeda de origem (ex: USD, BRL): ");
        String fromCurrency = scanner.next().trim().toUpperCase();

        System.out.println("Digite o código da moeda de destino (ex: EUR, JPY): ");
        String toCurrency = scanner.next().trim().toUpperCase();

        System.out.println("Valor a ser convertido (ex: 32, 100.0): ");
        double amount = Double.parseDouble(scanner.next().trim());

        ConversionRequest requestPair = new ConversionRequest(fromCurrency, toCurrency, amount);

        try {

            String jsonPair = client.getConversionRate(requestPair);
            Gson gson = Utils.getGson();
            ConversionResponse parsed = gson.fromJson(jsonPair, ConversionResponse.class);

            System.out.println("\n===== CONVERSÃO DE MOEDAS =====");
            System.out.println("Última atualização: " + parsed.time_last_update_utc());
            System.out.println("Moeda base: " + fromCurrency);
            System.out.println("Moeda alvo: " + toCurrency);
            System.out.println("Quantidade: " + amount);
            System.out.println("Taxa de conversão: " + parsed.conversion_rate());
            System.out.println("Resultado da conversão: " + parsed.conversion_result());
            System.out.println("================================\n");

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao comparar moedas: " + e.getMessage());
        }
    }
    private void handleSupportCodes(ExchangeRateClient client) {
        try {

            String jsonSupported = client.getSupportedCodes();
            Gson gson = Utils.getGson();
            SupportedResponse parsed = gson.fromJson(jsonSupported, SupportedResponse.class);

            System.out.println("\n===== MOEDAS SUPORTADAS =====");
            try {
                for (List<String> code : parsed.supported_codes()) {
                    System.out.println("• " + code.get(0) + " - " + code.get(1));
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Erro ao listar as moedas suportadas. Dados incompletos recebidos da API.");
            } catch (Exception e) {
                System.out.println("");
            }
            for (List<String> code : parsed.supported_codes()) {
                System.out.println("• " + code.get(0) + " - " + code.get(1));

            }
            System.out.println("================================\n");
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao exibir valores suportados: " + e.getMessage());
        }
    }
}


