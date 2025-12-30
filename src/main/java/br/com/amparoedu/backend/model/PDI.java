package br.com.amparoedu.backend.model;

public class PDI {
    private String id;
    private String educando_id;
    private String professor_id;
    private String data_criacao;
    private String periodo_aee;
    private String horario_atendimento;
    private String frequencia_atendimento;
    private String dias_atendimento;
    private String composicao_grupo;
    private String objetivos;
    private String potencialidades;
    private String necessidades_especiais;
    private String habilidades;
    private String atividades;
    private String recursos_materiais;
    private String recursos_necessitam_adaptacao;
    private String recursos_necessitam_produzir;
    private String parcerias_necessarias;
    private int sincronizado;
    private int excluido;

    public PDI() {
    }

    public PDI(String id, String educando_id, String professor_id, String data_criacao, String periodo_aee,
               String horario_atendimento, String frequencia_atendimento, String dias_atendimento,
               String composicao_grupo, String objetivos, String potencialidades, String necessidades_especiais,
               String habilidades, String atividades, String recursos_materiais,
               String recursos_necessitam_adaptacao, String recursos_necessitam_produzir,
               String parcerias_necessarias, int sincronizado, int excluido) {
        this.id = id;
        this.educando_id = educando_id;
        this.professor_id = professor_id;
        this.data_criacao = data_criacao;
        this.periodo_aee = periodo_aee;
        this.horario_atendimento = horario_atendimento;
        this.frequencia_atendimento = frequencia_atendimento;
        this.dias_atendimento = dias_atendimento;
        this.composicao_grupo = composicao_grupo;
        this.objetivos = objetivos;
        this.potencialidades = potencialidades;
        this.necessidades_especiais = necessidades_especiais;
        this.habilidades = habilidades;
        this.atividades = atividades;
        this.recursos_materiais = recursos_materiais;
        this.recursos_necessitam_adaptacao = recursos_necessitam_adaptacao;
        this.recursos_necessitam_produzir = recursos_necessitam_produzir;
        this.parcerias_necessarias = parcerias_necessarias;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    // Getters

    public String getId() {
        return id;
    }
    public String getEducandoId() {
        return educando_id;
    }
    public String getProfessorId() {
        return professor_id;
    }
    public String getDataCriacao() {
        return data_criacao;
    }
    public String getPeriodoAee() {
        return periodo_aee;
    }
    public String getHorarioAtendimento() {
        return horario_atendimento;
    }
    public String getFrequenciaAtendimento() {
        return frequencia_atendimento;
    }
    public String getDiasAtendimento() {
        return dias_atendimento;
    }
    public String getComposicaoGrupo() {
        return composicao_grupo;
    }
    public String getObjetivos() {
        return objetivos;
    }
    public String getPotencialidades() {
        return potencialidades;
    }
    public String getNecessidadesEspeciais() {
        return necessidades_especiais;
    }
    public String getHabilidades() {
        return habilidades;
    }
    public String getAtividades() {
        return atividades;
    }
    public String getRecursosMateriais() {
        return recursos_materiais;
    }
    public String getRecursosNecessitamAdaptacao() {
        return recursos_necessitam_adaptacao;
    }
    public String getRecursosNecessitamProduzir() {
        return recursos_necessitam_produzir;
    }
    public String getParceriasNecessarias() {
        return parcerias_necessarias;
    }
    public int getSincronizado() {
        return sincronizado;
    }
    public int getExcluido() {
        return excluido;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }
    public void setEducandoId(String educandoId) {
        this.educando_id = educandoId;
    }
    public void setProfessorId(String professorId) {
        this.professor_id = professorId;
    }
    public void setDataCriacao(String dataCriacao) {
        this.data_criacao = dataCriacao;
    }
    public void setPeriodoAee(String periodoAee) {
        this.periodo_aee = periodoAee;
    }
    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horario_atendimento = horarioAtendimento;
    }
    public void setFrequenciaAtendimento(String frequenciaAtendimento) {
        this.frequencia_atendimento = frequenciaAtendimento;
    }
    public void setDiasAtendimento(String diasAtendimento) {
        this.dias_atendimento = diasAtendimento;
    }
    public void setComposicaoGrupo(String composicaoGrupo) {
        this.composicao_grupo = composicaoGrupo;
    }
    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }
    public void setPotencialidades(String potencialidades) {
        this.potencialidades = potencialidades;
    }
    public void setNecessidadesEspeciais(String necessidadesEspeciais) {
        this.necessidades_especiais = necessidadesEspeciais;
    }
    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }
    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }
    public void setRecursosMateriais(String recursosMateriais) {
        this.recursos_materiais = recursosMateriais;
    }
    public void setRecursosNecessitamAdaptacao(String recursosNecessitamAdaptacao) {
        this.recursos_necessitam_adaptacao = recursosNecessitamAdaptacao;
    }
    public void setRecursosNecessitamProduzir(String recursosNecessitamProduzir) {
        this.recursos_necessitam_produzir = recursosNecessitamProduzir;
    }
    public void setParceriasNecessarias(String parceriasNecessarias) {
        this.parcerias_necessarias = parceriasNecessarias;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
    
}
