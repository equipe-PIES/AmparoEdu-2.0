package br.com.amparoedu.backend.model;

public class DI {
    private String id;
    private String educando_id;
    private String professor_id;
    private String data_criacao;
    // Area da Comunicação
    private int fala_nome;
    private int fala_nascimento;
    private int le_palavras;
    private int fala_telefone;
    private int emite_respostas;
    private int transmite_recados;
    private int fala_endereco;
    private int fala_nome_pais;
    private int compreende_ordens;
    private int expoe_ideias;
    private int reconta_historia;
    private int usa_sistema_ca;
    private int relata_fatos;
    private int pronuncia_letras;
    private int verbaliza_musicas;
    private int interpreta_historias;
    private int formula_perguntas;
    private int utiliza_gestos;
    // Areas Afetivas/Sociais
    private int demonstra_cooperacao;
    private int timido;
    private int birra;
    private int pede_ajuda;
    private int ri;
    private int compartilha;
    private int demonstra_amor;
    private int chora;
    private int interage;
    // Areas Sensoriais
    private int detalhes_gravura;
    private int reconhece_vozes;
    private int reconhece_cancoes;
    private int percebe_texturas;
    private int percebe_cores;
    private int discrimina_sons;
    private int discrimina_odores;
    private int aceita_texturas;
    private int percepcao_formas;
    private int identifica_direcao_sons;
    private int discrimina_sabores;
    private int acompanha_luz;
    // Areas Motoras
    private int movimento_pinca;
    private int amassa_papel;
    private int cai_facilmente;
    private int encaixa_pecas;
    private int recorta;
    private int une_pontos;
    private int corre;
    private int empilha;
    private int agitacao_motora;
    private int anda_reto;
    private int sobe_escada;
    private int arremessa_bola;
    // AVDs
    private int usa_sanitario;
    private int penteia_cabelo;
    private int veste_se;
    private int lava_maos;
    private int banha_se;
    private int calca_se;
    private int reconhece_roupas;
    private int abre_torneira;
    private int escova_dentes;
    private int da_nos;
    private int abotoa_roupas;
    private int identifica_partes_corpo;
    // Niveis de aprendizagem
    private int garatujas;
    private int silabico_alfabetico;
    private int alfabetico;
    private int pre_silabico;
    private int silabico;
    private String observacoes;
    private int sincronizado;
    private int excluido;

    public DI() {
    }

