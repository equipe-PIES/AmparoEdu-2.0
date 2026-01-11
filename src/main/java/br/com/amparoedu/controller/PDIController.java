package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.PDIService;
import br.com.amparoedu.controller.AnamneseController.ModoAnamnese;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.BooleanSupplier;

public class PDIController implements Initializable {

    // Modo de uso
    public enum ModoPDI {
        NOVA, EDICAO, VISUALIZACAO
    }

    // Estado e serviços
    private final PDIService pdiService = new PDIService();
    private PDI pdiAtual = new PDI();
    private static int telaAtual = 1; // 1, 2, 3 ou 4
    private static PDI pdiCompartilhada;
    private static String turmaIdOrigem;
    private static ModoPDI modoAtual = ModoPDI.NOVA;
    private static boolean navegandoEntreTelas;

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

    // Ciclo de vida
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean vindoDeNavegacao = navegandoEntreTelas;
        navegandoEntreTelas = false;

        // Detecta qual tela está carregada pelos controles presentes
        if (periodoPlano != null) {
            telaAtual = 1;
        } else if (potencialidadesTextArea != null) {
            telaAtual = 2;
        } else if (atividadesTextArea != null) {
            telaAtual = 3;
        } else if (recursosProduzidosTextArea != null) {
            telaAtual = 4;
        }

        // Se não veio de navegação, prepara estado conforme o modo selecionado
        if (!vindoDeNavegacao) {
            if (modoAtual == ModoPDI.NOVA) {
                telaAtual = 1;
                if (pdiCompartilhada == null) {
                    pdiCompartilhada = new PDI();
                }
            } else {
                if (pdiCompartilhada == null) {
                    pdiCompartilhada = new PDI();
                }
                telaAtual = 1;
            }
        } else if (telaAtual == 1 && pdiCompartilhada == null) {
            pdiCompartilhada = new PDI();
        }

        // Usa a PDI compartilhada
        if (pdiCompartilhada != null) {
            pdiAtual = pdiCompartilhada;
        }

