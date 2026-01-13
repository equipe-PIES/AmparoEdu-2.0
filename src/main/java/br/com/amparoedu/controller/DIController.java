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

    // Controles DI - Tela 1: Área da Comunicação
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

    // Controles DI - Tela 1: Área Afetiva
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

    // Controles DI - Tela 2: Área Sensorial
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

    // Controles DI - Tela 2: Área Motora
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

    // Controles DI - Tela 3: AVDs
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

    // Controles DI - Tela 3: Níveis de Aprendizagem
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

    // Controles DI - Tela 3: Observações
    @FXML
    private TextArea observacoes;

    // Controles comuns
    @FXML
    private Label nomeUsuario;
    @FXML
    private Label cargoUsuario;
    @FXML
    private Label indicadorDeTela;
    @FXML
    private Label validationMsg;
    @FXML
    private Label nameUser;
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
        desabilitarCheckBox(falaSeuNome);
        desabilitarCheckBox(dizDataNascimento);
        desabilitarCheckBox(lePalavras);
        desabilitarCheckBox(informaNumeroTelefone);
        desabilitarCheckBox(emiteRespostas);
        desabilitarCheckBox(transmiteRecado);
        desabilitarCheckBox(informaEndereco);
        desabilitarCheckBox(informaNomePais);
        desabilitarCheckBox(compreendeOrdens);
        desabilitarCheckBox(expoeIdeias);
        desabilitarCheckBox(recontaHistorias);
        desabilitarCheckBox(usaSistemaCA);
        desabilitarCheckBox(relataFatosComCoerencia);
        desabilitarCheckBox(pronunciaLetrasAlfabeto);
        desabilitarCheckBox(verbalizaMusicas);
        desabilitarCheckBox(interpretaHistorias);
        desabilitarCheckBox(formulaPerguntas);
        desabilitarCheckBox(utilizaGestosParaSeComunicar);
        desabilitarCheckBox(demonstraCooperacao);
        desabilitarCheckBox(timidoInseguro);
        desabilitarCheckBox(fazBirra);
        desabilitarCheckBox(solicitaOfereceAjuda);
        desabilitarCheckBox(riComFrequencia);
        desabilitarCheckBox(compartilhaOQueESeu);
        desabilitarCheckBox(demonstraAmorGentilezaAtencao);
        desabilitarCheckBox(choraComFrequencia);
        desabilitarCheckBox(interageComColegas);
        desabilitarCheckBox(captaDetalhesGravura);
        desabilitarCheckBox(reconheceVozes);
        desabilitarCheckBox(reconheceCancoes);
        desabilitarCheckBox(percebeTexturas);
        desabilitarCheckBox(percepcaoCores);
        desabilitarCheckBox(discriminaSons);
        desabilitarCheckBox(discriminaOdores);
        desabilitarCheckBox(aceitaDiferentesTexturas);
        desabilitarCheckBox(percepcaoFormas);
        desabilitarCheckBox(identificaDirecaoSom);
        desabilitarCheckBox(percebeDiscriminaSabores);
        desabilitarCheckBox(acompanhaFocoLuminoso);
        desabilitarCheckBox(movimentoPincaComTesoura);
        desabilitarCheckBox(amassaPapel);
        desabilitarCheckBox(caiComFacilidade);
        desabilitarCheckBox(encaixaPecas);
        desabilitarCheckBox(recorta);
        desabilitarCheckBox(unePontos);
        desabilitarCheckBox(consegueCorrer);
        desabilitarCheckBox(empilha);
        desabilitarCheckBox(agitacaoMotora);
        desabilitarCheckBox(andaLinhaReta);
        desabilitarCheckBox(sobeDesceEscadas);
        desabilitarCheckBox(arremessaBola);
        desabilitarCheckBox(usaSanitarioSemAjuda);
        desabilitarCheckBox(penteiaSeSo);
        desabilitarCheckBox(consegueVestirDespirSe);
        desabilitarCheckBox(lavaSecaAsMaos);
        desabilitarCheckBox(banhoComModeracao);
        desabilitarCheckBox(calcaSeSo);
        desabilitarCheckBox(reconheceRoupas);
        desabilitarCheckBox(abreFechaTorneira);
        desabilitarCheckBox(escovaDentesSemAjuda);
        desabilitarCheckBox(consegueDarNosLacos);
        desabilitarCheckBox(abotoaDesabotoaRoupas);
        desabilitarCheckBox(identificaPartesDoCorpo);
        desabilitarCheckBox(garatujas);
        desabilitarCheckBox(silabicoAlfabetico);
        desabilitarCheckBox(alfabetico);
        desabilitarCheckBox(preSilabico);
        desabilitarCheckBox(silabico);

        // Desabilita TextArea
        if (observacoes != null) {
            observacoes.setDisable(true);
        }

        // Desabilita botão de conclusão
        if (btnConcluir != null) {
            btnConcluir.setDisable(true);
        }
    }

    // Método auxiliar para desabilitar checkbox
    private void desabilitarCheckBox(CheckBox checkbox) {
        if (checkbox != null) {
            checkbox.setDisable(true);
        }
    }

    // Carrega dados do DI atual na tela
    private void carregarDadosNaTela() {
        if (diAtual == null)
            return;

        // Tela 1 - Área da Comunicação
        if (falaSeuNome != null) {
            falaSeuNome.setSelected(diAtual.getFala_nome() == 1);
        }
        if (dizDataNascimento != null) {
            dizDataNascimento.setSelected(diAtual.getFala_nascimento() == 1);
        }
        if (lePalavras != null) {
            lePalavras.setSelected(diAtual.getLe_palavras() == 1);
        }
        if (informaNumeroTelefone != null) {
            informaNumeroTelefone.setSelected(diAtual.getFala_telefone() == 1);
        }
        if (emiteRespostas != null) {
            emiteRespostas.setSelected(diAtual.getEmite_respostas() == 1);
        }
        if (transmiteRecado != null) {
            transmiteRecado.setSelected(diAtual.getTransmite_recados() == 1);
        }
        if (informaEndereco != null) {
            informaEndereco.setSelected(diAtual.getFala_endereco() == 1);
        }
        if (informaNomePais != null) {
            informaNomePais.setSelected(diAtual.getFala_nome_pais() == 1);
        }
        if (compreendeOrdens != null) {
            compreendeOrdens.setSelected(diAtual.getCompreende_ordens() == 1);
        }
        if (expoeIdeias != null) {
            expoeIdeias.setSelected(diAtual.getExpoe_ideias() == 1);
        }
        if (recontaHistorias != null) {
            recontaHistorias.setSelected(diAtual.getReconta_historia() == 1);
        }
        if (usaSistemaCA != null) {
            usaSistemaCA.setSelected(diAtual.getUsa_sistema_ca() == 1);
        }
        if (relataFatosComCoerencia != null) {
            relataFatosComCoerencia.setSelected(diAtual.getRelata_fatos() == 1);
        }
        if (pronunciaLetrasAlfabeto != null) {
            pronunciaLetrasAlfabeto.setSelected(diAtual.getPronuncia_letras() == 1);
        }
        if (verbalizaMusicas != null) {
            verbalizaMusicas.setSelected(diAtual.getVerbaliza_musicas() == 1);
        }
        if (interpretaHistorias != null) {
            interpretaHistorias.setSelected(diAtual.getInterpreta_historias() == 1);
        }
        if (formulaPerguntas != null) {
            formulaPerguntas.setSelected(diAtual.getFormula_perguntas() == 1);
        }
        if (utilizaGestosParaSeComunicar != null) {
            utilizaGestosParaSeComunicar.setSelected(diAtual.getUtiliza_gestos() == 1);
        }

        // Tela 1 - Área Afetiva
        if (demonstraCooperacao != null) {
            demonstraCooperacao.setSelected(diAtual.getDemonstra_cooperacao() == 1);
        }
        if (timidoInseguro != null) {
            timidoInseguro.setSelected(diAtual.getTimido() == 1);
        }
        if (fazBirra != null) {
            fazBirra.setSelected(diAtual.getBirra() == 1);
        }
        if (solicitaOfereceAjuda != null) {
            solicitaOfereceAjuda.setSelected(diAtual.getPede_ajuda() == 1);
        }
        if (riComFrequencia != null) {
            riComFrequencia.setSelected(diAtual.getRi() == 1);
        }
        if (compartilhaOQueESeu != null) {
            compartilhaOQueESeu.setSelected(diAtual.getCompartilha() == 1);
        }
        if (demonstraAmorGentilezaAtencao != null) {
            demonstraAmorGentilezaAtencao.setSelected(diAtual.getDemonstra_amor() == 1);
        }
        if (choraComFrequencia != null) {
            choraComFrequencia.setSelected(diAtual.getChora() == 1);
        }
        if (interageComColegas != null) {
            interageComColegas.setSelected(diAtual.getInterage() == 1);
        }

        // Tela 2 - Área Sensorial
        if (captaDetalhesGravura != null) {
            captaDetalhesGravura.setSelected(diAtual.getDetalhes_gravura() == 1);
        }
        if (reconheceVozes != null) {
            reconheceVozes.setSelected(diAtual.getReconhece_vozes() == 1);
        }
        if (reconheceCancoes != null) {
            reconheceCancoes.setSelected(diAtual.getReconhece_cancoes() == 1);
        }
        if (percebeTexturas != null) {
            percebeTexturas.setSelected(diAtual.getPercebe_texturas() == 1);
        }
        if (percepcaoCores != null) {
            percepcaoCores.setSelected(diAtual.getPercebe_cores() == 1);
        }
        if (discriminaSons != null) {
            discriminaSons.setSelected(diAtual.getDiscrimina_sons() == 1);
        }
        if (discriminaOdores != null) {
            discriminaOdores.setSelected(diAtual.getDiscrimina_odores() == 1);
        }
        if (aceitaDiferentesTexturas != null) {
            aceitaDiferentesTexturas.setSelected(diAtual.getAceita_texturas() == 1);
        }
        if (percepcaoFormas != null) {
            percepcaoFormas.setSelected(diAtual.getPercepcao_formas() == 1);
        }
        if (identificaDirecaoSom != null) {
            identificaDirecaoSom.setSelected(diAtual.getIdentifica_direcao_sons() == 1);
        }
        if (percebeDiscriminaSabores != null) {
            percebeDiscriminaSabores.setSelected(diAtual.getDiscrimina_sabores() == 1);
        }
        if (acompanhaFocoLuminoso != null) {
            acompanhaFocoLuminoso.setSelected(diAtual.getAcompanha_luz() == 1);
        }

        // Tela 2 - Área Motora
        if (movimentoPincaComTesoura != null) {
            movimentoPincaComTesoura.setSelected(diAtual.getMovimento_pinca() == 1);
        }
        if (amassaPapel != null) {
            amassaPapel.setSelected(diAtual.getAmassa_papel() == 1);
        }
        if (caiComFacilidade != null) {
            caiComFacilidade.setSelected(diAtual.getCai_facilmente() == 1);
        }
        if (encaixaPecas != null) {
            encaixaPecas.setSelected(diAtual.getEncaixa_pecas() == 1);
        }
        if (recorta != null) {
            recorta.setSelected(diAtual.getRecorta() == 1);
        }
        if (unePontos != null) {
            unePontos.setSelected(diAtual.getUne_pontos() == 1);
        }
        if (consegueCorrer != null) {
            consegueCorrer.setSelected(diAtual.getCorre() == 1);
        }
        if (empilha != null) {
            empilha.setSelected(diAtual.getEmpilha() == 1);
        }
        if (agitacaoMotora != null) {
            agitacaoMotora.setSelected(diAtual.getAgitacao_motora() == 1);
        }
        if (andaLinhaReta != null) {
            andaLinhaReta.setSelected(diAtual.getAnda_reto() == 1);
        }
        if (sobeDesceEscadas != null) {
            sobeDesceEscadas.setSelected(diAtual.getSobe_escada() == 1);
        }
        if (arremessaBola != null) {
            arremessaBola.setSelected(diAtual.getArremessa_bola() == 1);
        }

        // Tela 3 - AVDs
        if (usaSanitarioSemAjuda != null) {
            usaSanitarioSemAjuda.setSelected(diAtual.getUsa_sanitario() == 1);
        }
        if (penteiaSeSo != null) {
            penteiaSeSo.setSelected(diAtual.getPenteia_cabelo() == 1);
        }
        if (consegueVestirDespirSe != null) {
            consegueVestirDespirSe.setSelected(diAtual.getVeste_se() == 1);
        }
        if (lavaSecaAsMaos != null) {
            lavaSecaAsMaos.setSelected(diAtual.getLava_maos() == 1);
        }
        if (banhoComModeracao != null) {
            banhoComModeracao.setSelected(diAtual.getBanha_se() == 1);
        }
        if (calcaSeSo != null) {
            calcaSeSo.setSelected(diAtual.getCalca_se() == 1);
        }
        if (reconheceRoupas != null) {
            reconheceRoupas.setSelected(diAtual.getReconhece_roupas() == 1);
        }
        if (abreFechaTorneira != null) {
            abreFechaTorneira.setSelected(diAtual.getAbre_torneira() == 1);
        }
        if (escovaDentesSemAjuda != null) {
            escovaDentesSemAjuda.setSelected(diAtual.getEscova_dentes() == 1);
        }
        if (consegueDarNosLacos != null) {
            consegueDarNosLacos.setSelected(diAtual.getDa_nos() == 1);
        }
        if (abotoaDesabotoaRoupas != null) {
            abotoaDesabotoaRoupas.setSelected(diAtual.getAbotoa_roupas() == 1);
        }
        if (identificaPartesDoCorpo != null) {
            identificaPartesDoCorpo.setSelected(diAtual.getIdentifica_partes_corpo() == 1);
        }

        // Tela 3 - Níveis de Aprendizagem
        if (garatujas != null) {
            garatujas.setSelected(diAtual.getGaratujas() == 1);
        }
        if (silabicoAlfabetico != null) {
            silabicoAlfabetico.setSelected(diAtual.getSilabico_alfabetico() == 1);
        }
        if (alfabetico != null) {
            alfabetico.setSelected(diAtual.getAlfabetico() == 1);
        }
        if (preSilabico != null) {
            preSilabico.setSelected(diAtual.getPre_silabico() == 1);
        }
        if (silabico != null) {
            silabico.setSelected(diAtual.getSilabico() == 1);
        }

        // Tela 3 - Observações
        if (observacoes != null && diAtual.getObservacoes() != null) {
            observacoes.setText(diAtual.getObservacoes());
        }
    }

    // Salva os dados da tela atual no objeto compartilhado
    private void salvarDadosTelaAtual() {
        if (diCompartilhada == null) {
            diCompartilhada = new DI();
        }

        // Tela 1 - Área da Comunicação
        if (falaSeuNome != null) {
            diCompartilhada.setFala_nome(falaSeuNome.isSelected() ? 1 : 0);
        }
        if (dizDataNascimento != null) {
            diCompartilhada.setFala_nascimento(dizDataNascimento.isSelected() ? 1 : 0);
        }
        if (lePalavras != null) {
            diCompartilhada.setLe_palavras(lePalavras.isSelected() ? 1 : 0);
        }
        if (informaNumeroTelefone != null) {
            diCompartilhada.setFala_telefone(informaNumeroTelefone.isSelected() ? 1 : 0);
        }
        if (emiteRespostas != null) {
            diCompartilhada.setEmite_respostas(emiteRespostas.isSelected() ? 1 : 0);
        }
        if (transmiteRecado != null) {
            diCompartilhada.setTransmite_recados(transmiteRecado.isSelected() ? 1 : 0);
        }
        if (informaEndereco != null) {
            diCompartilhada.setFala_endereco(informaEndereco.isSelected() ? 1 : 0);
        }
        if (informaNomePais != null) {
            diCompartilhada.setFala_nome_pais(informaNomePais.isSelected() ? 1 : 0);
        }
        if (compreendeOrdens != null) {
            diCompartilhada.setCompreende_ordens(compreendeOrdens.isSelected() ? 1 : 0);
        }
        if (expoeIdeias != null) {
            diCompartilhada.setExpoe_ideias(expoeIdeias.isSelected() ? 1 : 0);
        }
        if (recontaHistorias != null) {
            diCompartilhada.setReconta_historia(recontaHistorias.isSelected() ? 1 : 0);
        }
        if (usaSistemaCA != null) {
            diCompartilhada.setUsa_sistema_ca(usaSistemaCA.isSelected() ? 1 : 0);
        }
        if (relataFatosComCoerencia != null) {
            diCompartilhada.setRelata_fatos(relataFatosComCoerencia.isSelected() ? 1 : 0);
        }
        if (pronunciaLetrasAlfabeto != null) {
            diCompartilhada.setPronuncia_letras(pronunciaLetrasAlfabeto.isSelected() ? 1 : 0);
        }
        if (verbalizaMusicas != null) {
            diCompartilhada.setVerbaliza_musicas(verbalizaMusicas.isSelected() ? 1 : 0);
        }
        if (interpretaHistorias != null) {
            diCompartilhada.setInterpreta_historias(interpretaHistorias.isSelected() ? 1 : 0);
        }
        if (formulaPerguntas != null) {
            diCompartilhada.setFormula_perguntas(formulaPerguntas.isSelected() ? 1 : 0);
        }
        if (utilizaGestosParaSeComunicar != null) {
            diCompartilhada.setUtiliza_gestos(utilizaGestosParaSeComunicar.isSelected() ? 1 : 0);
        }

        // Tela 1 - Área Afetiva
        if (demonstraCooperacao != null) {
            diCompartilhada.setDemonstra_cooperacao(demonstraCooperacao.isSelected() ? 1 : 0);
        }
        if (timidoInseguro != null) {
            diCompartilhada.setTimido(timidoInseguro.isSelected() ? 1 : 0);
        }
        if (fazBirra != null) {
            diCompartilhada.setBirra(fazBirra.isSelected() ? 1 : 0);
        }
        if (solicitaOfereceAjuda != null) {
            diCompartilhada.setPede_ajuda(solicitaOfereceAjuda.isSelected() ? 1 : 0);
        }
        if (riComFrequencia != null) {
            diCompartilhada.setRi(riComFrequencia.isSelected() ? 1 : 0);
        }
        if (compartilhaOQueESeu != null) {
            diCompartilhada.setCompartilha(compartilhaOQueESeu.isSelected() ? 1 : 0);
        }
        if (demonstraAmorGentilezaAtencao != null) {
            diCompartilhada.setDemonstra_amor(demonstraAmorGentilezaAtencao.isSelected() ? 1 : 0);
        }
        if (choraComFrequencia != null) {
            diCompartilhada.setChora(choraComFrequencia.isSelected() ? 1 : 0);
        }
        if (interageComColegas != null) {
            diCompartilhada.setInterage(interageComColegas.isSelected() ? 1 : 0);
        }

        // Tela 2 - Área Sensorial
        if (captaDetalhesGravura != null) {
            diCompartilhada.setDetalhes_gravura(captaDetalhesGravura.isSelected() ? 1 : 0);
        }
        if (reconheceVozes != null) {
            diCompartilhada.setReconhece_vozes(reconheceVozes.isSelected() ? 1 : 0);
        }
        if (reconheceCancoes != null) {
            diCompartilhada.setReconhece_cancoes(reconheceCancoes.isSelected() ? 1 : 0);
        }
        if (percebeTexturas != null) {
            diCompartilhada.setPercebe_texturas(percebeTexturas.isSelected() ? 1 : 0);
        }
        if (percepcaoCores != null) {
            diCompartilhada.setPercebe_cores(percepcaoCores.isSelected() ? 1 : 0);
        }
        if (discriminaSons != null) {
            diCompartilhada.setDiscrimina_sons(discriminaSons.isSelected() ? 1 : 0);
        }
        if (discriminaOdores != null) {
            diCompartilhada.setDiscrimina_odores(discriminaOdores.isSelected() ? 1 : 0);
        }
        if (aceitaDiferentesTexturas != null) {
            diCompartilhada.setAceita_texturas(aceitaDiferentesTexturas.isSelected() ? 1 : 0);
        }
        if (percepcaoFormas != null) {
            diCompartilhada.setPercepcao_formas(percepcaoFormas.isSelected() ? 1 : 0);
        }
        if (identificaDirecaoSom != null) {
            diCompartilhada.setIdentifica_direcao_sons(identificaDirecaoSom.isSelected() ? 1 : 0);
        }
        if (percebeDiscriminaSabores != null) {
            diCompartilhada.setDiscrimina_sabores(percebeDiscriminaSabores.isSelected() ? 1 : 0);
        }
        if (acompanhaFocoLuminoso != null) {
            diCompartilhada.setAcompanha_luz(acompanhaFocoLuminoso.isSelected() ? 1 : 0);
        }

        // Tela 2 - Área Motora
        if (movimentoPincaComTesoura != null) {
            diCompartilhada.setMovimento_pinca(movimentoPincaComTesoura.isSelected() ? 1 : 0);
        }
        if (amassaPapel != null) {
            diCompartilhada.setAmassa_papel(amassaPapel.isSelected() ? 1 : 0);
        }
        if (caiComFacilidade != null) {
            diCompartilhada.setCai_facilmente(caiComFacilidade.isSelected() ? 1 : 0);
        }
        if (encaixaPecas != null) {
            diCompartilhada.setEncaixa_pecas(encaixaPecas.isSelected() ? 1 : 0);
        }
        if (recorta != null) {
            diCompartilhada.setRecorta(recorta.isSelected() ? 1 : 0);
        }
        if (unePontos != null) {
            diCompartilhada.setUne_pontos(unePontos.isSelected() ? 1 : 0);
        }
        if (consegueCorrer != null) {
            diCompartilhada.setCorre(consegueCorrer.isSelected() ? 1 : 0);
        }
        if (empilha != null) {
            diCompartilhada.setEmpilha(empilha.isSelected() ? 1 : 0);
        }
        if (agitacaoMotora != null) {
            diCompartilhada.setAgitacao_motora(agitacaoMotora.isSelected() ? 1 : 0);
        }
        if (andaLinhaReta != null) {
            diCompartilhada.setAnda_reto(andaLinhaReta.isSelected() ? 1 : 0);
        }
        if (sobeDesceEscadas != null) {
            diCompartilhada.setSobe_escada(sobeDesceEscadas.isSelected() ? 1 : 0);
        }
        if (arremessaBola != null) {
            diCompartilhada.setArremessa_bola(arremessaBola.isSelected() ? 1 : 0);
        }

        // Tela 3 - AVDs
        if (usaSanitarioSemAjuda != null) {
            diCompartilhada.setUsa_sanitario(usaSanitarioSemAjuda.isSelected() ? 1 : 0);
        }
        if (penteiaSeSo != null) {
            diCompartilhada.setPenteia_cabelo(penteiaSeSo.isSelected() ? 1 : 0);
        }
        if (consegueVestirDespirSe != null) {
            diCompartilhada.setVeste_se(consegueVestirDespirSe.isSelected() ? 1 : 0);
        }
        if (lavaSecaAsMaos != null) {
            diCompartilhada.setLava_maos(lavaSecaAsMaos.isSelected() ? 1 : 0);
        }
        if (banhoComModeracao != null) {
            diCompartilhada.setBanha_se(banhoComModeracao.isSelected() ? 1 : 0);
        }
        if (calcaSeSo != null) {
            diCompartilhada.setCalca_se(calcaSeSo.isSelected() ? 1 : 0);
        }
        if (reconheceRoupas != null) {
            diCompartilhada.setReconhece_roupas(reconheceRoupas.isSelected() ? 1 : 0);
        }
        if (abreFechaTorneira != null) {
            diCompartilhada.setAbre_torneira(abreFechaTorneira.isSelected() ? 1 : 0);
        }
        if (escovaDentesSemAjuda != null) {
            diCompartilhada.setEscova_dentes(escovaDentesSemAjuda.isSelected() ? 1 : 0);
        }
        if (consegueDarNosLacos != null) {
            diCompartilhada.setDa_nos(consegueDarNosLacos.isSelected() ? 1 : 0);
        }
        if (abotoaDesabotoaRoupas != null) {
            diCompartilhada.setAbotoa_roupas(abotoaDesabotoaRoupas.isSelected() ? 1 : 0);
        }
        if (identificaPartesDoCorpo != null) {
            diCompartilhada.setIdentifica_partes_corpo(identificaPartesDoCorpo.isSelected() ? 1 : 0);
        }

        // Tela 3 - Níveis de Aprendizagem
        if (garatujas != null) {
            diCompartilhada.setGaratujas(garatujas.isSelected() ? 1 : 0);
        }
        if (silabicoAlfabetico != null) {
            diCompartilhada.setSilabico_alfabetico(silabicoAlfabetico.isSelected() ? 1 : 0);
        }
        if (alfabetico != null) {
            diCompartilhada.setAlfabetico(alfabetico.isSelected() ? 1 : 0);
        }
        if (preSilabico != null) {
            diCompartilhada.setPre_silabico(preSilabico.isSelected() ? 1 : 0);
        }
        if (silabico != null) {
            diCompartilhada.setSilabico(silabico.isSelected() ? 1 : 0);
        }

        // Tela 3 - Observações
        if (observacoes != null) {
            String texto = observacoes.getText();
            diCompartilhada.setObservacoes(texto != null ? texto.trim() : "");
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

    // Handler para o botão Sair - fecha a janela/volta para a tela anterior
    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }

    // Handler para o botão Cancelar - cancela o processo de DI
    @FXML
    private void btnCancelarClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Diagnóstico Inicial");
        alert.setHeaderText("Deseja realmente cancelar?");
        alert.setContentText("Todos os dados preenchidos serão perdidos.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Salva o educando ID antes de resetar
            String educandoId = diAtual != null ? diAtual.getEducando_id() : null;
            resetarDI();
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

    private void resetarDI() {
        telaAtual = 1;
        diCompartilhada = null;
        diAtual = new DI();
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
                GerenciadorTelas.getInstance().trocarTela("diagnostico-2.fxml");
                break;
            case 2:
                GerenciadorTelas.getInstance().trocarTela("diagnostico-3.fxml");
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
                GerenciadorTelas.getInstance().trocarTela("diagnostico-1.fxml");
                break;
            case 3:
                GerenciadorTelas.getInstance().trocarTela("diagnostico-2.fxml");
                break;
        }
    }

    // Métodos de fluxo
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