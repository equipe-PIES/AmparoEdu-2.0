package br.com.amparoedu.backend.factory;

/* Interface que define o contrato para operações de fluxo de documentos (PDI, Anamnese, PAEE, DI, RI).
Implementa o padrão Factory para centralizar a lógica de criar, editar e visualizar documentos. */
public interface DocumentoFluxo<T> {
    
    // Inicia o fluxo para criação de um novo documento
    void iniciarNovo();
    
    // Inicia o fluxo para edição de um documento existente
    void iniciarEdicao(T documento);
    
    // Inicia o fluxo para visualização de um documento existente
    void iniciarVisualizacao(T documento);
    
    // Define o educando associado ao documento
    void setEducandoId(String educandoId);
    
    // Define a turma de origem do documento
    void setTurmaOrigem(String turmaId);
    
    // Retorna o nome do arquivo FXML da primeira tela do fluxo
    String getPrimeiraTela();
    
    // Retorna o nome do tipo de documento (ex: "PDI", "Anamnese", etc.)
    String getNomeDocumento();

    // Exclui o documento informado, retornando sucesso/falha.
    boolean excluir(T documento);
}
