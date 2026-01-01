package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProgressoAtendimentoController {

    @FXML private Button statusDI, statusPDI, statusRI, statusAnamnese, statusPAEE;
    @FXML private Button closeProgressoAtd;

    private Educando educando;

    /**
     * Recebe o educando para o qual o progresso será exibido.
     * Aplica Especialista na Informação ao manter o estado do objeto necessário para as consultas.
     */
    public void setEducando(Educando educando) {
        this.educando = educando;
        atualizarStatusDocumentos();
    }

    private void atualizarStatusDocumentos() {
        // Exemplo: Se existir PDI no banco, mudar texto para "Visualizar"
        // Este método deve usar os Repositories (Invenção Pura) para checar a existência.
    }

    @FXML
    private void btnStatusAnamneseClick() {
        System.out.println("Acessando Anamnese do educando: " + educando.getNome());
        // Lógica para abrir a tela ou popup de Anamnese
    }

    @FXML
    private void btnStatusPDIClick() {
        System.out.println("Acessando PDI do educando: " + educando.getId());
    }

    @FXML
    private void btnStatusDIClick() {
        // Ação para o Desenvolvimento Infantil
    }

    @FXML
    private void btnStatusRIClick() {
        // Ação para o Relatório Individual
    }

    @FXML
    private void btnStatusPAEEClick() {
        // Ação para o Plano AEE
    }

    @FXML
    private void btnCloseProgressoAtdClick() {
        // Como esta tela será aberta como popup (Modal), fechamos o Stage atual
        Stage stage = (Stage) statusDI.getScene().getWindow();
        stage.close();
    }
}