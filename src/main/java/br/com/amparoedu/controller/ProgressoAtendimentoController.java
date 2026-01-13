package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.DI;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.PDIRepository;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.backend.service.DIService;
import br.com.amparoedu.backend.service.PAEEService;
import br.com.amparoedu.backend.service.PDIService;
import br.com.amparoedu.backend.service.RIService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ProgressoAtendimentoController {

    private Educando educando;
    private Turma turma;
    private PDI pdiAtual;
    private PAEE paeeAtual;
    private RI riAtual;
    private DI diAtual;
    private final AnamneseService anamneseService = new AnamneseService();
    private final PDIService pdiService = new PDIService();
    private final PAEEService paeeService = new PAEEService();
    private final RIService riService = new RIService();
    private final DIService diService = new DIService();
    private final PDIRepository pdiRepository = new PDIRepository();

    @FXML
    private Button criarRI, editarRI, excluirRI, baixarRI;
    @FXML
    private Button criarAnamnese, editarAnamnese, verAnamnese, excluirAnamnese;
    @FXML
    private Button criarDI, editarDI, verDI, excluirDI;
    @FXML
    private Button criarPDI, editarPDI, verPDI, excluirPDI;
    @FXML
    private Button criarPAEE, editarPAEE, verPAEE, excluirPAEE;
    @FXML
    private Button closeProgressoAtd;

    public void setEducando(Educando educando) {
        this.educando = educando;
        carregarPDIAtual();
        carregarPAEEAtual();
        carregarRIAtual();
        carregarDIAtual();
        atualizarInterface();
    }

    /**
     * Busca o PDI mais recente do educando e atribui a pdiAtual.
     * Se não houver, pdiAtual fica null.
     */
    private void carregarPDIAtual() {
        if (educando == null || educando.getId() == null) {
            pdiAtual = null;
            return;
        }
        var lista = pdiService.buscarPorEducando(educando.getId());
        if (lista != null && !lista.isEmpty()) {
            // Considera o mais recente (assumindo que o primeiro é o mais novo)
            pdiAtual = lista.get(0);
        } else {
            pdiAtual = null;
        }
    }

    /**
     * Busca o PAEE mais recente do educando e atribui a paeeAtual.
     * Se não houver, paeeAtual fica null.
     */
    private void carregarPAEEAtual() {
        if (educando == null || educando.getId() == null) {
            paeeAtual = null;
            return;
        }
        var lista = paeeService.buscarPorEducando(educando.getId());
        if (lista != null && !lista.isEmpty()) {
            // Considera o mais recente (assumindo que o primeiro é o mais novo)
            paeeAtual = lista.get(0);
        } else {
            paeeAtual = null;
        }
    }

    /**
     * Busca o RI mais recente do educando e atribui a riAtual.
     * Se não houver, riAtual fica null.
     */
    private void carregarRIAtual() {
        if (educando == null || educando.getId() == null) {
            riAtual = null;
            return;
        }
        var lista = riService.buscarPorEducando(educando.getId());
        if (lista != null && !lista.isEmpty()) {
            // Considera o mais recente (assumindo que o primeiro é o mais novo)
            riAtual = lista.get(0);
        } else {
            riAtual = null;
        }
    }

    /**
     * Busca o DI mais recente do educando e atribui a diAtual.
     * Se não houver, diAtual fica null.
     */
    private void carregarDIAtual() {
        if (educando == null || educando.getId() == null) {
            diAtual = null;
            return;
        }
        var lista = diService.buscarPorEducando(educando.getId());
        if (lista != null && !lista.isEmpty()) {
            // Considera o mais recente (assumindo que o primeiro é o mais novo)
            diAtual = lista.get(0);
        } else {
            diAtual = null;
        }
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
        GerenciadorTelas.getInstance().trocarTela("anamnese-1.fxml");
    }

    @FXML
    private void btnCriarDIClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        // Inicializa novo DI e define o ID do educando
        DIController.iniciarNovoDI();
        DIController.setEducandoIdParaDI(educando.getId());
        if (turma != null && turma.getId() != null) {
            DIController.setTurmaIdOrigem(turma.getId());
        }

        // Fecha o popup de progresso antes de abrir o DI
        Stage popupStage = (Stage) criarDI.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela do DI
        GerenciadorTelas.getInstance().trocarTela("diagnostico-1.fxml");
    }

    @FXML
    public void btnCriarPDIClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        // Inicializa novo PDI e define o ID do educando
        PDIController.iniciarNovoPDI();
        PDIController.setEducandoIdParaPDI(educando.getId());
        if (turma != null && turma.getId() != null) {
            PDIController.setTurmaIdOrigem(turma.getId());
        }

        // Fecha o popup de progresso antes de abrir o PDI
        Stage popupStage = (Stage) criarPDI.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela do PDI
        GerenciadorTelas.getInstance().trocarTela("pdi-1.fxml");
    }

    @FXML
    private void btnCriarPAEEClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        // Inicializa novo PAEE e define o ID do educando
        PAEEController.iniciarNovoPAEE();
        PAEEController.setEducandoIdParaPAEE(educando.getId());
        if (turma != null && turma.getId() != null) {
            PAEEController.setTurmaIdOrigem(turma.getId());
        }

        // Fecha o popup de progresso antes de abrir o PAEE
        Stage popupStage = (Stage) criarPAEE.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela do PAEE
        GerenciadorTelas.getInstance().trocarTela("paee-1.fxml");
    }

    @FXML
    private void btnCriarRIClick() {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        // Inicializa novo RI e define o ID do educando
        RIController.iniciarNovoRI();
        RIController.setEducandoIdParaRI(educando.getId());
        if (turma != null && turma.getId() != null) {
            RIController.setTurmaIdOrigem(turma.getId());
        }

        // Fecha o popup de progresso antes de abrir o RI
        Stage popupStage = (Stage) criarRI.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela do RI
        GerenciadorTelas.getInstance().trocarTela("relatorio-individual-1.fxml");
    }

    @FXML
    private void handleCloseAction() {
        Stage stage = (Stage) closeProgressoAtd.getScene().getWindow();
        stage.close();
    }

    // Métodos de edição e visualização
    @FXML
    private void btnEditarAnamneseClick() {
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
        GerenciadorTelas.getInstance().trocarTela("anamnese-1.fxml");
    }

    @FXML
    private void btnVerAnamneseClick() {
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
        GerenciadorTelas.getInstance().trocarTela("anamnese-1.fxml");
    }

    @FXML
    private void btnExcluirAnamneseClick() {
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

    @FXML
    private void btnEditarDIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (diAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Diagnóstico Inicial cadastrado.");
            return;
        }

        try {
            DIController.editarDIExistente(diAtual);

            if (turma != null && turma.getId() != null) {
                DIController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) editarDI.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("diagnostico-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao editar Diagnóstico Inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnVerDIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (diAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Diagnóstico Inicial cadastrado.");
            return;
        }

        try {
            DIController.visualizarDI(diAtual);

            if (turma != null && turma.getId() != null) {
                DIController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) verDI.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("diagnostico-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao visualizar Diagnóstico Inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnExcluirDIClick() {
        // Garante que temos um ID de educando válido, mesmo se o objeto educando
        // estiver null
        String educandoId = (educando != null && educando.getId() != null)
                ? educando.getId()
                : (diAtual != null ? diAtual.getEducando_id() : null);
        if (educandoId == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (diAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Diagnóstico Inicial cadastrado.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este Diagnóstico Inicial?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");

        var resultado = confirmacao.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean sucesso = diService.excluirDI(diAtual.getId());

            if (sucesso) {
                exibirAlerta("Sucesso", "Diagnóstico Inicial excluído com sucesso!");
                diAtual = null;
                atualizarInterface();
                // Fecha o popup de progresso, se estiver aberto
                if (excluirDI != null && excluirDI.getScene() != null) {
                    javafx.stage.Stage stage = (javafx.stage.Stage) excluirDI.getScene().getWindow();
                    stage.close();
                }
            } else {
                exibirAlerta("Erro", "Não foi possível excluir o Diagnóstico Inicial.");
            }

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao excluir Diagnóstico Inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEditarPDIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (pdiAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui PDI cadastrado.");
            return;
        }

        try {
            PDIController.editarPDIExistente(pdiAtual);

            if (turma != null && turma.getId() != null) {
                PDIController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) editarPDI.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("pdi-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao editar PDI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnVerPDIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (pdiAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui PDI cadastrado.");
            return;
        }

        try {
            PDIController.visualizarPDI(pdiAtual);

            if (turma != null && turma.getId() != null) {
                PDIController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) verPDI.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("pdi-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao visualizar PDI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnExcluirPDIClick() {

        // Garante que temos um ID de educando válido, mesmo se o objeto educando
        // estiver null
        String educandoId = (educando != null && educando.getId() != null)
                ? educando.getId()
                : (pdiAtual != null ? pdiAtual.getEducandoId() : null);
        if (educandoId == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (pdiAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui PDI cadastrado.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este PDI?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");

        var resultado = confirmacao.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean sucesso = pdiService.excluirPDI(pdiAtual.getId());

            if (sucesso) {
                exibirAlerta("Sucesso", "PDI excluído com sucesso!");
                pdiAtual = null;
                atualizarInterface();
                // Fecha o popup de progresso, se estiver aberto
                if (excluirPDI != null && excluirPDI.getScene() != null) {
                    javafx.stage.Stage stage = (javafx.stage.Stage) excluirPDI.getScene().getWindow();
                    stage.close();
                }
            } else {
                exibirAlerta("Erro", "Não foi possível excluir o PDI.");
            }

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao excluir PDI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEditarPAEEClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (paeeAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui PAEE cadastrado.");
            return;
        }

        try {
            PAEEController.editarPAEEExistente(paeeAtual);

            if (turma != null && turma.getId() != null) {
                PAEEController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) editarPAEE.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("paee-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao editar PAEE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnVerPAEEClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (paeeAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui PAEE cadastrado.");
            return;
        }

        try {
            PAEEController.visualizarPAEE(paeeAtual);

            if (turma != null && turma.getId() != null) {
                PAEEController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) verPAEE.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("paee-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao visualizar PAEE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnExcluirPAEEClick() {
        // Garante que temos um ID de educando válido, mesmo se o objeto educando
        // estiver null
        String educandoId = (educando != null && educando.getId() != null)
                ? educando.getId()
                : (paeeAtual != null ? paeeAtual.getEducandoId() : null);
        if (educandoId == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (paeeAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui PAEE cadastrado.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este PAEE?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");

        var resultado = confirmacao.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean sucesso = paeeService.excluirPAEE(paeeAtual.getId());

            if (sucesso) {
                exibirAlerta("Sucesso", "PAEE excluído com sucesso!");
                paeeAtual = null;
                atualizarInterface();
                // Fecha o popup de progresso, se estiver aberto
                if (excluirPAEE != null && excluirPAEE.getScene() != null) {
                    javafx.stage.Stage stage = (javafx.stage.Stage) excluirPAEE.getScene().getWindow();
                    stage.close();
                }
            } else {
                exibirAlerta("Erro", "Não foi possível excluir o PAEE.");
            }

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao excluir PAEE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEditarRIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (riAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Relatório Individual cadastrado.");
            return;
        }

        try {
            RIController.editarRIExistente(riAtual);

            if (turma != null && turma.getId() != null) {
                RIController.setTurmaIdOrigem(turma.getId());
            }

            Stage popupStage = (Stage) editarRI.getScene().getWindow();
            popupStage.close();

            GerenciadorTelas.getInstance().trocarTela("relatorio-individual-1.fxml");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao editar Relatório Individual: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnExcluirRIClick() {
        // Garante que temos um ID de educando válido, mesmo se o objeto educando
        // estiver null
        String educandoId = (educando != null && educando.getId() != null)
                ? educando.getId()
                : (riAtual != null ? riAtual.getEducando_id() : null);
        if (educandoId == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (riAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Relatório Individual cadastrado.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este Relatório Individual?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");

        var resultado = confirmacao.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean sucesso = riService.excluirRI(riAtual.getId());

            if (sucesso) {
                exibirAlerta("Sucesso", "Relatório Individual excluído com sucesso!");
                riAtual = null;
                atualizarInterface();
                // Fecha o popup de progresso, se estiver aberto
                if (excluirRI != null && excluirRI.getScene() != null) {
                    javafx.stage.Stage stage = (javafx.stage.Stage) excluirRI.getScene().getWindow();
                    stage.close();
                }
            } else {
                exibirAlerta("Erro", "Não foi possível excluir o Relatório Individual.");
            }

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao excluir Relatório Individual: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnBaixarRIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        if (riAtual == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Relatório Individual cadastrado.");
            return;
        }

        // Por enquanto, apenas exibe uma mensagem informando que a funcionalidade será implementada
        // Futuramente, aqui pode ser implementada a geração de PDF ou exportação do relatório
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Baixar Relatório Individual");
        alert.setHeaderText("Funcionalidade em desenvolvimento");
        alert.setContentText("A funcionalidade de download do Relatório Individual será implementada em breve.");
        alert.showAndWait();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert.AlertType tipo;

        switch (titulo.toLowerCase()) {
            case "erro":
                tipo = Alert.AlertType.ERROR;
                break;
            case "aviso":
                tipo = Alert.AlertType.WARNING;
                break;
            case "sucesso":
                tipo = Alert.AlertType.INFORMATION;
                break;
            default:
                tipo = Alert.AlertType.INFORMATION;
        }

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
}