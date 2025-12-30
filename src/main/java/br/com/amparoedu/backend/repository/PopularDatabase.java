package br.com.amparoedu.backend.repository;

import br.com.amparoedu.backend.model.Anamnese;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.PAEE;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.TurmaEducando;
import br.com.amparoedu.backend.model.Usuario;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Esse arquivo será útil para popular o banco de dados com um usuário inicial e para futuramente inserir vários dados de uma vez no banco.

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

    public static void inserirAnamnesePDIePAEE() {
        String educandoId = "bbf20a5d-9eb7-45fa-9810-e6f2319aa35f";
        String professorId = "2";
        String dataCriacao = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        // Verificar se educando e professor existem
        EducandoRepository educandoRepo = new EducandoRepository();
        ProfessorRepository professorRepo = new ProfessorRepository();
        
        if (educandoRepo.buscarPorId(educandoId) == null) {
            System.err.println("Erro: Educando com ID " + educandoId + " não encontrado.");
            return;
        }
        
        if (professorRepo.buscarPorId(professorId) == null) {
            System.err.println("Erro: Professor com ID " + professorId + " não encontrado.");
            return;
        }


        // Inserir Anamnese
        Anamnese anamnese = new Anamnese(
            UUID.randomUUID().toString(),
            educandoId,
            professorId,
            dataCriacao,
            0, // tem_convulsao
            0, // tem_convenio_medico
            "", // nome_convenio
            1, // vacinas_em_dia
            0, // teve_doenca_contagiosa
            "", // quais_doencas
            0, // usa_medicacao
            "", // quais_medicacoes
            0, // usou_servico_saude_educacao
            "", // quais_servicos
            "3 anos", // inicio_escolarizacao
            0, // apresenta_dificuldades
            "", // quais_dificuldades
            1, // recebe_apoio_pedagogico_casa
            "Pais", // apoio_quem
            "9 meses", // duracao_da_gestacao
            1, // fez_prenatal
            0, // houve_prematuridade
            "", // causa_prematuridade
            "São Paulo", // cidade_nascimento
            "Maternidade Test", // maternidade
            "Normal", // tipo_parto
            1, // chorou_ao_nascer
            0, // ficou_roxo
            0, // usou_incubadora
            1, // foi_amamentado
            1, // sustentou_a_cabeca
            "3 meses", // quantos_meses_sustentou_cabeca
            1, // engatinhou
            "6 meses", // quantos_meses_engatinhou
            1, // sentou
            "6 meses", // quantos_meses_sentou
            1, // andou
            "12 meses", // quantos_meses_andou
            0, // precisou_de_terapia
            "", // qual_motivo_terapia
            1, // falou
            "12 meses", // quantos_meses_falou
            "4 meses", // quantos_meses_balbuciou
            "12 meses", // quando_primeiras_palavras
            "18 meses", // quando_primeiras_frases
            "Natural", // fala_natural_inibido
            0, // possui_disturbio
            "", // qual_disturbio
            "Educando sem maiores dificuldades", // observacoes_adicionais
            1, // dorme_sozinho
            1, // tem_seu_quarto
            "Calmo", // sono_calmo_agitado
            1, // respeita_regras
            0, // e_desmotivado
            0, // e_agressivo
            0, // apresenta_inquietacao
            0, // sincronizado
            0  // excluido
        );

        AnamneseRepository anamneseRepo = new AnamneseRepository();
        anamneseRepo.salvar(anamnese);
        System.out.println("Anamnese inserida com sucesso.");

        // Inserir PDI
        PDI pdi = new PDI(
            UUID.randomUUID().toString(),
            educandoId,
            professorId,
            dataCriacao,
            "Ano letivo 2025", // periodoAee
            "13:00 - 14:00", // horarioAtendimento
            "2 vezes por semana", // frequenciaAtendimento
            "Segunda e Quarta", // diasAtendimento
            "Grupo de 5 educandos", // composicaoGrupo
            "Desenvolver habilidades de leitura e escrita", // objetivos
            "Ótima participação em atividades em grupo", // potencialidades
            "Dificuldade leve em concentração", // necessidadesEspeciais
            "Comunicação clara, entende instruções", // habilidades
            "Atividades de leitura, escrita e jogos pedagógicos", // atividades
            "Livros, material didático, jogos", // recursosMateriais
            "Nenhum", // recursosNecessitamAdaptacao
            "Cartazes educativos", // recursosNecessitamProduzir
            "Apoio da família", // parceriasNecessarias
            0, // sincronizado
            0  // excluido
        );

        PDIRepository pdiRepo = new PDIRepository();
        pdiRepo.salvar(pdi);
        System.out.println("PDI inserido com sucesso.");

        // Inserir PAEE
        PAEE paee = new PAEE(
            UUID.randomUUID().toString(),
            educandoId,
            professorId,
            dataCriacao,
            "Educando com desenvolvimento típico, necessita de apoio em concentração", // resumo
            0, // dificuldades_motoras
            0, // dificuldades_cognitivas
            0, // dificuldades_sensoriais
            0, // dificuldades_comunicacao
            0, // dificuldades_familiares
            0, // dificuldades_afetivas
            1, // dificuldades_raciocinio
            0, // dificuldades_avas
            "Sem dificuldades motoras", // dif_des_motor
            "Atividades de motricidade fina diárias", // intervencoes_motor
            "Comunicação clara e apropriada", // dif_comunicacao
            "Estimular discussões em grupo", // intervencoes_comunicacao
            "Dificuldade leve em raciocínio lógico", // dif_raciocinio
            "Problemas matemáticos progressivos", // intervencoes_raciocinio
            "Dificuldade em concentração prolongada", // dif_atencao
            "Atividades com duração progressiva", // intervencoes_atencao
            "Memória dentro dos padrões esperados", // dif_memoria
            "Reforço de aprendizado com repetição", // intervencoes_memoria
            "Percepção visual e auditiva normais", // dif_percepcao
            "Estimulação sensorial variada", // intervencoes_percepcao
            "Socializa bem com pares", // dif_sociabilidade
            "Atividades em grupo diversificadas", // intervencoes_sociabilidade
            "Melhorar concentração e desempenho acadêmico", // objetivo_plano
            0, // aee
            0, // psicologo
            0, // fisioterapeuta
            0, // psicopedagogo
            0, // terapeuta_ocupacional
            0, // educacao_fisica
            0, // estimulacao_precoce
            0, // sincronizado
            0  // excluido
        );

        PAEERepository paeeRepo = new PAEERepository();
        paeeRepo.salvar(paee);
        System.out.println("PAEE inserido com sucesso.");

        // Inserir Turma
        Turma turma = new Turma(
            UUID.randomUUID().toString(),
            professorId,
            "Turma AEE 2025",
            "6-8 anos",
            "Fundamental I",
            "Tarde",
            0,
            0
        );

        TurmaRepository turmaRepo = new TurmaRepository();
        turmaRepo.salvar(turma);
        System.out.println("Turma inserida com sucesso.");

        // Vincular educando à turma
        TurmaEducando turmaEducando = new TurmaEducando(
            turma.getId(),
            educandoId,
            0,
            0
        );

        TurmaEducandoRepository turmaEducandoRepo = new TurmaEducandoRepository();
        turmaEducandoRepo.salvar(turmaEducando);
        System.out.println("Educando vinculado à turma com sucesso.");
    }
}