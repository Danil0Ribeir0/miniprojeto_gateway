package model.service.book;

import model.entity.student;
import model.entity.book;
import model.repository.studentRepository;
import model.repository.bookRepository;

public class bookReservationService {
    private final studentRepository studentRepository;
    private final bookRepository bookRepository;

    public bookReservationService() {
        this.studentRepository = new studentRepository();
        this.bookRepository = new bookRepository();
    }

    public ReservationResult simulateReservation(String studentId, String bookId) {
        ReservationResult failResult = new ReservationResult(false, null, null);

        student student = studentRepository.getById(studentId);
        if (student == null) {
            return failResult.withMessage("Discente não encontrado.");
        }

        if (student.getPrimaryEnrollmentId() == null) {
            return failResult.withMessage("Reserva não permitida. O discente deve possuir um ID de Matrícula Principal gerado.");
        }

        book book = bookRepository.getById(bookId);
        if (book == null) {
            return failResult.withMessage("Livro não encontrado no acervo.");
        }

        if (!book.isAvailable()) {
            return failResult.withMessage("Reserva não permitida. O livro '" + book.getTitle() + "' já está indisponível/reservado.");
        }

        if (student.hasBookReservation(bookId)) {
            return failResult.withMessage("Reserva não permitida. O discente já possui reserva para este livro.");
        }

        // --- Execução da Simulação ---

        String uniqueId = student.getPrimaryEnrollmentId();

        // 1. Altera o estado volátil do Livro.
        book.reserve();

        // 2. Altera o estado volátil do Discente (rastreia a reserva).
        student.addBookReservation(uniqueId, bookId);

        return new ReservationResult(true, "Reserva simulada com sucesso para o livro '" + book.getTitle() + "'. ID do Discente: " + uniqueId, bookId);
    }

    public ReservationResult simulateCancellation(String studentId, String bookId) {
        ReservationResult failResult = new ReservationResult(false, null, null);

        student student = studentRepository.getById(studentId);
        if (student == null) {
            return failResult.withMessage("Discente não encontrado.");
        }

        if (!student.hasBookReservation(bookId)) {
            return failResult.withMessage("Cancelamento não permitido. O discente não possui reserva para o livro ID: " + bookId);
        }

        book book = bookRepository.getById(bookId);

        // Remove a reserva do discente
        student.removeBookReservation(bookId);

        // Altera o estado do livro (devolve ao acervo)
        if (book != null) {
            book.cancelReservation();
        }

        return new ReservationResult(true, "Cancelamento da reserva do livro ID " + bookId + " simulado com sucesso.", bookId);
    }

    public static class ReservationResult {
        private final boolean success;
        private final String message;
        private final String bookId;

        public ReservationResult(boolean success, String message, String bookId) {
            this.success = success;
            this.message = message;
            this.bookId = bookId;
        }

        public ReservationResult withMessage(String message) {
            return new ReservationResult(this.success, message, this.bookId);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getBookId() {
            return bookId;
        }
    }
}
