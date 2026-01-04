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
        try {
            System.out.println("Botão Entrar clicado.");
            
            if (txtEmail == null || txtSenha == null) {
                exibirAlerta("Erro Interno", "Campos de texto não foram carregados corretamente.");
                return;
            }

            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();
            System.out.println("Email: " + email);

            if (email.isEmpty() || senha.isEmpty()) {
                exibirAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
                return;
            }

            System.out.println("Tentando login...");
            Usuario usuario = authService.fazerLogin(email, senha);

            if (usuario != null) {
                System.out.println("Login bem sucedido para: " + usuario.getEmail());
                
                // Inicia a sincronização em segundo plano
                new Thread(() -> {
                    try {
                        new SincronizacaoService().iniciarAgendamento();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                // Redireciona para a tela inicial conforme o tipo de usuário
                if (usuario.getTipo().equals("COORDENADOR")) {
                    System.out.println("Redirecionando para tela de coordenador...");
                    GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
                } else {
                    System.out.println("Redirecionando para tela de professor...");
                    GerenciadorTelas.trocarTela("tela-inicio-professor.fxml");
                }
            } else {
                System.out.println("Usuario retornou null.");
                exibirAlerta("Erro de Login", "E-mail ou senha incorretos.");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            exibirAlerta("Erro de Sistema", "Ocorreu um erro inesperado: " + e.getMessage());
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