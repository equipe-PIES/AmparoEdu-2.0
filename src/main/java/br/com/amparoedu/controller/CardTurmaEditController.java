package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.util.Optional;

public class CardTurmaEditController {

    @FXML private VBox cardTurma;
    @FXML private Label nomeTurmaCard;
    @FXML private Label totalAlunoCard;
    @FXML private Label faixaEtariaCard;
    @FXML private Label grauTurmaCard;
    @FXML private Label turnoTurmaCard;
    
    @FXML private Button editarTurma;
    @FXML private Button excluirTurma;

    private Turma turma;
    private final TurmaRepository turmaRepo = new TurmaRepository();

    public void configurarDados(Turma turma) {
        this.turma = turma;
        nomeTurmaCard.setText(turma.getNome());
        faixaEtariaCard.setText("Faixa Etária: " + turma.getFaixa_etaria());
        grauTurmaCard.setText("Grau da turma: " + turma.getGrau_ensino());
        
        if (turnoTurmaCard != null) {
            turnoTurmaCard.setText("Turno: " + turma.getTurno());
        }
        
        String qtdAlunos = turma.getQuantidade_alunos();
        if (qtdAlunos == null || qtdAlunos.isEmpty()) {
            totalAlunoCard.setText("Total de alunos: 0");
        } else {
            totalAlunoCard.setText("Total de alunos: " + qtdAlunos);
        }
    }
    
    @FXML
    private void handleEditar(ActionEvent event) {
        if (turma == null) return;
        
        try {
            CadastroTurmaController.setTurmaEdicao(turma);
            GerenciadorTelas.trocarTela("cadastro-de-turma.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir edição");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleExcluir(ActionEvent event) {
        if (turma == null) return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Turma");
        alert.setHeaderText("Tem certeza que deseja excluir esta turma?");
        alert.setContentText("Esta ação não pode ser desfeita.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                turmaRepo.excluir(turma.getId());
                GerenciadorTelas.trocarTela("view-turmas-coord.fxml");
            } catch (Exception e) {
                e.printStackTrace();
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setHeaderText("Erro ao excluir turma");
                erro.setContentText(e.getMessage());
                erro.showAndWait();
            }
        }
    }
}
