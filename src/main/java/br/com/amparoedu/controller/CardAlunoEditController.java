package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.Responsavel;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.TurmaEducando;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.EnderecoRepository;
import br.com.amparoedu.backend.repository.PDIRepository;
import br.com.amparoedu.backend.repository.ResponsavelRepository;
import br.com.amparoedu.backend.repository.TurmaEducandoRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CardAlunoEditController {

    @FXML private Label nomeLabel;
    @FXML private Label idadeLabel;
    @FXML private Label cidLabel;
    @FXML private Label grauEscolaridadeLabel;
    @FXML private Label turmaLabel;
    @FXML private Button btnInfo;
    @FXML private Button excluirAluno;
    @FXML private Button editarAluno;

    private Educando educando;
    private final EducandoRepository educandoRepo = new EducandoRepository();
    private final ResponsavelRepository responsavelRepo = new ResponsavelRepository();
    private final EnderecoRepository enderecoRepo = new EnderecoRepository();
    private final PDIRepository pdiRepo = new PDIRepository();
    private final TurmaRepository turmaRepo = new TurmaRepository();
    private final TurmaEducandoRepository turmaEducandoRepo = new TurmaEducandoRepository();

    public void setEducando(Educando educando) {
        this.educando = educando;
        preencherDados();
    }

    private void preencherDados() {
        if (educando != null) {
            nomeLabel.setText(educando.getNome());
            // Calcular idade se necessário, ou usar data de nascimento
            idadeLabel.setText("Nascimento: " + educando.getData_nascimento()); 
            cidLabel.setText("CID: " + (educando.getCid() != null ? educando.getCid() : "N/A"));
            grauEscolaridadeLabel.setText("Escolaridade: " + educando.getGrau_ensino());
            
            // Buscar turmas
            List<TurmaEducando> vinculos = turmaEducandoRepo.buscarPorEducando(educando.getId());
            if (vinculos != null && !vinculos.isEmpty()) {
                StringBuilder turmasStr = new StringBuilder();
                boolean primeiro = true;
                for (TurmaEducando vinculo : vinculos) {
                    Turma turma = turmaRepo.buscarPorId(vinculo.getTurma_id());
                    if (turma != null) {
                        if (!primeiro) turmasStr.append(", ");
                        turmasStr.append(turma.getNome());
                        primeiro = false;
                    }
                }
                
                if (turmasStr.length() > 0) {
                     turmaLabel.setText("Turma: " + turmasStr.toString());
                } else {
                     turmaLabel.setText("Turma: Ainda não foi designado(a) a nenhuma turma.");
                }
            } else {
                turmaLabel.setText("Turma: Ainda não foi designado(a) a nenhuma turma.");
            }
        }
    }

    @FXML
    private void btnInfoClick() {
        if (educando == null) return;
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/infos-aluno.fxml"));
            Parent root = loader.load();

            // Buscar dados complementares
            Responsavel responsavel = responsavelRepo.buscarPorEducando(educando.getId());
            
            Endereco endereco = null;
            if (educando.getEndereco_id() != null) {
                endereco = enderecoRepo.buscarPorId(educando.getEndereco_id());
            } else {
                endereco = enderecoRepo.buscarPorEducando(educando.getId());
            }
            
            PDI pdi = null;
            List<PDI> pdis = pdiRepo.buscarPorEducando(educando.getId());
            if (pdis != null && !pdis.isEmpty()) {
                pdi = pdis.get(0); // Pega o PDI mais recente
            }

            InfoAlunoController controller = loader.getController();
            controller.configurarDados(educando, responsavel, endereco, pdi);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.TRANSPARENT); // Remove a barra de título padrão se desejar estilizar
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            popupStage.setScene(scene);
            
            // Centralizar na tela
            popupStage.centerOnScreen();
            
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao abrir popup de informações do aluno: " + e.getMessage());
        }
    }

    @FXML
    private void excluirAlunoClick() {
         if (educando == null) return;

         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Confirmação de Exclusão");
         alert.setHeaderText(null);
         alert.setContentText("Tem certeza que deseja excluir esse cadastro?");
         
         ButtonType btnExcluir = new ButtonType("Excluir");
         ButtonType btnCancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
         
         alert.getButtonTypes().setAll(btnExcluir, btnCancelar);
         
         Optional<ButtonType> result = alert.showAndWait();
         if (result.isPresent() && result.get() == btnExcluir) {
             educandoRepo.excluir(educando.getId());
             turmaEducandoRepo.excluirPorEducando(educando.getId());
             // Atualizar a tela recarregando a view de alunos
             GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml");
         }
    }

    @FXML
    private void editarAlunoClick() {
        if (educando == null) return;
        
        // Buscar dados complementares
        Responsavel responsavel = responsavelRepo.buscarPorEducando(educando.getId());
        
        Endereco endereco = null;
        if (educando.getEndereco_id() != null) {
            endereco = enderecoRepo.buscarPorId(educando.getEndereco_id());
        } else {
            endereco = enderecoRepo.buscarPorEducando(educando.getId());
        }

        CadastroEducandoController.setDadosEdicao(educando, responsavel, endereco);
        GerenciadorTelas.getInstance().trocarTela("cadastro-de-aluno.fxml");
    }
}
