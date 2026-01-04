package br.com.amparoedu.backend.model;

public class Educando {
    private String id;
    private String turma_id;
    private String endereco_id;
    private String nome;
    private String cpf;
    private String data_nascimento;
    private String genero;
    private String grau_ensino;
    private String cid;
    private String nis;
    private String escola;
    private String observacoes;
    private int sincronizado;
    private int excluido;

    public Educando() {
    }

    public Educando(String id, String turma_id, String endereco_id, String nome, String cpf, String data_nascimento, String genero, String grau_ensino, String cid, String nis, String escola, String observacoes, int sincronizado, int excluido) {
        this.id = id;
        this.turma_id = turma_id;
        this.endereco_id = endereco_id;
        this.nome = nome;
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
        this.genero = genero;
        this.grau_ensino = grau_ensino;
        this.cid = cid;
        this.nis = nis;
        this.escola = escola;
        this.observacoes = observacoes;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    public String getId() {
        return id;
    }

    public String getTurma_id() {
        return turma_id;
    }

    public String getEndereco_id() {
        return endereco_id;
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

    public String getGrau_ensino() {
        return grau_ensino;
    }

    public String getCid() {
        return cid;
    }

    public String getNis() {
        return nis;
    }

    public String getEscola() {
        return escola;
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

    public void setId(String id) {
        this.id = id;
    }
    public void setTurma_id(String turma_id) {
        this.turma_id = turma_id;
    }
    public void setEndereco_id(String endereco_id) {
        this.endereco_id = endereco_id;
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
    public void setGrau_ensino(String grau_ensino) {
        this.grau_ensino = grau_ensino;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public void setNis(String nis) {
        this.nis = nis;
    }
    public void setEscola(String escola) {
        this.escola = escola;
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
}