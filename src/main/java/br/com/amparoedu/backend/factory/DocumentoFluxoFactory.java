package br.com.amparoedu.backend.factory;

/**
 * Factory para criar instâncias de DocumentoFluxo baseado no tipo de documento.
 * Implementa o padrão Factory Method para centralizar a criação de fluxos de documentos.
 */
public class DocumentoFluxoFactory {
    
    /**
     * Cria uma instância de DocumentoFluxo apropriada para o tipo de documento especificado
     * 
     * @param tipo O tipo de documento desejado
     * @return Uma instância de DocumentoFluxo para o tipo especificado
     * @throws IllegalArgumentException se o tipo não for suportado
     */
    public static DocumentoFluxo<?> criar(TipoDocumento tipo) {
        switch (tipo) {
            case ANAMNESE:
                return new AnamneseFluxo();
            case PDI:
                return new PDIFluxo();
            case PAEE:
                return new PAEEFluxo();
            case RI:
                return new RIFluxo();
            default:
                throw new IllegalArgumentException("Tipo de documento não suportado: " + tipo);
        }
    }
    
    /**
     * Método de conveniência para criar um fluxo e já configurar educando e turma
     * 
     * @param tipo O tipo de documento desejado
     * @param educandoId ID do educando
     * @param turmaId ID da turma (pode ser null)
     * @return Uma instância de DocumentoFluxo configurada
     */
    public static DocumentoFluxo<?> criarComContexto(TipoDocumento tipo, String educandoId, String turmaId) {
        DocumentoFluxo<?> fluxo = criar(tipo);
        fluxo.setEducandoId(educandoId);
        if (turmaId != null && !turmaId.isBlank()) {
            fluxo.setTurmaOrigem(turmaId);
        }
        return fluxo;
    }
}
