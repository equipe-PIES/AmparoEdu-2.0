package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.util.List;

public class ViewProfsCoordController {

    @FXML private Label nameUser;
    @FXML private Label cargoUser;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;
    
    @FXML private Button sairButton;
    @FXML private Button inicioButton;
    @FXML private Button turmasButton;
    @FXML private Button professoresButton;
    @FXML private Button alunosButton;
    
    @FXML private TextField buscarProf;
    @FXML private Button buscarProfButton;

    private ProfessorRepository professorRepo = new ProfessorRepository();

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nameUser.setText(logado.getEmail()); // Ou nome se tiver
            cargoUser.setText(logado.getTipo());
        }
        
        indicadorDeTela.setText("Professores");
        carregarCardsProfessores(null);
        
        // Configura ação do botão de busca
        buscarProfButton.setOnAction(event -> {
            String termo = buscarProf.getText();
            carregarCardsProfessores(termo);
        });

        // Configura busca em tempo real ao digitar
        buscarProf.textProperty().addListener((observable, oldValue, newValue) -> {
            carregarCardsProfessores(newValue);
        });
    }

    private void carregarCardsProfessores(String filtroNome) {
        containerCards.getChildren().clear();
        List<Professor> professores;
        
        if (filtroNome == null || filtroNome.trim().isEmpty()) {
            professores = professorRepo.listarTodos();
        } else {
            professores = professorRepo.buscarPorNome(filtroNome);
        }

        for (Professor prof : professores) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-prof-edit.fxml"));
                Node cardNode = loader.load();
                
                CardProfEditController cardController = loader.getController();
                cardController.configurarDados(prof);
                
                containerCards.getChildren().add(cardNode);
            } catch (IOException e) {
                System.err.println("Erro ao carregar card de professor: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleInicioButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
    }

    @FXML
    private void handleTurmasButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("view-turmas-coord.fxml");
    }

    @FXML
    private void handleProfessoresButtonAction(ActionEvent event) {}

    @FXML
    private void handleAlunosButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("view-alunos-coord.fxml");
    }

    @FXML
    private void handleSairButtonAction(ActionEvent event) {
        AuthService.logout();
        GerenciadorTelas.trocarTela("tela-de-login.fxml");
    }
}
