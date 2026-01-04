package br.com.amparoedu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class CardAlunoController {

    @FXML private Label nomeLabel;
    @FXML private Label idadeLabel;
    @FXML private Label cidLabel;
    @FXML private Label grauEscolaridadeLabel;
    @FXML private Button infoButton;
    @FXML private Button verProgressoButton;

    @FXML
    public void handleInfoAction(ActionEvent event) {
        System.out.println("Info clicked");
    }

    @FXML
    public void handleVerProgressoAction(ActionEvent event) {
        System.out.println("Ver Progresso clicked");
    }
}
