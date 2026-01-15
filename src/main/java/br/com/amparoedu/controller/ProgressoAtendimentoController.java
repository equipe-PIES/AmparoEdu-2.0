package br.com.amparoedu.controller;

import java.io.File;
import java.util.List;

import br.com.amparoedu.backend.factory.DocumentoFluxo;
import br.com.amparoedu.backend.factory.DocumentoFluxoFactory;
import br.com.amparoedu.backend.factory.TipoDocumento;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.backend.service.DIService;
import br.com.amparoedu.backend.service.PAEEService;
import br.com.amparoedu.backend.service.PDIService;
import br.com.amparoedu.backend.service.RIService;
import br.com.amparoedu.util.RIPDFGenerator;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProgressoAtendimentoController {

    private enum OperacaoDocumento {
        CRIAR, EDITAR, VISUALIZAR, EXCLUIR
    }

    private Educando educando;
    private Turma turma;
    
    private final AnamneseService anamneseService = new AnamneseService();
    private final PDIService pdiService = new PDIService();
    private final PAEEService paeeService = new PAEEService();
    private final RIService riService = new RIService();
    private final DIService diService = new DIService();

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
    }
    
    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @FXML
    private void btnCriarAnamneseClick() {
        abrirDocumento(TipoDocumento.ANAMNESE, OperacaoDocumento.CRIAR, null);
    }

    @FXML
    private void btnCriarDIClick() {
        abrirDocumento(TipoDocumento.DI, OperacaoDocumento.CRIAR, null);
    }

    @FXML
    public void btnCriarPDIClick() {
        abrirDocumento(TipoDocumento.PDI, OperacaoDocumento.CRIAR, null);
    }

    @FXML
    private void btnCriarPAEEClick() {
        abrirDocumento(TipoDocumento.PAEE, OperacaoDocumento.CRIAR, null);
    }

    @FXML
    private void btnCriarRIClick() {
        abrirDocumento(TipoDocumento.RI, OperacaoDocumento.CRIAR, null);
    }

    @FXML
    private void btnEditarAnamneseClick() {
        processarOperacaoDocumento(TipoDocumento.ANAMNESE, OperacaoDocumento.EDITAR);
    }

    @FXML
    private void btnVerAnamneseClick() {
        processarOperacaoDocumento(TipoDocumento.ANAMNESE, OperacaoDocumento.VISUALIZAR);
    }

    @FXML
    private void btnExcluirAnamneseClick() {
        processarOperacaoDocumento(TipoDocumento.ANAMNESE, OperacaoDocumento.EXCLUIR);
    }

    @FXML
    private void btnEditarDIClick() {
        processarOperacaoDocumento(TipoDocumento.DI, OperacaoDocumento.EDITAR);
    }

    @FXML
    private void btnVerDIClick() {
        processarOperacaoDocumento(TipoDocumento.DI, OperacaoDocumento.VISUALIZAR);
    }

    @FXML
    private void btnExcluirDIClick() {
        processarOperacaoDocumento(TipoDocumento.DI, OperacaoDocumento.EXCLUIR);
    }

    @FXML
    private void btnEditarPDIClick() {
        processarOperacaoDocumento(TipoDocumento.PDI, OperacaoDocumento.EDITAR);
    }

    @FXML
    private void btnVerPDIClick() {
        processarOperacaoDocumento(TipoDocumento.PDI, OperacaoDocumento.VISUALIZAR);
    }

    @FXML
    private void btnExcluirPDIClick() {
        processarOperacaoDocumento(TipoDocumento.PDI, OperacaoDocumento.EXCLUIR);
    }

    @FXML
    private void btnEditarPAEEClick() {
        processarOperacaoDocumento(TipoDocumento.PAEE, OperacaoDocumento.EDITAR);
    }

    @FXML
    private void btnVerPAEEClick() {
        processarOperacaoDocumento(TipoDocumento.PAEE, OperacaoDocumento.VISUALIZAR);
    }

    @FXML
    private void btnExcluirPAEEClick() {
        processarOperacaoDocumento(TipoDocumento.PAEE, OperacaoDocumento.EXCLUIR);
    }

    @FXML
    private void btnEditarRIClick() {
        processarOperacaoDocumento(TipoDocumento.RI, OperacaoDocumento.EDITAR);
    }

    @FXML
    private void btnVerRIClick() {
        processarOperacaoDocumento(TipoDocumento.RI, OperacaoDocumento.VISUALIZAR);
    }

    @FXML
    private void btnExcluirRIClick() {
        processarOperacaoDocumento(TipoDocumento.RI, OperacaoDocumento.EXCLUIR);
    }

    @FXML
    private void handleCloseAction() {
        Stage stage = (Stage) closeProgressoAtd.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnBaixarRIClick() {
        if (educando == null || educando.getId() == null) {
            exibirAlerta("Erro", "Nenhum educando selecionado.");
            return;
        }

        RI ri = buscarDocumentoRecente(TipoDocumento.RI);
        if (ri == null) {
            exibirAlerta("Aviso", "Este educando ainda não possui Relatório Individual cadastrado.");
            return;
        }

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Relatório Individual");
            fileChooser.setInitialFileName(
                    "Relatorio_Individual_" + educando.getNome().replace(" ", "_") + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome, "Downloads");
            if (downloadsDir.exists() && downloadsDir.isDirectory()) {
                fileChooser.setInitialDirectory(downloadsDir);
            }

            Stage stage = (Stage) baixarRI.getScene().getWindow();
            File arquivo = fileChooser.showSaveDialog(stage);
            if (arquivo == null) {
                return;
            }

            RIPDFGenerator generator = new RIPDFGenerator();
            boolean sucesso = generator.gerarRelatorioIndividual(educando, ri, arquivo.getAbsolutePath());

            if (sucesso) {
                exibirAlerta("Sucesso", "Relatório Individual gerado e salvo em: " + arquivo.getAbsolutePath());
            } else {
                exibirAlerta("Erro", "Falha ao gerar o Relatório Individual.");
            }
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao gerar Relatório Individual: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Busca o documento mais recente do educando para o tipo especificado.
    @SuppressWarnings("unchecked")
    private <T> T buscarDocumentoRecente(TipoDocumento tipo) {
        if (educando == null || educando.getId() == null) {
            return null;
        }
        
        List<?> lista = null;
        
        switch (tipo) {
            case ANAMNESE:
                lista = anamneseService.buscarPorEducando(educando.getId());
                break;
            case PDI:
                lista = pdiService.buscarPorEducando(educando.getId());
                break;
            case PAEE:
                lista = paeeService.buscarPorEducando(educando.getId());
                break;
            case DI:
                lista = diService.buscarPorEducando(educando.getId());
                break;
            case RI:
                lista = riService.buscarPorEducando(educando.getId());
                break;
        }
        
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        
        return (T) lista.get(0);
    }

    // Método centralizado para abrir documentos usando o padrão Factory.
    @SuppressWarnings("unchecked")
    private <T> void abrirDocumento(TipoDocumento tipo, OperacaoDocumento operacao, T documento) {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }

        // Cria o fluxo apropriado usando o factory
        DocumentoFluxo<T> fluxo = (DocumentoFluxo<T>) DocumentoFluxoFactory.criar(tipo);
        
        // Define o contexto (educando e turma)
        fluxo.setEducandoId(educando.getId());
        if (turma != null && turma.getId() != null) {
            fluxo.setTurmaOrigem(turma.getId());
        }

        // Executa a operação apropriada
        switch (operacao) {
            case CRIAR:
                fluxo.iniciarNovo();
                break;
            case EDITAR:
                if (documento == null) {
                    System.err.println("Erro: Nenhum documento fornecido para edição.");
                    return;
                }
                fluxo.iniciarEdicao(documento);
                break;
            case VISUALIZAR:
                if (documento == null) {
                    System.err.println("Erro: Nenhum documento fornecido para visualização.");
                    return;
                }
                fluxo.iniciarVisualizacao(documento);
                break;
            case EXCLUIR:
                System.err.println("Erro: Operação EXCLUIR não deve chamar abrirDocumento.");
                return;
        }

        // Fecha o popup de progresso
        Stage popupStage = (Stage) closeProgressoAtd.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela do documento
        GerenciadorTelas.getInstance().trocarTela(fluxo.getPrimeiraTela());
    }
    
    // Método genérico que processa operações em documentos (editar, visualizar, excluir).
    @SuppressWarnings("unchecked")
    private void processarOperacaoDocumento(TipoDocumento tipo, OperacaoDocumento operacao) {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }
        
        DocumentoFluxo<Object> fluxo = (DocumentoFluxo<Object>) DocumentoFluxoFactory.criar(tipo);
        fluxo.setEducandoId(educando.getId());
        if (turma != null && turma.getId() != null) {
            fluxo.setTurmaOrigem(turma.getId());
        }

        // Busca documento recente
        Object documento = buscarDocumentoRecente(tipo);
        
        if (documento == null && operacao != OperacaoDocumento.CRIAR) {
            String nomeDoc = fluxo.getNomeDocumento();
            exibirAlerta("Aviso", "Este educando ainda não possui " + nomeDoc + " cadastrado.");
            return;
        }
        
        // Processa operação
        switch (operacao) {
            case EDITAR:
            case VISUALIZAR:
                abrirDocumento(tipo, operacao, documento);
                break;
                
            case EXCLUIR:
                String nomeDoc = fluxo.getNomeDocumento();
                if (!confirmarExclusao(nomeDoc)) {
                    return;
                }

                boolean sucesso = fluxo.excluir(documento);
                if (sucesso) {
                    exibirAlerta("Sucesso", nomeDoc + " excluído com sucesso.");
                }
                break;
                
            case CRIAR:
                abrirDocumento(tipo, operacao, null);
                break;
        }
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

    // Confirmação simples antes de excluir um documento.
    private boolean confirmarExclusao(String nomeDocumento) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Excluir " + nomeDocumento);
        confirmacao.setHeaderText("Deseja realmente excluir este " + nomeDocumento + "?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");
        return confirmacao.showAndWait().filter(btn -> btn == ButtonType.OK).isPresent();
    }
}