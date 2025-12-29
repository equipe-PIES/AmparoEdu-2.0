package br.com.amparoedu.backend.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:sqlite:amparo_local.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        
        conn.createStatement().execute("PRAGMA foreign_keys = ON;");
        return conn;
    }

    public static void inicializarBanco() {

        // Por ser sincronizado ao supabase, nosso banco está utilizando soft delete (coluna excluido) para fazer a exclusão
        // dos dados e também possui uma coluna de sincronização para controlar o que já foi sincronizado.

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            
            stmt.execute("BEGIN TRANSACTION;");

            // --- 1. TABELAS BÁSICAS ---
            // Usuário
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "email TEXT UNIQUE NOT NULL, " +
                        "senha TEXT NOT NULL, " +
                        "tipo TEXT NOT NULL, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "excluido INTEGER DEFAULT 0)");
            // Endereço
            stmt.execute("CREATE TABLE IF NOT EXISTS enderecos (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "cep TEXT NOT NULL, " +
                        "uf TEXT NOT NULL, " +
                        "cidade TEXT NOT NULL, " +
                        "bairro TEXT NOT NULL, " +
                        "logradouro TEXT NOT NULL, " +
                        "numero TEXT NOT NULL, " +
                        "complemento TEXT, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "excluido INTEGER DEFAULT 0)");

            // --- 2. TABELAS DE PESSOAS ---
            // Professor depende de Usuário
            stmt.execute("CREATE TABLE IF NOT EXISTS professores (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "usuario_id TEXT NOT NULL, " +
                        "nome TEXT NOT NULL, " +
                        "cpf TEXT UNIQUE NOT NULL, " +
                        "data_nascimento TEXT NOT NULL, " +
                        "genero TEXT, " +
                        "observacoes TEXT, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "excluido INTEGER DEFAULT 0, " +
                        "FOREIGN KEY(usuario_id) REFERENCES usuarios(id))"); 

            // Educando depende de Endereço
            stmt.execute("CREATE TABLE IF NOT EXISTS educandos (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "endereco_id TEXT NOT NULL, " +
                        "nome TEXT NOT NULL, " +
                        "cpf TEXT UNIQUE NOT NULL, " +
                        "data_nascimento TEXT NOT NULL, " +
                        "genero TEXT, " +
                        "grau_ensino TEXT, " +
                        "cid TEXT, " +
                        "nis TEXT, " +
                        "escola TEXT, " +
                        "observacoes TEXT, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "excluido INTEGER DEFAULT 0, " +
                        "FOREIGN KEY(endereco_id) REFERENCES enderecos(id))");

            // Responsável depende de Educando
            stmt.execute("CREATE TABLE IF NOT EXISTS responsaveis (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "educando_id TEXT NOT NULL, " +
                        "nome TEXT NOT NULL, " +
                        "cpf TEXT UNIQUE NOT NULL, " +
                        "parentesco TEXT NOT NULL, " +
                        "telefone TEXT NOT NULL, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "excluido INTEGER DEFAULT 0, " +
                        "FOREIGN KEY(educando_id) REFERENCES educandos(id))");

            // --- 3. ESTRUTURA ESCOLAR ---
            // Turma depende de um Professor
            stmt.execute("CREATE TABLE IF NOT EXISTS turmas (" +
                        "id TEXT PRIMARY KEY NOT NULL, " +
                        "professor_id TEXT NOT NULL, " +
                        "nome TEXT NOT NULL, " +
                        "turno TEXT NOT NULL, " +
                        "grau_ensino TEXT NOT NULL, " +
                        "faixa_etaria TEXT, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "excluido INTEGER DEFAULT 0, " +
                        "FOREIGN KEY(professor_id) REFERENCES professores(id))");

            // Relacionamento N:N entre Turma e Educando
            stmt.execute("CREATE TABLE IF NOT EXISTS turma_educando (" +
                        "turma_id TEXT NOT NULL, " +
                        "educando_id TEXT NOT NULL, " +
                        "excluido INTEGER DEFAULT 0, " +
                        "sincronizado INTEGER DEFAULT 0, " +
                        "PRIMARY KEY(turma_id, educando_id), " +
                        "FOREIGN KEY(turma_id) REFERENCES turmas(id), " +
                        "FOREIGN KEY(educando_id) REFERENCES educandos(id))");

            // --- 4. DOCUMENTOS (TABELAS SEPARADAS) ---
            // Cada documento aponta para um Aluno (dono) e um Professor (autor)
            // Anamnese
             stmt.execute("CREATE TABLE IF NOT EXISTS anamneses (" +
                            "id TEXT PRIMARY KEY NOT NULL, " +
                            "educando_id TEXT NOT NULL, " +
                            "professor_id TEXT NOT NULL, " +
                            "data_criacao TEXT NOT NULL, " +
                            "tem_convulsao INTEGER NOT NULL, " +
                            "tem_convenio_medico INTEGER NOT NULL, " +
                            "nome_convenio TEXT NOT NULL, " +
                            "vacinas_em_dia INTEGER NOT NULL, " +
                            "teve_doenca_contagiosa INTEGER NOT NULL, " +
                            "quais_doencas TEXT NOT NULL, " +
                            "usa_medicacao INTEGER NOT NULL, " +
                            "quais_medicacoes TEXT NOT NULL, " +
                            "usou_servico_saude_educacao INTEGER NOT NULL, " +
                            "quais_servicos TEXT NOT NULL, " +
                            "inicio_escolarizacao TEXT NOT NULL, " +
                            "apresenta_dificuldades INTEGER NOT NULL, " +
                            "quais_dificuldades TEXT NOT NULL, " +
                            "recebe_apoio_pedagogico_casa INTEGER NOT NULL, " +
                            "apoio_quem TEXT NOT NULL, " +
                            "duracao_da_gestacao TEXT NOT NULL, " +
                            "fez_prenatal INTEGER NOT NULL, " +
                            "houve_prematuridade INTEGER NOT NULL, " +
                            "causa_prematuridade TEXT NOT NULL, " +
                            "cidade_nascimento TEXT NOT NULL, " +
                            "maternidade TEXT NOT NULL, " +
                            "tipo_parto TEXT NOT NULL, " +
                            "chorou_ao_nascer INTEGER NOT NULL, " +
                            "ficou_roxo INTEGER NOT NULL, " +
                            "usou_incubadora INTEGER NOT NULL, " +
                            "foi_amamentado INTEGER NOT NULL, " +
                            "sustentou_a_cabeca INTEGER NOT NULL, " +
                            "quantos_meses_sustentou_cabeca TEXT NOT NULL, " +
                            "engatinhou INTEGER NOT NULL, " +
                            "quantos_meses_engatinhou TEXT NOT NULL, " +
                            "sentou INTEGER NOT NULL, " +
                            "quantos_meses_sentou TEXT NOT NULL, " +
                            "andou INTEGER NOT NULL, " +
                            "quantos_meses_andou TEXT NOT NULL, " +
                            "precisou_de_terapia INTEGER NOT NULL, " +
                            "qual_motivo_terapia TEXT NOT NULL, " +
                            "falou INTEGER NOT NULL, " +
                            "quantos_meses_falou TEXT NOT NULL, " +
                            "quantos_meses_balbuciou TEXT NOT NULL, " +
                            "quando_primeiras_palavras TEXT NOT NULL, " +
                            "quando_primeiras_frases TEXT NOT NULL, " +
                            "fala_natural_inibido TEXT NOT NULL, " +
                            "possui_disturbio INTEGER NOT NULL, " +
                            "qual_disturbio TEXT NOT NULL, " +
                            "observacoes_adicionais TEXT NOT NULL, " +
                            "dorme_sozinho INTEGER NOT NULL, " +
                            "tem_seu_quarto INTEGER NOT NULL, " +
                            "sono_calmo_agitado TEXT NOT NULL, " +
                            "respeita_regras INTEGER NOT NULL, " +
                            "e_desmotivado INTEGER NOT NULL, " +
                            "e_agressivo INTEGER NOT NULL, " +
                            "apresenta_inquietacao INTEGER NOT NULL, " +
                            "sincronizado INTEGER DEFAULT 0, " +
                            "excluido INTEGER DEFAULT 0, " +
                            "FOREIGN KEY(educando_id) REFERENCES educandos(id), " +
                            "FOREIGN KEY(professor_id) REFERENCES professores(id))");
            // PDI
            stmt.execute("CREATE TABLE IF NOT EXISTS pdis (" +
                            "id TEXT PRIMARY KEY NOT NULL, " +
                            "educando_id TEXT NOT NULL, " +
                            "professor_id TEXT NOT NULL, " +
                            "data_criacao TEXT NOT NULL, " +
                            "periodo_aee TEXT NOT NULL, " +
                            "horario_atendimento TEXT NOT NULL, " +
                            "frequencia_atendimento TEXT NOT NULL, " +
                            "dias_atendimento TEXT NOT NULL, " +
                            "composicao_grupo TEXT NOT NULL, " +
                            "objetivos TEXT NOT NULL, " +
                            "potencialidades TEXT NOT NULL, " +
                            "necessidades_especiais TEXT NOT NULL, " +
                            "habilidades TEXT NOT NULL, " +
                            "atividades TEXT NOT NULL, " +
                            "recursos_materiais TEXT NOT NULL, " +
                            "recursos_necessitam_adaptacao TEXT NOT NULL, " +
                            "recursos_necessitam_produzir TEXT NOT NULL, " +
                            "parcerias_necessarias TEXT NOT NULL, " +
                            "sincronizado INTEGER DEFAULT 0, " +
                            "excluido INTEGER DEFAULT 0, " +
                            "FOREIGN KEY(educando_id) REFERENCES educandos(id), " +
                            "FOREIGN KEY(professor_id) REFERENCES professores(id))");
            // PAEE
            stmt.execute("CREATE TABLE IF NOT EXISTS paees (" +
                            "id TEXT PRIMARY KEY NOT NULL, " +
                            "educando_id TEXT NOT NULL, " +
                            "professor_id TEXT NOT NULL, " +
                            "data_criacao TEXT NOT NULL, " +
                            "resumo TEXT NOT NULL, " +
                            "dificuldades_motoras INTEGER NOT NULL, " +
                            "dificuldades_cognitivas INTEGER NOT NULL, " +
                            "dificuldades_sensoriais INTEGER NOT NULL, " +
                            "dificuldades_comunicacao INTEGER NOT NULL, " +
                            "dificuldades_familiares INTEGER NOT NULL, " +
                            "dificuldades_afetivas INTEGER NOT NULL, " +
                            "dificuldades_raciocinio INTEGER NOT NULL, " +
                            "dificuldades_avas INTEGER NOT NULL, " +
                            "dif_des_motor TEXT NOT NULL, " +
                            "intervencoes_motor TEXT NOT NULL, " +
                            "dif_comunicacao TEXT NOT NULL, " +
                            "intervencoes_comunicacao TEXT NOT NULL, " +
                            "dif_raciocinio TEXT NOT NULL, " +
                            "intervencoes_raciocinio TEXT NOT NULL, " +
                            "dif_atencao TEXT NOT NULL, " +
                            "intervencoes_atencao TEXT NOT NULL, " +
                            "dif_memoria TEXT NOT NULL, " +
                            "intervencoes_memoria TEXT NOT NULL, " +
                            "dif_percepcao TEXT NOT NULL, " +
                            "intervencoes_percepcao TEXT NOT NULL, " +
                            "dif_sociabilidade TEXT NOT NULL, " +
                            "intervencoes_sociabilidade TEXT NOT NULL, " +
                            "objetivo_plano TEXT NOT NULL, " +
                            "aee INTEGER NOT NULL, " +
                            "psicologo INTEGER NOT NULL, " +
                            "fisioterapeuta INTEGER NOT NULL, " +
                            "psicopedagogo INTEGER NOT NULL, " +
                            "terapeuta_ocupacional INTEGER NOT NULL, " +
                            "educacao_fisica INTEGER NOT NULL, " +
                            "estimulacao_precoce INTEGER NOT NULL, " +
                            "sincronizado INTEGER DEFAULT 0, " +
                            "excluido INTEGER DEFAULT 0, " +
                            "FOREIGN KEY(educando_id) REFERENCES educandos(id)," +
                            "FOREIGN KEY(professor_id) REFERENCES professores(id))");
            // Diagnóstico Inicial
            stmt.execute("CREATE TABLE IF NOT EXISTS dis (" +
                            "id TEXT PRIMARY KEY NOT NULL, " +
                            "educando_id TEXT NOT NULL, " +
                            "professor_id TEXT NOT NULL, " +
                            "data_criacao TEXT NOT NULL, " +
                            // Area da Comunicação
                            "fala_nome INTEGER NOT NULL, " +
                            "fala_nascimento INTEGER NOT NULL, " +
                            "le_palavras INTEGER NOT NULL, " +
                            "fala_telefone INTEGER NOT NULL, " +
                            "emite_respostas INTEGER NOT NULL, " +
                            "transmite_recados INTEGER NOT NULL, " +
                            "fala_endereco INTEGER NOT NULL, " +
                            "fala_nome_pais INTEGER NOT NULL, " +
                            "compreende_ordens INTEGER NOT NULL, " +
                            "expoe_ideias INTEGER NOT NULL, " +
                            "reconta_historia INTEGER NOT NULL, " +
                            "usa_sistema_ca INTEGER NOT NULL, " +
                            "relata_fatos INTEGER NOT NULL, " +
                            "pronuncia_letras INTEGER NOT NULL, " +
                            "verbaliza_musicas INTEGER NOT NULL, " +
                            "interpreta_historias INTEGER NOT NULL, " +
                            "formula_perguntas INTEGER NOT NULL, " +
                            "utiliza_gestos INTEGER NOT NULL, " +
                            // Areas Afetivas/Sociais
                            "demonstra_cooperacao INTEGER NOT NULL, " +
                            "timido INTEGER NOT NULL, " +
                            "birra INTEGER NOT NULL, " +
                            "pede_ajuda INTEGER NOT NULL, " +
                            "ri INTEGER NOT NULL, " +
                            "compartilha INTEGER NOT NULL, " +
                            "demonstra_amor INTEGER NOT NULL, " +
                            "chora INTEGER NOT NULL, " +
                            "interage INTEGER NOT NULL, " +
                            // Areas Sensoriais
                            "detalhes_gravura INTEGER NOT NULL, " +
                            "reconhece_vozes INTEGER NOT NULL, " +
                            "reconhece_cancoes INTEGER NOT NULL, " +
                            "percebe_texturas INTEGER NOT NULL, " +
                            "percebe_cores INTEGER NOT NULL, " +
                            "discrimina_sons INTEGER NOT NULL, " +
                            "discrimina_odores INTEGER NOT NULL, " +
                            "aceita_texturas INTEGER NOT NULL, " +
                            "percepcao_formas INTEGER NOT NULL, " +
                            "identifica_direcao_sons INTEGER NOT NULL, " +
                            "discrimina_sabores INTEGER NOT NULL, " +
                            "acompanha_luz INTEGER NOT NULL, " +
                            // Areas Motoras
                            "movimento_pinca INTEGER NOT NULL, " +
                            "amassa_papel INTEGER NOT NULL, " +
                            "cai_facilmente INTEGER NOT NULL, " +
                            "encaixa_pecas INTEGER NOT NULL, " +
                            "recorta INTEGER NOT NULL, " +
                            "une_pontos INTEGER NOT NULL, " +
                            "corre INTEGER NOT NULL, " +
                            "empilha INTEGER NOT NULL, " +
                            "agitacao_motora INTEGER NOT NULL, " +
                            "anda_reto INTEGER NOT NULL, " +
                            "sobe_escada INTEGER NOT NULL, " +
                            "arremessa_bola INTEGER NOT NULL, " +
                            // AVDs
                            "usa_sanitario INTEGER NOT NULL, " +
                            "penteia_cabelo INTEGER NOT NULL, " +
                            "veste_se INTEGER NOT NULL, " +
                            "lava_maos INTEGER NOT NULL, " +
                            "banha_se INTEGER NOT NULL, " +
                            "calca_se INTEGER NOT NULL, " +
                            "reconhece_roupas INTEGER NOT NULL, " +
                            "abre_torneira INTEGER NOT NULL, " +
                            "escova_dentes INTEGER NOT NULL, " +
                            "da_nos INTEGER NOT NULL, " +
                            "abotoa_roupas INTEGER NOT NULL, " +
                            "identifica_partes_corpo INTEGER NOT NULL, " +
                            // Niveis de aprendizagem
                            "garatujas INTEGER NOT NULL, " +
                            "silabico_alfabetico INTEGER NOT NULL, " +
                            "alfabetico INTEGER NOT NULL, " +
                            "pre_silabico INTEGER NOT NULL, " +
                            "silabico INTEGER NOT NULL, " +
                            "observacoes TEXT, " +
                            "sincronizado INTEGER DEFAULT 0, " +
                            "excluido INTEGER DEFAULT 0, " +
                            "FOREIGN KEY(educando_id) REFERENCES educandos(id), " +
                            "FOREIGN KEY(professor_id) REFERENCES professores(id))");
            // Relatório Individual
            stmt.execute("CREATE TABLE IF NOT EXISTS ris (" +
                            "id TEXT PRIMARY KEY NOT NULL, " +
                            "educando_id TEXT NOT NULL, " +
                            "professor_id TEXT NOT NULL, " +
                            "data_criacao TEXT NOT NULL, " +
                            "dados_funcionais TEXT NOT NULL, " +
                            "funcionalidade_cognitiva TEXT NOT NULL, " +
                            "alfabetizacao TEXT NOT NULL, " +
                            "adaptacoes_curriculares TEXT NOT NULL, " +
                            "participacao_atividade TEXT NOT NULL, " +
                            "autonomia INTEGER NOT NULL, " +
                            "interacao_professora INTEGER NOT NULL, " +
                            "atividades_vida_diaria TEXT NOT NULL, " +
                            "sincronizado INTEGER DEFAULT 0, " +
                            "excluido INTEGER DEFAULT 0, " +
                            "FOREIGN KEY(educando_id) REFERENCES educandos(id), " +
                            "FOREIGN KEY(professor_id) REFERENCES professores(id))");

            stmt.execute("COMMIT;");
            System.out.println("Estrutura do AmparoEdu 2.0 inicializada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco local: " + e.getMessage());
        }
    }
}