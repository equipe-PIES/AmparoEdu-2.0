package br.com.amparoedu.backend.builder;
import br.com.amparoedu.backend.model.PAEE;

public class PAEEBuilder {
    private PAEE documento = new PAEE();

    public PAEEBuilder() {}

    public PAEEBuilder(PAEE base) {
        this.documento = (base != null) ? base : new PAEE();
    }

    // Identificadores e metadados
    public PAEEBuilder comId(String id) {
        documento.setId(id);
        return this;
    }

    public PAEEBuilder comEducandoId(String educandoId) {
        documento.setEducando_id(educandoId);
        return this;
    }

    public PAEEBuilder comProfessorId(String professorId) {
        documento.setProfessor_id(professorId);
        return this;
    }

    public PAEEBuilder comDataCriacao(String dataCriacao) {
        documento.setData_criacao(dataCriacao);
        return this;
    }

    // Dificuldades gerais

    public PAEEBuilder comDificuldadesMotoras(String valor) {
        documento.setDificuldades_motoras(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesCognitivas(String valor) {
        documento.setDificuldades_cognitivas(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesSensoriais(String valor) {
        documento.setDificuldades_sensoriais(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesComunicacao(String valor) {
        documento.setDificuldades_comunicacao(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesFamiliares(String valor) {
        documento.setDificuldades_familiares(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesAfetivas(String valor) {
        documento.setDificuldades_afetivas(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesRaciocinio(String valor) {
        documento.setDificuldades_raciocinio(valor);
        return this;
    }

    public PAEEBuilder comDificuldadesAvas(String valor) {
        documento.setDificuldades_avas(valor);
        return this;
    }

    // Dimensão motora
    public PAEEBuilder comDifDesMotor(String valor) {
        documento.setDif_des_motor(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesMotor(String valor) {
        documento.setIntervencoes_motor(valor);
        return this;
    }

    // Dimensão comunicação
    public PAEEBuilder comDifComunicacao(String valor) {
        documento.setDif_comunicacao(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesComunicacao(String valor) {
        documento.setIntervencoes_comunicacao(valor);
        return this;
    }

    // Dimensão raciocínio
    public PAEEBuilder comDifRaciocinio(String valor) {
        documento.setDif_raciocinio(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesRaciocinio(String valor) {
        documento.setIntervencoes_raciocinio(valor);
        return this;
    }

    // Dimensão atenção
    public PAEEBuilder comDifAtencao(String valor) {
        documento.setDif_atencao(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesAtencao(String valor) {
        documento.setIntervencoes_atencao(valor);
        return this;
    }

    // Dimensão memória
    public PAEEBuilder comDifMemoria(String valor) {
        documento.setDif_memoria(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesMemoria(String valor) {
        documento.setIntervencoes_memoria(valor);
        return this;
    }

    // Dimensão percepção
    public PAEEBuilder comDifPercepcao(String valor) {
        documento.setDif_percepcao(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesPercepcao(String valor) {
        documento.setIntervencoes_percepcao(valor);
        return this;
    }

    // Dimensão sociabilidade
    public PAEEBuilder comDifSociabilidade(String valor) {
        documento.setDif_sociabilidade(valor);
        return this;
    }

    public PAEEBuilder comIntervencoesSociabilidade(String valor) {
        documento.setIntervencoes_sociabilidade(valor);
        return this;
    }

    // Objetivos e apoios
    public PAEEBuilder comObjetivoPlano(String valor) {
        documento.setObjetivo_plano(valor);
        return this;
    }

    public PAEEBuilder comAee(String valor) {
        documento.setAee(valor);
        return this;
    }

    public PAEEBuilder comPsicologo(String valor) {
        documento.setPsicologo(valor);
        return this;
    }

    public PAEEBuilder comFisioterapeuta(String valor) {
        documento.setFisioterapeuta(valor);
        return this;
    }

    public PAEEBuilder comPsicopedagogo(String valor) {
        documento.setPsicopedagogo(valor);
        return this;
    }

    public PAEEBuilder comTerapeutaOcupacional(String valor) {
        documento.setTerapeuta_ocupacional(valor);
        return this;
    }

    public PAEEBuilder comEducacaoFisica(String valor) {
        documento.setEducacao_fisica(valor);
        return this;
    }

    public PAEEBuilder comEstimulacaoPrecoce(String valor) {
        documento.setEstimulacao_precoce(valor);
        return this;
    }

    public PAEEBuilder comResumo(String resumo) {
        documento.setResumo(resumo);
        return this;
    }

    // Flags
    public PAEEBuilder comSincronizado(int flag) {
        documento.setSincronizado(flag);
        return this;
    }

    public PAEEBuilder comExcluido(int flag) {
        documento.setExcluido(flag);
        return this;
    }

    public PAEE build() {
        // Validação final de consistência
        if (documento.getEducandoId() == null) {
            throw new IllegalStateException("Todo PAEE deve ter um Educando ID");
        }
        return documento;
    }

    public PAEE buildPartial() {
        return documento;
    }
}