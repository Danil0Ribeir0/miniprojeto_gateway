package api.dto;

public class subjectDTO {
    private String id;
    private String course;
    private String name;
    private int slots;

    public String getId() { return id; }
    public String getCourse() { return course; }
    public String getName() { return name; }
    public int getSlots() { return slots; }

    public void setId(String id) { this.id = id; }
    public void setCourse(String course) { this.course = course; }
    public void setName(String name) { this.name = name; }
    public void setSlots(int slots) { this.slots = slots; }

    @Override
    public String toString() {
        return "DisciplineDTO {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", slots=" + slots +
                '}';
    }
}
