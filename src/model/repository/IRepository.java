package model.repository;

import java.util.List;

public interface IRepository<T> {
    List<T> getAll();

    T getById(String id);
}
