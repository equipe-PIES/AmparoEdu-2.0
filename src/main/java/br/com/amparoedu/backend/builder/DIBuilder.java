package br.com.amparoedu.backend.builder;

import br.com.amparoedu.backend.model.DI;

// Builder para DI
public class DIBuilder {
    private DI documento = new DI();

    public DIBuilder() {
    }

    public DIBuilder(DI base) {
        this.documento = (base != null) ? base : new DI();
    }

    // Identificadores 
    public DIBuilder comId(String id) {
        documento.setId(id);
        return this;
    }

    public DIBuilder comEducandoId(String educandoId) {
        documento.setEducando_id(educandoId);
        return this;
    }

    public DIBuilder comProfessorId(String professorId) {
        documento.setProfessor_id(professorId);
        return this;
    }

    public DIBuilder comDataCriacao(String dataCriacao) {
        documento.setData_criacao(dataCriacao);
        return this;
    }

    // Tela 1
    public DIBuilder comFalaNome(String valor) {
        documento.setFala_nome(valor);
        return this;
    }

    public DIBuilder comFalaNascimento(String valor) {
        documento.setFala_nascimento(valor);
        return this;
    }

    public DIBuilder comLePalavras(String valor) {
        documento.setLe_palavras(valor);
        return this;
    }

    public DIBuilder comFalaTelefone(String valor) {
        documento.setFala_telefone(valor);
        return this;
    }

    public DIBuilder comEmiteRespostas(String valor) {
        documento.setEmite_respostas(valor);
        return this;
    }

    public DIBuilder comTransmiteRecados(String valor) {
        documento.setTransmite_recados(valor);
        return this;
    }

    public DIBuilder comFalaEndereco(String valor) {
        documento.setFala_endereco(valor);
        return this;
    }

    public DIBuilder comFalaNomePais(String valor) {
        documento.setFala_nome_pais(valor);
        return this;
    }

    public DIBuilder comComprehendeOrdens(String valor) {
        documento.setCompreende_ordens(valor);
        return this;
    }

    public DIBuilder comExpoeIdeias(String valor) {
        documento.setExpoe_ideias(valor);
        return this;
    }

    public DIBuilder comRecontaHistoria(String valor) {
        documento.setReconta_historia(valor);
        return this;
    }

    public DIBuilder comUsaSistemaCA(String valor) {
        documento.setUsa_sistema_ca(valor);
        return this;
    }

    public DIBuilder comRelataFatos(String valor) {
        documento.setRelata_fatos(valor);
        return this;
    }

    public DIBuilder comPronunciaLetras(String valor) {
        documento.setPronuncia_letras(valor);
        return this;
    }

    public DIBuilder comVerbalizaMusicas(String valor) {
        documento.setVerbaliza_musicas(valor);
        return this;
    }

    public DIBuilder comInterpretaHistorias(String valor) {
        documento.setInterpreta_historias(valor);
        return this;
    }

    public DIBuilder comFormulaPerguntas(String valor) {
        documento.setFormula_perguntas(valor);
        return this;
    }

    public DIBuilder comUtilizaGestos(String valor) {
        documento.setUtiliza_gestos(valor);
        return this;
    }

    public DIBuilder comDemonstbraCooperacao(String valor) {
        documento.setDemonstra_cooperacao(valor);
        return this;
    }

    public DIBuilder comTimido(String valor) {
        documento.setTimido(valor);
        return this;
    }

    public DIBuilder comBirra(String valor) {
        documento.setBirra(valor);
        return this;
    }

    public DIBuilder comPedeAjuda(String valor) {
        documento.setPede_ajuda(valor);
        return this;
    }

    public DIBuilder comRi(String valor) {
        documento.setRi(valor);
        return this;
    }

    public DIBuilder comCompartilha(String valor) {
        documento.setCompartilha(valor);
        return this;
    }

    public DIBuilder comDemonstbraAmor(String valor) {
        documento.setDemonstra_amor(valor);
        return this;
    }

    public DIBuilder comChora(String valor) {
        documento.setChora(valor);
        return this;
    }

    public DIBuilder comInterage(String valor) {
        documento.setInterage(valor);
        return this;
    }

    // Tela 2: Sensorial e Motora
    public DIBuilder comDetalhesGravura(String valor) {
        documento.setDetalhes_gravura(valor);
        return this;
    }

    public DIBuilder comReconheceVozes(String valor) {
        documento.setReconhece_vozes(valor);
        return this;
    }

    public DIBuilder comReconheceCancoes(String valor) {
        documento.setReconhece_cancoes(valor);
        return this;
    }

    public DIBuilder comPercebeTexturas(String valor) {
        documento.setPercebe_texturas(valor);
        return this;
    }

    public DIBuilder comPercebeCores(String valor) {
        documento.setPercebe_cores(valor);
        return this;
    }

    public DIBuilder comDiscriminaSons(String valor) {
        documento.setDiscrimina_sons(valor);
        return this;
    }

