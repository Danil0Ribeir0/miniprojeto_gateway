package view.subject;

import controller.subject.subjectController;
import model.entity.subject;
import model.service.subject.enrollmentService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class subjectView {
    private final subjectController controller;

    public subjectView() {
        this.controller = new subjectController();
    }

    public void displaySubjectOptions(Scanner scanner) {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- DOMÍNIO DISCIPLINA/MATRÍCULA ---");
            System.out.println("1. [CONSULTA] Listar todas as Disciplinas");
            System.out.println("2. [SIMULAÇÃO] Matricular Discente em Disciplina");
            System.out.println("3. [SIMULAÇÃO] Cancelar Matrícula (por ID Único)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Sua opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listAllSubjects();
                        break;
                    case 2:
                        simulateEnrollment(scanner);
                        break;
                    case 3:
                        simulateCancellation(scanner);
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

    private void listAllSubjects() {
        System.out.println("\n--- LISTA DE DISCIPLINAS ---");
        List<subject> subjects = controller.listAllSubjects();
        String apiMessage = controller.getLastApiMessage();

        if (apiMessage != null) {
            System.err.println("[LOG] ERRO DA API:");
            System.err.println(apiMessage);
        }

        if (subjects.isEmpty()) {
            System.out.println("Nenhuma disciplina encontrada na API.");
            return;
        }
        subjects.forEach(s -> System.out.printf("ID: %s | Nome: %s | Curso: %s | Vagas: %d\n",
                s.getId(), s.getName(), s.getCourse(), s.getTotalSlots()));
    }

    private void simulateEnrollment(Scanner scanner) {
        System.out.print("Digite o ID do Discente para matricular: ");
        String studentId = scanner.nextLine();
        System.out.print("Digite o ID da Disciplina: ");
        String subjectId = scanner.nextLine();

        enrollmentService.enrollmentResult result = controller.simulateEnrollment(studentId, subjectId);

        System.out.println("\n--- RESULTADO DA MATRÍCULA ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
        if (result.isSuccess()) {
            System.out.println("Novo ID de Matrícula Único: " + result.getEnrollmentId());
        }
    }

    private void simulateCancellation(Scanner scanner) {
        System.out.print("Digite o ID do Discente: ");
        String studentId = scanner.nextLine();
        System.out.print("Digite o ID ÚNICO da Matrícula para cancelar (o que foi gerado): ");
        String enrollmentId = scanner.nextLine();

        enrollmentService.enrollmentResult result = controller.simulateCancellation(studentId, enrollmentId);

        System.out.println("\n--- RESULTADO DO CANCELAMENTO ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
    }
}
