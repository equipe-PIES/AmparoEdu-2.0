package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.service.PAEEService;
import br.com.amparoedu.controller.PAEEController;

// Implementação do fluxo de documentos para PAEE
public class PAEEFluxo implements DocumentoFluxo<PAEE> {
    private final PAEEService service = new PAEEService();
    
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

    @Override
    public boolean excluir(PAEE documento) {
        try {
            if (documento == null || documento.getId() == null || documento.getId().isBlank()) {
                throw new Exception("PAEE sem identificador para exclusão.");
            }
            return service.excluirPAEE(documento.getId());
        } catch (Exception e) {
            System.err.println("Erro ao excluir PAEE: " + e.getMessage());
            return false;
        }
    }
}
