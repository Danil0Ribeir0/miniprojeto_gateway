package view.book;

import controller.book.bookController;
import model.entity.book;
import model.service.book.bookReservationService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class bookView {
    private final bookController controller;

    public bookView() {
        this.controller = new bookController();
    }

    public void displayBookOptions(Scanner scanner) {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- DOMÍNIO BIBLIOTECA/LIVRO ---");
            System.out.println("1. [CONSULTA] Listar todos os Livros");
            System.out.println("2. [SIMULAÇÃO] Reservar Livro");
            System.out.println("3. [SIMULAÇÃO] Cancelar Reserva de Livro");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Sua opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listAllBooks();
                        break;
                    case 2:
                        simulateBookReservation(scanner);
                        break;
                    case 3:
                        simulateBookReservationCancellation(scanner);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private void listAllBooks() {
        System.out.println("\n--- LISTA DE LIVROS NO ACERVO ---");
        List<book> books = controller.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("Nenhum livro encontrado na API.");
            return;
        }
        books.forEach(b -> System.out.printf("ID: %s | Título: %s | Autor: %s | Ano: %d | Status: %s\n",
                b.getId(), b.getTitle(), b.getAuthor(), b.getYear(), b.getStatus()));
    }

    private void simulateBookReservation(Scanner scanner) {
        System.out.print("Digite o ID do Discente para reservar: ");
        String studentId = scanner.nextLine();
        System.out.print("Digite o ID do Livro: ");
        String bookId = scanner.nextLine();

        bookReservationService.ReservationResult result = controller.simulateReservation(studentId, bookId);

        System.out.println("\n--- RESULTADO DA RESERVA ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
        if (result.isSuccess()) {
            System.out.println("ID do Livro Reservado: " + result.getBookId());
        }
    }

    private void simulateBookReservationCancellation(Scanner scanner) {
        System.out.print("Digite o ID do Discente: ");
        String studentId = scanner.nextLine();
        System.out.print("Digite o ID do Livro para cancelar a reserva: ");
        String bookId = scanner.nextLine();

        bookReservationService.ReservationResult result = controller.simulateCancellation(studentId, bookId);

        System.out.println("\n--- RESULTADO DO CANCELAMENTO DA RESERVA ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
    }
}