package model.service.student;

import model.entity.student;
import model.repository.studentRepository;

import static model.service.student.enrollmentService.enrollmentResult;

public class studentOnboardingService {
    private final studentRepository studentRepository;
    private final enrollmentIdGenerator idGenerator;

    public studentOnboardingService(
            studentRepository studentRepository,
            enrollmentIdGenerator idGenerator) {
        this.studentRepository = studentRepository;
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

        String newEnrollmentId = idGenerator.generateEnrollmentId();

        student.setPrimaryEnrollmentId(newEnrollmentId);

        return new enrollmentResult(true, "ID de Matrícula Principal gerado com sucesso.", newEnrollmentId);
    }
}
