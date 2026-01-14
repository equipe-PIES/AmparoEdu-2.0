package br.com.amparoedu.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.amparoedu.backend.model.DI;

public class DIRepository {
    
    // Salva um novo DI
    public void salvar(DI di) {
        String sql = "INSERT INTO dis (id, educando_id, professor_id, data_criacao, " +
                "fala_nome, fala_nascimento, le_palavras, fala_telefone, emite_respostas, transmite_recados, " +
                "fala_endereco, fala_nome_pais, compreende_ordens, expoe_ideias, reconta_historia, usa_sistema_ca, " +
                "relata_fatos, pronuncia_letras, verbaliza_musicas, interpreta_historias, formula_perguntas, " +
                "utiliza_gestos, demonstra_cooperacao, timido, birra, pede_ajuda, ri, compartilha, " +
                "demonstra_amor, chora, interage, detalhes_gravura, reconhece_vozes, reconhece_cancoes, " +
                "percebe_texturas, percebe_cores, discrimina_sons, discrimina_odores, aceita_texturas, " +
                "percepcao_formas, identifica_direcao_sons, discrimina_sabores, acompanha_luz, movimento_pinca, " +
                "amassa_papel, cai_facilmente, encaixa_pecas, recorta, une_pontos, corre, empilha, " +
                "agitacao_motora, anda_reto, sobe_escada, arremessa_bola, usa_sanitario, penteia_cabelo, " +
                "veste_se, lava_maos, banha_se, calca_se, reconhece_roupas, abre_torneira, escova_dentes, " +
                "da_nos, abotoa_roupas, identifica_partes_corpo, garatujas, silabico_alfabetico, alfabetico, " +
                "pre_silabico, silabico, observacoes, sincronizado, excluido) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, di, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Atualiza os dados de um DI
    public void atualizar(DI di) {
        String sql = "UPDATE dis SET educando_id = ?, professor_id = ?, data_criacao = ?, " +
                "fala_nome = ?, fala_nascimento = ?, le_palavras = ?, fala_telefone = ?, emite_respostas = ?, " +
                "transmite_recados = ?, fala_endereco = ?, fala_nome_pais = ?, compreende_ordens = ?, " +
                "expoe_ideias = ?, reconta_historia = ?, usa_sistema_ca = ?, relata_fatos = ?, " +
                "pronuncia_letras = ?, verbaliza_musicas = ?, interpreta_historias = ?, formula_perguntas = ?, " +
                "utiliza_gestos = ?, demonstra_cooperacao = ?, timido = ?, birra = ?, pede_ajuda = ?, " +
                "ri = ?, compartilha = ?, demonstra_amor = ?, chora = ?, interage = ?, detalhes_gravura = ?, " +
                "reconhece_vozes = ?, reconhece_cancoes = ?, percebe_texturas = ?, percebe_cores = ?, " +
                "discrimina_sons = ?, discrimina_odores = ?, aceita_texturas = ?, percepcao_formas = ?, " +
                "identifica_direcao_sons = ?, discrimina_sabores = ?, acompanha_luz = ?, movimento_pinca = ?, " +
                "amassa_papel = ?, cai_facilmente = ?, encaixa_pecas = ?, recorta = ?, une_pontos = ?, " +
                "corre = ?, empilha = ?, agitacao_motora = ?, anda_reto = ?, sobe_escada = ?, " +
                "arremessa_bola = ?, usa_sanitario = ?, penteia_cabelo = ?, veste_se = ?, lava_maos = ?, " +
                "banha_se = ?, calca_se = ?, reconhece_roupas = ?, abre_torneira = ?, escova_dentes = ?, " +
                "da_nos = ?, abotoa_roupas = ?, identifica_partes_corpo = ?, garatujas = ?, " +
                "silabico_alfabetico = ?, alfabetico = ?, pre_silabico = ?, silabico = ?, observacoes = ?, " +
                "sincronizado = ?, excluido = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, di, false);
            stmt.setString(75, di.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Exclusão lógica de um DI
    public void excluir(String id) {
        String sql = "UPDATE dis SET excluido = 1, sincronizado = 0 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Busca por ID
    public DI buscarPorId(String id) {
        String sql = "SELECT * FROM dis WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairDI(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Busca DIs por educando
    public List<DI> buscarPorEducando(String educandoId) {
        List<DI> dis = new ArrayList<>();
        String sql = "SELECT * FROM dis WHERE educando_id = ? AND excluido = 0 ORDER BY data_criacao DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, educandoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dis.add(extrairDI(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dis;
    }
    
    // Busca DIs não sincronizados
    public List<DI> buscarNaoSincronizados() {
        List<DI> dis = new ArrayList<>();
        String sql = "SELECT * FROM dis WHERE sincronizado = 0 AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                dis.add(extrairDI(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dis;
    }
    
    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE dis SET sincronizado = ? WHERE id = ?";
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
    private void setStatementParameters(PreparedStatement stmt, DI di, boolean isInsert) throws SQLException {
        int index = 1;
        
        if (isInsert) {
            stmt.setString(index++, di.getId());
        }
        
        stmt.setString(index++, di.getEducando_id());
        stmt.setString(index++, di.getProfessor_id());
        stmt.setString(index++, di.getData_criacao());
        stmt.setString(index++, di.getFala_nome());
        stmt.setString(index++, di.getFala_nascimento());
        stmt.setString(index++, di.getLe_palavras());
        stmt.setString(index++, di.getFala_telefone());
        stmt.setString(index++, di.getEmite_respostas());
        stmt.setString(index++, di.getTransmite_recados());
        stmt.setString(index++, di.getFala_endereco());
        stmt.setString(index++, di.getFala_nome_pais());
        stmt.setString(index++, di.getCompreende_ordens());
        stmt.setString(index++, di.getExpoe_ideias());
        stmt.setString(index++, di.getReconta_historia());
        stmt.setString(index++, di.getUsa_sistema_ca());
        stmt.setString(index++, di.getRelata_fatos());
        stmt.setString(index++, di.getPronuncia_letras());
        stmt.setString(index++, di.getVerbaliza_musicas());
        stmt.setString(index++, di.getInterpreta_historias());
        stmt.setString(index++, di.getFormula_perguntas());
        stmt.setString(index++, di.getUtiliza_gestos());
        stmt.setString(index++, di.getDemonstra_cooperacao());
        stmt.setString(index++, di.getTimido());
        stmt.setString(index++, di.getBirra());
        stmt.setString(index++, di.getPede_ajuda());
        stmt.setString(index++, di.getRi());
        stmt.setString(index++, di.getCompartilha());
        stmt.setString(index++, di.getDemonstra_amor());
        stmt.setString(index++, di.getChora());
        stmt.setString(index++, di.getInterage());
        stmt.setString(index++, di.getDetalhes_gravura());
        stmt.setString(index++, di.getReconhece_vozes());
        stmt.setString(index++, di.getReconhece_cancoes());
        stmt.setString(index++, di.getPercebe_texturas());
        stmt.setString(index++, di.getPercebe_cores());
        stmt.setString(index++, di.getDiscrimina_sons());
        stmt.setString(index++, di.getDiscrimina_odores());
        stmt.setString(index++, di.getAceita_texturas());
        stmt.setString(index++, di.getPercepcao_formas());
        stmt.setString(index++, di.getIdentifica_direcao_sons());
        stmt.setString(index++, di.getDiscrimina_sabores());
        stmt.setString(index++, di.getAcompanha_luz());
        stmt.setString(index++, di.getMovimento_pinca());
        stmt.setString(index++, di.getAmassa_papel());
        stmt.setString(index++, di.getCai_facilmente());
        stmt.setString(index++, di.getEncaixa_pecas());
        stmt.setString(index++, di.getRecorta());
        stmt.setString(index++, di.getUne_pontos());
        stmt.setString(index++, di.getCorre());
        stmt.setString(index++, di.getEmpilha());
        stmt.setString(index++, di.getAgitacao_motora());
        stmt.setString(index++, di.getAnda_reto());
        stmt.setString(index++, di.getSobe_escada());
        stmt.setString(index++, di.getArremessa_bola());
        stmt.setString(index++, di.getUsa_sanitario());
        stmt.setString(index++, di.getPenteia_cabelo());
        stmt.setString(index++, di.getVeste_se());
        stmt.setString(index++, di.getLava_maos());
        stmt.setString(index++, di.getBanha_se());
        stmt.setString(index++, di.getCalca_se());
        stmt.setString(index++, di.getReconhece_roupas());
        stmt.setString(index++, di.getAbre_torneira());
        stmt.setString(index++, di.getEscova_dentes());
        stmt.setString(index++, di.getDa_nos());
        stmt.setString(index++, di.getAbotoa_roupas());
        stmt.setString(index++, di.getIdentifica_partes_corpo());
        stmt.setString(index++, di.getGaratujas());
        stmt.setString(index++, di.getSilabico_alfabetico());
        stmt.setString(index++, di.getAlfabetico());
        stmt.setString(index++, di.getPre_silabico());
        stmt.setString(index++, di.getSilabico());
        stmt.setString(index++, di.getObservacoes());
        stmt.setInt(index++, di.getSincronizado());
        stmt.setInt(index++, di.getExcluido());
    }
    
    // Método para extrair DI do ResultSet
    private DI extrairDI(ResultSet rs) throws SQLException {
        return new DI(
            rs.getString("id"),
            rs.getString("educando_id"),
            rs.getString("professor_id"),
            rs.getString("data_criacao"),
            rs.getString("fala_nome"),
            rs.getString("fala_nascimento"),
            rs.getString("le_palavras"),
            rs.getString("fala_telefone"),
            rs.getString("emite_respostas"),
            rs.getString("transmite_recados"),
            rs.getString("fala_endereco"),
            rs.getString("fala_nome_pais"),
            rs.getString("compreende_ordens"),
            rs.getString("expoe_ideias"),
            rs.getString("reconta_historia"),
            rs.getString("usa_sistema_ca"),
            rs.getString("relata_fatos"),
            rs.getString("pronuncia_letras"),
            rs.getString("verbaliza_musicas"),
            rs.getString("interpreta_historias"),
            rs.getString("formula_perguntas"),
            rs.getString("utiliza_gestos"),
            rs.getString("demonstra_cooperacao"),
            rs.getString("timido"),
            rs.getString("birra"),
            rs.getString("pede_ajuda"),
            rs.getString("ri"),
            rs.getString("compartilha"),
            rs.getString("demonstra_amor"),
            rs.getString("chora"),
            rs.getString("interage"),
            rs.getString("detalhes_gravura"),
            rs.getString("reconhece_vozes"),
            rs.getString("reconhece_cancoes"),
            rs.getString("percebe_texturas"),
            rs.getString("percebe_cores"),
            rs.getString("discrimina_sons"),
            rs.getString("discrimina_odores"),
            rs.getString("aceita_texturas"),
            rs.getString("percepcao_formas"),
            rs.getString("identifica_direcao_sons"),
            rs.getString("discrimina_sabores"),
            rs.getString("acompanha_luz"),
            rs.getString("movimento_pinca"),
            rs.getString("amassa_papel"),
            rs.getString("cai_facilmente"),
            rs.getString("encaixa_pecas"),
            rs.getString("recorta"),
            rs.getString("une_pontos"),
            rs.getString("corre"),
            rs.getString("empilha"),
            rs.getString("agitacao_motora"),
            rs.getString("anda_reto"),
            rs.getString("sobe_escada"),
            rs.getString("arremessa_bola"),
            rs.getString("usa_sanitario"),
            rs.getString("penteia_cabelo"),
            rs.getString("veste_se"),
            rs.getString("lava_maos"),
            rs.getString("banha_se"),
            rs.getString("calca_se"),
            rs.getString("reconhece_roupas"),
            rs.getString("abre_torneira"),
            rs.getString("escova_dentes"),
            rs.getString("da_nos"),
            rs.getString("abotoa_roupas"),
            rs.getString("identifica_partes_corpo"),
            rs.getString("garatujas"),
            rs.getString("silabico_alfabetico"),
            rs.getString("alfabetico"),
            rs.getString("pre_silabico"),
            rs.getString("silabico"),
            rs.getString("observacoes"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }
}
