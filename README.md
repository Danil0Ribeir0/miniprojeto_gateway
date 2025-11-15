# API Gateway Acadêmico (Mini Projeto)

## 1. Objetivo do Projeto

Este projeto consiste em uma aplicação monolítica em Java que atua como **Fachada/API Gateway** para três microsserviços acadêmicos externos simulados: **Discente, Disciplina e Biblioteca**. O objetivo é agregar e apresentar informações de diferentes domínios em uma interface unificada para o usuário final.

* As operações de consulta (leitura) são realizadas nos microsserviços externos.
* As operações de escrita (Matrícula, Reserva) são **simulações locais**, mantendo o estado volátil apenas em **memória** enquanto a aplicação estiver em execução, sem afetar os serviços externos.

---

## 2. Arquitetura Adotada: MVC Moderno (Camadas)

O projeto segue o padrão arquitetural **Model-View-Controller (MVC)** em camadas, promovendo a separação de responsabilidades e aplicando princípios de design como **SOLID** e **GRASP**.

### Estrutura de Camadas (Pacotes)

| Camada | Pacote | Responsabilidade Principal | Conceito |
| :--- | :--- | :--- | :--- |
| **View** | `view.*` | Interação com o usuário (CLI). Exibe dados e captura *inputs*. | **Controller (GRASP)** |
| **Controller** | `controller.*` | Ponto de entrada das requisições. Atua como **Fachada** da aplicação, delegando tarefas complexas aos serviços do Model. | **Controller (GRASP)** |
| **Model - Service** | `model.service.*` | Contém toda a **lógica de negócio** e validações (Ex: limite de 5 disciplinas, verificação de status ativo). | **Alta Coesão (GRASP), SRP (SOLID)** |
| **Model - Repository** | `model.repository.*` | Camada de acesso a dados. Implementa o padrão **Singleton** e o **Repositório em Memória**. | **DIP (SOLID)** via interface `IRepository` |
| **Model - Entity** | `model.entity.*` | Objetos que representam os dados do domínio. Também gerenciam o **estado volátil simulado** (matrículas e reservas). | |
| **Model - API/DTO** | `api.*`, `api.dto.*` | Comunicação HTTP com serviços externos e *mapping* de dados (Jackson). | **Baixo Acoplamento (GRASP)** |

---

## 3. Padrões de Design e Princípios Aplicados

### 3.1. Princípios SOLID (Mínimo 2)

| Princípio | Aplicação no Projeto |
| :--- | :--- |
| **Single Responsibility Principle (SRP)** | Evidente na divisão entre serviços. Ex: `bookService.java` apenas lista dados, enquanto `bookReservationService.java` lida estritamente com a lógica de simulação de reserva. |
| **Dependency Inversion Principle (DIP)** | Módulos de alto nível não dependem de módulos de baixo nível. Ex: A classe base `apiBase.java` depende da abstração `IJsonObjectMapper.java` para a deserialização JSON, não de implementações concretas. |

### 3.2. Padrões GRASP (Mínimo 3)

| Padrão | Aplicação no Projeto |
| :--- | :--- |
| **Controller** | As classes `*Controller` (Ex: `studentController.java`) recebem todas as requisições do usuário (`View`) e orquestram a execução na camada de serviço, desacoplando a lógica de negócio da interface. |
| **High Cohesion (Alta Coesão)** | Classes são altamente focadas. Ex: `enrollmentIdGenerator.java` existe unicamente para criar o ID Único de Matrícula/Transação. |
| **Low Coupling (Baixo Acoplamento)** | O projeto utiliza a Inversão de Dependência via interfaces (`IRepository`), reduzindo a interdependência direta entre as classes. |

---

## 4. Requisitos Não Funcionais

### 4.1. Usabilidade (10 Heurísticas de Nielsen)

O design da interface de linha de comando (CLI) foi concebido seguindo as 10 Heurísticas de Nielsen, garantindo uma boa **User Experience (UX)**  mesmo em ambiente de console:

* **Visibilidade e Feedback:** O sistema exibe o menu principal e submenus após cada ação e sempre retorna mensagens claras de **SUCESSO** ou **FALHA** para as simulações.
* **Controle e Liberdade:** O usuário tem a opção de **Voltar** (opção 0) em todos os níveis, oferecendo controle sobre a navegação.
* **Prevenção e Tratamento de Erros:** Erros de digitação (*input*) são capturados (`InputMismatchException`) e as regras de negócio atuam como prevenção de erros lógicos (Ex: não permitir reserva de livro indisponível).
* **Consistência e Padrões:** A estrutura e a terminologia (`Discente`, `Disciplina`) são mantidas de forma uniforme em toda a aplicação.

### 4.2. Desempenho e Tolerância a Falhas

* **Controle de Timeout:** A classe base `apiBase.java` impõe um tempo limite de **3 segundos** para todas as requisições HTTP.
* **Degradação Graciosa:** Em caso de falha de comunicação HTTP (status != 200) ou erro de I/O, a aplicação retorna listas vazias e exibe uma **mensagem amigável** na *View*, informando a falha do serviço externo.

### 4.3. Endpoints dos Microsserviços Externos

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