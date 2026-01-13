package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Anamnese;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnamneseRepository {
    
    // Salva uma nova anamnese
    public void salvar(Anamnese anamnese) {
        String sql = "INSERT INTO anamneses (id, educando_id, professor_id, data_criacao, tem_convulsao, " +
                "tem_convenio_medico, nome_convenio, vacinas_em_dia, teve_doenca_contagiosa, quais_doencas, " +
                "usa_medicacao, quais_medicacoes, usou_servico_saude_educacao, quais_servicos, " +
                "inicio_escolarizacao, apresenta_dificuldades, quais_dificuldades, recebe_apoio_pedagogico_casa, " +
                "apoio_quem, duracao_da_gestacao, fez_prenatal, houve_prematuridade, causa_prematuridade, " +
                "cidade_nascimento, maternidade, tipo_parto, chorou_ao_nascer, ficou_roxo, usou_incubadora, " +
                "foi_amamentado, sustentou_a_cabeca, quantos_meses_sustentou_cabeca, engatinhou, " +
                "quantos_meses_engatinhou, sentou, quantos_meses_sentou, andou, quantos_meses_andou, " +
                "precisou_de_terapia, qual_motivo_terapia, falou, quantos_meses_falou, quantos_meses_balbuciou, " +
                "quando_primeiras_palavras, quando_primeiras_frases, fala_natural_inibido, possui_disturbio, " +
                "qual_disturbio, observacoes_adicionais, dorme_sozinho, tem_seu_quarto, sono_calmo_agitado, " +
                "respeita_regras, e_desmotivado, e_agressivo, apresenta_inquietacao, sincronizado, excluido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, anamnese, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Atualiza os dados de uma anamnese
    public void atualizar(Anamnese anamnese) {
        String sql = "UPDATE anamneses SET educando_id = ?, professor_id = ?, data_criacao = ?, " +
                "tem_convulsao = ?, tem_convenio_medico = ?, nome_convenio = ?, vacinas_em_dia = ?, " +
                "teve_doenca_contagiosa = ?, quais_doencas = ?, usa_medicacao = ?, quais_medicacoes = ?, " +
                "usou_servico_saude_educacao = ?, quais_servicos = ?, inicio_escolarizacao = ?, " +
                "apresenta_dificuldades = ?, quais_dificuldades = ?, recebe_apoio_pedagogico_casa = ?, " +
                "apoio_quem = ?, duracao_da_gestacao = ?, fez_prenatal = ?, houve_prematuridade = ?, " +
                "causa_prematuridade = ?, cidade_nascimento = ?, maternidade = ?, tipo_parto = ?, " +
                "chorou_ao_nascer = ?, ficou_roxo = ?, usou_incubadora = ?, foi_amamentado = ?, " +
                "sustentou_a_cabeca = ?, quantos_meses_sustentou_cabeca = ?, engatinhou = ?, " +
                "quantos_meses_engatinhou = ?, sentou = ?, quantos_meses_sentou = ?, andou = ?, " +
                "quantos_meses_andou = ?, precisou_de_terapia = ?, qual_motivo_terapia = ?, falou = ?, " +
                "quantos_meses_falou = ?, quantos_meses_balbuciou = ?, quando_primeiras_palavras = ?, " +
                "quando_primeiras_frases = ?, fala_natural_inibido = ?, possui_disturbio = ?, " +
                "qual_disturbio = ?, observacoes_adicionais = ?, dorme_sozinho = ?, tem_seu_quarto = ?, " +
                "sono_calmo_agitado = ?, respeita_regras = ?, e_desmotivado = ?, e_agressivo = ?, " +
                "apresenta_inquietacao = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, anamnese, false);
            stmt.setString(58, anamnese.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Exclusão lógica de uma anamnese
    public void excluir(String id) {
        String sql = "UPDATE anamneses SET excluido = 1, sincronizado = 0 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Busca por ID
    public Anamnese buscarPorId(String id) {
        String sql = "SELECT * FROM anamneses WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairAnamnese(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Busca anamneses por educando
    public List<Anamnese> buscarPorEducando(String educandoId) {
        List<Anamnese> anamneses = new ArrayList<>();
        String sql = "SELECT * FROM anamneses WHERE educando_id = ? AND excluido = 0 ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educandoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    anamneses.add(extrairAnamnese(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anamneses;
    }
    
    // Busca anamneses não sincronizadas
    public List<Anamnese> buscarNaoSincronizados() {
        List<Anamnese> anamneses = new ArrayList<>();
        String sql = "SELECT * FROM anamneses WHERE sincronizado = 0 AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                anamneses.add(extrairAnamnese(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anamneses;
    }
    
    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE anamneses SET sincronizado = ? WHERE id = ?";
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
    private void setStatementParameters(PreparedStatement stmt, Anamnese anamnese, boolean isInsert) throws SQLException {
        int index = 1;
        
        if (isInsert) {
            stmt.setString(index++, anamnese.getId());
        }
        
        stmt.setString(index++, anamnese.getEducando_id());
        stmt.setString(index++, anamnese.getProfessor_id());
        stmt.setString(index++, anamnese.getData_criacao());
        stmt.setString(index++, anamnese.getTem_convulsao());
        stmt.setString(index++, anamnese.getTem_convenio_medico());
        stmt.setString(index++, anamnese.getNome_convenio());
        stmt.setString(index++, anamnese.getVacinas_em_dia());
        stmt.setString(index++, anamnese.getTeve_doenca_contagiosa());
        stmt.setString(index++, anamnese.getQuais_doencas());
        stmt.setString(index++, anamnese.getUsa_medicacao());
        stmt.setString(index++, anamnese.getQuais_medicacoes());
        stmt.setString(index++, anamnese.getUsou_servico_saude_educacao());
        stmt.setString(index++, anamnese.getQuais_servicos());
        stmt.setString(index++, anamnese.getInicio_escolarizacao());
        stmt.setString(index++, anamnese.getApresenta_dificuldades());
        stmt.setString(index++, anamnese.getQuais_dificuldades());
        stmt.setString(index++, anamnese.getRecebe_apoio_pedagogico_casa());
        stmt.setString(index++, anamnese.getApoio_quem());
        stmt.setString(index++, anamnese.getDuracao_da_gestacao());
        stmt.setString(index++, anamnese.getFez_prenatal());
        stmt.setString(index++, anamnese.getHouve_prematuridade());
        stmt.setString(index++, anamnese.getCausa_prematuridade());
        stmt.setString(index++, anamnese.getCidade_nascimento());
        stmt.setString(index++, anamnese.getMaternidade());
        stmt.setString(index++, anamnese.getTipo_parto());
        stmt.setString(index++, anamnese.getChorou_ao_nascer());
        stmt.setString(index++, anamnese.getFicou_roxo());
        stmt.setString(index++, anamnese.getUsou_incubadora());
        stmt.setString(index++, anamnese.getFoi_amamentado());
        stmt.setString(index++, anamnese.getSustentou_a_cabeca());
        stmt.setString(index++, anamnese.getQuantos_meses_sustentou_cabeca());
        stmt.setString(index++, anamnese.getEngatinhou());
        stmt.setString(index++, anamnese.getQuantos_meses_engatinhou());
        stmt.setString(index++, anamnese.getSentou());
        stmt.setString(index++, anamnese.getQuantos_meses_sentou());
        stmt.setString(index++, anamnese.getAndou());
        stmt.setString(index++, anamnese.getQuantos_meses_andou());
        stmt.setString(index++, anamnese.getPrecisou_de_terapia());
        stmt.setString(index++, anamnese.getQual_motivo_terapia());
        stmt.setString(index++, anamnese.getFalou());
        stmt.setString(index++, anamnese.getQuantos_meses_falou());
        stmt.setString(index++, anamnese.getQuantos_meses_balbuciou());
        stmt.setString(index++, anamnese.getQuando_primeiras_palavras());
        stmt.setString(index++, anamnese.getQuando_primeiras_frases());
        stmt.setString(index++, anamnese.getFala_natural_inibido());
        stmt.setString(index++, anamnese.getPossui_disturbio());
        stmt.setString(index++, anamnese.getQual_disturbio());
        stmt.setString(index++, anamnese.getObservacoes_adicionais());
        stmt.setString(index++, anamnese.getDorme_sozinho());
        stmt.setString(index++, anamnese.getTem_seu_quarto());
        stmt.setString(index++, anamnese.getSono_calmo_agitado());
        stmt.setString(index++, anamnese.getRespeita_regras());
        stmt.setString(index++, anamnese.getE_desmotivado());
        stmt.setString(index++, anamnese.getE_agressivo());
        stmt.setString(index++, anamnese.getApresenta_inquietacao());
        stmt.setInt(index++, anamnese.getSincronizado());
        stmt.setInt(index++, anamnese.getExcluido());
    }
    
    // Método para simplificar o código
    private Anamnese extrairAnamnese(ResultSet rs) throws SQLException {
        return new Anamnese(
            rs.getString("id"),
            rs.getString("educando_id"),
            rs.getString("professor_id"),
            rs.getString("data_criacao"),
            rs.getString("tem_convulsao"),
            rs.getString("tem_convenio_medico"),
            rs.getString("nome_convenio"),
            rs.getString("vacinas_em_dia"),
            rs.getString("teve_doenca_contagiosa"),
            rs.getString("quais_doencas"),
            rs.getString("usa_medicacao"),
            rs.getString("quais_medicacoes"),
            rs.getString("usou_servico_saude_educacao"),
            rs.getString("quais_servicos"),
            rs.getString("inicio_escolarizacao"),
            rs.getString("apresenta_dificuldades"),
            rs.getString("quais_dificuldades"),
            rs.getString("recebe_apoio_pedagogico_casa"),
            rs.getString("apoio_quem"),
            rs.getString("duracao_da_gestacao"),
            rs.getString("fez_prenatal"),
            rs.getString("houve_prematuridade"),
            rs.getString("causa_prematuridade"),
            rs.getString("cidade_nascimento"),
            rs.getString("maternidade"),
            rs.getString("tipo_parto"),
            rs.getString("chorou_ao_nascer"),
            rs.getString("ficou_roxo"),
            rs.getString("usou_incubadora"),
            rs.getString("foi_amamentado"),
            rs.getString("sustentou_a_cabeca"),
            rs.getString("quantos_meses_sustentou_cabeca"),
            rs.getString("engatinhou"),
            rs.getString("quantos_meses_engatinhou"),
            rs.getString("sentou"),
            rs.getString("quantos_meses_sentou"),
            rs.getString("andou"),
            rs.getString("quantos_meses_andou"),
            rs.getString("precisou_de_terapia"),
            rs.getString("qual_motivo_terapia"),
            rs.getString("falou"),
            rs.getString("quantos_meses_falou"),
            rs.getString("quantos_meses_balbuciou"),
            rs.getString("quando_primeiras_palavras"),
            rs.getString("quando_primeiras_frases"),
            rs.getString("fala_natural_inibido"),
            rs.getString("possui_disturbio"),
            rs.getString("qual_disturbio"),
            rs.getString("observacoes_adicionais"),
            rs.getString("dorme_sozinho"),
            rs.getString("tem_seu_quarto"),
            rs.getString("sono_calmo_agitado"),
            rs.getString("respeita_regras"),
            rs.getString("e_desmotivado"),
            rs.getString("e_agressivo"),
            rs.getString("apresenta_inquietacao"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }
}