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
import br.com.amparoedu.backend.repository.TurmaRepository;
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

public class RIController implements Initializable {

    // Modo de uso
    public enum ModoRI {
        NOVA, EDICAO, VISUALIZACAO
    }

    // Estado e serviços
    private final RIService riService = new RIService();
    private RI riAtual = new RI();
    private static int telaAtual = 1; // 1, 2 ou 3
    private static RI riCompartilhada;
    private static String turmaIdOrigem;
    private static ModoRI modoAtual = ModoRI.NOVA;
    private static boolean navegandoEntreTelas;

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

    // Ciclo de vida
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean vindoDeNavegacao = navegandoEntreTelas;
        navegandoEntreTelas = false;

        // Detecta qual tela está carregada pelos controles presentes
        if (dadosFuncionais != null) {
            telaAtual = 1;
        } else if (adaptacoesCurriculares != null) {
            telaAtual = 2;
        } else if (interacaoProfessora != null) {
            telaAtual = 3;
        }

        // Se não veio de navegação, prepara estado conforme o modo selecionado
        if (!vindoDeNavegacao) {
            if (modoAtual == ModoRI.NOVA) {
                telaAtual = 1;
                if (riCompartilhada == null) {
                    riCompartilhada = new RI();
                }
            } else {
                if (riCompartilhada == null) {
                    riCompartilhada = new RI();
                }
                telaAtual = 1;
            }
        } else if (telaAtual == 1 && riCompartilhada == null) {
            riCompartilhada = new RI();
        }

        // Usa o RI compartilhado
        if (riCompartilhada != null) {
            riAtual = riCompartilhada;
        }

