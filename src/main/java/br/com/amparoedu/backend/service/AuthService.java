package br.com.amparoedu.backend.service;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.DatabaseConfig;
import br.com.amparoedu.backend.repository.UsuarioRepository;

public class AuthService {
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    
    // Variável estática para manter o usuário logado durante toda a sessão
    private static Usuario usuarioLogado;

    public Usuario fazerLogin(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        // Validação da senha usando bcrypt e verifica se o usuário não está excluído
        if (usuario != null && usuario.getExcluido() == 0 && BCrypt.checkpw(senha, usuario.getSenha())) {
            usuarioLogado = usuario;
            return usuario;
        }
        return null;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static String getIdProfessorLogado() {
    Usuario usuario = getUsuarioLogado();
    if (usuario == null) return null;
    
    String sql = "SELECT id FROM professores WHERE usuario_id = ?";
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, usuario.getId());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("id"); // ID da tabela professores
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    public static void logout() {
        // Realiza sincronização final antes de fazer logout
        SincronizacaoService syncService = new SincronizacaoService();
        syncService.sincronizarTudo();
        syncService.pararAgendamento();
        
        usuarioLogado = null;
    }
}