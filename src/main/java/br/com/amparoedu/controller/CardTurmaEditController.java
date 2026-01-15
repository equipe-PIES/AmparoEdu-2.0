package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.repository.TurmaEducandoRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.util.List;
import java.util.Optional;

public class CardTurmaEditController {

    @FXML private Label nomeTurmaCard;
    @FXML private Label totalAlunoCard;
    @FXML private Label faixaEtariaCard;
    @FXML private Label grauTurmaCard;
    @FXML private Label turnoTurmaCard;
    @FXML private Button excluirTurma;
    @FXML private Button editarTurma;

    private Turma turma;
    private final TurmaRepository turmaRepo = new TurmaRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();
    private final TurmaEducandoRepository turmaEducandoRepo = new TurmaEducandoRepository();

    public void setTurma(Turma turma) {
        this.turma = turma;
        
        nomeTurmaCard.setText(turma.getNome());
        faixaEtariaCard.setText("Faixa Etária: " + turma.getFaixa_etaria());
        grauTurmaCard.setText("Grau: " + turma.getGrau_ensino());
        turnoTurmaCard.setText("Turno: " + turma.getTurno());
        
        // Contar alunos
        List<Educando> alunos = educandoRepo.buscarPorTurma(turma.getId());
        totalAlunoCard.setText("Total de alunos: " + alunos.size());
    }

    @FXML
    public void initialize() {
        if (excluirTurma != null) {
            excluirTurma.setOnAction(e -> excluirTurmaClick());
        }
        if (editarTurma != null) {
            editarTurma.setOnAction(e -> editarTurmaClick());
        }
    }

    private void excluirTurmaClick() {
        if (turma == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja excluir a turma " + turma.getNome() + "?");
        
        ButtonType btnExcluir = new ButtonType("Excluir");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
        
        alert.getButtonTypes().setAll(btnExcluir, btnCancelar);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnExcluir) {
            turmaRepo.excluir(turma.getId());
            turmaEducandoRepo.excluirPorTurma(turma.getId());
            GerenciadorTelas.getInstance().trocarTela("view-turmas-coord.fxml");
        }
    }

    private void editarTurmaClick() {
        if (turma == null) return;
        
        CadastroTurmaController.setTurmaEdicao(turma);
        GerenciadorTelas.getInstance().trocarTela("cadastro-de-turma.fxml");
    }
}
