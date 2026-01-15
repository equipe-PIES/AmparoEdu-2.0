package br.com.amparoedu.backend.builder;

import br.com.amparoedu.backend.model.Anamnese;

// Builder para Anamnese
public class AnamneseBuilder {
    private Anamnese documento = new Anamnese();

    public AnamneseBuilder() {}

    public AnamneseBuilder(Anamnese base) {
        this.documento = (base != null) ? base : new Anamnese();
    }

    // Identificadores
    public AnamneseBuilder comId(String id) {
        documento.setId(id);
        return this;
    }

    public AnamneseBuilder comEducandoId(String educandoId) {
        documento.setEducando_id(educandoId);
        return this;
    }

    public AnamneseBuilder comProfessorId(String professorId) {
        documento.setProfessor_id(professorId);
        return this;
    }

    public AnamneseBuilder comDataCriacao(String dataCriacao) {
        documento.setData_criacao(dataCriacao);
        return this;
    }

    // Tela 1
    public AnamneseBuilder comTemConvulsao(String valor) {
        documento.setTem_convulsao(valor);
        return this;
    }

    public AnamneseBuilder comTemConvenioMedico(String valor) {
        documento.setTem_convenio_medico(valor);
        return this;
    }

    public AnamneseBuilder comNomeConvenio(String valor) {
        documento.setNome_convenio(valor);
        return this;
    }

    public AnamneseBuilder comVacinasEmDia(String valor) {
        documento.setVacinas_em_dia(valor);
        return this;
    }

    public AnamneseBuilder comTeveDoencaContagiosa(String valor) {
        documento.setTeve_doenca_contagiosa(valor);
        return this;
    }

    public AnamneseBuilder comQuaisDoencas(String valor) {
        documento.setQuais_doencas(valor);
        return this;
    }

    public AnamneseBuilder comUsaMedicacao(String valor) {
        documento.setUsa_medicacao(valor);
        return this;
    }

    public AnamneseBuilder comQuaisMedicacoes(String valor) {
        documento.setQuais_medicacoes(valor);
        return this;
    }

    public AnamneseBuilder comUsouServicoSaudeEducacao(String valor) {
        documento.setUsou_servico_saude_educacao(valor);
        return this;
    }

    public AnamneseBuilder comQuaisServicos(String valor) {
        documento.setQuais_servicos(valor);
        return this;
    }

    public AnamneseBuilder comInicioEscolarizacao(String valor) {
        documento.setInicio_escolarizacao(valor);
        return this;
    }

    public AnamneseBuilder comApresentaDificuldades(String valor) {
        documento.setApresenta_dificuldades(valor);
        return this;
    }

    public AnamneseBuilder comQuaisDificuldades(String valor) {
        documento.setQuais_dificuldades(valor);
        return this;
    }

    public AnamneseBuilder comRecebeApoioPedagogicoCasa(String valor) {
        documento.setRecebe_apoio_pedagogico_casa(valor);
        return this;
    }

    public AnamneseBuilder comApoioQuem(String valor) {
        documento.setApoio_quem(valor);
        return this;
    }

    public AnamneseBuilder comDuracaoGestacao(String valor) {
        documento.setDuracao_da_gestacao(valor);
        return this;
    }

    public AnamneseBuilder comFezPreNatal(String valor) {
        documento.setFez_prenatal(valor);
        return this;
    }

    public AnamneseBuilder comHouvePrematuridade(String valor) {
        documento.setHouve_prematuridade(valor);
        return this;
    }

    public AnamneseBuilder comCausaPrematuridade(String valor) {
        documento.setCausa_prematuridade(valor);
        return this;
    }

    // Tela 2
    public AnamneseBuilder comCidadeNascimento(String valor) {
        documento.setCidade_nascimento(valor);
        return this;
    }

    public AnamneseBuilder comMaternidade(String valor) {
        documento.setMaternidade(valor);
        return this;
    }

    public AnamneseBuilder comTipoParto(String valor) {
        documento.setTipo_parto(valor);
        return this;
    }

    public AnamneseBuilder comChorouAoNascer(String valor) {
        documento.setChorou_ao_nascer(valor);
        return this;
    }

    public AnamneseBuilder comFicouRoxo(String valor) {
        documento.setFicou_roxo(valor);
        return this;
    }

