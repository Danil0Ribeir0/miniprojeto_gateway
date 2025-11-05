package model.repository;

import java.util.Collections;
import java.util.List;

public abstract class baseRepository<T> implements IRepository {
    protected final List<T> entities;

    protected baseRepository(List<T> intialDTOs) {
        this.entities = intialDTOs;
    }

    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(entities);
    }
}
