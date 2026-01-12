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

    // Modo de uso
    public enum ModoAnamnese { NOVA, EDICAO, VISUALIZACAO }

    // Estado e serviços
    private final AnamneseService anamneseService = new AnamneseService();
    private Anamnese anamneseAtual = new Anamnese();
    private static int telaAtual = 1; // 1, 2 ou 3
    private static Anamnese anamneseCompartilhada;
    private static String turmaIdOrigem;
    private static ModoAnamnese modoAtual = ModoAnamnese.NOVA;
    private static boolean navegandoEntreTelas;
    
    // Controles (ToggleGroups)
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

    // Controles (Tela 3)
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
    @FXML private Button btnConcluir;
    
    // Ciclo de vida
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean vindoDeNavegacao = navegandoEntreTelas;
        navegandoEntreTelas = false;

        // Detecta qual tela está carregada pelos controles presentes
        if (convulsaoSim != null) {
            telaAtual = 1;
        } else if (chorouSim != null) {
            telaAtual = 2;
        } else if (disturbioSim != null || balbucio != null) {
            telaAtual = 3;
        }

        // Se não veio de navegação, prepara estado conforme o modo selecionado
        if (!vindoDeNavegacao) {
            if (modoAtual == ModoAnamnese.NOVA) {
                telaAtual = 1;
                // Só cria nova anamnese se não houver uma já compartilhada (com educando_id já setado)
                if (anamneseCompartilhada == null || anamneseCompartilhada.getEducando_id() == null) {
                    anamneseCompartilhada = new Anamnese();
                }
            } else {
                if (anamneseCompartilhada == null) {
                    anamneseCompartilhada = new Anamnese();
                }
                telaAtual = 1;
            }
        } else if (telaAtual == 1 && anamneseCompartilhada == null) {
            // fallback: se por algum motivo não existir, cria
            anamneseCompartilhada = new Anamnese();
        }

        // Usa a anamnese compartilhada
        if (anamneseCompartilhada != null) {
            anamneseAtual = anamneseCompartilhada;
        }
        
        // Primeiro esconde campos condicionais antes de criar vínculos de visibilidade
        ocultarCamposCondicionais();
        configurarToggleGroups();
        inicializarChoiceBoxes();
        if (modoAtual != ModoAnamnese.NOVA) {
            preencherCamposComDadosExistentes();
        }
        desabilitarEdicaoSeVisualizacao();
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
            // Tela 1 com labels
            if (convulsaoSim != null) {
                configurarToggle(convenioSim, convenioNao, convenioGroup, convenio, questConvenio);
                configurarToggle(doencaContagiosaSim, doencaContagiosaNao, doencaContagiosaGroup, doencaContagiosa, questDoencaContagiosa);
                configurarToggle(dificuldadesSim, dificuldadesNao, dificuldadesGroup, dificuldades, questDificuldade);
                configurarToggle(apoioPedagogicoSim, apoioPedagogicoNao, apoioPedagogicoGroup, apoioPedagogico, questApioPedagogico);
                configurarToggle(medicacaoSim, medicacaoNao, medicacaoGroup, medicacoes, questMedicacao);
                configurarToggle(prematuridadeSim, prematuridadeNao, prematuridadeGroup, prematuridade1, questCausaPrematuridade1);
            }
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
    }
    
    // Métodos Auxiliares Genéricos

        // Sobrecarga: configurar RadioButton com Label também
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
            anamneseAtual.setDorme_sozinho(converterSimNaoAsVezes(dormeSozinho.getValue()));
        } else {
            anamneseAtual.setDorme_sozinho(0);
        }
        
        if (temQuarto.getValue() != null) {
            anamneseAtual.setTem_seu_quarto(converterSimNaoAsVezes(temQuarto.getValue()));
        } else {
            anamneseAtual.setTem_seu_quarto(0);
        }
        
        String sonoVal = sono.getValue();
        anamneseAtual.setSono_calmo_agitado(sonoVal != null ? sonoVal : "");
        
        // Aspectos sociais
        if (respeitaRegras.getValue() != null) {
            anamneseAtual.setRespeita_regras(converterEscalaFrequencia(respeitaRegras.getValue()));
        } else {
            anamneseAtual.setRespeita_regras(0);
        }
        
        if (desmotivado.getValue() != null) {
            anamneseAtual.setE_desmotivado(converterEscalaFrequencia(desmotivado.getValue()));
        } else {
            anamneseAtual.setE_desmotivado(0);
        }
        
        if (agressivo.getValue() != null) {
            anamneseAtual.setE_agressivo(converterEscalaFrequencia(agressivo.getValue()));
        } else {
            anamneseAtual.setE_agressivo(0);
        }
        
        if (inquietacao.getValue() != null) {
            anamneseAtual.setApresenta_inquietacao(converterEscalaFrequencia(inquietacao.getValue()));
        } else {
            anamneseAtual.setApresenta_inquietacao(0);
        }
    }
    
    // Métodos Auxiliares
    

    // Seleciona RadioButtons a partir de um valor inteiro (1 = sim, 0 = não)
    private void selecionarToggle(RadioButton sim, RadioButton nao, int valor) {
        if (sim == null || nao == null) return;
        if (valor == 1) {
            sim.setSelected(true);
        } else if (valor == 0) {
            nao.setSelected(true);
        }
    }


    // Converte ChoiceBox Sim/Não/Às vezes para inteiro
    private int converterSimNaoAsVezes(String valor) {
        if (valor == null) return -1;
        return switch (valor) {
            case "Sim" -> 1;
            case "Não" -> 0;
            case "Às vezes" -> 2;
            default -> -1;
        };
    }

    // Converte ChoiceBox de frequência para inteiro (Sempre=3, Às vezes=2, Raramente=1, Nunca=0)
    private int converterEscalaFrequencia(String valor) {
        if (valor == null) return -1;
        return switch (valor) {
            case "Sempre" -> 3;
            case "Às vezes" -> 2;
            case "Raramente" -> 1;
            case "Nunca" -> 0;
            default -> -1;
        };
    }

    // Mapeia inteiros para ChoiceBox Sim/Não/Às vezes
    private String toChoiceSimNaoAsVezes(int valor) {
        return switch (valor) {
            case 1 -> "Sim";
            case 0 -> "Não";
            case 2 -> "Às vezes";
            default -> null;
        };
    }

    // Mapeia inteiros para ChoiceBox de frequência
    private String toChoiceFrequencia(int valor) {
        return switch (valor) {
            case 3 -> "Sempre";
            case 2 -> "Às vezes";
            case 1 -> "Raramente";
            case 0 -> "Nunca";
            default -> null;
        };
    }

    // Preenche os campos da tela com os dados já digitados ao navegar para trás
    private void preencherCamposComDadosExistentes() {
        if (anamneseAtual == null) return;

        // Tela 1
        if (convulsaoSim != null) {
            selecionarToggle(convulsaoSim, convulsaoNao, anamneseAtual.getTem_convulsao());
            selecionarToggle(convenioSim, convenioNao, anamneseAtual.getTem_convenio_medico());
            if (convenio != null) convenio.setText(anamneseAtual.getNome_convenio());

            selecionarToggle(vacinacaoSim, vacinacaoNao, anamneseAtual.getVacinas_em_dia());

            selecionarToggle(doencaContagiosaSim, doencaContagiosaNao, anamneseAtual.getTeve_doenca_contagiosa());
            if (doencaContagiosa != null) doencaContagiosa.setText(anamneseAtual.getQuais_doencas());

            if (inicioEscolarizacao != null) inicioEscolarizacao.setText(anamneseAtual.getInicio_escolarizacao());

            selecionarToggle(dificuldadesSim, dificuldadesNao, anamneseAtual.getApresenta_dificuldades());
            if (dificuldades != null) dificuldades.setText(anamneseAtual.getQuais_dificuldades());

            selecionarToggle(apoioPedagogicoSim, apoioPedagogicoNao, anamneseAtual.getRecebe_apoio_pedagogico_casa());
            if (apoioPedagogico != null) apoioPedagogico.setText(anamneseAtual.getApoio_quem());

            selecionarToggle(medicacaoSim, medicacaoNao, anamneseAtual.getUsa_medicacao());
            if (medicacoes != null) medicacoes.setText(anamneseAtual.getQuais_medicacoes());

            if (servicosFrequentados1 != null) servicosFrequentados1.setText(anamneseAtual.getQuais_servicos());

            selecionarToggle(preNatalSim, preNatalNao, anamneseAtual.getFez_prenatal());
            if (duracaoGestacao1 != null) duracaoGestacao1.setText(anamneseAtual.getDuracao_da_gestacao());

            selecionarToggle(prematuridadeSim, prematuridadeNao, anamneseAtual.getHouve_prematuridade());
            if (prematuridade1 != null) prematuridade1.setText(anamneseAtual.getCausa_prematuridade());
            return;
        }

        // Tela 2
        if (chorouSim != null) {
            if (cidadeNascimento != null) cidadeNascimento.setText(anamneseAtual.getCidade_nascimento());
            if (maternidade != null) maternidade.setText(anamneseAtual.getMaternidade());
            if (tipoParto != null && anamneseAtual.getTipo_parto() != null && !anamneseAtual.getTipo_parto().isEmpty()) {
                tipoParto.setValue(anamneseAtual.getTipo_parto());
            }

            selecionarToggle(chorouSim, chorouNao, anamneseAtual.getChorou_ao_nascer());
            selecionarToggle(ficouRoxoSim, ficouRoxoNao, anamneseAtual.getFicou_roxo());
            selecionarToggle(incubadoraSim, incubadoraNao, anamneseAtual.getUsou_incubadora());
            selecionarToggle(amamentadoSim, amamentadoNao, anamneseAtual.getFoi_amamentado());

            selecionarToggle(sustentouCabecaSim, sustentouCabecaNao, anamneseAtual.getSustentou_a_cabeca());
            if (sustentouCabeca != null) sustentouCabeca.setText(anamneseAtual.getQuantos_meses_sustentou_cabeca());

            selecionarToggle(engatinhouSim, engatinhouNao, anamneseAtual.getEngatinhou());
            if (engatinhou != null) engatinhou.setText(anamneseAtual.getQuantos_meses_engatinhou());

            selecionarToggle(sentouSim, sentouNao, anamneseAtual.getSentou());
            if (sentou != null) sentou.setText(anamneseAtual.getQuantos_meses_sentou());

            selecionarToggle(andouSim, andouNao, anamneseAtual.getAndou());
            if (andou != null) andou.setText(anamneseAtual.getQuantos_meses_andou());

            selecionarToggle(terapiaSim, terapiaNao, anamneseAtual.getPrecisou_de_terapia());
            if (terapia != null) terapia.setText(anamneseAtual.getQual_motivo_terapia());

            selecionarToggle(falouSim, falouNao, anamneseAtual.getFalou());
            if (falou != null) falou.setText(anamneseAtual.getQuantos_meses_falou());
            return;
        }

        // Tela 3
        if (disturbioSim != null || balbucio != null) {
            if (balbucio != null && anamneseAtual.getQuantos_meses_balbuciou() != null && !anamneseAtual.getQuantos_meses_balbuciou().isEmpty()) {
                balbucio.setValue(anamneseAtual.getQuantos_meses_balbuciou());
            }

            if (primeiraPalavra != null) primeiraPalavra.setText(anamneseAtual.getQuando_primeiras_palavras());
            if (primeiraFrase != null) primeiraFrase.setText(anamneseAtual.getQuando_primeiras_frases());

            if (tipoFala != null && anamneseAtual.getFala_natural_inibido() != null && !anamneseAtual.getFala_natural_inibido().isEmpty()) {
                tipoFala.setValue(anamneseAtual.getFala_natural_inibido());
            }

            selecionarToggle(disturbioSim, disturbioNao, anamneseAtual.getPossui_disturbio());
            if (disturbio != null) disturbio.setText(anamneseAtual.getQual_disturbio());

            if (servicos != null) servicos.setText(anamneseAtual.getQuais_servicos());

            if (dormeSozinho != null) dormeSozinho.setValue(toChoiceSimNaoAsVezes(anamneseAtual.getDorme_sozinho()));
            if (temQuarto != null) temQuarto.setValue(toChoiceSimNaoAsVezes(anamneseAtual.getTem_seu_quarto()));
            if (sono != null && anamneseAtual.getSono_calmo_agitado() != null && !anamneseAtual.getSono_calmo_agitado().isEmpty()) {
                sono.setValue(anamneseAtual.getSono_calmo_agitado());
            }

            if (respeitaRegras != null) respeitaRegras.setValue(toChoiceFrequencia(anamneseAtual.getRespeita_regras()));
            if (desmotivado != null) desmotivado.setValue(toChoiceFrequencia(anamneseAtual.getE_desmotivado()));
            if (agressivo != null) agressivo.setValue(toChoiceFrequencia(anamneseAtual.getE_agressivo()));
            if (inquietacao != null) inquietacao.setValue(toChoiceFrequencia(anamneseAtual.getApresenta_inquietacao()));
        }
    }

    // Desabilita edição quando em modo de visualização
    private void desabilitarEdicaoSeVisualizacao() {
        if (modoAtual != ModoAnamnese.VISUALIZACAO) return;

        desabilitarControlesTextuais(convenio, doencaContagiosa, inicioEscolarizacao, dificuldades, apoioPedagogico,
                medicacoes, servicosFrequentados1, prematuridade1, duracaoGestacao1, cidadeNascimento, maternidade,
                sustentouCabeca, engatinhou, sentou, andou, terapia, falou, primeiraPalavra, primeiraFrase, disturbio,
                servicos);

        desabilitarRadios(convulsaoSim, convulsaoNao, convenioSim, convenioNao, vacinacaoSim, vacinacaoNao,
                doencaContagiosaSim, doencaContagiosaNao, dificuldadesSim, dificuldadesNao, apoioPedagogicoSim,
                apoioPedagogicoNao, medicacaoSim, medicacaoNao, preNatalSim, preNatalNao, prematuridadeSim,
                prematuridadeNao, chorouSim, chorouNao, ficouRoxoSim, ficouRoxoNao, incubadoraSim, incubadoraNao,
                amamentadoSim, amamentadoNao, sustentouCabecaSim, sustentouCabecaNao, engatinhouSim, engatinhouNao,
                sentouSim, sentouNao, andouSim, andouNao, terapiaSim, terapiaNao, falouSim, falouNao, disturbioSim,
                disturbioNao);

        desabilitarChoices(tipoParto, balbucio, tipoFala, dormeSozinho, temQuarto, sono, respeitaRegras, desmotivado,
                agressivo, inquietacao);

        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }
    }

    private void desabilitarControlesTextuais(TextField... campos) {
        for (TextField campo : campos) {
            if (campo != null) {
                campo.setEditable(false);
                campo.setDisable(true);
            }
        }
    }

    private void desabilitarRadios(RadioButton... radios) {
        for (RadioButton rb : radios) {
            if (rb != null) {
                rb.setDisable(true);
            }
        }
    }

    private void desabilitarChoices(ChoiceBox<?>... choices) {
        for (ChoiceBox<?> cb : choices) {
            if (cb != null) {
                cb.setDisable(true);
            }
        }
    }
    
    // Navegação
    // Navega entre telas, aplicando validação e salvamento opcionais.
    private void navegarEntreTelas(int passo, Runnable salvarDados, BooleanSupplier validarAntes, String mensagemLimite) {
        int novaTela = telaAtual + passo;

        // Limites de navegação
        if (novaTela < 1 || novaTela > 3) {
            exibirMensagemErro(mensagemLimite);
            return;
        }

        if (modoAtual != ModoAnamnese.VISUALIZACAO && validarAntes != null && !validarAntes.getAsBoolean()) {
            return;
        }

        if (modoAtual != ModoAnamnese.VISUALIZACAO && salvarDados != null) {
            salvarDados.run();
        }

        anamneseCompartilhada = anamneseAtual;
        telaAtual = novaTela;
        navegandoEntreTelas = true;
        GerenciadorTelas.getInstance().trocarTela("anamnese-" + novaTela + ".fxml");
    }
    
    // Abertura de fluxos
    // Inicia uma nova anamnese.
    public static void iniciarNovaAnamnese() {
        modoAtual = ModoAnamnese.NOVA;
        telaAtual = 1;
        anamneseCompartilhada = new Anamnese();
        navegandoEntreTelas = false;
    }

    // Inicia modo edição com uma anamnese já existente
    public static void editarAnamneseExistente(Anamnese existente) {
        modoAtual = ModoAnamnese.EDICAO;
        telaAtual = 1;
        anamneseCompartilhada = (existente != null) ? existente : new Anamnese();
        navegandoEntreTelas = false;
    }

    // Inicia modo visualização com uma anamnese já existente
    public static void visualizarAnamnese(Anamnese existente) {
        modoAtual = ModoAnamnese.VISUALIZACAO;
        telaAtual = 1;
        anamneseCompartilhada = existente;
        navegandoEntreTelas = false;
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
    
    
    // Mensagens
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
    
    // Handlers de UI
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
            // Salva o educando ID antes de resetar
            String educandoId = anamneseAtual.getEducando_id();
            resetarAnamnese();
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
        navegarEntreTelas(1, this::preencherDadosTela1, this::validarTela1,
            "Você já está na primeira tela. Avance para continuar.");
    }
    
    // Handler para o botão Anterior da tela 2 - volta para tela 1
    @FXML
    private void btnAnterior1Click() {
        navegarEntreTelas(-1, this::preencherDadosTela2, null, "Você já está na primeira tela.");
    }
    
    // Handler para o botão Seguinte da tela 2 - valida e avança para tela 3
    @FXML
    private void btnSeguinte3Click() {
        navegarEntreTelas(1, this::preencherDadosTela2, this::validarTela2, "Você já está na última tela.");
    }
    
    // Handler para o botão Anterior da tela 3 - volta para tela 2
    @FXML
    private void btnAnterior2Click() {
        navegarEntreTelas(-1, this::preencherDadosTela3, null,
                "Você já está na primeira tela.");
    }
    
    // Handler para o botão Concluir da tela 3 - valida e salva a anamnese
    @FXML
    private void btnConcluirClick() {
        if (modoAtual == ModoAnamnese.VISUALIZACAO) {
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
        anamneseCompartilhada = anamneseAtual;
        
        // Salva o educando ID antes de resetar
        String educandoId = anamneseCompartilhada.getEducando_id();
        
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