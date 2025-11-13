package model.entity;

public class book {
    private final String id;
    private final String title;
    private final String author;
    private final int year;
    private boolean isAvailable;

    public book(String id, String title, String author, int year, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isAvailable = "disponível".equalsIgnoreCase(status);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }

    public String getStatus() {
        return isAvailable ? "Disponível" : "Indisponível";
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void reserve() {
        if (isAvailable) {
            this.isAvailable = false;
        }
    }

    public void cancelReservation() {
        if (!isAvailable) {
            this.isAvailable = true;
        }
    }
}
