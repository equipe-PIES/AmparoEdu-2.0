package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.TurmaEducando;   
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.TurmaEducandoRepository;
import java.util.UUID;

public class TurmaService {
    private final TurmaRepository turmaRepo = new TurmaRepository();
    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final TurmaEducandoRepository turmaEducandoRepo = new TurmaEducandoRepository();

    public boolean cadastrarNovaTurma(Turma turma) throws Exception {
        if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
            throw new Exception("O nome da turma é obrigatório.");
        }

        if (turma.getProfessor_id() == null || professorRepo.buscarPorId(turma.getProfessor_id()) == null) {
            throw new Exception("Professor responsável não encontrado ou não selecionado.");
        }

        try {
            turma.setId(UUID.randomUUID().toString());
            turma.setSincronizado(0); 
            turma.setExcluido(0);
            turmaRepo.salvar(turma);
            return true;
        } catch (Exception e) {
            throw new Exception("Erro ao salvar a turma: " + e.getMessage());
        }
    }

    public boolean atualizarTurma(Turma turma) throws Exception {
        if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
            throw new Exception("O nome da turma é obrigatório.");
        }

        try {
            turma.setSincronizado(0);
            turmaRepo.atualizar(turma);
            return true;
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar a turma: " + e.getMessage());
        }
    }

    public boolean atribuirAlunoATurma(String turmaId, String educandoId) {
        try {
            turmaEducandoRepo.salvar(new TurmaEducando(turmaId, educandoId, 0, 0));
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao vincular aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean desvincularAlunoDaTurma(String turmaId, String educandoId) {
        try {
            turmaEducandoRepo.desvincular(turmaId, educandoId, 0);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao desvincular aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirTurma(String turmaId) {
        try {
            Turma turma = turmaRepo.buscarPorId(turmaId);
            if (turma == null) {
                return false;
            }
            
            // Marca a turma como excluída
            turma.setExcluido(1);
            turma.setSincronizado(0);
            turmaRepo.atualizar(turma);
            
            // Marca todos os vínculos de alunos como excluídos
            java.util.List<TurmaEducando> vinculos = turmaEducandoRepo.buscarPorTurma(turmaId);
            if (vinculos != null) {
                for (TurmaEducando vinculo : vinculos) {
                    vinculo.setExcluido(1);
                    vinculo.setSincronizado(0);
                    turmaEducandoRepo.atualizar(vinculo);
                }
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
