package api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

public abstract class jsonObjectMapper<T> implements IJsonObjectMapper<T> {
    private final ObjectMapper objMapper = new ObjectMapper();
    protected abstract TypeReference<List<T>> getTypeReference();

    public List<T> mapJsonToList(String json) {
        try {
            return objMapper.readValue(
                    json,
                    getTypeReference()
            );
        } catch (Exception e) {
            System.err.println("Erro de deserialização JSON: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
