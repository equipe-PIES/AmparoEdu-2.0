package br.com.amparoedu.backend.factory;

/**
 * Interface que define o contrato para operações de fluxo de documentos (PDI, Anamnese, PAEE, DI, RI).
 * Implementa o padrão Factory para centralizar a lógica de criar, editar e visualizar documentos.
 */
public interface DocumentoFluxo<T> {
    
    /**
     * Inicializa um novo documento
     */
    void iniciarNovo();
    
    /**
     * Inicializa modo de edição com documento existente
     * @param documento O documento a ser editado
     */
    void iniciarEdicao(T documento);
    
    /**
     * Inicializa modo de visualização com documento existente
     * @param documento O documento a ser visualizado
     */
    void iniciarVisualizacao(T documento);
    
    /**
     * Define o ID do educando para o documento
     * @param educandoId ID do educando
     */
    void setEducandoId(String educandoId);
    
    /**
     * Define a turma de origem para navegação
     * @param turmaId ID da turma
     */
    void setTurmaOrigem(String turmaId);
    
    /**
     * Retorna o nome da primeira tela FXML do documento
     * @return Nome do arquivo FXML (ex: "pdi-1.fxml")
     */
    String getPrimeiraTela();
    
    /**
     * Retorna o nome do tipo de documento
     * @return Nome do documento (ex: "PDI", "Anamnese")
     */
    String getNomeDocumento();
}
