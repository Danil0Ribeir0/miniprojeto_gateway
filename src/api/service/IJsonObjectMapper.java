package api.service;

import java.util.List;

public interface IJsonObjectMapper<T> {
    List<T> mapJsonToList(String json);
}