        // Inicializa componentes
        carregarDadosNaTela();
        desabilitarEdicaoSeVisualizacao();
        atualizarUsuarioLogado();
    }

    // Desabilita edição se estiver em modo visualização
    private void desabilitarEdicaoSeVisualizacao() {
        if (modoAtual != ModoRI.VISUALIZACAO)
            return;

        // Desabilita campos textuais
        desabilitarCampo(dadosFuncionais);
        desabilitarCampo(funcionalidadeCognitiva);
        desabilitarCampo(alfabetizacao);
        desabilitarCampo(adaptacoesCurriculares);
        desabilitarCampo(participacaoAtividade);
        desabilitarCampo(autonomia);
        desabilitarCampo(interacaoProfessora);
        desabilitarCampo(atividadesVidaDiaria);

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

    // Carrega dados do RI atual na tela
    private void carregarDadosNaTela() {
        if (riAtual == null)
            return;

        // Tela 1
        if (dadosFuncionais != null && riAtual.getDados_funcionais() != null) {
            dadosFuncionais.setText(riAtual.getDados_funcionais());
        }
        if (funcionalidadeCognitiva != null && riAtual.getFuncionalidade_cognitiva() != null) {
            funcionalidadeCognitiva.setText(riAtual.getFuncionalidade_cognitiva());
        }
        if (alfabetizacao != null && riAtual.getAlfabetizacao() != null) {
            alfabetizacao.setText(riAtual.getAlfabetizacao());
        }

        // Tela 2
        if (adaptacoesCurriculares != null && riAtual.getAdaptacoes_curriculares() != null) {
            adaptacoesCurriculares.setText(riAtual.getAdaptacoes_curriculares());
        }
        if (participacaoAtividade != null && riAtual.getParticipacao_atividade() != null) {
            participacaoAtividade.setText(riAtual.getParticipacao_atividade());
        }
        // Autonomia é int no modelo, mas tratamos como String (conversão será feita no salvar)
        if (autonomia != null && riAtual.getAutonomia() > 0) {
            autonomia.setText(String.valueOf(riAtual.getAutonomia()));
        }

        // Tela 3
        // InteracaoProfessora é int no modelo, mas tratamos como String (conversão será feita no salvar)
        if (interacaoProfessora != null && riAtual.getInteracao_professora() > 0) {
            interacaoProfessora.setText(String.valueOf(riAtual.getInteracao_professora()));
        }
        if (atividadesVidaDiaria != null && riAtual.getAtividades_vida_diaria() != null) {
            atividadesVidaDiaria.setText(riAtual.getAtividades_vida_diaria());
        }
    }

    // Atualiza informações do usuário logado
    private void atualizarUsuarioLogado() {
        try {
            Usuario usuario = AuthService.getUsuarioLogado();
            if (usuario != null) {
                if (nomeUsuario != null) {
                    nomeUsuario.setText(usuario.getEmail());
                }
                if (cargoUsuario != null) {
                    cargoUsuario.setText(usuario.getTipo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        if (modoAtual == ModoRI.VISUALIZACAO) {
            exibirMensagemErro("Modo visualização: não é possível salvar.");
            return;
        }

        // Salva os dados da tela atual primeiro
        salvarDadosTelaAtual();

        // Mostra aviso antes de salvar
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Concluir Relatório Individual");
        alerta.setHeaderText("Deseja salvar o Relatório Individual agora?");
        alerta.setContentText("Todos os dados serão salvos no sistema.");
        var opcao = alerta.showAndWait();
        if (opcao.isEmpty() || opcao.get() != ButtonType.OK) {
            return;
        }

        // Garante que o objeto compartilhado tenha os dados mais recentes
        riAtual = riCompartilhada;

        // Salva o educando ID antes de resetar
        String educandoId = riCompartilhada.getEducando_id();

        try {
            // 1. Obtém o usuário logado
            Usuario usuarioLogado = AuthService.getUsuarioLogado();

            if (usuarioLogado == null) {
                exibirMensagemErro("Usuário não está logado. Faça login novamente.");
                return;
            }

            // 2. Verifica se é professor
            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirMensagemErro("Apenas professores podem criar Relatórios Individuais. Tipo do usuário: " + usuarioLogado.getTipo());
                return;
            }

            // 3. Define o ID do professor no RI
            riCompartilhada.setProfessor_id(getIdProfessorLogado());

            // 4. Metadados obrigatórios
            riCompartilhada.setData_criacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

            boolean sucesso;
            boolean edicao = modoAtual == ModoRI.EDICAO;
            if (edicao) {
                sucesso = riService.atualizarRI(riCompartilhada);
            } else {
                sucesso = riService.cadastrarNovoRI(riCompartilhada);
            }

            if (sucesso) {
                exibirMensagemSucesso(edicao ? "Relatório Individual atualizado com sucesso!" : "Relatório Individual criado com sucesso!");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            resetarRI();
                            voltarComPopup(educandoId);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                exibirMensagemErro(edicao ? "Erro ao atualizar Relatório Individual. Tente novamente." : "Erro ao cadastrar Relatório Individual. Tente novamente.");
            }

        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar Relatório Individual: " + e.getMessage());
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

    // Handler para o botão Cancelar - cancela o processo de RI
    @FXML
    private void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Relatório Individual");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Salva o educando ID antes de resetar
            String educandoId = riAtual != null ? riAtual.getEducando_id() : null;
            resetarRI();
            voltarComPopup(educandoId);
        }
    }

    // Handler para o botão Cancelar (versão alternativa)
    @FXML
    private void btnCancelClick() {
        btnCancelarClick();
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

    // Handler para o botão Relatórios
    @FXML
    private void btnRelatoriosClick() {
        btnTurmasClick();
    }

    private void resetarRI() {
        telaAtual = 1;
        riCompartilhada = null;
        riAtual = new RI();
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
                GerenciadorTelas.getInstance().trocarTela("relatorio-individual-2.fxml");
                break;
            case 2:
                GerenciadorTelas.getInstance().trocarTela("relatorio-individual-3.fxml");
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
                GerenciadorTelas.getInstance().trocarTela("relatorio-individual-1.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("relatorio-individual-2.fxml");
                break;
        }
    }

    // Salva os dados da tela atual no objeto compartilhado
    private void salvarDadosTelaAtual() {
        if (riCompartilhada == null) {
            return;
        }

        // Tela 1
        if (dadosFuncionais != null) {
            String valor = dadosFuncionais.getText().trim();
            riCompartilhada.setDados_funcionais(valor);
        }
        if (funcionalidadeCognitiva != null) {
            String valor = funcionalidadeCognitiva.getText().trim();
            riCompartilhada.setFuncionalidade_cognitiva(valor);
        }
        if (alfabetizacao != null) {
            String valor = alfabetizacao.getText().trim();
            riCompartilhada.setAlfabetizacao(valor);
        }

        // Tela 2
        if (adaptacoesCurriculares != null) {
            String valor = adaptacoesCurriculares.getText().trim();
            riCompartilhada.setAdaptacoes_curriculares(valor);
        }
        if (participacaoAtividade != null) {
            String valor = participacaoAtividade.getText().trim();
            riCompartilhada.setParticipacao_atividade(valor);
        }
        // Autonomia: como é int no modelo mas TextArea na interface, vamos tratar como String por enquanto
        // Por enquanto vamos deixar como 0 (será ajustado depois se necessário)
        if (autonomia != null) {
            String texto = autonomia.getText().trim();
            // Se não vazio, seta como 1, senão 0
            riCompartilhada.setAutonomia(texto.isEmpty() ? 0 : 1);
        }

        // Tela 3
        // InteracaoProfessora: como é int no modelo mas TextArea na interface, vamos tratar como String por enquanto
        if (interacaoProfessora != null) {
            String texto = interacaoProfessora.getText().trim();
            riCompartilhada.setInteracao_professora(texto.isEmpty() ? 0 : 1);
        }
        if (atividadesVidaDiaria != null) {
            String valor = atividadesVidaDiaria.getText().trim();
            riCompartilhada.setAtividades_vida_diaria(valor);
        }
    }

    // Métodos de fluxo
    public static void iniciarNovoRI() {
        modoAtual = ModoRI.NOVA;
        telaAtual = 1;
        riCompartilhada = new RI();
        navegandoEntreTelas = false;
    }

    public static void editarRIExistente(RI existente) {
        modoAtual = ModoRI.EDICAO;
        telaAtual = 1;
        riCompartilhada = (existente != null) ? existente : new RI();
        navegandoEntreTelas = false;
    }

    public static void visualizarRI(RI existente) {
        modoAtual = ModoRI.VISUALIZACAO;
        telaAtual = 1;
        riCompartilhada = existente;
        navegandoEntreTelas = false;
    }

    public static void setEducandoIdParaRI(String educandoId) {
        if (riCompartilhada == null) {
            riCompartilhada = new RI();
        }
        riCompartilhada.setEducando_id(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        turmaIdOrigem = turmaId;
    }

}
