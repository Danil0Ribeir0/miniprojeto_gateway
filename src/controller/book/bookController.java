package controller.book;

import model.entity.book;
import model.service.book.bookReservationService;
import model.service.book.bookReservationService.ReservationResult;
import model.service.book.bookService;

import java.util.List;

public class bookController {
    private final bookService readService;
    private final bookReservationService reservationService;

    public bookController() {
        this.readService = new bookService();
        this.reservationService = new bookReservationService();
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
}