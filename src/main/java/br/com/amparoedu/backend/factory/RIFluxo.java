package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.backend.service.RIService;
import br.com.amparoedu.controller.RIController;

// Implementação do fluxo de documentos para RI
public class RIFluxo implements DocumentoFluxo<RI> {
    private final RIService service = new RIService();
    
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

    @Override
    public boolean excluir(RI documento) {
        try {
            if (documento == null || documento.getId() == null || documento.getId().isBlank()) {
                throw new Exception("RI sem identificador para exclusão.");
            }
            return service.excluirRI(documento.getId());
        } catch (Exception e) {
            System.err.println("Erro ao excluir RI: " + e.getMessage());
            return false;
        }
    }
}
