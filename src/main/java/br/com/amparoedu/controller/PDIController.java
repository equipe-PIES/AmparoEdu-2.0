package br.com.amparoedu.controller;

import br.com.amparoedu.backend.builder.PDIBuilder;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.service.PDIService;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PDIController extends DocumentoControllerBase<PDI> implements Initializable {

    // Estado estático compartilhado entre telas
    private static final EstadoDocumento<PDI> ESTADO = new EstadoDocumento<>();
    // Evita múltiplos salvamentos caso o usuário clique mais de uma vez em "Concluir"
    private static boolean salvando = false;

    // Serviço
    private final PDIService pdiService = new PDIService();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    // Controles PDI - Tela 1
    @FXML
    private TextField periodoPlano;
    @FXML
    private TextField horarioAtendimento;
    @FXML
    private ChoiceBox<String> frequenciaSemana;
    @FXML
    private ChoiceBox<String> diasSemana;
    @FXML
    private ChoiceBox<String> composicaoGrupo;
    @FXML
    private TextArea objetivosPlano;
    @FXML
    private Label validationMsg;
    @FXML
    private Button btnConcluir;

    // Controles PDI - Tela 2
    @FXML
    private TextArea potencialidadesTextArea;
    @FXML
    private TextArea necessidadesTextArea;
    @FXML
    private TextArea habilidadesTextArea;

    // Controles PDI - Tela 3
    @FXML
    private TextArea atividadesTextArea;
    @FXML
    private TextArea recursosMateriaisTextArea;
    @FXML
    private TextArea recursosAdequacaoTextArea;

    // Controles PDI - Tela 4
    @FXML
    private TextArea recursosProduzidosTextArea;
    @FXML
    private TextArea parceriasTextArea;

    // Controles comuns
    @FXML
    private Label nomeUsuario;
    @FXML
    private Label cargoUsuario;

    // Implementação dos Métodos Abstratos

    @Override
    protected EstadoDocumento<PDI> getEstado() {
        return ESTADO;
    }

    @Override
    protected int getTotalTelas() {
        return 4;
    }

    @Override
    protected String getPrefixoTela() {
        return "pdi";
    }

    @Override
    protected PDI criarNovoDocumento() {
        return new PDI();
    }

    @Override
    protected int detectarTelaAtual() {
        if (periodoPlano != null) return 1;
        if (potencialidadesTextArea != null) return 2;
        if (atividadesTextArea != null) return 3;
        if (recursosProduzidosTextArea != null) return 4;
        return -1;
    }

    @Override
    protected void salvarDadosTelaAtual() {
        PDIBuilder builder = obterOuCriarBuilder();

        EstadoDocumento<PDI> estado = getEstado();
        if (estado.telaAtual == 1) {
            preencherDadosTela1(builder);
        } else if (estado.telaAtual == 2) {
            preencherDadosTela2(builder);
        } else if (estado.telaAtual == 3) {
            preencherDadosTela3(builder);
        } else if (estado.telaAtual == 4) {
            preencherDadosTela4(builder);
        }

        // Atualiza estado compartilhado com dados parciais
        ESTADO.documentoCompartilhado = builder.buildPartial();
        documentoAtual = ESTADO.documentoCompartilhado;
    }

    @Override
    protected void carregarDadosNaTela() {
        if (documentoAtual == null) return;

        // Tela 1
        if (periodoPlano != null && documentoAtual.getPeriodoAee() != null) {
            periodoPlano.setText(documentoAtual.getPeriodoAee());
        }
        if (horarioAtendimento != null && documentoAtual.getHorarioAtendimento() != null) {
            horarioAtendimento.setText(documentoAtual.getHorarioAtendimento());
        }
        if (frequenciaSemana != null && documentoAtual.getFrequenciaAtendimento() != null) {
            frequenciaSemana.setValue(documentoAtual.getFrequenciaAtendimento());
        }
        if (diasSemana != null && documentoAtual.getDiasAtendimento() != null) {
            diasSemana.setValue(documentoAtual.getDiasAtendimento());
        }
        if (composicaoGrupo != null && documentoAtual.getComposicaoGrupo() != null) {
            composicaoGrupo.setValue(documentoAtual.getComposicaoGrupo());
        }
        if (objetivosPlano != null && documentoAtual.getObjetivos() != null) {
            objetivosPlano.setText(documentoAtual.getObjetivos());
        }

        // Tela 2
        if (potencialidadesTextArea != null && documentoAtual.getPotencialidades() != null) {
            potencialidadesTextArea.setText(documentoAtual.getPotencialidades());
        }
        if (necessidadesTextArea != null && documentoAtual.getNecessidadesEspeciais() != null) {
            necessidadesTextArea.setText(documentoAtual.getNecessidadesEspeciais());
        }
        if (habilidadesTextArea != null && documentoAtual.getHabilidades() != null) {
            habilidadesTextArea.setText(documentoAtual.getHabilidades());
        }

        // Tela 3
        if (atividadesTextArea != null && documentoAtual.getAtividades() != null) {
            atividadesTextArea.setText(documentoAtual.getAtividades());
        }
        if (recursosMateriaisTextArea != null && documentoAtual.getRecursosMateriais() != null) {
            recursosMateriaisTextArea.setText(documentoAtual.getRecursosMateriais());
        }
        if (recursosAdequacaoTextArea != null && documentoAtual.getRecursosNecessitamAdaptacao() != null) {
            recursosAdequacaoTextArea.setText(documentoAtual.getRecursosNecessitamAdaptacao());
        }

        // Tela 4
        if (recursosProduzidosTextArea != null && documentoAtual.getRecursosNecessitamProduzir() != null) {
            recursosProduzidosTextArea.setText(documentoAtual.getRecursosNecessitamProduzir());
        }
        if (parceriasTextArea != null && documentoAtual.getParceriasNecessarias() != null) {
            parceriasTextArea.setText(documentoAtual.getParceriasNecessarias());
        }

        // Atualiza usuário logado
        atualizarUsuarioLogado();
    }

    @Override
    protected boolean validarTelaAtual() {
        return true;
    }

    @Override
    protected void setEducandoIdNoDocumento(String educandoId) {
        if (documentoAtual != null) {
            documentoAtual.setEducandoId(educandoId);
        }
    }

    @Override
    protected String getEducandoIdDoDocumento() {
        return documentoAtual != null ? documentoAtual.getEducandoId() : null;
    }

    @Override
    protected String getNomeDocumento() {
        return "PDI";
    }

    @Override
    protected void desabilitarCampos() {
        desabilitarCampo(periodoPlano);
        desabilitarCampo(horarioAtendimento);
        desabilitarCampo(objetivosPlano);
        desabilitarCampo(potencialidadesTextArea);
        desabilitarCampo(necessidadesTextArea);
        desabilitarCampo(habilidadesTextArea);
        desabilitarCampo(atividadesTextArea);
        desabilitarCampo(recursosMateriaisTextArea);
        desabilitarCampo(recursosAdequacaoTextArea);
        desabilitarCampo(recursosProduzidosTextArea);
        desabilitarCampo(parceriasTextArea);
        desabilitarCampo(frequenciaSemana);
        desabilitarCampo(diasSemana);
        desabilitarCampo(composicaoGrupo);
        if (btnConcluir != null) btnConcluir.setDisable(true);
    }

    // Ciclo de Vida

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarChoiceBoxes();
        inicializarBase();
    }

    // Inicializa os valores das ChoiceBoxes.
    private void inicializarChoiceBoxes() {
        if (frequenciaSemana != null) {
            frequenciaSemana.getItems().setAll("Uma vez", "Duas vezes");
        }

        if (diasSemana != null) {
            diasSemana.getItems().setAll("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira");
        }

        if (composicaoGrupo != null) {
            composicaoGrupo.getItems().setAll("Individual", "Coletivo");
        }
    }

    // Método auxiliar para desabilitar campos
    private void desabilitarCampo(javafx.scene.Node campo) {
        if (campo != null) {
            campo.setDisable(true);
        }
    }

    // Mensagens
    @Override
    protected void exibirMensagemErro(String mensagem) {
        if (validationMsg != null) {
            validationMsg.setText(mensagem);
            validationMsg.setStyle("-fx-text-fill: red;");
            validationMsg.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText(mensagem);
            alert.show();
        }
    }

    @Override
    protected void exibirMensagemSucesso(String mensagem) {
        if (validationMsg != null) {
            validationMsg.setText(mensagem);
            validationMsg.setStyle("-fx-text-fill: green;");
            validationMsg.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText(mensagem);
            alert.show();
        }
    }

    @FXML
    private void btnConcluirClick() {
        EstadoDocumento<PDI> estado = getEstado();
        if (estado.modoAtual == ModoDocumento.VISUALIZACAO) {
            exibirErro("Modo visualização: não é possível salvar.");
            return;
        }

        if (salvando) {
            return;
        }

        salvarDadosTelaAtual();

        // Valida todos os campos obrigatórios
        if (!validarTodosCampos()) {
            exibirErro("Preencha todos os campos obrigatórios para concluir.");
            return;
        }

        // Mostra aviso antes de salvar
        if (!exibirConfirmacao("Concluir PDI", 
                               "Deseja salvar o PDI agora?", 
                               "Todos os dados serão salvos no sistema.")) {
            return;
        }

        salvando = true;
        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }

        // Atualiza builder com a tela atual e documento compartilhado
        salvarDadosTelaAtual();
        String educandoId = documentoAtual != null ? documentoAtual.getEducandoId() : null;
        PDIBuilder builder = obterOuCriarBuilder();
        if (educandoId != null) {
            builder.comEducandoId(educandoId);
        }

        try {
            Usuario usuarioLogado = AuthService.getUsuarioLogado();
            if (usuarioLogado == null) {
                exibirErro("Usuário não está logado. Faça login novamente.");
                return;
            }

            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirErro("Apenas professores podem criar PDIs.");
                return;
            }

            builder.comProfessorId(AuthService.getIdProfessorLogado());
            if (estado.modoAtual == ModoDocumento.NOVA) {
                builder.comDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            } else if (documentoAtual != null) {
                String dataExistente = documentoAtual.getDataCriacao();
                if (dataExistente == null || dataExistente.isBlank()) {
                    builder.comDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                }
            }

            // Construção final com validação
            PDI pdiFinal = builder.build();
            ESTADO.documentoCompartilhado = pdiFinal;
            documentoAtual = pdiFinal;

            boolean edicao = estado.modoAtual == ModoDocumento.EDICAO;
            boolean sucesso = edicao ? pdiService.atualizarPDI(pdiFinal) : pdiService.cadastrarNovoPDI(pdiFinal);

            if (sucesso) {
                exibirSucesso(edicao ? "PDI atualizado com sucesso!" : "PDI criado com sucesso!");
                limparEstado();
                salvando = false;
                voltarComPopup(educandoId);
            } else {
                exibirErro(edicao ? "Erro ao atualizar PDI." : "Erro ao cadastrar PDI.");
                salvando = false;
                if (btnConcluir != null) {
                    btnConcluir.setDisable(false);
                }
            }
        } catch (Exception e) {
            exibirErro("Erro ao salvar PDI: " + e.getMessage());
            e.printStackTrace();
            salvando = false;
            if (btnConcluir != null) {
                btnConcluir.setDisable(false);
            }
        }
    }

    // Valida todos os campos obrigatórios do PDI
    private boolean validarTodosCampos() {
        if (documentoAtual == null) {
            return false;
        }

        // Validação da Tela 1
        if (documentoAtual.getPeriodoAee() == null || documentoAtual.getPeriodoAee().trim().isEmpty()) {
            return false;
        }
        if (documentoAtual.getHorarioAtendimento() == null
                || documentoAtual.getHorarioAtendimento().trim().isEmpty()) {
            return false;
        }
        if (documentoAtual.getFrequenciaAtendimento() == null
                || documentoAtual.getFrequenciaAtendimento().trim().isEmpty()) {
            return false;
        }
        if (documentoAtual.getDiasAtendimento() == null || documentoAtual.getDiasAtendimento().trim().isEmpty()) {
            return false;
        }
        if (documentoAtual.getComposicaoGrupo() == null || documentoAtual.getComposicaoGrupo().trim().isEmpty()) {
            System.out.println("Valor: " + documentoAtual.getComposicaoGrupo());
            return false;
        }
        if (documentoAtual.getObjetivos() == null || documentoAtual.getObjetivos().trim().isEmpty()) {
            System.out.println("Valor: " + documentoAtual.getObjetivos());
            return false;
        }
        return true;
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }

    @FXML
    @Override
    protected void btnCancelarClick() {
        super.btnCancelarClick();
    }

    @FXML
    private void btnCancelClick() {
        btnCancelarClick();
    }

    // Handlers genéricos referenciados pelo FXML
    @FXML
    protected void btnVoltarClick() {
        super.btnVoltarClick();
    }

    @FXML
    protected void btnSeguinteClick() {
        super.btnProximoClick();
    }

    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    @FXML
    private void btnAlunosClick() {
        voltarParaTurma();
    }

    // Métodos Auxiliares

    protected void voltarComPopup(String educandoId) {
        EstadoDocumento<PDI> estado = getEstado();
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
        EstadoDocumento<PDI> estado = getEstado();
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

    // Métodos Estáticos para Factory

    public static void iniciarNovoPDI() {
        salvando = false;
        // Preserva educandoId caso tenha sido definido antes do iniciarNovo()
        String educandoIdPreservado = null;
        if (ESTADO.documentoCompartilhado != null) {
            educandoIdPreservado = ESTADO.documentoCompartilhado.getEducandoId();
        }

        iniciarNovo(ESTADO, new PDI());
        ESTADO.builder = null;

        if (educandoIdPreservado != null) {
            ((PDI) ESTADO.documentoCompartilhado).setEducandoId(educandoIdPreservado);
        }
    }

    public static void editarPDIExistente(PDI existente) {
        salvando = false;
        iniciarEdicao(ESTADO, existente != null ? existente : new PDI());
        ESTADO.builder = new PDIBuilder((PDI) ESTADO.documentoCompartilhado);
    }

    public static void visualizarPDI(PDI existente) {
        salvando = false;
        iniciarVisualizacao(ESTADO, existente);
        ESTADO.builder = new PDIBuilder((PDI) ESTADO.documentoCompartilhado);
    }

    public static void setEducandoIdParaPDI(String educandoId) {
        if (ESTADO.documentoCompartilhado == null) {
            ESTADO.documentoCompartilhado = new PDI();
        }
        ESTADO.documentoCompartilhado.setEducandoId(educandoId);

        // Sempre garanta que o builder aponte para o documento compartilhado atual
        ESTADO.builder = new PDIBuilder((PDI) ESTADO.documentoCompartilhado);
        ((PDIBuilder) ESTADO.builder).comEducandoId(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        setTurmaOrigem(ESTADO, turmaId);
    }

    //  Builder helpers e preenchimento por tela

    private PDIBuilder obterOuCriarBuilder() {
        if (ESTADO.builder instanceof PDIBuilder) {
            return (PDIBuilder) ESTADO.builder;
        }

        PDI base = documentoAtual != null ? documentoAtual : new PDI();
        PDIBuilder builder = new PDIBuilder(base);
        String educandoId = getEducandoIdDoDocumento();
        if (educandoId != null) {
            builder.comEducandoId(educandoId);
        }
        ESTADO.builder = builder;
        return builder;
    }

    private void preencherDadosTela1(PDIBuilder builder) {
        if (periodoPlano != null) {
            builder.comPeriodoAee(periodoPlano.getText().trim());
        }
        if (horarioAtendimento != null) {
            builder.comHorarioAtendimento(horarioAtendimento.getText().trim());
        }
        if (frequenciaSemana != null && frequenciaSemana.getValue() != null) {
            builder.comFrequenciaAtendimento(frequenciaSemana.getValue());
        }
        if (diasSemana != null && diasSemana.getValue() != null) {
            builder.comDiasAtendimento(diasSemana.getValue());
        }
        if (composicaoGrupo != null && composicaoGrupo.getValue() != null) {
            builder.comComposicaoGrupo(composicaoGrupo.getValue());
        }
        if (objetivosPlano != null) {
            builder.comObjetivos(objetivosPlano.getText().trim());
        }
    }

    private void preencherDadosTela2(PDIBuilder builder) {
        if (potencialidadesTextArea != null) {
            builder.comPotencialidades(potencialidadesTextArea.getText().trim());
        }
        if (necessidadesTextArea != null) {
            builder.comNecessidadesEspeciais(necessidadesTextArea.getText().trim());
        }
        if (habilidadesTextArea != null) {
            builder.comHabilidades(habilidadesTextArea.getText().trim());
        }
    }

    private void preencherDadosTela3(PDIBuilder builder) {
        if (atividadesTextArea != null) {
            builder.comAtividades(atividadesTextArea.getText().trim());
        }
        if (recursosMateriaisTextArea != null) {
            builder.comRecursosMateriais(recursosMateriaisTextArea.getText().trim());
        }
        if (recursosAdequacaoTextArea != null) {
            builder.comRecursosNecessitamAdaptacao(recursosAdequacaoTextArea.getText().trim());
        }
    }

    private void preencherDadosTela4(PDIBuilder builder) {
        if (recursosProduzidosTextArea != null) {
            builder.comRecursosNecessitamProduzir(recursosProduzidosTextArea.getText().trim());
        }
        if (parceriasTextArea != null) {
            builder.comParceriasNecessarias(parceriasTextArea.getText().trim());
        }
    }
}