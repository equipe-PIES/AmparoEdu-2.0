package br.com.amparoedu.backend.model;

public class Anamnese {
    private String id;
    private String educando_id;
    private String professor_id;
    private String data_criacao;
    private String tem_convulsao;
    private String tem_convenio_medico;
    private String nome_convenio;
    private String vacinas_em_dia;
    private String teve_doenca_contagiosa;
    private String quais_doencas;
    private String usa_medicacao;
    private String quais_medicacoes;
    private String usou_servico_saude_educacao;
    private String quais_servicos;
    private String inicio_escolarizacao;
    private String apresenta_dificuldades;
    private String quais_dificuldades;
    private String recebe_apoio_pedagogico_casa;
    private String apoio_quem;
    private String duracao_da_gestacao;
    private String fez_prenatal;
    private String houve_prematuridade;
    private String causa_prematuridade;
    private String cidade_nascimento;
    private String maternidade;
    private String tipo_parto;
    private String chorou_ao_nascer;
    private String ficou_roxo;
    private String usou_incubadora;
    private String foi_amamentado;
    private String sustentou_a_cabeca;
    private String quantos_meses_sustentou_cabeca;
    private String engatinhou;
    private String quantos_meses_engatinhou;
    private String sentou;
    private String quantos_meses_sentou;
    private String andou;
    private String quantos_meses_andou;
    private String precisou_de_terapia;
    private String qual_motivo_terapia;
    private String falou;
    private String quantos_meses_falou;
    private String quantos_meses_balbuciou;
    private String quando_primeiras_palavras;
    private String quando_primeiras_frases;
    private String fala_natural_inibido;
    private String possui_disturbio;
    private String qual_disturbio;
    private String observacoes_adicionais;
    private String dorme_sozinho;
    private String tem_seu_quarto;
    private String sono_calmo_agitado;
    private String respeita_regras;
    private String e_desmotivado;
    private String e_agressivo;
    private String apresenta_inquietacao;
    private int sincronizado;
    private int excluido;

    public Anamnese() {
        // Inicializa valores padrão para campos NOT NULL
        this.nome_convenio = "";
        this.quais_doencas = "";
        this.quais_medicacoes = "";
        this.quais_servicos = "";
        this.inicio_escolarizacao = "";
        this.quais_dificuldades = "";
        this.apoio_quem = "";
        this.duracao_da_gestacao = "";
        this.causa_prematuridade = "";
        this.cidade_nascimento = "";
        this.maternidade = "";
        this.tipo_parto = "";
        this.quantos_meses_sustentou_cabeca = "";
        this.quantos_meses_engatinhou = "";
        this.quantos_meses_sentou = "";
        this.quantos_meses_andou = "";
        this.qual_motivo_terapia = "";
        this.quantos_meses_falou = "";
        this.quantos_meses_balbuciou = "";
        this.quando_primeiras_palavras = "";
        this.quando_primeiras_frases = "";
        this.fala_natural_inibido = "";
        this.qual_disturbio = "";
        this.observacoes_adicionais = "";
        this.sono_calmo_agitado = "";
        this.sincronizado = 0;
        this.excluido = 0;
    }