        // Inicializa componentes
        inicializarChoiceBoxes();
        carregarDadosNaTela();
        desabilitarEdicaoSeVisualizacao();
    }

    // Inicializa os valores das ChoiceBoxes.
    private void inicializarChoiceBoxes() {
        if (frequenciaSemana != null) {
            frequenciaSemana.getItems().addAll("Uma vez", "Duas vezes");
        }

        if (diasSemana != null) {
            diasSemana.getItems().addAll("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira");
        }

        if (composicaoGrupo != null) {
            composicaoGrupo.getItems().addAll("Individual", "Coletivo");
        }
    }

    // Desabilita edição se estiver em modo visualização
    private void desabilitarEdicaoSeVisualizacao() {
        if (modoAtual != ModoPDI.VISUALIZACAO)
            return;

        // Desabilita campos textuais
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

        // Desabilita ChoiceBoxes
        desabilitarCampo(frequenciaSemana);
        desabilitarCampo(diasSemana);
        desabilitarCampo(composicaoGrupo);

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

    // Carrega dados do PDI atual na tela
    private void carregarDadosNaTela() {
        if (pdiAtual == null)
            return;

        // Tela 1
        if (periodoPlano != null && pdiAtual.getPeriodoAee() != null) {
            periodoPlano.setText(pdiAtual.getPeriodoAee());
        }
        if (horarioAtendimento != null && pdiAtual.getHorarioAtendimento() != null) {
            horarioAtendimento.setText(pdiAtual.getHorarioAtendimento());
        }
        if (frequenciaSemana != null && pdiAtual.getFrequenciaAtendimento() != null) {
            frequenciaSemana.setValue(pdiAtual.getFrequenciaAtendimento());
        }
        if (diasSemana != null && pdiAtual.getDiasAtendimento() != null) {
            diasSemana.setValue(pdiAtual.getDiasAtendimento());
        }
        if (composicaoGrupo != null && pdiAtual.getComposicaoGrupo() != null) {
            composicaoGrupo.setValue(pdiAtual.getComposicaoGrupo());
        }
        if (objetivosPlano != null && pdiAtual.getObjetivos() != null) {
            objetivosPlano.setText(pdiAtual.getObjetivos());
        }

        // Tela 2
        if (potencialidadesTextArea != null && pdiAtual.getPotencialidades() != null) {
            potencialidadesTextArea.setText(pdiAtual.getPotencialidades());
        }
        if (necessidadesTextArea != null && pdiAtual.getNecessidadesEspeciais() != null) {
            necessidadesTextArea.setText(pdiAtual.getNecessidadesEspeciais());
        }
        if (habilidadesTextArea != null && pdiAtual.getHabilidades() != null) {
            habilidadesTextArea.setText(pdiAtual.getHabilidades());
        }

        // Tela 3
        if (atividadesTextArea != null && pdiAtual.getAtividades() != null) {
            atividadesTextArea.setText(pdiAtual.getAtividades());
        }
        if (recursosMateriaisTextArea != null && pdiAtual.getRecursosMateriais() != null) {
            recursosMateriaisTextArea.setText(pdiAtual.getRecursosMateriais());
        }
        if (recursosAdequacaoTextArea != null && pdiAtual.getRecursosNecessitamAdaptacao() != null) {
            recursosAdequacaoTextArea.setText(pdiAtual.getRecursosNecessitamAdaptacao());
        }

        // Tela 4
        if (recursosProduzidosTextArea != null && pdiAtual.getRecursosNecessitamProduzir() != null) {
            recursosProduzidosTextArea.setText(pdiAtual.getRecursosNecessitamProduzir());
        }
        if (parceriasTextArea != null && pdiAtual.getParceriasNecessarias() != null) {
            parceriasTextArea.setText(pdiAtual.getParceriasNecessarias());
        }
    }

    // Valida se um campo de texto obrigatório está preenchido.
    private boolean validarCampoObrigatorio(TextField campo, String mensagemErro) {
        if (campo == null || campo.getText() == null || campo.getText().trim().isEmpty()) {
            exibirMensagemErro("Preencha todos os campos.");
            return false;
        }
        return true;
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
    // Handler para o botão Concluir do cadastro de PDI

    @FXML
    private void btnConcluirClick() {
        if (modoAtual == ModoPDI.VISUALIZACAO) {
            exibirMensagemErro("Modo visualização: não é possível salvar.");
            return;
        }

        System.out.println("DEBUG: Botão concluir clicado na tela " + telaAtual);

        // Salva os dados da tela atual primeiro
        salvarDadosTelaAtual();

        // Valida todos os campos obrigatórios
        if (!validarTodosCampos()) {
            exibirMensagemErro("Preencha todos os campos obrigatórios para concluir.");
            return;
        }

        // Mostra aviso antes de salvar
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Concluir PDI");
        alerta.setHeaderText("Deseja salvar o PDI agora?");
        alerta.setContentText("Todos os dados serão salvos no sistema.");
        var opcao = alerta.showAndWait();
        if (opcao.isEmpty() || opcao.get() != ButtonType.OK) {
            return;
        }

        // Garante que o objeto compartilhado tenha os dados mais recentes
        pdiAtual = pdiCompartilhada;

        // Salva o educando ID antes de resetar
        String educandoId = pdiCompartilhada.getEducandoId();

        try {
            // 1. Obtém o usuário logado
            Usuario usuarioLogado = AuthService.getUsuarioLogado();

            if (usuarioLogado == null) {
                exibirMensagemErro("Usuário não está logado. Faça login novamente.");
                return;
            }

            // 2. Verifica se é professor
            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirMensagemErro("Apenas professores podem criar PDIs. Tipo do usuário: " + usuarioLogado.getTipo());
                return;
            }

            // 3. Define o ID do USUÁRIO no PDI
            // O PDIRepository vai converter para o ID do professor
            pdiCompartilhada.setProfessorId(getIdProfessorLogado());

            // 4. Metadados obrigatórios
            pdiCompartilhada.setDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

            boolean sucesso;
            boolean edicao = modoAtual == ModoPDI.EDICAO;
            if (edicao) {
                sucesso = pdiService.atualizarPDI(pdiCompartilhada);
            } else {
                sucesso = pdiService.cadastrarNovoPDI(pdiCompartilhada);
            }

            if (sucesso) {
                exibirMensagemSucesso(edicao ? "PDI atualizado com sucesso!" : "PDI criado com sucesso!");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            resetarPDI();
                            voltarComPopup(educandoId);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                exibirMensagemErro(edicao ? "Erro ao atualizar PDI. Tente novamente." : "Erro ao cadastrar PDI. Tente novamente.");
            }

        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar PDI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getIdProfessorLogado() {
        return AuthService.getIdProfessorLogado();
    }

    // Valida todos os campos obrigatórios do PDI
    private boolean validarTodosCampos() {
        if (pdiCompartilhada == null) {
            return false;
        }


        // Validação da Tela 1
        if (pdiCompartilhada.getPeriodoAee() == null || pdiCompartilhada.getPeriodoAee().trim().isEmpty()) {
            return false;
        }
        if (pdiCompartilhada.getHorarioAtendimento() == null
                || pdiCompartilhada.getHorarioAtendimento().trim().isEmpty()) {
            return false;
        }
        if (pdiCompartilhada.getFrequenciaAtendimento() == null
                || pdiCompartilhada.getFrequenciaAtendimento().trim().isEmpty()) {
            return false;
        }
        if (pdiCompartilhada.getDiasAtendimento() == null || pdiCompartilhada.getDiasAtendimento().trim().isEmpty()) {
            return false;
        }
        if (pdiCompartilhada.getComposicaoGrupo() == null || pdiCompartilhada.getComposicaoGrupo().trim().isEmpty()) {
            System.out.println("Valor: " + pdiCompartilhada.getComposicaoGrupo());
            return false;
        }
        if (pdiCompartilhada.getObjetivos() == null || pdiCompartilhada.getObjetivos().trim().isEmpty()) {
            System.out.println("Valor: " + pdiCompartilhada.getObjetivos());
            return false;
        }
        return true;
    }

    // Handler para o botão Sair - fecha a janela/volta para a tela anterior
    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }

    // Handler para o botão Cancelar - cancela o processo de PDI
    @FXML
    private void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar PDI");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Salva o educando ID antes de resetar
            String educandoId = pdiAtual.getEducandoId();
            resetarPDI();
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

    private void resetarPDI() {
        telaAtual = 1;
        pdiCompartilhada = null;
        pdiAtual = new PDI();
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
                GerenciadorTelas.getInstance().trocarTela("pdi-2.fxml");
                break;
            case 2:
                GerenciadorTelas.getInstance().trocarTela("pdi-3.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("pdi-4.fxml");
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
                GerenciadorTelas.getInstance().trocarTela("pdi-1.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("pdi-2.fxml");
                break;
            case 4:
                GerenciadorTelas.getInstance().trocarTela("pdi-3.fxml");
                break;
        }
    }

    // Salva os dados da tela atual no objeto compartilhado
    private void salvarDadosTelaAtual() {
        if (pdiCompartilhada == null) {
            System.out.println("DEBUG: pdiCompartilhada é null em salvarDadosTelaAtual");
            return;
        }

        System.out.println("DEBUG: Salvando dados da tela " + telaAtual);

        // Tela 1
        if (periodoPlano != null) {
            String valor = periodoPlano.getText().trim();
            pdiCompartilhada.setPeriodoAee(valor);
        }
        if (horarioAtendimento != null) {
            String valor = horarioAtendimento.getText().trim();
            pdiCompartilhada.setHorarioAtendimento(valor);
        }
        if (frequenciaSemana != null && frequenciaSemana.getValue() != null) {
            String valor = frequenciaSemana.getValue();
            pdiCompartilhada.setFrequenciaAtendimento(valor);
        }
        if (diasSemana != null && diasSemana.getValue() != null) {
            String valor = diasSemana.getValue();
            pdiCompartilhada.setDiasAtendimento(valor);
        }
        if (composicaoGrupo != null && composicaoGrupo.getValue() != null) {
            String valor = composicaoGrupo.getValue();
            pdiCompartilhada.setComposicaoGrupo(valor);
        }
        if (objetivosPlano != null) {
            String valor = objetivosPlano.getText().trim();
            pdiCompartilhada.setObjetivos(valor);
        }

        // Tela 2
        if (potencialidadesTextArea != null)
            pdiCompartilhada.setPotencialidades(potencialidadesTextArea.getText().trim());
        if (necessidadesTextArea != null)
            pdiCompartilhada.setNecessidadesEspeciais(necessidadesTextArea.getText().trim());
        if (habilidadesTextArea != null)
            pdiCompartilhada.setHabilidades(habilidadesTextArea.getText().trim());

        // Tela 3
        if (atividadesTextArea != null)
            pdiCompartilhada.setAtividades(atividadesTextArea.getText().trim());
        if (recursosMateriaisTextArea != null)
            pdiCompartilhada.setRecursosMateriais(recursosMateriaisTextArea.getText().trim());
        if (recursosAdequacaoTextArea != null)
            pdiCompartilhada.setRecursosNecessitamAdaptacao(recursosAdequacaoTextArea.getText().trim());

        // Tela 4
        if (recursosProduzidosTextArea != null)
            pdiCompartilhada.setRecursosNecessitamProduzir(recursosProduzidosTextArea.getText().trim());
        if (parceriasTextArea != null)
            pdiCompartilhada.setParceriasNecessarias(parceriasTextArea.getText().trim());

    }

    // Handler para o botão Cancelar - volta para tela de progresso ou popup
    @FXML
    private void btnCancelClick() {
        GerenciadorTelas.getInstance().trocarTela("progresso-atendimento.fxml");
    }

    // Métodos de fluxo
    public static void iniciarNovoPDI() {
        modoAtual = ModoPDI.NOVA;
        telaAtual = 1;
        pdiCompartilhada = new PDI();
        navegandoEntreTelas = false;
    }

    public static void editarPDIExistente(PDI existente) {
        modoAtual = ModoPDI.EDICAO;
        telaAtual = 1;
        pdiCompartilhada = (existente != null) ? existente : new PDI();
        navegandoEntreTelas = false;
    }

    public static void visualizarPDI(PDI existente) {
        modoAtual = ModoPDI.VISUALIZACAO;
        telaAtual = 1;
        pdiCompartilhada = existente;
        navegandoEntreTelas = false;
    }

    public static void setEducandoIdParaPDI(String educandoId) {
        if (pdiCompartilhada == null) {
            pdiCompartilhada = new PDI();
        }
        pdiCompartilhada.setEducandoId(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        turmaIdOrigem = turmaId;
    }
}