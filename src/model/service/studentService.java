package model.service;

import model.entity.student;
import model.repository.studentRepository;

import java.util.Collections;
import java.util.List;

public class studentService {
    private final studentRepository repository;

    public studentService(studentRepository repository) {
        this.repository = repository;
    }

    public List<student> listAllStudents() {
        List<student> students = repository.getAllStudents();

        if (students.isEmpty()) {
            return Collections.emptyList();
        }

        return students;
    }

    public student getStudentById(String id) {
        return repository.getStudentById(id);
    }
}
