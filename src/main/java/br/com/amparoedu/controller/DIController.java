package br.com.amparoedu.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DIController implements Initializable {

    // Modo de uso
    public enum ModoDI {
        NOVA, EDICAO, VISUALIZACAO
    }

    // Estado e serviços
    private final DIService diService = new DIService();
    private DI diAtual = new DI();
    private static int telaAtual = 1; // 1, 2 ou 3
    private static DI diCompartilhada;
    private static String turmaIdOrigem;
    private static ModoDI modoAtual = ModoDI.NOVA;
    private static boolean navegandoEntreTelas;

    // Controles DI - Tela 1: Comunicação e Afetiva
    @FXML
    private CheckBox falaSeuNome;
    @FXML
    private CheckBox dizDataNascimento;
    @FXML
    private CheckBox lePalavras;
    @FXML
    private CheckBox informaNumeroTelefone;
    @FXML
    private CheckBox emiteRespostas;
    @FXML
    private CheckBox transmiteRecado;
    @FXML
    private CheckBox informaEndereco;
    @FXML
    private CheckBox informaNomePais;
    @FXML
    private CheckBox compreendeOrdens;
    @FXML
    private CheckBox expoeIdeias;
    @FXML
    private CheckBox recontaHistorias;
    @FXML
    private CheckBox usaSistemaCA;
    @FXML
    private CheckBox relataFatosComCoerencia;
    @FXML
    private CheckBox pronunciaLetrasAlfabeto;
    @FXML
    private CheckBox verbalizaMusicas;
    @FXML
    private CheckBox interpretaHistorias;
    @FXML
    private CheckBox formulaPerguntas;
    @FXML
    private CheckBox utilizaGestosParaSeComunicar;
    @FXML
    private CheckBox demonstraCooperacao;
    @FXML
    private CheckBox timidoInseguro;
    @FXML
    private CheckBox fazBirra;
    @FXML
    private CheckBox solicitaOfereceAjuda;
    @FXML
    private CheckBox riComFrequencia;
    @FXML
    private CheckBox compartilhaOQueESeu;
    @FXML
    private CheckBox demonstraAmorGentilezaAtencao;
    @FXML
    private CheckBox choraComFrequencia;
    @FXML
    private CheckBox interageComColegas;

    // Controles DI - Tela 2: Sensorial e Motora
    @FXML
    private CheckBox captaDetalhesGravura;
    @FXML
    private CheckBox reconheceVozes;
    @FXML
    private CheckBox reconheceCancoes;
    @FXML
    private CheckBox percebeTexturas;
    @FXML
    private CheckBox percepcaoCores;
    @FXML
    private CheckBox discriminaSons;
    @FXML
    private CheckBox discriminaOdores;
    @FXML
    private CheckBox aceitaDiferentesTexturas;
    @FXML
    private CheckBox percepcaoFormas;
    @FXML
    private CheckBox identificaDirecaoSom;
    @FXML
    private CheckBox percebeDiscriminaSabores;
    @FXML
    private CheckBox acompanhaFocoLuminoso;
    @FXML
    private CheckBox movimentoPincaComTesoura;
    @FXML
    private CheckBox amassaPapel;
    @FXML
    private CheckBox caiComFacilidade;
    @FXML
    private CheckBox encaixaPecas;
    @FXML
    private CheckBox recorta;
    @FXML
    private CheckBox unePontos;
    @FXML
    private CheckBox consegueCorrer;
    @FXML
    private CheckBox empilha;
    @FXML
    private CheckBox agitacaoMotora;
    @FXML
    private CheckBox andaLinhaReta;
    @FXML
    private CheckBox sobeDesceEscadas;
    @FXML
    private CheckBox arremessaBola;

    // Controles DI - Tela 3: AVDs, Níveis de Aprendizagem e Observações
    @FXML
    private CheckBox usaSanitarioSemAjuda;
    @FXML
    private CheckBox penteiaSeSo;
    @FXML
    private CheckBox consegueVestirDespirSe;
    @FXML
    private CheckBox lavaSecaAsMaos;
    @FXML
    private CheckBox banhoComModeracao;
    @FXML
    private CheckBox calcaSeSo;
    @FXML
    private CheckBox reconheceRoupas;
    @FXML
    private CheckBox abreFechaTorneira;
    @FXML
    private CheckBox escovaDentesSemAjuda;
    @FXML
    private CheckBox consegueDarNosLacos;
    @FXML
    private CheckBox abotoaDesabotoaRoupas;
    @FXML
    private CheckBox identificaPartesDoCorpo;
    @FXML
    private CheckBox garatujas;
    @FXML
    private CheckBox silabicoAlfabetico;
    @FXML
    private CheckBox alfabetico;
    @FXML
    private CheckBox preSilabico;
    @FXML
    private CheckBox silabico;
    @FXML
    private TextArea observacoes;

    // Controles comuns
    @FXML
    private Label nomeUsuario;
    @FXML
    private Label cargoUsuario;
    @FXML
    private Label nameUser;
    @FXML
    private Label indicadorDeTela;
    @FXML
    private Label validationMsg;
    @FXML
    private Button btnConcluir;

    // Ciclo de vida
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean vindoDeNavegacao = navegandoEntreTelas;
        navegandoEntreTelas = false;

        // Detecta qual tela está carregada pelos controles presentes
        if (falaSeuNome != null) {
            telaAtual = 1;
        } else if (captaDetalhesGravura != null) {
            telaAtual = 2;
        } else if (usaSanitarioSemAjuda != null) {
            telaAtual = 3;
        }

        // Se não veio de navegação, prepara estado conforme o modo selecionado
        if (!vindoDeNavegacao) {
            if (modoAtual == ModoDI.NOVA) {
                telaAtual = 1;
                if (diCompartilhada == null) {
                    diCompartilhada = new DI();
                }
            } else {
                if (diCompartilhada == null) {
                    diCompartilhada = new DI();
                }
                telaAtual = 1;
            }
        } else if (telaAtual == 1 && diCompartilhada == null) {
            diCompartilhada = new DI();
        }

        // Usa o DI compartilhado
        if (diCompartilhada != null) {
            diAtual = diCompartilhada;
        }

        // Inicializa componentes
        carregarDadosNaTela();
        desabilitarEdicaoSeVisualizacao();
        atualizarUsuarioLogado();
        atualizarIndicadorDeTela();
    }

    // Atualiza o indicador de tela
    private void atualizarIndicadorDeTela() {
        if (indicadorDeTela != null) {
            indicadorDeTela.setText("Diagnóstico Inicial - " + telaAtual);
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
                if (nameUser != null) {
                    nameUser.setText(usuario.getEmail());
                }
                if (cargoUsuario != null) {
                    cargoUsuario.setText(usuario.getTipo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Desabilita edição se estiver em modo visualização
    private void desabilitarEdicaoSeVisualizacao() {
        if (modoAtual != ModoDI.VISUALIZACAO)
            return;

        // Desabilita todos os checkboxes
        desabilitarCheckbox(falaSeuNome);
        desabilitarCheckbox(dizDataNascimento);
        desabilitarCheckbox(lePalavras);
        desabilitarCheckbox(informaNumeroTelefone);
        desabilitarCheckbox(emiteRespostas);
        desabilitarCheckbox(transmiteRecado);
        desabilitarCheckbox(informaEndereco);
        desabilitarCheckbox(informaNomePais);
        desabilitarCheckbox(compreendeOrdens);
        desabilitarCheckbox(expoeIdeias);
        desabilitarCheckbox(recontaHistorias);
        desabilitarCheckbox(usaSistemaCA);
        desabilitarCheckbox(relataFatosComCoerencia);
        desabilitarCheckbox(pronunciaLetrasAlfabeto);
        desabilitarCheckbox(verbalizaMusicas);
        desabilitarCheckbox(interpretaHistorias);
        desabilitarCheckbox(formulaPerguntas);
        desabilitarCheckbox(utilizaGestosParaSeComunicar);
        desabilitarCheckbox(demonstraCooperacao);
        desabilitarCheckbox(timidoInseguro);
        desabilitarCheckbox(fazBirra);
        desabilitarCheckbox(solicitaOfereceAjuda);
        desabilitarCheckbox(riComFrequencia);
        desabilitarCheckbox(compartilhaOQueESeu);
        desabilitarCheckbox(demonstraAmorGentilezaAtencao);
        desabilitarCheckbox(choraComFrequencia);
        desabilitarCheckbox(interageComColegas);
        desabilitarCheckbox(captaDetalhesGravura);
        desabilitarCheckbox(reconheceVozes);
        desabilitarCheckbox(reconheceCancoes);
        desabilitarCheckbox(percebeTexturas);
        desabilitarCheckbox(percepcaoCores);
        desabilitarCheckbox(discriminaSons);
        desabilitarCheckbox(discriminaOdores);
        desabilitarCheckbox(aceitaDiferentesTexturas);
        desabilitarCheckbox(percepcaoFormas);
        desabilitarCheckbox(identificaDirecaoSom);
        desabilitarCheckbox(percebeDiscriminaSabores);
        desabilitarCheckbox(acompanhaFocoLuminoso);
        desabilitarCheckbox(movimentoPincaComTesoura);
        desabilitarCheckbox(amassaPapel);
        desabilitarCheckbox(caiComFacilidade);
        desabilitarCheckbox(encaixaPecas);
        desabilitarCheckbox(recorta);
        desabilitarCheckbox(unePontos);
        desabilitarCheckbox(consegueCorrer);
        desabilitarCheckbox(empilha);
        desabilitarCheckbox(agitacaoMotora);
        desabilitarCheckbox(andaLinhaReta);
        desabilitarCheckbox(sobeDesceEscadas);
        desabilitarCheckbox(arremessaBola);
        desabilitarCheckbox(usaSanitarioSemAjuda);
        desabilitarCheckbox(penteiaSeSo);
        desabilitarCheckbox(consegueVestirDespirSe);
        desabilitarCheckbox(lavaSecaAsMaos);
        desabilitarCheckbox(banhoComModeracao);
        desabilitarCheckbox(calcaSeSo);
        desabilitarCheckbox(reconheceRoupas);
        desabilitarCheckbox(abreFechaTorneira);
        desabilitarCheckbox(escovaDentesSemAjuda);
        desabilitarCheckbox(consegueDarNosLacos);
        desabilitarCheckbox(abotoaDesabotoaRoupas);
        desabilitarCheckbox(identificaPartesDoCorpo);
        desabilitarCheckbox(garatujas);
        desabilitarCheckbox(silabicoAlfabetico);
        desabilitarCheckbox(alfabetico);
        desabilitarCheckbox(preSilabico);
        desabilitarCheckbox(silabico);

        // Desabilita textarea
        if (observacoes != null) {
            observacoes.setDisable(true);
        }

        // Desabilita botão de conclusão
        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }
    }

    // Método auxiliar para desabilitar checkbox
    private void desabilitarCheckbox(CheckBox checkbox) {
        if (checkbox != null) {
            checkbox.setDisable(true);
        }
    }

    // Carrega dados do DI atual na tela
    private void carregarDadosNaTela() {
        if (diAtual == null)
            return;

        // Tela 1: Comunicação
        if (falaSeuNome != null) {
            falaSeuNome.setSelected(converterIntParaBoolean(diAtual.getFala_nome()));
        }
        if (dizDataNascimento != null) {
            dizDataNascimento.setSelected(converterIntParaBoolean(diAtual.getFala_nascimento()));
        }
        if (lePalavras != null) {
            lePalavras.setSelected(converterIntParaBoolean(diAtual.getLe_palavras()));
        }
        if (informaNumeroTelefone != null) {
            informaNumeroTelefone.setSelected(converterIntParaBoolean(diAtual.getFala_telefone()));
        }
        if (emiteRespostas != null) {
            emiteRespostas.setSelected(converterIntParaBoolean(diAtual.getEmite_respostas()));
        }
        if (transmiteRecado != null) {
            transmiteRecado.setSelected(converterIntParaBoolean(diAtual.getTransmite_recados()));
        }
        if (informaEndereco != null) {
            informaEndereco.setSelected(converterIntParaBoolean(diAtual.getFala_endereco()));
        }
        if (informaNomePais != null) {
            informaNomePais.setSelected(converterIntParaBoolean(diAtual.getFala_nome_pais()));
        }
        if (compreendeOrdens != null) {
            compreendeOrdens.setSelected(converterIntParaBoolean(diAtual.getCompreende_ordens()));
        }
        if (expoeIdeias != null) {
            expoeIdeias.setSelected(converterIntParaBoolean(diAtual.getExpoe_ideias()));
        }
        if (recontaHistorias != null) {
            recontaHistorias.setSelected(converterIntParaBoolean(diAtual.getReconta_historia()));
        }
        if (usaSistemaCA != null) {
            usaSistemaCA.setSelected(converterIntParaBoolean(diAtual.getUsa_sistema_ca()));
        }
        if (relataFatosComCoerencia != null) {
            relataFatosComCoerencia.setSelected(converterIntParaBoolean(diAtual.getRelata_fatos()));
        }
        if (pronunciaLetrasAlfabeto != null) {
            pronunciaLetrasAlfabeto.setSelected(converterIntParaBoolean(diAtual.getPronuncia_letras()));
        }
        if (verbalizaMusicas != null) {
            verbalizaMusicas.setSelected(converterIntParaBoolean(diAtual.getVerbaliza_musicas()));
        }
        if (interpretaHistorias != null) {
            interpretaHistorias.setSelected(converterIntParaBoolean(diAtual.getInterpreta_historias()));
        }
        if (formulaPerguntas != null) {
            formulaPerguntas.setSelected(converterIntParaBoolean(diAtual.getFormula_perguntas()));
        }
        if (utilizaGestosParaSeComunicar != null) {
            utilizaGestosParaSeComunicar.setSelected(converterIntParaBoolean(diAtual.getUtiliza_gestos()));
        }

        // Tela 1: Afetiva
        if (demonstraCooperacao != null) {
            demonstraCooperacao.setSelected(converterIntParaBoolean(diAtual.getDemonstra_cooperacao()));
        }
        if (timidoInseguro != null) {
            timidoInseguro.setSelected(converterIntParaBoolean(diAtual.getTimido()));
        }
        if (fazBirra != null) {
            fazBirra.setSelected(converterIntParaBoolean(diAtual.getBirra()));
        }
        if (solicitaOfereceAjuda != null) {
            solicitaOfereceAjuda.setSelected(converterIntParaBoolean(diAtual.getPede_ajuda()));
        }
        if (riComFrequencia != null) {
            riComFrequencia.setSelected(converterIntParaBoolean(diAtual.getRi()));
        }
        if (compartilhaOQueESeu != null) {
            compartilhaOQueESeu.setSelected(converterIntParaBoolean(diAtual.getCompartilha()));
        }
        if (demonstraAmorGentilezaAtencao != null) {
            demonstraAmorGentilezaAtencao.setSelected(converterIntParaBoolean(diAtual.getDemonstra_amor()));
        }
        if (choraComFrequencia != null) {
            choraComFrequencia.setSelected(converterIntParaBoolean(diAtual.getChora()));
        }
        if (interageComColegas != null) {
            interageComColegas.setSelected(converterIntParaBoolean(diAtual.getInterage()));
        }

        // Tela 2: Sensorial
        if (captaDetalhesGravura != null) {
            captaDetalhesGravura.setSelected(converterIntParaBoolean(diAtual.getDetalhes_gravura()));
        }
        if (reconheceVozes != null) {
            reconheceVozes.setSelected(converterIntParaBoolean(diAtual.getReconhece_vozes()));
        }
        if (reconheceCancoes != null) {
            reconheceCancoes.setSelected(converterIntParaBoolean(diAtual.getReconhece_cancoes()));
        }
        if (percebeTexturas != null) {
            percebeTexturas.setSelected(converterIntParaBoolean(diAtual.getPercebe_texturas()));
        }
        if (percepcaoCores != null) {
            percepcaoCores.setSelected(converterIntParaBoolean(diAtual.getPercebe_cores()));
        }
        if (discriminaSons != null) {
            discriminaSons.setSelected(converterIntParaBoolean(diAtual.getDiscrimina_sons()));
        }
        if (discriminaOdores != null) {
            discriminaOdores.setSelected(converterIntParaBoolean(diAtual.getDiscrimina_odores()));
        }
        if (aceitaDiferentesTexturas != null) {
            aceitaDiferentesTexturas.setSelected(converterIntParaBoolean(diAtual.getAceita_texturas()));
        }
        if (percepcaoFormas != null) {
            percepcaoFormas.setSelected(converterIntParaBoolean(diAtual.getPercepcao_formas()));
        }
        if (identificaDirecaoSom != null) {
            identificaDirecaoSom.setSelected(converterIntParaBoolean(diAtual.getIdentifica_direcao_sons()));
        }
        if (percebeDiscriminaSabores != null) {
            percebeDiscriminaSabores.setSelected(converterIntParaBoolean(diAtual.getDiscrimina_sabores()));
        }
        if (acompanhaFocoLuminoso != null) {
            acompanhaFocoLuminoso.setSelected(converterIntParaBoolean(diAtual.getAcompanha_luz()));
        }

        // Tela 2: Motora
        if (movimentoPincaComTesoura != null) {
            movimentoPincaComTesoura.setSelected(converterIntParaBoolean(diAtual.getMovimento_pinca()));
        }
        if (amassaPapel != null) {
            amassaPapel.setSelected(converterIntParaBoolean(diAtual.getAmassa_papel()));
        }
        if (caiComFacilidade != null) {
            caiComFacilidade.setSelected(converterIntParaBoolean(diAtual.getCai_facilmente()));
        }
        if (encaixaPecas != null) {
            encaixaPecas.setSelected(converterIntParaBoolean(diAtual.getEncaixa_pecas()));
        }
        if (recorta != null) {
            recorta.setSelected(converterIntParaBoolean(diAtual.getRecorta()));
        }
        if (unePontos != null) {
            unePontos.setSelected(converterIntParaBoolean(diAtual.getUne_pontos()));
        }
        if (consegueCorrer != null) {
            consegueCorrer.setSelected(converterIntParaBoolean(diAtual.getCorre()));
        }
        if (empilha != null) {
            empilha.setSelected(converterIntParaBoolean(diAtual.getEmpilha()));
        }
        if (agitacaoMotora != null) {
            agitacaoMotora.setSelected(converterIntParaBoolean(diAtual.getAgitacao_motora()));
        }
        if (andaLinhaReta != null) {
            andaLinhaReta.setSelected(converterIntParaBoolean(diAtual.getAnda_reto()));
        }
        if (sobeDesceEscadas != null) {
            sobeDesceEscadas.setSelected(converterIntParaBoolean(diAtual.getSobe_escada()));
        }
        if (arremessaBola != null) {
            arremessaBola.setSelected(converterIntParaBoolean(diAtual.getArremessa_bola()));
        }

        // Tela 3: AVDs
        if (usaSanitarioSemAjuda != null) {
            usaSanitarioSemAjuda.setSelected(converterIntParaBoolean(diAtual.getUsa_sanitario()));
        }
        if (penteiaSeSo != null) {
            penteiaSeSo.setSelected(converterIntParaBoolean(diAtual.getPenteia_cabelo()));
        }
        if (consegueVestirDespirSe != null) {
            consegueVestirDespirSe.setSelected(converterIntParaBoolean(diAtual.getVeste_se()));
        }
        if (lavaSecaAsMaos != null) {
            lavaSecaAsMaos.setSelected(converterIntParaBoolean(diAtual.getLava_maos()));
        }
        if (banhoComModeracao != null) {
            banhoComModeracao.setSelected(converterIntParaBoolean(diAtual.getBanha_se()));
        }
        if (calcaSeSo != null) {
            calcaSeSo.setSelected(converterIntParaBoolean(diAtual.getCalca_se()));
        }
        if (reconheceRoupas != null) {
            reconheceRoupas.setSelected(converterIntParaBoolean(diAtual.getReconhece_roupas()));
        }
        if (abreFechaTorneira != null) {
            abreFechaTorneira.setSelected(converterIntParaBoolean(diAtual.getAbre_torneira()));
        }
        if (escovaDentesSemAjuda != null) {
            escovaDentesSemAjuda.setSelected(converterIntParaBoolean(diAtual.getEscova_dentes()));
        }
        if (consegueDarNosLacos != null) {
            consegueDarNosLacos.setSelected(converterIntParaBoolean(diAtual.getDa_nos()));
        }
        if (abotoaDesabotoaRoupas != null) {
            abotoaDesabotoaRoupas.setSelected(converterIntParaBoolean(diAtual.getAbotoa_roupas()));
        }
        if (identificaPartesDoCorpo != null) {
            identificaPartesDoCorpo.setSelected(converterIntParaBoolean(diAtual.getIdentifica_partes_corpo()));
        }

        // Tela 3: Níveis de Aprendizagem
        if (garatujas != null) {
            garatujas.setSelected(converterIntParaBoolean(diAtual.getGaratujas()));
        }
        if (silabicoAlfabetico != null) {
            silabicoAlfabetico.setSelected(converterIntParaBoolean(diAtual.getSilabico_alfabetico()));
        }
        if (alfabetico != null) {
            alfabetico.setSelected(converterIntParaBoolean(diAtual.getAlfabetico()));
        }
        if (preSilabico != null) {
            preSilabico.setSelected(converterIntParaBoolean(diAtual.getPre_silabico()));
        }
        if (silabico != null) {
            silabico.setSelected(converterIntParaBoolean(diAtual.getSilabico()));
        }

        // Tela 3: Observações
        if (observacoes != null && diAtual.getObservacoes() != null) {
            observacoes.setText(diAtual.getObservacoes());
        }
    }

    // Converte int (0/1) para boolean (false/true)
    private boolean converterIntParaBoolean(int valor) {
        return valor == 1;
    }

    // Converte boolean (false/true) para int (0/1)
    private int converterBooleanParaInt(boolean valor) {
        return valor ? 1 : 0;
    }

    // Salva os dados da tela atual no objeto compartilhado
    private void salvarDadosTelaAtual() {
        if (diCompartilhada == null) {
            diCompartilhada = new DI();
        }

        // Tela 1: Comunicação
        if (falaSeuNome != null) {
            diCompartilhada.setFala_nome(converterBooleanParaInt(falaSeuNome.isSelected()));
        }
        if (dizDataNascimento != null) {
            diCompartilhada.setFala_nascimento(converterBooleanParaInt(dizDataNascimento.isSelected()));
        }
        if (lePalavras != null) {
            diCompartilhada.setLe_palavras(converterBooleanParaInt(lePalavras.isSelected()));
        }
        if (informaNumeroTelefone != null) {
            diCompartilhada.setFala_telefone(converterBooleanParaInt(informaNumeroTelefone.isSelected()));
        }
        if (emiteRespostas != null) {
            diCompartilhada.setEmite_respostas(converterBooleanParaInt(emiteRespostas.isSelected()));
        }
        if (transmiteRecado != null) {
            diCompartilhada.setTransmite_recados(converterBooleanParaInt(transmiteRecado.isSelected()));
        }
        if (informaEndereco != null) {
            diCompartilhada.setFala_endereco(converterBooleanParaInt(informaEndereco.isSelected()));
        }
        if (informaNomePais != null) {
            diCompartilhada.setFala_nome_pais(converterBooleanParaInt(informaNomePais.isSelected()));
        }
        if (compreendeOrdens != null) {
            diCompartilhada.setCompreende_ordens(converterBooleanParaInt(compreendeOrdens.isSelected()));
        }
        if (expoeIdeias != null) {
            diCompartilhada.setExpoe_ideias(converterBooleanParaInt(expoeIdeias.isSelected()));
        }
        if (recontaHistorias != null) {
            diCompartilhada.setReconta_historia(converterBooleanParaInt(recontaHistorias.isSelected()));
        }
        if (usaSistemaCA != null) {
            diCompartilhada.setUsa_sistema_ca(converterBooleanParaInt(usaSistemaCA.isSelected()));
        }
        if (relataFatosComCoerencia != null) {
            diCompartilhada.setRelata_fatos(converterBooleanParaInt(relataFatosComCoerencia.isSelected()));
        }
        if (pronunciaLetrasAlfabeto != null) {
            diCompartilhada.setPronuncia_letras(converterBooleanParaInt(pronunciaLetrasAlfabeto.isSelected()));
        }
        if (verbalizaMusicas != null) {
            diCompartilhada.setVerbaliza_musicas(converterBooleanParaInt(verbalizaMusicas.isSelected()));
        }
        if (interpretaHistorias != null) {
            diCompartilhada.setInterpreta_historias(converterBooleanParaInt(interpretaHistorias.isSelected()));
        }
        if (formulaPerguntas != null) {
            diCompartilhada.setFormula_perguntas(converterBooleanParaInt(formulaPerguntas.isSelected()));
        }
        if (utilizaGestosParaSeComunicar != null) {
            diCompartilhada.setUtiliza_gestos(converterBooleanParaInt(utilizaGestosParaSeComunicar.isSelected()));
        }

        // Tela 1: Afetiva
        if (demonstraCooperacao != null) {
            diCompartilhada.setDemonstra_cooperacao(converterBooleanParaInt(demonstraCooperacao.isSelected()));
        }
        if (timidoInseguro != null) {
            diCompartilhada.setTimido(converterBooleanParaInt(timidoInseguro.isSelected()));
        }
        if (fazBirra != null) {
            diCompartilhada.setBirra(converterBooleanParaInt(fazBirra.isSelected()));
        }
        if (solicitaOfereceAjuda != null) {
            diCompartilhada.setPede_ajuda(converterBooleanParaInt(solicitaOfereceAjuda.isSelected()));
        }
        if (riComFrequencia != null) {
            diCompartilhada.setRi(converterBooleanParaInt(riComFrequencia.isSelected()));
        }
        if (compartilhaOQueESeu != null) {
            diCompartilhada.setCompartilha(converterBooleanParaInt(compartilhaOQueESeu.isSelected()));
        }
        if (demonstraAmorGentilezaAtencao != null) {
            diCompartilhada.setDemonstra_amor(converterBooleanParaInt(demonstraAmorGentilezaAtencao.isSelected()));
        }
        if (choraComFrequencia != null) {
            diCompartilhada.setChora(converterBooleanParaInt(choraComFrequencia.isSelected()));
        }
        if (interageComColegas != null) {
            diCompartilhada.setInterage(converterBooleanParaInt(interageComColegas.isSelected()));
        }

        // Tela 2: Sensorial
        if (captaDetalhesGravura != null) {
            diCompartilhada.setDetalhes_gravura(converterBooleanParaInt(captaDetalhesGravura.isSelected()));
        }
        if (reconheceVozes != null) {
            diCompartilhada.setReconhece_vozes(converterBooleanParaInt(reconheceVozes.isSelected()));
        }
        if (reconheceCancoes != null) {
            diCompartilhada.setReconhece_cancoes(converterBooleanParaInt(reconheceCancoes.isSelected()));
        }
        if (percebeTexturas != null) {
            diCompartilhada.setPercebe_texturas(converterBooleanParaInt(percebeTexturas.isSelected()));
        }
        if (percepcaoCores != null) {
            diCompartilhada.setPercebe_cores(converterBooleanParaInt(percepcaoCores.isSelected()));
        }
        if (discriminaSons != null) {
            diCompartilhada.setDiscrimina_sons(converterBooleanParaInt(discriminaSons.isSelected()));
        }
        if (discriminaOdores != null) {
            diCompartilhada.setDiscrimina_odores(converterBooleanParaInt(discriminaOdores.isSelected()));
        }
        if (aceitaDiferentesTexturas != null) {
            diCompartilhada.setAceita_texturas(converterBooleanParaInt(aceitaDiferentesTexturas.isSelected()));
        }
        if (percepcaoFormas != null) {
            diCompartilhada.setPercepcao_formas(converterBooleanParaInt(percepcaoFormas.isSelected()));
        }
        if (identificaDirecaoSom != null) {
            diCompartilhada.setIdentifica_direcao_sons(converterBooleanParaInt(identificaDirecaoSom.isSelected()));
        }
        if (percebeDiscriminaSabores != null) {
            diCompartilhada.setDiscrimina_sabores(converterBooleanParaInt(percebeDiscriminaSabores.isSelected()));
        }
        if (acompanhaFocoLuminoso != null) {
            diCompartilhada.setAcompanha_luz(converterBooleanParaInt(acompanhaFocoLuminoso.isSelected()));
        }

        // Tela 2: Motora
        if (movimentoPincaComTesoura != null) {
            diCompartilhada.setMovimento_pinca(converterBooleanParaInt(movimentoPincaComTesoura.isSelected()));
        }
        if (amassaPapel != null) {
            diCompartilhada.setAmassa_papel(converterBooleanParaInt(amassaPapel.isSelected()));
        }
        if (caiComFacilidade != null) {
            diCompartilhada.setCai_facilmente(converterBooleanParaInt(caiComFacilidade.isSelected()));
        }
        if (encaixaPecas != null) {
            diCompartilhada.setEncaixa_pecas(converterBooleanParaInt(encaixaPecas.isSelected()));
        }
        if (recorta != null) {
            diCompartilhada.setRecorta(converterBooleanParaInt(recorta.isSelected()));
        }
        if (unePontos != null) {
            diCompartilhada.setUne_pontos(converterBooleanParaInt(unePontos.isSelected()));
        }
        if (consegueCorrer != null) {
            diCompartilhada.setCorre(converterBooleanParaInt(consegueCorrer.isSelected()));
        }
        if (empilha != null) {
            diCompartilhada.setEmpilha(converterBooleanParaInt(empilha.isSelected()));
        }
        if (agitacaoMotora != null) {
            diCompartilhada.setAgitacao_motora(converterBooleanParaInt(agitacaoMotora.isSelected()));
        }
        if (andaLinhaReta != null) {
            diCompartilhada.setAnda_reto(converterBooleanParaInt(andaLinhaReta.isSelected()));
        }
        if (sobeDesceEscadas != null) {
            diCompartilhada.setSobe_escada(converterBooleanParaInt(sobeDesceEscadas.isSelected()));
        }
        if (arremessaBola != null) {
            diCompartilhada.setArremessa_bola(converterBooleanParaInt(arremessaBola.isSelected()));
        }

        // Tela 3: AVDs
        if (usaSanitarioSemAjuda != null) {
            diCompartilhada.setUsa_sanitario(converterBooleanParaInt(usaSanitarioSemAjuda.isSelected()));
        }
        if (penteiaSeSo != null) {
            diCompartilhada.setPenteia_cabelo(converterBooleanParaInt(penteiaSeSo.isSelected()));
        }
        if (consegueVestirDespirSe != null) {
            diCompartilhada.setVeste_se(converterBooleanParaInt(consegueVestirDespirSe.isSelected()));
        }
        if (lavaSecaAsMaos != null) {
            diCompartilhada.setLava_maos(converterBooleanParaInt(lavaSecaAsMaos.isSelected()));
        }
        if (banhoComModeracao != null) {
            diCompartilhada.setBanha_se(converterBooleanParaInt(banhoComModeracao.isSelected()));
        }
        if (calcaSeSo != null) {
            diCompartilhada.setCalca_se(converterBooleanParaInt(calcaSeSo.isSelected()));
        }
        if (reconheceRoupas != null) {
            diCompartilhada.setReconhece_roupas(converterBooleanParaInt(reconheceRoupas.isSelected()));
        }
        if (abreFechaTorneira != null) {
            diCompartilhada.setAbre_torneira(converterBooleanParaInt(abreFechaTorneira.isSelected()));
        }
        if (escovaDentesSemAjuda != null) {
            diCompartilhada.setEscova_dentes(converterBooleanParaInt(escovaDentesSemAjuda.isSelected()));
        }
        if (consegueDarNosLacos != null) {
            diCompartilhada.setDa_nos(converterBooleanParaInt(consegueDarNosLacos.isSelected()));
        }
        if (abotoaDesabotoaRoupas != null) {
            diCompartilhada.setAbotoa_roupas(converterBooleanParaInt(abotoaDesabotoaRoupas.isSelected()));
        }
        if (identificaPartesDoCorpo != null) {
            diCompartilhada.setIdentifica_partes_corpo(converterBooleanParaInt(identificaPartesDoCorpo.isSelected()));
        }

        // Tela 3: Níveis de Aprendizagem
        if (garatujas != null) {
            diCompartilhada.setGaratujas(converterBooleanParaInt(garatujas.isSelected()));
        }
        if (silabicoAlfabetico != null) {
            diCompartilhada.setSilabico_alfabetico(converterBooleanParaInt(silabicoAlfabetico.isSelected()));
        }
        if (alfabetico != null) {
            diCompartilhada.setAlfabetico(converterBooleanParaInt(alfabetico.isSelected()));
        }
        if (preSilabico != null) {
            diCompartilhada.setPre_silabico(converterBooleanParaInt(preSilabico.isSelected()));
        }
        if (silabico != null) {
            diCompartilhada.setSilabico(converterBooleanParaInt(silabico.isSelected()));
        }

        // Tela 3: Observações
        if (observacoes != null) {
            diCompartilhada.setObservacoes(observacoes.getText().trim());
        }
    }

    // Handlers de UI
    @FXML
    private void btnConcluirClick() {
        if (modoAtual == ModoDI.VISUALIZACAO) {
            exibirMensagemErro("Modo visualização: não é possível salvar.");
            return;
        }

        // Salva os dados da tela atual primeiro
        salvarDadosTelaAtual();

        // Mostra aviso antes de salvar
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Concluir Diagnóstico Inicial");
        alerta.setHeaderText("Deseja salvar o Diagnóstico Inicial agora?");
        alerta.setContentText("Todos os dados serão salvos no sistema.");
        var opcao = alerta.showAndWait();
        if (opcao.isEmpty() || opcao.get() != ButtonType.OK) {
            return;
        }

        // Garante que o objeto compartilhado tenha os dados mais recentes
        diAtual = diCompartilhada;

        // Salva o educando ID antes de resetar
        String educandoId = diCompartilhada.getEducando_id();

        try {
            // 1. Obtém o usuário logado
            Usuario usuarioLogado = AuthService.getUsuarioLogado();

            if (usuarioLogado == null) {
                exibirMensagemErro("Usuário não está logado. Faça login novamente.");
                return;
            }

            // 2. Verifica se é professor
            if (!"PROFESSOR".equalsIgnoreCase(usuarioLogado.getTipo())) {
                exibirMensagemErro("Apenas professores podem criar Diagnósticos Iniciais. Tipo do usuário: " + usuarioLogado.getTipo());
                return;
            }

            // 3. Define o ID do professor no DI
            diCompartilhada.setProfessor_id(getIdProfessorLogado());

            // 4. Metadados obrigatórios
            boolean edicao = modoAtual == ModoDI.EDICAO;
            // Apenas define a data de criação se for uma nova criação
            if (!edicao) {
                diCompartilhada.setData_criacao(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            }

            boolean sucesso;
            if (edicao) {
                sucesso = diService.atualizarDI(diCompartilhada);
            } else {
                sucesso = diService.cadastrarNovoDI(diCompartilhada);
            }

            if (sucesso) {
                exibirMensagemSucesso(edicao ? "Diagnóstico Inicial atualizado com sucesso!" : "Diagnóstico Inicial criado com sucesso!");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            resetarDI();
                            voltarComPopup(educandoId);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                exibirMensagemErro(edicao ? "Erro ao atualizar Diagnóstico Inicial. Tente novamente." : "Erro ao cadastrar Diagnóstico Inicial. Tente novamente.");
            }

        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar Diagnóstico Inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getIdProfessorLogado() {
        return AuthService.getIdProfessorLogado();
    }

    @FXML
    private void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Diagnóstico Inicial");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            String educandoId = diAtual != null ? diAtual.getEducando_id() : null;
            resetarDI();
            voltarComPopup(educandoId);
        }
    }

    @FXML
    private void btnCancelClick() {
        btnCancelarClick();
    }

    @FXML
    private void btnSeguinteClick() {
        navegandoEntreTelas = true;

        // Salva os dados da tela atual antes de navegar
        salvarDadosTelaAtual();

        // Determina qual é a próxima tela
        switch (telaAtual) {
            case 1:
                GerenciadorTelas.getInstance().trocarTela("diagnostico-2.fxml");
                break;
            case 2:
                GerenciadorTelas.getInstance().trocarTela("diagnostico-3.fxml");
                break;
        }
    }

    @FXML
    private void btnVoltarClick() {
        navegandoEntreTelas = true;

        // Salva os dados da tela atual antes de navegar
        salvarDadosTelaAtual();

        // Determina qual é a tela anterior
        switch (telaAtual) {
            case 2:
                GerenciadorTelas.getInstance().trocarTela("diagnostico-1.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("diagnostico-2.fxml");
                break;
        }
    }

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

    @FXML
    private void btnRelatoriosClick() {
        // Implementar navegação para relatórios se necessário
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

    private void resetarDI() {
        modoAtual = ModoDI.NOVA;
        telaAtual = 1;
        diCompartilhada = new DI();
        navegandoEntreTelas = false;
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

    // Métodos de fluxo estáticos
    public static void iniciarNovoDI() {
        modoAtual = ModoDI.NOVA;
        telaAtual = 1;
        diCompartilhada = new DI();
        navegandoEntreTelas = false;
    }

    public static void editarDIExistente(DI existente) {
        modoAtual = ModoDI.EDICAO;
        telaAtual = 1;
        diCompartilhada = (existente != null) ? existente : new DI();
        navegandoEntreTelas = false;
    }

    public static void visualizarDI(DI existente) {
        modoAtual = ModoDI.VISUALIZACAO;
        telaAtual = 1;
        diCompartilhada = existente;
        navegandoEntreTelas = false;
    }

    public static void setEducandoIdParaDI(String educandoId) {
        if (diCompartilhada == null) {
            diCompartilhada = new DI();
        }
        diCompartilhada.setEducando_id(educandoId);
    }

    public static void setTurmaIdOrigem(String turmaId) {
        turmaIdOrigem = turmaId;
    }
}
