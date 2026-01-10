package br.com.amparoedu.backend.model;

public class PAEE {
    private String id;
    private String educando_id;
    private String professor_id;
    private String data_criacao;
    private String resumo;
    private int dificuldades_motoras;
    private int dificuldades_cognitivas;
    private int dificuldades_sensoriais;
    private int dificuldades_comunicacao;
    private int dificuldades_familiares;
    private int dificuldades_afetivas;
    private int dificuldades_raciocinio;
    private int dificuldades_avas;
    private String dif_des_motor;
    private String intervencoes_motor;
    private String dif_comunicacao;
    private String intervencoes_comunicacao;
    private String dif_raciocinio;
    private String intervencoes_raciocinio;
    private String dif_atencao;
    private String intervencoes_atencao;
    private String dif_memoria;
    private String intervencoes_memoria;
    private String dif_percepcao;
    private String intervencoes_percepcao;
    private String dif_sociabilidade;
    private String intervencoes_sociabilidade;
    private String objetivo_plano;
    private int aee;
    private int psicologo;
    private int fisioterapeuta;
    private int psicopedagogo;
    private int terapeuta_ocupacional;
    private int educacao_fisica;
    private int estimulacao_precoce;
    private int sincronizado;
    private int excluido;

    public PAEE() {
    }