    public DIBuilder comDiscriminaOdores(String valor) {
        documento.setDiscrimina_odores(valor);
        return this;
    }

    public DIBuilder comAceitaTexturas(String valor) {
        documento.setAceita_texturas(valor);
        return this;
    }

    public DIBuilder comPercepcaoFormas(String valor) {
        documento.setPercepcao_formas(valor);
        return this;
    }

    public DIBuilder comIdentificaDirecaoSons(String valor) {
        documento.setIdentifica_direcao_sons(valor);
        return this;
    }

    public DIBuilder comDiscriminaSabores(String valor) {
        documento.setDiscrimina_sabores(valor);
        return this;
    }

    public DIBuilder comAcompanhaLuz(String valor) {
        documento.setAcompanha_luz(valor);
        return this;
    }

    public DIBuilder comMovimentoPinca(String valor) {
        documento.setMovimento_pinca(valor);
        return this;
    }

    public DIBuilder comAmassaPapel(String valor) {
        documento.setAmassa_papel(valor);
        return this;
    }

    public DIBuilder comCaiFacilmente(String valor) {
        documento.setCai_facilmente(valor);
        return this;
    }

    public DIBuilder comEncaixaPecas(String valor) {
        documento.setEncaixa_pecas(valor);
        return this;
    }

    public DIBuilder comRecorta(String valor) {
        documento.setRecorta(valor);
        return this;
    }

    public DIBuilder comUnePontos(String valor) {
        documento.setUne_pontos(valor);
        return this;
    }

    public DIBuilder comCorre(String valor) {
        documento.setCorre(valor);
        return this;
    }

    public DIBuilder comEmpilha(String valor) {
        documento.setEmpilha(valor);
        return this;
    }

    public DIBuilder comAgitacaoMotora(String valor) {
        documento.setAgitacao_motora(valor);
        return this;
    }

    public DIBuilder comAndaReto(String valor) {
        documento.setAnda_reto(valor);
        return this;
    }

    public DIBuilder comSobeEscada(String valor) {
        documento.setSobe_escada(valor);
        return this;
    }

    public DIBuilder comArremessaBola(String valor) {
        documento.setArremessa_bola(valor);
        return this;
    }

    // Tela 3: AVDs, Niveis de Aprendizagem e Observacoes
    public DIBuilder comUsaSanitario(String valor) {
        documento.setUsa_sanitario(valor);
        return this;
    }

    public DIBuilder comPenteiaCabelo(String valor) {
        documento.setPenteia_cabelo(valor);
        return this;
    }

    public DIBuilder comVesteSe(String valor) {
        documento.setVeste_se(valor);
        return this;
    }

    public DIBuilder comLavamaos(String valor) {
        documento.setLava_maos(valor);
        return this;
    }

    public DIBuilder comBanhaSe(String valor) {
        documento.setBanha_se(valor);
        return this;
    }

    public DIBuilder comCalcaSe(String valor) {
        documento.setCalca_se(valor);
        return this;
    }

    public DIBuilder comReconheceRoupas(String valor) {
        documento.setReconhece_roupas(valor);
        return this;
    }

    public DIBuilder comAbreFechaTorneira(String valor) {
        documento.setAbre_torneira(valor);
        return this;
    }

    public DIBuilder comEscovaDentes(String valor) {
        documento.setEscova_dentes(valor);
        return this;
    }

    public DIBuilder comDarNosLacos(String valor) {
        documento.setDa_nos(valor);
        return this;
    }

    public DIBuilder comAbotoaDesabotoa(String valor) {
        documento.setAbotoa_roupas(valor);
        return this;
    }

    public DIBuilder comIdentificaPartesCorpo(String valor) {
        documento.setIdentifica_partes_corpo(valor);
        return this;
    }

    public DIBuilder comGaratujas(String valor) {
        documento.setGaratujas(valor);
        return this;
    }

    public DIBuilder comSilabicoAlfabetico(String valor) {
        documento.setSilabico_alfabetico(valor);
        return this;
    }

    public DIBuilder comAlfabetico(String valor) {
        documento.setAlfabetico(valor);
        return this;
    }

    public DIBuilder comPreSilabico(String valor) {
        documento.setPre_silabico(valor);
        return this;
    }

    public DIBuilder comSilabico(String valor) {
        documento.setSilabico(valor);
        return this;
    }

    public DIBuilder comObservacoes(String observacoes) {
        documento.setObservacoes(observacoes);
        return this;
    }

    // Flags
    public DIBuilder comSincronizado(int flag) {
        documento.setSincronizado(flag);
        return this;
    }

    public DIBuilder comExcluido(int flag) {
        documento.setExcluido(flag);
        return this;
    }

    // Constroi o DI com validacao completa.
    public DI build() {
        if (documento.getEducando_id() == null) {
            throw new IllegalStateException("Todo DI deve ter um Educando ID");
        }
        return documento;
    }

    // Constroi o DI sem validacao completa.
    public DI buildPartial() {
        return documento;
    }
}
