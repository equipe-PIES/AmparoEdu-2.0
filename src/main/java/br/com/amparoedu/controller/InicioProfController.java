package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.util.List;

public class InicioProfController {

    @FXML private Label nomeUsuario;
    @FXML private Label cargoUsuario;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;

    private TurmaRepository turmaRepo = new TurmaRepository();

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
        
        indicadorDeTela.setText("Minhas Turmas");
        carregarCardsTurmas();
    }

    private void carregarCardsTurmas() {
        containerCards.getChildren().clear(); 
        
        // Busca a lista de todas as turmas do banco
        List<Turma> turmas = turmaRepo.listarTodas();

        for (Turma turma : turmas) {
            try {
                // Carrega o FXML do Card
                FXMLLoader carregador = new FXMLLoader(getClass().getResource("/view/screens/card-turma.fxml"));
                Node card = carregador.load();

                // Pega o controlador do card carregado e passa os dados da turma
                CardTurmaController controller = carregador.getController();
                controller.configurarDados(turma);

                // Adiciona o card visualmente ao FlowPane da tela principal
                containerCards.getChildren().add(card);
            } catch (IOException e) {
                System.err.println("Erro ao carregar o card de turma: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnTurmasClick() {
        carregarCardsTurmas(); // Recarrega os dados ao clicar no botão
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.trocarTela("tela-de-login.fxml");
    }
}