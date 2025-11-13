package model.service.book;

import model.entity.book;
import model.repository.bookRepository;

import java.util.Collections;
import java.util.List;

public class bookService {
    private final bookRepository repository;

    public bookService() {
        this.repository = new bookRepository();
    }

    public List<book> listAllBooks() {
        List<book> books = repository.getAll();

        if (books.isEmpty()) {
            return Collections.emptyList();
        }

        return books;
    }

    public book getBookById(String id) {
        return repository.getById(id);
    }
}
