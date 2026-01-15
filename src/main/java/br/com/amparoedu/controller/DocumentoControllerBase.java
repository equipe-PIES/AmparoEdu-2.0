package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.function.BooleanSupplier;

/* Classe base abstrata para controllers de documentos multi-tela (PDI, PAEE, RI, Anamnese).
    Centraliza toda a lógica comum de navegação, estado e gerenciamento de modo. */
public abstract class DocumentoControllerBase<T> {

    // Modos de operação do documento
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
        Object builder;
    }

    protected final TurmaRepository turmaRepo = new TurmaRepository();
    protected T documentoAtual;

    // Retorna o estado compartilhado do documento.
    protected abstract EstadoDocumento<T> getEstado();

    // Retorna o total de telas do documento.
    protected abstract int getTotalTelas();

    // Retorna o prefixo do nome das telas FXML.
    protected abstract String getPrefixoTela();

    // Cria uma nova instância do documento.
    protected abstract T criarNovoDocumento();

    // Detecta qual tela está atualmente carregada.
    protected abstract int detectarTelaAtual();

    // Salva os dados da tela atual no documento.
    protected abstract void salvarDadosTelaAtual();

    // Carrega os dados do documento na tela atual.
    protected abstract void carregarDadosNaTela();

    // valida a tela atual antes de avançar.
    protected abstract boolean validarTelaAtual();

    // Define o ID do educando no documento.
    protected abstract void setEducandoIdNoDocumento(String educandoId);

    // Retorna o ID do educando do documento.
    protected abstract String getEducandoIdDoDocumento();

    // Retorna o nome do tipo de documento.
    protected abstract String getNomeDocumento();

    // Volta para a tela anterior com popup de confirmação.
    protected abstract void voltarComPopup(String educandoId);

    // Inicializa o estado base do documento.
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

    // Navega para a próxima ou anterior tela.
    protected void navegarTela(int passo, BooleanSupplier validarAntes) {
        EstadoDocumento<T> estado = getEstado();
        int novaTela = estado.telaAtual + passo;
        int totalTelas = getTotalTelas();

        // Verifica limites - bloqueia navegação inválida
        if (novaTela < 1 || novaTela > totalTelas) {
            return; // Silenciosamente ignora navegação fora dos limites
        }

        // Valida antes de avançar (apenas em modos editáveis)
        if (estado.modoAtual != ModoDocumento.VISUALIZACAO && passo > 0 && validarAntes != null) {
            if (!validarAntes.getAsBoolean()) {
                return; // Validação falhou, não avança
            }
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

    // Volta para a tela anterior.
    protected void btnVoltarClick() {
        navegarTela(-1, null);
    }

    // Avança para a próxima tela.
    protected void btnProximoClick() {
        navegarTela(1, this::validarTelaAtual);
    }

    // Volta para a tela ou dashboard de origem.
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

    // Obtém o cargo do usuário logado.
    protected String obterCargoUsuarioLogado() {
        try {
            Usuario usuario = br.com.amparoedu.backend.service.AuthService.getUsuarioLogado();
            return usuario != null ? usuario.getTipo() : "Professor";
        } catch (Exception e) {
            return "Professor";
        }
    }

    // Cancela a operação e volta para origem com popup.
    protected void btnCancelarClick() {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Cancelar " + getNomeDocumento());
        confirmacao.setHeaderText("Deseja realmente cancelar?");
        confirmacao.setContentText("Todos os dados preenchidos serão perdidos.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            String educandoId = getEducandoIdDoDocumento();
            limparEstado();
            voltarComPopup(educandoId);
        }
    }

    // Limpa o estado do documento.
    protected void limparEstado() {
        EstadoDocumento<T> estado = getEstado();
        estado.telaAtual = 1;
        estado.documentoCompartilhado = null;
        estado.turmaIdOrigem = null;
        estado.navegandoEntreTelas = false;
        estado.builder = null;
    }

    // Desabilita campos da tela (para modo visualização).
    protected void desabilitarCampos() {
        // Implementação padrão vazia - subclasses podem sobrescrever
    }

    // Exibe mensagem de sucesso.
    protected void exibirSucesso(String mensagem) {
        exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", null, mensagem);
    }

    // Exibe mensagem de erro.
    protected void exibirErro(String mensagem) {
        exibirAlerta(Alert.AlertType.ERROR, "Erro", null, mensagem);
    }

    // Exibe aviso ao usuário.
    protected void exibirAviso(String mensagem) {
        exibirAlerta(Alert.AlertType.WARNING, "Aviso", null, mensagem);
    }

    // Exibe um alerta genérico.
    protected void exibirAlerta(Alert.AlertType tipo, String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    // Exibe confirmação e retorna se usuário clicou OK.
    protected boolean exibirConfirmacao(String titulo, String cabecalho, String conteudo) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle(titulo);
        confirmacao.setHeaderText(cabecalho);
        confirmacao.setContentText(conteudo);
        Optional<ButtonType> resultado = confirmacao.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    // Exibe mensagem de sucesso
    protected void exibirMensagemSucesso(String mensagem) {
        exibirSucesso(mensagem);
    }

    // Exibe mensagem de erro
    protected void exibirMensagemErro(String mensagem) {
        exibirErro(mensagem);
    }

    // Atualiza informações do usuário logado na interface (se aplicável).
    protected void atualizarUsuarioLogado() {
        try {
            Usuario usuario = br.com.amparoedu.backend.service.AuthService.getUsuarioLogado();
            if (usuario != null) {
                // Subclasses podem ter labels para nome e cargo
                // Esta implementação base não faz nada
            }
        } catch (Exception e) {
            // Silenciosamente ignora erros de atualização de usuário
        }
    }

    // Retorna o builder tipado do estado, se disponível.
    protected <B> B obterBuilder(Class<B> builderClass) {
        EstadoDocumento<T> estado = getEstado();
        if (estado.builder != null && builderClass.isInstance(estado.builder)) {
            return builderClass.cast(estado.builder);
        }
        return null;
    }

    //Armazena o builder no estado.
    protected void salvarBuilder(Object builder) {
        getEstado().builder = builder;
    }

    // Métodos Estáticos para Factory

    
    // Inicializa um novo documento.
    public static <T> void iniciarNovo(EstadoDocumento<T> estado, T novoDocumento) {
        estado.modoAtual = ModoDocumento.NOVA;
        estado.telaAtual = 1;
        estado.documentoCompartilhado = novoDocumento;
        estado.navegandoEntreTelas = false;
    }

    // Inicializa edição de documento existente.
    public static <T> void iniciarEdicao(EstadoDocumento<T> estado, T documento) {
        estado.modoAtual = ModoDocumento.EDICAO;
        estado.telaAtual = 1;
        estado.documentoCompartilhado = documento;
        estado.navegandoEntreTelas = false;
    }

    // Inicializa visualização de documento existente.
    public static <T> void iniciarVisualizacao(EstadoDocumento<T> estado, T documento) {
        estado.modoAtual = ModoDocumento.VISUALIZACAO;
        estado.telaAtual = 1;
        estado.documentoCompartilhado = documento;
        estado.navegandoEntreTelas = false;
    }

    // Define a turma de origem para retorno após conclusão.
    public static <T> void setTurmaOrigem(EstadoDocumento<T> estado, String turmaId) {
        estado.turmaIdOrigem = turmaId;
    }
}
