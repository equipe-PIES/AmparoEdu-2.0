package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.function.BooleanSupplier;

/**
 * Classe base abstrata para controllers de documentos multi-tela (PDI, PAEE, RI, Anamnese).
 * Centraliza toda a lógica comum de navegação, estado e gerenciamento de modo.
 * 
 * @param <T> O tipo do documento (PDI, PAEE, RI, Anamnese)
 */
public abstract class DocumentoControllerBase<T> {

    /**
     * Enum genérico para modo de operação do documento
     */
    public enum ModoDocumento {
        NOVA, EDICAO, VISUALIZACAO
    }

    // Estado compartilhado entre todas as telas do documento
    protected static class EstadoDocumento<T> {
        int telaAtual = 1;
        T documentoCompartilhado;
        String turmaIdOrigem;
        ModoDocumento modoAtual = ModoDocumento.NOVA;
        boolean navegandoEntreTelas;
    }

    protected final TurmaRepository turmaRepo = new TurmaRepository();
    protected T documentoAtual;

    /**
     * Retorna o estado específico do tipo de documento.
     * Cada subclasse deve manter seu próprio estado estático.
     */
    protected abstract EstadoDocumento<T> getEstado();

    /**
     * Retorna o número total de telas do fluxo.
     */
    protected abstract int getTotalTelas();

    /**
     * Retorna o prefixo do nome da tela (ex: "pdi", "paee", "relatorio-individual").
     */
    protected abstract String getPrefixoTela();

    /**
     * Cria uma nova instância do documento.
     */
    protected abstract T criarNovoDocumento();

    /**
     * Detecta qual tela está carregada baseado nos controles presentes.
     * @return número da tela (1, 2, 3, etc)
     */
    protected abstract int detectarTelaAtual();

    /**
     * Salva os dados da tela atual no documento compartilhado.
     */
    protected abstract void salvarDadosTelaAtual();

    /**
     * Carrega os dados do documento compartilhado na tela atual.
     */
    protected abstract void carregarDadosNaTela();

    /**
     * Valida os dados da tela atual.
     * @return true se válidos, false caso contrário
     */
    protected abstract boolean validarTelaAtual();

    /**
     * Define o ID do educando no documento.
     */
    protected abstract void setEducandoIdNoDocumento(String educandoId);

    /**
     * Obtém o ID do educando do documento.
     */
    protected abstract String getEducandoIdDoDocumento();

    /**
     * Obtém o nome do documento para mensagens (ex: "PDI", "PAEE").
     */
    protected abstract String getNomeDocumento();

    /**
     * Inicialização comum para todos os controllers de documento.
     * Deve ser chamado no initialize() da subclasse.
     */
    protected void inicializarBase() {
        EstadoDocumento<T> estado = getEstado();
        boolean vindoDeNavegacao = estado.navegandoEntreTelas;
        estado.navegandoEntreTelas = false;

        // Detecta qual tela está carregada
        int telaDetectada = detectarTelaAtual();
        if (telaDetectada > 0) {
            estado.telaAtual = telaDetectada;
        }

        // Se não veio de navegação, prepara estado conforme o modo
        if (!vindoDeNavegacao) {
            if (estado.modoAtual == ModoDocumento.NOVA) {
                estado.telaAtual = 1;
                if (estado.documentoCompartilhado == null) {
                    estado.documentoCompartilhado = criarNovoDocumento();
                }
            } else if (estado.modoAtual == ModoDocumento.EDICAO || estado.modoAtual == ModoDocumento.VISUALIZACAO) {
                if (estado.documentoCompartilhado == null) {
                    estado.documentoCompartilhado = criarNovoDocumento();
                }
                estado.telaAtual = 1;
            }
        } else if (estado.telaAtual == 1 && estado.documentoCompartilhado == null) {
            estado.documentoCompartilhado = criarNovoDocumento();
        }

        // Usa o documento compartilhado
        if (estado.documentoCompartilhado != null) {
            documentoAtual = estado.documentoCompartilhado;
        }

        // Carrega dados na tela
        carregarDadosNaTela();

        // Desabilita campos em modo visualização
        if (estado.modoAtual == ModoDocumento.VISUALIZACAO) {
            desabilitarCampos();
        }
    }

    /**
     * Navega entre telas do fluxo.
     * @param passo +1 para avançar, -1 para voltar
     * @param validarAntes Função que valida antes de avançar (opcional)
     */
    protected void navegarTela(int passo, BooleanSupplier validarAntes) {
        EstadoDocumento<T> estado = getEstado();
        int novaTela = estado.telaAtual + passo;

        // Verifica limites
        if (novaTela < 1 || novaTela > getTotalTelas()) {
            return;
        }

        // Valida antes de avançar (apenas em modos editáveis)
        if (estado.modoAtual != ModoDocumento.VISUALIZACAO && validarAntes != null && !validarAntes.getAsBoolean()) {
            return;
        }

        // Salva dados da tela atual (apenas em modos editáveis)
        if (estado.modoAtual != ModoDocumento.VISUALIZACAO) {
            salvarDadosTelaAtual();
        }

        // Atualiza estado e navega
        estado.documentoCompartilhado = documentoAtual;
        estado.telaAtual = novaTela;
        estado.navegandoEntreTelas = true;
        GerenciadorTelas.getInstance().trocarTela(getPrefixoTela() + "-" + novaTela + ".fxml");
    }

