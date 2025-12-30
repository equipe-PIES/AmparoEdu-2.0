package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Responsavel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ResponsavelRepository {
    // Salva um novo responsavel
    public void salvar(Responsavel responsavel) {
        String sql = "INSERT INTO responsaveis (id, educando_id, nome, cpf, parentesco, telefone, sincronizado, excluido) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, responsavel.getId());
            stmt.setString(2, responsavel.getEducando_id());
            stmt.setString(3, responsavel.getNome());
            stmt.setString(4, responsavel.getCpf());
            stmt.setString(5, responsavel.getParentesco());
            stmt.setString(6, responsavel.getTelefone());
            stmt.setInt(7, responsavel.getSincronizado());
            stmt.setInt(8, responsavel.getExcluido());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Atualiza os dados de um responsavel
    public void atualizar(Responsavel responsavel) {
        String sql = "UPDATE responsaveis SET educando_id = ?, nome = ?, cpf = ?, parentesco = ?, telefone = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, responsavel.getEducando_id());
            stmt.setString(2, responsavel.getNome());
            stmt.setString(3, responsavel.getCpf());
            stmt.setString(4, responsavel.getParentesco());
            stmt.setString(5, responsavel.getTelefone());
            stmt.setInt(6, responsavel.getSincronizado());
            stmt.setInt(7, responsavel.getExcluido());
            stmt.setString(8, responsavel.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Exclusao lógica de um responsavel
    public void excluirLogicamente(String id) {
        String sql = "UPDATE responsaveis SET excluido = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca por ID
    public Responsavel buscarPorId(String id) {
        String sql = "SELECT * FROM responsaveis WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairResponsavel(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Buscar não sincronizados
    public List<Responsavel> buscarNaoSincronizados() {
        List<Responsavel> responsaveis = new ArrayList<>();
        String sql = "SELECT * FROM responsaveis WHERE sincronizado = 0 AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                responsaveis.add(extrairResponsavel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsaveis;
    }

    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int status) {
        String sql = "UPDATE responsaveis SET sincronizado = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Extrai um Responsavel do ResultSet
    private Responsavel extrairResponsavel(ResultSet rs) throws SQLException {
        return new Responsavel(
            rs.getString("id"),
            rs.getString("educando_id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("parentesco"),
            rs.getString("telefone"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }

}
