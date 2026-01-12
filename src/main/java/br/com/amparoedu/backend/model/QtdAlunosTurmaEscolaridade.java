package br.com.amparoedu.backend.model;

public class QtdAlunosTurmaEscolaridade {
    private String escolaridade;
    private String nomeTurma;
    private int quantidadeAlunos;

    public QtdAlunosTurmaEscolaridade() {}

    public QtdAlunosTurmaEscolaridade(String escolaridade, String nomeTurma, int quantidadeAlunos) {
        this.escolaridade = escolaridade;
        this.nomeTurma = nomeTurma;
        this.quantidadeAlunos = quantidadeAlunos;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public int getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public void setQuantidadeAlunos(int quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }
}