package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.UsuarioRepository;

public class AuthService {
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    
    // Variável estática para manter o usuário logado durante toda a sessão
    private static Usuario usuarioLogado;

    public Usuario fazerLogin(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        // Validação simples de senha
        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuarioLogado = usuario;
            return usuario;
        }
        return null;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void logout() {
        usuarioLogado = null;
        new SincronizacaoService().pararAgendamento();
    }
}