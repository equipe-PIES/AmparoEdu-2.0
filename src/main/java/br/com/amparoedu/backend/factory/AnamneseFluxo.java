package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.controller.AnamneseController;

/**
 * Implementação do fluxo de documentos para Anamnese
 */
public class AnamneseFluxo implements DocumentoFluxo<Anamnese> {
    
    @Override
    public void iniciarNovo() {
        AnamneseController.iniciarNovaAnamnese();
    }
    
    @Override
    public void iniciarEdicao(Anamnese documento) {
        AnamneseController.editarAnamneseExistente(documento);
    }
    
    @Override
    public void iniciarVisualizacao(Anamnese documento) {
        AnamneseController.visualizarAnamnese(documento);
    }
    
    @Override
    public void setEducandoId(String educandoId) {
        AnamneseController.setEducandoIdParaAnamnese(educandoId);
    }
    
    @Override
    public void setTurmaOrigem(String turmaId) {
        AnamneseController.setTurmaOrigem(turmaId);
    }
    
    @Override
    public String getPrimeiraTela() {
        return "anamnese-1.fxml";
    }
    
    @Override
    public String getNomeDocumento() {
        return "Anamnese";
    }
}
