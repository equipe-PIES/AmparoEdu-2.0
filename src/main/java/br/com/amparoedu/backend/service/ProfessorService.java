package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.UsuarioRepository;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

public class ProfessorService {

    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();

    public boolean cadastrarNovoProfessor(Professor professor, Usuario usuario) {
        // Gera IDs únicos
        String professorId = UUID.randomUUID().toString();
        String usuarioId = UUID.randomUUID().toString();

        // Define o ID e sincronização do usuario
        usuario.setId(usuarioId);
        usuario.setSincronizado(0);

        // Vincula o usuário ao professor criado
        professor.setId(professorId);
        professor.setUsuario_id(usuarioId);
        professor.setSincronizado(0);

        // Hash da senha usando bcrypt
        String senhaHasheada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaHasheada);

        // Salva os dados no banco
        professorRepo.salvar(professor);
        usuarioRepo.salvar(usuario);

        System.out.println("Cadastro realizado com sucesso para: " + professor.getNome());
        return true;
    }
    
}
