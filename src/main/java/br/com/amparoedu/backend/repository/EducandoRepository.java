package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Educando;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducandoRepository {

    // Lista todos os educandos não excluídos
    public List<Educando> listarTodos() {
        List<Educando> educandos = new ArrayList<>();
        String sql = "SELECT * FROM educandos WHERE excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                educandos.add(extrairEducando(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return educandos;
    }

    // Salva um novo educando
    public void salvar(Educando educando) {
        String sql = "INSERT INTO educandos (id, turma_id, endereco_id, nome, cpf, data_nascimento, genero, grau_ensino, cid, nis, escola, observacoes, sincronizado, excluido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educando.getId());
            if (educando.getTurma_id() != null) stmt.setString(2, educando.getTurma_id());
            else stmt.setNull(2, java.sql.Types.VARCHAR);
            stmt.setString(3, educando.getEndereco_id());
            stmt.setString(4, educando.getNome());
            stmt.setString(5, educando.getCpf());
            stmt.setString(6, educando.getData_nascimento());
            stmt.setString(7, educando.getGenero());
            stmt.setString(8, educando.getGrau_ensino());
            stmt.setString(9, educando.getCid());
            stmt.setString(10, educando.getNis());
            stmt.setString(11, educando.getEscola());
            stmt.setString(12, educando.getObservacoes());
            stmt.setInt(13, educando.getSincronizado());
            stmt.setInt(14, educando.getExcluido());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar educando: " + e.getMessage(), e);
        }
    }
    
    // Atualiza os dados de um educando
    public void atualizar(Educando educando) {
        String sql = "UPDATE educandos SET turma_id = ?, endereco_id = ?, nome = ?, cpf = ?, data_nascimento = ?, genero = ?, grau_ensino = ?, cid = ?, nis = ?, escola = ?, observacoes = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (educando.getTurma_id() != null) stmt.setString(1, educando.getTurma_id());
            else stmt.setNull(1, java.sql.Types.VARCHAR);
            stmt.setString(2, educando.getEndereco_id());
            stmt.setString(3, educando.getNome());
            stmt.setString(4, educando.getCpf());
            stmt.setString(5, educando.getData_nascimento());
            stmt.setString(6, educando.getGenero());
            stmt.setString(7, educando.getGrau_ensino());
            stmt.setString(8, educando.getCid());
            stmt.setString(9, educando.getNis());
            stmt.setString(10, educando.getEscola());
            stmt.setString(11, educando.getObservacoes());
            stmt.setInt(12, educando.getSincronizado());
            stmt.setInt(13, educando.getExcluido());
            stmt.setString(14, educando.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar educando: " + e.getMessage(), e);
        }
    }

    // Exclusão lógica de um educando
    public void excluir(String id) {
        String sql = "UPDATE educandos SET excluido = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca por ID
    public Educando buscarPorId(String id) {
        String sql = "SELECT * FROM educandos WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairEducando(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Busca educandos não sincronizados
    public List<Educando> buscarNaoSincronizados() {
        List<Educando> educandos = new ArrayList<>();
        String sql = "SELECT * FROM educandos WHERE sincronizado = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                educandos.add(extrairEducando(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return educandos;
    }

    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE educandos SET sincronizado = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para simplificar o código
    private Educando extrairEducando(ResultSet rs) throws SQLException {
        return new Educando(
            rs.getString("id"),
            rs.getString("turma_id"),
            rs.getString("endereco_id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("data_nascimento"),
            rs.getString("genero"),
            rs.getString("grau_ensino"),
            rs.getString("cid"),
            rs.getString("nis"),
            rs.getString("escola"),
            rs.getString("observacoes"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }

}