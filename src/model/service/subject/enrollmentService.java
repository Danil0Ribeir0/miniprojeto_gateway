package model.service.subject;

import model.entity.student;
import model.entity.subject;
import model.repository.studentRepository;
import model.repository.subjectRepository;
import model.service.student.studentOnboardingService;
import model.service.student.studentService;

public class enrollmentService {
    private static final int MAX_SUBJECTS_PER_STUDENT = 5;
    private final studentRepository studentRepository;
    private final subjectRepository subjectRepository;
    private final enrollmentIdGenerator idGenerator;

    public enrollmentService() {
        this.studentRepository = new studentRepository();

        this.idGenerator = new enrollmentIdGenerator();

        this.subjectRepository = new subjectRepository();
    }

    public enrollmentResult simulateEnrollment(String studentId, String subjectId) {
        enrollmentResult failResult = new enrollmentResult(false, null, null);

        student student = studentRepository.getById(studentId);
        if (student == null) {
            return failResult.withMessage("Discente não encontrado.");
        }

        subject subject = subjectRepository.getById(subjectId);
        if (subject == null) {
            return failResult.withMessage("Disciplina não encontrada.");
        }

        if (!student.isStatusActive()) {
            return failResult.withMessage("Matrícula não permitida. Discente com status acadêmico: " + student.getStatus());
        }

        if (student.getEnrollmentCount() >= MAX_SUBJECTS_PER_STUDENT) {
            return failResult.withMessage("Matrícula não permitida. O discente já está matriculado em " + MAX_SUBJECTS_PER_STUDENT + " disciplinas.");
        }

        if (student.isEnrolledInSubject(subjectId)) {
            return failResult.withMessage("Matrícula não permitida. O discente já está matriculado nesta disciplina.");
        }

        if (!student.getCurso().equals(subject.getCourse())) {
            return failResult.withMessage("Matrícula não permitida. A disciplina '" + subject.getName() + "' pertence ao curso '" + subject.getCourse() + "', diferente do curso do discente ('" + student.getCurso() + "').");
        }

        if (!subject.hasAvailableSlots()) {
            return failResult.withMessage("Matrícula não permitida. A disciplina '" + subject.getName() + "' não possui vagas disponíveis (0 vagas).");
        }

        String newEnrollmentId = idGenerator.generateEnrollmentId();

        student.addEnrollment(newEnrollmentId, subjectId);

        subject.decrementSlot();

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

        subject subject = subjectRepository.getById(subjectId);
        if (subject != null) {
            subject.incrementSlot();
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