    public Anamnese(String id, String educando_id, String professor_id, String data_criacao, String tem_convulsao, String tem_convenio_medico, String nome_convenio, String vacinas_em_dia, String teve_doenca_contagiosa, String quais_doencas, String usa_medicacao, String quais_medicacoes, String usou_servico_saude_educacao, String quais_servicos, String inicio_escolarizacao, String apresenta_dificuldades, String quais_dificuldades, String recebe_apoio_pedagogico_casa, String apoio_quem, String duracao_da_gestacao, String fez_prenatal, String houve_prematuridade, String causa_prematuridade, String cidade_nascimento, String maternidade, String tipo_parto, String chorou_ao_nascer, String ficou_roxo, String usou_incubadora, String foi_amamentado, String sustentou_a_cabeca, String quantos_meses_sustentou_cabeca, String engatinhou, String quantos_meses_engatinhou, String sentou, String quantos_meses_sentou, String andou, String quantos_meses_andou, String precisou_de_terapia, String qual_motivo_terapia, String falou, String quantos_meses_falou, String quantos_meses_balbuciou, String quando_primeiras_palavras, String quando_primeiras_frases, String fala_natural_inibido, String possui_disturbio, String qual_disturbio, String observacoes_adicionais, String dorme_sozinho, String tem_seu_quarto, String sono_calmo_agitado, String respeita_regras, String e_desmotivado, String e_agressivo, String apresenta_inquietacao, int sincronizado, int excluido) {
        this.id = id;
        this.educando_id = educando_id;
        this.professor_id = professor_id;
        this.data_criacao = data_criacao;
        this.tem_convulsao = tem_convulsao;
        this.tem_convenio_medico = tem_convenio_medico;
        this.nome_convenio = nome_convenio;
        this.vacinas_em_dia = vacinas_em_dia;
        this.teve_doenca_contagiosa = teve_doenca_contagiosa;
        this.quais_doencas = quais_doencas;
        this.usa_medicacao = usa_medicacao;
        this.quais_medicacoes = quais_medicacoes;
        this.usou_servico_saude_educacao = usou_servico_saude_educacao;
        this.quais_servicos = quais_servicos;
        this.inicio_escolarizacao = inicio_escolarizacao;
        this.apresenta_dificuldades = apresenta_dificuldades;
        this.quais_dificuldades = quais_dificuldades;
        this.recebe_apoio_pedagogico_casa = recebe_apoio_pedagogico_casa;
        this.apoio_quem = apoio_quem;
        this.duracao_da_gestacao = duracao_da_gestacao;
        this.fez_prenatal = fez_prenatal;
        this.houve_prematuridade = houve_prematuridade;
        this.causa_prematuridade = causa_prematuridade;
        this.cidade_nascimento = cidade_nascimento;
        this.maternidade = maternidade;
        this.tipo_parto = tipo_parto;
        this.chorou_ao_nascer = chorou_ao_nascer;
        this.ficou_roxo = ficou_roxo;
        this.usou_incubadora = usou_incubadora;
        this.foi_amamentado = foi_amamentado;
        this.sustentou_a_cabeca = sustentou_a_cabeca;
        this.quantos_meses_sustentou_cabeca = quantos_meses_sustentou_cabeca;
        this.engatinhou = engatinhou;
        this.quantos_meses_engatinhou = quantos_meses_engatinhou;
        this.sentou = sentou;
        this.quantos_meses_sentou = quantos_meses_sentou;
        this.andou = andou;
        this.quantos_meses_andou = quantos_meses_andou;
        this.precisou_de_terapia = precisou_de_terapia;
        this.qual_motivo_terapia = qual_motivo_terapia;
        this.falou = falou;
        this.quantos_meses_falou = quantos_meses_falou;
        this.quantos_meses_balbuciou = quantos_meses_balbuciou;
        this.quando_primeiras_palavras = quando_primeiras_palavras;
        this.quando_primeiras_frases = quando_primeiras_frases;
        this.fala_natural_inibido = fala_natural_inibido;
        this.possui_disturbio = possui_disturbio;
        this.qual_disturbio = qual_disturbio;
        this.observacoes_adicionais = observacoes_adicionais;
        this.dorme_sozinho = dorme_sozinho;
        this.tem_seu_quarto = tem_seu_quarto;
        this.sono_calmo_agitado = sono_calmo_agitado;
        this.respeita_regras = respeita_regras;
        this.e_desmotivado = e_desmotivado;
        this.e_agressivo = e_agressivo;
        this.apresenta_inquietacao = apresenta_inquietacao;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

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
    public String getTem_convulsao() {
        return tem_convulsao;
    }
    public String getTem_convenio_medico() {
        return tem_convenio_medico;
    }
    public String getNome_convenio() {
        return nome_convenio;
    }
    public String getVacinas_em_dia() {
        return vacinas_em_dia;
    }
    public String getTeve_doenca_contagiosa() {
        return teve_doenca_contagiosa;
    }
    public String getQuais_doencas() {
        return quais_doencas;
    }
    public String getUsa_medicacao() {
        return usa_medicacao;
    }
    public String getQuais_medicacoes() {
        return quais_medicacoes;
    }
    public String getUsou_servico_saude_educacao() {
        return usou_servico_saude_educacao;
    }
    public String getQuais_servicos() {
        return quais_servicos;
    }
    public String getInicio_escolarizacao() {
        return inicio_escolarizacao;
    }
    public String getApresenta_dificuldades() {
        return apresenta_dificuldades;
    }
    public String getQuais_dificuldades() {
        return quais_dificuldades;
    }
    public String getRecebe_apoio_pedagogico_casa() {
        return recebe_apoio_pedagogico_casa;
    }
    public String getApoio_quem() {
        return apoio_quem;
    }
    public String getDuracao_da_gestacao() {
        return duracao_da_gestacao;
    }
    public String getFez_prenatal() {
        return fez_prenatal;
    }
    public String getHouve_prematuridade() {
        return houve_prematuridade;
    }
    public String getCausa_prematuridade() {
        return causa_prematuridade;
    }
    public String getCidade_nascimento() {
        return cidade_nascimento;
    }
    public String getMaternidade() {
        return maternidade;
    }
    public String getTipo_parto() {
        return tipo_parto;
    }
    public String getChorou_ao_nascer() {
        return chorou_ao_nascer;
    }
    public String getFicou_roxo() {
        return ficou_roxo;
    }
    public String getUsou_incubadora() {
        return usou_incubadora;
    }
    public String getFoi_amamentado() {
        return foi_amamentado;
    }
    public String getSustentou_a_cabeca() {
        return sustentou_a_cabeca;
    }
    public String getQuantos_meses_sustentou_cabeca() {
        return quantos_meses_sustentou_cabeca;
    }
    public String getEngatinhou() {
        return engatinhou;
    }
    public String getQuantos_meses_engatinhou() {
        return quantos_meses_engatinhou;
    }
    public String getSentou() {
        return sentou;
    }
    public String getQuantos_meses_sentou() {
        return quantos_meses_sentou;
    }
    public String getAndou() {
        return andou;
    }
    public String getQuantos_meses_andou() {
        return quantos_meses_andou;
    }
    public String getPrecisou_de_terapia() {
        return precisou_de_terapia;
    }
    public String getQual_motivo_terapia() {
        return qual_motivo_terapia;
    }
    public String getFalou() {
        return falou;
    }
    public String getQuantos_meses_falou() {
        return quantos_meses_falou;
    }
    public String getQuantos_meses_balbuciou() {
        return quantos_meses_balbuciou;
    }
    public String getQuando_primeiras_palavras() {
        return quando_primeiras_palavras;
    }
    public String getQuando_primeiras_frases() {
        return quando_primeiras_frases;
    }
    public String getFala_natural_inibido() {
        return fala_natural_inibido;
    }
    public String getPossui_disturbio() {
        return possui_disturbio;
    }
    public String getQual_disturbio() {
        return qual_disturbio;
    }
    public String getObservacoes_adicionais() {
        return observacoes_adicionais;
    }
    public String getDorme_sozinho() {
        return dorme_sozinho;
    }
    public String getTem_seu_quarto() {
        return tem_seu_quarto;
    }
    public String getSono_calmo_agitado() {
        return sono_calmo_agitado;
    }
    public String getRespeita_regras() {
        return respeita_regras;
    }
    public String getE_desmotivado() {
        return e_desmotivado;
    }
    public String getE_agressivo() {
        return e_agressivo;
    }
    public String getApresenta_inquietacao() {
        return apresenta_inquietacao;
    }
    public int getSincronizado() {
        return sincronizado;
    }
    public int getExcluido() {
        return excluido;
    }

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
    public void setTem_convulsao(String tem_convulsao) {
        this.tem_convulsao = tem_convulsao;
    }
    public void setTem_convenio_medico(String tem_convenio_medico) {
        this.tem_convenio_medico = tem_convenio_medico;
    }
    public void setNome_convenio(String nome_convenio) {
        this.nome_convenio = nome_convenio;
    }
    public void setVacinas_em_dia(String vacinas_em_dia) {
        this.vacinas_em_dia = vacinas_em_dia;
    }
    public void setTeve_doenca_contagiosa(String teve_doenca_contagiosa) {
        this.teve_doenca_contagiosa = teve_doenca_contagiosa;
    }
    public void setQuais_doencas(String quais_doencas) {
        this.quais_doencas = quais_doencas;
    }
    public void setUsa_medicacao(String usa_medicacao) {
        this.usa_medicacao = usa_medicacao;
    }
    public void setQuais_medicacoes(String quais_medicacoes) {
        this.quais_medicacoes = quais_medicacoes;
    }
    public void setUsou_servico_saude_educacao(String usou_servico_saude_educacao) {
        this.usou_servico_saude_educacao = usou_servico_saude_educacao;
    }
    public void setQuais_servicos(String quais_servicos) {
        this.quais_servicos = quais_servicos;
    }
    public void setInicio_escolarizacao(String inicio_escolarizacao) {
        this.inicio_escolarizacao = inicio_escolarizacao;
    }
    public void setApresenta_dificuldades(String apresenta_dificuldades) {
        this.apresenta_dificuldades = apresenta_dificuldades;
    }
    public void setQuais_dificuldades(String quais_dificuldades) {
        this.quais_dificuldades = quais_dificuldades;
    }
    public void setRecebe_apoio_pedagogico_casa(String recebe_apoio_pedagogico_casa) {
        this.recebe_apoio_pedagogico_casa = recebe_apoio_pedagogico_casa;
    }
    public void setApoio_quem(String apoio_quem) {
        this.apoio_quem = apoio_quem;
    }
    public void setDuracao_da_gestacao(String duracao_da_gestacao) {
        this.duracao_da_gestacao = duracao_da_gestacao;
    }
    public void setFez_prenatal(String fez_prenatal) {
        this.fez_prenatal = fez_prenatal;
    }
    public void setHouve_prematuridade(String houve_prematuridade) {
        this.houve_prematuridade = houve_prematuridade;
    }
    public void setCausa_prematuridade(String causa_prematuridade) {
        this.causa_prematuridade = causa_prematuridade;
    }
    public void setCidade_nascimento(String cidade_nascimento) {
        this.cidade_nascimento = cidade_nascimento;
    }
    public void setMaternidade(String maternidade) {
        this.maternidade = maternidade;
    }
    public void setTipo_parto(String tipo_parto) {
        this.tipo_parto = tipo_parto;
    }
    public void setChorou_ao_nascer(String chorou_ao_nascer) {
        this.chorou_ao_nascer = chorou_ao_nascer;
    }
    public void setFicou_roxo(String ficou_roxo) {
        this.ficou_roxo = ficou_roxo;
    }
    public void setUsou_incubadora(String usou_incubadora) {
        this.usou_incubadora = usou_incubadora;
    }
    public void setFoi_amamentado(String foi_amamentado) {
        this.foi_amamentado = foi_amamentado;
    }
    public void setSustentou_a_cabeca(String sustentou_a_cabeca) {
        this.sustentou_a_cabeca = sustentou_a_cabeca;
    }
    public void setQuantos_meses_sustentou_cabeca(String quantos_meses_sustentou_cabeca) {
        this.quantos_meses_sustentou_cabeca = quantos_meses_sustentou_cabeca;
    }
    public void setEngatinhou(String engatinhou) {
        this.engatinhou = engatinhou;
    }
    public void setQuantos_meses_engatinhou(String quantos_meses_engatinhou) {
        this.quantos_meses_engatinhou = quantos_meses_engatinhou;
    }
    public void setSentou(String sentou) {
        this.sentou = sentou;
    }
    public void setQuantos_meses_sentou(String quantos_meses_sentou) {
        this.quantos_meses_sentou = quantos_meses_sentou;
    }
    public void setAndou(String andou) {
        this.andou = andou;
    }
    public void setQuantos_meses_andou(String quantos_meses_andou) {
        this.quantos_meses_andou = quantos_meses_andou;
    }
    public void setPrecisou_de_terapia(String precisou_de_terapia) {
        this.precisou_de_terapia = precisou_de_terapia;
    }
    public void setQual_motivo_terapia(String qual_motivo_terapia) {
        this.qual_motivo_terapia = qual_motivo_terapia;
    }
    public void setFalou(String falou) {
        this.falou = falou;
    }
    public void setQuantos_meses_falou(String quantos_meses_falou) {
        this.quantos_meses_falou = quantos_meses_falou;
    }
    public void setQuantos_meses_balbuciou(String quantos_meses_balbuciou) {
        this.quantos_meses_balbuciou = quantos_meses_balbuciou;
    }
    public void setQuando_primeiras_palavras(String quando_primeiras_palavras) {
        this.quando_primeiras_palavras = quando_primeiras_palavras;
    }
    public void setQuando_primeiras_frases(String quando_primeiras_frases) {
        this.quando_primeiras_frases = quando_primeiras_frases;
    }
    public void setFala_natural_inibido(String fala_natural_inibido) {
        this.fala_natural_inibido = fala_natural_inibido;
    }
    public void setPossui_disturbio(String possui_disturbio) {
        this.possui_disturbio = possui_disturbio;
    }
    public void setQual_disturbio(String qual_disturbio) {
        this.qual_disturbio = qual_disturbio;
    }
    public void setObservacoes_adicionais(String observacoes_adicionais) {
        this.observacoes_adicionais = observacoes_adicionais;
    }
    public void setDorme_sozinho(String dorme_sozinho) {
        this.dorme_sozinho = dorme_sozinho;
    }
    public void setTem_seu_quarto(String tem_seu_quarto) {
        this.tem_seu_quarto = tem_seu_quarto;
    }
    public void setSono_calmo_agitado(String sono_calmo_agitado) {
        this.sono_calmo_agitado = sono_calmo_agitado;
    }
    public void setRespeita_regras(String respeita_regras) {
        this.respeita_regras = respeita_regras;
    }
    public void setE_desmotivado(String e_desmotivado) {
        this.e_desmotivado = e_desmotivado;
    }
    public void setE_agressivo(String e_agressivo) {
        this.e_agressivo = e_agressivo;
    }
    public void setApresenta_inquietacao(String apresenta_inquietacao) {
        this.apresenta_inquietacao = apresenta_inquietacao;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }

}
