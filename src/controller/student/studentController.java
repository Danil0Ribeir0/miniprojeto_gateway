package controller.student;

import model.entity.student;
import model.repository.studentRepository;
import model.service.student.studentOnboardingService;
import model.service.student.studentService;
import model.service.subject.enrollmentService.enrollmentResult;

import java.util.List;

public class studentController {
    private final studentService readService;
    private final studentOnboardingService onboardingService;
    private final studentRepository studentRepository;

    public studentController(
            studentService readService,
            studentOnboardingService onboardingService,
            studentRepository studentRepository) {
        this.readService = readService;
        this.onboardingService = onboardingService;
        this.studentRepository = studentRepository;
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
