package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.service.AnamneseService;
import br.com.amparoedu.controller.AnamneseController;

// Implementação do fluxo de documentos para Anamnese
public class AnamneseFluxo implements DocumentoFluxo<Anamnese> {
    private final AnamneseService service = new AnamneseService();
    
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

    @Override
    public boolean excluir(Anamnese documento) {
        try {
            if (documento == null || documento.getId() == null || documento.getId().isBlank()) {
                throw new Exception("Anamnese sem identificador para exclusão.");
            }
            return service.excluirAnamnese(documento.getId());
        } catch (Exception e) {
            System.err.println("Erro ao excluir Anamnese: " + e.getMessage());
            return false;
        }
    }
}
