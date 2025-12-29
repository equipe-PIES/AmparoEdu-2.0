package br.com.amparoedu.backend.model;

public class TurmaEducando {
    private String turma_id;
    private String educando_id;
    private int sincronizado;
    private int excluido;

    public TurmaEducando(String turma_id, String educando_id, int sincronizado, int excluido) {
        this.turma_id = turma_id;
        this.educando_id = educando_id;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }
    
    public String getTurma_id() {
        return turma_id;
    }
    public String getEducando_id() {
        return educando_id;
    }
    public int getSincronizado() {
        return sincronizado;
    }
     public int getExcluido() {
        return excluido;
    }

    public void setTurma_id(String turma_id) {
        this.turma_id = turma_id;
    }
    public void setEducando_id(String educando_id) {
        this.educando_id = educando_id;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
}
