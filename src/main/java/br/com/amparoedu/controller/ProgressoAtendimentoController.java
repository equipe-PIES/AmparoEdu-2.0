package br.com.amparoedu.controller;

import br.com.amparoedu.backend.factory.DocumentoFluxo;
import br.com.amparoedu.backend.factory.DocumentoFluxoFactory;
import br.com.amparoedu.backend.factory.TipoDocumento;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.backend.service.PAEEService;
import br.com.amparoedu.backend.service.PDIService;
import br.com.amparoedu.backend.service.RIService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    
    // Map para buscar documentos por tipo
    private final Map<TipoDocumento, Function<String, List<?>>> buscaDocumento = new HashMap<>();
    
    // Map para excluir documentos por tipo
    private final Map<TipoDocumento, Function<String, Boolean>> exclusaoDocumento = new HashMap<>();

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
        inicializarMaps();
        atualizarInterface();
    }
    
    private void inicializarMaps() {
        // Configurar busca de documentos
        buscaDocumento.put(TipoDocumento.ANAMNESE, anamneseService::buscarPorEducando);
        buscaDocumento.put(TipoDocumento.PDI, pdiService::buscarPorEducando);
        buscaDocumento.put(TipoDocumento.PAEE, paeeService::buscarPorEducando);
        buscaDocumento.put(TipoDocumento.RI, riService::buscarPorEducando);
        
        // Configurar exclusão (com wrapper para capturar exceptions)
        exclusaoDocumento.put(TipoDocumento.ANAMNESE, id -> {
            try {
                return anamneseService.excluirAnamnese(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        exclusaoDocumento.put(TipoDocumento.PDI, id -> {
            try {
                return pdiService.excluirPDI(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        exclusaoDocumento.put(TipoDocumento.PAEE, id -> {
            try {
                return paeeService.excluirPAEE(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        exclusaoDocumento.put(TipoDocumento.RI, id -> {
            try {
                return riService.excluirRI(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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
        abrirDocumento(TipoDocumento.ANAMNESE, OperacaoDocumento.CRIAR, null);
    }

    @FXML
    private void btnCriarDIClick() {
        /* Lógica para Diagnóstico Inicial */
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
    private void handleCloseAction() {
        Stage stage = (Stage) closeProgressoAtd.getScene().getWindow();
        stage.close();
    }

    /**
     * Busca o documento mais recente do educando para o tipo especificado.
     */
    @SuppressWarnings("unchecked")
    private <T> T buscarDocumentoRecente(TipoDocumento tipo) {
        if (educando == null || educando.getId() == null) {
            return null;
        }
        
        Function<String, List<?>> busca = buscaDocumento.get(tipo);
        if (busca == null) return null;
        
        List<?> lista = busca.apply(educando.getId());
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        
        return (T) lista.get(0);
    }

    /**
     * Método centralizado para abrir documentos usando o padrão Factory.
     * Elimina código duplicado para criar, editar e visualizar documentos.
     */
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
                // EXCLUIR é tratado em processarOperacaoDocumento, não em abrirDocumento
                System.err.println("Erro: Operação EXCLUIR não deve chamar abrirDocumento.");
                return;
        }

        // Fecha o popup de progresso
        Stage popupStage = (Stage) closeProgressoAtd.getScene().getWindow();
        popupStage.close();

        // Abre a primeira tela do documento
        GerenciadorTelas.getInstance().trocarTela(fluxo.getPrimeiraTela());
    }

    // Métodos de edição e visualização
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Baixar Relatório Individual");
        alert.setHeaderText("Funcionalidade em desenvolvimento");
        alert.setContentText("A funcionalidade de download do Relatório Individual será implementada em breve.");
        alert.showAndWait();
    }
    
    /**
     * Método genérico que processa operações em documentos (editar, visualizar, excluir).
     * Busca o documento automaticamente e executa a operação apropriada.
     */
    private void processarOperacaoDocumento(TipoDocumento tipo, OperacaoDocumento operacao) {
        if (educando == null || educando.getId() == null) {
            System.err.println("Erro: Educando não definido ou sem ID.");
            return;
        }
        
        // Busca documento recente
        Object documento = buscarDocumentoRecente(tipo);
        
        if (documento == null && operacao != OperacaoDocumento.CRIAR) {
            String nomeDoc = DocumentoFluxoFactory.criar(tipo).getNomeDocumento();
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
                excluirDocumento(tipo, documento);
                break;
                
            case CRIAR:
                abrirDocumento(tipo, operacao, null);
                break;
        }
    }
    
    /**
     * Método genérico para excluir documentos com confirmação.
     */
    private void excluirDocumento(TipoDocumento tipo, Object documento) {
        if (documento == null) return;
        
        String nomeDoc = DocumentoFluxoFactory.criar(tipo).getNomeDocumento();
        
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este " + nomeDoc + "?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");
        
        var resultado = confirmacao.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
            return;
        }
        
        try {
            // Obtém o ID do documento
            String documentoId = obterIdDocumento(documento);
            
            // Executa exclusão
            Function<String, Boolean> excluir = exclusaoDocumento.get(tipo);
            boolean sucesso = excluir != null && excluir.apply(documentoId);
            
            if (sucesso) {
                exibirAlerta("Sucesso", nomeDoc + " excluído com sucesso!");
                atualizarInterface();
            } else {
                exibirAlerta("Erro", "Não foi possível excluir o " + nomeDoc + ".");
            }
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao excluir " + nomeDoc + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Obtém o ID do documento usando reflexão.
     */
    private String obterIdDocumento(Object documento) {
        try {
            return (String) documento.getClass().getMethod("getId").invoke(documento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter ID do documento", e);
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
}