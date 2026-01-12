package br.com.amparoedu.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class ViewProfsCoordController {

    @FXML private Label nomeUsuario;
    @FXML private Label cargoUsuario;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;
    @FXML private TextField buscarProf;
    @FXML private Button btnSair;
    @FXML private Button btnBuscarProf;

    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private List<Professor> todosProfessores;

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
        
        if (indicadorDeTela != null) {
            indicadorDeTela.setText("Professores Cadastrados");
        }
        
        carregarProfessores();
        
        // Adiciona listener para busca em tempo real (ao digitar)
        if (buscarProf != null) {
            buscarProf.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarProfessores();
            });
        }
        
        // Configura ação do botão buscar
        if (btnBuscarProf != null) {
            btnBuscarProf.setOnAction(event -> filtrarProfessores());
        }
    }

    private void carregarProfessores() {
        todosProfessores = professorRepo.listarTodos();
        exibirProfessores(todosProfessores);
    }

    private void exibirProfessores(List<Professor> professores) {
        containerCards.getChildren().clear();
        
        for (Professor professor : professores) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-prof-edit.fxml"));
                Node card = loader.load();

                CardProfEditController controller = loader.getController();
                controller.setProfessor(professor);

                containerCards.getChildren().add(card);
            } catch (IOException e) {
                System.err.println("Erro ao carregar card do professor: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void filtrarProfessores() {
        if (todosProfessores == null) return;
        
        String termoBusca = (buscarProf != null) ? buscarProf.getText() : "";
        
        List<Professor> filtrados = todosProfessores.stream()
            .filter(p -> {
                // Filtro de nome
                if (termoBusca != null && !termoBusca.isEmpty()) {
                    return p.getNome().toLowerCase().contains(termoBusca.toLowerCase());
                }
                return true;
            })
            .collect(Collectors.toList());
            
        exibirProfessores(filtrados);
    }

    @FXML
    private void btnInicioClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml");
    }

    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("view-turmas-coord.fxml");
    }

    @FXML
    private void btnProfessoresClick() {
        // Já estamos na tela de professores
    }

    @FXML
    private void btnAlunosClick() {
        GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml");
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
}
