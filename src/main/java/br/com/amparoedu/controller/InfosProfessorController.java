package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfosProfessorController {

    @FXML private Label nomeLabel;
    @FXML private Label cpfLabel;
    @FXML private Label dataNascimentoLabel;
    @FXML private Label generoLabel;
    @FXML private Label emailLabel;
    @FXML private Label observacoesLabel;
    @FXML private Button closeButton;

    public void setDados(Professor professor, Usuario usuario) {
        if (professor != null) {
            nomeLabel.setText(professor.getNome());
            cpfLabel.setText(professor.getCpf());
            dataNascimentoLabel.setText(professor.getData_nascimento());
            generoLabel.setText(professor.getGenero());
            observacoesLabel.setText(professor.getObservacoes());
        }

        if (usuario != null) {
            emailLabel.setText(usuario.getEmail());
        } else {
            emailLabel.setText("N/A");
        }
    }

    @FXML
    public void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
