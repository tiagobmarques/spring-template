# Introdução
Esta API foi desenvolvida em Java usando o framework Spring, seguindo os princípios da arquitetura Clean Arch. A API foi projetada como um template para uso interno na empresa, visando facilitar o desenvolvimento de futuras aplicações. Ela serve como uma base sólida, permitindo a adição de funcionalidades específicas conforme necessário, garantindo a manutenção de boas práticas de organização e separação de responsabilidades no código.

#### Pré-requisitos
Para rodar este projeto é necessário ter algumas ferramentas instaladas:
* Docker
* Java 17

#### Tecnologias e frameworks
* Java 17
* Spring Boot
* Spring Data JPA
* Postgres
* Docker
* Gradle
* Lombok
* Swagger
* JUnit
* Mockito
* Jacoco

#### Rodar o projeto local
Para rodar a aplicação é necessário antes alguns passos:

```
./gradlew build
```
O comando acima irá baixar as dependências e gerar o artefato(.jar) da aplicação.

```
docker-compose up --build
```
Este comando acima irá subir o banco de dados e aplicação dentro de um container docker para realizar o teste local. 


#### Rodar os testes

Neste projeto foram implementados dois tipos de testes. Teste unitário e testes de contrato.

```
./gradlew test
```
O comando acima irá rodar os testes unitários e os testes de contrato.

#### Acessando a documentação da aplicação

Toda aplicação está documentada via Swagger. Para acessar basta clicar neste [link](http://localhost:8080/swagger-ui/index.html).

#### Testes e Cobertura de Código
Este projeto utiliza o JaCoCo para gerar relatórios de cobertura de código. A verificação de cobertura exige que pelo menos 80% do código esteja coberto por testes. Arquivos que terminam com Repository e Config são excluídos da verificação de cobertura.

Para executar os testes e verificar a cobertura de código, use o seguinte comando:

```
./gradlew clean test jacocoTestReport jacocoTestCoverageVerification
```

#### Acessando o Relatório de Cobertura
Após a execução dos testes, o relatório de cobertura de código será gerado no seguinte caminho:

```
build/reports/jacoco/test/html/index.html
```
