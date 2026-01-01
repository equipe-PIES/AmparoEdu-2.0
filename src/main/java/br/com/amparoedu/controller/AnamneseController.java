package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnamneseController {

    @FXML private BorderPane anamnese;
    @FXML private Label nameUser, cargoUser;
    
    // ETAPA 1 - Campos
    @FXML private CheckBox convulsaoSim, convulsaoNao;
    @FXML private CheckBox convenioSim, convenioNao;
    @FXML private TextField convenio;
    @FXML private CheckBox vacinacaoSim, vacinacaoNao;
    @FXML private CheckBox doencaContagiosaSim, doencaContagiosaNao;
    @FXML private TextField doencaContagiosa;
    @FXML private CheckBox medicacaoSim, medicacaoNao;
    @FXML private TextField medicacoes;
    @FXML private TextField servicosFrequentados1;
    @FXML private TextField inicioEscolarizacao;
    @FXML private CheckBox dificuldadesSim, dificuldadesNao;
    @FXML private TextField dificuldades;
    @FXML private CheckBox apoioPedagogicoSim, apoioPedagogicoNao;
    @FXML private TextField apoioPedagogico;
    @FXML private TextField duracaoGestacao1;
    @FXML private CheckBox preNatalSim, preNatalNao;
    @FXML private CheckBox prematuridadeSim, prematuridadeNao;
    @FXML private TextField prematuridade1;
    
    // ETAPA 2 - Campos
    @FXML private TextField cidadeNascimento;
    @FXML private TextField maternidade;
    @FXML private ChoiceBox<String> tipoParto;
    @FXML private CheckBox chorouSim, chorouNao;
    @FXML private CheckBox ficouRoxoSim, ficouRoxoNao;
    @FXML private CheckBox incubadoraSim, incubadoraNao;
    @FXML private CheckBox amamentadoSim, amamentadoNao;
    @FXML private CheckBox sustentouCabecaSim, sustentouCabecaNao;
    @FXML private TextField sustentouCabeca;
    @FXML private CheckBox engatinhouSim, engatinhouNao;
    @FXML private TextField engatinhou;
    @FXML private CheckBox sentouSim, sentouNao;
    @FXML private TextField sentou;
    @FXML private CheckBox andouSim, andouNao;
    @FXML private TextField andou;
    @FXML private CheckBox terapiaSim, terapiaNao;
    @FXML private TextField terapia;
    @FXML private CheckBox falouSim, falouNao;
    @FXML private TextField falou;
    
    // ETAPA 3 - Campos
    @FXML private ChoiceBox<String> balbucio;
    @FXML private TextField primeiraPalavra;
    @FXML private TextField primeiraFrase;
    @FXML private ChoiceBox<String> tipoFala;
    @FXML private CheckBox disturbioSim, disturbioNao;
    @FXML private TextField disturbio;
    @FXML private TextField servicos;
    @FXML private ChoiceBox<String> dormeSozinho;
    @FXML private ChoiceBox<String> temQuarto;
    @FXML private ChoiceBox<String> sono;
    @FXML private ChoiceBox<String> respeitaRegras;
    @FXML private ChoiceBox<String> desmotivado;
    @FXML private ChoiceBox<String> agressivo;
    @FXML private ChoiceBox<String> inquietacao;
    
    // Botões
    @FXML private Button btnConcluir, btnSair, btnCancelar;
    @FXML private Button btnTurmas, btnAlunos;

    private static Anamnese anamneseData = new Anamnese();
    private static Educando educandoAtual;
    private final AnamneseService anamneseService = new AnamneseService();

    @FXML
    public void initialize() {
        configurarLabelsUsuario();
        sincronizarCamposComEstado();
        configurarVisibilidadeCondicional();
        inicializarChoiceBoxes();
    }

    public void setEducando(Educando educando) {
        educandoAtual = educando;
        anamneseData.setEducando_id(educando.getId());
        anamneseData.setProfessor_id(AuthService.getUsuarioLogado().getId());
    }

    @FXML
    private void btnSeguinte2Click() { // Muda para Anamnese 2
        capturarDadosAtuais();
        GerenciadorTelas.trocarTela("anamnese-2.fxml");
    }

    @FXML
    private void btnSeguinte3Click() { // Muda para Anamnese 3
        capturarDadosAtuais();
        GerenciadorTelas.trocarTela("anamnese-3.fxml");
    }

    @FXML
    private void btnAnterior1Click() { // Volta para Anamnese 1
        capturarDadosAtuais();
        GerenciadorTelas.trocarTela("anamnese-1.fxml");
    }

    @FXML
    private void btnAnterior2Click() { // Volta para Anamnese 2
        capturarDadosAtuais();
        GerenciadorTelas.trocarTela("anamnese-2.fxml");
    }

    @FXML
    private void btnConcluirClick() {
        capturarDadosAtuais();
        try {
            if (anamneseService.cadastrarNovaAnamnese(anamneseData)) {
                limparEstadoEFechar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnCancelarClick() {
        limparEstadoEFechar();
    }

    @FXML
    private void btnSairClick() {
        limparEstadoEFechar();
    }

    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.trocarTela("tela-inicio-professor.fxml");
    }

    @FXML
    private void btnAlunosClick() {
        GerenciadorTelas.trocarTela("tela-inicio-professor.fxml");
    }

    private void capturarDadosAtuais() {
        // Tela 1
        if (convulsaoSim != null) {
            anamneseData.setTem_convulsao(convulsaoSim.isSelected() ? 1 : 0);
        }
        if (convenioSim != null) {
            anamneseData.setTem_convenio_medico(convenioSim.isSelected() ? 1 : 0);
        }
        if (convenio != null) {
            anamneseData.setNome_convenio(convenio.getText());
        }
        if (vacinacaoSim != null) {
            anamneseData.setVacinas_em_dia(vacinacaoSim.isSelected() ? 1 : 0);
        }
        if (doencaContagiosaSim != null) {
            anamneseData.setTeve_doenca_contagiosa(doencaContagiosaSim.isSelected() ? 1 : 0);
        }
        if (doencaContagiosa != null) {
            anamneseData.setQuais_doencas(doencaContagiosa.getText());
        }
        if (medicacaoSim != null) {
            anamneseData.setUsa_medicacao(medicacaoSim.isSelected() ? 1 : 0);
        }
        if (medicacoes != null) {
            anamneseData.setQuais_medicacoes(medicacoes.getText());
        }
        if (servicosFrequentados1 != null) {
            anamneseData.setQuais_servicos(servicosFrequentados1.getText());
        }
        if (inicioEscolarizacao != null) {
            anamneseData.setInicio_escolarizacao(inicioEscolarizacao.getText());
        }
        if (dificuldadesSim != null) {
            anamneseData.setApresenta_dificuldades(dificuldadesSim.isSelected() ? 1 : 0);
        }
        if (dificuldades != null) {
            anamneseData.setQuais_dificuldades(dificuldades.getText());
        }
        if (apoioPedagogicoSim != null) {
            anamneseData.setRecebe_apoio_pedagogico_casa(apoioPedagogicoSim.isSelected() ? 1 : 0);
        }
        if (apoioPedagogico != null) {
            anamneseData.setApoio_quem(apoioPedagogico.getText());
        }
        if (duracaoGestacao1 != null) {
            anamneseData.setDuracao_da_gestacao(duracaoGestacao1.getText());
        }
        if (preNatalSim != null) {
            anamneseData.setFez_prenatal(preNatalSim.isSelected() ? 1 : 0);
        }
        if (prematuridadeSim != null) {
            anamneseData.setHouve_prematuridade(prematuridadeSim.isSelected() ? 1 : 0);
        }
        if (prematuridade1 != null) {
            anamneseData.setCausa_prematuridade(prematuridade1.getText());
        }

        // Tela 2
        if (cidadeNascimento != null) {
            anamneseData.setCidade_nascimento(cidadeNascimento.getText());
        }
        if (maternidade != null) {
            anamneseData.setMaternidade(maternidade.getText());
        }
        if (tipoParto != null && tipoParto.getValue() != null) {
            anamneseData.setTipo_parto(tipoParto.getValue());
        }
        if (chorouSim != null) {
            anamneseData.setChorou_ao_nascer(chorouSim.isSelected() ? 1 : 0);
        }
        if (ficouRoxoSim != null) {
            anamneseData.setFicou_roxo(ficouRoxoSim.isSelected() ? 1 : 0);
        }
        if (incubadoraSim != null) {
            anamneseData.setUsou_incubadora(incubadoraSim.isSelected() ? 1 : 0);
        }
        if (amamentadoSim != null) {
            anamneseData.setFoi_amamentado(amamentadoSim.isSelected() ? 1 : 0);
        }
        if (sustentouCabecaSim != null) {
            anamneseData.setSustentou_a_cabeca(sustentouCabecaSim.isSelected() ? 1 : 0);
        }
        if (sustentouCabeca != null) {
            anamneseData.setQuantos_meses_sustentou_cabeca(sustentouCabeca.getText());
        }
        if (engatinhouSim != null) {
            anamneseData.setEngatinhou(engatinhouSim.isSelected() ? 1 : 0);
        }
        if (engatinhou != null) {
            anamneseData.setQuantos_meses_engatinhou(engatinhou.getText());
        }
        if (sentouSim != null) {
            anamneseData.setSentou(sentouSim.isSelected() ? 1 : 0);
        }
        if (sentou != null) {
            anamneseData.setQuantos_meses_sentou(sentou.getText());
        }
        if (andouSim != null) {
            anamneseData.setAndou(andouSim.isSelected() ? 1 : 0);
        }
        if (andou != null) {
            anamneseData.setQuantos_meses_andou(andou.getText());
        }
        if (terapiaSim != null) {
            anamneseData.setPrecisou_de_terapia(terapiaSim.isSelected() ? 1 : 0);
        }
        if (terapia != null) {
            anamneseData.setQual_motivo_terapia(terapia.getText());
        }
        if (falouSim != null) {
            anamneseData.setFalou(falouSim.isSelected() ? 1 : 0);
        }
        if (falou != null) {
            anamneseData.setQuantos_meses_falou(falou.getText());
        }

        // Tela 3
        if (balbucio != null && balbucio.getValue() != null) {
            anamneseData.setQuantos_meses_balbuciou(balbucio.getValue());
        }
        if (primeiraPalavra != null) {
            anamneseData.setQuando_primeiras_palavras(primeiraPalavra.getText());
        }
        if (primeiraFrase != null) {
            anamneseData.setQuando_primeiras_frases(primeiraFrase.getText());
        }
        if (tipoFala != null && tipoFala.getValue() != null) {
            anamneseData.setFala_natural_inibido(tipoFala.getValue());
        }
        if (disturbioSim != null) {
            anamneseData.setPossui_disturbio(disturbioSim.isSelected() ? 1 : 0);
        }
        if (disturbio != null) {
            anamneseData.setQual_disturbio(disturbio.getText());
        }
        if (servicos != null) {
            anamneseData.setQuais_servicos(servicos.getText());
        }
        if (dormeSozinho != null && dormeSozinho.getValue() != null) {
            anamneseData.setDorme_sozinho(convertChoiceBoxToInt(dormeSozinho.getValue()));
        }
        if (temQuarto != null && temQuarto.getValue() != null) {
            anamneseData.setTem_seu_quarto(convertChoiceBoxToInt(temQuarto.getValue()));
        }
        if (sono != null && sono.getValue() != null) {
            anamneseData.setSono_calmo_agitado(sono.getValue());
        }
        if (respeitaRegras != null && respeitaRegras.getValue() != null) {
            anamneseData.setRespeita_regras(convertChoiceBoxToInt(respeitaRegras.getValue()));
        }
        if (desmotivado != null && desmotivado.getValue() != null) {
            anamneseData.setE_desmotivado(convertChoiceBoxToInt(desmotivado.getValue()));
        }
        if (agressivo != null && agressivo.getValue() != null) {
            anamneseData.setE_agressivo(convertChoiceBoxToInt(agressivo.getValue()));
        }
        if (inquietacao != null && inquietacao.getValue() != null) {
            anamneseData.setApresenta_inquietacao(convertChoiceBoxToInt(inquietacao.getValue()));
        }
    }

    private void sincronizarCamposComEstado() {
        // Sincronizar campos de texto
        if (convenio != null && anamneseData.getNome_convenio() != null) {
            convenio.setText(anamneseData.getNome_convenio());
        }
        if (convenioSim != null) {
            convenioSim.setSelected(anamneseData.getTem_convenio_medico() == 1);
        }
        if (convulsaoSim != null) {
            convulsaoSim.setSelected(anamneseData.getTem_convulsao() == 1);
        }
    }

    private void configurarVisibilidadeCondicional() {
        // Campo de texto só aparece com o sim selecionado
        if (convenioSim != null && convenio != null) {
            convenio.visibleProperty().bind(convenioSim.selectedProperty());
            convenio.managedProperty().bind(convenioSim.selectedProperty());
        }
        if (doencaContagiosaSim != null && doencaContagiosa != null) {
            doencaContagiosa.visibleProperty().bind(doencaContagiosaSim.selectedProperty());
            doencaContagiosa.managedProperty().bind(doencaContagiosaSim.selectedProperty());
        }
        if (medicacaoSim != null && medicacoes != null) {
            medicacoes.visibleProperty().bind(medicacaoSim.selectedProperty());
            medicacoes.managedProperty().bind(medicacaoSim.selectedProperty());
        }
        if (dificuldadesSim != null && dificuldades != null) {
            dificuldades.visibleProperty().bind(dificuldadesSim.selectedProperty());
            dificuldades.managedProperty().bind(dificuldadesSim.selectedProperty());
        }
        if (apoioPedagogicoSim != null && apoioPedagogico != null) {
            apoioPedagogico.visibleProperty().bind(apoioPedagogicoSim.selectedProperty());
            apoioPedagogico.managedProperty().bind(apoioPedagogicoSim.selectedProperty());
        }
        if (prematuridadeSim != null && prematuridade1 != null) {
            prematuridade1.visibleProperty().bind(prematuridadeSim.selectedProperty());
            prematuridade1.managedProperty().bind(prematuridadeSim.selectedProperty());
        }
        if (sustentouCabecaSim != null && sustentouCabeca != null) {
            sustentouCabeca.visibleProperty().bind(sustentouCabecaSim.selectedProperty());
            sustentouCabeca.managedProperty().bind(sustentouCabecaSim.selectedProperty());
        }
        if (engatinhouSim != null && engatinhou != null) {
            engatinhou.visibleProperty().bind(engatinhouSim.selectedProperty());
            engatinhou.managedProperty().bind(engatinhouSim.selectedProperty());
        }
        if (sentouSim != null && sentou != null) {
            sentou.visibleProperty().bind(sentouSim.selectedProperty());
            sentou.managedProperty().bind(sentouSim.selectedProperty());
        }
        if (andouSim != null && andou != null) {
            andou.visibleProperty().bind(andouSim.selectedProperty());
            andou.managedProperty().bind(andouSim.selectedProperty());
        }
        if (terapiaSim != null && terapia != null) {
            terapia.visibleProperty().bind(terapiaSim.selectedProperty());
            terapia.managedProperty().bind(terapiaSim.selectedProperty());
        }
        if (falouSim != null && falou != null) {
            falou.visibleProperty().bind(falouSim.selectedProperty());
            falou.managedProperty().bind(falouSim.selectedProperty());
        }
        if (disturbioSim != null && disturbio != null) {
            disturbio.visibleProperty().bind(disturbioSim.selectedProperty());
            disturbio.managedProperty().bind(disturbioSim.selectedProperty());
        }
    }

    private void inicializarChoiceBoxes() {
        // Inicializar ChoiceBoxes com opções padrão
        if (tipoParto != null) {
            tipoParto.getItems().addAll("Normal", "Cesariana", "Fórceps", "Vácuo");
        }
        if (balbucio != null) {
            balbucio.getItems().addAll("1 mês", "2 meses", "3 meses", "4 meses", "5 meses", "6 meses", "7 meses", "8 meses", "9 meses", "Não sabe");
        }
        if (tipoFala != null) {
            tipoFala.getItems().addAll("Natural", "Inibido");
        }
        if (dormeSozinho != null) {
            dormeSozinho.getItems().addAll("Sim", "Não");
        }
        if (temQuarto != null) {
            temQuarto.getItems().addAll("Sim", "Não");
        }
        if (sono != null) {
            sono.getItems().addAll("Calmo", "Agitado");
        }
        if (respeitaRegras != null) {
            respeitaRegras.getItems().addAll("Sempre", "Às vezes", "Nunca");
        }
        if (desmotivado != null) {
            desmotivado.getItems().addAll("Sempre", "Às vezes", "Nunca");
        }
        if (agressivo != null) {
            agressivo.getItems().addAll("Sempre", "Às vezes", "Nunca");
        }
        if (inquietacao != null) {
            inquietacao.getItems().addAll("Sempre", "Às vezes", "Nunca");
        }
    }

    private int convertChoiceBoxToInt(String value) {
        return switch (value) {
            case "Sim" -> 1;
            case "Sempre" -> 1;
            case "Não" -> 0;
            case "Nunca" -> 0;
            case "Às vezes" -> 2;
            default -> 0;
        };
    }

    private void limparEstadoEFechar() {
        anamneseData = new Anamnese();
        educandoAtual = null;
        Stage stage = (Stage) anamnese.getScene().getWindow();
        stage.close();
    }

    private void configurarLabelsUsuario() {
        if (nameUser != null && cargoUser != null) {
            try {
                ProfessorRepository professorRepository = new ProfessorRepository();
                Professor professor = professorRepository.buscarPorUsuarioId(AuthService.getUsuarioLogado().getId());
                
                if (professor != null) {
                    nameUser.setText(professor.getNome());
                } else {
                    nameUser.setText(AuthService.getUsuarioLogado().getEmail());
                }
                cargoUser.setText(AuthService.getUsuarioLogado().getTipo());
            } catch (Exception e) {
                nameUser.setText(AuthService.getUsuarioLogado().getEmail());
                cargoUser.setText(AuthService.getUsuarioLogado().getTipo());
            }
        }
    }
}