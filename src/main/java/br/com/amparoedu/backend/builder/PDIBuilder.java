package br.com.amparoedu.backend.builder;

import br.com.amparoedu.backend.model.PDI;

public class PDIBuilder {
    private PDI documento = new PDI();

    public PDIBuilder() {
    }

    public PDIBuilder(PDI base) {
        this.documento = (base != null) ? base : new PDI();
    }

    // Identificadores e metadados
    public PDIBuilder comId(String id) {
        documento.setId(id);
        return this;
    }

    public PDIBuilder comEducandoId(String educandoId) {
        documento.setEducandoId(educandoId);
        return this;
    }

    public PDIBuilder comProfessorId(String professorId) {
        documento.setProfessorId(professorId);
        return this;
    }

    public PDIBuilder comDataCriacao(String dataCriacao) {
        documento.setDataCriacao(dataCriacao);
        return this;
    }

    // Tela 1
    public PDIBuilder comPeriodoAee(String periodoAee) {
        documento.setPeriodoAee(periodoAee);
        return this;
    }

    public PDIBuilder comHorarioAtendimento(String horarioAtendimento) {
        documento.setHorarioAtendimento(horarioAtendimento);
        return this;
    }

    public PDIBuilder comFrequenciaAtendimento(String frequenciaAtendimento) {
        documento.setFrequenciaAtendimento(frequenciaAtendimento);
        return this;
    }

    public PDIBuilder comDiasAtendimento(String diasAtendimento) {
        documento.setDiasAtendimento(diasAtendimento);
        return this;
    }

    public PDIBuilder comComposicaoGrupo(String composicaoGrupo) {
        documento.setComposicaoGrupo(composicaoGrupo);
        return this;
    }

    public PDIBuilder comObjetivos(String objetivos) {
        documento.setObjetivos(objetivos);
        return this;
    }

    // Tela 2
    public PDIBuilder comPotencialidades(String potencialidades) {
        documento.setPotencialidades(potencialidades);
        return this;
    }

    public PDIBuilder comNecessidadesEspeciais(String necessidadesEspeciais) {
        documento.setNecessidadesEspeciais(necessidadesEspeciais);
        return this;
    }

    public PDIBuilder comHabilidades(String habilidades) {
        documento.setHabilidades(habilidades);
        return this;
    }

    // Tela 3
    public PDIBuilder comAtividades(String atividades) {
        documento.setAtividades(atividades);
        return this;
    }

    public PDIBuilder comRecursosMateriais(String recursosMateriais) {
        documento.setRecursosMateriais(recursosMateriais);
        return this;
    }

    public PDIBuilder comRecursosNecessitamAdaptacao(String recursosNecessitamAdaptacao) {
        documento.setRecursosNecessitamAdaptacao(recursosNecessitamAdaptacao);
        return this;
    }

    // Tela 4
    public PDIBuilder comRecursosNecessitamProduzir(String recursosNecessitamProduzir) {
        documento.setRecursosNecessitamProduzir(recursosNecessitamProduzir);
        return this;
    }

    public PDIBuilder comParceriasNecessarias(String parceriasNecessarias) {
        documento.setParceriasNecessarias(parceriasNecessarias);
        return this;
    }

    // Flags
    public PDIBuilder comSincronizado(int flag) {
        documento.setSincronizado(flag);
        return this;
    }

    public PDIBuilder comExcluido(int flag) {
        documento.setExcluido(flag);
        return this;
    }

    public PDI build() {
        if (documento.getEducandoId() == null) {
            throw new IllegalStateException("Todo PDI deve ter um Educando ID");
        }
        return documento;
    }

    public PDI buildPartial() {
        return documento;
    }
}
