package br.com.amparoedu.backend.model;

public class Turma {
    private String id;
    private String professor_id;
    private String nome;
    private String turno;
    private String grau_ensino;
    private String faixa_etaria;
    private int sincronizado;
    private int excluido;
    

    public Turma() {
    }

    public Turma(String id, String professor_id, String nome, String faixa_etaria, String grau_ensino, String turno, int sincronizado, int excluido) {
        this.id = id;
        this.professor_id = professor_id;
        this.nome = nome;
        this.turno = turno;
        this.grau_ensino = grau_ensino;
        this.faixa_etaria = faixa_etaria;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    public String getId() {
        return id;
    }
    public String getProfessor_id() {
        return professor_id;
    }
    public String getNome() {
        return nome;
    }
    public String getTurno() {
        return turno;
    }
    public String getGrau_ensino() {
        return grau_ensino;
    }
    public String getFaixa_etaria() {
        return faixa_etaria;
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
    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTurno(String turno) {
        this.turno = turno;
    }
    public void setGrau_ensino(String grau_ensino) {
        this.grau_ensino = grau_ensino;
    }
    public void setFaixa_etaria(String faixa_etaria) {
        this.faixa_etaria = faixa_etaria;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }

}
