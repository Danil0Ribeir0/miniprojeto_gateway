package view.student;

import controller.student.studentController;
import model.entity.student;
import model.service.subject.enrollmentService.enrollmentResult;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class studentView {
    private final studentController controller;

    public studentView() {
        this.controller = new studentController();
    }

    public void displayStudentOptions(Scanner scanner) {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- DOMÍNIO DISCENTE ---");
            System.out.println("1. Listar todos os Discentes");
            System.out.println("2. Detalhes do Discente por ID");
            System.out.println("3. Gerar ID de Matrícula Principal");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Sua opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listAllStudents();
                        break;
                    case 2:
                        getStudentDetails(scanner);
                        break;
                    case 3:
                        registerPrimaryEnrollmentId(scanner);
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

    private void listAllStudents() {
        System.out.println("\n--- LISTA DE DISCENTES ---");
        List<student> students = controller.listAllStudents();
        String apiMessage = controller.getLastApiMessage();

        if (apiMessage != null) {
            System.err.println("[LOG] ERRO DA API:");
            System.err.println(apiMessage);
        }

        if (students.isEmpty()) {
            System.out.println("Nenhum discente encontrado na API.");
            return;
        }
        students.forEach(s -> System.out.printf("ID: %s | Nome: %s | Curso: %s | Status: %s\n",
                s.getId(), s.getNome(), s.getCurso(), s.getStatus()));
    }

    private void getStudentDetails(Scanner scanner) {
        System.out.print("Digite o ID do Discente: ");
        String id = scanner.nextLine();
        student s = controller.getStudentDetails(id);

        if (s != null) {
            System.out.println("\n--- DETALHES DO DISCENTE ---");
            System.out.printf("ID: %s\n", s.getId());
            System.out.printf("Nome: %s\n", s.getNome());
            System.out.printf("Curso: %s\n", s.getCurso());
            System.out.printf("Modalidade: %s\n", s.getModalidade());
            System.out.printf("Status Acadêmico: %s (Ativo: %b)\n", s.getStatus(), s.isStatusActive());
            System.out.printf("ID de Matrícula Principal: %s\n", s.getPrimaryEnrollmentId() != null ? s.getPrimaryEnrollmentId() : "N/A");
            System.out.println("Reservas de Livro Simuladas: " + s.getBookReservations().size());

            List<Map.Entry<String, String>> enrollments = s.getEnrollments();
            System.out.println("\n--- MATRÍCULAS SIMULADAS ---");

            if (enrollments.isEmpty()) {
                System.out.println("O discente não possui disciplinas matriculadas (simuladas).");
            } else {
                System.out.println("Disciplinas Matriculadas:");
                enrollments.forEach(e -> System.out.printf("  > ID Matrícula Único: %s | ID Disciplina: %s\n",
                        e.getKey(), e.getValue()));
            }

        } else {
            System.out.println("Discente com ID " + id + " não encontrado.");
        }
    }

    private void registerPrimaryEnrollmentId(Scanner scanner) {
        System.out.print("Digite o ID do Discente para gerar o ID de Matrícula Principal: ");
        String studentId = scanner.nextLine();

        enrollmentResult result = controller.registerPrimaryEnrollmentId(studentId);

        System.out.println("\n--- RESULTADO DA MATRÍCULA PRINCIPAL ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
        if (result.isSuccess()) {
            System.out.println("ID de Matrícula Gerado: " + result.getEnrollmentId());
        }
    }
}