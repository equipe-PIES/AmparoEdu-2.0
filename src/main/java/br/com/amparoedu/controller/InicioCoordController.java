package br.com.amparoedu.controller;

import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InicioCoordController {
    @FXML private Label NomeUsuario;
    @FXML private Label CargoUsuario;

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            NomeUsuario.setText(logado.getEmail());
            CargoUsuario.setText(logado.getTipo());
        }
    }

    @FXML
    private void btnInicioClick() {
        System.out.println("Indo para tela de inicio...");
    }

    @FXML
    private void btnTurmasClick() {
        System.out.println("Indo para tela de turmas...");
    }

    @FXML
    private void btnProfessoresClick() {
        System.out.println("Indo para tela de professores...");
    }
    
    @FXML
    private void btnAlunosClick() {
        System.out.println("Indo para tela de alunos...");
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
    
    @FXML
    private void btnAddTurmaClick() {
        System.out.println("Indo para tela de cadastro de turma...");
    }

    @FXML
    private void btnAddProfClick() {
        System.out.println("Indo para tela de cadastro de professor...");
    }

    @FXML
    private void btnAddAlunoClick() {

        GerenciadorTelas.getInstance().trocarTela("cadastro-de-aluno.fxml");
    }
}