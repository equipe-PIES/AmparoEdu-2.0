package br.com.amparoedu.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.amparoedu.backend.model.MediaTempoGestacao;
import br.com.amparoedu.backend.model.QtdAlunosTurmaEscolaridade;

public class ViewsRepository {

    public List<QtdAlunosTurmaEscolaridade> buscarQtdAlunosPorTurmaEscolaridade() {
        List<QtdAlunosTurmaEscolaridade> lista = new ArrayList<>();
        String sql = "SELECT * FROM Qtd_Alunos_Turma_Escolaridade";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                QtdAlunosTurmaEscolaridade item = new QtdAlunosTurmaEscolaridade();
                item.setEscolaridade(rs.getString("escolaridade"));
                item.setNomeTurma(rs.getString("nome"));
                item.setQuantidadeAlunos(rs.getInt("quantidade_alunos"));
                lista.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<MediaTempoGestacao> buscarMediaTempoGestacao() {
        List<MediaTempoGestacao> lista = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Media_Tempo_Gestacao");
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new MediaTempoGestacao(
                        rs.getString("cidade_nascimento"),
                        rs.getDouble("media_tempo_gestacao"),
                        rs.getString("tipo_parto"),
                        rs.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
