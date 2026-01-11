package br.com.amparoedu.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.view.GerenciadorTelas;


public class CardTurmaController {

    @FXML private Label nomeTurmaCard;
    @FXML private Label totalAlunoCard;
    @FXML private Label faixaEtariaCard;
    @FXML private Label grauTurmaCard;
    @FXML private Label turnoTurmaCard;    
    private Turma turma;

    // Preenche o card com os dados do banco
    public void configurarDados(Turma turma) {
        this.turma = turma;
        nomeTurmaCard.setText(turma.getNome());
        faixaEtariaCard.setText("Faixa etária: " + turma.getFaixa_etaria());
        grauTurmaCard.setText("Grau da turma: " + turma.getGrau_ensino());
        turnoTurmaCard.setText("Turno: " + turma.getTurno());
        totalAlunoCard.setText("Total de alunos: --");
    }

    @FXML
    private void btnVerificarTurmaClick() {
        try {
            // Carrega a tela de visualização da turma
            FXMLLoader loader = GerenciadorTelas.getLoader("view-turma.fxml");
            Parent root = loader.load();

            // Obtém o controlador da tela de visualização da turma
            ViewTurmaController controller = loader.getController();
            
            // Passa a turma atual para o controlador da tela de visualização
            controller.setTurma(this.turma); 

            // Muda para a tela de visualização da turma
            GerenciadorTelas.setRaiz(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}