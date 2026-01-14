package br.com.amparoedu.backend.factory;

import br.com.amparoedu.backend.model.DI;
import br.com.amparoedu.controller.DIController;

// Implementação do fluxo de documentos para DI
public class DIFluxo implements DocumentoFluxo<DI> {

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
}
