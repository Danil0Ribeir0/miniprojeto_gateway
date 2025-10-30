package model.repository;

import api.apiStudent;
import model.entity.student;
import model.mapper.studentMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class studentRepository {
    private final List<student> students;
    private final studentMapper mapper = new studentMapper();

    public studentRepository(apiStudent apiStudent) {
        List<api.dto.studentDTO> dtos = apiStudent.getStudents();

        this.students = dtos.stream()
                .map(mapper :: toEntity)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public List<student> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    public student getStudentById(String id) {
        if (id == null) return null;

        return students.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }
}
