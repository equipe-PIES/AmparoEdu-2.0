package br.com.amparoedu.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
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

public class PAEEController implements Initializable {

    // Modo de uso
    public enum ModoPAEE {
        NOVA, EDICAO, VISUALIZACAO
    }

    // Estado e serviços
    private final PAEEService paeeService = new PAEEService();
    private PAEE paeeAtual = new PAEE();
    private static int telaAtual = 1; // 1, 2, 3, 4, 5 ou 6
    private static PAEE paeeCompartilhada;
    private static String turmaIdOrigem;
    private static ModoPAEE modoAtual = ModoPAEE.NOVA;
    private static boolean navegandoEntreTelas;

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

    // Ciclo de vida
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean vindoDeNavegacao = navegandoEntreTelas;
        navegandoEntreTelas = false;

        // Detecta qual tela está carregada pelos controles presentes
        if (resumoTela1 != null) {
            telaAtual = 1;
        } else if (difDesenvolvimentoMotor != null) {
            telaAtual = 2;
        } else if (difRaciocinio != null) {
            telaAtual = 3;
        } else if (difMemoria != null) {
            telaAtual = 4;
        } else if (difSociabilidade != null) {
            telaAtual = 5;
        } else if (resumoObjetivoPlano != null) {
            telaAtual = 6;
        }

        // Se não veio de navegação, prepara estado conforme o modo selecionado
        if (!vindoDeNavegacao) {
            if (modoAtual == ModoPAEE.NOVA) {
                telaAtual = 1;
                if (paeeCompartilhada == null) {
                    paeeCompartilhada = new PAEE();
                }
            } else {
                if (paeeCompartilhada == null) {
                    paeeCompartilhada = new PAEE();
                }
                telaAtual = 1;
            }
        } else if (telaAtual == 1 && paeeCompartilhada == null) {
            paeeCompartilhada = new PAEE();
        }

        // Usa a PAEE compartilhada
        if (paeeCompartilhada != null) {
            paeeAtual = paeeCompartilhada;
        }

