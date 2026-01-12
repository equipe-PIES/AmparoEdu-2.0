package br.com.amparoedu.controller;

import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.SincronizacaoService;
import br.com.amparoedu.view.GerenciadorTelas;
import br.com.amparoedu.backend.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;

    private AuthService authService = new AuthService();

    @FXML
    private void btnEntrarClick() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            exibirAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
            return;
        }

        Usuario usuario = authService.fazerLogin(email, senha);

        if (usuario != null) {
            
            // Inicia a sincronização em segundo plano
            new Thread(() -> {
                new SincronizacaoService().iniciarAgendamento();
            }).start();

            // Redireciona para a tela inicial conforme o tipo de usuário
            if (usuario.getTipo().equals("COORDENADOR")) {
                GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml");
            } else {
                GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
            }
        } else {
            exibirAlerta("Erro de Login", "E-mail ou senha incorretos.");
        }
    }

    @FXML
    private void esqueciMinhaSenhaClick() {
        // implementar recuperação de senha aqui
    }

    // Método auxiliar para exibir alertas
    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}