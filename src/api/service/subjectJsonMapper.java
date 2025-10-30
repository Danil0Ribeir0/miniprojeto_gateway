package api.service;

import api.dto.subjectDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class subjectJsonMapper extends jsonObjectMapper<subjectDTO> {
    protected TypeReference<List<subjectDTO>> getTypeReference() { return new TypeReference<List<subjectDTO>>() {}; }
}
