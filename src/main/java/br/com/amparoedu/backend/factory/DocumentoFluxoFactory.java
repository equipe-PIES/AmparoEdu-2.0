package br.com.amparoedu.backend.factory;

/* Factory para criar instâncias de DocumentoFluxo baseado no tipo de documento.
Implementa o padrão Factory Method para centralizar a criação de fluxos de documentos. */
public class DocumentoFluxoFactory {
    
    // Cria uma instância de DocumentoFluxo conforme o TipoDocumento fornecido
    public static DocumentoFluxo<?> criar(TipoDocumento tipo) {
        switch (tipo) {
            case ANAMNESE:
                return new AnamneseFluxo();
            case PDI:
                return new PDIFluxo();
            case PAEE:
                return new PAEEFluxo();
            case DI:
                return new DIFluxo();
            case RI:
                return new RIFluxo();
            default:
                throw new IllegalArgumentException("Tipo de documento não suportado: " + tipo);
        }
    }
    
    // Método para criar um fluxo e já configurar educando e turma
    public static DocumentoFluxo<?> criarComContexto(TipoDocumento tipo, String educandoId, String turmaId) {
        DocumentoFluxo<?> fluxo = criar(tipo);
        fluxo.setEducandoId(educandoId);
        if (turmaId != null && !turmaId.isBlank()) {
            fluxo.setTurmaOrigem(turmaId);
        }
        return fluxo;
    }
}