    /**
     * Volta para a tela anterior.
     */
    protected void btnVoltarClick() {
        navegarTela(-1, null);
    }

    /**
     * Avança para próxima tela.
     */
    protected void btnProximoClick() {
        navegarTela(1, this::validarTelaAtual);
    }

    /**
     * Retorna à tela de origem (turma ou início).
     */
    protected void voltarParaOrigem() {
        EstadoDocumento<T> estado = getEstado();
        
        if (estado.turmaIdOrigem != null) {
            try {
                Turma turma = turmaRepo.buscarPorId(estado.turmaIdOrigem);
                if (turma != null) {
                    // Carrega a tela view-turma com a turma
                    GerenciadorTelas.getInstance().trocarTela("view-turma.fxml");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback: determina tela inicial baseado no usuário logado
        String cargo = obterCargoUsuarioLogado();
        if ("Coordenador".equalsIgnoreCase(cargo)) {
            GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml");
        } else {
            GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
        }
    }

    /**
     * Obtém o cargo do usuário logado (deve ser implementado ou injetado).
     */
    protected String obterCargoUsuarioLogado() {
        try {
            Usuario usuario = br.com.amparoedu.backend.service.AuthService.getUsuarioLogado();
            return usuario != null ? usuario.getTipo() : "Professor";
        } catch (Exception e) {
            return "Professor"; // Fallback
        }
    }

    /**
     * Cancela a operação e volta para origem.
     */
    protected void btnCancelarClick() {
        EstadoDocumento<T> estado = getEstado();
        
        if (estado.modoAtual == ModoDocumento.VISUALIZACAO) {
            voltarParaOrigem();
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Cancelamento");
        confirmacao.setHeaderText("Deseja cancelar?");
        confirmacao.setContentText("Os dados não salvos serão perdidos.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            limparEstado();
            voltarParaOrigem();
        }
    }

    /**
     * Limpa o estado do documento.
     */
    protected void limparEstado() {
        EstadoDocumento<T> estado = getEstado();
        estado.telaAtual = 1;
        estado.documentoCompartilhado = null;
        estado.turmaIdOrigem = null;
        estado.navegandoEntreTelas = false;
    }

    /**
     * Desabilita todos os campos da tela (para modo visualização).
     * Subclasses devem sobrescrever se necessário.
     */
    protected void desabilitarCampos() {
        // Implementação padrão vazia - subclasses podem sobrescrever
    }

    /**
     * Exibe mensagem de sucesso.
     */
    protected void exibirSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Alias para exibirSucesso (compatibilidade com PAEEController)
     */
    protected void exibirMensagemSucesso(String mensagem) {
        exibirSucesso(mensagem);
    }

    /**
     * Exibe mensagem de erro.
     */
    protected void exibirErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Alias para exibirErro (compatibilidade com PAEEController)
     */
    protected void exibirMensagemErro(String mensagem) {
        exibirErro(mensagem);
    }

    /**
     * Exibe mensagem de aviso.
     */
    protected void exibirAviso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Atualiza os dados do usuário logado na tela (labels de nome/cargo).
     * Subclasses podem sobrescrever para implementação específica.
     */
    protected void atualizarUsuarioLogado() {
        // Implementação padrão vazia - subclasses podem sobrescrever se tiverem labels de usuário
    }

    // ========== Métodos Estáticos para Factory ==========

    /**
     * Inicializa um novo documento.
     */
    public static <T> void iniciarNovo(EstadoDocumento<T> estado, T novoDocumento) {
        estado.modoAtual = ModoDocumento.NOVA;
        estado.telaAtual = 1;
        estado.documentoCompartilhado = novoDocumento;
        estado.navegandoEntreTelas = false;
    }

    /**
     * Inicializa edição de documento existente.
     */
    public static <T> void iniciarEdicao(EstadoDocumento<T> estado, T documento) {
        estado.modoAtual = ModoDocumento.EDICAO;
        estado.telaAtual = 1;
        estado.documentoCompartilhado = documento;
        estado.navegandoEntreTelas = false;
    }

    /**
     * Inicializa visualização de documento existente.
     */
    public static <T> void iniciarVisualizacao(EstadoDocumento<T> estado, T documento) {
        estado.modoAtual = ModoDocumento.VISUALIZACAO;
        estado.telaAtual = 1;
        estado.documentoCompartilhado = documento;
        estado.navegandoEntreTelas = false;
    }

    /**
     * Define a turma de origem para retorno após conclusão.
     */
    public static <T> void setTurmaOrigem(EstadoDocumento<T> estado, String turmaId) {
        estado.turmaIdOrigem = turmaId;
    }
}
