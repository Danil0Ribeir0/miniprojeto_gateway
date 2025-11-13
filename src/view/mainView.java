package view;

import controller.book.bookController;
import controller.student.studentController;
import controller.subject.subjectController;

import view.book.bookView;
import view.student.studentView;
import view.subject.subjectView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class mainView {
    private final studentView studentView;
    private final subjectView subjectView;
    private final bookView bookView;

    public mainView(studentController studentController, subjectController subjectController, bookController bookController) {
        this.studentView = new studentView(studentController);
        this.subjectView = new subjectView(subjectController);
        this.bookView = new bookView(bookController);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        System.out.println("--- API GATEWAY ACADÊMICO - SIMULAÇÃO ---");

        while (option != 0) {
            displayMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        studentView.displayStudentOptions(scanner);
                        break;
                    case 2:
                        subjectView.displaySubjectOptions(scanner);
                        break;
                    case 3:
                        bookView.displayBookOptions(scanner);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Estado volátil descartado.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println("Escolha o Domínio de Interação:");
        System.out.println("1. Discente (Estudante)");
        System.out.println("2. Disciplina (Matrículas)");
        System.out.println("3. Biblioteca (Livros)");
        System.out.println("0. Sair");
        System.out.print("Sua opção: ");
    }
}