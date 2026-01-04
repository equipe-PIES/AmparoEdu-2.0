package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.TurmaRepository;
import java.util.UUID;

public class TurmaService {

    private final TurmaRepository turmaRepo = new TurmaRepository();

    public boolean cadastrarNovaTurma(Turma turma) {
        try {
            // Gera ID único
            String turmaId = UUID.randomUUID().toString();
            turma.setId(turmaId);
            turma.setSincronizado(0);
            turma.setExcluido(0);

            // Salva os dados no banco
            turmaRepo.salvar(turma);

            System.out.println("Cadastro de turma realizado com sucesso: " + turma.getNome());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar turma: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarTurma(Turma turma) {
        try {
            turma.setSincronizado(0);
            turmaRepo.atualizar(turma);
            System.out.println("Atualização de turma realizada com sucesso: " + turma.getNome());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
