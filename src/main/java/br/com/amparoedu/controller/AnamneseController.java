package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.AnamneseService;
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

public class AnamneseController implements Initializable {

    private final AnamneseService anamneseService = new AnamneseService();
    private Anamnese anamneseAtual = new Anamnese();
    
    // Rastreia a tela atual (1, 2 ou 3)
    private static int telaAtual = 1;
    
    // Armazena a anamnese entre as telas para não perder dados
    private static Anamnese anamneseCompartilhada;
    
    // Armazena a turma de onde veio o educando para poder voltar
    private static String turmaIdOrigem;
    
    // ToggleGroups para gerenciar os RadioButtons
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

    // Tela 1
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

    // Tela 2
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
    @FXML private RadioButton engatinhouSim;
    @FXML private RadioButton engatinhouNao;
    @FXML private TextField engatinhou;
    @FXML private RadioButton sentouSim;
    @FXML private RadioButton sentouNao;
    @FXML private TextField sentou;
    @FXML private RadioButton andouSim;
    @FXML private RadioButton andouNao;
    @FXML private TextField andou;
    @FXML private RadioButton terapiaSim;
    @FXML private RadioButton terapiaNao;
    @FXML private TextField terapia;
    @FXML private RadioButton falouSim;
    @FXML private RadioButton falouNao;
    @FXML private TextField falou;

    // Tela 3
    @FXML private ChoiceBox<String> balbucio;
    @FXML private TextField primeiraPalavra;
    @FXML private TextField primeiraFrase;
    @FXML private ChoiceBox<String> tipoFala;
    @FXML private RadioButton disturbioSim;
    @FXML private RadioButton disturbioNao;
    @FXML private TextField disturbio;
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
    
    // Inicializa o controller configurando os ToggleGroups e valores padrão.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Detecta qual tela está carregada pelos controles presentes
        if (convulsaoSim != null) {
            telaAtual = 1;
        } else if (chorouSim != null) {
            telaAtual = 2;
        } else if (disturbioSim != null || balbucio != null) {
            telaAtual = 3;
        }

        // Se é a primeira tela e não há estado compartilhado, cria uma nova anamnese
        if (telaAtual == 1 && anamneseCompartilhada == null) {
            anamneseCompartilhada = new Anamnese();
        }

        // Usa a anamnese compartilhada
        if (anamneseCompartilhada != null) {
            anamneseAtual = anamneseCompartilhada;
        }
        
