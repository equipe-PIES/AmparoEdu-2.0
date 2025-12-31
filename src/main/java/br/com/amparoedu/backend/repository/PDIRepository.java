package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.PDI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PDIRepository {
    public void salvar(PDI pdi) {
        String sql = "INSERT INTO pdis (id, educando_id, professor_id, data_criacao, periodo_aee, horario_atendimento, " +
                "frequencia_atendimento, dias_atendimento, composicao_grupo, objetivos, potencialidades, " +
                "necessidades_especiais, habilidades, atividades, recursos_materiais, recursos_necessitam_adaptacao, " +
                "recursos_necessitam_produzir, parcerias_necessarias, sincronizado, excluido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, pdi, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(PDI pdi) {
        String sql = "UPDATE pdis SET educando_id = ?, professor_id = ?, data_criacao = ?, periodo_aee = ?, " +
                "horario_atendimento = ?, frequencia_atendimento = ?, dias_atendimento = ?, composicao_grupo = ?, " +
                "objetivos = ?, potencialidades = ?, necessidades_especiais = ?, habilidades = ?, atividades = ?, " +
                "recursos_materiais = ?, recursos_necessitam_adaptacao = ?, recursos_necessitam_produzir = ?, " +
                "parcerias_necessarias = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, pdi, false);
            stmt.setString(20, pdi.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Exclusão lógica de um PDI
    public void excluir(String id) {
        String sql = "UPDATE pdis SET excluido = 1, sincronizado = 0 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // buscar por ID
    public PDI buscarPorId(String id) {
        String sql = "SELECT * FROM pdis WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairPDI(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Busca PDIs por educando
    public List<PDI> buscarPorEducando(String educandoId) {
        List<PDI> pdis = new ArrayList<>();
        String sql = "SELECT * FROM pdis WHERE educando_id = ? AND excluido = 0 ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educandoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pdis.add(extrairPDI(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pdis;
    }

    // Busca PDIs não sincronizados
    public List<PDI> buscarNaoSincronizados() {
        List<PDI> pdis = new ArrayList<>();
        String sql = "SELECT * FROM pdis WHERE sincronizado = 0 AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                pdis.add(extrairPDI(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pdis;
    }

    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE pdis SET sincronizado = ? WHERE id = ?";
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
    private void setStatementParameters(PreparedStatement stmt, PDI pdi, boolean isInsert) throws SQLException {
        int index = 1;
        
        if (isInsert) {
            stmt.setString(index++, pdi.getId());
        }
        
        stmt.setString(index++, pdi.getEducandoId());
        stmt.setString(index++, pdi.getProfessorId());
        stmt.setString(index++, pdi.getDataCriacao());
        stmt.setString(index++, pdi.getPeriodoAee());
        stmt.setString(index++, pdi.getHorarioAtendimento());
        stmt.setString(index++, pdi.getFrequenciaAtendimento());
        stmt.setString(index++, pdi.getDiasAtendimento());
        stmt.setString(index++, pdi.getComposicaoGrupo());
        stmt.setString(index++, pdi.getObjetivos());
        stmt.setString(index++, pdi.getPotencialidades());
        stmt.setString(index++, pdi.getNecessidadesEspeciais());
        stmt.setString(index++, pdi.getHabilidades());
        stmt.setString(index++, pdi.getAtividades());
        stmt.setString(index++, pdi.getRecursosMateriais());
        stmt.setString(index++, pdi.getRecursosNecessitamAdaptacao());
        stmt.setString(index++, pdi.getRecursosNecessitamProduzir());
        stmt.setString(index++, pdi.getParceriasNecessarias());
        stmt.setInt(index++, pdi.getSincronizado());
        stmt.setInt(index++, pdi.getExcluido());

    }
    
    // Método para simplificar o código
    private PDI extrairPDI(ResultSet rs) throws SQLException {
        return new PDI(
            rs.getString("id"),
            rs.getString("educando_id"),
            rs.getString("professor_id"),
            rs.getString("data_criacao"),
            rs.getString("periodo_aee"),
            rs.getString("horario_atendimento"),
            rs.getString("frequencia_atendimento"),
            rs.getString("dias_atendimento"),
            rs.getString("composicao_grupo"),
            rs.getString("objetivos"),
            rs.getString("potencialidades"),
            rs.getString("necessidades_especiais"),
            rs.getString("habilidades"),
            rs.getString("atividades"),
            rs.getString("recursos_materiais"),
            rs.getString("recursos_necessitam_adaptacao"),
            rs.getString("recursos_necessitam_produzir"),
            rs.getString("parcerias_necessarias"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }

}
