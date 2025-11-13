package api.dto;

public class bookDTO {
    private String id;
    private String titulo;
    private String autor;
    private int ano;
    private String status;

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAno() { return ano; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setAno(int ano) { this.ano = ano; }
    public void setStatus(String status) { this.status = status; }

    public String toString() {
        return "BookDTO {" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", ano=" + ano +
                ", status='" + status + '\'' +
                '}';
    }
}
