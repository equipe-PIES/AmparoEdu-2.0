package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.Responsavel;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.EnderecoRepository;
import br.com.amparoedu.backend.repository.ResponsavelRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class CardAlunoEditController {

    @FXML private VBox cardAluno;
    @FXML private Label nomeLabel;
    @FXML private Label idadeLabel;
    @FXML private Label cidLabel;
    @FXML private Label grauEscolaridadeLabel;
    @FXML private Label turmaLabel;
    @FXML private Button infoButton;
    @FXML private Button excluirAluno;
    @FXML private Button editarAluno;

    private Educando educando;
    private TurmaRepository turmaRepo = new TurmaRepository();
    private EducandoRepository educandoRepo = new EducandoRepository();
    private EnderecoRepository enderecoRepo = new EnderecoRepository();
    private ResponsavelRepository responsavelRepo = new ResponsavelRepository();

    public void configurarDados(Educando educando) {
        this.educando = educando;
        nomeLabel.setText(educando.getNome());
        cidLabel.setText("CID: " + (educando.getCid() != null ? educando.getCid() : "N/A"));
        grauEscolaridadeLabel.setText("Grau: " + (educando.getGrau_ensino() != null ? educando.getGrau_ensino() : "N/A"));
        
        if (educando.getData_nascimento() != null && !educando.getData_nascimento().isEmpty()) {
            try {
                LocalDate birthDate = LocalDate.parse(educando.getData_nascimento());
                int age = Period.between(birthDate, LocalDate.now()).getYears();
                idadeLabel.setText("Idade: " + age + " anos");
            } catch (DateTimeParseException e) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate birthDate = LocalDate.parse(educando.getData_nascimento(), formatter);
                    int age = Period.between(birthDate, LocalDate.now()).getYears();
                    idadeLabel.setText("Idade: " + age + " anos");
                } catch (DateTimeParseException ex) {
                    idadeLabel.setText("Idade: " + educando.getData_nascimento());
                }
            }
        } else {
            idadeLabel.setText("Idade: N/A");
        }

        if (educando.getTurma_id() != null && !educando.getTurma_id().isEmpty()) {
            Turma turma = turmaRepo.buscarPorId(educando.getTurma_id());
            if (turma != null) {
                turmaLabel.setText("Turma: " + turma.getNome());
            } else {
                turmaLabel.setText("Turma: ID " + educando.getTurma_id());
            }
        } else {
            turmaLabel.setText("Turma: Sem turma");
        }
    }

    @FXML
    private void handleInfoAction(ActionEvent event) {
        if (educando == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/infos-aluno.fxml"));
            Parent root = loader.load();

            InfosAlunoController controller = loader.getController();
            
            // Busca dados completos
            Endereco endereco = enderecoRepo.buscarPorId(educando.getEndereco_id());
            Responsavel responsavel = responsavelRepo.buscarPorEducandoId(educando.getId());
            Turma turma = null;
            if (educando.getTurma_id() != null && !educando.getTurma_id().isEmpty()) {
                turma = turmaRepo.buscarPorId(educando.getTurma_id());
            }

            controller.setDados(educando, responsavel, endereco, turma);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Informações do Aluno");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir tela de informações");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleExcluir(ActionEvent event) { //Excluir aluno
        if (educando == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Aluno");
        alert.setContentText("Tem certeza que deseja excluir o aluno " + educando.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            educandoRepo.excluir(educando.getId());
            GerenciadorTelas.trocarTela("view-alunos-coord.fxml");
        }
    }

    @FXML
    private void handleEditar(ActionEvent event) { //Editar aluno
        if (educando == null) return;

        try {
            Endereco endereco = enderecoRepo.buscarPorId(educando.getEndereco_id());
            Responsavel responsavel = responsavelRepo.buscarPorEducandoId(educando.getId());

            CadastroEducandoController.setDadosEdicao(educando, endereco, responsavel);
            GerenciadorTelas.trocarTela("cadastro-de-aluno.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar dados para edição");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