    public PAEE(String id, String educando_id, String professor_id, String data_criacao, String resumo,
                int dificuldades_motoras, int dificuldades_cognitivas, int dificuldades_sensoriais,
                int dificuldades_comunicacao, int dificuldades_familiares, int dificuldades_afetivas,
                int dificuldades_raciocinio, int dificuldades_avas, String dif_des_motor,
                String intervencoes_motor, String dif_comunicacao, String intervencoes_comunicacao,
                String dif_raciocinio, String intervencoes_raciocinio, String dif_atencao,
                String intervencoes_atencao, String dif_memoria, String intervencoes_memoria,
                String dif_percepcao, String intervencoes_percepcao, String dif_sociabilidade,
                String intervencoes_sociabilidade, String objetivo_plano, int aee, int psicologo,
                int fisioterapeuta, int psicopedagogo, int terapeuta_ocupacional,
                int educacao_fisica, int estimulacao_precoce, int sincronizado, int excluido) {
        this.id = id;
        this.educando_id = educando_id;
        this.professor_id = professor_id;
        this.data_criacao = data_criacao;
        this.resumo = resumo;
        this.dificuldades_motoras = dificuldades_motoras;
        this.dificuldades_cognitivas = dificuldades_cognitivas;
        this.dificuldades_sensoriais = dificuldades_sensoriais;
        this.dificuldades_comunicacao = dificuldades_comunicacao;
        this.dificuldades_familiares = dificuldades_familiares;
        this.dificuldades_afetivas = dificuldades_afetivas;
        this.dificuldades_raciocinio = dificuldades_raciocinio;
        this.dificuldades_avas = dificuldades_avas;
        this.dif_des_motor = dif_des_motor;
        this.intervencoes_motor = intervencoes_motor;
        this.dif_comunicacao = dif_comunicacao;
        this.intervencoes_comunicacao = intervencoes_comunicacao;
        this.dif_raciocinio = dif_raciocinio;
        this.intervencoes_raciocinio = intervencoes_raciocinio;
        this.dif_atencao = dif_atencao;
        this.intervencoes_atencao = intervencoes_atencao;
        this.dif_memoria = dif_memoria;
        this.intervencoes_memoria = intervencoes_memoria;
        this.dif_percepcao = dif_percepcao;
        this.intervencoes_percepcao = intervencoes_percepcao;
        this.dif_sociabilidade = dif_sociabilidade;
        this.intervencoes_sociabilidade = intervencoes_sociabilidade;
        this.objetivo_plano = objetivo_plano;
        this.aee = aee;
        this.psicologo = psicologo;
        this.fisioterapeuta = fisioterapeuta;
        this.psicopedagogo = psicopedagogo;
        this.terapeuta_ocupacional = terapeuta_ocupacional;
        this.educacao_fisica = educacao_fisica;
        this.estimulacao_precoce = estimulacao_precoce;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    // getters
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
    public String getResumo() {
        return resumo;
    }
    public int getDificuldadesMotoras() {
        return dificuldades_motoras;
    }
    public int getDificuldadesCognitivas() {
        return dificuldades_cognitivas;
    }
    public int getDificuldadesSensoriais() {
        return dificuldades_sensoriais;
    }
    public int getDificuldadesComunicacao() {
        return dificuldades_comunicacao;
    }
    public int getDificuldadesFamiliares() {
        return dificuldades_familiares;
    }
    public int getDificuldadesAfetivas() {
        return dificuldades_afetivas;
    }
    public int getDificuldadesRaciocinio() {
        return dificuldades_raciocinio;
    }
    public int getDificuldadesAvas() {
        return dificuldades_avas;
    }
    public String getDifDesMotor() {
        return dif_des_motor;
    }
    public String getIntervencoesMotor() {
        return intervencoes_motor;
    }
    public String getDifComunicacao() {
        return dif_comunicacao;
    }
    public String getIntervencoesComunicacao() {
        return intervencoes_comunicacao;
    }
    public String getDifRaciocinio() {
        return dif_raciocinio;
    }
    public String getIntervencoesRaciocinio() {
        return intervencoes_raciocinio;
    }
    public String getDifAtencao() {
        return dif_atencao;
    }
    public String getIntervencoesAtencao() {
        return intervencoes_atencao;
    }
    public String getDifMemoria() {
        return dif_memoria;
    }
    public String getIntervencoesMemoria() {
        return intervencoes_memoria;
    }
    public String getDifPercepcao() {
        return dif_percepcao;
    }
    public String getIntervencoesPercepcao() {
        return intervencoes_percepcao;
    }
    public String getDifSociabilidade() {
        return dif_sociabilidade;
    }
    public String getIntervencoesSociabilidade() {
        return intervencoes_sociabilidade;
    }
    public String getObjetivoPlano() {
        return objetivo_plano;
    }
    public int getAee() {
        return aee;
    }
    public int getPsicologo() {
        return psicologo;
    }
    public int getFisioterapeuta() {
        return fisioterapeuta;
    }
    public int getPsicopedagogo() {
        return psicopedagogo;
    }
    public int getTerapeutaOcupacional() {
        return terapeuta_ocupacional;
    }
    public int getEducacaoFisica() {
        return educacao_fisica;
    }
    public int getEstimulacaoPrecoce() {
        return estimulacao_precoce;
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
    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
    public void setDificuldades_motoras(int dificuldades_motoras) {
        this.dificuldades_motoras = dificuldades_motoras;
    }
    public void setDificuldades_cognitivas(int dificuldades_cognitivas) {
        this.dificuldades_cognitivas = dificuldades_cognitivas;
    }
    public void setDificuldades_sensoriais(int dificuldades_sensoriais) {
        this.dificuldades_sensoriais = dificuldades_sensoriais;
    }
    public void setDificuldades_comunicacao(int dificuldades_comunicacao) {
        this.dificuldades_comunicacao = dificuldades_comunicacao;
    }
    public void setDificuldades_familiares(int dificuldades_familiares) {
        this.dificuldades_familiares = dificuldades_familiares;         
    }
    public void setDificuldades_afetivas(int dificuldades_afetivas) {
        this.dificuldades_afetivas = dificuldades_afetivas;
    }
    public void setDificuldades_raciocinio(int dificuldades_raciocinio) {
        this.dificuldades_raciocinio = dificuldades_raciocinio;
    }
    public void setDificuldades_avas(int dificuldades_avas) {
        this.dificuldades_avas = dificuldades_avas;
    }
    public void setDif_des_motor(String dif_des_motor) {
        this.dif_des_motor = dif_des_motor;
    }
    public void setIntervencoes_motor(String intervencoes_motor) {
        this.intervencoes_motor = intervencoes_motor;
    }
    public void setDif_comunicacao(String dif_comunicacao) {
        this.dif_comunicacao = dif_comunicacao;
    }
    public void setIntervencoes_comunicacao(String intervencoes_comunicacao) {
        this.intervencoes_comunicacao = intervencoes_comunicacao;
    }
    public void setDif_raciocinio(String dif_raciocinio) {
        this.dif_raciocinio = dif_raciocinio;
    }
    public void setIntervencoes_raciocinio(String intervencoes_raciocinio) {
        this.intervencoes_raciocinio = intervencoes_raciocinio;
    }
    public void setDif_atencao(String dif_atencao) {
        this.dif_atencao = dif_atencao;
    }
    public void setIntervencoes_atencao(String intervencoes_atencao) {
        this.intervencoes_atencao = intervencoes_atencao;
    }
    public void setDif_memoria(String dif_memoria) {
        this.dif_memoria = dif_memoria;
    }
    public void setIntervencoes_memoria(String intervencoes_memoria) {
        this.intervencoes_memoria = intervencoes_memoria;
    }
    public void setDif_percepcao(String dif_percepcao) {
        this.dif_percepcao = dif_percepcao;
    }
    public void setIntervencoes_percepcao(String intervencoes_percepcao) {
        this.intervencoes_percepcao = intervencoes_percepcao;
    }
    public void setDif_sociabilidade(String dif_sociabilidade) {
        this.dif_sociabilidade = dif_sociabilidade;
    }
    public void setIntervencoes_sociabilidade(String intervencoes_sociabilidade) {
        this.intervencoes_sociabilidade = intervencoes_sociabilidade;
    }
    public void setObjetivo_plano(String objetivo_plano) {
        this.objetivo_plano = objetivo_plano;
    }
    public void setAee(int aee) {
        this.aee = aee;
    }
    public void setPsicologo(int psicologo) {
        this.psicologo = psicologo;
    }
    public void setFisioterapeuta(int fisioterapeuta) {
        this.fisioterapeuta = fisioterapeuta;
    }
    public void setPsicopedagogo(int psicopedagogo) {
        this.psicopedagogo = psicopedagogo;
    }
    public void setTerapeuta_ocupacional(int terapeuta_ocupacional) {
        this.terapeuta_ocupacional = terapeuta_ocupacional;
    }
    public void setEducacao_fisica(int educacao_fisica) {
        this.educacao_fisica = educacao_fisica;
    }
    public void setEstimulacao_precoce(int estimulacao_precoce) {
        this.estimulacao_precoce = estimulacao_precoce;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
}
