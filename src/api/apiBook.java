package api;

import api.dto.bookDTO;
import api.service.bookJsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class apiBook extends apiBase<bookDTO> {
    private static final String URL_BOOK = "https://qiiw8bgxka.execute-api.us-east-2.amazonaws.com/acervo/biblioteca";

    public apiBook() { super(new bookJsonMapper()); }

    public String getBaseURL() { return URL_BOOK; }

    protected TypeReference<List<bookDTO>> getTypeReference() { return new TypeReference<List<bookDTO>>() {}; }

    public List<bookDTO> getBooks() { return executeGetList(); }

    public bookDTO getBookById(String id) {
        return getBooks().stream()
                .filter(bookDTO -> bookDTO.getId() != null && bookDTO.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
