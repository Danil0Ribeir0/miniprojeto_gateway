package model.service.student;

import model.entity.student;
import model.repository.studentRepository;
import model.repository.IRepository;

import java.util.Collections;
import java.util.List;


public class studentService {
    private final studentRepository repository;

    public studentService(studentRepository repository) {
        this.repository = repository;
    }

    public List<student> listAllStudents() {
        List<student> students = repository.getAll();

        if (students.isEmpty()) {
            return Collections.emptyList();
        }

        return students;
    }

    public student getStudentById(String id) {
        return repository.getById(id);
    }
}