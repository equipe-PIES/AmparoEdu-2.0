package br.com.amparoedu.backend.model;


public class Professor {
    private String id;
    private String nome;
    private String cpf;
    private String data_nascimento;
    private String genero;
    private String observacoes;
    private int sincronizado;
    private int excluido;
    private String usuario_id;

    public Professor() {
    }

    public Professor(String id, String nome, String cpf, String data_nascimento, String genero, String observacoes, int sincronizado, int excluido, String usuario_id) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
        this.genero = genero;
        this.observacoes = observacoes;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
        this.usuario_id = usuario_id;
    }

    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getData_nascimento() {
        return data_nascimento;
    }
    public String getGenero() {
        return genero;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public int getSincronizado() {
        return sincronizado;
    }
    public int getExcluido() {
        return excluido;
    }
    public String getUsuario_id() {
        return usuario_id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

}
