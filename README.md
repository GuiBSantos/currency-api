# 💱 Currency Converter API

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=200&color=gradient&fontColor=7700ff"/>

API de conversão de moedas desenvolvida em **Java 24**, utilizando a [ExchangeRate API](https://www.exchangerate-api.com/) para obter **taxas de câmbio em tempo real**.

> Desenvolvido como desafio na trilha de Back-end do programa Oracle Next Education (ONE), este projeto tem fins didáticos

<p align="center">
  <a href="https://ibb.co/sJNk7h3T">
    <img src="https://i.ibb.co/sJNk7h3T/Badge-Conversor.png" alt="Badge-Conversor" border="0" />
  </a>
</p>

---

## 🚀 Tecnologias

- Java 24
- Maven
- `HttpClient` (requisições HTTP)
- `Gson` (parse de JSON)
- `dotenv-java` (gerenciamento de variáveis de ambiente)
- Arquitetura com DTOs, services e exceções personalizadas

---

## 🛠 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 24](https://www.oracle.com/java/technologies/javase/jdk-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- Conta gratuita na [ExchangeRate API](https://www.exchangerate-api.com/)

---

## 📁 Configuração Inicial

1. **Clone o repositório**:

```bash
git clone https://github.com/GuiBSantos/currency-api.git
cd currency-converter-api
```

2. **Adicione sua chave da API** no arquivo `.env` na raiz do projeto:

```ini
API_KEY=sua_chave_aqui
```

3. **Compile e execute o projeto:**

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="currencyconverter.util.Main"
```

  > Certifique-se de que o plugin Maven `exec-maven-plugin` está configurado no `pom.xml`.

---

## 📷 Exemplo de Uso

```text
===== MENU PRINCIPAL =====
1 - Tabela de Cotação
2 - Conversão de Moedas
3 - Moedas Suportadas
4 - Sair
===========================

Digite o código da moeda de origem (ex: USD): USD
Digite o código da moeda de destino (ex: BRL): BRL
Valor a ser convertido: 100

Resultado da conversão: 507.65 BRL
```
---
## 🤝 Contribuições
Sinta-se à vontade para abrir uma issue, sugerir melhorias, enviar um pull request ou me enviar uma DM :)

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=200&color=gradient&fontColor=d900ff&section=footer"/>
