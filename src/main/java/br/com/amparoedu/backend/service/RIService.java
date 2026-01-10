package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.RI;
import br.com.amparoedu.backend.repository.RIRepository;
import br.com.amparoedu.backend.repository.EducandoRepository;

import java.util.List;
import java.util.UUID;

public class RIService {
    private final RIRepository riRepo = new RIRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    public boolean cadastrarNovoRI(RI ri) throws Exception {

        // Validação se o educando existe
        if (educandoRepo.buscarPorId(ri.getEducando_id()) == null) {
            throw new Exception("Educando não encontrado.");
        }

        try {
            // Gera IDs únicos
            String riId = UUID.randomUUID().toString();

            // Define o ID, sincronização e vincula a professor e educando
            ri.setId(riId);
            ri.setExcluido(0);
            ri.setSincronizado(0);

            // Salva no banco
            riRepo.salvar(ri);

            System.out.println("RI cadastrado com sucesso para o educando: " + ri.getEducando_id());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar RI: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarRI(RI ri) throws Exception {
        
        // Verifica se o RI existe
        if (riRepo.buscarPorId(ri.getId()) == null) {
            throw new Exception("RI não encontrado.");
        }

        try {
            // Marca como não sincronizado para atualizar no servidor
            ri.setSincronizado(0);

            // Atualiza no banco
            riRepo.atualizar(ri);

            System.out.println("RI atualizado com sucesso: " + ri.getId());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar RI: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirRI(String id) throws Exception {
        
        // Verifica se o RI existe
        if (riRepo.buscarPorId(id) == null) {
            throw new Exception("RI não encontrado.");
        }

        try {
            riRepo.excluir(id);

            System.out.println("RI excluído com sucesso: " + id);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao excluir RI: " + e.getMessage());
            return false;
        }
    }

    public RI buscarPorId(String id) {
        try {
            return riRepo.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar RI: " + e.getMessage());
            return null;
        }
    }

    public List<RI> buscarPorEducando(String educandoId) {
        try {
            return riRepo.buscarPorEducando(educandoId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar RIs do educando: " + e.getMessage());
            return null;
        }
    }

    public List<RI> buscarNaoSincronizados() {
        try {
            return riRepo.buscarNaoSincronizados();
        } catch (Exception e) {
            System.err.println("Erro ao buscar RIs não sincronizados: " + e.getMessage());
            return null;
        }
    }

    public boolean sincronizarRI(String id) {
        try {
            riRepo.atualizarSincronizacao(id, 1);
            System.out.println("RI sincronizado: " + id);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao sincronizar RI: " + e.getMessage());
            return false;
        }
    }
}
