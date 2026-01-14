package br.com.amparoedu.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.RIService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RIController extends DocumentoControllerBase<RI> implements Initializable {

    // Estado estático compartilhado entre telas
    private static final EstadoDocumento<RI> ESTADO = new EstadoDocumento<>();

    // Serviço
    private final RIService riService = new RIService();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    // Controles RI - Tela 1
    @FXML
    private TextArea dadosFuncionais;
    @FXML
    private TextArea funcionalidadeCognitiva;
    @FXML
    private TextArea alfabetizacao;
    @FXML
    private Label validationMsg;
    @FXML
    private Button btnConcluir;

    // Controles RI - Tela 2
    @FXML
    private TextArea adaptacoesCurriculares;
    @FXML
    private TextArea participacaoAtividade;
    @FXML
    private TextArea autonomia;

    // Controles RI - Tela 3
    @FXML
    private TextArea interacaoProfessora;
    @FXML
    private TextArea atividadesVidaDiaria;

    // Controles comuns
    @FXML
    private Label nomeUsuario;
    @FXML
    private Label cargoUsuario;

    // ========== Implementação dos Métodos Abstratos ==========

    @Override
    protected EstadoDocumento<RI> getEstado() {
        return ESTADO;
    }

    @Override
    protected int getTotalTelas() {
        return 3;
    }

    @Override
    protected String getPrefixoTela() {
        return "relatorio-individual";
    }

    @Override
    protected RI criarNovoDocumento() {
        return new RI();
    }

    @Override
    protected int detectarTelaAtual() {
        if (dadosFuncionais != null) return 1;
        if (adaptacoesCurriculares != null) return 2;
        if (interacaoProfessora != null) return 3;
        return -1;
    }

    @Override
    protected void salvarDadosTelaAtual() {
        if (documentoAtual == null) return;

        // Tela 1
        if (dadosFuncionais != null) {
            documentoAtual.setDados_funcionais(dadosFuncionais.getText().trim());
        }
        if (funcionalidadeCognitiva != null) {
            documentoAtual.setFuncionalidade_cognitiva(funcionalidadeCognitiva.getText().trim());
        }
        if (alfabetizacao != null) {
            documentoAtual.setAlfabetizacao(alfabetizacao.getText().trim());
        }

        // Tela 2
        if (adaptacoesCurriculares != null) {
            documentoAtual.setAdaptacoes_curriculares(adaptacoesCurriculares.getText().trim());
        }
        if (participacaoAtividade != null) {
            documentoAtual.setParticipacao_atividade(participacaoAtividade.getText().trim());
        }
        if (autonomia != null) {
            documentoAtual.setAutonomia(autonomia.getText().trim());
        }

        // Tela 3
        if (interacaoProfessora != null) {
            documentoAtual.setInteracao_professora(interacaoProfessora.getText().trim());
        }
        if (atividadesVidaDiaria != null) {
            documentoAtual.setAtividades_vida_diaria(atividadesVidaDiaria.getText().trim());
        }
    }

    @Override
    protected void carregarDadosNaTela() {
        if (documentoAtual == null) return;

        // Tela 1
        if (dadosFuncionais != null && documentoAtual.getDados_funcionais() != null) {
            dadosFuncionais.setText(documentoAtual.getDados_funcionais());
        }
        if (funcionalidadeCognitiva != null && documentoAtual.getFuncionalidade_cognitiva() != null) {
            funcionalidadeCognitiva.setText(documentoAtual.getFuncionalidade_cognitiva());
        }
        if (alfabetizacao != null && documentoAtual.getAlfabetizacao() != null) {
            alfabetizacao.setText(documentoAtual.getAlfabetizacao());
        }

        // Tela 2
        if (adaptacoesCurriculares != null && documentoAtual.getAdaptacoes_curriculares() != null) {
            adaptacoesCurriculares.setText(documentoAtual.getAdaptacoes_curriculares());
        }
        if (participacaoAtividade != null && documentoAtual.getParticipacao_atividade() != null) {
            participacaoAtividade.setText(documentoAtual.getParticipacao_atividade());
        }
        if (autonomia != null && documentoAtual.getAutonomia() != null) {
            autonomia.setText(documentoAtual.getAutonomia());
        }

        // Tela 3
        if (interacaoProfessora != null && documentoAtual.getInteracao_professora() != null) {
            interacaoProfessora.setText(documentoAtual.getInteracao_professora());
        }
        if (atividadesVidaDiaria != null && documentoAtual.getAtividades_vida_diaria() != null) {
            atividadesVidaDiaria.setText(documentoAtual.getAtividades_vida_diaria());
        }

        // Atualiza usuário logado
        atualizarUsuarioLogado();
    }

    @Override
    protected boolean validarTelaAtual() {
        return true; // RI não tem validação específica entre telas
    }

    @Override
    protected void setEducandoIdNoDocumento(String educandoId) {
        if (documentoAtual != null) {
            documentoAtual.setEducando_id(educandoId);
        }
    }

    @Override
    protected String getEducandoIdDoDocumento() {
        return documentoAtual != null ? documentoAtual.getEducando_id() : null;
    }

    @Override
    protected String getNomeDocumento() {
        return "Relatório Individual";
    }

    @Override
    protected void desabilitarCampos() {
        desabilitarCampo(dadosFuncionais);
        desabilitarCampo(funcionalidadeCognitiva);
        desabilitarCampo(alfabetizacao);
        desabilitarCampo(adaptacoesCurriculares);
        desabilitarCampo(participacaoAtividade);
        desabilitarCampo(autonomia);
        desabilitarCampo(interacaoProfessora);
        desabilitarCampo(atividadesVidaDiaria);
        if (btnConcluir != null) btnConcluir.setDisable(true);
    }

    private void desabilitarCampo(javafx.scene.Node campo) {
        if (campo != null) campo.setDisable(true);
    }

    // ========== Ciclo de Vida ==========

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarBase();
    }

    // ========== Handlers de UI ==========

    @FXML
    private void btnConcluirClick() {
        EstadoDocumento<RI> estado = getEstado();
        if (estado.modoAtual == ModoDocumento.VISUALIZACAO) {
            exibirErro("Modo visualização: não é possível salvar.");
            return;
        }

        salvarDadosTelaAtual();

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Concluir Relatório Individual");
        alerta.setHeaderText("Deseja salvar o Relatório Individual agora?");
        alerta.setContentText("Todos os dados serão salvos no sistema.");
        var opcao = alerta.showAndWait();
        if (opcao.isEmpty() || opcao.get() != ButtonType.OK) {
            return;
        }

        String educandoId = documentoAtual.getEducando_id();

        try {
            Usuario usuarioLogado = AuthService.getUsuarioLogado();
            if (usuarioLogado == null) {
                exibirErro("Usuário não está logado. Faça login novamente.");
                return;
            }

            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirErro("Apenas professores podem criar Relatórios Individuais.");
                return;
            }

            documentoAtual.setProfessor_id(AuthService.getIdProfessorLogado());
            documentoAtual.setData_criacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

            boolean edicao = estado.modoAtual == ModoDocumento.EDICAO;
            boolean sucesso = edicao ? riService.atualizarRI(documentoAtual) : riService.cadastrarNovoRI(documentoAtual);

            if (sucesso) {
                exibirSucesso(edicao ? "Relatório Individual atualizado com sucesso!" : "Relatório Individual criado com sucesso!");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            limparEstado();
                            voltarComPopup(educandoId);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                exibirErro(edicao ? "Erro ao atualizar Relatório Individual." : "Erro ao cadastrar Relatório Individual.");
            }
        } catch (Exception e) {
            exibirErro("Erro ao salvar Relatório Individual: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }

    @FXML
    @Override
    protected void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Relatório Individual");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            String educandoId = documentoAtual != null ? documentoAtual.getEducando_id() : null;
            limparEstado();
            voltarComPopup(educandoId);
        }
    }

    @FXML
    private void btnCancelClick() {
        btnCancelarClick();
    }

    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    @FXML
    private void btnAlunosClick() {
        voltarParaTurma();
    }

    @FXML
    private void btnRelatoriosClick() {
        btnTurmasClick();
    }

    @FXML
    private void btnSeguinteClick() {
        navegarTela(1, null);
    }

    @FXML
    @Override
    protected void btnVoltarClick() {
        navegarTela(-1, null);
    }

    // ========== Métodos Auxiliares ==========

    @Override
    protected void atualizarUsuarioLogado() {
        try {
            Usuario usuario = AuthService.getUsuarioLogado();
            if (usuario != null) {
                if (nomeUsuario != null) nomeUsuario.setText(usuario.getEmail());
                if (cargoUsuario != null) cargoUsuario.setText(usuario.getTipo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void voltarComPopup(String educandoId) {
        EstadoDocumento<RI> estado = getEstado();
        if (estado.turmaIdOrigem != null) {
            try {
                Turma turma = turmaRepo.buscarPorId(estado.turmaIdOrigem);
                if (turma != null) {
                    javafx.fxml.FXMLLoader loader = GerenciadorTelas.getLoader("view-turma.fxml");
                    javafx.scene.Parent root = loader.load();
                    ViewTurmaController controller = loader.getController();
                    controller.setTurma(turma);
                    GerenciadorTelas.setRaiz(root);

                    if (educandoId != null) {
                        Educando educando = educandoRepo.buscarPorId(educandoId);
                        if (educando != null) {
                            javafx.fxml.FXMLLoader popupLoader = GerenciadorTelas.getLoader("progresso-atendimento.fxml");
                            javafx.scene.Parent popupRoot = popupLoader.load();
                            ProgressoAtendimentoController popupController = popupLoader.getController();
                            popupController.setTurma(turma);
                            popupController.setEducando(educando);
                            GerenciadorTelas.getInstance().abrirPopup(popupRoot, "Progresso do Atendimento");
                        }
                    }
                    estado.turmaIdOrigem = null;
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        estado.turmaIdOrigem = null;
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    private void voltarParaTurma() {
        EstadoDocumento<RI> estado = getEstado();
        if (estado.turmaIdOrigem != null) {
            try {
                Turma turma = turmaRepo.buscarPorId(estado.turmaIdOrigem);
                if (turma != null) {
                    javafx.fxml.FXMLLoader loader = GerenciadorTelas.getLoader("view-turma.fxml");
                    javafx.scene.Parent root = loader.load();
                    ViewTurmaController controller = loader.getController();
                    controller.setTurma(turma);
                    GerenciadorTelas.setRaiz(root);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    // ========== Métodos Estáticos para Factory ==========

    public static void iniciarNovoRI() {
        iniciarNovo(ESTADO, new RI());
        GerenciadorTelas.getInstance().trocarTela("relatorio-individual-1.fxml");
    }

    public static void editarRIExistente(RI existente) {
        iniciarEdicao(ESTADO, existente != null ? existente : new RI());
        GerenciadorTelas.getInstance().trocarTela("relatorio-individual-1.fxml");
    }

    public static void visualizarRI(RI existente) {
        iniciarVisualizacao(ESTADO, existente);
        GerenciadorTelas.getInstance().trocarTela("relatorio-individual-1.fxml");
    }

    public static void setEducandoIdParaRI(String educandoId) {
        if (ESTADO.documentoCompartilhado == null) {
            ESTADO.documentoCompartilhado = new RI();
        }
        ESTADO.documentoCompartilhado.setEducando_id(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        setTurmaOrigem(ESTADO, turmaId);
    }
}

