package br.com.amparoedu.backend.model;

public class DI {
    private String id;
    private String educando_id;
    private String professor_id;
    private String data_criacao;
    // Area da Comunicação
    private String fala_nome;
    private String fala_nascimento;
    private String le_palavras;
    private String fala_telefone;
    private String emite_respostas;
    private String transmite_recados;
    private String fala_endereco;
    private String fala_nome_pais;
    private String compreende_ordens;
    private String expoe_ideias;
    private String reconta_historia;
    private String usa_sistema_ca;
    private String relata_fatos;
    private String pronuncia_letras;
    private String verbaliza_musicas;
    private String interpreta_historias;
    private String formula_perguntas;
    private String utiliza_gestos;
    // Areas Afetivas/Sociais
    private String demonstra_cooperacao;
    private String timido;
    private String birra;
    private String pede_ajuda;
    private String ri;
    private String compartilha;
    private String demonstra_amor;
    private String chora;
    private String interage;
    // Areas Sensoriais
    private String detalhes_gravura;
    private String reconhece_vozes;
    private String reconhece_cancoes;
    private String percebe_texturas;
    private String percebe_cores;
    private String discrimina_sons;
    private String discrimina_odores;
    private String aceita_texturas;
    private String percepcao_formas;
    private String identifica_direcao_sons;
    private String discrimina_sabores;
    private String acompanha_luz;
    // Areas Motoras
    private String movimento_pinca;
    private String amassa_papel;
    private String cai_facilmente;
    private String encaixa_pecas;
    private String recorta;
    private String une_pontos;
    private String corre;
    private String empilha;
    private String agitacao_motora;
    private String anda_reto;
    private String sobe_escada;
    private String arremessa_bola;
    // AVDs
    private String usa_sanitario;
    private String penteia_cabelo;
    private String veste_se;
    private String lava_maos;
    private String banha_se;
    private String calca_se;
    private String reconhece_roupas;
    private String abre_torneira;
    private String escova_dentes;
    private String da_nos;
    private String abotoa_roupas;
    private String identifica_partes_corpo;
    // Niveis de aprendizagem
    private String garatujas;
    private String silabico_alfabetico;
    private String alfabetico;
    private String pre_silabico;
    private String silabico;
    private String observacoes;
    private int sincronizado;
    private int excluido;

    public DI() {
    }

