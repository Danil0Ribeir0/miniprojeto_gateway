package api;

import api.dto.subjectDTO;
import api.service.subjectJsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class apiSubject extends apiBase<subjectDTO> {
    private static final String URL_SUBJECT = "https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina";

    public apiSubject() { super(new subjectJsonMapper()); }

    public String getBaseURL() { return URL_SUBJECT; }

    protected TypeReference<List<subjectDTO>> getTypeReference() { return new TypeReference<List<subjectDTO>>() {}; }

    public List<subjectDTO> getSubject() { return executeGetList(); }

    public subjectDTO getSubjectById(String id) {
        return getSubject().stream()
                .filter(subjectDTO -> subjectDTO.getId() != null && subjectDTO.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
