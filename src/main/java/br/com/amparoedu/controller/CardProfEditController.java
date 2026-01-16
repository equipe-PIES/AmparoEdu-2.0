package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.UsuarioRepository;
import br.com.amparoedu.backend.service.ProfessorService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.util.Optional;

public class CardProfEditController {

    @FXML private Label nomeLabel;
    @FXML private Button btnInfoProf;
    @FXML private Button excluirProf;
    @FXML private Button editarProf;

    private Professor professor;
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private final ProfessorService professorService = new ProfessorService();

    public void setProfessor(Professor professor) {
        this.professor = professor;
        preencherDados();
    }

    private void preencherDados() {
        if (professor != null) {
            nomeLabel.setText(professor.getNome());
        }
    }

    @FXML
    private void btnInfoProfClick() {
        if (professor == null) return;
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informações do Professor");
        alert.setHeaderText(professor.getNome());
        
        StringBuilder info = new StringBuilder();
        info.append("CPF: ").append(professor.getCpf()).append("\n");
        info.append("Data de Nascimento: ").append(professor.getData_nascimento() != null ? professor.getData_nascimento() : "N/A").append("\n");
        info.append("Gênero: ").append(professor.getGenero() != null ? professor.getGenero() : "N/A").append("\n");
        info.append("Observações: ").append(professor.getObservacoes() != null ? professor.getObservacoes() : "").append("\n");
        
        // Buscar email do usuário
        if (professor.getUsuario_id() != null) {
            Usuario usuario = usuarioRepo.buscarPorId(professor.getUsuario_id());
            if (usuario != null) {
                info.append("Email: ").append(usuario.getEmail()).append("\n");
            }
        }
        
        alert.setContentText(info.toString());
        alert.showAndWait();
    }

    @FXML
    private void excluirProfClick() {
         if (professor == null) return;

         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Confirmação de Exclusão");
         alert.setHeaderText(null);
         alert.setContentText("Tem certeza que deseja excluir o cadastro do professor " + professor.getNome() + "?");
         
         ButtonType btnExcluir = new ButtonType("Excluir");
         ButtonType btnCancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
         
         alert.getButtonTypes().setAll(btnExcluir, btnCancelar);
         
         Optional<ButtonType> result = alert.showAndWait();
         if (result.isPresent() && result.get() == btnExcluir) {
             // Usa o service para excluir o professor e seu usuário
             boolean sucesso = professorService.excluirProfessor(professor.getId());
             
             if (sucesso) {
                 // Atualizar a tela recarregando a view de professores
                 GerenciadorTelas.getInstance().trocarTela("view-profs-coord.fxml");
             } else {
                 Alert erro = new Alert(Alert.AlertType.ERROR);
                 erro.setTitle("Erro");
                 erro.setHeaderText(null);
                 erro.setContentText("Erro ao excluir o professor. Tente novamente.");
                 erro.showAndWait();
             }
         }
    }

    @FXML
    private void editarProfClick() {
        if (professor == null) return;
        
        Usuario usuario = null;
        if (professor.getUsuario_id() != null) {
            usuario = usuarioRepo.buscarPorId(professor.getUsuario_id());
        }
        
        CadastroProfessorController.setDadosEdicao(professor, usuario);
        GerenciadorTelas.getInstance().trocarTela("cadastro-de-prof.fxml");
    }
}
