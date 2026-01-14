package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.controller.RIController;

/**
 * Implementação do fluxo de documentos para RI (Relatório Individual)
 */
public class RIFluxo implements DocumentoFluxo<RI> {
    
    @Override
    public void iniciarNovo() {
        RIController.iniciarNovoRI();
    }
    
    @Override
    public void iniciarEdicao(RI documento) {
        RIController.editarRIExistente(documento);
    }
    
    @Override
    public void iniciarVisualizacao(RI documento) {
        RIController.visualizarRI(documento);
    }
    
    @Override
    public void setEducandoId(String educandoId) {
        RIController.setEducandoIdParaRI(educandoId);
    }
    
    @Override
    public void setTurmaOrigem(String turmaId) {
        RIController.setTurmaIdOrigem(turmaId);
    }
    
    @Override
    public String getPrimeiraTela() {
        return "relatorio-individual-1.fxml";
    }
    
    @Override
    public String getNomeDocumento() {
        return "Relatório Individual";
    }
}
