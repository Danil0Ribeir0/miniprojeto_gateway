package model.repository;

import api.apiStudent;
import api.dto.studentDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class studentRepository {
    private final List<studentDTO> students;

    public studentRepository(apiStudent apiStudent) {
        this.students = Optional.ofNullable(apiStudent.getStudents())
                .orElse(Collections.emptyList());
    }

    public List<studentDTO> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    public studentDTO getStudentById(String id) {
        if (id == null) return null;

        return students.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }
}
