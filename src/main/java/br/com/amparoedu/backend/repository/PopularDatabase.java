package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Usuario;
import java.util.UUID;

// Esse arquivo será útil para popular o banco de dados com um usuário inicial e para futuramente inserir vários dados de uma vez no banco.

public class PopularDatabase {
    public static void popularDadosIniciais() {
        UsuarioRepository repo = new UsuarioRepository();
        
        // Verifica se já existe um admin para não duplicar
        if (repo.buscarPorEmail("coordenador@test.com") == null) {
            Usuario admin = new Usuario(
                UUID.randomUUID().toString(), 
                "coordenador@test.com", 
                "1234", 
                "COORDENADOR",
                0, // indica que o novo usuário ainda não está sincronizado ao supabase
                0
            );
            repo.salvar(admin);
            System.out.println("Usuário administrador inicial criado.");
        }
    }
}