package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProgressoAtendimentoController {

    private Educando educando;
    private Turma turma;

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

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    private void atualizarInterface() {
        if (educando != null) {
            // Habilitar ou desabilitar botões com base nos dados do educando
        }
    }


    @FXML
    private void btnCriarAnamneseClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }
        
        // Inicializa nova anamnese e define o ID do educando
        AnamneseController.iniciarNovaAnamnese();
        AnamneseController.setEducandoIdParaAnamnese(educando.getId());
        if (turma != null && turma.getId() != null) {
            AnamneseController.setTurmaOrigem(turma.getId());
        }
        
        // Fecha o popup de progresso antes de abrir a anamnese
        Stage popupStage = (Stage) criarAnamnese.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela de anamnese
        GerenciadorTelas.trocarTela("anamnese-1.fxml");
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