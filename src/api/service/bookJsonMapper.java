package api.service;

import api.dto.bookDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class bookJsonMapper extends jsonObjectMapper<bookDTO> {
    protected TypeReference<List<bookDTO>> getTypeReference() { return new TypeReference<List<bookDTO>>() {}; }
}
