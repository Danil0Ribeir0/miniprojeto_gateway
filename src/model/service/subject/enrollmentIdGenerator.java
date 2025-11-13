package model.service.subject;

import java.time.LocalDate;
import java.util.Random;

public class enrollmentIdGenerator {
    private final Random random = new Random();

    public String generateEnrollmentId() {
        LocalDate today = LocalDate.now();

        int year = today.getYear() % 100;

        int semester = getAcademicSemester(today);

        String yearPart = String.format("%02d", year);
        String semesterPart = String.valueOf(semester);
        String randomPart = String.format("%02d", random.nextInt(100));

        return yearPart + semesterPart + randomPart;
    }

    private int getAcademicSemester(LocalDate date) {
        return (date.getMonthValue() >= 7) ? 2 : 1;
    }
}