    public AnamneseBuilder comUsouIncubadora(String valor) {
        documento.setUsou_incubadora(valor);
        return this;
    }

    public AnamneseBuilder comFoiAmamentado(String valor) {
        documento.setFoi_amamentado(valor);
        return this;
    }

    public AnamneseBuilder comSustentouCabeca(String valor) {
        documento.setSustentou_a_cabeca(valor);
        return this;
    }

    public AnamneseBuilder comMesesSustentouCabeca(String valor) {
        documento.setQuantos_meses_sustentou_cabeca(valor);
        return this;
    }

    public AnamneseBuilder comEngatinhou(String valor) {
        documento.setEngatinhou(valor);
        return this;
    }

    public AnamneseBuilder comMesesEngatinhou(String valor) {
        documento.setQuantos_meses_engatinhou(valor);
        return this;
    }

    public AnamneseBuilder comSentou(String valor) {
        documento.setSentou(valor);
        return this;
    }

    public AnamneseBuilder comMesesSentou(String valor) {
        documento.setQuantos_meses_sentou(valor);
        return this;
    }

    public AnamneseBuilder comAndou(String valor) {
        documento.setAndou(valor);
        return this;
    }

    public AnamneseBuilder comMesesAndou(String valor) {
        documento.setQuantos_meses_andou(valor);
        return this;
    }

    public AnamneseBuilder comPrecisouDeTerapia(String valor) {
        documento.setPrecisou_de_terapia(valor);
        return this;
    }

    public AnamneseBuilder comQualMotivoTerapia(String valor) {
        documento.setQual_motivo_terapia(valor);
        return this;
    }

    public AnamneseBuilder comFalou(String valor) {
        documento.setFalou(valor);
        return this;
    }

    public AnamneseBuilder comMesesFalou(String valor) {
        documento.setQuantos_meses_falou(valor);
        return this;
    }

    // Tela 3
    public AnamneseBuilder comMesesBalbuciou(String valor) {
        documento.setQuantos_meses_balbuciou(valor);
        return this;
    }

    public AnamneseBuilder comPrimeirasPalavras(String valor) {
        documento.setQuando_primeiras_palavras(valor);
        return this;
    }

    public AnamneseBuilder comPrimeirasFrases(String valor) {
        documento.setQuando_primeiras_frases(valor);
        return this;
    }

    public AnamneseBuilder comTipoFala(String valor) {
        documento.setFala_natural_inibido(valor);
        return this;
    }

    public AnamneseBuilder comPossuiDisturbio(String valor) {
        documento.setPossui_disturbio(valor);
        return this;
    }

    public AnamneseBuilder comQualDisturbio(String valor) {
        documento.setQual_disturbio(valor);
        return this;
    }

    public AnamneseBuilder comObservacoesAdicionais(String valor) {
        documento.setObservacoes_adicionais(valor);
        return this;
    }

    public AnamneseBuilder comDormeSozinho(String valor) {
        documento.setDorme_sozinho(valor);
        return this;
    }

    public AnamneseBuilder comTemSeuQuarto(String valor) {
        documento.setTem_seu_quarto(valor);
        return this;
    }

    public AnamneseBuilder comSonoCalmoAgitado(String valor) {
        documento.setSono_calmo_agitado(valor);
        return this;
    }

    public AnamneseBuilder comRespeitaRegras(String valor) {
        documento.setRespeita_regras(valor);
        return this;
    }

    public AnamneseBuilder comDesmotivado(String valor) {
        documento.setE_desmotivado(valor);
        return this;
    }

    public AnamneseBuilder comAgressivo(String valor) {
        documento.setE_agressivo(valor);
        return this;
    }

    public AnamneseBuilder comApresentaInquietacao(String valor) {
        documento.setApresenta_inquietacao(valor);
        return this;
    }

    // Flags
    public AnamneseBuilder comSincronizado(int flag) {
        documento.setSincronizado(flag);
        return this;
    }

    public AnamneseBuilder comExcluido(int flag) {
        documento.setExcluido(flag);
        return this;
    }

    public Anamnese build() {
        // Validação final de consistência
        if (documento.getEducando_id() == null) {
            throw new IllegalStateException("Toda Anamnese deve ter um Educando ID");
        }
        return documento;
    }

    public Anamnese buildPartial() {
        return documento;
    }
}
