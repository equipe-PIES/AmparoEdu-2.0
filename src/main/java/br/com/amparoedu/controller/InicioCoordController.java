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
        GerenciadorTelas.trocarTela("view-turmas-coord.fxml");
    }

    @FXML
    private void btnProfessoresClick() {
        GerenciadorTelas.trocarTela("view-profs-coord.fxml");
    }
    
    @FXML
    private void btnAlunosClick() {
        GerenciadorTelas.trocarTela("view-alunos-coord.fxml");
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.trocarTela("tela-de-login.fxml");
    }
    
    @FXML
    private void btnAddTurmaClick() {
        GerenciadorTelas.trocarTela("cadastro-de-turma.fxml");
    }

    @FXML
    private void btnAddProfClick() {
        GerenciadorTelas.trocarTela("cadastro-de-prof.fxml");
    }

    @FXML
    private void btnAddAlunoClick() {
        System.out.println("Indo para tela de cadastro de aluno...");
        GerenciadorTelas.trocarTela("cadastro-de-aluno.fxml");

    }
}