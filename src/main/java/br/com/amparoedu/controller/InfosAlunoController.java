package br.com.amparoedu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class InfosAlunoController {

    @FXML private Label nomeLabel;
    @FXML private Label dataNascimentoLabel;
    @FXML private Label cpfLabel;
    @FXML private Label generoLabel;
    @FXML private Label cidLabel;
    @FXML private Label nisLabel;
    @FXML private Label grauEscolaridadeLabel;
    @FXML private Label escolaLabel;
    @FXML private Label observacoesLabel;
    @FXML private Label nomeResponsavelLabel;
    @FXML private Label parentescoLabel;
    @FXML private Label cpfResponsavelLabel;
    @FXML private Label contatoLabel;
    @FXML private Label enderecoLabel;
    @FXML private Label turmaLabel;
    @FXML private Label professoraResponsavelLabel;
    @FXML private Label grauEscolaridadeTurmaLabel;
    @FXML private Label turnoLabel;
    @FXML private Button closeButton;

    @FXML
    public void handleCloseAction(ActionEvent event) {
        System.out.println("Close clicked");
    }
}