    public DI(String id, String educando_id, String professor_id, String data_criacao, int fala_nome, int fala_nascimento, int le_palavras, int fala_telefone,
              int emite_respostas, int transmite_recados, int fala_endereco, int fala_nome_pais,
              int compreende_ordens, int expoe_ideias, int reconta_historia, int usa_sistema_ca,
              int relata_fatos, int pronuncia_letras, int verbaliza_musicas, int interpreta_historias,
              int formula_perguntas, int utiliza_gestos, int demonstra_cooperacao, int timido,
              int birra, int pede_ajuda, int ri, int compartilha, int demonstra_amor, int chora,
              int interage, int detalhes_gravura, int reconhece_vozes, int reconhece_cancoes,
              int percebe_texturas, int percebe_cores, int discrimina_sons, int discrimina_odores,
              int aceita_texturas, int percepcao_formas, int identifica_direcao_sons,
              int discrimina_sabores, int acompanha_luz, int movimento_pinca, int amassa_papel,
              int cai_facilmente, int encaixa_pecas, int recorta, int une_pontos, int corre,
              int empilha, int agitacao_motora, int anda_reto, int sobe_escada, int arremessa_bola,
              int usa_sanitario, int penteia_cabelo, int veste_se, int lava_maos, int banha_se,
              int calca_se, int reconhece_roupas, int abre_torneira, int escova_dentes,
              int da_nos, int abotoa_roupas, int identifica_partes_corpo, int garatujas,
              int silabico_alfabetico, int alfabetico, int pre_silabico, int silabico,
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
    public int getFala_nome() {
        return fala_nome;
    }
    public int getFala_nascimento() {
        return fala_nascimento;
    }
    public int getLe_palavras() {
        return le_palavras;
    }
    public int getFala_telefone() {
        return fala_telefone;
    }
    public int getEmite_respostas() {
        return emite_respostas;
    }
    public int getTransmite_recados() {
        return transmite_recados;
    }
    public int getFala_endereco() {
        return fala_endereco;
    }
    public int getFala_nome_pais() {
        return fala_nome_pais;
    }
    public int getCompreende_ordens() {
        return compreende_ordens;
    }
    public int getExpoe_ideias() {
        return expoe_ideias;
    }
    public int getReconta_historia() {
        return reconta_historia;
    }
    public int getUsa_sistema_ca() {
        return usa_sistema_ca;
    }
    public int getRelata_fatos() {
        return relata_fatos;
    }
    public int getPronuncia_letras() {
        return pronuncia_letras;
    }
    public int getVerbaliza_musicas() {
        return verbaliza_musicas;
    }
    public int getInterpreta_historias() {
        return interpreta_historias;
    }
    public int getFormula_perguntas() {
        return formula_perguntas;
    }
    public int getUtiliza_gestos() {
        return utiliza_gestos;
    }
    public int getDemonstra_cooperacao() {
        return demonstra_cooperacao;
    }
    public int getTimido() {
        return timido;
    }
    public int getBirra() {
        return birra;
    }
    public int getPede_ajuda() {
        return pede_ajuda;
    }
    public int getRi() {
        return ri;
    }
    public int getCompartilha() {
        return compartilha;
    }
    public int getDemonstra_amor() {
        return demonstra_amor;
    }
    public int getChora() {
        return chora;
    }
    public int getInterage() {
        return interage;
    }
    public int getDetalhes_gravura() {
        return detalhes_gravura;
    }
    public int getReconhece_vozes() {
        return reconhece_vozes;
    }
    public int getReconhece_cancoes() {
        return reconhece_cancoes;
    }
    public int getPercebe_texturas() {
        return percebe_texturas;
    }
    public int getPercebe_cores() {
        return percebe_cores;
    }
    public int getDiscrimina_sons() {
        return discrimina_sons;
    }
    public int getDiscrimina_odores() {
        return discrimina_odores;
    }
    public int getAceita_texturas() {
        return aceita_texturas;
    }
    public int getPercepcao_formas() {
        return percepcao_formas;
    }
    public int getIdentifica_direcao_sons() {
        return identifica_direcao_sons;
    }
    public int getDiscrimina_sabores() {
        return discrimina_sabores;
    }
    public int getAcompanha_luz() {
        return acompanha_luz;
    }
    public int getMovimento_pinca() {
        return movimento_pinca;
    }
    public int getAmassa_papel() {
        return amassa_papel;
    }
    public int getCai_facilmente() {
        return cai_facilmente;
    }
    public int getEncaixa_pecas() {
        return encaixa_pecas;
    }
    public int getRecorta() {
        return recorta;
    }
    public int getUne_pontos() {
        return une_pontos;
    }
    public int getCorre() {
        return corre;
    }
    public int getEmpilha() {
        return empilha;
    }
    public int getAgitacao_motora() {
        return agitacao_motora;
    }
    public int getAnda_reto() {
        return anda_reto;
    }
    public int getSobe_escada() {
        return sobe_escada;
    }
    public int getArremessa_bola() {
        return arremessa_bola;
    }
    public int getUsa_sanitario() {
        return usa_sanitario;
    }
    public int getPenteia_cabelo() {
        return penteia_cabelo;
    }
    public int getVeste_se() {
        return veste_se;
    }
    public int getLava_maos() {
        return lava_maos;
    }
    public int getBanha_se() {
        return banha_se;
    }
    public int getCalca_se() {
        return calca_se;
    }
    public int getReconhece_roupas() {
        return reconhece_roupas;
    }
    public int getAbre_torneira() {
        return abre_torneira;
    }
    public int getEscova_dentes() {
        return escova_dentes;
    }
    public int getDa_nos() {
        return da_nos;
    }
    public int getAbotoa_roupas() {
        return abotoa_roupas;
    }
    public int getIdentifica_partes_corpo() {
        return identifica_partes_corpo;
    }
    public int getGaratujas() {
        return garatujas;
    }
    public int getSilabico_alfabetico() {
        return silabico_alfabetico;
    }
    public int getAlfabetico() {
        return alfabetico;
    }
    public int getPre_silabico() {
        return pre_silabico;
    }
    public int getSilabico() {
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
    public void setFala_nome(int fala_nome) {
        this.fala_nome = fala_nome;
    }
    public void setFala_nascimento(int fala_nascimento) {
        this.fala_nascimento = fala_nascimento;
    }
    public void setLe_palavras(int le_palavras) {
        this.le_palavras = le_palavras;
    }
    public void setFala_telefone(int fala_telefone) {
        this.fala_telefone = fala_telefone;
    }
    public void setEmite_respostas(int emite_respostas) {
        this.emite_respostas = emite_respostas;
    }
    public void setTransmite_recados(int transmite_recados) {
        this.transmite_recados = transmite_recados;
    }
    public void setFala_endereco(int fala_endereco) {
        this.fala_endereco = fala_endereco;
    }
    public void setFala_nome_pais(int fala_nome_pais) {
        this.fala_nome_pais = fala_nome_pais;
    }
    public void setCompreende_ordens(int compreende_ordens) {
        this.compreende_ordens = compreende_ordens;
    }
    public void setExpoe_ideias(int expoe_ideias) {
        this.expoe_ideias = expoe_ideias;
    }
    public void setReconta_historia(int reconta_historia) {
        this.reconta_historia = reconta_historia;
    }
    public void setUsa_sistema_ca(int usa_sistema_ca) {
        this.usa_sistema_ca = usa_sistema_ca;
    }
    public void setRelata_fatos(int relata_fatos) {
        this.relata_fatos = relata_fatos;
    }
    public void setPronuncia_letras(int pronuncia_letras) {
        this.pronuncia_letras = pronuncia_letras;
    }
    public void setVerbaliza_musicas(int verbaliza_musicas) {
        this.verbaliza_musicas = verbaliza_musicas;
    }
    public void setInterpreta_historias(int interpreta_historias) {
        this.interpreta_historias = interpreta_historias;
    }
    public void setFormula_perguntas(int formula_perguntas) {
        this.formula_perguntas = formula_perguntas;
    }
    public void setUtiliza_gestos(int utiliza_gestos) {
        this.utiliza_gestos = utiliza_gestos;
    }
    public void setDemonstra_cooperacao(int demonstra_cooperacao) {
        this.demonstra_cooperacao = demonstra_cooperacao;
    }
    public void setTimido(int timido) {
        this.timido = timido;
    }
    public void setBirra(int birra) {
        this.birra = birra;
    }
    public void setPede_ajuda(int pede_ajuda) {
        this.pede_ajuda = pede_ajuda;
    }
    public void setRi(int ri) {
        this.ri = ri;
    }
    public void setCompartilha(int compartilha) {
        this.compartilha = compartilha;
    }
    public void setDemonstra_amor(int demonstra_amor) {
        this.demonstra_amor = demonstra_amor;
    }
    public void setChora(int chora) {
        this.chora = chora;
    }
    public void setInterage(int interage) {
        this.interage = interage;
    }
    public void setDetalhes_gravura(int detalhes_gravura) {
        this.detalhes_gravura = detalhes_gravura;
    }
    public void setReconhece_vozes(int reconhece_vozes) {
        this.reconhece_vozes = reconhece_vozes;
    }
    public void setReconhece_cancoes(int reconhece_cancoes) {
        this.reconhece_cancoes = reconhece_cancoes;
    }
    public void setPercebe_texturas(int percebe_texturas) {
        this.percebe_texturas = percebe_texturas;
    }
    public void setPercebe_cores(int percebe_cores) {
        this.percebe_cores = percebe_cores;
    }
    public void setDiscrimina_sons(int discrimina_sons) {
        this.discrimina_sons = discrimina_sons;
    }
    public void setDiscrimina_odores(int discrimina_odores) {
        this.discrimina_odores = discrimina_odores;
    }
    public void setAceita_texturas(int aceita_texturas) {
        this.aceita_texturas = aceita_texturas;
    }
    public void setPercepcao_formas(int percepcao_formas) {
        this.percepcao_formas = percepcao_formas;
    }
    public void setIdentifica_direcao_sons(int identifica_direcao_sons) {
        this.identifica_direcao_sons = identifica_direcao_sons;
    }
    public void setDiscrimina_sabores(int discrimina_sabores) {
        this.discrimina_sabores = discrimina_sabores;
    }
    public void setAcompanha_luz(int acompanha_luz) {
        this.acompanha_luz = acompanha_luz;
    }
    public void setMovimento_pinca(int movimento_pinca) {
        this.movimento_pinca = movimento_pinca;
    }
    public void setAmassa_papel(int amassa_papel) {
        this.amassa_papel = amassa_papel;
    }
    public void setCai_facilmente(int cai_facilmente) {
        this.cai_facilmente = cai_facilmente;
    }
    public void setEncaixa_pecas(int encaixa_pecas) {
        this.encaixa_pecas = encaixa_pecas;
    }
    public void setRecorta(int recorta) {
        this.recorta = recorta;
    }
    public void setUne_pontos(int une_pontos) {
        this.une_pontos = une_pontos;
    }
    public void setCorre(int corre) {
        this.corre = corre;
    }
    public void setEmpilha(int empilha) {
        this.empilha = empilha;
    }
    public void setAgitacao_motora(int agitacao_motora) {
        this.agitacao_motora = agitacao_motora;
    }
    public void setAnda_reto(int anda_reto) {
        this.anda_reto = anda_reto;
    }
    public void setSobe_escada(int sobe_escada) {
        this.sobe_escada = sobe_escada;
    }
    public void setArremessa_bola(int arremessa_bola) {
        this.arremessa_bola = arremessa_bola;
    }
    public void setUsa_sanitario(int usa_sanitario) {
        this.usa_sanitario = usa_sanitario;
    }
    public void setPenteia_cabelo(int penteia_cabelo) {
        this.penteia_cabelo = penteia_cabelo;
    }
    public void setVeste_se(int veste_se) {
        this.veste_se = veste_se;
    }
    public void setLava_maos(int lava_maos) {
        this.lava_maos = lava_maos;
    }
    public void setBanha_se(int banha_se) {
        this.banha_se = banha_se;
    }
    public void setCalca_se(int calca_se) {
        this.calca_se = calca_se;
    }
    public void setReconhece_roupas(int reconhece_roupas) {
        this.reconhece_roupas = reconhece_roupas;
    }
    public void setAbre_torneira(int abre_torneira) {
        this.abre_torneira = abre_torneira;
    }
    public void setEscova_dentes(int escova_dentes) {
        this.escova_dentes = escova_dentes;
    }
    public void setDa_nos(int da_nos) {
        this.da_nos = da_nos;
    }
    public void setAbotoa_roupas(int abotoa_roupas) {
        this.abotoa_roupas = abotoa_roupas;
    }
    public void setIdentifica_partes_corpo(int identifica_partes_corpo) {
        this.identifica_partes_corpo = identifica_partes_corpo;
    }
    public void setGaratujas(int garatujas) {
        this.garatujas = garatujas;
    }
    public void setSilabico_alfabetico(int silabico_alfabetico) {
        this.silabico_alfabetico = silabico_alfabetico;
    }
    public void setAlfabetico(int alfabetico) {
        this.alfabetico = alfabetico;
    }
    public void setPre_silabico(int pre_silabico) {
        this.pre_silabico = pre_silabico;
    }
    public void setSilabico(int silabico) {
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
