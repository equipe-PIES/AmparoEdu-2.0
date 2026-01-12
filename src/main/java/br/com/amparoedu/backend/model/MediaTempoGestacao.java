package br.com.amparoedu.backend.model;

public class MediaTempoGestacao {
    private String cidadeNascimento;
    private double mediaTempoGestacao;
    private String tipoParto;
    private String nomeTurma;

    public MediaTempoGestacao() {}

    public MediaTempoGestacao(String cidadeNascimento, double mediaTempoGestacao, String tipoParto, String nomeTurma) {
        this.cidadeNascimento = cidadeNascimento;
        this.mediaTempoGestacao = mediaTempoGestacao;
        this.tipoParto = tipoParto;
        this.nomeTurma = nomeTurma;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public double getMediaTempoGestacao() {
        return mediaTempoGestacao;
    }

    public void setMediaTempoGestacao(double mediaTempoGestacao) {
        this.mediaTempoGestacao = mediaTempoGestacao;
    }

    public String getTipoParto() {
        return tipoParto;
    }

    public void setTipoParto(String tipoParto) {
        this.tipoParto = tipoParto;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }
}
