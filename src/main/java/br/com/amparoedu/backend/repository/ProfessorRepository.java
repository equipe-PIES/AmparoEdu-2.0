package br.com.amparoedu.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.amparoedu.backend.model.Professor;

public class ProfessorRepository {

    public void salvar(Professor professor) {
        String sql = "INSERT INTO professores (id, nome, cpf, data_nascimento, genero, observacoes, sincronizado, excluido, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, professor.getId());
            stmt.setString(2, professor.getNome());
            stmt.setString(3, professor.getCpf());
            stmt.setString(4, professor.getData_nascimento());
            stmt.setString(5, professor.getGenero());
            stmt.setString(6, professor.getObservacoes());
            stmt.setInt(7, professor.getSincronizado());
            stmt.setInt(8, professor.getExcluido());
            stmt.setString(9, professor.getUsuario_id());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca por ID 
    public Professor buscarPorId(String id) {
        String sql = "SELECT * FROM professores WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairProfessor(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Atualiza os dados
    public void atualizar(Professor professor) {
        String sql = "UPDATE professores SET nome = ?, cpf = ?, data_nascimento = ?, genero = ?, observacoes = ?, sincronizado = ?, excluido = ?, usuario_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getData_nascimento());
            stmt.setString(4, professor.getGenero());
            stmt.setString(5, professor.getObservacoes());
            stmt.setInt(6, professor.getSincronizado());
            stmt.setInt(7, professor.getExcluido());
            stmt.setString(8, professor.getUsuario_id());
            stmt.setString(9, professor.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // exclusão Lógica
    public void excluir(String id) {
        String sql = "UPDATE professores SET excluido = 1, sincronizado = 0 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // buscar professores não sincronizados
    public List<Professor> buscarNaoSincronizados() {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM professores WHERE sincronizado = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                professores.add(extrairProfessor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professores;
    }

    // atualizar a sincronização
    public void atualizarSincronizacao(String id, int status) {
        String sql = "UPDATE professores SET sincronizado = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, status);
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // evita repetição no código
    private Professor extrairProfessor(ResultSet rs) throws SQLException {
        return new Professor(
            rs.getString("id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("data_nascimento"),
            rs.getString("genero"),
            rs.getString("observacoes"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido"),
            rs.getString("usuario_id")
        );
    }
}