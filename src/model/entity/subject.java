package model.entity;

public class subject {
    private final String id;
    private final String course;
    private final String name;
    private final int totalSlots;

    public subject(String id, String course, String name, int totalSlots) {
        this.id = id;
        this.course = course;
        this.name = name;
        this.totalSlots = totalSlots;
    }

    public String getId() { return id; }

    public String getCourse() { return course; }

    public String getName() { return name; }

    public int getTotalSlots() { return totalSlots; }
}
