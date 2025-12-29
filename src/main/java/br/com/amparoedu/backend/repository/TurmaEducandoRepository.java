package br.com.amparoedu.backend.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.amparoedu.backend.model.TurmaEducando;

public class TurmaEducandoRepository {
    public void salvar(TurmaEducando turmaEducando) {
        String sql = "INSERT INTO turma_educando (turma_id, educando_id, sincronizado, excluido) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turmaEducando.getTurma_id());
            stmt.setString(2, turmaEducando.getEducando_id());
            stmt.setInt(3, turmaEducando.getSincronizado());
            stmt.setInt(4, turmaEducando.getExcluido());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void desvincular(String turmaId, String educandoId, int sincronizado) {
        String sql = "UPDATE turma_educando SET excluido = 1 WHERE turma_id = ? AND educando_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turmaId);
            stmt.setString(2, educandoId);
            stmt.setInt(3, sincronizado);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public TurmaEducando buscarPorIds(String turmaId, String educandoId) {
        String sql = "SELECT * FROM turma_educando WHERE turma_id = ? AND educando_id = ? AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turmaId);
            stmt.setString(2, educandoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TurmaEducando(
                        rs.getString("turma_id"),
                        rs.getString("educando_id"),
                        rs.getInt("sincronizado"),
                        rs.getInt("excluido")
                    );
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return null;
    }

    // buscar vinculos não sincronizados
    public List<TurmaEducando> buscarNaoSincronizados() {
        List<TurmaEducando> lista = new ArrayList<>();
        String sql = "SELECT * FROM turma_educando WHERE sincronizado = 0";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new TurmaEducando(
                    rs.getString("turma_id"),
                    rs.getString("educando_id"),
                    rs.getInt("sincronizado"),
                    rs.getInt("excluido")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // atualizar a sincronização
    public void atualizarSincronizacao(String turmaId, String educandoId, int sincronizado) {
        String sql = "UPDATE turma_educando SET sincronizado = ? WHERE turma_id = ? AND educando_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sincronizado);
            stmt.setString(2, turmaId);
            stmt.setString(3, educandoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
