package currencyconverter.service;

import com.google.gson.Gson;
import currencyconverter.api.ExchangeRateClient;
import currencyconverter.model.ConversionResponse;
import currencyconverter.model.StandardResponse;
import currencyconverter.model.SupportedResponse;
import currencyconverter.model.request.ConversionRequest;
import currencyconverter.model.request.StandardRequest;
import currencyconverter.util.Utils;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

    public class CurrencyConverterService {

        private final ExchangeRateClient client = new ExchangeRateClient();
        private final Gson gson = Utils.getGson();

        public void startConverterService() {
            System.out.println("==== Conversor de Moeda ====");
            try (Scanner scanner = new Scanner(System.in)) {
                boolean continueRunning = true;
                while (continueRunning) {
                    showMainMenu();
                    try {
                        int option = scanner.nextInt();
                        scanner.nextLine();
                        switch (option) {
                            case 1 -> handleExchangeRateTable(scanner);
                            case 2 -> handleCurrencyConversion(scanner);
                            case 3 -> handleSupportedCurrencies();
                            case 4 -> {
                                System.out.println("Saindo...");
                                return;
                            }
                            default -> System.out.println("\nOpção inválida, tente novamente.\n");
                        }

                        continueRunning = askToContinue(scanner);

                    } catch (InputMismatchException e) {
                        System.out.println("\nEntrada inválida. Certifique-se de digitar um número válido.\n");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("\nErro inesperado: " + e.getMessage() + "\n");
                    }
                }
            }
        }

        private void showMainMenu() {
            System.out.print("""
                    
                    ====== MENU PRINCIPAL ======
                    1 - Tabela de Cotação
                    2 - Conversão de Moedas
                    3 - Moedas Suportadas
                    4 - Sair
                    ========================
                    
                    Escolha uma opção: """);
        }

        private boolean askToContinue(Scanner scanner) {
            System.out.print("""
                    Deseja realizar outra operação?
                    1 - Sim
                    2 - Não
                    
                    Sua escolha: """);

            try {
                int response = scanner.nextInt();
                scanner.nextLine();
                return response == 1;
            } catch (InputMismatchException e) {
                scanner.nextLine();

                return false;
            }
        }

        private void handleExchangeRateTable(Scanner scanner) {
            System.out.print("\nDigite o código da moeda (ex: USD, BRL): ");

            try {
                String currency = scanner.nextLine().trim().toUpperCase();

                if (currency.isEmpty() || !currency.matches("^[A-Z]{3}$")) {
                    throw new IllegalArgumentException("Erro: Por favor, insira um código de moeda de 3 letras (ex: USD, BRL).");
                }

                StandardRequest request = new StandardRequest(currency);
                String json = client.getStandardRate(request);
                StandardResponse response = gson.fromJson(json, StandardResponse.class);

                System.out.println("\n===== TABELA DE COTAÇÃO =====");
                System.out.println("Moeda base: " + response.base_code());
                System.out.println("Última atualização: " + response.time_last_update_utc());
                System.out.println("Taxas de conversão: " + response.conversion_rates());
                System.out.println("================================\n");

            } catch (IOException | InterruptedException e) {
                System.out.println("Erro ao buscar a taxa de câmbio: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        private void handleCurrencyConversion(Scanner scanner) {
            try {
                System.out.println("Digite o código da moeda de origem (ex: USD, BRL): ");
                String fromCurrency = scanner.nextLine().trim().toUpperCase();

                System.out.println("Digite o código da moeda de destino (ex: EUR, JPY): ");
                String toCurrency = scanner.nextLine().trim().toUpperCase();

                System.out.println("Valor a ser convertido (ex: 32.5): ");
                double amount = Double.parseDouble(scanner.nextLine().trim());

                ConversionRequest request = new ConversionRequest(fromCurrency, toCurrency, amount);
                String json = client.getConversionRate(request);
                ConversionResponse response = gson.fromJson(json, ConversionResponse.class);

                System.out.println("\n===== CONVERSÃO DE MOEDAS =====");
                System.out.println("Última atualização: " + response.time_last_update_utc());
                System.out.println("Moeda base: " + fromCurrency);
                System.out.println("Moeda de destino: " + toCurrency);
                System.out.println("Quantidade: " + amount);
                System.out.println("Taxa de conversão: " + response.conversion_rate());
                System.out.println("Resultado da conversão: " + response.conversion_result());
                System.out.println("================================\n");

            } catch (NumberFormatException e) {
                System.out.println("Erro: Valor inválido. Use ponto para decimais (ex: 10.5).");
            } catch (IOException | InterruptedException e) {
                System.out.println("Erro ao realizar a conversão de moedas: " + e.getMessage());
            }
        }

        private void handleSupportedCurrencies() {
            try {
                String json = client.getSupportedCodes();
                SupportedResponse response = gson.fromJson(json, SupportedResponse.class);

                System.out.println("\n===== MOEDAS SUPORTADAS =====");
                for (List<String> code : response.supported_codes()) {
                    if (code.size() >= 2) {
                        System.out.println("• " + code.get(0) + " - " + code.get(1));
                    }
                }
                System.out.println("================================\n");

            } catch (IOException | InterruptedException e) {
                System.out.println("Erro ao exibir as moedas suportadas: " + e.getMessage());
            }
        }
    }

