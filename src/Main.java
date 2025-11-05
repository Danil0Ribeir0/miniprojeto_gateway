import api.apiStudent;
import model.entity.student;
import model.repository.studentRepository;
import model.service.studentService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Camada de API (Acesso ao Microsserviço)
        System.out.println("Iniciando consumo da API de Discente...");

        apiStudent api = new apiStudent();

        // 2. Camada de Repositório (Popula o cache em memória)
        System.out.println("Populando Repositório de Discentes (cache in-memory)...");
        studentRepository repository = new studentRepository(api);


        System.out.println("Iniciando Camada de Serviço de Discentes...");
        studentService service = new studentService(repository);

        // Teste 1: Listar todos os discentes
        System.out.println("\n--- TESTE 1: LISTAR TODOS OS DISCENTES ---");
        List<student> allStudents = service.listAllStudents();

        if (allStudents.isEmpty()) {
            System.out.println("ERRO: Nenhuma lista de discente foi retornada. Verifique se a API está acessível ou se há erro de deserialização.");
        } else {
            System.out.println("Total de Discentes encontrados: " + allStudents.size());
            for (student s : allStudents) {
                // Consultar dados do discente (id, nome, curso, modalidade e status) [cite: 8]
                System.out.println("ID: " + s.getId() + " | Nome: " + s.getNome() + " | Curso: " + s.getCurso() + " | Status: " + s.getStatus());
            }
        }

        // Teste 2: Buscar um discente específico por ID (Exemplo com ID '1')
        System.out.println("\n--- TESTE 2: BUSCAR DISCENTE POR ID (ID: 1) ---");
        student student1 = service.getStudentById("1");

        if (student1 != null) {
            System.out.println("Discente encontrado: " + student1.getNome() + " | Curso: " + student1.getCurso() + " | Status: " + student1.getStatus());
            System.out.println("Está ativo? " + student1.isStatusActive());
        } else {
            System.out.println("Discente com ID '1' não encontrado no cache ou a lista inicial veio vazia.");
        }

        System.out.println("\nTeste de consumo da API Discente concluído.");
    }
}