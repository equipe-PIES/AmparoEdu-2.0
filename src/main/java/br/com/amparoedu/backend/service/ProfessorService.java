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
        // Gera IDs únicos
        String professorId = UUID.randomUUID().toString();
        String usuarioId = UUID.randomUUID().toString();

        try {
            // Define o ID e sincronização do usuario
            usuario.setId(usuarioId);
            usuario.setSincronizado(0);

            // Define o ID do professor e vincula o usuário
            professor.setId(professorId);
            professor.setUsuario_id(usuarioId);
            professor.setSincronizado(0);

            System.out.println("DEBUG: Salvando usuário ID: " + usuarioId);
            boolean usuarioSalvo = usuarioRepo.salvar(usuario) > 0;
            
            if (!usuarioSalvo) {
                System.out.println("DEBUG: Falha ao salvar usuário!");
                return false;
            }
            
            System.out.println("DEBUG: Usuário salvo. Salvando professor ID: " + professorId);
            boolean professorSalvo = professorRepo.salvar(professor) > 0;
            
            if (!professorSalvo) {
                System.out.println("DEBUG: Falha ao salvar professor!");
                // Opcional: reverter o usuário salvo
                usuarioRepo.excluir(usuarioId);
                return false;
            }
            
            System.out.println("Cadastro realizado com sucesso para: " + professor.getNome());
            return true;
            
        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao cadastrar professor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean atualizarProfessor(Professor professor, Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarProfessor'");
    }
    
}
