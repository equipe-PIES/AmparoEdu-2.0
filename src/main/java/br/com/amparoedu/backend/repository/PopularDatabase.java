package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.model.DI;
import br.com.amparoedu.backend.repository.AnamneseRepository;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.PDIRepository;
import br.com.amparoedu.backend.repository.PAEERepository;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.repository.UsuarioRepository;
import br.com.amparoedu.backend.repository.DIRepository;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Esse arquivo é utilizado para inserir dados iniciais no banco e realizar populações de teste

// Caso queira adicionar qualquer um desses elementos no banco, lembre de modifica-los e char o método na main
// Dessa forma evitamos dados duplicados no banco
// Não modifique o método de popularDadosIniciais

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

    public static String adicionarUsuarioCoordenador(String email, String senha) {
        UsuarioRepository usuarioRepo = new UsuarioRepository();

        // evita duplicar pelo mesmo e-mail
        Usuario existente = usuarioRepo.buscarPorEmail(email);
        if (existente != null) {
            System.out.println("Coordenador já existe: " + existente.getId());
            return existente.getId();
        }

        String usuarioId = UUID.randomUUID().toString();
        Usuario usuario = new Usuario(usuarioId, email, senha, "COORDENADOR", 0, 0);
        usuarioRepo.salvar(usuario);
        System.out.println("Coordenador criado: " + usuarioId);
        return usuarioId;
    }

    public static String adicionarUsuarioProfessor(String email, String senha, String nomeProfessor) {
        String usuarioId = UUID.randomUUID().toString();
        String professorId = UUID.randomUUID().toString();

        UsuarioRepository usuarioRepo = new UsuarioRepository();
        ProfessorRepository professorRepo = new ProfessorRepository();

        Usuario usuario = new Usuario(usuarioId, email, senha, "PROFESSOR", 0, 0);
        usuarioRepo.salvar(usuario);

        Professor professor = new Professor(
            professorId,
            nomeProfessor,
            "000.000.000-00",
            "1990-01-01",
            "M",
            "Professor cadastrado automaticamente",
            0,
            0,
            usuarioId
        );
        professorRepo.salvar(professor);

        System.out.println("Professor e usuário criados: " + professorId);
        return professorId;
    }

    public static String adicionarEducando(String enderecoId, String nomeEducando) {
        String educandoId = UUID.randomUUID().toString();
        EducandoRepository educandoRepo = new EducandoRepository();

        Educando educando = new Educando(
            educandoId,
            enderecoId,
            nomeEducando,
            "000.000.000-00",
            "2015-01-01",
            "F",
            "Fundamental I",
            "CID-10 F81",
            "00000000000",
            "Escola Municipal",
            "Educando inserido automaticamente",
            0,
            0
        );

        educandoRepo.salvar(educando);
        System.out.println("Educando criado: " + educandoId);
        return educandoId;
    }

    public static String adicionarTurma(String professorId, String nomeTurma) {
        String turmaId = UUID.randomUUID().toString();
        TurmaRepository turmaRepo = new TurmaRepository();

        Turma turma = new Turma(
            turmaId,
            professorId,
            nomeTurma,
            "Tarde",
            "Fundamental I",
            "6-8 anos",
            0,
            0
        );

        turmaRepo.salvar(turma);
        System.out.println("Turma criada: " + turmaId);
        return turmaId;
    }

    public static String adicionarAnamnese(String educandoId, String professorId) {
        String anamneseId = UUID.randomUUID().toString();
        String dataCriacao = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        AnamneseRepository repo = new AnamneseRepository();

        Anamnese anamnese = new Anamnese(
            anamneseId,
            educandoId,
            professorId,
            dataCriacao,
            0,
            0,
            "",
            1,
            0,
            "",
            0,
            "",
            0,
            "",
            "3 anos",
            0,
            "",
            1,
            "Pais",
            "9 meses",
            1,
            0,
            "",
            "São Paulo",
            "Maternidade Central",
            "Normal",
            1,
            0,
            0,
            1,
            1,
            "3 meses",
            1,
            "6 meses",
            1,
            "6 meses",
            1,
            "12 meses",
            0,
            "",
            1,
            "12 meses",
            "4 meses",
            "12 meses",
            "18 meses",
            "Natural",
            0,
            "",
            "Sem observações",
            1,
            1,
            "Calmo",
            1,
            0,
            0,
            0,
            0,
            0
        );

        repo.salvar(anamnese);
        System.out.println("Anamnese criada: " + anamneseId);
        return anamneseId;
    }

    public static String adicionarPDI(String educando_id, String professor_id) {
        String pdiId = UUID.randomUUID().toString();
        String dataCriacao = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        PDIRepository repo = new PDIRepository();

        PDI pdi = new PDI(
            pdiId,
            educando_id,
            professor_id,
            dataCriacao,
            "2025",
            "13:00-14:00",
            "2x semana",
            "Segunda e Quarta",
            "Grupo 5",
            "Melhorar leitura",
            "Participa bem",
            "Déficit de atenção leve",
            "Compreende instruções",
            "Leitura e jogos",
            "Livros e jogos",
            "Nenhum",
            "Cartazes",
            "Família",
            0,
            0
        );

        repo.salvar(pdi);
        System.out.println("PDI criado: " + pdiId);
        return pdiId;
    }

    public static String adicionarPAEE(String educandoId, String professorId) {
        String paeeId = UUID.randomUUID().toString();
        String dataCriacao = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        PAEERepository repo = new PAEERepository();

        PAEE paee = new PAEE(
            paeeId,
            educandoId,
            professorId,
            dataCriacao,
            "Plano inicial",
            0,
            0,
            0,
            0,
            0,
            0,
            1,
            0,
            "Sem dif. motor",
            "Motricidade fina",
            "Sem dif. comunicacao",
            "Discussões em grupo",
            "Raciocínio lógico leve",
            "Problemas progressivos",
            "Atenção prolongada",
            "Atividades curtas",
            "Memória ok",
            "Repetição",
            "Percepção ok",
            "Estimulação sensorial",
            "Sociabilidade boa",
            "Atividades em grupo",
            "Melhorar concentração",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        );

        repo.salvar(paee);
        System.out.println("PAEE criado: " + paeeId);
        return paeeId;
    }

    public static String adicionarDI(String educandoId, String professorId) {
        String diId = UUID.randomUUID().toString();
        String dataCriacao = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        DIRepository repo = new DIRepository();

        DI di = new DI(
            diId,                    // id
            educandoId,              // educando_id
            professorId,             // professor_id
            dataCriacao,             // data_criacao
            // Comunicação (18)
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            // Afetivas/Sociais (9)
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            // Sensoriais (12)
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            // Motoras (12)
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            // AVDs (12)
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            // Níveis de aprendizagem (5)
            0, 0, 0, 0, 0,
            // Observações e flags (3)
            "", 0, 0
        );

        repo.salvar(di);
        System.out.println("DI criado: " + diId);
        return diId;
    }
   
}