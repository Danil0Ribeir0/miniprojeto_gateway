package model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class student {
    private final String id;
    private final String nome;
    private final String curso;
    private final String modalidade;

    private String status;
    private String primaryEnrollmentId;

    private final Map<String, String> enrollments = new ConcurrentHashMap<>();

    public student(String id, String nome, String curso, String modalidade, String status) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.modalidade = modalidade;
        this.status = status;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCurso() { return curso; }
    public String getModalidade() { return modalidade; }
    public String getStatus() { return status; }
    public String getPrimaryEnrollmentId() { return primaryEnrollmentId; }

    public void setPrimaryEnrollmentId(String primaryEnrollmentId) {
        this.primaryEnrollmentId = primaryEnrollmentId;
    }

    public void setStatus(String status) { this.status = status; }

    public boolean isStatusActive() {
        return "ativo".equalsIgnoreCase(this.status);
    }

    public void addEnrollment(String enrollmentId, String subjectId) {
        this.enrollments.put(enrollmentId, subjectId);
    }

    public String removeEnrollment(String enrollmentId) {
        return this.enrollments.remove(enrollmentId);
    }

    public int getEnrollmentCount() {
        return this.enrollments.size();
    }

    public boolean isEnrolledInSubject(String subjectId) {
        return this.enrollments.containsValue(subjectId);
    }

    public List<Map.Entry<String, String>> getEnrollments() {
        return new ArrayList<>(this.enrollments.entrySet());
    }
}
