package br.com.amparoedu.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.builder.PAEEBuilder;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.PAEEService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class PAEEController extends DocumentoControllerBase<PAEE> implements Initializable {

    // Estado estático compartilhado entre telas
    private static final EstadoDocumento<PAEE> ESTADO = new EstadoDocumento<>();

    // Serviço
    private final PAEEService paeeService = new PAEEService();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    // Controles PAEE - Tela 1
    @FXML
    private TextArea resumoTela1;
    @FXML
    private ChoiceBox<String> dificuldadesMotoras;
    @FXML
    private ChoiceBox<String> dificuldadesCognitivas;
    @FXML
    private ChoiceBox<String> dificuldadesSensoriais;
    @FXML
    private ChoiceBox<String> dificuldadesComunicacao;
    @FXML
    private ChoiceBox<String> dificuldadesFamiliares;
    @FXML
    private ChoiceBox<String> dificuldadesAfetivas;
    @FXML
    private ChoiceBox<String> dificuldadesRaciocinio;
    @FXML
    private ChoiceBox<String> dificuldadesAvas;
    @FXML
    private Label validationMsg;
    @FXML
    private Button btnConcluir;

    // Controles PAEE - Tela 2
    @FXML
    private TextArea difDesenvolvimentoMotor;
    @FXML
    private TextArea intervencoesMotor;
    @FXML
    private TextArea difComunicacaoLinguagem;
    @FXML
    private TextArea intervencoesComunicacao;

    // Controles PAEE - Tela 3
    @FXML
    private TextArea difRaciocinio;
    @FXML
    private TextArea intervencoesRaciocinio;
    @FXML
    private TextArea difAtencao;
    @FXML
    private TextArea intervencoesAtencao;

    // Controles PAEE - Tela 4
    @FXML
    private TextArea difMemoria;
    @FXML
    private TextArea intervencoesMemoria;
    @FXML
    private TextArea difPercepcao;
    @FXML
    private TextArea intervencoesPercepcao;

    // Controles PAEE - Tela 5
    @FXML
    private TextArea difSociabilidade;
    @FXML
    private TextArea intervencoesSociabilidade;
    @FXML
    private TextArea difAva;
    @FXML
    private TextArea intervencoesAva;

    // Controles PAEE - Tela 6
    @FXML
    private TextArea resumoObjetivoPlano;
    @FXML
    private ChoiceBox<String> atendimentoAee;
    @FXML
    private ChoiceBox<String> atendimentoPsicologo;
    @FXML
    private ChoiceBox<String> atendimentoFisioterapeuta;
    @FXML
    private ChoiceBox<String> atendimentoPsicopedagogo;
    @FXML
    private ChoiceBox<String> atendimentoTerapeutaOcupacional;
    @FXML
    private ChoiceBox<String> atendimentoEducacaoFisica;
    @FXML
    private ChoiceBox<String> atendimentoEstimulacaoPrecoce;

    // Controles comuns
    @FXML
    private Label nomeUsuario;
    @FXML
    private Label cargoUsuario;

    // ========== Implementação dos Métodos Abstratos ==========

    @Override
    protected EstadoDocumento<PAEE> getEstado() {
        return ESTADO;
    }

    @Override
    protected int getTotalTelas() {
        return 6;
    }

    @Override
    protected String getPrefixoTela() {
        return "paee";
    }

    @Override
    protected PAEE criarNovoDocumento() {
        return new PAEE();
    }

    @Override
    protected int detectarTelaAtual() {
        if (resumoTela1 != null) return 1;
        if (difDesenvolvimentoMotor != null) return 2;
        if (difRaciocinio != null) return 3;
        if (difMemoria != null) return 4;
        if (difSociabilidade != null) return 5;
        if (resumoObjetivoPlano != null) return 6;
        return -1;
    }

    @Override
    protected void salvarDadosTelaAtual() {
        // Usa o builder no estado para popular o documento
        PAEEBuilder builder = obterOuCriarBuilder();

        // Tela 1
        if (resumoTela1 != null) builder.comResumo(safeText(resumoTela1));
        if (dificuldadesMotoras != null && dificuldadesMotoras.getValue() != null) builder.comDificuldadesMotoras(dificuldadesMotoras.getValue());
        if (dificuldadesCognitivas != null && dificuldadesCognitivas.getValue() != null) builder.comDificuldadesCognitivas(dificuldadesCognitivas.getValue());
        if (dificuldadesSensoriais != null && dificuldadesSensoriais.getValue() != null) builder.comDificuldadesSensoriais(dificuldadesSensoriais.getValue());
        if (dificuldadesComunicacao != null && dificuldadesComunicacao.getValue() != null) builder.comDificuldadesComunicacao(dificuldadesComunicacao.getValue());
        if (dificuldadesFamiliares != null && dificuldadesFamiliares.getValue() != null) builder.comDificuldadesFamiliares(dificuldadesFamiliares.getValue());
        if (dificuldadesAfetivas != null && dificuldadesAfetivas.getValue() != null) builder.comDificuldadesAfetivas(dificuldadesAfetivas.getValue());
        if (dificuldadesRaciocinio != null && dificuldadesRaciocinio.getValue() != null) builder.comDificuldadesRaciocinio(dificuldadesRaciocinio.getValue());
        if (dificuldadesAvas != null && dificuldadesAvas.getValue() != null) builder.comDificuldadesAvas(dificuldadesAvas.getValue());

        // Tela 2
        if (difDesenvolvimentoMotor != null) builder.comDifDesMotor(safeText(difDesenvolvimentoMotor));
        if (intervencoesMotor != null) builder.comIntervencoesMotor(safeText(intervencoesMotor));
        if (difComunicacaoLinguagem != null) builder.comDifComunicacao(safeText(difComunicacaoLinguagem));
        if (intervencoesComunicacao != null) builder.comIntervencoesComunicacao(safeText(intervencoesComunicacao));

        // Tela 3
        if (difRaciocinio != null) builder.comDifRaciocinio(safeText(difRaciocinio));
        if (intervencoesRaciocinio != null) builder.comIntervencoesRaciocinio(safeText(intervencoesRaciocinio));
        if (difAtencao != null) builder.comDifAtencao(safeText(difAtencao));
        if (intervencoesAtencao != null) builder.comIntervencoesAtencao(safeText(intervencoesAtencao));

        // Tela 4
        if (difMemoria != null) builder.comDifMemoria(safeText(difMemoria));
        if (intervencoesMemoria != null) builder.comIntervencoesMemoria(safeText(intervencoesMemoria));
        if (difPercepcao != null) builder.comDifPercepcao(safeText(difPercepcao));
        if (intervencoesPercepcao != null) builder.comIntervencoesPercepcao(safeText(intervencoesPercepcao));

        // Tela 5
        if (difSociabilidade != null) builder.comDifSociabilidade(safeText(difSociabilidade));
        if (intervencoesSociabilidade != null) builder.comIntervencoesSociabilidade(safeText(intervencoesSociabilidade));

        // Tela 6
        if (resumoObjetivoPlano != null) builder.comObjetivoPlano(safeText(resumoObjetivoPlano));
        if (atendimentoAee != null && atendimentoAee.getValue() != null) builder.comAee(atendimentoAee.getValue());
        if (atendimentoPsicologo != null && atendimentoPsicologo.getValue() != null) builder.comPsicologo(atendimentoPsicologo.getValue());
        if (atendimentoFisioterapeuta != null && atendimentoFisioterapeuta.getValue() != null) builder.comFisioterapeuta(atendimentoFisioterapeuta.getValue());
        if (atendimentoPsicopedagogo != null && atendimentoPsicopedagogo.getValue() != null) builder.comPsicopedagogo(atendimentoPsicopedagogo.getValue());
        if (atendimentoTerapeutaOcupacional != null && atendimentoTerapeutaOcupacional.getValue() != null) builder.comTerapeutaOcupacional(atendimentoTerapeutaOcupacional.getValue());
        if (atendimentoEducacaoFisica != null && atendimentoEducacaoFisica.getValue() != null) builder.comEducacaoFisica(atendimentoEducacaoFisica.getValue());
        if (atendimentoEstimulacaoPrecoce != null && atendimentoEstimulacaoPrecoce.getValue() != null) builder.comEstimulacaoPrecoce(atendimentoEstimulacaoPrecoce.getValue());

        // Atualiza documento compartilhado a partir do builder (sem validação durante navegação)
        ESTADO.documentoCompartilhado = builder.buildPartial();
        documentoAtual = ESTADO.documentoCompartilhado;
    }

    private String safeText(TextArea area) {
        return area.getText() != null ? area.getText().trim() : "";
    }

    private PAEEBuilder obterOuCriarBuilder() {
        if (ESTADO.builder instanceof PAEEBuilder) {
            return (PAEEBuilder) ESTADO.builder;
        }
        // Inicializa builder com o documento atual (para preservar dados)
        PAEEBuilder builder = new PAEEBuilder(documentoAtual != null ? documentoAtual : new PAEE());
        // Garante que o EducandoId já conhecido seja mantido
        String educandoId = getEducandoIdDoDocumento();
        if (educandoId != null) {
            builder.comEducandoId(educandoId);
        }
        ESTADO.builder = builder;
        return builder;
    }

    @Override
    protected void carregarDadosNaTela() {
        if (documentoAtual == null) return;

        // Tela 1
        if (resumoTela1 != null && documentoAtual.getResumo() != null) resumoTela1.setText(documentoAtual.getResumo());
        if (dificuldadesMotoras != null && documentoAtual.getDificuldadesMotoras() != null) dificuldadesMotoras.setValue(documentoAtual.getDificuldadesMotoras());
        if (dificuldadesCognitivas != null && documentoAtual.getDificuldadesCognitivas() != null) dificuldadesCognitivas.setValue(documentoAtual.getDificuldadesCognitivas());
        if (dificuldadesSensoriais != null && documentoAtual.getDificuldadesSensoriais() != null) dificuldadesSensoriais.setValue(documentoAtual.getDificuldadesSensoriais());
        if (dificuldadesComunicacao != null && documentoAtual.getDificuldadesComunicacao() != null) dificuldadesComunicacao.setValue(documentoAtual.getDificuldadesComunicacao());
        if (dificuldadesFamiliares != null && documentoAtual.getDificuldadesFamiliares() != null) dificuldadesFamiliares.setValue(documentoAtual.getDificuldadesFamiliares());
        if (dificuldadesAfetivas != null && documentoAtual.getDificuldadesAfetivas() != null) dificuldadesAfetivas.setValue(documentoAtual.getDificuldadesAfetivas());
        if (dificuldadesRaciocinio != null && documentoAtual.getDificuldadesRaciocinio() != null) dificuldadesRaciocinio.setValue(documentoAtual.getDificuldadesRaciocinio());
        if (dificuldadesAvas != null && documentoAtual.getDificuldadesAvas() != null) dificuldadesAvas.setValue(documentoAtual.getDificuldadesAvas());

        // Tela 2
        if (difDesenvolvimentoMotor != null && documentoAtual.getDifDesMotor() != null) difDesenvolvimentoMotor.setText(documentoAtual.getDifDesMotor());
        if (intervencoesMotor != null && documentoAtual.getIntervencoesMotor() != null) intervencoesMotor.setText(documentoAtual.getIntervencoesMotor());
        if (difComunicacaoLinguagem != null && documentoAtual.getDifComunicacao() != null) difComunicacaoLinguagem.setText(documentoAtual.getDifComunicacao());
        if (intervencoesComunicacao != null && documentoAtual.getIntervencoesComunicacao() != null) intervencoesComunicacao.setText(documentoAtual.getIntervencoesComunicacao());

        // Tela 3
        if (difRaciocinio != null && documentoAtual.getDifRaciocinio() != null) difRaciocinio.setText(documentoAtual.getDifRaciocinio());
        if (intervencoesRaciocinio != null && documentoAtual.getIntervencoesRaciocinio() != null) intervencoesRaciocinio.setText(documentoAtual.getIntervencoesRaciocinio());
        if (difAtencao != null && documentoAtual.getDifAtencao() != null) difAtencao.setText(documentoAtual.getDifAtencao());
        if (intervencoesAtencao != null && documentoAtual.getIntervencoesAtencao() != null) intervencoesAtencao.setText(documentoAtual.getIntervencoesAtencao());

        // Tela 4
        if (difMemoria != null && documentoAtual.getDifMemoria() != null) difMemoria.setText(documentoAtual.getDifMemoria());
        if (intervencoesMemoria != null && documentoAtual.getIntervencoesMemoria() != null) intervencoesMemoria.setText(documentoAtual.getIntervencoesMemoria());
        if (difPercepcao != null && documentoAtual.getDifPercepcao() != null) difPercepcao.setText(documentoAtual.getDifPercepcao());
        if (intervencoesPercepcao != null && documentoAtual.getIntervencoesPercepcao() != null) intervencoesPercepcao.setText(documentoAtual.getIntervencoesPercepcao());

        // Tela 5
        if (difSociabilidade != null && documentoAtual.getDifSociabilidade() != null) difSociabilidade.setText(documentoAtual.getDifSociabilidade());
        if (intervencoesSociabilidade != null && documentoAtual.getIntervencoesSociabilidade() != null) intervencoesSociabilidade.setText(documentoAtual.getIntervencoesSociabilidade());

        // Tela 6
        if (resumoObjetivoPlano != null && documentoAtual.getObjetivoPlano() != null) resumoObjetivoPlano.setText(documentoAtual.getObjetivoPlano());
        if (atendimentoAee != null && documentoAtual.getAee() != null) atendimentoAee.setValue(documentoAtual.getAee());
        if (atendimentoPsicologo != null && documentoAtual.getPsicologo() != null) atendimentoPsicologo.setValue(documentoAtual.getPsicologo());
        if (atendimentoFisioterapeuta != null && documentoAtual.getFisioterapeuta() != null) atendimentoFisioterapeuta.setValue(documentoAtual.getFisioterapeuta());
        if (atendimentoPsicopedagogo != null && documentoAtual.getPsicopedagogo() != null) atendimentoPsicopedagogo.setValue(documentoAtual.getPsicopedagogo());
        if (atendimentoTerapeutaOcupacional != null && documentoAtual.getTerapeutaOcupacional() != null) atendimentoTerapeutaOcupacional.setValue(documentoAtual.getTerapeutaOcupacional());
        if (atendimentoEducacaoFisica != null && documentoAtual.getEducacaoFisica() != null) atendimentoEducacaoFisica.setValue(documentoAtual.getEducacaoFisica());
        if (atendimentoEstimulacaoPrecoce != null && documentoAtual.getEstimulacaoPrecoce() != null) atendimentoEstimulacaoPrecoce.setValue(documentoAtual.getEstimulacaoPrecoce());

        // Atualiza usuário logado
        atualizarUsuarioLogado();
    }

    @Override
    protected boolean validarTelaAtual() {
        return true; // PAEE não tem validação específica entre telas
    }

    @Override
    protected void setEducandoIdNoDocumento(String educandoId) {
        if (documentoAtual != null) {
            documentoAtual.setEducando_id(educandoId);
        }
    }

    @Override
    protected String getEducandoIdDoDocumento() {
        return documentoAtual != null ? documentoAtual.getEducandoId() : null;
    }

    @Override
    protected String getNomeDocumento() {
        return "PAEE";
    }

    @Override
    protected void desabilitarCampos() {
        desabilitarCampo(resumoTela1);
        desabilitarCampo(dificuldadesMotoras);
        desabilitarCampo(dificuldadesCognitivas);
        desabilitarCampo(dificuldadesSensoriais);
        desabilitarCampo(dificuldadesComunicacao);
        desabilitarCampo(dificuldadesFamiliares);
        desabilitarCampo(dificuldadesAfetivas);
        desabilitarCampo(dificuldadesRaciocinio);
        desabilitarCampo(dificuldadesAvas);
        desabilitarCampo(difDesenvolvimentoMotor);
        desabilitarCampo(intervencoesMotor);
        desabilitarCampo(difComunicacaoLinguagem);
        desabilitarCampo(intervencoesComunicacao);
        desabilitarCampo(difRaciocinio);
        desabilitarCampo(intervencoesRaciocinio);
        desabilitarCampo(difAtencao);
        desabilitarCampo(intervencoesAtencao);
        desabilitarCampo(difMemoria);
        desabilitarCampo(intervencoesMemoria);
        desabilitarCampo(difPercepcao);
        desabilitarCampo(intervencoesPercepcao);
        desabilitarCampo(difSociabilidade);
        desabilitarCampo(intervencoesSociabilidade);
        desabilitarCampo(resumoObjetivoPlano);
        desabilitarCampo(atendimentoAee);
        desabilitarCampo(atendimentoPsicologo);
        desabilitarCampo(atendimentoFisioterapeuta);
        desabilitarCampo(atendimentoPsicopedagogo);
        desabilitarCampo(atendimentoTerapeutaOcupacional);
        desabilitarCampo(atendimentoEducacaoFisica);
        desabilitarCampo(atendimentoEstimulacaoPrecoce);
        if (btnConcluir != null) btnConcluir.setDisable(true);
    }

    private void desabilitarCampo(javafx.scene.Node campo) {
        if (campo != null) campo.setDisable(true);
    }

    // ========== Ciclo de Vida ==========

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarChoiceBoxes();
        inicializarBase();
    }

    // Inicializa os valores das ChoiceBoxes
    private void inicializarChoiceBoxes() {
        // Tela 1 - Dificuldades (Sim/Não)
        if (dificuldadesMotoras != null) {
            dificuldadesMotoras.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesCognitivas != null) {
            dificuldadesCognitivas.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesSensoriais != null) {
            dificuldadesSensoriais.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesComunicacao != null) {
            dificuldadesComunicacao.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesFamiliares != null) {
            dificuldadesFamiliares.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesAfetivas != null) {
            dificuldadesAfetivas.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesRaciocinio != null) {
            dificuldadesRaciocinio.getItems().setAll("Sim", "Não");
        }
        if (dificuldadesAvas != null) {
            dificuldadesAvas.getItems().setAll("Sim", "Não");
        }

        // Tela 6 - Atendimentos (Sim/Não)
        if (atendimentoAee != null) {
            atendimentoAee.getItems().setAll("Sim", "Não");
        }
        if (atendimentoPsicologo != null) {
            atendimentoPsicologo.getItems().setAll("Sim", "Não");
        }
        if (atendimentoFisioterapeuta != null) {
            atendimentoFisioterapeuta.getItems().setAll("Sim", "Não");
        }
        if (atendimentoPsicopedagogo != null) {
            atendimentoPsicopedagogo.getItems().setAll("Sim", "Não");
        }
        if (atendimentoTerapeutaOcupacional != null) {
            atendimentoTerapeutaOcupacional.getItems().setAll("Sim", "Não");
        }
        if (atendimentoEducacaoFisica != null) {
            atendimentoEducacaoFisica.getItems().setAll("Sim", "Não");
        }
        if (atendimentoEstimulacaoPrecoce != null) {
            atendimentoEstimulacaoPrecoce.getItems().setAll("Sim", "Não");
        }
    }

    // Handlers de UI
    @FXML
    private void btnConcluirClick() {
        if (ESTADO.modoAtual == ModoDocumento.VISUALIZACAO) {
            exibirMensagemErro("Modo visualização: não é possível salvar.");
            return;
        }

        salvarDadosTelaAtual();

        // Mostra aviso antes de salvar
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Concluir PAEE");
        alerta.setHeaderText("Deseja salvar o PAEE agora?");
        alerta.setContentText("Todos os dados serão salvos no sistema.");
        var opcao = alerta.showAndWait();
        if (opcao.isEmpty() || opcao.get() != ButtonType.OK) {
            return;
        }

        // Garante que o builder tem os dados mais recentes
        salvarDadosTelaAtual();
        PAEEBuilder builder = obterOuCriarBuilder();

        try {
            // 1. Obtém o usuário logado
            Usuario usuarioLogado = AuthService.getUsuarioLogado();

            if (usuarioLogado == null) {
                exibirMensagemErro("Usuário não está logado. Faça login novamente.");
                return;
            }

            // 2. Verifica se é professor
            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirMensagemErro("Apenas professores podem criar PAEEs. Tipo do usuário: " + usuarioLogado.getTipo());
                return;
            }

            // 3. Garante que o educandoId está no builder antes de validar
            String educandoId = getEducandoIdDoDocumento();
            if (educandoId == null) {
                exibirMensagemErro("Educando não foi definido. Impossível salvar o PAEE.");
                return;
            }
            builder.comEducandoId(educandoId);

            // 4. Define o ID do USUÁRIO e metadados via builder
            builder
                .comProfessorId(getIdProfessorLogado())
                .comDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

            // Constrói o documento final a partir do builder
            PAEE documentoFinal = builder.build();
            ESTADO.documentoCompartilhado = documentoFinal;
            documentoAtual = documentoFinal;

            boolean sucesso;
            boolean edicao = ESTADO.modoAtual == ModoDocumento.EDICAO;
            sucesso = edicao
                    ? paeeService.atualizarPAEE(documentoFinal)
                    : paeeService.cadastrarNovaPAEE(documentoFinal);

            if (sucesso) {
                exibirMensagemSucesso(edicao ? "PAEE atualizado com sucesso!" : "PAEE criado com sucesso!");
                limparEstado();
                voltarComPopup(educandoId);
            } else {
                exibirMensagemErro(
                        edicao ? "Erro ao atualizar PAEE. Tente novamente." : "Erro ao cadastrar PAEE. Tente novamente.");
            }

        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar PAEE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Handlers genéricos referenciados pelo FXML
    @FXML
    protected void btnCancelarClick() {
        super.btnCancelarClick();
    }

    @FXML
    protected void btnVoltarClick() {
        super.btnVoltarClick();
    }

    @FXML
    protected void btnSeguinteClick() {
        super.btnProximoClick();
    }

    private String getIdProfessorLogado() {
        return AuthService.getIdProfessorLogado();
    }

    // Handler para o botão Sair - fecha a janela/volta para a tela anterior
    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }



    // Volta para a turma com popup do educando
    private void voltarComPopup(String educandoId) {
        if (ESTADO.turmaIdOrigem != null) {
            try {
                TurmaRepository turmaRepo = new TurmaRepository();
                Turma turma = turmaRepo.buscarPorId(ESTADO.turmaIdOrigem);

                if (turma != null) {
                    javafx.fxml.FXMLLoader loader = GerenciadorTelas.getLoader("view-turma.fxml");
                    javafx.scene.Parent root = loader.load();
                    ViewTurmaController controller = loader.getController();
                    controller.setTurma(turma);
                    GerenciadorTelas.setRaiz(root);

                    if (educandoId != null) {
                        Educando educando = educandoRepo.buscarPorId(educandoId);

                        if (educando != null) {
                            javafx.fxml.FXMLLoader popupLoader = GerenciadorTelas
                                    .getLoader("progresso-atendimento.fxml");
                            javafx.scene.Parent popupRoot = popupLoader.load();
                            ProgressoAtendimentoController popupController = popupLoader.getController();
                            popupController.setTurma(turma);
                            popupController.setEducando(educando);
                            GerenciadorTelas.getInstance().abrirPopup(popupRoot, "Progresso do Atendimento");
                        }
                    }
                    ESTADO.turmaIdOrigem = null;
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ESTADO.turmaIdOrigem = null;
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    // Handler para o botão Turmas - navega para a tela de turmas
    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    // Handler para o botão Alunos - navega para a tela de alunos
    @FXML
    private void btnAlunosClick() {
        voltarParaTurma();
    }

    // Método auxiliar para voltar à turma de origem
    private void voltarParaTurma() {
        if (ESTADO.turmaIdOrigem != null) {
            try {
                TurmaRepository turmaRepo = new TurmaRepository();
                Turma turma = turmaRepo.buscarPorId(ESTADO.turmaIdOrigem);

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



    // Métodos de fluxo - delega para a classe base
    public static void iniciarNovoPAEE() {
        // Preserva educandoId se já foi definido antes desta chamada
        String educandoIdAnterior = null;
        if (ESTADO.documentoCompartilhado != null) {
            educandoIdAnterior = ESTADO.documentoCompartilhado.getEducandoId();
        }
        
        ESTADO.modoAtual = ModoDocumento.NOVA;
        ESTADO.telaAtual = 1;
        PAEE novoPAEE = new PAEE();
        
        // Restaura o educandoId se havia um antes
        if (educandoIdAnterior != null) {
            novoPAEE.setEducando_id(educandoIdAnterior);
        }
        
        ESTADO.documentoCompartilhado = novoPAEE;
        ESTADO.builder = null; // Builder será criado ao acessar a primeira tela
        ESTADO.navegandoEntreTelas = false;
    }

    public static void editarPAEEExistente(PAEE existente) {
        ESTADO.modoAtual = ModoDocumento.EDICAO;
        ESTADO.telaAtual = 1;
        ESTADO.documentoCompartilhado = (existente != null) ? existente : new PAEE();
        ESTADO.builder = new PAEEBuilder((PAEE) ESTADO.documentoCompartilhado);
        ESTADO.navegandoEntreTelas = false;
    }

    public static void visualizarPAEE(PAEE existente) {
        ESTADO.modoAtual = ModoDocumento.VISUALIZACAO;
        ESTADO.telaAtual = 1;
        ESTADO.documentoCompartilhado = existente;
        ESTADO.builder = new PAEEBuilder((PAEE) ESTADO.documentoCompartilhado);
        ESTADO.navegandoEntreTelas = false;
    }

    public static void setEducandoIdParaPAEE(String educandoId) {
        if (ESTADO.documentoCompartilhado == null) {
            ESTADO.documentoCompartilhado = new PAEE();
        }
        ESTADO.documentoCompartilhado.setEducando_id(educandoId);
        if (!(ESTADO.builder instanceof PAEEBuilder)) {
            ESTADO.builder = new PAEEBuilder((PAEE) ESTADO.documentoCompartilhado);
        }
        ((PAEEBuilder) ESTADO.builder).comEducandoId(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        ESTADO.turmaIdOrigem = turmaId;
    }
}
