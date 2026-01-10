package br.com.amparoedu.backend.service;

import java.util.List;
import java.util.UUID;

import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.repository.PAEERepository;
import br.com.amparoedu.backend.repository.EducandoRepository;

public class PAEEService {
    private final PAEERepository paeeRepo = new PAEERepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    public boolean cadastrarNovaPAEE(PAEE paee) throws Exception {

        // Validação se o educando existe
        if (educandoRepo.buscarPorId(paee.getEducandoId()) == null) {
            throw new Exception("Educando não encontrado.");
        }

        try {
            // Gera IDs únicos
            String paeeId = UUID.randomUUID().toString();

            // Define o ID, sincronização e vincula a professor e educando
            paee.setId(paeeId);
            paee.setExcluido(0);
            paee.setSincronizado(0);

            // Salva no banco
            paeeRepo.salvar(paee);

            System.out.println("PAEE cadastrada com sucesso para o educando: " + paee.getEducandoId());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar PAEE: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPAEE(PAEE paee) throws Exception {
        
        // Verifica se a PAEE existe
        if (paeeRepo.buscarPorId(paee.getId()) == null) {
            throw new Exception("PAEE não encontrada.");
        }

        try {
            // Marca como não sincronizado para atualizar no servidor
            paee.setSincronizado(0);

            // Atualiza no banco
            paeeRepo.atualizar(paee);

            System.out.println("PAEE atualizada com sucesso: " + paee.getId());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar PAEE: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirPAEE(String id) throws Exception {
        
        // Verifica se a PAEE existe
        if (paeeRepo.buscarPorId(id) == null) {
            throw new Exception("PAEE não encontrada.");
        }

        try {
            paeeRepo.excluir(id);

            System.out.println("PAEE excluída com sucesso: " + id);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao excluir PAEE: " + e.getMessage());
            return false;
        }
    }

    public PAEE buscarPorId(String id) {
        try {
            return paeeRepo.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar PAEE: " + e.getMessage());
            return null;
        }
    }

    public List<PAEE> buscarPorEducando(String educandoId) {
        try {
            return paeeRepo.buscarPorEducando(educandoId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar PAEE do educando: " + e.getMessage());
            return null;
        }
    }

    public List<PAEE> buscarNaoSincronizados() {
        try {
            return paeeRepo.buscarNaoSincronizados();
        } catch (Exception e) {
            System.err.println("Erro ao buscar PAEE não sincronizadas: " + e.getMessage());
            return null;
        }
    }

    public boolean sincronizarPAEE(String id) {
        try {
            paeeRepo.atualizarSincronizacao(id, 1);
            System.out.println("PAEE sincronizada: " + id);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao sincronizar PAEE: " + e.getMessage());
            return false;
        }
    }
}
