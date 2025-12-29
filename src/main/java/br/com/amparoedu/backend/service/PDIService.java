package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.repository.PDIRepository;
import br.com.amparoedu.backend.repository.EducandoRepository;

import java.util.List;
import java.util.UUID;

public class PDIService {
    private final PDIRepository pdiRepo = new PDIRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    public boolean cadastrarNovoPDI(PDI pdi) throws Exception {

        // Validação se o educando existe
        if (educandoRepo.buscarPorId(pdi.getEducandoId()) == null) {
            throw new Exception("Educando não encontrado.");
        }

        try {
            // Gera IDs únicos
            String pdiId = UUID.randomUUID().toString();

            // Define o ID, sincronização e vincula a professor e educando
            pdi.setId(pdiId);
            pdi.setExcluido(0);
            pdi.setSincronizado(0);

            // Salva no banco
            pdiRepo.salvar(pdi);

            System.out.println("PDI cadastrado com sucesso para o educando: " + pdi.getEducandoId());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar PDI: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPDI(PDI pdi) throws Exception {
        
        // Verifica se o PDI existe
        if (pdiRepo.buscarPorId(pdi.getId()) == null) {
            throw new Exception("PDI não encontrado.");
        }

        try {
            // Marca como não sincronizado para atualizar no servidor
            pdi.setSincronizado(0);

            // Atualiza no banco
            pdiRepo.atualizar(pdi);

            System.out.println("PDI atualizado com sucesso: " + pdi.getId());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar PDI: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirPDI(String id) throws Exception {
        
        // Verifica se o PDI existe
        if (pdiRepo.buscarPorId(id) == null) {
            throw new Exception("PDI não encontrado.");
        }

        try {
            pdiRepo.excluir(id);

            System.out.println("PDI excluído com sucesso: " + id);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao excluir PDI: " + e.getMessage());
            return false;
        }
    }

    public PDI buscarPorId(String id) {
        try {
            return pdiRepo.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar PDI: " + e.getMessage());
            return null;
        }
    }

    public List<PDI> buscarPorEducando(String educandoId) {
        try {
            return pdiRepo.buscarPorEducando(educandoId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar PDIs do educando: " + e.getMessage());
            return null;
        }
    }

    public List<PDI> buscarNaoSincronizados() {
        try {
            return pdiRepo.buscarNaoSincronizados();
        } catch (Exception e) {
            System.err.println("Erro ao buscar PDIs não sincronizados: " + e.getMessage());
            return null;
        }
    }

    public boolean sincronizarPDI(String id) {
        try {
            pdiRepo.atualizarSincronizacao(id, 1);
            System.out.println("PDI sincronizado: " + id);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao sincronizar PDI: " + e.getMessage());
            return false;
        }
    }
}
