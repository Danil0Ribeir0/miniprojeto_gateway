package api.dto;

public class subjectDTO {
    private String id;
    private String curso; // Corresponde ao JSON
    private String nome;  // ALTERADO: De 'name' para 'nome'
    private int vagas;    // ALTERADO: De 'slots' para 'vagas'

    public String getId() { return id; }
    public String getCurso() { return curso; }
    public String getNome() { return nome; } // Getter alterado
    public int getVagas() { return vagas; }  // Getter alterado

    public void setId(String id) { this.id = id; }
    public void setCurso(String curso) { this.curso = curso; }
    public void setNome(String nome) { this.nome = nome; } // Setter alterado
    public void setVagas(int vagas) { this.vagas = vagas; } // Setter alterado

    @Override
    public String toString() {
        return "DisciplineDTO {" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                ", vagas=" + vagas +
                '}';
    }
}
