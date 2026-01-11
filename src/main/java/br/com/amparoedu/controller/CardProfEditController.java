package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.UsuarioRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CardProfEditController {

    @FXML private VBox cardProf;
    @FXML private Label nomeLabel;
    @FXML private Button excluirProf;
    @FXML private Button editarProf;
    @FXML private Button infoProfButton;

    private Professor professor;
    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();

    public void configurarDados(Professor professor) {
        this.professor = professor;
        if (professor != null) {
            nomeLabel.setText(professor.getNome());
        }
    }

    @FXML
    public void handleInfoAction(ActionEvent event) {
        if (professor == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/info-professor.fxml"));
            Parent root = loader.load();

            InfosProfessorController controller = loader.getController();
            
            Usuario usuario = usuarioRepo.buscarPorId(professor.getUsuario_id());
            
            controller.setDados(professor, usuario);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Informações do Professor");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir tela de informações");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar dados do usuário");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void handleExcluirAction(ActionEvent event) {
        if (professor == null) return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Professor");
        alert.setHeaderText("Tem certeza que deseja excluir este professor?");
        alert.setContentText("Esta ação não pode ser desfeita.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                professorRepo.excluir(professor.getId());
                // Se possível, excluir usuário também ou deixar que a lógica de negócio trate
                // usuarioRepo.excluir(professor.getUsuario_id()); 
                
                // Atualiza a tela (gambiarra: recarrega a tela de lista)
                GerenciadorTelas.trocarTela("view-profs-coord.fxml");
            } catch (Exception e) {
                e.printStackTrace();
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setHeaderText("Erro ao excluir professor");
                erro.setContentText(e.getMessage());
                erro.showAndWait();
            }
        }
    }

    @FXML
    public void handleEditarAction(ActionEvent event) {
        if (professor == null) return;
        
        try {
            Usuario usuario = usuarioRepo.buscarPorId(professor.getUsuario_id());
            CadastroProfessorController.setDadosEdicao(professor, usuario);
            GerenciadorTelas.trocarTela("cadastro-de-prof.fxml");
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
