package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.RI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RIRepository {
    
    // Salva um novo RI
    public void salvar(RI ri) {
        String sql = "INSERT INTO ris (id, educando_id, professor_id, data_criacao, " +
                "dados_funcionais, funcionalidade_cognitiva, alfabetizacao, " +
                "adaptacoes_curriculares, participacao_atividade, autonomia, " +
                "interacao_professora, atividades_vida_diaria, sincronizado, excluido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, ri, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Atualiza os dados de um RI
    public void atualizar(RI ri) {
        String sql = "UPDATE ris SET educando_id = ?, professor_id = ?, data_criacao = ?, " +
                "dados_funcionais = ?, funcionalidade_cognitiva = ?, alfabetizacao = ?, " +
                "adaptacoes_curriculares = ?, participacao_atividade = ?, autonomia = ?, " +
                "interacao_professora = ?, atividades_vida_diaria = ?, sincronizado = ?, excluido = ? " +
                "WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, ri, false);
            stmt.setString(14, ri.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Exclusão lógica de um RI
    public void excluir(String id) {
        String sql = "UPDATE ris SET excluido = 1, sincronizado = 0 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Busca por ID
    public RI buscarPorId(String id) {
        String sql = "SELECT * FROM ris WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairRI(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Busca RIs por educando
    public List<RI> buscarPorEducando(String educandoId) {
        List<RI> ris = new ArrayList<>();
        String sql = "SELECT * FROM ris WHERE educando_id = ? AND excluido = 0 ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educandoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ris.add(extrairRI(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ris;
    }
    
    // Busca RIs não sincronizados
    public List<RI> buscarNaoSincronizados() {
        List<RI> ris = new ArrayList<>();
        String sql = "SELECT * FROM ris WHERE sincronizado = 0 AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ris.add(extrairRI(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ris;
    }
    
    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE ris SET sincronizado = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sincronizado);
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método auxiliar para setar parâmetros do PreparedStatement
    private void setStatementParameters(PreparedStatement stmt, RI ri, boolean isInsert) throws SQLException {
        int index = 1;
        
        if (isInsert) {
            stmt.setString(index++, ri.getId());
        }
        
        stmt.setString(index++, ri.getEducando_id());
        stmt.setString(index++, ri.getProfessor_id());
        stmt.setString(index++, ri.getData_criacao());
        stmt.setString(index++, ri.getDados_funcionais());
        stmt.setString(index++, ri.getFuncionalidade_cognitiva());
        stmt.setString(index++, ri.getAlfabetizacao());
        stmt.setString(index++, ri.getAdaptacoes_curriculares());
        stmt.setString(index++, ri.getParticipacao_atividade());
        stmt.setInt(index++, ri.getAutonomia());
        stmt.setInt(index++, ri.getInteracao_professora());
        stmt.setString(index++, ri.getAtividades_vida_diaria());
        stmt.setInt(index++, ri.getSincronizado());
        stmt.setInt(index++, ri.getExcluido());
    }
    
    // Método para extrair RI do ResultSet
    private RI extrairRI(ResultSet rs) throws SQLException {
        return new RI(
            rs.getString("id"),
            rs.getString("educando_id"),
            rs.getString("professor_id"),
            rs.getString("data_criacao"),
            rs.getString("dados_funcionais"),
            rs.getString("funcionalidade_cognitiva"),
            rs.getString("alfabetizacao"),
            rs.getString("adaptacoes_curriculares"),
            rs.getString("participacao_atividade"),
            rs.getInt("autonomia"),
            rs.getInt("interacao_professora"),
            rs.getString("atividades_vida_diaria"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }
}
