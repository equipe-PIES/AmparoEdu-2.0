package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.Responsavel;
import br.com.amparoedu.backend.model.Turma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    public void setDados(Educando educando, Responsavel responsavel, Endereco endereco, Turma turma) {
        if (educando != null) {
            nomeLabel.setText(educando.getNome());
            dataNascimentoLabel.setText(educando.getData_nascimento());
            cpfLabel.setText(educando.getCpf());
            generoLabel.setText(educando.getGenero());
            cidLabel.setText(educando.getCid() != null ? educando.getCid() : "N/A");
            nisLabel.setText(educando.getNis() != null ? educando.getNis() : "N/A");
            grauEscolaridadeLabel.setText(educando.getGrau_ensino());
            escolaLabel.setText(educando.getEscola());
            observacoesLabel.setText(educando.getObservacoes());
        }

        if (responsavel != null) {
            nomeResponsavelLabel.setText(responsavel.getNome());
            parentescoLabel.setText(responsavel.getParentesco());
            cpfResponsavelLabel.setText(responsavel.getCpf());
            contatoLabel.setText(responsavel.getTelefone());
        } else {
            nomeResponsavelLabel.setText("N/A");
            parentescoLabel.setText("N/A");
            cpfResponsavelLabel.setText("N/A");
            contatoLabel.setText("N/A");
        }

        if (endereco != null) {
            String endCompleto = String.format("%s, %s - %s, %s - %s, CEP: %s",
                    endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(),
                    endereco.getCidade(), endereco.getUf(), endereco.getCep());
            if (endereco.getComplemento() != null && !endereco.getComplemento().isEmpty()) {
                endCompleto += " (" + endereco.getComplemento() + ")";
            }
            enderecoLabel.setText(endCompleto);
        } else {
            enderecoLabel.setText("Endereço não cadastrado");
        }

        if (turma != null) {
            turmaLabel.setText(turma.getNome());
            grauEscolaridadeTurmaLabel.setText(turma.getGrau_ensino());
            turnoLabel.setText(turma.getTurno());
            // professoraResponsavelLabel.setText(...) // Se tiver professor vinculado à turma
        } else {
            turmaLabel.setText("Sem turma vinculada");
            grauEscolaridadeTurmaLabel.setText("-");
            turnoLabel.setText("-");
        }
    }

    @FXML
    public void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
