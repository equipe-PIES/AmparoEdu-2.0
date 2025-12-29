package br.com.amparoedu.backend.model;

public class Responsavel {
    private String id;
    private String educando_id;
    private String nome;
    private String cpf;
    private String parentesco;
    private String telefone;
    private int sincronizado;
    private int excluido;

    public Responsavel() {
    }

    public Responsavel(String id, String educando_id, String nome, String cpf, String parentesco, String telefone, int sincronizado, int excluido) {
        this.id = id;
        this.educando_id = educando_id;
        this.nome = nome;
        this.cpf = cpf;
        this.parentesco = parentesco;
        this.telefone = telefone;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    public String getId() {
        return id;
    }

    public String getEducando_id() {
        return educando_id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getParentesco() {
        return parentesco;
    }

    public String getTelefone() {
        return telefone;
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

    public void setEducando_id(String educando_id) {
        this.educando_id = educando_id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }

    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
}