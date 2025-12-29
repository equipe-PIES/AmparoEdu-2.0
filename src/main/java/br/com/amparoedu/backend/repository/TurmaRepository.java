package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Turma;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaRepository {

    public List<Turma> listarTodas() {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT * FROM turmas WHERE excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                turmas.add(extrairTurma(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return turmas;
    }

    public void salvar(Turma turma) {
        String sql = "INSERT INTO turmas (id, professor_id, nome, turno, grau_ensino, faixa_etaria, sincronizado, excluido) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turma.getId());
            stmt.setString(2, turma.getProfessor_id());
            stmt.setString(3, turma.getNome());
            stmt.setString(4, turma.getTurno());
            stmt.setString(5, turma.getGrau_ensino());
            stmt.setString(6, turma.getFaixa_etaria());
            stmt.setInt(7, turma.getSincronizado());
            stmt.setInt(8, turma.getExcluido());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Turma buscarPorId(String id) {
        String sql = "SELECT * FROM turmas WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extrairTurma(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void atualizar(Turma turma) {
        String sql = "UPDATE turmas SET professor_id = ?, nome = ?, turno = ?, grau_ensino = ?, faixa_etaria = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turma.getProfessor_id());
            stmt.setString(2, turma.getNome());
            stmt.setString(3, turma.getTurno());
            stmt.setString(4, turma.getGrau_ensino());
            stmt.setString(5, turma.getFaixa_etaria());
            stmt.setInt(6, turma.getSincronizado());
            stmt.setInt(7, turma.getExcluido());
            stmt.setString(8, turma.getId());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Turma> naoSincronizados() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM turmas WHERE sincronizado = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(extrairTurma(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizarStatusSincronia(String id) {
        String sql = "UPDATE turmas SET sincronizado = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

     // evita repetição
    private Turma extrairTurma(ResultSet rs) throws SQLException {
        return new Turma(
            rs.getString("id"),
            rs.getString("professor_id"),
            rs.getString("nome"),
            rs.getString("turno"),
            rs.getString("grau_ensino"),
            rs.getString("faixa_etaria"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }
}