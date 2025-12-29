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
}
