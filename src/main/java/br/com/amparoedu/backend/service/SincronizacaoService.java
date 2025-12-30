package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.*;
import br.com.amparoedu.backend.repository.*;
import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SincronizacaoService {
    private final SupabaseClient nuvemClient = new SupabaseClient();
    private final Gson gson = new Gson();
    private final UsuarioRepository userRepo = new UsuarioRepository();
    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();
    private final EnderecoRepository enderecoRepo = new EnderecoRepository();
    private final ResponsavelRepository responsavelRepo = new ResponsavelRepository();
    private final TurmaRepository turmaRepo = new TurmaRepository();
    private final TurmaEducandoRepository turmaEducandoRepo = new TurmaEducandoRepository();
    private final AnamneseRepository anamneseRepo = new AnamneseRepository();
    private final PDIRepository pdiRepo = new PDIRepository();

    private static ScheduledExecutorService scheduler;

    //faz com que a sincronização ocorra obrigatoriamente a cada 5 minutos
    public void iniciarAgendamento() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            
            scheduler.scheduleAtFixedRate(() -> {
                sincronizarTudo();
            }, 0, 5, TimeUnit.MINUTES);
            
        }
    }

    // interrompe a sincronização agendada
    public void pararAgendamento() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    public void sincronizarTudo() {
        System.out.println("Iniciando sincronização completa...");

        try {
            sincronizarUsuarios();
            sincronizarProfessores();
            sincronizarEducandos();
            sincronizarEnderecos();
            sincronizarResponsaveis();
            sincronizarTurmas();
            sincronizarTurmaEducandos();
            sincronizarAnamneses();
            sincronizarPDIs();

            System.out.println("Sincronização finalizada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro durante a sincronização geral: " + e.getMessage());
        }
    }

    private void sincronizarUsuarios() {
        // upload das modificações locais 
        List<Usuario> pendentes = userRepo.naoSincronizados();
        for (Usuario u : pendentes) {
            u.setSincronizado(1); // marca como sincronizado antes de enviar
            if (nuvemClient.enviarParaNuvem("usuarios", u)) { 
                userRepo.atualizarStatusSincronia(u.getId());
            } else {
                u.setSincronizado(0); // Reverte se falhar 
            }
        }

        // download das modificações da nuvem 
        String json = nuvemClient.buscarDaNuvem("usuarios"); 
        if (json != null && !json.equals("[]")) {
            Usuario[] daNuvem = gson.fromJson(json, Usuario[].class); 
            for (Usuario uNuvem : daNuvem) {
                uNuvem.setSincronizado(1); 
                Usuario local = userRepo.buscarPorId(uNuvem.getId()); 
                
                if (local == null) {
                    userRepo.salvar(uNuvem); 
                } else {
                    userRepo.atualizar(uNuvem);
                }
            }
        }
    }

    private void sincronizarProfessores() {
        List<Professor> pendentes = professorRepo.buscarNaoSincronizados();
        for (Professor p : pendentes) {
            p.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("professores", p)) {
                professorRepo.atualizarSincronizacao(p.getId(), 1);
            } else {
                p.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("professores");
        if (json != null && !json.equals("[]")) {
            Professor[] daNuvem = gson.fromJson(json, Professor[].class);
            for (Professor pNuvem : daNuvem) {
                pNuvem.setSincronizado(1);
                Professor local = professorRepo.buscarPorId(pNuvem.getId());
                
                if (local == null) {
                    professorRepo.salvar(pNuvem);
                } else {
                    professorRepo.atualizarSincronizacao(pNuvem.getId(), 1);
                }
            }
        }
    }

    private void sincronizarEducandos() {
        List<Educando> pendentes = educandoRepo.buscarNaoSincronizados();
        for (Educando e : pendentes) {
            e.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("educandos", e)) {
                educandoRepo.atualizarSincronizacao(e.getId(), 1);
            } else {
                e.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("educandos");
        if (json != null && !json.equals("[]")) {
            Educando[] daNuvem = gson.fromJson(json, Educando[].class);
            for (Educando eNuvem : daNuvem) {
                eNuvem.setSincronizado(1);
                Educando local = educandoRepo.buscarPorId(eNuvem.getId());
                
                if (local == null) {
                    educandoRepo.salvar(eNuvem);
                } else {
                    educandoRepo.atualizarSincronizacao(eNuvem.getId(), 1);
                }
            }
        }
    }

    private void sincronizarEnderecos() {
        List<Endereco> pendentes = enderecoRepo.buscarNaoSincronizados();
        for (Endereco end : pendentes) {
            end.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("enderecos", end)) {
                enderecoRepo.atualizarSincronizacao(end.getId(), 1);
            } else {
                end.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("enderecos");
        if (json != null && !json.equals("[]")) {
            Endereco[] daNuvem = gson.fromJson(json, Endereco[].class);
            for (Endereco eNuvem : daNuvem) {
                eNuvem.setSincronizado(1);
                Endereco local = enderecoRepo.buscarPorId(eNuvem.getId());
                
                if (local == null) {
                    enderecoRepo.salvar(eNuvem);
                } else {
                    enderecoRepo.atualizarSincronizacao(eNuvem.getId(), 1);
                }
            }
        }
    }

    private void sincronizarResponsaveis() {
        List<Responsavel> pendentes = responsavelRepo.buscarNaoSincronizados();
        for (Responsavel r : pendentes) {
            r.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("responsaveis", r)) {
                responsavelRepo.atualizarSincronizacao(r.getId(), 1);
            } else {
                r.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("responsaveis");
        if (json != null && !json.equals("[]")) {
            Responsavel[] daNuvem = gson.fromJson(json, Responsavel[].class);
            for (Responsavel rNuvem : daNuvem) {
                rNuvem.setSincronizado(1);
                Responsavel local = responsavelRepo.buscarPorId(rNuvem.getId());
                
                if (local == null) {
                    responsavelRepo.salvar(rNuvem);
                } else {
                    responsavelRepo.atualizarSincronizacao(rNuvem.getId(), 1);
                }
            }
        }
    }

    private void sincronizarTurmas() {
        List<Turma> pendentes = turmaRepo.buscarNaoSincronizados();
        for (Turma t : pendentes) {
            t.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("turmas", t)) {
                turmaRepo.atualizarSincronizacao(t.getId(), 1);
            } else {
                t.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("turmas");
        if (json != null && !json.equals("[]")) {
            Turma[] daNuvem = gson.fromJson(json, Turma[].class);
            for (Turma tNuvem : daNuvem) {
                tNuvem.setSincronizado(1);
                Turma local = turmaRepo.buscarPorId(tNuvem.getId());
                
                if (local == null) {
                    turmaRepo.salvar(tNuvem);
                } else {
                    turmaRepo.atualizarSincronizacao(tNuvem.getId(), 1);
                }
            }
        }
    }
   
    private void sincronizarTurmaEducandos() {
        List<TurmaEducando> pendentes = turmaEducandoRepo.buscarNaoSincronizados();
        for (TurmaEducando te : pendentes) {
            te.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("turma_educando", te)) {
                turmaEducandoRepo.atualizarSincronizacao(te.getTurma_id(), te.getEducando_id(), 1);
            } else {
                te.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("turma_educando");
        if (json != null && !json.equals("[]")) {
            TurmaEducando[] daNuvem = gson.fromJson(json, TurmaEducando[].class);
            for (TurmaEducando teNuvem : daNuvem) {
                teNuvem.setSincronizado(1);
                TurmaEducando local = turmaEducandoRepo.buscarPorIds(teNuvem.getTurma_id(), teNuvem.getEducando_id());
                
                if (local == null) {
                    turmaEducandoRepo.salvar(teNuvem);
                } else {
                    turmaEducandoRepo.atualizarSincronizacao(teNuvem.getTurma_id(), teNuvem.getEducando_id(), 1);
                }
            }
        }
    }

    private void sincronizarAnamneses() {
        List<Anamnese> pendentes = anamneseRepo.buscarNaoSincronizados();
        for (Anamnese a : pendentes) {
            a.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("anamneses", a)) {
                anamneseRepo.atualizarSincronizacao(a.getId(), 1);
            } else {
                a.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("anamneses");
        if (json != null && !json.equals("[]")) {
            Anamnese[] daNuvem = gson.fromJson(json, Anamnese[].class);
            for (Anamnese aNuvem : daNuvem) {
                aNuvem.setSincronizado(1);
                Anamnese local = anamneseRepo.buscarPorId(aNuvem.getId());
                
                if (local == null) {
                    anamneseRepo.salvar(aNuvem);
                } else {
                    anamneseRepo.atualizarSincronizacao(aNuvem.getId(), 1);
                }
            }
        }
    }

    private void sincronizarPDIs() {
        List<PDI> pendentes = pdiRepo.buscarNaoSincronizados();
        for (PDI p : pendentes) {
            p.setSincronizado(1);
            if (nuvemClient.enviarParaNuvem("pdis", p)) {
                pdiRepo.atualizarSincronizacao(p.getId(), 1);
            } else {
                p.setSincronizado(0);
            }
        }

        String json = nuvemClient.buscarDaNuvem("pdis");
        if (json != null && !json.equals("[]")) {
            PDI[] daNuvem = gson.fromJson(json, PDI[].class);
            for (PDI pNuvem : daNuvem) {
                pNuvem.setSincronizado(1);
                PDI local = pdiRepo.buscarPorId(pNuvem.getId());
                
                if (local == null) {
                    pdiRepo.salvar(pNuvem);
                } else {
                    pdiRepo.atualizarSincronizacao(pNuvem.getId(), 1);
                }
            }
        }
    }
}