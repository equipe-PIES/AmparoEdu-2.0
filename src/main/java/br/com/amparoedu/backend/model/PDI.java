package br.com.amparoedu.backend.model;

public class PDI {
    private String id;
    private String educandoId;
    private String professorId;
    private String dataCriacao;
    private String periodoAee;
    private String horarioAtendimento;
    private String frequenciaAtendimento;
    private String diasAtendimento;
    private String composicaoGrupo;
    private String objetivos;
    private String potencialidades;
    private String necessidadesEspeciais;
    private String habilidades;
    private String atividades;
    private String recursosMateriais;
    private String recursosNecessitamAdaptacao;
    private String recursosNecessitamProduzir;
    private String parceriasNecessarias;
    private int sincronizado;
    private int excluido;

    public PDI() {
    }

    public PDI(String id, String educandoId, String professorId, String dataCriacao, String periodoAee,
               String horarioAtendimento, String frequenciaAtendimento, String diasAtendimento,
               String composicaoGrupo, String objetivos, String potencialidades, String necessidadesEspeciais,
               String habilidades, String atividades, String recursosMateriais,
               String recursosNecessitamAdaptacao, String recursosNecessitamProduzir,
               String parceriasNecessarias, int sincronizado, int excluido) {
        this.id = id;
        this.educandoId = educandoId;
        this.professorId = professorId;
        this.dataCriacao = dataCriacao;
        this.periodoAee = periodoAee;
        this.horarioAtendimento = horarioAtendimento;
        this.frequenciaAtendimento = frequenciaAtendimento;
        this.diasAtendimento = diasAtendimento;
        this.composicaoGrupo = composicaoGrupo;
        this.objetivos = objetivos;
        this.potencialidades = potencialidades;
        this.necessidadesEspeciais = necessidadesEspeciais;
        this.habilidades = habilidades;
        this.atividades = atividades;
        this.recursosMateriais = recursosMateriais;
        this.recursosNecessitamAdaptacao = recursosNecessitamAdaptacao;
        this.recursosNecessitamProduzir = recursosNecessitamProduzir;
        this.parceriasNecessarias = parceriasNecessarias;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    // Getters

    public String getId() {
        return id;
    }
    public String getEducandoId() {
        return educandoId;
    }
    public String getProfessorId() {
        return professorId;
    }
    public String getDataCriacao() {
        return dataCriacao;
    }
    public String getPeriodoAee() {
        return periodoAee;
    }
    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }
    public String getFrequenciaAtendimento() {
        return frequenciaAtendimento;
    }
    public String getDiasAtendimento() {
        return diasAtendimento;
    }
    public String getComposicaoGrupo() {
        return composicaoGrupo;
    }
    public String getObjetivos() {
        return objetivos;
    }
    public String getPotencialidades() {
        return potencialidades;
    }
    public String getNecessidadesEspeciais() {
        return necessidadesEspeciais;
    }
    public String getHabilidades() {
        return habilidades;
    }
    public String getAtividades() {
        return atividades;
    }
    public String getRecursosMateriais() {
        return recursosMateriais;
    }
    public String getRecursosNecessitamAdaptacao() {
        return recursosNecessitamAdaptacao;
    }
    public String getRecursosNecessitamProduzir() {
        return recursosNecessitamProduzir;
    }
    public String getParceriasNecessarias() {
        return parceriasNecessarias;
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
        this.educandoId = educandoId;
    }
    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public void setPeriodoAee(String periodoAee) {
        this.periodoAee = periodoAee;
    }
    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }
    public void setFrequenciaAtendimento(String frequenciaAtendimento) {
        this.frequenciaAtendimento = frequenciaAtendimento;
    }
    public void setDiasAtendimento(String diasAtendimento) {
        this.diasAtendimento = diasAtendimento;
    }
    public void setComposicaoGrupo(String composicaoGrupo) {
        this.composicaoGrupo = composicaoGrupo;
    }
    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }
    public void setPotencialidades(String potencialidades) {
        this.potencialidades = potencialidades;
    }
    public void setNecessidadesEspeciais(String necessidadesEspeciais) {
        this.necessidadesEspeciais = necessidadesEspeciais;
    }
    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }
    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }
    public void setRecursosMateriais(String recursosMateriais) {
        this.recursosMateriais = recursosMateriais;
    }
    public void setRecursosNecessitamAdaptacao(String recursosNecessitamAdaptacao) {
        this.recursosNecessitamAdaptacao = recursosNecessitamAdaptacao;
    }
    public void setRecursosNecessitamProduzir(String recursosNecessitamProduzir) {
        this.recursosNecessitamProduzir = recursosNecessitamProduzir;
    }
    public void setParceriasNecessarias(String parceriasNecessarias) {
        this.parceriasNecessarias = parceriasNecessarias;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
    
}
