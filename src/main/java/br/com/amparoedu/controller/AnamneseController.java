package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AnamneseController extends DocumentoControllerBase<Anamnese> implements Initializable {

    // Estado estático compartilhado entre telas
    private static final EstadoDocumento<Anamnese> ESTADO = new EstadoDocumento<>();

    // Serviço
    private final AnamneseService anamneseService = new AnamneseService();
    private final EducandoRepository educandoRepo = new EducandoRepository();
    
    // ToggleGroups gerenciados por HashMap
    private final ToggleGroup convulsaoGroup = new ToggleGroup();
    private final ToggleGroup convenioGroup = new ToggleGroup();
    private final ToggleGroup vacinacaoGroup = new ToggleGroup();
    private final ToggleGroup doencaContagiosaGroup = new ToggleGroup();
    private final ToggleGroup medicacaoGroup = new ToggleGroup();
    private final ToggleGroup dificuldadesGroup = new ToggleGroup();
    private final ToggleGroup apoioPedagogicoGroup = new ToggleGroup();
    private final ToggleGroup prematuridadeGroup = new ToggleGroup();
    private final ToggleGroup chorouGroup = new ToggleGroup();
    private final ToggleGroup ficouRoxoGroup = new ToggleGroup();
    private final ToggleGroup incubadoraGroup = new ToggleGroup();
    private final ToggleGroup amamentadoGroup = new ToggleGroup();
    private final ToggleGroup sustentouCabecaGroup = new ToggleGroup();
    private final ToggleGroup engatinhouGroup = new ToggleGroup();
    private final ToggleGroup sentouGroup = new ToggleGroup();
    private final ToggleGroup andouGroup = new ToggleGroup();
    private final ToggleGroup terapiaGroup = new ToggleGroup();
    private final ToggleGroup falouGroup = new ToggleGroup();
    private final ToggleGroup disturbioGroup = new ToggleGroup();

    // Controles (Tela 1)
    @FXML private RadioButton convulsaoSim;
    @FXML private RadioButton convulsaoNao;
    @FXML private RadioButton convenioSim;
    @FXML private RadioButton convenioNao;
    @FXML private TextField convenio;
    @FXML private RadioButton vacinacaoSim;
    @FXML private RadioButton vacinacaoNao;
    @FXML private RadioButton doencaContagiosaSim;
    @FXML private RadioButton doencaContagiosaNao;
    @FXML private TextField doencaContagiosa;
    @FXML private TextField inicioEscolarizacao;
    @FXML private RadioButton dificuldadesSim;
    @FXML private RadioButton dificuldadesNao;
    @FXML private TextField dificuldades;
    @FXML private RadioButton apoioPedagogicoSim;
    @FXML private RadioButton apoioPedagogicoNao;
    @FXML private TextField apoioPedagogico;
    @FXML private RadioButton medicacaoSim;
    @FXML private RadioButton medicacaoNao;
    @FXML private TextField medicacoes;
    @FXML private TextField servicosFrequentados1;
    @FXML private RadioButton preNatalSim;
    @FXML private RadioButton preNatalNao;
    @FXML private RadioButton prematuridadeSim;
    @FXML private RadioButton prematuridadeNao;
    @FXML private TextField prematuridade1;
    @FXML private TextField duracaoGestacao1;
    @FXML private Label questConvenio;
    @FXML private Label questDoencaContagiosa;
    @FXML private Label questMedicacao;
    @FXML private Label questDificuldade;
    @FXML private Label questApioPedagogico;
    @FXML private Label questCausaPrematuridade1;

    // Controles (Tela 2)
    @FXML private TextField cidadeNascimento;
    @FXML private TextField maternidade;
    @FXML private ChoiceBox<String> tipoParto;
    @FXML private RadioButton chorouSim;
    @FXML private RadioButton chorouNao;
    @FXML private RadioButton ficouRoxoSim;
    @FXML private RadioButton ficouRoxoNao;
    @FXML private RadioButton incubadoraSim;
    @FXML private RadioButton incubadoraNao;
    @FXML private RadioButton amamentadoSim;
    @FXML private RadioButton amamentadoNao;
    @FXML private RadioButton sustentouCabecaSim;
    @FXML private RadioButton sustentouCabecaNao;
    @FXML private TextField sustentouCabeca;
    @FXML private Label lblSustentouCabecaMeses;
    @FXML private RadioButton engatinhouSim;
    @FXML private RadioButton engatinhouNao;
    @FXML private TextField engatinhou;
    @FXML private Label lblEngatinhouMeses;
    @FXML private RadioButton sentouSim;
    @FXML private RadioButton sentouNao;
    @FXML private TextField sentou;
    @FXML private Label lblSentouMeses;
    @FXML private RadioButton andouSim;
    @FXML private RadioButton andouNao;
    @FXML private TextField andou;
    @FXML private Label lblAndouMeses;
    @FXML private RadioButton terapiaSim;
    @FXML private RadioButton terapiaNao;
    @FXML private TextField terapia;
    @FXML private Label lblTerapiaMotivo;
    @FXML private RadioButton falouSim;
    @FXML private RadioButton falouNao;
    @FXML private TextField falou;
    @FXML private Label lblFalouMeses;

    // Controles (Tela 3)
    @FXML private ChoiceBox<String> balbucio;
    @FXML private TextField primeiraPalavra;
    @FXML private TextField primeiraFrase;
    @FXML private ChoiceBox<String> tipoFala;
    @FXML private RadioButton disturbioSim;
    @FXML private RadioButton disturbioNao;
    @FXML private TextField disturbio;
    @FXML private Label questDisturbio;
    @FXML private ChoiceBox<String> dormeSozinho;
    @FXML private ChoiceBox<String> temQuarto;
    @FXML private ChoiceBox<String> sono;
    @FXML private ChoiceBox<String> respeitaRegras;
    @FXML private ChoiceBox<String> desmotivado;
    @FXML private ChoiceBox<String> agressivo;
    @FXML private ChoiceBox<String> inquietacao;
    @FXML private TextField servicos;

    // Botões e Mensagens
    @FXML private Label validationMsg;
    @FXML private Button btnConcluir;

    // ========== Implementação dos Métodos Abstratos ==========

    @Override
    protected EstadoDocumento<Anamnese> getEstado() {
        return ESTADO;
    }

    @Override
    protected int getTotalTelas() {
        return 3;
    }

    @Override
    protected String getPrefixoTela() {
        return "anamnese";
    }

    @Override
    protected Anamnese criarNovoDocumento() {
        return new Anamnese();
    }

    @Override
    protected int detectarTelaAtual() {
        if (convulsaoSim != null) return 1;
        if (chorouSim != null) return 2;
        if (disturbioSim != null || balbucio != null) return 3;
        return -1;
    }

    @Override
    protected void carregarDadosNaTela() {
        if (documentoAtual == null) return;

        // Primeiro esconde campos condicionais antes de criar vínculos de visibilidade
        ocultarCamposCondicionais();
        configurarToggleGroups();
        inicializarChoiceBoxes();
        
        // Se estiver em modo edição ou visualização, preenche com dados existentes
        if (ESTADO.modoAtual != ModoDocumento.NOVA) {
            preencherCamposComDadosExistentes();
        }
    }

    @Override
    protected void salvarDadosTelaAtual() {
        if (documentoAtual == null) return;

        EstadoDocumento<Anamnese> estado = getEstado();
        if (estado.telaAtual == 1) {
            preencherDadosTela1();
        } else if (estado.telaAtual == 2) {
            preencherDadosTela2();
        } else if (estado.telaAtual == 3) {
            preencherDadosTela3();
        }
    }

    @Override
    protected boolean validarTelaAtual() {
        EstadoDocumento<Anamnese> estado = getEstado();
        if (estado.telaAtual == 1) {
            return validarTela1();
        } else if (estado.telaAtual == 2) {
            return validarTela2();
        } else if (estado.telaAtual == 3) {
            return validarTela3();
        }
        return false;
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
        return "Anamnese";
    }

    @Override
    protected void desabilitarCampos() {
        desabilitarControles(convenio, doencaContagiosa, inicioEscolarizacao, dificuldades, apoioPedagogico,
                medicacoes, servicosFrequentados1, prematuridade1, duracaoGestacao1, cidadeNascimento, maternidade,
                sustentouCabeca, engatinhou, sentou, andou, terapia, falou, primeiraPalavra, primeiraFrase, disturbio,
                servicos);

        desabilitarControles(convulsaoSim, convulsaoNao, convenioSim, convenioNao, vacinacaoSim, vacinacaoNao,
                doencaContagiosaSim, doencaContagiosaNao, dificuldadesSim, dificuldadesNao, apoioPedagogicoSim,
                apoioPedagogicoNao, medicacaoSim, medicacaoNao, preNatalSim, preNatalNao, prematuridadeSim,
                prematuridadeNao, chorouSim, chorouNao, ficouRoxoSim, ficouRoxoNao, incubadoraSim, incubadoraNao,
                amamentadoSim, amamentadoNao, sustentouCabecaSim, sustentouCabecaNao, engatinhouSim, engatinhouNao,
                sentouSim, sentouNao, andouSim, andouNao, terapiaSim, terapiaNao, falouSim, falouNao, disturbioSim,
                disturbioNao);

        desabilitarControles(tipoParto, balbucio, tipoFala, dormeSozinho, temQuarto, sono, respeitaRegras, desmotivado,
                agressivo, inquietacao);

        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarChoiceBoxes();
        inicializarBase();
    }
    
    // Configura os ToggleGroups para os RadioButtons.
    private void configurarToggleGroups() {
        // Tela 1
        if (convulsaoSim != null) {
            configurarToggle(convulsaoSim, convulsaoNao, convulsaoGroup, null, null);
            configurarToggle(convenioSim, convenioNao, convenioGroup, convenio, questConvenio);
            configurarToggle(vacinacaoSim, vacinacaoNao, vacinacaoGroup, null, null);
            configurarToggle(doencaContagiosaSim, doencaContagiosaNao, doencaContagiosaGroup, doencaContagiosa, questDoencaContagiosa);
            configurarToggle(dificuldadesSim, dificuldadesNao, dificuldadesGroup, dificuldades, questDificuldade);
            configurarToggle(apoioPedagogicoSim, apoioPedagogicoNao, apoioPedagogicoGroup, apoioPedagogico, questApioPedagogico);
            configurarToggle(medicacaoSim, medicacaoNao, medicacaoGroup, medicacoes, questMedicacao);
            configurarToggle(prematuridadeSim, prematuridadeNao, prematuridadeGroup, prematuridade1, questCausaPrematuridade1);
        }
        
        // Tela 2
        if (chorouSim != null) {
            configurarToggle(chorouSim, chorouNao, chorouGroup, null, null);
            configurarToggle(ficouRoxoSim, ficouRoxoNao, ficouRoxoGroup, null, null);
            configurarToggle(incubadoraSim, incubadoraNao, incubadoraGroup, null, null);
            configurarToggle(amamentadoSim, amamentadoNao, amamentadoGroup, null, null);
            configurarToggle(sustentouCabecaSim, sustentouCabecaNao, sustentouCabecaGroup, sustentouCabeca, lblSustentouCabecaMeses);
            configurarToggle(engatinhouSim, engatinhouNao, engatinhouGroup, engatinhou, lblEngatinhouMeses);
            configurarToggle(sentouSim, sentouNao, sentouGroup, sentou, lblSentouMeses);
            configurarToggle(andouSim, andouNao, andouGroup, andou, lblAndouMeses);
            configurarToggle(terapiaSim, terapiaNao, terapiaGroup, terapia, lblTerapiaMotivo);
            configurarToggle(falouSim, falouNao, falouGroup, falou, lblFalouMeses);
        }
        
        // Tela 3
        if (disturbioSim != null) {
            configurarToggle(disturbioSim, disturbioNao, disturbioGroup, disturbio, questDisturbio);
        }
    }
    
    // Método utilitário para configurar RadioButton com Label
    private void configurarToggle(RadioButton sim, RadioButton nao, ToggleGroup grupo, TextField campoOpcional, Label labelOpcional) {
        if (sim == null || nao == null || grupo == null) return;

        sim.setToggleGroup(grupo);
        nao.setToggleGroup(grupo);

        if (campoOpcional != null) {
            // Vincula a visibilidade do campo diretamente à seleção do RadioButton "Sim"
            campoOpcional.visibleProperty().bind(sim.selectedProperty());
            campoOpcional.managedProperty().bind(sim.selectedProperty());
        
            // Limpa o texto se o usuário mudar de "Sim" para "Não"
            nao.selectedProperty().addListener((obs, old, selected) -> {
                if (selected) campoOpcional.clear();
            });
        }
    
        if (labelOpcional != null) {
            // Vincula a visibilidade da label ao RadioButton "Sim"
            labelOpcional.visibleProperty().bind(sim.selectedProperty());
            labelOpcional.managedProperty().bind(sim.selectedProperty());
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
    
    // Valida se um ToggleGroup tem uma opção selecionada.
    private boolean validarToggleObrigatorio(ToggleGroup grupo, String mensagemErro) {
        if (grupo.getSelectedToggle() == null) {
            exibirMensagemErro("Preencha todos os campos.");
            return false;
        }
        return true;
    }
    
    // Valida se um ChoiceBox tem um valor selecionado.
    private boolean validarChoiceBoxObrigatorio(ChoiceBox<?> choiceBox, String mensagemErro) {
        if (choiceBox == null || choiceBox.getValue() == null) {
            exibirMensagemErro("Preencha todos os campos.");
            return false;
        }
        return true;
    }
    
    // Retorna "Sim" se o RadioButton está selecionado, "Não" caso contrário.
    private String getValorToggle(RadioButton radioButton) {
        return radioButton != null && radioButton.isSelected() ? "Sim" : "Não";
    }
    
    // Retorna o texto do campo se o RadioButton está selecionado, string vazia caso contrário.
    private String getTextoSeSelecionado(RadioButton radioButtonSim, TextField campo) {
        if (radioButtonSim.isSelected()) {
            String texto = campo.getText();
            return (texto == null || texto.isEmpty()) ? "" : texto;
        }
        return "";
    }
    
    // Valida campo condicional: só valida se o RadioButton "Sim" estiver selecionado
    private boolean validarCampoCondicional(RadioButton sim, TextField campo, String mensagemErro) {
        return !sim.isSelected() || validarCampoObrigatorio(campo, mensagemErro);
    }
    
    // Preenche flag e texto de forma consolidada
    private void preencherCampo(RadioButton sim, TextField campo,
                                java.util.function.Consumer<String> setFlag,
                                java.util.function.Consumer<String> setTexto) {
        setFlag.accept(getValorToggle(sim));
        setTexto.accept(getTextoSeSelecionado(sim, campo));
    }
    
    // Inicializa os valores das ChoiceBoxes.
    private void inicializarChoiceBoxes() {
        if (tipoParto != null) {
            tipoParto.getItems().addAll("Normal", "Cesariana", "Fórceps");
        }
        
        if (balbucio != null) {
            balbucio.getItems().addAll("0-6 meses", "6-12 meses", "12-18 meses", "Não balbuciou");
        }
        
        if (tipoFala != null) {
            tipoFala.getItems().addAll("Natural", "Inibido", "Gagueja", "Outro");
        }
        
        if (dormeSozinho != null) {
            dormeSozinho.getItems().addAll("Sim", "Não", "Às vezes");
        }
        
        if (temQuarto != null) {
            temQuarto.getItems().addAll("Sim", "Não", "Compartilhado");
        }
        
        if (sono != null) {
            sono.getItems().addAll("Tranquilo", "Agitado", "Pesadelos", "Insônia");
        }
        
        if (respeitaRegras != null) {
            respeitaRegras.getItems().addAll("Sempre", "Às vezes", "Raramente", "Nunca");
        }
        
        if (desmotivado != null) {
            desmotivado.getItems().addAll("Sempre", "Às vezes", "Raramente", "Nunca");
        }
        
        if (agressivo != null) {
            agressivo.getItems().addAll("Sempre", "Às vezes", "Raramente", "Nunca");
        }
        
        if (inquietacao != null) {
            inquietacao.getItems().addAll("Sempre", "Às vezes", "Raramente", "Nunca");
        }
    }
    
    // Oculta campos condicionais no carregamento inicial.
    private void ocultarCamposCondicionais() {
        ocultarNos(
                convenio, questConvenio,
                doencaContagiosa, questDoencaContagiosa,
                dificuldades, questDificuldade,
                apoioPedagogico, questApioPedagogico,
                medicacoes, questMedicacao,
                prematuridade1, questCausaPrematuridade1,
                sustentouCabeca, lblSustentouCabecaMeses,
                engatinhou, lblEngatinhouMeses,
                sentou, lblSentouMeses,
                andou, lblAndouMeses,
                terapia, lblTerapiaMotivo,
                falou, lblFalouMeses,
                disturbio, questDisturbio
        );
    }
    
    // Oculta múltiplos nós de uma vez (TextField, Label, etc.)
    private void ocultarNos(Node... nos) {
        for (Node no : nos) {
            if (no != null) {
                no.setVisible(false);
                no.setManaged(false);
            }
        }
    }

    // Validação
    
    // Valida os campos obrigatórios da Tela 1.
    private boolean validarTela1() {
        if (!validarToggleObrigatorio(convulsaoGroup, "Por favor, informe se o educando teve convulsão.")) return false;
        if (!validarToggleObrigatorio(convenioGroup, "Por favor, informe se o educando possui convênio médico.")) return false;
        if (!validarCampoCondicional(convenioSim, convenio, "Por favor, informe o nome do convênio médico.")) return false;
        
        if (!validarToggleObrigatorio(vacinacaoGroup, "Por favor, informe se a vacinação está em dias.")) return false;
        
        if (!validarToggleObrigatorio(doencaContagiosaGroup, "Por favor, informe se o educando teve doença contagiosa.")) return false;
        if (!validarCampoCondicional(doencaContagiosaSim, doencaContagiosa, "Por favor, especifique quais doenças contagiosas.")) return false;
        
        if (!validarCampoObrigatorio(inicioEscolarizacao, "Por favor, informe o início da escolarização.")) return false;
        
        if (!validarToggleObrigatorio(dificuldadesGroup, "Por favor, informe se o educando apresenta dificuldades.")) return false;
        if (!validarCampoCondicional(dificuldadesSim, dificuldades, "Por favor, especifique quais dificuldades.")) return false;
        
        if (!validarToggleObrigatorio(apoioPedagogicoGroup, "Por favor, informe se o educando recebe apoio pedagógico em casa.")) return false;
        if (!validarCampoCondicional(apoioPedagogicoSim, apoioPedagogico, "Por favor, especifique quem oferece o apoio.")) return false;
        
        if (!validarToggleObrigatorio(medicacaoGroup, "Por favor, informe se o educando faz uso de medicações.")) return false;
        if (!validarCampoCondicional(medicacaoSim, medicacoes, "Por favor, especifique quais medicações.")) return false;
        
        if (!validarCampoObrigatorio(servicosFrequentados1, "Por favor, informe os serviços de saúde ou educação frequentados.")) return false;
        
        return true;
    }
    
    // Valida os campos obrigatórios da Tela 2.
    private boolean validarTela2() {
        if (!validarCampoObrigatorio(cidadeNascimento, "Por favor, informe a cidade de nascimento.")) return false;
        if (!validarCampoObrigatorio(maternidade, "Por favor, informe a maternidade/hospital.")) return false;
        if (!validarChoiceBoxObrigatorio(tipoParto, "Por favor, selecione o tipo de parto.")) return false;
        
        if (!validarToggleObrigatorio(chorouGroup, "Por favor, informe se o educando chorou ao nascer.")) return false;
        if (!validarToggleObrigatorio(ficouRoxoGroup, "Por favor, informe se o educando ficou roxo.")) return false;
        if (!validarToggleObrigatorio(incubadoraGroup, "Por favor, informe se o educando usou incubadora.")) return false;
        if (!validarToggleObrigatorio(amamentadoGroup, "Por favor, informe se o educando foi amamentado.")) return false;
        
        if (!validarToggleObrigatorio(sustentouCabecaGroup, "Por favor, informe se o educando sustentou a cabeça.")) return false;
        if (!validarCampoCondicional(sustentouCabecaSim, sustentouCabeca, "Por favor, informe quantos meses sustentou a cabeça.")) return false;
        
        if (!validarToggleObrigatorio(engatinhouGroup, "Por favor, informe se o educando engatinhou.")) return false;
        if (!validarCampoCondicional(engatinhouSim, engatinhou, "Por favor, informe quantos meses engatinhou.")) return false;
        
        if (!validarToggleObrigatorio(sentouGroup, "Por favor, informe se o educando sentou.")) return false;
        if (!validarCampoCondicional(sentouSim, sentou, "Por favor, informe quantos meses sentou.")) return false;
        
        if (!validarToggleObrigatorio(andouGroup, "Por favor, informe se o educando andou.")) return false;
        if (!validarCampoCondicional(andouSim, andou, "Por favor, informe quantos meses andou.")) return false;
        
        if (!validarToggleObrigatorio(terapiaGroup, "Por favor, informe se o educando precisou de terapia.")) return false;
        if (!validarCampoCondicional(terapiaSim, terapia, "Por favor, informe qual o motivo da terapia.")) return false;
        
        if (!validarToggleObrigatorio(falouGroup, "Por favor, informe se o educando falou.")) return false;
        if (!validarCampoCondicional(falouSim, falou, "Por favor, informe quantos meses falou.")) return false;
        
        return true;
    }
    
    // Valida os campos obrigatórios da Tela 3.
    private boolean validarTela3() {
        if (!validarChoiceBoxObrigatorio(balbucio, "Por favor, selecione quando o educando começou a balbuciar.")) return false;
        if (!validarChoiceBoxObrigatorio(tipoFala, "Por favor, selecione o tipo de fala do educando.")) return false;
        if (!validarToggleObrigatorio(disturbioGroup, "Por favor, informe se o educando possui distúrbio.")) return false;
        if (!validarCampoCondicional(disturbioSim, disturbio, "Por favor, especifique qual é o distúrbio.")) return false;
        // Validar campos adicionais para evitar valores indefinidos
        if (!validarChoiceBoxObrigatorio(dormeSozinho, "Preencha todos os campos.")) return false;
        if (!validarChoiceBoxObrigatorio(temQuarto, "Preencha todos os campos.")) return false;
        if (!validarChoiceBoxObrigatorio(sono, "Preencha todos os campos.")) return false;
        if (!validarChoiceBoxObrigatorio(respeitaRegras, "Preencha todos os campos.")) return false;
        if (!validarChoiceBoxObrigatorio(desmotivado, "Preencha todos os campos.")) return false;
        if (!validarChoiceBoxObrigatorio(agressivo, "Preencha todos os campos.")) return false;
        if (!validarChoiceBoxObrigatorio(inquietacao, "Preencha todos os campos.")) return false;
        
        return true;
    }
    
    // Preenchimento do modelo
    
    //Preenche os dados da Tela 1 no objeto Anamnese.
    private void preencherDadosTela1() {
        documentoAtual.setTem_convulsao(getValorToggle(convulsaoSim));
        preencherCampo(convenioSim, convenio, documentoAtual::setTem_convenio_medico, documentoAtual::setNome_convenio);
        preencherCampo(doencaContagiosaSim, doencaContagiosa, documentoAtual::setTeve_doenca_contagiosa, documentoAtual::setQuais_doencas);
        
        // Vacinação
        documentoAtual.setVacinas_em_dia(getValorToggle(vacinacaoSim));

        String inicioEscVal = inicioEscolarizacao.getText();
        documentoAtual.setInicio_escolarizacao(inicioEscVal != null ? inicioEscVal : "");
        
        preencherCampo(dificuldadesSim, dificuldades, documentoAtual::setApresenta_dificuldades, documentoAtual::setQuais_dificuldades);
        preencherCampo(apoioPedagogicoSim, apoioPedagogico, documentoAtual::setRecebe_apoio_pedagogico_casa, documentoAtual::setApoio_quem);
        preencherCampo(medicacaoSim, medicacoes, documentoAtual::setUsa_medicacao, documentoAtual::setQuais_medicacoes);

        // Serviços de saúde ou educação frequentados (tela 1)
        if (servicosFrequentados1 != null && servicosFrequentados1.getText() != null) {
            String sv = servicosFrequentados1.getText().trim();
            documentoAtual.setQuais_servicos(sv);
            documentoAtual.setUsou_servico_saude_educacao(sv.isEmpty() ? "Não" : "Sim");
        }

        // Dados da gestação
        if (duracaoGestacao1 != null) {
            String duracao = duracaoGestacao1.getText();
            documentoAtual.setDuracao_da_gestacao(duracao != null ? duracao : "");
        }
        documentoAtual.setFez_prenatal(getValorToggle(preNatalSim));
        preencherCampo(prematuridadeSim, prematuridade1, documentoAtual::setHouve_prematuridade, documentoAtual::setCausa_prematuridade);
    }
    
    //Preenche os dados da Tela 2 no objeto Anamnese.
    private void preencherDadosTela2() {
        String cidadeVal = cidadeNascimento.getText();
        documentoAtual.setCidade_nascimento(cidadeVal != null ? cidadeVal : "");
        
        String maternidadeVal = maternidade.getText();
        documentoAtual.setMaternidade(maternidadeVal != null ? maternidadeVal : "");
        
        String tipoPartoVal = tipoParto.getValue();
        documentoAtual.setTipo_parto(tipoPartoVal != null ? tipoPartoVal : "");
        
        documentoAtual.setChorou_ao_nascer(getValorToggle(chorouSim));
        documentoAtual.setFicou_roxo(getValorToggle(ficouRoxoSim));
        documentoAtual.setUsou_incubadora(getValorToggle(incubadoraSim));
        documentoAtual.setFoi_amamentado(getValorToggle(amamentadoSim));
        
        preencherCampo(sustentouCabecaSim, sustentouCabeca, documentoAtual::setSustentou_a_cabeca, documentoAtual::setQuantos_meses_sustentou_cabeca);
        preencherCampo(engatinhouSim, engatinhou, documentoAtual::setEngatinhou, documentoAtual::setQuantos_meses_engatinhou);
        preencherCampo(sentouSim, sentou, documentoAtual::setSentou, documentoAtual::setQuantos_meses_sentou);
        preencherCampo(andouSim, andou, documentoAtual::setAndou, documentoAtual::setQuantos_meses_andou);
        preencherCampo(terapiaSim, terapia, documentoAtual::setPrecisou_de_terapia, documentoAtual::setQual_motivo_terapia);
        preencherCampo(falouSim, falou, documentoAtual::setFalou, documentoAtual::setQuantos_meses_falou);
    }
    
    //Preenche os dados da Tela 3 no objeto Anamnese.
    private void preencherDadosTela3() {
        // Comunicação e linguagem
        String balbucioVal = balbucio.getValue();
        documentoAtual.setQuantos_meses_balbuciou(balbucioVal != null ? balbucioVal : "");
        
        String primeiraPalavraVal = primeiraPalavra.getText();
        documentoAtual.setQuando_primeiras_palavras(primeiraPalavraVal != null ? primeiraPalavraVal : "");
        
        String primeiraFraseVal = primeiraFrase.getText();
        documentoAtual.setQuando_primeiras_frases(primeiraFraseVal != null ? primeiraFraseVal : "");
        
        String tipoFalaVal = tipoFala.getValue();
        documentoAtual.setFala_natural_inibido(tipoFalaVal != null ? tipoFalaVal : "");
        
        preencherCampo(disturbioSim, disturbio, documentoAtual::setPossui_disturbio, documentoAtual::setQual_disturbio);

        // Serviços de saúde ou educação frequentados (tela 3, se preenchido aqui)
        if (servicos != null && servicos.getText() != null) {
            String sv = servicos.getText().trim();
            if (!sv.isEmpty()) {
                documentoAtual.setQuais_servicos(sv);
                documentoAtual.setUsou_servico_saude_educacao("Sim");
            }
        }
        
        // Preenche os campos opcionais da Tela 3 (sleep)
        if (dormeSozinho != null && dormeSozinho.getValue() != null) {
            documentoAtual.setDorme_sozinho(dormeSozinho.getValue());
        } else {
            documentoAtual.setDorme_sozinho("Não");
        }
        
        if (temQuarto != null && temQuarto.getValue() != null) {
            documentoAtual.setTem_seu_quarto(temQuarto.getValue());
        } else {
            documentoAtual.setTem_seu_quarto("Não");
        }
        
        String sonoVal = sono.getValue();
        documentoAtual.setSono_calmo_agitado(sonoVal != null ? sonoVal : "");
        
        // Aspectos sociais
        if (respeitaRegras != null && respeitaRegras.getValue() != null) {
            documentoAtual.setRespeita_regras(respeitaRegras.getValue());
        } else {
            documentoAtual.setRespeita_regras("Nunca");
        }
        
        if (desmotivado != null && desmotivado.getValue() != null) {
            documentoAtual.setE_desmotivado(desmotivado.getValue());
        } else {
            documentoAtual.setE_desmotivado("Nunca");
        }
        
        if (agressivo != null && agressivo.getValue() != null) {
            documentoAtual.setE_agressivo(agressivo.getValue());
        } else {
            documentoAtual.setE_agressivo("Nunca");
        }
        
        if (inquietacao != null && inquietacao.getValue() != null) {
            documentoAtual.setApresenta_inquietacao(inquietacao.getValue());
        } else {
            documentoAtual.setApresenta_inquietacao("Nunca");
        }
    }
    
    // Métodos Auxiliares
    

    // Seleciona RadioButtons a partir de um valor textual ("Sim"/"Não")
    private void selecionarToggle(RadioButton sim, RadioButton nao, String valor) {
        if (sim == null || nao == null) return;
        if (valor != null && valor.trim().equalsIgnoreCase("Sim")) {
            sim.setSelected(true);
        } else if (valor != null && (valor.trim().equalsIgnoreCase("Não") || valor.trim().equalsIgnoreCase("Nao"))) {
            nao.setSelected(true);
        }
    }
    
    // Métodos auxiliares para reduzir duplicação
    private void setTextSafe(TextField field, String value) {
        if (field != null && value != null) field.setText(value);
    }
    
    private void setValueSafe(ChoiceBox<String> choice, String value) {
        if (choice != null && value != null && !value.isEmpty()) choice.setValue(value);
    }
    
    private void carregarToggleComTexto(RadioButton sim, RadioButton nao, String valorToggle, TextField campo, String valorCampo) {
        selecionarToggle(sim, nao, valorToggle);
        setTextSafe(campo, valorCampo);
    }

    // Preenche os campos da tela com os dados já digitados ao navegar para trás
    private void preencherCamposComDadosExistentes() {
        if (documentoAtual == null) return;

        // Tela 1
        if (convulsaoSim != null) {
            selecionarToggle(convulsaoSim, convulsaoNao, documentoAtual.getTem_convulsao());
            carregarToggleComTexto(convenioSim, convenioNao, documentoAtual.getTem_convenio_medico(), convenio, documentoAtual.getNome_convenio());
            selecionarToggle(vacinacaoSim, vacinacaoNao, documentoAtual.getVacinas_em_dia());
            carregarToggleComTexto(doencaContagiosaSim, doencaContagiosaNao, documentoAtual.getTeve_doenca_contagiosa(), doencaContagiosa, documentoAtual.getQuais_doencas());
            setTextSafe(inicioEscolarizacao, documentoAtual.getInicio_escolarizacao());
            carregarToggleComTexto(dificuldadesSim, dificuldadesNao, documentoAtual.getApresenta_dificuldades(), dificuldades, documentoAtual.getQuais_dificuldades());
            carregarToggleComTexto(apoioPedagogicoSim, apoioPedagogicoNao, documentoAtual.getRecebe_apoio_pedagogico_casa(), apoioPedagogico, documentoAtual.getApoio_quem());
            carregarToggleComTexto(medicacaoSim, medicacaoNao, documentoAtual.getUsa_medicacao(), medicacoes, documentoAtual.getQuais_medicacoes());
            setTextSafe(servicosFrequentados1, documentoAtual.getQuais_servicos());
            selecionarToggle(preNatalSim, preNatalNao, documentoAtual.getFez_prenatal());
            setTextSafe(duracaoGestacao1, documentoAtual.getDuracao_da_gestacao());
            carregarToggleComTexto(prematuridadeSim, prematuridadeNao, documentoAtual.getHouve_prematuridade(), prematuridade1, documentoAtual.getCausa_prematuridade());
            return;
        }

        // Tela 2
        if (chorouSim != null) {
            setTextSafe(cidadeNascimento, documentoAtual.getCidade_nascimento());
            setTextSafe(maternidade, documentoAtual.getMaternidade());
            setValueSafe(tipoParto, documentoAtual.getTipo_parto());
            selecionarToggle(chorouSim, chorouNao, documentoAtual.getChorou_ao_nascer());
            selecionarToggle(ficouRoxoSim, ficouRoxoNao, documentoAtual.getFicou_roxo());
            selecionarToggle(incubadoraSim, incubadoraNao, documentoAtual.getUsou_incubadora());
            selecionarToggle(amamentadoSim, amamentadoNao, documentoAtual.getFoi_amamentado());
            carregarToggleComTexto(sustentouCabecaSim, sustentouCabecaNao, documentoAtual.getSustentou_a_cabeca(), sustentouCabeca, documentoAtual.getQuantos_meses_sustentou_cabeca());
            carregarToggleComTexto(engatinhouSim, engatinhouNao, documentoAtual.getEngatinhou(), engatinhou, documentoAtual.getQuantos_meses_engatinhou());
            carregarToggleComTexto(sentouSim, sentouNao, documentoAtual.getSentou(), sentou, documentoAtual.getQuantos_meses_sentou());
            carregarToggleComTexto(andouSim, andouNao, documentoAtual.getAndou(), andou, documentoAtual.getQuantos_meses_andou());
            carregarToggleComTexto(terapiaSim, terapiaNao, documentoAtual.getPrecisou_de_terapia(), terapia, documentoAtual.getQual_motivo_terapia());
            carregarToggleComTexto(falouSim, falouNao, documentoAtual.getFalou(), falou, documentoAtual.getQuantos_meses_falou());
            return;
        }

        // Tela 3
        if (disturbioSim != null || balbucio != null) {
            setValueSafe(balbucio, documentoAtual.getQuantos_meses_balbuciou());
            setTextSafe(primeiraPalavra, documentoAtual.getQuando_primeiras_palavras());
            setTextSafe(primeiraFrase, documentoAtual.getQuando_primeiras_frases());
            setValueSafe(tipoFala, documentoAtual.getFala_natural_inibido());
            carregarToggleComTexto(disturbioSim, disturbioNao, documentoAtual.getPossui_disturbio(), disturbio, documentoAtual.getQual_disturbio());
            setTextSafe(servicos, documentoAtual.getQuais_servicos());
            if (dormeSozinho != null) dormeSozinho.setValue(documentoAtual.getDorme_sozinho());
            if (temQuarto != null) temQuarto.setValue(documentoAtual.getTem_seu_quarto());
            setValueSafe(sono, documentoAtual.getSono_calmo_agitado());
            if (respeitaRegras != null) respeitaRegras.setValue(documentoAtual.getRespeita_regras());
            if (desmotivado != null) desmotivado.setValue(documentoAtual.getE_desmotivado());
            if (agressivo != null) agressivo.setValue(documentoAtual.getE_agressivo());
            if (inquietacao != null) inquietacao.setValue(documentoAtual.getApresenta_inquietacao());
        }
    }

    // Desabilita controles genéricos quando em modo de visualização
    private void desabilitarControles(Control... controles) {
        for (Control controle : controles) {
            if (controle != null) {
                controle.setDisable(true);
                if (controle instanceof TextField) {
                    ((TextField) controle).setEditable(false);
                }
            }
        }
    }
    
    // Abertura de fluxos (Métodos Estáticos para Factory)
    
    // Inicia uma nova anamnese.
    public static void iniciarNovaAnamnese() {
        iniciarNovo(ESTADO, new Anamnese());
    }

    // Inicia modo edição com uma anamnese já existente
    public static void editarAnamneseExistente(Anamnese existente) {
        iniciarEdicao(ESTADO, (existente != null) ? existente : new Anamnese());
    }

    // Inicia modo visualização com uma anamnese já existente
    public static void visualizarAnamnese(Anamnese existente) {
        iniciarVisualizacao(ESTADO, existente);
    }
    
    // Define o ID do educando para a anamnese
    public static void setEducandoIdParaAnamnese(String educandoId) {
        if (ESTADO.documentoCompartilhado == null) {
            ESTADO.documentoCompartilhado = new Anamnese();
        }
        ESTADO.documentoCompartilhado.setEducando_id(educandoId);
    }
    
    // Define a turma de origem para poder voltar
    public static void setTurmaOrigem(String turmaId) {
        setTurmaOrigem(ESTADO, turmaId);
    }
    
    
    // Handlers de UI
    
    //Handler para o botão Sair - fecha a janela/volta para a tela anterior
    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
    
    //Handler para o botão Cancelar - cancela o processo de anamnese
    @FXML
    protected void btnCancelarClick() {
        super.btnCancelarClick();
    }
    
    //Handler para o botão Turmas - navega para a tela de turmas
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
        EstadoDocumento<Anamnese> estado = getEstado();
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
    
    // Volta para a turma com popup do educando
    private void voltarComPopup(String educandoId) {
        EstadoDocumento<Anamnese> estado = getEstado();
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
    
    // Handler para o botão Seguinte da tela 1 - valida e avança para tela 2
    @FXML
    private void btnSeguinte2Click() {
        navegarTela(1, this::validarTelaAtual);
    }
    
    // Handler para o botão Anterior da tela 2 - volta para tela 1
    @FXML
    private void btnAnterior1Click() {
        navegarTela(-1, null);
    }
    
    // Handler para o botão Seguinte da tela 2 - valida e avança para tela 3
    @FXML
    private void btnSeguinte3Click() {
        navegarTela(1, this::validarTelaAtual);
    }
    
    // Handler para o botão Anterior da tela 3 - volta para tela 2
    @FXML
    private void btnAnterior2Click() {
        navegarTela(-1, null);
    }
    
    // Handler para o botão Concluir da tela 3 - valida e salva a anamnese
    @FXML
    private void btnConcluirClick() {
        EstadoDocumento<Anamnese> estado = getEstado();
        if (estado.modoAtual == ModoDocumento.VISUALIZACAO) {
            exibirMensagemErro("Modo visualização: não é possível salvar.");
            return;
        }

        // Valida a última tela antes de exibir o alerta
        if (!validarTela3()) {
            exibirMensagemErro("Preencha todos os campos para concluir.");
            return;
        }

        // Mostra aviso antes de salvar
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Concluir Anamnese");
        alerta.setHeaderText("Preencha todos os campos para concluir.");
        alerta.setContentText("Deseja salvar a anamnese agora?");
        var opcao = alerta.showAndWait();
        if (opcao.isEmpty() || opcao.get() != ButtonType.OK) {
            return;
        }

        preencherDadosTela3();
        // Garante que o objeto compartilhado tenha os dados mais recentes
        estado.documentoCompartilhado = documentoAtual;
        
        // Salva o educando ID antes de resetar
        String educandoId = documentoAtual.getEducando_id();
        
        try {
            // Preferir o professor logado; fallback para qualquer professor do banco
            String professorId = AuthService.getIdProfessorLogado();
            if (professorId == null) {
                professorId = anamneseService.buscarProfessorValido();
            }
            if (professorId == null || professorId.isBlank()) {
                exibirMensagemErro("Não foi possível identificar um professor para vincular à anamnese. Faça login como professor ou cadastre um professor.");
                return;
            }
            documentoAtual.setProfessor_id(professorId);

            if (documentoAtual.getEducando_id() == null || documentoAtual.getEducando_id().isBlank()) {
                exibirMensagemErro("Educando não definido nesta anamnese. Abra a anamnese a partir do aluno desejado.");
                return;
            }
            
            // Metadados obrigatórios para persistência
            documentoAtual.setData_criacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            
            anamneseService.cadastrarNovaAnamnese(documentoAtual);
            
            exibirMensagemSucesso("Anamnese criada com sucesso!");
            
            // Após 2s, volta para o popup de progresso do aluno
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
            
        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar anamnese: " + e.getMessage());
            e.printStackTrace();
        }
    }
}