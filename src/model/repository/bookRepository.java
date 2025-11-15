package model.repository;

import api.apiBook;
import model.entity.book;
import model.mapper.bookMapper;

import java.util.List;
import java.util.stream.Collectors;

public class bookRepository extends baseRepository<book>{
    private static bookRepository INSTANCE = null;
    private static final bookMapper MAPPER = new bookMapper();

    private bookRepository() {
        super(loadAndMapBooks(new apiBook()));
    }

    public static synchronized bookRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new bookRepository();
        }
        return INSTANCE;
    }

    private static List<book> loadAndMapBooks(apiBook apiBook) {
        List<api.dto.bookDTO> dtos = apiBook.getBooks();

        return dtos.stream()
                .map(MAPPER :: toEntity)
                .filter(b -> b != null)
                .collect(Collectors.toList());
    }

    public book getById(String id) {
        if (id == null) return null;

        return entities.stream()
                .filter(b -> id.equals(b.getId()))
                .findFirst()
                .orElse(null);
    }
}
