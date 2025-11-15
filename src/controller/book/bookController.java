package controller.book;

import model.entity.book;
import model.repository.bookRepository;
import model.repository.studentRepository;
import model.service.book.bookReservationService;
import model.service.book.bookReservationService.ReservationResult;
import model.service.book.bookService;
import api.apiBase;

import java.util.List;

public class bookController {
    private final bookService readService;
    private final bookReservationService reservationService;

    public bookController() {
        // CORREÇÃO: Criação ÚNICA dos repositórios
        bookRepository bookRepository = new bookRepository();
        studentRepository studentRepository = new studentRepository();

        // INJEÇÃO: Passa as instâncias únicas para os serviços.
        this.readService = new bookService(bookRepository);
        this.reservationService = new bookReservationService(studentRepository, bookRepository);
    }

    public List<book> listAllBooks() {
        return readService.listAllBooks();
    }

    public ReservationResult simulateReservation(String studentId, String bookId) {
        return reservationService.simulateReservation(studentId, bookId);
    }

    public ReservationResult simulateCancellation(String studentId, String bookId) {
        return reservationService.simulateCancellation(studentId, bookId);
    }

    public String getLastApiMessage() {
        return apiBase.getLastApiMessage();
    }
}