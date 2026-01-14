package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.service.PDIService;
import br.com.amparoedu.controller.PDIController;

// Implementação do fluxo de documentos para PDI
public class PDIFluxo implements DocumentoFluxo<PDI> {
    private final PDIService service = new PDIService();
    
    @Override
    public void iniciarNovo() {
        PDIController.iniciarNovoPDI();
    }
    
    @Override
    public void iniciarEdicao(PDI documento) {
        PDIController.editarPDIExistente(documento);
    }
    
    @Override
    public void iniciarVisualizacao(PDI documento) {
        PDIController.visualizarPDI(documento);
    }
    
    @Override
    public void setEducandoId(String educandoId) {
        PDIController.setEducandoIdParaPDI(educandoId);
    }
    
    @Override
    public void setTurmaOrigem(String turmaId) {
        PDIController.setTurmaIdOrigem(turmaId);
    }
    
    @Override
    public String getPrimeiraTela() {
        return "pdi-1.fxml";
    }
    
    @Override
    public String getNomeDocumento() {
        return "PDI";
    }

    @Override
    public boolean excluir(PDI documento) {
        try {
            if (documento == null || documento.getId() == null || documento.getId().isBlank()) {
                throw new Exception("PDI sem identificador para exclusão.");
            }
            return service.excluirPDI(documento.getId());
        } catch (Exception e) {
            System.err.println("Erro ao excluir PDI: " + e.getMessage());
            return false;
        }
    }
}
