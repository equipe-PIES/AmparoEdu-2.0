package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.UsuarioRepository;
import java.util.UUID;

public class ProfessorService {

    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();

    public boolean cadastrarNovoProfessor(Professor professor, Usuario usuario) {
        // Validação prévia
        if (usuarioRepo.buscarPorEmail(usuario.getEmail()) != null) {
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail.");
        }
        if (professorRepo.buscarPorCpf(professor.getCpf()) != null) {
            throw new RuntimeException("Já existe um professor cadastrado com este CPF.");
        }

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

        // Salva os dados no banco
        usuarioRepo.salvar(usuario);
        
        try {
            professorRepo.salvar(professor);
        } catch (Exception e) {
            // Se falhar ao salvar o professor, remove o usuário criado para evitar inconsistência (rollback manual)
            usuarioRepo.deletarFisicamente(usuarioId);
            throw new RuntimeException("Erro ao salvar dados do professor: " + e.getMessage(), e);
        }

        System.out.println("Cadastro realizado com sucesso para: " + professor.getNome());
        return true;
    }
    
}
