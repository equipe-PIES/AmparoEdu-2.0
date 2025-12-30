package br.com.amparoedu.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.amparoedu.backend.model.PAEE;

public class PAEERepository {
     // Salva um novo paee
    public void salvar(PAEE paee) {
        String sql = "INSERT INTO paees (id, educando_id, professor_id, data_criacao, resumo, " +
            "dificuldades_motoras, dificuldades_cognitivas, dificuldades_sensoriais, dificuldades_comunicacao, " +
            "dificuldades_familiares, dificuldades_afetivas, dificuldades_raciocinio, dificuldades_avas, " +
            "dif_des_motor, intervencoes_motor, dif_comunicacao, intervencoes_comunicacao, dif_raciocinio, " +
            "intervencoes_raciocinio, dif_atencao, intervencoes_atencao, dif_memoria, intervencoes_memoria, " +
            "dif_percepcao, intervencoes_percepcao, dif_sociabilidade, intervencoes_sociabilidade, objetivo_plano, " +
            "aee, psicologo, fisioterapeuta, psicopedagogo, terapeuta_ocupacional, educacao_fisica, " +
            "estimulacao_precoce, sincronizado, excluido) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, paee, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Atualiza os dados de um paee
    public void atualizar(PAEE paee) {
        String sql = "UPDATE paees SET educando_id = ?, professor_id = ?, data_criacao = ?, " +
                "resumo = ?, dificuldades_motoras = ?, dificuldades_cognitivas = ?, dificuldades_sensoriais = ?, " +
                "dificuldades_comunicacao = ?, dificuldades_familiares = ?, dificuldades_afetivas = ?, " +
                "dificuldades_raciocinio = ?, dificuldades_avas = ?, dif_des_motor = ?, intervencoes_motor = ?, " +
                "dif_comunicacao = ?, intervencoes_comunicacao = ?, dif_raciocinio = ?, intervencoes_raciocinio = ?, " +
                "dif_atencao = ?, intervencoes_atencao = ?, dif_memoria = ?, intervencoes_memoria = ?, " +
                "dif_percepcao = ?, intervencoes_percepcao = ?, dif_sociabilidade = ?, intervencoes_sociabilidade = ?, " +
                "objetivo_plano = ?, aee = ?, psicologo = ?, fisioterapeuta = ?, psicopedagogo = ?, " +
                "terapeuta_ocupacional = ?, educacao_fisica = ?, estimulacao_precoce = ?, sincronizado = ?, excluido = ? " +
                "WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, paee, false);
            stmt.setString(37, paee.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Exclusão lógica de um paee
    public void excluir(String id) {
        String sql = "UPDATE paees SET excluido = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Busca por ID
    public PAEE buscarPorId(String id) {
        String sql = "SELECT * FROM paees WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairPAEE(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Busca paees por educando
    public List<PAEE> buscarPorEducando(String educandoId) {
        List<PAEE> paees = new ArrayList<>();
        String sql = "SELECT * FROM paees WHERE educando_id = ? AND excluido = 0 ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educandoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    paees.add(extrairPAEE(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paees;
    }
    
    // Busca paees não sincronizados
    public List<PAEE> buscarNaoSincronizados() {
        List<PAEE> paees = new ArrayList<>();
        String sql = "SELECT * FROM paees WHERE sincronizado = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                paees.add(extrairPAEE(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paees;
    }
    
    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE paees SET sincronizado = ? WHERE id = ?";
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
    private void setStatementParameters(PreparedStatement stmt, PAEE paee, boolean isInsert) throws SQLException {
        int index = 1;
        
        if (isInsert) {
            stmt.setString(index++, paee.getId());
        }
        
        stmt.setString(index++, paee.getEducandoId());
        stmt.setString(index++, paee.getProfessorId());
        stmt.setString(index++, paee.getDataCriacao());
        stmt.setString(index++, paee.getResumo());
        stmt.setInt(index++, paee.getDificuldadesMotoras());
        stmt.setInt(index++, paee.getDificuldadesCognitivas());
        stmt.setInt(index++, paee.getDificuldadesSensoriais());
        stmt.setInt(index++, paee.getDificuldadesComunicacao());
        stmt.setInt(index++, paee.getDificuldadesFamiliares());
        stmt.setInt(index++, paee.getDificuldadesAfetivas());
        stmt.setInt(index++, paee.getDificuldadesRaciocinio());
        stmt.setInt(index++, paee.getDificuldadesAvas());
        stmt.setString(index++, paee.getDifDesMotor());
        stmt.setString(index++, paee.getIntervencoesMotor());
        stmt.setString(index++, paee.getDifComunicacao());
        stmt.setString(index++, paee.getIntervencoesComunicacao());
        stmt.setString(index++, paee.getDifRaciocinio());
        stmt.setString(index++, paee.getIntervencoesRaciocinio());
        stmt.setString(index++, paee.getDifAtencao());
        stmt.setString(index++, paee.getIntervencoesAtencao());
        stmt.setString(index++, paee.getDifMemoria());
        stmt.setString(index++, paee.getIntervencoesMemoria());
        stmt.setString(index++, paee.getDifPercepcao());
        stmt.setString(index++, paee.getIntervencoesPercepcao());
        stmt.setString(index++, paee.getDifSociabilidade());
        stmt.setString(index++, paee.getIntervencoesSociabilidade());
        stmt.setString(index++, paee.getObjetivoPlano());
        stmt.setInt(index++, paee.getAee());
        stmt.setInt(index++, paee.getPsicologo());
        stmt.setInt(index++, paee.getFisioterapeuta());
        stmt.setInt(index++, paee.getPsicopedagogo());
        stmt.setInt(index++, paee.getTerapeutaOcupacional());
        stmt.setInt(index++, paee.getEducacaoFisica());
        stmt.setInt(index++, paee.getEstimulacaoPrecoce());
        stmt.setInt(index++, paee.getSincronizado());
        stmt.setInt(index++, paee.getExcluido());
    }
    
    // Método para simplificar o código
    private PAEE extrairPAEE(ResultSet rs) throws SQLException {
        return new PAEE(
            rs.getString("id"),
            rs.getString("educando_id"),
            rs.getString("professor_id"),
            rs.getString("data_criacao"),
            rs.getString("resumo"),
            rs.getInt("dificuldades_motoras"),
            rs.getInt("dificuldades_cognitivas"),
            rs.getInt("dificuldades_sensoriais"),
            rs.getInt("dificuldades_comunicacao"),
            rs.getInt("dificuldades_familiares"),
            rs.getInt("dificuldades_afetivas"),
            rs.getInt("dificuldades_raciocinio"),
            rs.getInt("dificuldades_avas"),
            rs.getString("dif_des_motor"),
            rs.getString("intervencoes_motor"),
            rs.getString("dif_comunicacao"),
            rs.getString("intervencoes_comunicacao"),
            rs.getString("dif_raciocinio"),
            rs.getString("intervencoes_raciocinio"),
            rs.getString("dif_atencao"),
            rs.getString("intervencoes_atencao"),
            rs.getString("dif_memoria"),
            rs.getString("intervencoes_memoria"),
            rs.getString("dif_percepcao"),
            rs.getString("intervencoes_percepcao"),
            rs.getString("dif_sociabilidade"),
            rs.getString("intervencoes_sociabilidade"),
            rs.getString("objetivo_plano"),
            rs.getInt("aee"),
            rs.getInt("psicologo"),
            rs.getInt("fisioterapeuta"),
            rs.getInt("psicopedagogo"),
            rs.getInt("terapeuta_ocupacional"),
            rs.getInt("educacao_fisica"),
            rs.getInt("estimulacao_precoce"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }
}