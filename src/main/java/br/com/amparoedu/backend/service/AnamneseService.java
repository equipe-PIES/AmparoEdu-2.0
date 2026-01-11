package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.repository.AnamneseRepository;
import br.com.amparoedu.backend.repository.EducandoRepository;

import java.util.List;
import java.util.UUID;

public class AnamneseService {
    private final AnamneseRepository anamneseRepo = new AnamneseRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();

    // Busca um professor válido no banco
    public String buscarProfessorValido() {
        String sql = "SELECT id FROM professores LIMIT 1";
        try (java.sql.Connection conn = br.com.amparoedu.backend.repository.DatabaseConfig.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cadastrarNovaAnamnese(Anamnese anamnese) throws Exception {

        // Validação se o educando existe
        if (educandoRepo.buscarPorId(anamnese.getEducando_id()) == null) {
            throw new Exception("Educando não encontrado.");
        }

        try {
            // Gera IDs únicos
            String anamneseId = UUID.randomUUID().toString();

            // Define o ID, sincronização e vincula a professor e educando
            anamnese.setId(anamneseId);
            anamnese.setExcluido(0);
            anamnese.setSincronizado(0);

            // Salva no banco
            anamneseRepo.salvar(anamnese);

            return true;
            
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean atualizarAnamnese(Anamnese anamnese) throws Exception {
        
        // Verifica se a anamnese existe
        if (anamneseRepo.buscarPorId(anamnese.getId()) == null) {
            throw new Exception("Anamnese não encontrada.");
        }

        try {
            // Marca como não sincronizado para atualizar no servidor
            anamnese.setSincronizado(0);

            // Atualiza no banco
            anamneseRepo.atualizar(anamnese);

            System.out.println("Anamnese atualizada com sucesso: " + anamnese.getId());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar anamnese: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirAnamnese(String id) throws Exception {
        
        // Verifica se a anamnese existe
        if (anamneseRepo.buscarPorId(id) == null) {
            throw new Exception("Anamnese não encontrada.");
        }

        try {
            anamneseRepo.excluir(id);

            System.out.println("Anamnese excluída com sucesso: " + id);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao excluir anamnese: " + e.getMessage());
            return false;
        }
    }

    public Anamnese buscarPorId(String id) {
        try {
            return anamneseRepo.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar anamnese: " + e.getMessage());
            return null;
        }
    }

    public List<Anamnese> buscarPorEducando(String educandoId) {
        try {
            return anamneseRepo.buscarPorEducando(educandoId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar anamneses do educando: " + e.getMessage());
            return null;
        }
    }

    public List<Anamnese> buscarNaoSincronizados() {
        try {
            return anamneseRepo.buscarNaoSincronizados();
        } catch (Exception e) {
            System.err.println("Erro ao buscar anamneses não sincronizadas: " + e.getMessage());
            return null;
        }
    }

    public boolean sincronizarAnamnese(String id) {
        try {
            anamneseRepo.atualizarSincronizacao(id, 1);
            System.out.println("Anamnese sincronizada: " + id);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao sincronizar anamnese: " + e.getMessage());
            return false;
        }
    }
}