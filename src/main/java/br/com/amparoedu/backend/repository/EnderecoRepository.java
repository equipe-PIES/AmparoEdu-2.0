package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoRepository {
    // Salva um novo endereco
    public void salvar(Endereco endereco) {
        String sql = "INSERT INTO enderecos (id, cep, uf, cidade, bairro, logradouro, numero, complemento, sincronizado, excluido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, endereco.getId());
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getUf());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getLogradouro());
            stmt.setString(7, endereco.getNumero());
            stmt.setString(8, endereco.getComplemento());
            stmt.setInt(9, endereco.getSincronizado());
            stmt.setInt(10, endereco.getExcluido());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Atualiza os dados de um endereco
    public void atualizar(Endereco endereco) {
        String sql = "UPDATE enderecos SET cep = ?, uf = ?, cidade = ?, bairro = ?, logradouro = ?, numero = ?, complemento = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getUf());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getLogradouro());
            stmt.setString(6, endereco.getNumero());
            stmt.setString(7, endereco.getComplemento());
            stmt.setInt(8, endereco.getSincronizado());
            stmt.setInt(9, endereco.getExcluido());
            stmt.setString(10, endereco.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // exclusao lógica de um endereco
    public void excluirLogicamente(String id) {
        String sql = "UPDATE enderecos SET excluido = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca por ID
    public Endereco buscarPorId(String id) {
        String sql = "SELECT * FROM enderecos WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairEndereco(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Buscar não sincronizados
    public List<Endereco> buscarNaoSincronizados() {
        String sql = "SELECT * FROM enderecos WHERE sincronizado = 0";
        List<Endereco> enderecos = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                enderecos.add(extrairEndereco(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enderecos;
    }

    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int status) {
        String sql = "UPDATE enderecos SET sincronizado = 1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Extrai um Endereco do ResultSet
    private Endereco extrairEndereco(ResultSet rs) throws SQLException {
        return new Endereco(
            rs.getString("id"),
            rs.getString("cep"),
            rs.getString("uf"),
            rs.getString("cidade"),
            rs.getString("bairro"),
            rs.getString("logradouro"),
            rs.getString("numero"),
            rs.getString("complemento"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }

}
