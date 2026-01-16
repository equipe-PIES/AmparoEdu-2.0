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

        try {
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
        try {
            // Atualiza o professor
            professor.setSincronizado(0); // Marca como não sincronizado para upload
            professorRepo.atualizar(professor);
            
            // Atualiza o usuário
            usuario.setSincronizado(0);

            String senha = usuario.getSenha();
            if (senha != null && !senha.isBlank() && !isBcryptHash(senha)) {
                String senhaHasheada = BCrypt.hashpw(senha, BCrypt.gensalt());
                usuario.setSenha(senhaHasheada);
            }
            usuarioRepo.atualizar(usuario);
            
            System.out.println("DEBUG: Professor e usuário atualizados: " + professor.getNome());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar professor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirProfessor(String professorId) {
        try {
            // Busca o professor
            Professor professor = professorRepo.buscarPorId(professorId);
            if (professor == null) {
                return false;
            }
            
            // Marca o professor como excluído
            professor.setExcluido(1);
            professor.setSincronizado(0);
            professorRepo.atualizar(professor);
            
            // Marca o usuário associado como excluído
            if (professor.getUsuario_id() != null) {
                Usuario usuario = usuarioRepo.buscarPorId(professor.getUsuario_id());
                if (usuario != null) {
                    usuario.setExcluido(1);
                    usuario.setSincronizado(0);
                    usuarioRepo.atualizar(usuario);
                }
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isBcryptHash(String valor) {
        return valor.startsWith("$2a$") || valor.startsWith("$2b$") || valor.startsWith("$2y$");
    }
    
}
