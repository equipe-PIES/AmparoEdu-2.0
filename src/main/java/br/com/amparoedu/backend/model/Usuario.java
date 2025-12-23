package br.com.amparoedu.backend.model;

public class Usuario {
    private String id;
    private String email;
    private String senha;
    private String tipo; // "COORDENADOR", "PROFESSOR"
    private int sincronizado;
    private int excluido;


    public Usuario() {
    }


    public Usuario(String id, String email, String senha, String tipo, int sincronizado, int excluido) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    public String getTipo() {
        return tipo;
    }
    public int getSincronizado() {
        return sincronizado;
    }
    public int getExcluido() {
        return excluido;
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }


    public boolean isCoordenador() {
        return "COORDENADOR".equalsIgnoreCase(this.tipo);
    }
}