package model.service.student;

import model.entity.student;
import model.entity.subject;
import model.repository.studentRepository;
import model.repository.subjectRepository;

public class enrollmentService {
    private static final int MAX_SUBJECTS_PER_STUDENT = 5;
    private final studentRepository studentRepository;
    private final subjectRepository subjectRepository;
    private final enrollmentIdGenerator idGenerator;

    public enrollmentService(
            studentRepository studentRepository,
            subjectRepository subjectRepository,
            enrollmentIdGenerator idGenerator) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.idGenerator = idGenerator;
    }

    public enrollmentResult registerStudentEnrollmentId(String studentId) {
        student student = studentRepository.getById(studentId);
        enrollmentResult failResult = new enrollmentResult(false, null, null);

        if (student == null) {
            return failResult.withMessage("Discente não encontrado.");
        }

        if (student.getPrimaryEnrollmentId() != null) {
            return failResult.withMessage("Discente já possui o ID de Matrícula: " + student.getPrimaryEnrollmentId());
        }

        // 1. Gera o ID único
        String newEnrollmentId = idGenerator.generateEnrollmentId();

        // 2. Armazena no campo da entidade (persistência volátil)
        student.setPrimaryEnrollmentId(newEnrollmentId);

        return new enrollmentResult(true, "ID de Matrícula Principal gerado com sucesso.", newEnrollmentId);
    }

    public enrollmentResult simulateEnrollment(String studentId, String subjectId) {
        enrollmentResult failResult = new enrollmentResult(false, null, null);

        student student = studentRepository.getById(studentId);
        if (student == null) {
            return failResult.withMessage("Discente não encontrado.");
        }

        subject subject = (subject) subjectRepository.getById(subjectId);
        if (subject == null) {
            return failResult.withMessage("Disciplina não encontrada.");
        }

        // --- Checagem das Regras de Negócio (Requisitos do Mini Projeto) ---

        // Regra 1a: Situação acadêmica não trancada
        if (!student.isStatusActive()) {
            return failResult.withMessage("Matrícula não permitida. Discente com status acadêmico: " + student.getStatus());
        }

        // Regra 1b: Máximo de 5 disciplinas simultâneas
        if (student.getEnrollmentCount() >= MAX_SUBJECTS_PER_STUDENT) {
            return failResult.withMessage("Matrícula não permitida. O discente já está matriculado em " + MAX_SUBJECTS_PER_STUDENT + " disciplinas.");
        }

        // Verifica duplicidade (já matriculado na mesma disciplina)
        if (student.isEnrolledInSubject(subjectId)) {
            return failResult.withMessage("Matrícula não permitida. O discente já está matriculado nesta disciplina.");
        }

        // Regra 1c: Disciplinas pertencem ao curso do discente
        if (!student.getCurso().equals(subject.getCourse())) {
            return failResult.withMessage("Matrícula não permitida. A disciplina '" + subject.getName() + "' pertence ao curso '" + subject.getCourse() + "', diferente do curso do discente ('" + student.getCurso() + "').");
        }

        // Regra 2: Disciplina sem vagas
        // Nota: A checagem de vagas foi omitida para simplificar a persistência volátil,
        // mas a regra existe no documento. Caso seja implementado um controle global
        // de vagas, esta lógica deve ser inserida aqui.

        // --- Execução da Simulação ---
        String newEnrollmentId = idGenerator.generateEnrollmentId();

        // Persistência local (Coesão): Delega a mudança de estado para o student
        student.addEnrollment(newEnrollmentId, subjectId);

        // Retorna o ID único em caso de sucesso
        return new enrollmentResult(true, "Matrícula simulada com sucesso na disciplina '" + subject.getName() + "'. ID: " + newEnrollmentId, newEnrollmentId);
    }

    public enrollmentResult simulateCancellation(String studentId, String enrollmentId) {
        student student = studentRepository.getById(studentId);

        if (student == null) {
            return new enrollmentResult(false, "Discente não encontrado.", null);
        }

        // Delega a remoção ao student. Retorna o subjectId se for bem-sucedido.
        String subjectId = student.removeEnrollment(enrollmentId);

        if (subjectId == null) {
            return new enrollmentResult(false, "Matrícula com ID " + enrollmentId + " não encontrada para este discente.", null);
        }

        return new enrollmentResult(true, "Cancelamento da matrícula " + enrollmentId + " simulado com sucesso.", enrollmentId);
    }

    public static class enrollmentResult {
        private final boolean success;
        private final String message;
        private final String enrollmentId;

        public enrollmentResult(boolean success, String message, String enrollmentId) {
            this.success = success;
            this.message = message;
            this.enrollmentId = enrollmentId;
        }

        public enrollmentResult withMessage(String message) {
            return new enrollmentResult(this.success, message, this.enrollmentId);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getEnrollmentId() {
            return enrollmentId;
        }
    }
}
