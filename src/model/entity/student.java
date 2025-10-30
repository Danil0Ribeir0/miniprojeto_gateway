package model.entity;

public class student {
    private final String id;
    private final String nome;
    private final String curso;
    private final String modalidade;

    private String status;

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

    public void setStatus(String status) { this.status = status; }

    public boolean isStatusActive() {
        return "ativo".equalsIgnoreCase(this.status);
    }
}
