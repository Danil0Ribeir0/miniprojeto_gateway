package model.repository;

import api.apiSubject;
import model.entity.subject;
import model.mapper.subjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class subjectRepository extends baseRepository<subject> {
    private static final subjectMapper MAPPER = new subjectMapper();

    public subjectRepository(apiSubject apiSubject) { super(loadAndMapSubjects(apiSubject)); }

    private static List<subject> loadAndMapSubjects(apiSubject apiSubject) {
        List<api.dto.subjectDTO> dtos = apiSubject.getSubject();

        return dtos.stream()
                .map(MAPPER :: toEntity)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public subject getById(String id) {
        if (id == null) return null;

        return entities.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }
}