        configurarToggleGroups();
        inicializarChoiceBoxes();
        ocultarCamposCondicionais();
    }
    
    // Configura os ToggleGroups para os RadioButtons.
    private void configurarToggleGroups() {
        // Tela 1
        if (convulsaoSim != null) {
            configurarToggle(convulsaoSim, convulsaoNao, convulsaoGroup, null);
            configurarToggle(convenioSim, convenioNao, convenioGroup, convenio);
            configurarToggle(vacinacaoSim, vacinacaoNao, vacinacaoGroup, null);
            configurarToggle(doencaContagiosaSim, doencaContagiosaNao, doencaContagiosaGroup, doencaContagiosa);
            configurarToggle(dificuldadesSim, dificuldadesNao, dificuldadesGroup, dificuldades);
            configurarToggle(apoioPedagogicoSim, apoioPedagogicoNao, apoioPedagogicoGroup, apoioPedagogico);
            configurarToggle(medicacaoSim, medicacaoNao, medicacaoGroup, medicacoes);
            configurarToggle(prematuridadeSim, prematuridadeNao, prematuridadeGroup, prematuridade1);
        }
        
        // Tela 2
        if (chorouSim != null) {
            configurarToggle(chorouSim, chorouNao, chorouGroup, null);
            configurarToggle(ficouRoxoSim, ficouRoxoNao, ficouRoxoGroup, null);
            configurarToggle(incubadoraSim, incubadoraNao, incubadoraGroup, null);
            configurarToggle(amamentadoSim, amamentadoNao, amamentadoGroup, null);
            configurarToggle(sustentouCabecaSim, sustentouCabecaNao, sustentouCabecaGroup, sustentouCabeca);
            configurarToggle(engatinhouSim, engatinhouNao, engatinhouGroup, engatinhou);
            configurarToggle(sentouSim, sentouNao, sentouGroup, sentou);
            configurarToggle(andouSim, andouNao, andouGroup, andou);
            configurarToggle(terapiaSim, terapiaNao, terapiaGroup, terapia);
            configurarToggle(falouSim, falouNao, falouGroup, falou);
        }
        
        // Tela 3
        if (disturbioSim != null) {
            configurarToggle(disturbioSim, disturbioNao, disturbioGroup, disturbio);
        }
    }
    
    // Método utilitário para configurar RadioButton em ToggleGroup e habilitar/desabilitar campo opcional.
    private void configurarToggle(RadioButton sim, RadioButton nao, ToggleGroup grupo, TextField campoOpcional) {
        if (sim == null || nao == null || grupo == null) {
            return;
        }
        sim.setToggleGroup(grupo);
        nao.setToggleGroup(grupo);
        if (campoOpcional != null) {
            // Oculta o campo inicialmente
            campoOpcional.setVisible(false);
            campoOpcional.setManaged(false);
            // Mostra/oculta o campo conforme o RadioButton é selecionado
            sim.selectedProperty().addListener((obs, old, selected) -> {
                campoOpcional.setVisible(selected);
                campoOpcional.setManaged(selected);
                if (!selected) {
                    campoOpcional.clear();
                }
            });
        }
    }
    
    // Métodos Auxiliares Genéricos
    
    // Valida se um campo de texto obrigatório está preenchido.
    private boolean validarCampoObrigatorio(TextField campo, String mensagemErro) {
        if (campo == null || campo.getText() == null || campo.getText().trim().isEmpty()) {
            exibirMensagemErro(mensagemErro);
            return false;
        }
        return true;
    }
    
    // Valida se um ToggleGroup tem uma opção selecionada.
    private boolean validarToggleObrigatorio(ToggleGroup grupo, String mensagemErro) {
        if (grupo.getSelectedToggle() == null) {
            exibirMensagemErro(mensagemErro);
            return false;
        }
        return true;
    }
    
    // Valida se um ChoiceBox tem um valor selecionado.
    private boolean validarChoiceBoxObrigatorio(ChoiceBox<?> choiceBox, String mensagemErro) {
        if (choiceBox == null || choiceBox.getValue() == null) {
            exibirMensagemErro(mensagemErro);
            return false;
        }
        return true;
    }
    
    // Retorna 1 se o RadioButton está selecionado, 0 caso contrário.
    private int getValorToggle(RadioButton radioButton) {
        return radioButton.isSelected() ? 1 : 0;
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
                                java.util.function.IntConsumer setFlag,
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
        ocultarCampos(convenio, doencaContagiosa, dificuldades, apoioPedagogico, medicacoes,
                      sustentouCabeca, engatinhou, sentou, andou, terapia, falou, prematuridade1, disturbio);
    }
    
    // Oculta múltiplos campos de uma vez
    private void ocultarCampos(TextField... campos) {
        for (TextField campo : campos) {
            if (campo != null) {
                campo.setVisible(false);
                campo.setManaged(false);
            }
        }
    }

    // Métodos de Validação
    
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
        
        return true;
    }
    
    // Métodos auxiliares para preencher objeto
    
    //Preenche os dados da Tela 1 no objeto Anamnese.
    private void preencherDadosTela1() {
        anamneseAtual.setTem_convulsao(getValorToggle(convulsaoSim));
        preencherCampo(convenioSim, convenio, anamneseAtual::setTem_convenio_medico, anamneseAtual::setNome_convenio);
        preencherCampo(doencaContagiosaSim, doencaContagiosa, anamneseAtual::setTeve_doenca_contagiosa, anamneseAtual::setQuais_doencas);
        
        // Vacinação
        anamneseAtual.setVacinas_em_dia(getValorToggle(vacinacaoSim));

        String inicioEscVal = inicioEscolarizacao.getText();
        anamneseAtual.setInicio_escolarizacao(inicioEscVal != null ? inicioEscVal : "");
        
        preencherCampo(dificuldadesSim, dificuldades, anamneseAtual::setApresenta_dificuldades, anamneseAtual::setQuais_dificuldades);
        preencherCampo(apoioPedagogicoSim, apoioPedagogico, anamneseAtual::setRecebe_apoio_pedagogico_casa, anamneseAtual::setApoio_quem);
        preencherCampo(medicacaoSim, medicacoes, anamneseAtual::setUsa_medicacao, anamneseAtual::setQuais_medicacoes);

        // Serviços de saúde ou educação frequentados (tela 1)
        if (servicosFrequentados1 != null && servicosFrequentados1.getText() != null) {
            String sv = servicosFrequentados1.getText().trim();
            anamneseAtual.setQuais_servicos(sv);
            anamneseAtual.setUsou_servico_saude_educacao(sv.isEmpty() ? 0 : 1);
        }

        // Dados da gestação
        if (duracaoGestacao1 != null) {
            String duracao = duracaoGestacao1.getText();
            anamneseAtual.setDuracao_da_gestacao(duracao != null ? duracao : "");
        }
        anamneseAtual.setFez_prenatal(getValorToggle(preNatalSim));
        preencherCampo(prematuridadeSim, prematuridade1, anamneseAtual::setHouve_prematuridade, anamneseAtual::setCausa_prematuridade);
    }
    
    //Preenche os dados da Tela 2 no objeto Anamnese.
    private void preencherDadosTela2() {
        String cidadeVal = cidadeNascimento.getText();
        anamneseAtual.setCidade_nascimento(cidadeVal != null ? cidadeVal : "");
        
        String maternidadeVal = maternidade.getText();
        anamneseAtual.setMaternidade(maternidadeVal != null ? maternidadeVal : "");
        
        String tipoPartoVal = tipoParto.getValue();
        anamneseAtual.setTipo_parto(tipoPartoVal != null ? tipoPartoVal : "");
        
        anamneseAtual.setChorou_ao_nascer(getValorToggle(chorouSim));
        anamneseAtual.setFicou_roxo(getValorToggle(ficouRoxoSim));
        anamneseAtual.setUsou_incubadora(getValorToggle(incubadoraSim));
        anamneseAtual.setFoi_amamentado(getValorToggle(amamentadoSim));
        
        preencherCampo(sustentouCabecaSim, sustentouCabeca, anamneseAtual::setSustentou_a_cabeca, anamneseAtual::setQuantos_meses_sustentou_cabeca);
        preencherCampo(engatinhouSim, engatinhou, anamneseAtual::setEngatinhou, anamneseAtual::setQuantos_meses_engatinhou);
        preencherCampo(sentouSim, sentou, anamneseAtual::setSentou, anamneseAtual::setQuantos_meses_sentou);
        preencherCampo(andouSim, andou, anamneseAtual::setAndou, anamneseAtual::setQuantos_meses_andou);
        preencherCampo(terapiaSim, terapia, anamneseAtual::setPrecisou_de_terapia, anamneseAtual::setQual_motivo_terapia);
        preencherCampo(falouSim, falou, anamneseAtual::setFalou, anamneseAtual::setQuantos_meses_falou);
    }
    
    //Preenche os dados da Tela 3 no objeto Anamnese.
    private void preencherDadosTela3() {
        // Comunicação e linguagem
        String balbucioVal = balbucio.getValue();
        anamneseAtual.setQuantos_meses_balbuciou(balbucioVal != null ? balbucioVal : "");
        
        String primeiraPalavraVal = primeiraPalavra.getText();
        anamneseAtual.setQuando_primeiras_palavras(primeiraPalavraVal != null ? primeiraPalavraVal : "");
        
        String primeiraFraseVal = primeiraFrase.getText();
        anamneseAtual.setQuando_primeiras_frases(primeiraFraseVal != null ? primeiraFraseVal : "");
        
        String tipoFalaVal = tipoFala.getValue();
        anamneseAtual.setFala_natural_inibido(tipoFalaVal != null ? tipoFalaVal : "");
        
        preencherCampo(disturbioSim, disturbio, anamneseAtual::setPossui_disturbio, anamneseAtual::setQual_disturbio);

        // Serviços de saúde ou educação frequentados (tela 3, se preenchido aqui)
        if (servicos != null && servicos.getText() != null) {
            String sv = servicos.getText().trim();
            if (!sv.isEmpty()) {
                anamneseAtual.setQuais_servicos(sv);
                anamneseAtual.setUsou_servico_saude_educacao(1);
            }
        }
        
        // Preenche os campos opcionais da Tela 3 (sleep)
        if (dormeSozinho.getValue() != null) {
            anamneseAtual.setDorme_sozinho(converterSimNao(dormeSozinho.getValue()));
        } else {
            anamneseAtual.setDorme_sozinho(-1);
        }
        
        if (temQuarto.getValue() != null) {
            anamneseAtual.setTem_seu_quarto(converterSimNao(temQuarto.getValue()));
        } else {
            anamneseAtual.setTem_seu_quarto(-1);
        }
        
        String sonoVal = sono.getValue();
        anamneseAtual.setSono_calmo_agitado(sonoVal != null ? sonoVal : "");
        
        // Aspectos sociais
        if (respeitaRegras.getValue() != null) {
            anamneseAtual.setRespeita_regras(converterSimNao(respeitaRegras.getValue()));
        } else {
            anamneseAtual.setRespeita_regras(-1);
        }
        
        if (desmotivado.getValue() != null) {
            anamneseAtual.setE_desmotivado(converterSimNao(desmotivado.getValue()));
        } else {
            anamneseAtual.setE_desmotivado(-1);
        }
        
        if (agressivo.getValue() != null) {
            anamneseAtual.setE_agressivo(converterSimNao(agressivo.getValue()));
        } else {
            anamneseAtual.setE_agressivo(-1);
        }
        
        if (inquietacao.getValue() != null) {
            anamneseAtual.setApresenta_inquietacao(converterSimNao(inquietacao.getValue()));
        } else {
            anamneseAtual.setApresenta_inquietacao(-1);
        }
    }
    
    // Métodos Auxiliares
    
    //Converte valores de ChoiceBox (Sim/Não) para inteiro.
    private int converterSimNao(String valor) {
        if (valor == null) return -1;
        return switch (valor) {
            case "Sim" -> 1;
            case "Não" -> 0;
            default -> -1;
        };
    }
    
    // Navega delta telas, aplicando validação e salvamento opcionais.
    private void navegarDelta(int delta, Runnable salvarDados, BooleanSupplier validarAntes, String mensagemLimite) {
        int novaTela = telaAtual + delta;

        // Limites de navegação
        if (novaTela < 1 || novaTela > 3) {
            exibirMensagemErro(mensagemLimite);
            return;
        }

        if (validarAntes != null && !validarAntes.getAsBoolean()) {
            return;
        }

        if (salvarDados != null) {
            salvarDados.run();
        }

        anamneseCompartilhada = anamneseAtual;
        telaAtual = novaTela;
        GerenciadorTelas.getInstance().trocarTela("anamnese-" + novaTela + ".fxml");
    }
    
    // Inicia uma nova anamnese.
    public static void iniciarNovaAnamnese() {
        telaAtual = 1;
        anamneseCompartilhada = new Anamnese();
    }
    
    // Define o ID do educando para a anamnese
    public static void setEducandoIdParaAnamnese(String educandoId) {
        if (anamneseCompartilhada == null) {
            anamneseCompartilhada = new Anamnese();
        }
        anamneseCompartilhada.setEducando_id(educandoId);
    }
    
    // Define a turma de origem para poder voltar
    public static void setTurmaOrigem(String turmaId) {
        turmaIdOrigem = turmaId;
    }
    
    
    //Exibe mensagem de erro para o usuário.
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
    
    //Exibe mensagem de sucesso para o usuário
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
    
    //Handler para o botão Sair - fecha a janela/volta para a tela anterior
    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
    
    //Handler para o botão Cancelar - cancela o processo de anamnese
    @FXML
    private void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Anamnese");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            resetarAnamnese();
            String educandoId = anamneseAtual.getEducando_id();
            voltarComPopup(educandoId);
        }
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
    
    // Reseta o estado da anamnese
    private void resetarAnamnese() {
        telaAtual = 1;
        anamneseCompartilhada = null;
        anamneseAtual = new Anamnese();
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
                            javafx.fxml.FXMLLoader popupLoader = GerenciadorTelas.getLoader("progresso-atendimento.fxml");
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
    
    // Handler para o botão Seguinte da tela 1 - valida e avança para tela 2
    @FXML
    private void btnSeguinte2Click() {
        navegarDelta(1, this::preencherDadosTela1, this::validarTela1,
                "Você já está na primeira tela. Avance para continuar.");
    }
    
    // Handler para o botão Anterior da tela 2 - volta para tela 1
    @FXML
    private void btnAnterior1Click() {
        navegarDelta(-1, this::preencherDadosTela2, null,
                "Você já está na primeira tela.");
    }
    
    // Handler para o botão Seguinte da tela 2 - valida e avança para tela 3
    @FXML
    private void btnSeguinte3Click() {
        navegarDelta(1, this::preencherDadosTela2, this::validarTela2,
                "Você já está na última tela. Clique em Concluir para salvar.");
    }
    
    // Handler para o botão Anterior da tela 3 - volta para tela 2
    @FXML
    private void btnAnterior2Click() {
        navegarDelta(-1, this::preencherDadosTela3, null,
                "Você já está na primeira tela.");
    }
    
    // Handler para o botão Concluir da tela 3 - valida e salva a anamnese
    @FXML
    private void btnConcluirClick() {
        if (validarTela3()) {
            preencherDadosTela3();
            // Garante que o objeto compartilhado tenha os dados mais recentes
            anamneseCompartilhada = anamneseAtual;
            
            try {
                // Busca um professor válido no banco de dados
                String professorId = anamneseService.buscarProfessorValido();
                if (professorId != null) {
                    anamneseCompartilhada.setProfessor_id(professorId);
                }
                
                // Metadados obrigatórios para persistência
                anamneseCompartilhada.setData_criacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                
                anamneseService.cadastrarNovaAnamnese(anamneseCompartilhada);
                
                exibirMensagemSucesso("Anamnese criada com sucesso!");
                
                // Após 2s, volta para o popup de progresso do aluno
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            resetarAnamnese();
                            String educandoId = anamneseAtual.getEducando_id();
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
}