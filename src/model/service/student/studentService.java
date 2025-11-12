package model.service.student;

import model.entity.student;
import model.repository.studentRepository; // Depende da classe concreta
import model.repository.IRepository;     // Depende da abstração, mas aqui usa a concreta para injeção

import java.util.Collections;
import java.util.List;


public class studentService {
    // O atributo pode ser a interface IRepository<student> para maior DIP,
    // mas usaremos studentRepository para manter a consistência do construtor original.
    private final studentRepository repository;

    public studentService(studentRepository repository) {
        this.repository = repository;
    }

    public List<student> listAllStudents() {
        // Usa o método getAll() definido na IRepository e implementado na baseRepository
        List<student> students = repository.getAll();

        if (students.isEmpty()) {
            return Collections.emptyList();
        }

        return students;
    }

    public student getStudentById(String id) {
        // Usa o método getById(id) definido na IRepository
        return repository.getById(id);
    }
}