package br.com.amparoedu.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import br.com.amparoedu.backend.builder.DIBuilder;
import br.com.amparoedu.backend.model.DI;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.DIService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class DIController extends DocumentoControllerBase<DI> implements Initializable {

    // Estado estático compartilhado entre telas
    private static final EstadoDocumento<DI> ESTADO = new EstadoDocumento<>();
    private static boolean salvando = false;

    // Serviço
    private final DIService diService = new DIService();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    //  CONTROLES FXML 
    // Tela 1: Comunicação
    @FXML private CheckBox falaSeuNome, dizDataNascimento, lePalavras, informaNumeroTelefone;
    @FXML private CheckBox emiteRespostas, transmiteRecado, informaEndereco, informaNomePais;
    @FXML private CheckBox compreendeOrdens, expoeIdeias, recontaHistorias, usaSistemaCA;
    @FXML private CheckBox relataFatosComCoerencia, pronunciaLetrasAlfabeto, verbalizaMusicas, interpretaHistorias;
    @FXML private CheckBox formulaPerguntas, utilizaGestosParaSeComunicar;

    // Tela 1: Afetiva
    @FXML private CheckBox demonstraCooperacao, timidoInseguro, fazBirra, solicitaOfereceAjuda;
    @FXML private CheckBox riComFrequencia, compartilhaOQueESeu, demonstraAmorGentilezaAtencao, choraComFrequencia;
    @FXML private CheckBox interageComColegas;

    // Tela 2: Sensorial
    @FXML private CheckBox captaDetalhesGravura, reconheceVozes, reconheceCancoes, percebeTexturas;
    @FXML private CheckBox percepcaoCores, discriminaSons, discriminaOdores, aceitaDiferentesTexturas;
    @FXML private CheckBox percepcaoFormas, identificaDirecaoSom, percebeDiscriminaSabores, acompanhaFocoLuminoso;

    // Tela 2: Motora
    @FXML private CheckBox movimentoPincaComTesoura, amassaPapel, caiComFacilidade, encaixaPecas;
    @FXML private CheckBox recorta, unePontos, consegueCorrer, empilha;
    @FXML private CheckBox agitacaoMotora, andaLinhaReta, sobeDesceEscadas, arremessaBola;

    // Tela 3: AVDs
    @FXML private CheckBox usaSanitarioSemAjuda, penteiaSeSo, consegueVestirDespirSe, lavaSecaAsMaos;
    @FXML private CheckBox banhoComModeracao, calcaSeSo, reconheceRoupas, abreFechaTorneira;
    @FXML private CheckBox escovaDentesSemAjuda, consegueDarNosLacos, abotoaDesabotoaRoupas, identificaPartesDoCorpo;

    // Tela 3: Níveis de Aprendizagem
    @FXML private CheckBox garatujas, silabicoAlfabetico, alfabetico, preSilabico, silabico;

    // Tela 3: Observações
    @FXML private TextArea observacoes;

    // Controles comuns
    @FXML private Label nomeUsuario, cargoUsuario, nameUser, indicadorDeTela, validationMsg;
    @FXML private Button btnConcluir;

    // Implementação dos Métodos Abstratos 

    @Override
    protected EstadoDocumento<DI> getEstado() {
        return ESTADO;
    }

    @Override
    protected int getTotalTelas() {
        return 3;
    }

    @Override
    protected String getPrefixoTela() {
        return "diagnostico";
    }

    @Override
    protected DI criarNovoDocumento() {
        return new DI();
    }

    @Override
    protected int detectarTelaAtual() {
        if (falaSeuNome != null) return 1;
        if (captaDetalhesGravura != null) return 2;
        if (usaSanitarioSemAjuda != null) return 3;
        return -1;
    }

    @Override
    protected void salvarDadosTelaAtual() {
        DIBuilder builder = obterOuCriarBuilder();
        EstadoDocumento<DI> estado = getEstado();

        if (estado.telaAtual == 1) {
            preencherDadosTela1(builder);
        } else if (estado.telaAtual == 2) {
            preencherDadosTela2(builder);
        } else if (estado.telaAtual == 3) {
            preencherDadosTela3(builder);
        }

        ESTADO.documentoCompartilhado = builder.buildPartial();
        documentoAtual = ESTADO.documentoCompartilhado;
    }

    @Override
    protected void carregarDadosNaTela() {
        if (documentoAtual == null) return;
        
        EstadoDocumento<DI> estado = getEstado();
        if (estado.telaAtual == 1) {
            carregarTela1();
        } else if (estado.telaAtual == 2) {
            carregarTela2();
        } else if (estado.telaAtual == 3) {
            carregarTela3();
        }
        
        atualizarUsuarioLogado();
    }

    @Override
    protected boolean validarTelaAtual() {
        return true;
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
        return "Diagnóstico Inicial";
    }

    @Override
    protected void desabilitarCampos() {
        desabilitarTodosOsCheckBoxes();
        if (observacoes != null) observacoes.setDisable(true);
        if (btnConcluir != null) btnConcluir.setDisable(true);
    }

    //  Ciclo de Vida

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarBase();
    }

    //  Métodos de Carregamento por Tela

    private void carregarTela1() {
        if (documentoAtual == null) return;

        // Comunicação
        setCheckBoxSelected(falaSeuNome, documentoAtual.getFala_nome());
        setCheckBoxSelected(dizDataNascimento, documentoAtual.getFala_nascimento());
        setCheckBoxSelected(lePalavras, documentoAtual.getLe_palavras());
        setCheckBoxSelected(informaNumeroTelefone, documentoAtual.getFala_telefone());
        setCheckBoxSelected(emiteRespostas, documentoAtual.getEmite_respostas());
        setCheckBoxSelected(transmiteRecado, documentoAtual.getTransmite_recados());
        setCheckBoxSelected(informaEndereco, documentoAtual.getFala_endereco());
        setCheckBoxSelected(informaNomePais, documentoAtual.getFala_nome_pais());
        setCheckBoxSelected(compreendeOrdens, documentoAtual.getCompreende_ordens());
        setCheckBoxSelected(expoeIdeias, documentoAtual.getExpoe_ideias());
        setCheckBoxSelected(recontaHistorias, documentoAtual.getReconta_historia());
        setCheckBoxSelected(usaSistemaCA, documentoAtual.getUsa_sistema_ca());
        setCheckBoxSelected(relataFatosComCoerencia, documentoAtual.getRelata_fatos());
        setCheckBoxSelected(pronunciaLetrasAlfabeto, documentoAtual.getPronuncia_letras());
        setCheckBoxSelected(verbalizaMusicas, documentoAtual.getVerbaliza_musicas());
        setCheckBoxSelected(interpretaHistorias, documentoAtual.getInterpreta_historias());
        setCheckBoxSelected(formulaPerguntas, documentoAtual.getFormula_perguntas());
        setCheckBoxSelected(utilizaGestosParaSeComunicar, documentoAtual.getUtiliza_gestos());

        // Afetiva
        setCheckBoxSelected(demonstraCooperacao, documentoAtual.getDemonstra_cooperacao());
        setCheckBoxSelected(timidoInseguro, documentoAtual.getTimido());
        setCheckBoxSelected(fazBirra, documentoAtual.getBirra());
        setCheckBoxSelected(solicitaOfereceAjuda, documentoAtual.getPede_ajuda());
        setCheckBoxSelected(riComFrequencia, documentoAtual.getRi());
        setCheckBoxSelected(compartilhaOQueESeu, documentoAtual.getCompartilha());
        setCheckBoxSelected(demonstraAmorGentilezaAtencao, documentoAtual.getDemonstra_amor());
        setCheckBoxSelected(choraComFrequencia, documentoAtual.getChora());
        setCheckBoxSelected(interageComColegas, documentoAtual.getInterage());
    }

    private void carregarTela2() {
        if (documentoAtual == null) return;

        // Sensorial
        setCheckBoxSelected(captaDetalhesGravura, documentoAtual.getDetalhes_gravura());
        setCheckBoxSelected(reconheceVozes, documentoAtual.getReconhece_vozes());
        setCheckBoxSelected(reconheceCancoes, documentoAtual.getReconhece_cancoes());
        setCheckBoxSelected(percebeTexturas, documentoAtual.getPercebe_texturas());
        setCheckBoxSelected(percepcaoCores, documentoAtual.getPercebe_cores());
        setCheckBoxSelected(discriminaSons, documentoAtual.getDiscrimina_sons());
        setCheckBoxSelected(discriminaOdores, documentoAtual.getDiscrimina_odores());
        setCheckBoxSelected(aceitaDiferentesTexturas, documentoAtual.getAceita_texturas());
        setCheckBoxSelected(percepcaoFormas, documentoAtual.getPercepcao_formas());
        setCheckBoxSelected(identificaDirecaoSom, documentoAtual.getIdentifica_direcao_sons());
        setCheckBoxSelected(percebeDiscriminaSabores, documentoAtual.getDiscrimina_sabores());
        setCheckBoxSelected(acompanhaFocoLuminoso, documentoAtual.getAcompanha_luz());

        // Motora
        setCheckBoxSelected(movimentoPincaComTesoura, documentoAtual.getMovimento_pinca());
        setCheckBoxSelected(amassaPapel, documentoAtual.getAmassa_papel());
        setCheckBoxSelected(caiComFacilidade, documentoAtual.getCai_facilmente());
        setCheckBoxSelected(encaixaPecas, documentoAtual.getEncaixa_pecas());
        setCheckBoxSelected(recorta, documentoAtual.getRecorta());
        setCheckBoxSelected(unePontos, documentoAtual.getUne_pontos());
        setCheckBoxSelected(consegueCorrer, documentoAtual.getCorre());
        setCheckBoxSelected(empilha, documentoAtual.getEmpilha());
        setCheckBoxSelected(agitacaoMotora, documentoAtual.getAgitacao_motora());
        setCheckBoxSelected(andaLinhaReta, documentoAtual.getAnda_reto());
        setCheckBoxSelected(sobeDesceEscadas, documentoAtual.getSobe_escada());
        setCheckBoxSelected(arremessaBola, documentoAtual.getArremessa_bola());
    }

    private void carregarTela3() {
        if (documentoAtual == null) return;

        // AVDs
        setCheckBoxSelected(usaSanitarioSemAjuda, documentoAtual.getUsa_sanitario());
        setCheckBoxSelected(penteiaSeSo, documentoAtual.getPenteia_cabelo());
        setCheckBoxSelected(consegueVestirDespirSe, documentoAtual.getVeste_se());
        setCheckBoxSelected(lavaSecaAsMaos, documentoAtual.getLava_maos());
        setCheckBoxSelected(banhoComModeracao, documentoAtual.getBanha_se());
        setCheckBoxSelected(calcaSeSo, documentoAtual.getCalca_se());
        setCheckBoxSelected(reconheceRoupas, documentoAtual.getReconhece_roupas());
        setCheckBoxSelected(abreFechaTorneira, documentoAtual.getAbre_torneira());
        setCheckBoxSelected(escovaDentesSemAjuda, documentoAtual.getEscova_dentes());
        setCheckBoxSelected(consegueDarNosLacos, documentoAtual.getDa_nos());
        setCheckBoxSelected(abotoaDesabotoaRoupas, documentoAtual.getAbotoa_roupas());
        setCheckBoxSelected(identificaPartesDoCorpo, documentoAtual.getIdentifica_partes_corpo());

        // Níveis de Aprendizagem
        setCheckBoxSelectedFromString(garatujas, documentoAtual.getGaratujas());
        setCheckBoxSelectedFromString(silabicoAlfabetico, documentoAtual.getSilabico_alfabetico());
        setCheckBoxSelectedFromString(alfabetico, documentoAtual.getAlfabetico());
        setCheckBoxSelectedFromString(preSilabico, documentoAtual.getPre_silabico());
        setCheckBoxSelectedFromString(silabico, documentoAtual.getSilabico());

        // Observações
        if (observacoes != null && documentoAtual.getObservacoes() != null) {
            observacoes.setText(documentoAtual.getObservacoes());
        }
    }

    //  Métodos de Preenchimento do Builder por Tela 

    private void preencherDadosTela1(DIBuilder builder) {
        if (builder == null) return;

        // Comunicação
        if (falaSeuNome != null) builder.comFalaNome(String.valueOf(converterBooleanParaInt(falaSeuNome.isSelected())));
        if (dizDataNascimento != null) builder.comFalaNascimento(String.valueOf(converterBooleanParaInt(dizDataNascimento.isSelected())));
        if (lePalavras != null) builder.comLePalavras(String.valueOf(converterBooleanParaInt(lePalavras.isSelected())));
        if (informaNumeroTelefone != null) builder.comFalaTelefone(String.valueOf(converterBooleanParaInt(informaNumeroTelefone.isSelected())));
        if (emiteRespostas != null) builder.comEmiteRespostas(String.valueOf(converterBooleanParaInt(emiteRespostas.isSelected())));
        if (transmiteRecado != null) builder.comTransmiteRecados(String.valueOf(converterBooleanParaInt(transmiteRecado.isSelected())));
        if (informaEndereco != null) builder.comFalaEndereco(String.valueOf(converterBooleanParaInt(informaEndereco.isSelected())));
        if (informaNomePais != null) builder.comFalaNomePais(String.valueOf(converterBooleanParaInt(informaNomePais.isSelected())));
        if (compreendeOrdens != null) builder.comComprehendeOrdens(String.valueOf(converterBooleanParaInt(compreendeOrdens.isSelected())));
        if (expoeIdeias != null) builder.comExpoeIdeias(String.valueOf(converterBooleanParaInt(expoeIdeias.isSelected())));
        if (recontaHistorias != null) builder.comRecontaHistoria(String.valueOf(converterBooleanParaInt(recontaHistorias.isSelected())));
        if (usaSistemaCA != null) builder.comUsaSistemaCA(String.valueOf(converterBooleanParaInt(usaSistemaCA.isSelected())));
        if (relataFatosComCoerencia != null) builder.comRelataFatos(String.valueOf(converterBooleanParaInt(relataFatosComCoerencia.isSelected())));
        if (pronunciaLetrasAlfabeto != null) builder.comPronunciaLetras(String.valueOf(converterBooleanParaInt(pronunciaLetrasAlfabeto.isSelected())));
        if (verbalizaMusicas != null) builder.comVerbalizaMusicas(String.valueOf(converterBooleanParaInt(verbalizaMusicas.isSelected())));
        if (interpretaHistorias != null) builder.comInterpretaHistorias(String.valueOf(converterBooleanParaInt(interpretaHistorias.isSelected())));
        if (formulaPerguntas != null) builder.comFormulaPerguntas(String.valueOf(converterBooleanParaInt(formulaPerguntas.isSelected())));
        if (utilizaGestosParaSeComunicar != null) builder.comUtilizaGestos(String.valueOf(converterBooleanParaInt(utilizaGestosParaSeComunicar.isSelected())));

        // Afetiva
        if (demonstraCooperacao != null) builder.comDemonstbraCooperacao(String.valueOf(converterBooleanParaInt(demonstraCooperacao.isSelected())));
        if (timidoInseguro != null) builder.comTimido(String.valueOf(converterBooleanParaInt(timidoInseguro.isSelected())));
        if (fazBirra != null) builder.comBirra(String.valueOf(converterBooleanParaInt(fazBirra.isSelected())));
        if (solicitaOfereceAjuda != null) builder.comPedeAjuda(String.valueOf(converterBooleanParaInt(solicitaOfereceAjuda.isSelected())));
        if (riComFrequencia != null) builder.comRi(String.valueOf(converterBooleanParaInt(riComFrequencia.isSelected())));
        if (compartilhaOQueESeu != null) builder.comCompartilha(String.valueOf(converterBooleanParaInt(compartilhaOQueESeu.isSelected())));
        if (demonstraAmorGentilezaAtencao != null) builder.comDemonstbraAmor(String.valueOf(converterBooleanParaInt(demonstraAmorGentilezaAtencao.isSelected())));
        if (choraComFrequencia != null) builder.comChora(String.valueOf(converterBooleanParaInt(choraComFrequencia.isSelected())));
        if (interageComColegas != null) builder.comInterage(String.valueOf(converterBooleanParaInt(interageComColegas.isSelected())));
    }

    private void preencherDadosTela2(DIBuilder builder) {
        if (builder == null) return;

        // Sensorial
        if (captaDetalhesGravura != null) builder.comDetalhesGravura(String.valueOf(converterBooleanParaInt(captaDetalhesGravura.isSelected())));
        if (reconheceVozes != null) builder.comReconheceVozes(String.valueOf(converterBooleanParaInt(reconheceVozes.isSelected())));
        if (reconheceCancoes != null) builder.comReconheceCancoes(String.valueOf(converterBooleanParaInt(reconheceCancoes.isSelected())));
        if (percebeTexturas != null) builder.comPercebeTexturas(String.valueOf(converterBooleanParaInt(percebeTexturas.isSelected())));
        if (percepcaoCores != null) builder.comPercebeCores(String.valueOf(converterBooleanParaInt(percepcaoCores.isSelected())));
        if (discriminaSons != null) builder.comDiscriminaSons(String.valueOf(converterBooleanParaInt(discriminaSons.isSelected())));
        if (discriminaOdores != null) builder.comDiscriminaOdores(String.valueOf(converterBooleanParaInt(discriminaOdores.isSelected())));
        if (aceitaDiferentesTexturas != null) builder.comAceitaTexturas(String.valueOf(converterBooleanParaInt(aceitaDiferentesTexturas.isSelected())));
        if (percepcaoFormas != null) builder.comPercepcaoFormas(String.valueOf(converterBooleanParaInt(percepcaoFormas.isSelected())));
        if (identificaDirecaoSom != null) builder.comIdentificaDirecaoSons(String.valueOf(converterBooleanParaInt(identificaDirecaoSom.isSelected())));
        if (percebeDiscriminaSabores != null) builder.comDiscriminaSabores(String.valueOf(converterBooleanParaInt(percebeDiscriminaSabores.isSelected())));
        if (acompanhaFocoLuminoso != null) builder.comAcompanhaLuz(String.valueOf(converterBooleanParaInt(acompanhaFocoLuminoso.isSelected())));

        // Motora
        if (movimentoPincaComTesoura != null) builder.comMovimentoPinca(String.valueOf(converterBooleanParaInt(movimentoPincaComTesoura.isSelected())));
        if (amassaPapel != null) builder.comAmassaPapel(String.valueOf(converterBooleanParaInt(amassaPapel.isSelected())));
        if (caiComFacilidade != null) builder.comCaiFacilmente(String.valueOf(converterBooleanParaInt(caiComFacilidade.isSelected())));
        if (encaixaPecas != null) builder.comEncaixaPecas(String.valueOf(converterBooleanParaInt(encaixaPecas.isSelected())));
        if (recorta != null) builder.comRecorta(String.valueOf(converterBooleanParaInt(recorta.isSelected())));
        if (unePontos != null) builder.comUnePontos(String.valueOf(converterBooleanParaInt(unePontos.isSelected())));
        if (consegueCorrer != null) builder.comCorre(String.valueOf(converterBooleanParaInt(consegueCorrer.isSelected())));
        if (empilha != null) builder.comEmpilha(String.valueOf(converterBooleanParaInt(empilha.isSelected())));
        if (agitacaoMotora != null) builder.comAgitacaoMotora(String.valueOf(converterBooleanParaInt(agitacaoMotora.isSelected())));
        if (andaLinhaReta != null) builder.comAndaReto(String.valueOf(converterBooleanParaInt(andaLinhaReta.isSelected())));
        if (sobeDesceEscadas != null) builder.comSobeEscada(String.valueOf(converterBooleanParaInt(sobeDesceEscadas.isSelected())));
        if (arremessaBola != null) builder.comArremessaBola(String.valueOf(converterBooleanParaInt(arremessaBola.isSelected())));
    }

    private void preencherDadosTela3(DIBuilder builder) {
        if (builder == null) return;

        // AVDs
        if (usaSanitarioSemAjuda != null) builder.comUsaSanitario(String.valueOf(converterBooleanParaInt(usaSanitarioSemAjuda.isSelected())));
        if (penteiaSeSo != null) builder.comPenteiaCabelo(String.valueOf(converterBooleanParaInt(penteiaSeSo.isSelected())));
        if (consegueVestirDespirSe != null) builder.comVesteSe(String.valueOf(converterBooleanParaInt(consegueVestirDespirSe.isSelected())));
        if (lavaSecaAsMaos != null) builder.comLavamaos(String.valueOf(converterBooleanParaInt(lavaSecaAsMaos.isSelected())));
        if (banhoComModeracao != null) builder.comBanhaSe(String.valueOf(converterBooleanParaInt(banhoComModeracao.isSelected())));
        if (calcaSeSo != null) builder.comCalcaSe(String.valueOf(converterBooleanParaInt(calcaSeSo.isSelected())));
        if (reconheceRoupas != null) builder.comReconheceRoupas(String.valueOf(converterBooleanParaInt(reconheceRoupas.isSelected())));
        if (abreFechaTorneira != null) builder.comAbreFechaTorneira(String.valueOf(converterBooleanParaInt(abreFechaTorneira.isSelected())));
        if (escovaDentesSemAjuda != null) builder.comEscovaDentes(String.valueOf(converterBooleanParaInt(escovaDentesSemAjuda.isSelected())));
        if (consegueDarNosLacos != null) builder.comDarNosLacos(String.valueOf(converterBooleanParaInt(consegueDarNosLacos.isSelected())));
        if (abotoaDesabotoaRoupas != null) builder.comAbotoaDesabotoa(String.valueOf(converterBooleanParaInt(abotoaDesabotoaRoupas.isSelected())));
        if (identificaPartesDoCorpo != null) builder.comIdentificaPartesCorpo(String.valueOf(converterBooleanParaInt(identificaPartesDoCorpo.isSelected())));

        // Níveis de Aprendizagem
        if (garatujas != null) builder.comGaratujas(garatujas.isSelected() ? "1" : "0");
        if (silabicoAlfabetico != null) builder.comSilabicoAlfabetico(silabicoAlfabetico.isSelected() ? "1" : "0");
        if (alfabetico != null) builder.comAlfabetico(alfabetico.isSelected() ? "1" : "0");
        if (preSilabico != null) builder.comPreSilabico(preSilabico.isSelected() ? "1" : "0");
        if (silabico != null) builder.comSilabico(silabico.isSelected() ? "1" : "0");

        // Observações
        if (observacoes != null) builder.comObservacoes(observacoes.getText().trim());
    }

    //  Métodos Auxiliares de Conversão e UI 

    private void setCheckBoxSelected(CheckBox checkbox, Object valor) {
        if (checkbox != null) {
            checkbox.setSelected(converterIntParaBoolean(valor));
        }
    }

    private void setCheckBoxSelectedFromString(CheckBox checkbox, String valor) {
        if (checkbox != null) {
            checkbox.setSelected("1".equals(valor));
        }
    }

    private boolean converterIntParaBoolean(Object valor) {
        if (valor == null) return false;
        if (valor instanceof Integer) {
            return ((Integer) valor) == 1;
        } else if (valor instanceof String) {
            try {
                return Integer.parseInt((String) valor) == 1;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private int converterBooleanParaInt(boolean valor) {
        return valor ? 1 : 0;
    }

    private void desabilitarTodosOsCheckBoxes() {
        desabilitarCheckbox(falaSeuNome, dizDataNascimento, lePalavras, informaNumeroTelefone);
        desabilitarCheckbox(emiteRespostas, transmiteRecado, informaEndereco, informaNomePais);
        desabilitarCheckbox(compreendeOrdens, expoeIdeias, recontaHistorias, usaSistemaCA);
        desabilitarCheckbox(relataFatosComCoerencia, pronunciaLetrasAlfabeto, verbalizaMusicas, interpretaHistorias);
        desabilitarCheckbox(formulaPerguntas, utilizaGestosParaSeComunicar);

        desabilitarCheckbox(demonstraCooperacao, timidoInseguro, fazBirra, solicitaOfereceAjuda);
        desabilitarCheckbox(riComFrequencia, compartilhaOQueESeu, demonstraAmorGentilezaAtencao, choraComFrequencia);
        desabilitarCheckbox(interageComColegas);

        desabilitarCheckbox(captaDetalhesGravura, reconheceVozes, reconheceCancoes, percebeTexturas);
        desabilitarCheckbox(percepcaoCores, discriminaSons, discriminaOdores, aceitaDiferentesTexturas);
        desabilitarCheckbox(percepcaoFormas, identificaDirecaoSom, percebeDiscriminaSabores, acompanhaFocoLuminoso);

        desabilitarCheckbox(movimentoPincaComTesoura, amassaPapel, caiComFacilidade, encaixaPecas);
        desabilitarCheckbox(recorta, unePontos, consegueCorrer, empilha);
        desabilitarCheckbox(agitacaoMotora, andaLinhaReta, sobeDesceEscadas, arremessaBola);

        desabilitarCheckbox(usaSanitarioSemAjuda, penteiaSeSo, consegueVestirDespirSe, lavaSecaAsMaos);
        desabilitarCheckbox(banhoComModeracao, calcaSeSo, reconheceRoupas, abreFechaTorneira);
        desabilitarCheckbox(escovaDentesSemAjuda, consegueDarNosLacos, abotoaDesabotoaRoupas, identificaPartesDoCorpo);

        desabilitarCheckbox(garatujas, silabicoAlfabetico, alfabetico, preSilabico, silabico);
    }

    private void desabilitarCheckbox(CheckBox... checkboxes) {
        for (CheckBox cb : checkboxes) {
            if (cb != null) cb.setDisable(true);
        }
    }

    protected void atualizarUsuarioLogado() {
        try {
            Usuario usuario = AuthService.getUsuarioLogado();
            if (usuario != null) {
                if (nomeUsuario != null) nomeUsuario.setText(usuario.getEmail());
                if (nameUser != null) nameUser.setText(usuario.getEmail());
                if (cargoUsuario != null) cargoUsuario.setText(usuario.getTipo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Métodos privados de Builder e Conclusão 

    private DIBuilder obterOuCriarBuilder() {
        EstadoDocumento<DI> estado = getEstado();
        if (estado.builder instanceof DIBuilder) {
            return (DIBuilder) estado.builder;
        }

        DI base = documentoAtual != null ? documentoAtual : new DI();
        DIBuilder builder = new DIBuilder(base);
        String educandoId = getEducandoIdDoDocumento();
        if (educandoId != null) {
            builder.comEducandoId(educandoId);
        }
        estado.builder = builder;
        return builder;
    }

    @FXML
    protected void btnConcluirClick() {
        EstadoDocumento<DI> estado = getEstado();
        if (estado.modoAtual == ModoDocumento.VISUALIZACAO) {
            exibirErro("Modo visualização: não é possível salvar.");
            return;
        }

        if (salvando) {
            return;
        }

        salvarDadosTelaAtual();

        if (!exibirConfirmacao("Concluir Diagnóstico Inicial", 
                               "Deseja salvar o Diagnóstico Inicial agora?", 
                               "Todos os dados serão salvos no sistema.")) {
            return;
        }

        salvando = true;
        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }

        String educandoId = documentoAtual != null ? documentoAtual.getEducando_id() : null;
        DIBuilder builder = obterOuCriarBuilder();
        if (educandoId != null) {
            builder.comEducandoId(educandoId);
        }

        try {
            Usuario usuarioLogado = AuthService.getUsuarioLogado();
            if (usuarioLogado == null) {
                exibirErro("Usuário não está logado. Faça login novamente.");
                salvando = false;
                if (btnConcluir != null) btnConcluir.setDisable(false);
                return;
            }

            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirErro("Apenas professores podem criar Diagnósticos Iniciais.");
                salvando = false;
                if (btnConcluir != null) btnConcluir.setDisable(false);
                return;
            }

            builder.comProfessorId(AuthService.getIdProfessorLogado());
            if (estado.modoAtual == ModoDocumento.NOVA) {
                builder.comDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            } else if (documentoAtual != null) {
                try {
                    // Tenta recuperar a data de criação existente se disponível
                    java.lang.reflect.Method getDataMethod = documentoAtual.getClass().getMethod("getDataCriacao");
                    String dataExistente = (String) getDataMethod.invoke(documentoAtual);
                    if (dataExistente == null || dataExistente.isBlank()) {
                        builder.comDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                    }
                } catch (Exception e) {
                    // Se não conseguir recuperar a data, usa a atual
                    builder.comDataCriacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                }
            }

            DI diFinal = builder.build();
            ESTADO.documentoCompartilhado = diFinal;
            documentoAtual = diFinal;

            boolean edicao = estado.modoAtual == ModoDocumento.EDICAO;
            boolean sucesso = edicao ? diService.atualizarDI(diFinal) : diService.cadastrarNovoDI(diFinal);

            if (sucesso) {
                exibirSucesso(edicao ? "Diagnóstico Inicial atualizado com sucesso!" : "Diagnóstico Inicial criado com sucesso!");
                limparEstado();
                salvando = false;
                voltarComPopup(educandoId);
            } else {
                exibirErro(edicao ? "Erro ao atualizar Diagnóstico Inicial." : "Erro ao cadastrar Diagnóstico Inicial.");
                salvando = false;
                if (btnConcluir != null) btnConcluir.setDisable(false);
            }
        } catch (Exception e) {
            exibirErro("Erro ao salvar Diagnóstico Inicial: " + e.getMessage());
            e.printStackTrace();
            salvando = false;
            if (btnConcluir != null) btnConcluir.setDisable(false);
        }
    }

    @FXML
    @Override
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

    @FXML
    private void btnCancelClick() {
        btnCancelarClick();
    }

    //  Métodos de navegação 

    protected void voltarComPopup(String educandoId) {
        EstadoDocumento<DI> estado = getEstado();
        if (estado.turmaIdOrigem != null) {
            try {
                TurmaRepository turmaRepo = new TurmaRepository();
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

    //  Métodos de fluxo estáticos (compatíveis com DIFluxo) 

    public static void iniciarNovoDI() {
        salvando = false;
        
        String educandoIdPreservado = null;
        if (ESTADO.documentoCompartilhado != null) {
            educandoIdPreservado = ESTADO.documentoCompartilhado.getEducando_id();
        }

        iniciarNovo(ESTADO, new DI());
        ESTADO.builder = null;

        if (educandoIdPreservado != null) {
            ((DI) ESTADO.documentoCompartilhado).setEducando_id(educandoIdPreservado);
        }
    }

    public static void editarDIExistente(DI existente) {
        ESTADO.modoAtual = ModoDocumento.EDICAO;
        ESTADO.telaAtual = 1;
        ESTADO.documentoCompartilhado = (existente != null) ? existente : new DI();
        ESTADO.navegandoEntreTelas = false;
    }

    public static void visualizarDI(DI existente) {
        ESTADO.modoAtual = ModoDocumento.VISUALIZACAO;
        ESTADO.telaAtual = 1;
        ESTADO.documentoCompartilhado = existente;
        ESTADO.navegandoEntreTelas = false;
    }

    public static void setEducandoIdParaDI(String educandoId) {
        if (ESTADO.documentoCompartilhado == null) {
            ESTADO.documentoCompartilhado = new DI();
        }
        ESTADO.documentoCompartilhado.setEducando_id(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        ESTADO.turmaIdOrigem = turmaId;
    }

    //  Métodos de navegação da interface

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }

    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    @FXML
    private void btnAlunosClick() {
        voltarParaTurma();
    }

    private void voltarParaTurma() {
        EstadoDocumento<DI> estado = getEstado();
        if (estado.turmaIdOrigem != null) {
            try {
                TurmaRepository turmaRepo = new TurmaRepository();
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

}
