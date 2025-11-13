package model.repository;

import api.apiStudent;
import model.entity.student;
import model.mapper.studentMapper;

import java.util.List;
import java.util.stream.Collectors;

public class studentRepository extends baseRepository<student> {

    private static final studentMapper MAPPER = new studentMapper();

    public studentRepository() {
        super(loadAndMapStudents(new apiStudent()));
    }

    private static List<student> loadAndMapStudents(apiStudent apiStudent) {
        List<api.dto.studentDTO> dtos = apiStudent.getStudents();

        return dtos.stream()
                .map(MAPPER :: toEntity)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public student getById(String id) {
        if (id == null) return null;

        return entities.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }
}