package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    //Busca um usuário pelo email
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE LOWER(email) = LOWER(?) AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extrairUsuario(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    //Busca um usuário pelo ID
    public Usuario buscarPorId(String id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extrairUsuario(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    //Salva um novo usuário no banco de dados
    // Salva um novo usuário no banco de dados
    public int salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (id, email, senha, tipo, sincronizado, excluido) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());
            stmt.setInt(5, usuario.getSincronizado());
            stmt.setInt(6, 0);
            
            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("DEBUG: Usuario salvo - Linhas afetadas: " + linhasAfetadas);
            return linhasAfetadas;
            
        } catch (SQLException e) {
            System.err.println("ERRO ao salvar usuário: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    //Atualiza um usuário existente no banco de dados
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET email = ?, senha = ?, tipo = ?, sincronizado = ?, excluido = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipo());
            stmt.setInt(4, usuario.getSincronizado());
            stmt.setInt(5, usuario.getExcluido());
            stmt.setString(6, usuario.getId());
            stmt.executeUpdate(); // Executa a atualização
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Exclui um usuário do banco usando o metodo soft delete, ou seja, ele irá apenas marcar o usuário como excluído
    public void excluir(String id) {
        String sql = "UPDATE usuarios SET excluido = 1, sincronizado = 0 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Busca usuários não sincronizados
    public List<Usuario> buscarNaoSincronizados() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE sincronizado = 0 AND excluido = 0";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(extrairUsuario(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return usuarios;
    }

    // Atualiza o status de sincronização
    public void atualizarSincronizacao(String id, int sincronizado) {
        String sql = "UPDATE usuarios SET sincronizado = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sincronizado);
            stmt.setString(2, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // evita repetição no código
    private Usuario extrairUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getString("id"),
            rs.getString("email"),
            rs.getString("senha"),
            rs.getString("tipo"),
            rs.getInt("sincronizado"),
            rs.getInt("excluido")
        );
    }


    }