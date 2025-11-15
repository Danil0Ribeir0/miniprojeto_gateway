package model.service.student;

import model.entity.student;
import model.repository.studentRepository;
import model.service.subject.enrollmentIdGenerator;

import static model.service.subject.enrollmentService.enrollmentResult;

public class studentOnboardingService {
    private final studentRepository studentRepository;
    private final enrollmentIdGenerator idGenerator;

    public studentOnboardingService(studentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.idGenerator = new enrollmentIdGenerator();
    }

    public enrollmentResult registerStudentEnrollmentId(String studentId) {
        student student = studentRepository.getById(studentId);
        enrollmentResult failResult = new enrollmentResult(false, null, null);

        if (student == null) {
            return failResult.withMessage("Discente não encontrado.");
        }

        if (!student.isStatusActive()) {
            return failResult.withMessage("Geração de ID não permitida. Discente com status acadêmico: " + student.getStatus());
        }

        if (student.getPrimaryEnrollmentId() != null) {
            return failResult.withMessage("Discente já possui o ID de Matrícula: " + student.getPrimaryEnrollmentId());
        }

        String newEnrollmentId = idGenerator.generateEnrollmentId();

        student.setPrimaryEnrollmentId(newEnrollmentId);

        return new enrollmentResult(true, "ID de Matrícula Principal gerado com sucesso.", newEnrollmentId);
    }
}
