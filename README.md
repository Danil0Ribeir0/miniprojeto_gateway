# 📘 API Gateway Acadêmico (Mini Projeto)

## 1. Objetivo do Projeto

[cite_start]Este projeto consiste em uma aplicação monolítica em Java que atua como **Fachada/API Gateway** para três microsserviços acadêmicos externos simulados: **Discente, Disciplina e Biblioteca**[cite: 7]. [cite_start]O objetivo é agregar e apresentar informações de diferentes domínios em uma interface unificada para o usuário final[cite: 7].

* As operações de consulta (leitura) são realizadas nos microsserviços externos.
* [cite_start]As operações de escrita (Matrícula, Reserva) são **simulações locais**, mantendo o estado volátil apenas em **memória** enquanto a aplicação estiver em execução, sem afetar os serviços externos[cite: 8, 15].

---

## 2. Arquitetura Adotada: MVC Moderno (Camadas)

[cite_start]O projeto segue o padrão arquitetural **Model-View-Controller (MVC)** em camadas, promovendo a separação de responsabilidades e aplicando princípios de design como **SOLID** e **GRASP**[cite: 61, 62].

### Estrutura de Camadas (Pacotes)

| Camada | Pacote | Responsabilidade Principal | Conceito |
| :--- | :--- | :--- | :--- |
| **View** | `view.*` | Interação com o usuário (CLI). Exibe dados e captura *inputs*. | **Controller (GRASP)** |
| **Controller** | `controller.*` | Ponto de entrada das requisições. [cite_start]Atua como **Fachada** da aplicação[cite: 83], delegando tarefas complexas aos serviços do Model. | **Controller (GRASP)** |
| **Model - Service** | `model.service.*` | [cite_start]Contém toda a **lógica de negócio** e validações (Ex: limite de 5 disciplinas, verificação de status ativo)[cite: 27]. | **Alta Coesão (GRASP), SRP (SOLID)** |
| **Model - Repository** | `model.repository.*` | Camada de acesso a dados. [cite_start]Implementa o padrão **Singleton** e o **Repositório em Memória**[cite: 22]. | **DIP (SOLID)** via interface `IRepository` |
| **Model - Entity** | `model.entity.*` | Objetos que representam os dados do domínio. [cite_start]Também gerenciam o **estado volátil simulado** (matrículas e reservas)[cite: 15]. | |
| **Model - API/DTO** | `api.*`, `api.dto.*` | [cite_start]Comunicação HTTP com serviços externos e *mapping* de dados (Jackson)[cite: 32]. | **Baixo Acoplamento (GRASP)** |

---

## 3. Padrões de Design e Princípios Aplicados

### 3.1. [cite_start]Princípios SOLID (Mínimo 2) [cite: 62]

| Princípio | Aplicação no Projeto |
| :--- | :--- |
| **Single Responsibility Principle (SRP)** | Cada classe tem um único motivo para mudar, como o `studentMapper.java` que só lida com a conversão de DTO para Entity. |
| **Dependency Inversion Principle (DIP)** | Módulos de alto nível (`apiBase.java`) dependem de abstrações (`IJsonObjectMapper.java`) para a deserialização JSON, em vez de implementações concretas de baixo nível. |

### 3.2. [cite_start]Padrões GRASP (Mínimo 3) [cite: 62]

| Padrão | Aplicação no Projeto |
| :--- | :--- |
| **Controller** | As classes `*Controller` (Ex: `studentController.java`) recebem as requisições do usuário e orquestram a execução na camada de serviço, desacoplando a lógica de negócio da interface. |
| **High Cohesion (Alta Coesão)** | Classes são altamente focadas, como `enrollmentIdGenerator.java`, que existe unicamente para criar o ID Único de Matrícula/Transação. |
| **Low Coupling (Baixo Acoplamento)** | O projeto utiliza a Inversão de Dependência via interfaces (`IRepository`), reduzindo a interdependência direta entre as classes. |

---

## 4. Requisitos Não Funcionais

### 4.1. Desempenho e Tolerância a Falhas

* [cite_start]**Controle de Timeout:** A classe base `apiBase.java` impõe um tempo limite de **3 segundos** para todas as requisições HTTP[cite: 68].
* [cite_start]**Logging de Desempenho:** Caso a requisição exceda 3 segundos, um log de alerta é gerado e repassado para a *View* para notificação[cite: 68].
* [cite_start]**Degradação Graciosa:** Em caso de falha de comunicação HTTP (status != 200) ou erro de I/O, a aplicação retorna listas vazias e exibe uma **mensagem amigável** na *View*, garantindo que o sistema não trave e que o usuário seja informado da falha do serviço externo[cite: 68].

### 4.2. [cite_start]Endpoints dos Microsserviços Externos [cite: 37, 39, 45, 46, 53, 54]

| Serviço | Endpoint Base |
| :--- | :--- |
| **Discente** | `https://rmi6vdpsq8.execute-api.us-east-2.amazonaws.com/msAluno` |
| **Disciplina** | `https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina` |
| **Biblioteca** | `https://qiiw8bgxka.execute-api.us-east-2.amazonaws.com/acervo/biblioteca` |

---

## 5. Instruções de Execução

1.  **Requisitos:** Certifique-se de que o **JDK** e as bibliotecas **Jackson** (para JSON) estão configuradas no ambiente (conforme o `mini_projeto.iml`).
2.  **Execução:** Execute o método `main` da classe `Main.java` (`src/Main.java`).
3.  **Uso:** Siga o menu de texto para navegar entre os domínios (Discente, Disciplina, Biblioteca) e realizar consultas ou simulações. O estado de matrícula e reserva será mantido até o encerramento do sistema.