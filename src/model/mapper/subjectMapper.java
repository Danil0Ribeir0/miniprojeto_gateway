package model.mapper;

import model.entity.subject;
import api.dto.subjectDTO;

public class subjectMapper {
    public subject toEntity(subjectDTO dto) {
        if (dto == null) {
            return null;
        }

        // subjectDTO usa 'slots' e subject usa 'totalSlots'.
        return new subject(
                dto.getId(),
                dto.getCourse(),
                dto.getName(),
                dto.getSlots()
        );
    }
}
