package controller.subject;

import model.entity.subject;
import model.repository.subjectRepository;
import model.service.subject.enrollmentService;
import model.service.subject.enrollmentService.enrollmentResult;

import java.util.List;

public class subjectController {
    private final subjectRepository subjectRepository;
    private final enrollmentService enrollmentService;

    public subjectController() {
        this.subjectRepository = new subjectRepository();
        this.enrollmentService = new enrollmentService();
    }

    public List<subject> listAllSubjects() {
        return subjectRepository.getAll();
    }

    public enrollmentResult simulateEnrollment(String studentId, String subjectId) {
        return enrollmentService.simulateEnrollment(studentId, subjectId);
    }

    public enrollmentResult simulateCancellation(String studentId, String enrollmentId) {
        return enrollmentService.simulateCancellation(studentId, enrollmentId);
    }
}
