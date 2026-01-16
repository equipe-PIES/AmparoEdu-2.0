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
            
            setStatementParameters(stmt, educando, true);
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
            
            setStatementParameters(stmt, educando, false);
            stmt.setString(13, educando.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Exclusão lógica de um educando
    public void excluir(String id) {
        String sql = "UPDATE educandos SET excluido = 1, sincronizado = 0 WHERE id = ?";
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

    // Listar todos os educandos ativos
    public List<Educando> listarTodos() {
        List<Educando> lista = new ArrayList<>();
        String sql = "SELECT * FROM educandos WHERE excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(extrairEducando(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // buscar por turma
    public List<Educando> buscarPorTurma(String turmaId) {
        List<Educando> lista = new ArrayList<>();
        String sql = "SELECT e.* FROM educandos e " +
                    "JOIN turma_educando te ON e.id = te.educando_id " +
                    "WHERE te.turma_id = ? AND te.excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turmaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(extrairEducando(rs));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
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
        String sql = "UPDATE educandos SET sincronizado = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sincronizado);
            stmt.setString(2, id);
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

    // Método auxiliar para setar parâmetros do PreparedStatement
    private void setStatementParameters(PreparedStatement stmt, Educando educando, boolean isInsert) throws SQLException {
        int index = 1;

        if (isInsert) {
            stmt.setString(index++, educando.getId());
        }

        stmt.setString(index++, educando.getEndereco_id());
        stmt.setString(index++, educando.getNome());
        stmt.setString(index++, educando.getCpf());
        stmt.setString(index++, educando.getData_nascimento());
        stmt.setString(index++, educando.getGenero());
        stmt.setString(index++, educando.getGrau_ensino());
        stmt.setString(index++, educando.getCid());
        stmt.setString(index++, educando.getNis());
        stmt.setString(index++, educando.getEscola());
        stmt.setString(index++, educando.getObservacoes());
        stmt.setInt(index++, educando.getSincronizado());
        stmt.setInt(index++, educando.getExcluido());
    }

}