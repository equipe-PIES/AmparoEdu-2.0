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

   
}