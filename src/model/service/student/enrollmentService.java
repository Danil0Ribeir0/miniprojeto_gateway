package model.service.student;

import model.entity.student;
import model.entity.subject;
import model.repository.studentRepository;

public class enrollmentService {
    private static final int MAX_SUBJECTS_PER_STUDENT = 5;
    private final studentRepository studentRepository;
    private final enrollmentIdGenerator idGenerator;

    public enrollmentService(
            studentRepository studentRepository,
            enrollmentIdGenerator idGenerator) {
        this.studentRepository = studentRepository;
        this.idGenerator = idGenerator;
    }


}