        // Inicializa componentes
        inicializarChoiceBoxes();
        carregarDadosNaTela();
        desabilitarEdicaoSeVisualizacao();
    }

    // Inicializa os valores das ChoiceBoxes
    private void inicializarChoiceBoxes() {
        // Tela 1 - Dificuldades (Sim/Não)
        if (dificuldadesMotoras != null) {
            dificuldadesMotoras.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesCognitivas != null) {
            dificuldadesCognitivas.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesSensoriais != null) {
            dificuldadesSensoriais.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesComunicacao != null) {
            dificuldadesComunicacao.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesFamiliares != null) {
            dificuldadesFamiliares.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesAfetivas != null) {
            dificuldadesAfetivas.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesRaciocinio != null) {
            dificuldadesRaciocinio.getItems().addAll("Sim", "Não");
        }
        if (dificuldadesAvas != null) {
            dificuldadesAvas.getItems().addAll("Sim", "Não");
        }

        // Tela 6 - Atendimentos (Sim/Não)
        if (atendimentoAee != null) {
            atendimentoAee.getItems().addAll("Sim", "Não");
        }
        if (atendimentoPsicologo != null) {
            atendimentoPsicologo.getItems().addAll("Sim", "Não");
        }
        if (atendimentoFisioterapeuta != null) {
            atendimentoFisioterapeuta.getItems().addAll("Sim", "Não");
        }
        if (atendimentoPsicopedagogo != null) {
            atendimentoPsicopedagogo.getItems().addAll("Sim", "Não");
        }
        if (atendimentoTerapeutaOcupacional != null) {
            atendimentoTerapeutaOcupacional.getItems().addAll("Sim", "Não");
        }
        if (atendimentoEducacaoFisica != null) {
            atendimentoEducacaoFisica.getItems().addAll("Sim", "Não");
        }
        if (atendimentoEstimulacaoPrecoce != null) {
            atendimentoEstimulacaoPrecoce.getItems().addAll("Sim", "Não");
        }
    }

    // Desabilita edição se estiver em modo visualização
    private void desabilitarEdicaoSeVisualizacao() {
        if (modoAtual != ModoPAEE.VISUALIZACAO)
            return;

        // Desabilita campos textuais
        desabilitarCampo(resumoTela1);
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
        desabilitarCampo(difAva);
        desabilitarCampo(intervencoesAva);
        desabilitarCampo(resumoObjetivoPlano);

        // Desabilita ChoiceBoxes
        desabilitarCampo(dificuldadesMotoras);
        desabilitarCampo(dificuldadesCognitivas);
        desabilitarCampo(dificuldadesSensoriais);
        desabilitarCampo(dificuldadesComunicacao);
        desabilitarCampo(dificuldadesFamiliares);
        desabilitarCampo(dificuldadesAfetivas);
        desabilitarCampo(dificuldadesRaciocinio);
        desabilitarCampo(dificuldadesAvas);
        desabilitarCampo(atendimentoAee);
        desabilitarCampo(atendimentoPsicologo);
        desabilitarCampo(atendimentoFisioterapeuta);
        desabilitarCampo(atendimentoPsicopedagogo);
        desabilitarCampo(atendimentoTerapeutaOcupacional);
        desabilitarCampo(atendimentoEducacaoFisica);
        desabilitarCampo(atendimentoEstimulacaoPrecoce);

        // Desabilita botão de conclusão
        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }
    }

    // Método auxiliar para desabilitar campos
    private void desabilitarCampo(javafx.scene.Node campo) {
        if (campo != null) {
            campo.setDisable(true);
        }
    }

    // Carrega dados do PAEE atual na tela
    private void carregarDadosNaTela() {
        if (paeeAtual == null)
            return;

        // Tela 1
        if (resumoTela1 != null && paeeAtual.getResumo() != null) {
            resumoTela1.setText(paeeAtual.getResumo());
        }
        if (dificuldadesMotoras != null) {
            String valor = paeeAtual.getDificuldadesMotoras();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesMotoras.setValue(valor);
            }
        }
        if (dificuldadesCognitivas != null) {
            String valor = paeeAtual.getDificuldadesCognitivas();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesCognitivas.setValue(valor);
            }
        }
        if (dificuldadesSensoriais != null) {
            String valor = paeeAtual.getDificuldadesSensoriais();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesSensoriais.setValue(valor);
            }
        }
        if (dificuldadesComunicacao != null) {
            String valor = paeeAtual.getDificuldadesComunicacao();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesComunicacao.setValue(valor);
            }
        }
        if (dificuldadesFamiliares != null) {
            String valor = paeeAtual.getDificuldadesFamiliares();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesFamiliares.setValue(valor);
            }
        }
        if (dificuldadesAfetivas != null) {
            String valor = paeeAtual.getDificuldadesAfetivas();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesAfetivas.setValue(valor);
            }
        }
        if (dificuldadesRaciocinio != null) {
            String valor = paeeAtual.getDificuldadesRaciocinio();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesRaciocinio.setValue(valor);
            }
        }
        if (dificuldadesAvas != null) {
            String valor = paeeAtual.getDificuldadesAvas();
            if (valor != null && !valor.isEmpty()) {
                dificuldadesAvas.setValue(valor);
            }
        }

        // Tela 2
        if (difDesenvolvimentoMotor != null && paeeAtual.getDifDesMotor() != null) {
            difDesenvolvimentoMotor.setText(paeeAtual.getDifDesMotor());
        }
        if (intervencoesMotor != null && paeeAtual.getIntervencoesMotor() != null) {
            intervencoesMotor.setText(paeeAtual.getIntervencoesMotor());
        }
        if (difComunicacaoLinguagem != null && paeeAtual.getDifComunicacao() != null) {
            difComunicacaoLinguagem.setText(paeeAtual.getDifComunicacao());
        }
        if (intervencoesComunicacao != null && paeeAtual.getIntervencoesComunicacao() != null) {
            intervencoesComunicacao.setText(paeeAtual.getIntervencoesComunicacao());
        }

        // Tela 3
        if (difRaciocinio != null && paeeAtual.getDifRaciocinio() != null) {
            difRaciocinio.setText(paeeAtual.getDifRaciocinio());
        }
        if (intervencoesRaciocinio != null && paeeAtual.getIntervencoesRaciocinio() != null) {
            intervencoesRaciocinio.setText(paeeAtual.getIntervencoesRaciocinio());
        }
        if (difAtencao != null && paeeAtual.getDifAtencao() != null) {
            difAtencao.setText(paeeAtual.getDifAtencao());
        }
        if (intervencoesAtencao != null && paeeAtual.getIntervencoesAtencao() != null) {
            intervencoesAtencao.setText(paeeAtual.getIntervencoesAtencao());
        }

        // Tela 4
        if (difMemoria != null && paeeAtual.getDifMemoria() != null) {
            difMemoria.setText(paeeAtual.getDifMemoria());
        }
        if (intervencoesMemoria != null && paeeAtual.getIntervencoesMemoria() != null) {
            intervencoesMemoria.setText(paeeAtual.getIntervencoesMemoria());
        }
        if (difPercepcao != null && paeeAtual.getDifPercepcao() != null) {
            difPercepcao.setText(paeeAtual.getDifPercepcao());
        }
        if (intervencoesPercepcao != null && paeeAtual.getIntervencoesPercepcao() != null) {
            intervencoesPercepcao.setText(paeeAtual.getIntervencoesPercepcao());
        }

        // Tela 5
        if (difSociabilidade != null && paeeAtual.getDifSociabilidade() != null) {
            difSociabilidade.setText(paeeAtual.getDifSociabilidade());
        }
        if (intervencoesSociabilidade != null && paeeAtual.getIntervencoesSociabilidade() != null) {
            intervencoesSociabilidade.setText(paeeAtual.getIntervencoesSociabilidade());
        }
        // OBS: AVA não tem campo específico no modelo

        // Tela 6
        if (resumoObjetivoPlano != null && paeeAtual.getObjetivoPlano() != null) {
            resumoObjetivoPlano.setText(paeeAtual.getObjetivoPlano());
        }
        if (atendimentoAee != null && paeeAtual.getAee() != null && !paeeAtual.getAee().isEmpty()) {
            atendimentoAee.setValue(paeeAtual.getAee());
        }
        if (atendimentoPsicologo != null && paeeAtual.getPsicologo() != null && !paeeAtual.getPsicologo().isEmpty()) {
            atendimentoPsicologo.setValue(paeeAtual.getPsicologo());
        }
        if (atendimentoFisioterapeuta != null && paeeAtual.getFisioterapeuta() != null && !paeeAtual.getFisioterapeuta().isEmpty()) {
            atendimentoFisioterapeuta.setValue(paeeAtual.getFisioterapeuta());
        }
        if (atendimentoPsicopedagogo != null && paeeAtual.getPsicopedagogo() != null && !paeeAtual.getPsicopedagogo().isEmpty()) {
            atendimentoPsicopedagogo.setValue(paeeAtual.getPsicopedagogo());
        }
        if (atendimentoTerapeutaOcupacional != null && paeeAtual.getTerapeutaOcupacional() != null && !paeeAtual.getTerapeutaOcupacional().isEmpty()) {
            atendimentoTerapeutaOcupacional.setValue(paeeAtual.getTerapeutaOcupacional());
        }
        if (atendimentoEducacaoFisica != null && paeeAtual.getEducacaoFisica() != null && !paeeAtual.getEducacaoFisica().isEmpty()) {
            atendimentoEducacaoFisica.setValue(paeeAtual.getEducacaoFisica());
        }
        if (atendimentoEstimulacaoPrecoce != null && paeeAtual.getEstimulacaoPrecoce() != null && !paeeAtual.getEstimulacaoPrecoce().isEmpty()) {
            atendimentoEstimulacaoPrecoce.setValue(paeeAtual.getEstimulacaoPrecoce());
        }
    }

    // Mensagens
    private void exibirMensagemErro(String mensagem) {
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

    private void exibirMensagemSucesso(String mensagem) {
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

    // Handlers de UI
    @FXML
    private void btnConcluirClick() {
        if (modoAtual == ModoPAEE.VISUALIZACAO) {
            exibirMensagemErro("Modo visualização: não é possível salvar.");
            return;
        }

        // Salva os dados da tela atual primeiro
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

        // Garante que o objeto compartilhado tenha os dados mais recentes
        paeeAtual = paeeCompartilhada;

        // Salva o educando ID antes de resetar
        String educandoId = paeeCompartilhada.getEducandoId();

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

            // 3. Define o ID do USUÁRIO no PAEE
            paeeCompartilhada.setProfessor_id(getIdProfessorLogado());

            // 4. Metadados obrigatórios
            paeeCompartilhada.setData_criacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

            boolean sucesso;
            boolean edicao = modoAtual == ModoPAEE.EDICAO;
            if (edicao) {
                sucesso = paeeService.atualizarPAEE(paeeCompartilhada);
            } else {
                sucesso = paeeService.cadastrarNovaPAEE(paeeCompartilhada);
            }

            if (sucesso) {
                exibirMensagemSucesso(edicao ? "PAEE atualizado com sucesso!" : "PAEE criado com sucesso!");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            resetarPAEE();
                            voltarComPopup(educandoId);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                exibirMensagemErro(
                        edicao ? "Erro ao atualizar PAEE. Tente novamente." : "Erro ao cadastrar PAEE. Tente novamente.");
            }

        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar PAEE: " + e.getMessage());
            e.printStackTrace();
        }
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

    // Handler para o botão Cancelar - cancela o processo de PAEE
    @FXML
    private void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar PAEE");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Salva o educando ID antes de resetar
            String educandoId = paeeAtual.getEducandoId();
            resetarPAEE();
            voltarComPopup(educandoId);
        }
    }

    // Volta para a turma com popup do educando
    private void voltarComPopup(String educandoId) {
        if (turmaIdOrigem != null) {
            try {
                TurmaRepository turmaRepo = new TurmaRepository();
                Turma turma = turmaRepo.buscarPorId(turmaIdOrigem);

                if (turma != null) {
                    javafx.fxml.FXMLLoader loader = GerenciadorTelas.getLoader("view-turma.fxml");
                    javafx.scene.Parent root = loader.load();
                    ViewTurmaController controller = loader.getController();
                    controller.setTurma(turma);
                    GerenciadorTelas.setRaiz(root);

                    if (educandoId != null) {
                        EducandoRepository educandoRepo = new EducandoRepository();
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
                    turmaIdOrigem = null;
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        turmaIdOrigem = null;
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

    private void resetarPAEE() {
        telaAtual = 1;
        paeeCompartilhada = null;
        paeeAtual = new PAEE();
    }

    // Método auxiliar para voltar à turma de origem
    private void voltarParaTurma() {
        if (turmaIdOrigem != null) {
            try {
                TurmaRepository turmaRepo = new TurmaRepository();
                Turma turma = turmaRepo.buscarPorId(turmaIdOrigem);

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

    // Handler para o botão Seguinte - navega para próxima tela
    @FXML
    private void btnSeguinteClick() {
        navegandoEntreTelas = true;

        // Salva os dados da tela atual antes de navegar
        salvarDadosTelaAtual();

        // Determina qual é a próxima tela
        switch (telaAtual) {
            case 1:
                GerenciadorTelas.getInstance().trocarTela("paee-2.fxml");
                break;
            case 2:
                GerenciadorTelas.getInstance().trocarTela("paee-3.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("paee-4.fxml");
                break;
            case 4:
                GerenciadorTelas.getInstance().trocarTela("paee-5.fxml");
                break;
            case 5:
                GerenciadorTelas.getInstance().trocarTela("paee-6.fxml");
                break;
        }
    }

    // Handler para o botão Voltar - navega para tela anterior
    @FXML
    private void btnVoltarClick() {
        navegandoEntreTelas = true;

        // Salva os dados da tela atual antes de navegar
        salvarDadosTelaAtual();

        // Determina qual é a tela anterior
        switch (telaAtual) {
            case 2:
                GerenciadorTelas.getInstance().trocarTela("paee-1.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("paee-2.fxml");
                break;
            case 4:
                GerenciadorTelas.getInstance().trocarTela("paee-3.fxml");
                break;
            case 5:
                GerenciadorTelas.getInstance().trocarTela("paee-4.fxml");
                break;
            case 6:
                GerenciadorTelas.getInstance().trocarTela("paee-5.fxml");
                break;
        }
    }

    // Salva os dados da tela atual no objeto compartilhado
    private void salvarDadosTelaAtual() {
        if (paeeCompartilhada == null) {
            System.out.println("DEBUG: paeeCompartilhada é null em salvarDadosTelaAtual");
            return;
        }

        System.out.println("DEBUG: Salvando dados da tela " + telaAtual);

        // Tela 1
        if (resumoTela1 != null) {
            String valor = resumoTela1.getText().trim();
            paeeCompartilhada.setResumo(valor);
        }
        if (dificuldadesMotoras != null) {
            paeeCompartilhada.setDificuldades_motoras(dificuldadesMotoras.getValue() != null ? dificuldadesMotoras.getValue() : "Não");
        }
        if (dificuldadesCognitivas != null) {
            paeeCompartilhada.setDificuldades_cognitivas(dificuldadesCognitivas.getValue() != null ? dificuldadesCognitivas.getValue() : "Não");
        }
        if (dificuldadesSensoriais != null) {
            paeeCompartilhada.setDificuldades_sensoriais(dificuldadesSensoriais.getValue() != null ? dificuldadesSensoriais.getValue() : "Não");
        }
        if (dificuldadesComunicacao != null) {
            paeeCompartilhada.setDificuldades_comunicacao(dificuldadesComunicacao.getValue() != null ? dificuldadesComunicacao.getValue() : "Não");
        }
        if (dificuldadesFamiliares != null) {
            paeeCompartilhada.setDificuldades_familiares(dificuldadesFamiliares.getValue() != null ? dificuldadesFamiliares.getValue() : "Não");
        }
        if (dificuldadesAfetivas != null) {
            paeeCompartilhada.setDificuldades_afetivas(dificuldadesAfetivas.getValue() != null ? dificuldadesAfetivas.getValue() : "Não");
        }
        if (dificuldadesRaciocinio != null) {
            paeeCompartilhada.setDificuldades_raciocinio(dificuldadesRaciocinio.getValue() != null ? dificuldadesRaciocinio.getValue() : "Não");
        }
        if (dificuldadesAvas != null) {
            paeeCompartilhada.setDificuldades_avas(dificuldadesAvas.getValue() != null ? dificuldadesAvas.getValue() : "Não");
        }

        // Tela 2
        if (difDesenvolvimentoMotor != null)
            paeeCompartilhada.setDif_des_motor(difDesenvolvimentoMotor.getText().trim());
        if (intervencoesMotor != null)
            paeeCompartilhada.setIntervencoes_motor(intervencoesMotor.getText().trim());
        if (difComunicacaoLinguagem != null)
            paeeCompartilhada.setDif_comunicacao(difComunicacaoLinguagem.getText().trim());
        if (intervencoesComunicacao != null)
            paeeCompartilhada.setIntervencoes_comunicacao(intervencoesComunicacao.getText().trim());

        // Tela 3
        if (difRaciocinio != null)
            paeeCompartilhada.setDif_raciocinio(difRaciocinio.getText().trim());
        if (intervencoesRaciocinio != null)
            paeeCompartilhada.setIntervencoes_raciocinio(intervencoesRaciocinio.getText().trim());
        if (difAtencao != null)
            paeeCompartilhada.setDif_atencao(difAtencao.getText().trim());
        if (intervencoesAtencao != null)
            paeeCompartilhada.setIntervencoes_atencao(intervencoesAtencao.getText().trim());

        // Tela 4
        if (difMemoria != null)
            paeeCompartilhada.setDif_memoria(difMemoria.getText().trim());
        if (intervencoesMemoria != null)
            paeeCompartilhada.setIntervencoes_memoria(intervencoesMemoria.getText().trim());
        if (difPercepcao != null)
            paeeCompartilhada.setDif_percepcao(difPercepcao.getText().trim());
        if (intervencoesPercepcao != null)
            paeeCompartilhada.setIntervencoes_percepcao(intervencoesPercepcao.getText().trim());

        // Tela 5
        if (difSociabilidade != null)
            paeeCompartilhada.setDif_sociabilidade(difSociabilidade.getText().trim());
        if (intervencoesSociabilidade != null)
            paeeCompartilhada.setIntervencoes_sociabilidade(intervencoesSociabilidade.getText().trim());
        // Nota: AVA não tem campo específico no modelo

        // Tela 6
        if (resumoObjetivoPlano != null)
            paeeCompartilhada.setObjetivo_plano(resumoObjetivoPlano.getText().trim());
        if (atendimentoAee != null) {
            paeeCompartilhada.setAee(atendimentoAee.getValue() != null ? atendimentoAee.getValue() : "Não");
        }
        if (atendimentoPsicologo != null) {
            paeeCompartilhada.setPsicologo(atendimentoPsicologo.getValue() != null ? atendimentoPsicologo.getValue() : "Não");
        }
        if (atendimentoFisioterapeuta != null) {
            paeeCompartilhada.setFisioterapeuta(atendimentoFisioterapeuta.getValue() != null ? atendimentoFisioterapeuta.getValue() : "Não");
        }
        if (atendimentoPsicopedagogo != null) {
            paeeCompartilhada.setPsicopedagogo(atendimentoPsicopedagogo.getValue() != null ? atendimentoPsicopedagogo.getValue() : "Não");
        }
        if (atendimentoTerapeutaOcupacional != null) {
            paeeCompartilhada.setTerapeuta_ocupacional(atendimentoTerapeutaOcupacional.getValue() != null ? atendimentoTerapeutaOcupacional.getValue() : "Não");
        }
        if (atendimentoEducacaoFisica != null) {
            paeeCompartilhada.setEducacao_fisica(atendimentoEducacaoFisica.getValue() != null ? atendimentoEducacaoFisica.getValue() : "Não");
        }
        if (atendimentoEstimulacaoPrecoce != null) {
            paeeCompartilhada.setEstimulacao_precoce(atendimentoEstimulacaoPrecoce.getValue() != null ? atendimentoEstimulacaoPrecoce.getValue() : "Não");
        }
    }

    // Métodos de fluxo
    public static void iniciarNovoPAEE() {
        modoAtual = ModoPAEE.NOVA;
        telaAtual = 1;
        paeeCompartilhada = new PAEE();
        navegandoEntreTelas = false;
    }

    public static void editarPAEEExistente(PAEE existente) {
        modoAtual = ModoPAEE.EDICAO;
        telaAtual = 1;
        paeeCompartilhada = (existente != null) ? existente : new PAEE();
        navegandoEntreTelas = false;
    }

    public static void visualizarPAEE(PAEE existente) {
        modoAtual = ModoPAEE.VISUALIZACAO;
        telaAtual = 1;
        paeeCompartilhada = existente;
        navegandoEntreTelas = false;
    }

    public static void setEducandoIdParaPAEE(String educandoId) {
        if (paeeCompartilhada == null) {
            paeeCompartilhada = new PAEE();
        }
        paeeCompartilhada.setEducando_id(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        turmaIdOrigem = turmaId;
    }
}
