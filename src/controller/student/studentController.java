package controller.student;

import model.entity.student;
import model.repository.studentRepository;
import model.service.student.studentOnboardingService;
import model.service.student.studentService;
import model.service.subject.enrollmentService.enrollmentResult;
import model.service.subject.enrollmentIdGenerator;

import java.util.List;

public class studentController {
    private final studentService readService;
    private final studentOnboardingService onboardingService;
    private final studentRepository studentRepository;

    public studentController() {
        this.studentRepository = new studentRepository();

        enrollmentIdGenerator idGenerator = new enrollmentIdGenerator();

        this.readService = new studentService();
        this.onboardingService = new studentOnboardingService();
    }

    public List<student> listAllStudents() {
        return readService.listAllStudents();
    }

    public student getStudentDetails(String id) {
        return readService.getStudentById(id);
    }

    public enrollmentResult registerPrimaryEnrollmentId(String studentId) {
        return onboardingService.registerStudentEnrollmentId(studentId);
    }

    public student getStudentForEnrollmentsDisplay(String studentId) {
        return studentRepository.getById(studentId);
    }
}
