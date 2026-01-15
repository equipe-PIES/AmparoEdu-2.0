package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.controller.PAEEController;

// Implementação do fluxo de documentos para PAEE
public class PAEEFluxo implements DocumentoFluxo<PAEE> {
    
    @Override
    public void iniciarNovo() {
        PAEEController.iniciarNovoPAEE();
    }
    
    @Override
    public void iniciarEdicao(PAEE documento) {
        PAEEController.editarPAEEExistente(documento);
    }
    
    @Override
    public void iniciarVisualizacao(PAEE documento) {
        PAEEController.visualizarPAEE(documento);
    }
    
    @Override
    public void setEducandoId(String educandoId) {
        PAEEController.setEducandoIdParaPAEE(educandoId);
    }
    
    @Override
    public void setTurmaOrigem(String turmaId) {
        PAEEController.setTurmaIdOrigem(turmaId);
    }
    
    @Override
    public String getPrimeiraTela() {
        return "paee-1.fxml";
    }
    
    @Override
    public String getNomeDocumento() {
        return "PAEE";
    }
}
