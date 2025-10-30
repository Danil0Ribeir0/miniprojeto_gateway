package api.service;

import model.dto.studentDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class studentJsonMapper extends jsonObjectMapper<studentDTO> {
    protected TypeReference<List<studentDTO>> getTypeReference() { return new TypeReference<List<studentDTO>>() {}; }
}
