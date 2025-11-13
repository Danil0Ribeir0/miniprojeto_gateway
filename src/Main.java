import api.apiStudent;
import api.apiSubject;
import model.entity.student;
import model.entity.subject;
import model.repository.studentRepository;
import model.repository.subjectRepository;
import model.service.student.enrollmentIdGenerator;
import model.service.student.enrollmentService;
import model.service.student.studentOnboardingService;
import model.service.student.studentService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // --- 1. Inicialização e Injeção de Dependências ---

        // APIs
        apiStudent apiStudent = new apiStudent();
        apiSubject apiSubject = new apiSubject();

        // Repositórios (Carregam dados voláteis na memória)
        studentRepository studentRepo = new studentRepository(apiStudent);
        subjectRepository subjectRepo = new subjectRepository(apiSubject);

        // Serviços de Negócio
        studentService readService = new studentService(studentRepo);
        enrollmentIdGenerator idGenerator = new enrollmentIdGenerator();
        enrollmentService enrollmentService = new enrollmentService(
                studentRepo,
                subjectRepo,
                idGenerator
        );

        studentOnboardingService onboardingService = new studentOnboardingService(studentRepo, idGenerator);

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        System.out.println("--- API GATEWAY ACADÊMICO - SIMULAÇÃO ---");

        // ----------------------------------------------------

        while (option != 0) {
            displayMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listAllStudents(readService);
                        break;
                    case 2:
                        getStudentDetails(readService, scanner);
                        break;
                    case 3:
                        listAllSubjects(subjectRepo);
                        break;
                    case 4:
                        registerStudentEnrollmentId(onboardingService, scanner);
                        break;
                    case 5:
                        simulateEnrollment(enrollmentService, scanner);
                        break;
                    case 6:
                        displayStudentEnrollments(studentRepo, scanner);
                        break;
                    case 7:
                        simulateCancellation(enrollmentService, scanner);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Estado volátil descartado.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpa o buffer
                option = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println("Escolha uma opção (Funcionalidades de Teste):");
        System.out.println("1. [CONSULTA] Listar todos os Discentes");
        System.out.println("2. [CONSULTA] Detalhes do Discente por ID");
        System.out.println("3. [CONSULTA] Listar todas as Disciplinas");
        System.out.println("4. [SIMULAÇÃO] Gerar ID de Matrícula Principal"); // NOVO
        System.out.println("5. [SIMULAÇÃO] Matricular Discente em Disciplina"); // DESLOCADO
        System.out.println("6. [SIMULAÇÃO] Exibir Matrículas de um Discente"); // DESLOCADO
        System.out.println("7. [SIMULAÇÃO] Cancelar Matrícula (por ID Único)"); // DESLOCADO
        System.out.println("0. Sair");
        System.out.print("Sua opção: ");
    }

    private static void registerStudentEnrollmentId(studentOnboardingService service, Scanner scanner) { // ASSINATURA ATUALIZADA
        System.out.print("Digite o ID do Discente para gerar o ID de Matrícula Principal: ");
        String studentId = scanner.nextLine();

        enrollmentService.enrollmentResult result = service.registerStudentEnrollmentId(studentId);

        System.out.println("\n--- RESULTADO DA MATRÍCULA PRINCIPAL ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
        if (result.isSuccess()) {
            System.out.println("ID de Matrícula Gerado: " + result.getEnrollmentId());
        }
    }

    private static void listAllStudents(studentService service) {
        System.out.println("\n--- LISTA DE DISCENTES ---");
        List<student> students = service.listAllStudents();
        if (students.isEmpty()) {
            System.out.println("Nenhum discente encontrado na API.");
            return;
        }
        students.forEach(s -> System.out.printf("ID: %s | Nome: %s | Curso: %s | Status: %s\n",
                s.getId(), s.getNome(), s.getCurso(), s.getStatus()));
    }

    private static void getStudentDetails(studentService service, Scanner scanner) {
        System.out.print("Digite o ID do Discente: ");
        String id = scanner.nextLine();
        student s = service.getStudentById(id);

        if (s != null) {
            System.out.println("\n--- DETALHES DO DISCENTE ---");
            System.out.printf("ID: %s\n", s.getId());
            System.out.printf("Nome: %s\n", s.getNome());
            System.out.printf("Curso: %s\n", s.getCurso());
            System.out.printf("Modalidade: %s\n", s.getModalidade());
            System.out.printf("Status Acadêmico: %s (Ativo: %b)\n", s.getStatus(), s.isStatusActive());
        } else {
            System.out.println("Discente com ID " + id + " não encontrado.");
        }
    }

    private static void listAllSubjects(subjectRepository repo) {
        System.out.println("\n--- LISTA DE DISCIPLINAS ---");
        List<subject> subjects = repo.getAll();
        if (subjects.isEmpty()) {
            System.out.println("Nenhuma disciplina encontrada na API.");
            return;
        }
        subjects.forEach(s -> System.out.printf("ID: %s | Nome: %s | Curso: %s | Vagas: %d\n",
                s.getId(), s.getName(), s.getCourse(), s.getTotalSlots()));
    }

    private static void simulateEnrollment(enrollmentService service, Scanner scanner) {
        System.out.print("Digite o ID do Discente para matricular: ");
        String studentId = scanner.nextLine();
        System.out.print("Digite o ID da Disciplina: ");
        String subjectId = scanner.nextLine();

        enrollmentService.enrollmentResult result = service.simulateEnrollment(studentId, subjectId);

        System.out.println("\n--- RESULTADO DA MATRÍCULA ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
        if (result.isSuccess()) {
            System.out.println("Novo ID de Matrícula Único: " + result.getEnrollmentId());
        }
    }

    private static void displayStudentEnrollments(studentRepository repo, Scanner scanner) {
        System.out.print("Digite o ID do Discente para exibir as matrículas: ");
        String studentId = scanner.nextLine();
        student s = repo.getById(studentId);

        if (s == null) {
            System.out.println("Discente com ID " + studentId + " não encontrado.");
            return;
        }

        List<Map.Entry<String, String>> enrollments = s.getEnrollments();

        System.out.println("\n--- MATRÍCULAS SIMULADAS DE " + s.getNome() + " ---");
        if (enrollments.isEmpty()) {
            System.out.println("Discente não possui matrículas simuladas.");
            return;
        }

        enrollments.forEach(e -> System.out.printf("ID Matrícula Único: %s | ID Disciplina: %s\n",
                e.getKey(), e.getValue()));
    }

    private static void simulateCancellation(enrollmentService service, Scanner scanner) {
        System.out.print("Digite o ID do Discente: ");
        String studentId = scanner.nextLine();
        System.out.print("Digite o ID ÚNICO da Matrícula para cancelar (o que foi gerado): ");
        String enrollmentId = scanner.nextLine();

        enrollmentService.enrollmentResult result = service.simulateCancellation(studentId, enrollmentId);

        System.out.println("\n--- RESULTADO DO CANCELAMENTO ---");
        System.out.println("Status: " + (result.isSuccess() ? "SUCESSO" : "FALHA"));
        System.out.println("Mensagem: " + result.getMessage());
    }
}