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
            stmt.setString(76, di.getId());
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
        stmt.setInt(index++, di.getFala_nome());
        stmt.setInt(index++, di.getFala_nascimento());
        stmt.setInt(index++, di.getLe_palavras());
        stmt.setInt(index++, di.getFala_telefone());
        stmt.setInt(index++, di.getEmite_respostas());
        stmt.setInt(index++, di.getTransmite_recados());
        stmt.setInt(index++, di.getFala_endereco());
        stmt.setInt(index++, di.getFala_nome_pais());
        stmt.setInt(index++, di.getCompreende_ordens());
        stmt.setInt(index++, di.getExpoe_ideias());
        stmt.setInt(index++, di.getReconta_historia());
        stmt.setInt(index++, di.getUsa_sistema_ca());
        stmt.setInt(index++, di.getRelata_fatos());
        stmt.setInt(index++, di.getPronuncia_letras());
        stmt.setInt(index++, di.getVerbaliza_musicas());
        stmt.setInt(index++, di.getInterpreta_historias());
        stmt.setInt(index++, di.getFormula_perguntas());
        stmt.setInt(index++, di.getUtiliza_gestos());
        stmt.setInt(index++, di.getDemonstra_cooperacao());
        stmt.setInt(index++, di.getTimido());
        stmt.setInt(index++, di.getBirra());
        stmt.setInt(index++, di.getPede_ajuda());
        stmt.setInt(index++, di.getRi());
        stmt.setInt(index++, di.getCompartilha());
        stmt.setInt(index++, di.getDemonstra_amor());
        stmt.setInt(index++, di.getChora());
        stmt.setInt(index++, di.getInterage());
        stmt.setInt(index++, di.getDetalhes_gravura());
        stmt.setInt(index++, di.getReconhece_vozes());
        stmt.setInt(index++, di.getReconhece_cancoes());
        stmt.setInt(index++, di.getPercebe_texturas());
        stmt.setInt(index++, di.getPercebe_cores());
        stmt.setInt(index++, di.getDiscrimina_sons());
        stmt.setInt(index++, di.getDiscrimina_odores());
        stmt.setInt(index++, di.getAceita_texturas());
        stmt.setInt(index++, di.getPercepcao_formas());
        stmt.setInt(index++, di.getIdentifica_direcao_sons());
        stmt.setInt(index++, di.getDiscrimina_sabores());
        stmt.setInt(index++, di.getAcompanha_luz());
        stmt.setInt(index++, di.getMovimento_pinca());
        stmt.setInt(index++, di.getAmassa_papel());
        stmt.setInt(index++, di.getCai_facilmente());
        stmt.setInt(index++, di.getEncaixa_pecas());
        stmt.setInt(index++, di.getRecorta());
        stmt.setInt(index++, di.getUne_pontos());
        stmt.setInt(index++, di.getCorre());
        stmt.setInt(index++, di.getEmpilha());
        stmt.setInt(index++, di.getAgitacao_motora());
        stmt.setInt(index++, di.getAnda_reto());
        stmt.setInt(index++, di.getSobe_escada());
        stmt.setInt(index++, di.getArremessa_bola());
        stmt.setInt(index++, di.getUsa_sanitario());
        stmt.setInt(index++, di.getPenteia_cabelo());
        stmt.setInt(index++, di.getVeste_se());
        stmt.setInt(index++, di.getLava_maos());
        stmt.setInt(index++, di.getBanha_se());
        stmt.setInt(index++, di.getCalca_se());
        stmt.setInt(index++, di.getReconhece_roupas());
        stmt.setInt(index++, di.getAbre_torneira());
        stmt.setInt(index++, di.getEscova_dentes());
        stmt.setInt(index++, di.getDa_nos());
        stmt.setInt(index++, di.getAbotoa_roupas());
        stmt.setInt(index++, di.getIdentifica_partes_corpo());
        stmt.setInt(index++, di.getGaratujas());
        stmt.setInt(index++, di.getSilabico_alfabetico());
        stmt.setInt(index++, di.getAlfabetico());
        stmt.setInt(index++, di.getPre_silabico());
        stmt.setInt(index++, di.getSilabico());
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
            rs.getInt("fala_nome"),
            rs.getInt("fala_nascimento"),
            rs.getInt("le_palavras"),
            rs.getInt("fala_telefone"),
            rs.getInt("emite_respostas"),
            rs.getInt("transmite_recados"),
            rs.getInt("fala_endereco"),
            rs.getInt("fala_nome_pais"),
            rs.getInt("compreende_ordens"),
            rs.getInt("expoe_ideias"),
            rs.getInt("reconta_historia"),
            rs.getInt("usa_sistema_ca"),
            rs.getInt("relata_fatos"),
            rs.getInt("pronuncia_letras"),
            rs.getInt("verbaliza_musicas"),
            rs.getInt("interpreta_historias"),
            rs.getInt("formula_perguntas"),
            rs.getInt("utiliza_gestos"),
            rs.getInt("demonstra_cooperacao"),
            rs.getInt("timido"),
            rs.getInt("birra"),
            rs.getInt("pede_ajuda"),
            rs.getInt("ri"),
            rs.getInt("compartilha"),
            rs.getInt("demonstra_amor"),
            rs.getInt("chora"),
            rs.getInt("interage"),
            rs.getInt("detalhes_gravura"),
            rs.getInt("reconhece_vozes"),
            rs.getInt("reconhece_cancoes"),
            rs.getInt("percebe_texturas"),
            rs.getInt("percebe_cores"),
            rs.getInt("discrimina_sons"),
            rs.getInt("discrimina_odores"),
            rs.getInt("aceita_texturas"),
            rs.getInt("percepcao_formas"),
            rs.getInt("identifica_direcao_sons"),
            rs.getInt("discrimina_sabores"),
            rs.getInt("acompanha_luz"),
            rs.getInt("movimento_pinca"),
            rs.getInt("amassa_papel"),
            rs.getInt("cai_facilmente"),
            rs.getInt("encaixa_pecas"),
            rs.getInt("recorta"),
            rs.getInt("une_pontos"),
            rs.getInt("corre"),
            rs.getInt("empilha"),
            rs.getInt("agitacao_motora"),
            rs.getInt("anda_reto"),
            rs.getInt("sobe_escada"),
            rs.getInt("arremessa_bola"),
            rs.getInt("usa_sanitario"),
            rs.getInt("penteia_cabelo"),
            rs.getInt("veste_se"),
            rs.getInt("lava_maos"),
            rs.getInt("banha_se"),
            rs.getInt("calca_se"),
            rs.getInt("reconhece_roupas"),
            rs.getInt("abre_torneira"),
            rs.getInt("escova_dentes"),
            rs.getInt("da_nos"),
            rs.getInt("abotoa_roupas"),
            rs.getInt("identifica_partes_corpo"),
            rs.getInt("garatujas"),
            rs.getInt("silabico_alfabetico"),
            rs.getInt("alfabetico"),
            rs.getInt("pre_silabico"),
            rs.getInt("silabico"),
            rs.getString("observacoes"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }
}
