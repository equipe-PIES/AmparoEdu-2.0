package br.com.amparoedu.backend.builder;

import br.com.amparoedu.backend.model.RI;

// Builder para RI
public class RIBuilder {
    private RI documento = new RI();

    public RIBuilder() {
    }

    public RIBuilder(RI base) {
        this.documento = (base != null) ? base : new RI();
    }

    // Identificadores
    public RIBuilder comId(String id) {
        documento.setId(id);
        return this;
    }

    public RIBuilder comEducandoId(String educandoId) {
        documento.setEducando_id(educandoId);
        return this;
    }

    public RIBuilder comProfessorId(String professorId) {
        documento.setProfessor_id(professorId);
        return this;
    }

    public RIBuilder comDataCriacao(String dataCriacao) {
        documento.setData_criacao(dataCriacao);
        return this;
    }

    // Campos de conteúdo
    public RIBuilder comDadosFuncionais(String dados) {
        documento.setDados_funcionais(dados);
        return this;
    }

    public RIBuilder comFuncionalidadeCognitiva(String funcionalidade) {
        documento.setFuncionalidade_cognitiva(funcionalidade);
        return this;
    }

    public RIBuilder comAlfabetizacao(String alfabetizacao) {
        documento.setAlfabetizacao(alfabetizacao);
        return this;
    }

    public RIBuilder comAdaptacoesCurriculares(String adaptacoes) {
        documento.setAdaptacoes_curriculares(adaptacoes);
        return this;
    }

    public RIBuilder comParticipacaoAtividade(String participacao) {
        documento.setParticipacao_atividade(participacao);
        return this;
    }

    public RIBuilder comAutonomia(String autonomia) {
        documento.setAutonomia(autonomia);
        return this;
    }

    public RIBuilder comInteracaoProfessora(String interacao) {
        documento.setInteracao_professora(interacao);
        return this;
    }

    public RIBuilder comAtividadesVidaDiaria(String atividades) {
        documento.setAtividades_vida_diaria(atividades);
        return this;
    }

    // Flags
    public RIBuilder comSincronizado(int flag) {
        documento.setSincronizado(flag);
        return this;
    }

    public RIBuilder comExcluido(int flag) {
        documento.setExcluido(flag);
        return this;
    }

    // Constroi o RI com validacao completa.
    public RI build() {
        if (documento.getEducando_id() == null) {
            throw new IllegalStateException("Todo RI deve ter um Educando ID");
        }
        return documento;
    }

    // Constroi o RI sem validacao completa.
    public RI buildPartial() {
        return documento;
    }
}
