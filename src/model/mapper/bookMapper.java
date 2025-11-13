package model.mapper;

import model.entity.book;
import api.dto.bookDTO;

public class bookMapper {
    public book toEntity(bookDTO dto) {
        if (dto == null) {
            return null;
        }

        return new book(
                dto.getId(),
                dto.getTitulo(),
                dto.getAutor(),
                dto.getAno(),
                dto.getStatus()
        );
    }
}
