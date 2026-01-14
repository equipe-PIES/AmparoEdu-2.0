package br.com.amparoedu.backend.model;

public class RI {
    private String id;
    private String educando_id;
    private String professor_id;
    private String data_criacao;
    private String dados_funcionais;
    private String funcionalidade_cognitiva;
    private String alfabetizacao;
    private String adaptacoes_curriculares;
    private String participacao_atividade;
    private String autonomia;
    private String interacao_professora;
    private String atividades_vida_diaria;
    private int sincronizado;
    private int excluido;

    public RI() {
    }

    public RI(String id, String educando_id, String professor_id, String data_criacao,
              String dados_funcionais, String funcionalidade_cognitiva, String alfabetizacao,
              String adaptacoes_curriculares, String participacao_atividade, String autonomia,
              String interacao_professora, String atividades_vida_diaria, int sincronizado, int excluido) {
        this.id = id;
        this.educando_id = educando_id;
        this.professor_id = professor_id;
        this.data_criacao = data_criacao;
        this.dados_funcionais = dados_funcionais;
        this.funcionalidade_cognitiva = funcionalidade_cognitiva;
        this.alfabetizacao = alfabetizacao;
        this.adaptacoes_curriculares = adaptacoes_curriculares;
        this.participacao_atividade = participacao_atividade;
        this.autonomia = autonomia;
        this.interacao_professora = interacao_professora;
        this.atividades_vida_diaria = atividades_vida_diaria;
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
    public String getDados_funcionais() {
        return dados_funcionais;
    }
    public String getFuncionalidade_cognitiva() {
        return funcionalidade_cognitiva;
    }
    public String getAlfabetizacao() {
        return alfabetizacao;
    }
    public String getAdaptacoes_curriculares() {
        return adaptacoes_curriculares;
    }
    public String getParticipacao_atividade() {
        return participacao_atividade;
    }
    public String getAutonomia() {
        return autonomia;
    }
    public String getInteracao_professora() {
        return interacao_professora;
    }
    public String getAtividades_vida_diaria() {
        return atividades_vida_diaria;
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
    public void setDados_funcionais(String dados_funcionais) {
        this.dados_funcionais = dados_funcionais;
    }
    public void setFuncionalidade_cognitiva(String funcionalidade_cognitiva) {
        this.funcionalidade_cognitiva = funcionalidade_cognitiva;
    }
    public void setAlfabetizacao(String alfabetizacao) {
        this.alfabetizacao = alfabetizacao;
    }
    public void setAdaptacoes_curriculares(String adaptacoes_curriculares) {
        this.adaptacoes_curriculares = adaptacoes_curriculares;
    }
    public void setParticipacao_atividade(String participacao_atividade) {
        this.participacao_atividade = participacao_atividade;
    }
    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
    }
    public void setInteracao_professora(String interacao_professora) {
        this.interacao_professora = interacao_professora;
    }
    public void setAtividades_vida_diaria(String atividades_vida_diaria) {
        this.atividades_vida_diaria = atividades_vida_diaria;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
}