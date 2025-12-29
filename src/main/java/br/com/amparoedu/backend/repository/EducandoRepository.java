package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Educando;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducandoRepository {
    // Salva um novo educando
    public void salvar(Educando educando) {
        String sql = "INSERT INTO educandos (id, endereco_id, nome, cpf, data_nascimento, genero, grau_ensino, cid, nis, escola, observacoes, sincronizado, excluido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educando.getId());
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
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Atualiza os dados de um educando
    public void atualizar(Educando educando) {
        String sql = "UPDATE educandos SET endereco_id = ?, nome = ?, cpf = ?, data_nascimento = ?, genero = ?, grau_ensino = ?, cid = ?, nis = ?, escola = ?, observacoes = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educando.getEndereco_id());
            stmt.setString(2, educando.getNome());
            stmt.setString(3, educando.getCpf());
            stmt.setString(4, educando.getData_nascimento());
            stmt.setString(5, educando.getGenero());
            stmt.setString(6, educando.getGrau_ensino());
            stmt.setString(7, educando.getCid());
            stmt.setString(8, educando.getNis());
            stmt.setString(9, educando.getEscola());
            stmt.setString(10, educando.getObservacoes());
            stmt.setInt(11, educando.getSincronizado());
            stmt.setInt(12, educando.getExcluido());
            stmt.setString(13, educando.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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