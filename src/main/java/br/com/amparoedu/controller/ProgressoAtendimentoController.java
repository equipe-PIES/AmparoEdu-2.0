package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ProgressoAtendimentoController {

    private Educando educando;
    private Turma turma;
    private final AnamneseService anamneseService = new AnamneseService();

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
    @FXML private void btnEditarAnamneseClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        java.util.List<Anamnese> lista = anamneseService.buscarPorEducando(educando.getId());
        if (lista == null || lista.isEmpty()) {
            System.err.println("Nenhuma anamnese encontrada para edição.");
            return;
        }
        Anamnese ultima = lista.get(0); // ordenado por data_criacao DESC

        AnamneseController.editarAnamneseExistente(ultima);
        AnamneseController.setEducandoIdParaAnamnese(educando.getId());
        if (turma != null && turma.getId() != null) {
            AnamneseController.setTurmaOrigem(turma.getId());
        }

        Stage popupStage = (Stage) editarAnamnese.getScene().getWindow();
        popupStage.close();
        GerenciadorTelas.trocarTela("anamnese-1.fxml");
    }

    @FXML private void btnVerAnamneseClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        java.util.List<Anamnese> lista = anamneseService.buscarPorEducando(educando.getId());
        if (lista == null || lista.isEmpty()) {
            System.err.println("Nenhuma anamnese encontrada para visualização.");
            return;
        }
        Anamnese ultima = lista.get(0);

        AnamneseController.visualizarAnamnese(ultima);
        AnamneseController.setEducandoIdParaAnamnese(educando.getId());
        if (turma != null && turma.getId() != null) {
            AnamneseController.setTurmaOrigem(turma.getId());
        }

        Stage popupStage = (Stage) verAnamnese.getScene().getWindow();
        popupStage.close();
        GerenciadorTelas.trocarTela("anamnese-1.fxml");
    }

    @FXML private void btnExcluirAnamneseClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        java.util.List<Anamnese> lista = anamneseService.buscarPorEducando(educando.getId());
        if (lista == null || lista.isEmpty()) {
            System.err.println("Nenhuma anamnese para excluir.");
            return;
        }

        Anamnese ultima = lista.get(0);
        
        // Confirmação antes de excluir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir Anamnese");
        alert.setContentText("Tem certeza que deseja excluir esta anamnese? Esta ação não pode ser desfeita.");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                anamneseService.excluirAnamnese(ultima.getId());
                System.out.println("Anamnese excluída: " + ultima.getId());
                
                // Exibe mensagem de sucesso
                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("Sucesso");
                sucesso.setHeaderText(null);
                sucesso.setContentText("Anamnese excluída com sucesso!");
                sucesso.showAndWait();
            } catch (Exception e) {
                System.err.println("Erro ao excluir anamnese: " + e.getMessage());
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setHeaderText(null);
                erro.setContentText("Erro ao excluir anamnese: " + e.getMessage());
                erro.showAndWait();
            }
        }
    }
    
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