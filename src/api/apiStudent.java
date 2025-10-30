package api;

import api.service.studentJsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import api.dto.studentDTO;

import java.util.List;

public class apiStudent extends apiBase<studentDTO> {
    private static final String URL_STUDENT = "https://rmi6vdpsq8.execute-api.us-east-2.amazonaws.com/msAluno";

    public apiStudent() { super(new studentJsonMapper()); }

    public String getBaseURL() { return URL_STUDENT; }

    protected TypeReference<List<studentDTO>> getTypeReference() { return new TypeReference<List<studentDTO>>() {}; }

    public List<studentDTO> getStudents() { return executeGetList(); }

    public studentDTO getStudentById(String id) {
        return getStudents().stream()
                .filter(studentDTO -> studentDTO.getId() != null && studentDTO.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
