package model.dto;

public class studentDTO {
    private String id;
    private String nome;
    private String curso;
    private String modalidade;
    private String status;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() { return curso; }

    public void setCurso(String curso) { this.curso = curso; }

    public String getModalidade() { return modalidade; }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "studentDTO {" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
