package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProgressoAtendimentoController {

    private Educando educando;

    @FXML private Button criarRI, editarRI, excluirRI, baixarRI;
    @FXML private Button criarAnamnese, editarAnamnese, verAnamnese, excluirAnamnese;
    @FXML private Button criarDI, editarDI, verDI, excluirDI;
    @FXML private Button criarPDI, editarPDI, verPDI, excluirPDI;
    @FXML private Button criarPAEE, editarPAEE, verPAEE, excluirPAEE;
    @FXML private Button closeProgressoAtd;

    
    public void setEducando(Educando educando) {
        this.educando = educando;
        atualizarInterface();
    }

    private void atualizarInterface() {
        if (educando != null) {
            // Habilitar ou desabilitar botões com base nos dados do educando
        }
    }


    @FXML
    private void btnCriarAnamneseClick() {
        /* Lógica para Anamnese */
    }

    @FXML
    private void btnCriarDIClick() { /* Lógica para Diagnóstico Inicial */ }

    @FXML
    private void btnCriarPDIClick() { /* Lógica para PDI */ }

    @FXML
    private void btnCriarPAEEClick() { /* Lógica para PAEE */ }

    @FXML
    private void btnCriarRIClick() { /* Lógica para Relatório Individual */ }

    @FXML
    private void handleCloseAction() {
        Stage stage = (Stage) closeProgressoAtd.getScene().getWindow();
        stage.close();
    }

    // Métodos de edição e visualização
    @FXML private void btnEditarAnamneseClick() {}
    @FXML private void btnVerAnamneseClick() {}
    @FXML private void btnExcluirAnamneseClick() {}
    
    @FXML private void btnEditarDIClick() {}
    @FXML private void btnVerDIClick() {}
    @FXML private void btnExcluirDIClick() {}

    @FXML private void btnEditarPDIClick() {}
    @FXML private void btnVerPDIClick() {}
    @FXML private void btnExcluirPDIClick() {}

    @FXML private void btnEditarPAEEClick() {}
    @FXML private void btnVerPAEEClick() {}
    @FXML private void btnExcluirPAEEClick() {}

    @FXML private void btnEditarRIClick() {}
    @FXML private void btnExcluirRIClick() {}
    @FXML private void btnBaixarRIClick() {}
}