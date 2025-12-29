package br.com.amparoedu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import br.com.amparoedu.backend.model.Turma;

public class CardTurmaController {

    @FXML private Label nomeTurmaCard;
    @FXML private Label totalAlunoCard;
    @FXML private Label faixaEtariaCard;
    @FXML private Label grauTurmaCard;
    @FXML private Label turnoTurmaCard;

    // Preenche o card com os dados do banco
    public void configurarDados(Turma turma) {
        nomeTurmaCard.setText(turma.getNome());
        faixaEtariaCard.setText("Faixa etária: " + turma.getFaixa_etaria());
        grauTurmaCard.setText("Grau da turma: " + turma.getGrau_ensino());
        turnoTurmaCard.setText("Turno: " + turma.getTurno());
        totalAlunoCard.setText("Total de alunos: --");
    }

    @FXML
    private void handleVerificarTurmaAction() {
        System.out.println("Ação para verificar detalhes da turma selecionada.");
    }
}