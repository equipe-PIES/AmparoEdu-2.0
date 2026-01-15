package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.DI;
import br.com.amparoedu.backend.service.DIService;
import br.com.amparoedu.controller.DIController;

// Implementação do fluxo de documentos para DI
public class DIFluxo implements DocumentoFluxo<DI> {
    private final DIService service = new DIService();

    @Override
    public void iniciarNovo() {
        DIController.iniciarNovoDI();
    }

    @Override
    public void iniciarEdicao(DI documento) {
        DIController.editarDIExistente(documento);
    }

    @Override
    public void iniciarVisualizacao(DI documento) {
        DIController.visualizarDI(documento);
    }

    @Override
    public void setEducandoId(String educandoId) {
        DIController.setEducandoIdParaDI(educandoId);
    }

    @Override
    public void setTurmaOrigem(String turmaId) {
        DIController.setTurmaIdOrigem(turmaId);
    }

    @Override
    public String getPrimeiraTela() {
        return "diagnostico-1.fxml";
    }

    @Override
    public String getNomeDocumento() {
        return "Diagnóstico Inicial";
    }

    @Override
    public boolean excluir(DI documento) {
        try {
            if (documento == null || documento.getId() == null || documento.getId().isBlank()) {
                throw new Exception("DI sem identificador para exclusão.");
            }
            return service.excluirDI(documento.getId());
        } catch (Exception e) {
            System.err.println("Erro ao excluir DI: " + e.getMessage());
            return false;
        }
    }
}