    public DI(String id, String educando_id, String professor_id, String data_criacao, String fala_nome, String fala_nascimento, String le_palavras, String fala_telefone,
              String emite_respostas, String transmite_recados, String fala_endereco, String fala_nome_pais,
              String compreende_ordens, String expoe_ideias, String reconta_historia, String usa_sistema_ca,
              String relata_fatos, String pronuncia_letras, String verbaliza_musicas, String interpreta_historias,
              String formula_perguntas, String utiliza_gestos, String demonstra_cooperacao, String timido,
              String birra, String pede_ajuda, String ri, String compartilha, String demonstra_amor, String chora,
              String interage, String detalhes_gravura, String reconhece_vozes, String reconhece_cancoes,
              String percebe_texturas, String percebe_cores, String discrimina_sons, String discrimina_odores,
              String aceita_texturas, String percepcao_formas, String identifica_direcao_sons,
              String discrimina_sabores, String acompanha_luz, String movimento_pinca, String amassa_papel,
              String cai_facilmente, String encaixa_pecas, String recorta, String une_pontos, String corre,
              String empilha, String agitacao_motora, String anda_reto, String sobe_escada, String arremessa_bola,
              String usa_sanitario, String penteia_cabelo, String veste_se, String lava_maos, String banha_se,
              String calca_se, String reconhece_roupas, String abre_torneira, String escova_dentes,
              String da_nos, String abotoa_roupas, String identifica_partes_corpo, String garatujas,
              String silabico_alfabetico, String alfabetico, String pre_silabico, String silabico,
              String observacoes, int sincronizado, int excluido) {
        this.id = id;
        this.educando_id = educando_id;
        this.professor_id = professor_id;
        this.data_criacao = data_criacao;
        this.fala_nome = fala_nome;
        this.fala_nascimento = fala_nascimento;
        this.le_palavras = le_palavras;
        this.fala_telefone = fala_telefone;
        this.emite_respostas = emite_respostas;
        this.transmite_recados = transmite_recados;
        this.fala_endereco = fala_endereco;
        this.fala_nome_pais = fala_nome_pais;
        this.compreende_ordens = compreende_ordens;
        this.expoe_ideias = expoe_ideias;
        this.reconta_historia = reconta_historia;
        this.usa_sistema_ca = usa_sistema_ca;
        this.relata_fatos = relata_fatos;
        this.pronuncia_letras = pronuncia_letras;
        this.verbaliza_musicas = verbaliza_musicas;
        this.interpreta_historias = interpreta_historias;
        this.formula_perguntas = formula_perguntas;
        this.utiliza_gestos = utiliza_gestos;
        this.demonstra_cooperacao = demonstra_cooperacao;
        this.timido = timido;
        this.birra = birra;
        this.pede_ajuda = pede_ajuda;
        this.ri = ri;
        this.compartilha = compartilha;
        this.demonstra_amor = demonstra_amor;
        this.chora = chora;
        this.interage = interage;
        this.detalhes_gravura = detalhes_gravura;
        this.reconhece_vozes = reconhece_vozes;
        this.reconhece_cancoes = reconhece_cancoes;
        this.percebe_texturas = percebe_texturas;
        this.percebe_cores = percebe_cores;
        this.discrimina_sons = discrimina_sons;
        this.discrimina_odores = discrimina_odores;
        this.aceita_texturas = aceita_texturas;
        this.percepcao_formas = percepcao_formas;
        this.identifica_direcao_sons = identifica_direcao_sons;
        this.discrimina_sabores = discrimina_sabores;
        this.acompanha_luz = acompanha_luz;
        this.movimento_pinca = movimento_pinca;
        this.amassa_papel = amassa_papel;
        this.cai_facilmente = cai_facilmente;
        this.encaixa_pecas = encaixa_pecas;
        this.recorta = recorta;
        this.une_pontos = une_pontos;
        this.corre = corre;
        this.empilha = empilha;
        this.agitacao_motora = agitacao_motora;
        this.anda_reto = anda_reto;
        this.sobe_escada = sobe_escada;
        this.arremessa_bola = arremessa_bola;
        this.usa_sanitario = usa_sanitario;
        this.penteia_cabelo = penteia_cabelo;
        this.veste_se = veste_se;
        this.lava_maos = lava_maos;
        this.banha_se = banha_se;
        this.calca_se = calca_se;
        this.reconhece_roupas = reconhece_roupas;
        this.abre_torneira = abre_torneira;
        this.escova_dentes = escova_dentes;
        this.da_nos = da_nos;
        this.abotoa_roupas = abotoa_roupas;
        this.identifica_partes_corpo = identifica_partes_corpo;
        this.garatujas = garatujas;
        this.silabico_alfabetico = silabico_alfabetico;
        this.alfabetico = alfabetico;
        this.pre_silabico = pre_silabico;
        this.silabico = silabico;
        this.observacoes = observacoes;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    // getters
    public String getId() {
        return id;
    }
    public String getEducando_id() {
        return educando_id;
    }
    public String getProfessor_id() {
        return professor_id;
    }
    public String getData_criacao() {
        return data_criacao;
    }
    public String getFala_nome() {
        return fala_nome;
    }
    public String getFala_nascimento() {
        return fala_nascimento;
    }
    public String getLe_palavras() {
        return le_palavras;
    }
    public String getFala_telefone() {
        return fala_telefone;
    }
    public String getEmite_respostas() {
        return emite_respostas;
    }
    public String getTransmite_recados() {
        return transmite_recados;
    }
    public String getFala_endereco() {
        return fala_endereco;
    }
    public String getFala_nome_pais() {
        return fala_nome_pais;
    }
    public String getCompreende_ordens() {
        return compreende_ordens;
    }
    public String getExpoe_ideias() {
        return expoe_ideias;
    }
    public String getReconta_historia() {
        return reconta_historia;
    }
    public String getUsa_sistema_ca() {
        return usa_sistema_ca;
    }
    public String getRelata_fatos() {
        return relata_fatos;
    }
    public String getPronuncia_letras() {
        return pronuncia_letras;
    }
    public String getVerbaliza_musicas() {
        return verbaliza_musicas;
    }
    public String getInterpreta_historias() {
        return interpreta_historias;
    }
    public String getFormula_perguntas() {
        return formula_perguntas;
    }
    public String getUtiliza_gestos() {
        return utiliza_gestos;
    }
    public String getDemonstra_cooperacao() {
        return demonstra_cooperacao;
    }
    public String getTimido() {
        return timido;
    }
    public String getBirra() {
        return birra;
    }
    public String getPede_ajuda() {
        return pede_ajuda;
    }
    public String getRi() {
        return ri;
    }
    public String getCompartilha() {
        return compartilha;
    }
    public String getDemonstra_amor() {
        return demonstra_amor;
    }
    public String getChora() {
        return chora;
    }
    public String getInterage() {
        return interage;
    }
    public String getDetalhes_gravura() {
        return detalhes_gravura;
    }
    public String getReconhece_vozes() {
        return reconhece_vozes;
    }
    public String getReconhece_cancoes() {
        return reconhece_cancoes;
    }
    public String getPercebe_texturas() {
        return percebe_texturas;
    }
    public String getPercebe_cores() {
        return percebe_cores;
    }
    public String getDiscrimina_sons() {
        return discrimina_sons;
    }
    public String getDiscrimina_odores() {
        return discrimina_odores;
    }
    public String getAceita_texturas() {
        return aceita_texturas;
    }
    public String getPercepcao_formas() {
        return percepcao_formas;
    }
    public String getIdentifica_direcao_sons() {
        return identifica_direcao_sons;
    }
    public String getDiscrimina_sabores() {
        return discrimina_sabores;
    }
    public String getAcompanha_luz() {
        return acompanha_luz;
    }
    public String getMovimento_pinca() {
        return movimento_pinca;
    }
    public String getAmassa_papel() {
        return amassa_papel;
    }
    public String getCai_facilmente() {
        return cai_facilmente;
    }
    public String getEncaixa_pecas() {
        return encaixa_pecas;
    }
    public String getRecorta() {
        return recorta;
    }
    public String getUne_pontos() {
        return une_pontos;
    }
    public String getCorre() {
        return corre;
    }
    public String getEmpilha() {
        return empilha;
    }
    public String getAgitacao_motora() {
        return agitacao_motora;
    }
    public String getAnda_reto() {
        return anda_reto;
    }
    public String getSobe_escada() {
        return sobe_escada;
    }
    public String getArremessa_bola() {
        return arremessa_bola;
    }
    public String getUsa_sanitario() {
        return usa_sanitario;
    }
    public String getPenteia_cabelo() {
        return penteia_cabelo;
    }
    public String getVeste_se() {
        return veste_se;
    }
    public String getLava_maos() {
        return lava_maos;
    }
    public String getBanha_se() {
        return banha_se;
    }
    public String getCalca_se() {
        return calca_se;
    }
    public String getReconhece_roupas() {
        return reconhece_roupas;
    }
    public String getAbre_torneira() {
        return abre_torneira;
    }
    public String getEscova_dentes() {
        return escova_dentes;
    }
    public String getDa_nos() {
        return da_nos;
    }
    public String getAbotoa_roupas() {
        return abotoa_roupas;
    }
    public String getIdentifica_partes_corpo() {
        return identifica_partes_corpo;
    }
    public String getGaratujas() {
        return garatujas;
    }
    public String getSilabico_alfabetico() {
        return silabico_alfabetico;
    }
    public String getAlfabetico() {
        return alfabetico;
    }
    public String getPre_silabico() {
        return pre_silabico;
    }
    public String getSilabico() {
        return silabico;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public int getSincronizado() {
        return sincronizado;
    }
    public int getExcluido() {
        return excluido;
    }
    // setters
    public void setId(String id) {
        this.id = id;
    }
    public void setEducando_id(String educando_id) {
        this.educando_id = educando_id;
    }
    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }
    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }
    public void setFala_nome(String fala_nome) {
        this.fala_nome = fala_nome;
    }
    public void setFala_nascimento(String fala_nascimento) {
        this.fala_nascimento = fala_nascimento;
    }
    public void setLe_palavras(String le_palavras) {
        this.le_palavras = le_palavras;
    }
    public void setFala_telefone(String fala_telefone) {
        this.fala_telefone = fala_telefone;
    }
    public void setEmite_respostas(String emite_respostas) {
        this.emite_respostas = emite_respostas;
    }
    public void setTransmite_recados(String transmite_recados) {
        this.transmite_recados = transmite_recados;
    }
    public void setFala_endereco(String fala_endereco) {
        this.fala_endereco = fala_endereco;
    }
    public void setFala_nome_pais(String fala_nome_pais) {
        this.fala_nome_pais = fala_nome_pais;
    }
    public void setCompreende_ordens(String compreende_ordens) {
        this.compreende_ordens = compreende_ordens;
    }
    public void setExpoe_ideias(String expoe_ideias) {
        this.expoe_ideias = expoe_ideias;
    }
    public void setReconta_historia(String reconta_historia) {
        this.reconta_historia = reconta_historia;
    }
    public void setUsa_sistema_ca(String usa_sistema_ca) {
        this.usa_sistema_ca = usa_sistema_ca;
    }
    public void setRelata_fatos(String relata_fatos) {
        this.relata_fatos = relata_fatos;
    }
    public void setPronuncia_letras(String pronuncia_letras) {
        this.pronuncia_letras = pronuncia_letras;
    }
    public void setVerbaliza_musicas(String verbaliza_musicas) {
        this.verbaliza_musicas = verbaliza_musicas;
    }
    public void setInterpreta_historias(String interpreta_historias) {
        this.interpreta_historias = interpreta_historias;
    }
    public void setFormula_perguntas(String formula_perguntas) {
        this.formula_perguntas = formula_perguntas;
    }
    public void setUtiliza_gestos(String utiliza_gestos) {
        this.utiliza_gestos = utiliza_gestos;
    }
    public void setDemonstra_cooperacao(String demonstra_cooperacao) {
        this.demonstra_cooperacao = demonstra_cooperacao;
    }
    public void setTimido(String timido) {
        this.timido = timido;
    }
    public void setBirra(String birra) {
        this.birra = birra;
    }
    public void setPede_ajuda(String pede_ajuda) {
        this.pede_ajuda = pede_ajuda;
    }
    public void setRi(String ri) {
        this.ri = ri;
    }
    public void setCompartilha(String compartilha) {
        this.compartilha = compartilha;
    }
    public void setDemonstra_amor(String demonstra_amor) {
        this.demonstra_amor = demonstra_amor;
    }
    public void setChora(String chora) {
        this.chora = chora;
    }
    public void setInterage(String interage) {
        this.interage = interage;
    }
    public void setDetalhes_gravura(String detalhes_gravura) {
        this.detalhes_gravura = detalhes_gravura;
    }
    public void setReconhece_vozes(String reconhece_vozes) {
        this.reconhece_vozes = reconhece_vozes;
    }
    public void setReconhece_cancoes(String reconhece_cancoes) {
        this.reconhece_cancoes = reconhece_cancoes;
    }
    public void setPercebe_texturas(String percebe_texturas) {
        this.percebe_texturas = percebe_texturas;
    }
    public void setPercebe_cores(String percebe_cores) {
        this.percebe_cores = percebe_cores;
    }
    public void setDiscrimina_sons(String discrimina_sons) {
        this.discrimina_sons = discrimina_sons;
    }
    public void setDiscrimina_odores(String discrimina_odores) {
        this.discrimina_odores = discrimina_odores;
    }
    public void setAceita_texturas(String aceita_texturas) {
        this.aceita_texturas = aceita_texturas;
    }
    public void setPercepcao_formas(String percepcao_formas) {
        this.percepcao_formas = percepcao_formas;
    }
    public void setIdentifica_direcao_sons(String identifica_direcao_sons) {
        this.identifica_direcao_sons = identifica_direcao_sons;
    }
    public void setDiscrimina_sabores(String discrimina_sabores) {
        this.discrimina_sabores = discrimina_sabores;
    }
    public void setAcompanha_luz(String acompanha_luz) {
        this.acompanha_luz = acompanha_luz;
    }
    public void setMovimento_pinca(String movimento_pinca) {
        this.movimento_pinca = movimento_pinca;
    }
    public void setAmassa_papel(String amassa_papel) {
        this.amassa_papel = amassa_papel;
    }
    public void setCai_facilmente(String cai_facilmente) {
        this.cai_facilmente = cai_facilmente;
    }
    public void setEncaixa_pecas(String encaixa_pecas) {
        this.encaixa_pecas = encaixa_pecas;
    }
    public void setRecorta(String recorta) {
        this.recorta = recorta;
    }
    public void setUne_pontos(String une_pontos) {
        this.une_pontos = une_pontos;
    }
    public void setCorre(String corre) {
        this.corre = corre;
    }
    public void setEmpilha(String empilha) {
        this.empilha = empilha;
    }
    public void setAgitacao_motora(String agitacao_motora) {
        this.agitacao_motora = agitacao_motora;
    }
    public void setAnda_reto(String anda_reto) {
        this.anda_reto = anda_reto;
    }
    public void setSobe_escada(String sobe_escada) {
        this.sobe_escada = sobe_escada;
    }
    public void setArremessa_bola(String arremessa_bola) {
        this.arremessa_bola = arremessa_bola;
    }
    public void setUsa_sanitario(String usa_sanitario) {
        this.usa_sanitario = usa_sanitario;
    }
    public void setPenteia_cabelo(String penteia_cabelo) {
        this.penteia_cabelo = penteia_cabelo;
    }
    public void setVeste_se(String veste_se) {
        this.veste_se = veste_se;
    }
    public void setLava_maos(String lava_maos) {
        this.lava_maos = lava_maos;
    }
    public void setBanha_se(String banha_se) {
        this.banha_se = banha_se;
    }
    public void setCalca_se(String calca_se) {
        this.calca_se = calca_se;
    }
    public void setReconhece_roupas(String reconhece_roupas) {
        this.reconhece_roupas = reconhece_roupas;
    }
    public void setAbre_torneira(String abre_torneira) {
        this.abre_torneira = abre_torneira;
    }
    public void setEscova_dentes(String escova_dentes) {
        this.escova_dentes = escova_dentes;
    }
    public void setDa_nos(String da_nos) {
        this.da_nos = da_nos;
    }
    public void setAbotoa_roupas(String abotoa_roupas) {
        this.abotoa_roupas = abotoa_roupas;
    }
    public void setIdentifica_partes_corpo(String identifica_partes_corpo) {
        this.identifica_partes_corpo = identifica_partes_corpo;
    }
    public void setGaratujas(String garatujas) {
        this.garatujas = garatujas;
    }
    public void setSilabico_alfabetico(String silabico_alfabetico) {
        this.silabico_alfabetico = silabico_alfabetico;
    }
    public void setAlfabetico(String alfabetico) {
        this.alfabetico = alfabetico;
    }
    public void setPre_silabico(String pre_silabico) {
        this.pre_silabico = pre_silabico;
    }
    public void setSilabico(String silabico) {
        this.silabico = silabico;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
}
