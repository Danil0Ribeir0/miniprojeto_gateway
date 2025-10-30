package model.mapper;

import model.entity.student;
import api.dto.studentDTO;

public class studentMapper {
    public student toEntity(studentDTO dto) {
        if (dto == null) {
            return null;
        }

        return new student(
                dto.getId(),
                dto.getNome(),
                dto.getCurso(),
                dto.getModalidade(),
                dto.getStatus()
        );
    }
}
