package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.controller.PDIController;

/**
 * Implementação do fluxo de documentos para PDI
 */
public class PDIFluxo implements DocumentoFluxo<PDI> {
    
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
}
