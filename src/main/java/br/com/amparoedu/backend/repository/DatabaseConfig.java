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
                            "tem_convulsao TEXT NOT NULL, " +
                            "tem_convenio_medico TEXT NOT NULL, " +
                            "nome_convenio TEXT NOT NULL, " +
                            "vacinas_em_dia TEXT NOT NULL, " +
                            "teve_doenca_contagiosa TEXT NOT NULL, " +
                            "quais_doencas TEXT NOT NULL, " +
                            "usa_medicacao TEXT NOT NULL, " +
                            "quais_medicacoes TEXT NOT NULL, " +
                            "usou_servico_saude_educacao TEXT NOT NULL, " +
                            "quais_servicos TEXT NOT NULL, " +
                            "inicio_escolarizacao TEXT NOT NULL, " +
                            "apresenta_dificuldades TEXT NOT NULL, " +
                            "quais_dificuldades TEXT NOT NULL, " +
                            "recebe_apoio_pedagogico_casa TEXT NOT NULL, " +
                            "apoio_quem TEXT NOT NULL, " +
                            "duracao_da_gestacao TEXT NOT NULL, " +
                            "fez_prenatal TEXT NOT NULL, " +
                            "houve_prematuridade TEXT NOT NULL, " +
                            "causa_prematuridade TEXT NOT NULL, " +
                            "cidade_nascimento TEXT NOT NULL, " +
                            "maternidade TEXT NOT NULL, " +
                            "tipo_parto TEXT NOT NULL, " +
                            "chorou_ao_nascer TEXT NOT NULL, " +
                            "ficou_roxo TEXT NOT NULL, " +
                            "usou_incubadora TEXT NOT NULL, " +
                            "foi_amamentado TEXT NOT NULL, " +
                            "sustentou_a_cabeca TEXT NOT NULL, " +
                            "quantos_meses_sustentou_cabeca TEXT NOT NULL, " +
                            "engatinhou TEXT NOT NULL, " +
                            "quantos_meses_engatinhou TEXT NOT NULL, " +
                            "sentou TEXT NOT NULL, " +
                            "quantos_meses_sentou TEXT NOT NULL, " +
                            "andou TEXT NOT NULL, " +
                            "quantos_meses_andou TEXT NOT NULL, " +
                            "precisou_de_terapia TEXT NOT NULL, " +
                            "qual_motivo_terapia TEXT NOT NULL, " +
                            "falou TEXT NOT NULL, " +
                            "quantos_meses_falou TEXT NOT NULL, " +
                            "quantos_meses_balbuciou TEXT NOT NULL, " +
                            "quando_primeiras_palavras TEXT NOT NULL, " +
                            "quando_primeiras_frases TEXT NOT NULL, " +
                            "fala_natural_inibido TEXT NOT NULL, " +
                            "possui_disturbio TEXT NOT NULL, " +
                            "qual_disturbio TEXT NOT NULL, " +
                            "observacoes_adicionais TEXT NOT NULL, " +
                            "dorme_sozinho TEXT NOT NULL, " +
                            "tem_seu_quarto TEXT NOT NULL, " +
                            "sono_calmo_agitado TEXT NOT NULL, " +
                            "respeita_regras TEXT NOT NULL, " +
                            "e_desmotivado TEXT NOT NULL, " +
                            "e_agressivo TEXT NOT NULL, " +
                            "apresenta_inquietacao TEXT NOT NULL, " +
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
                            "dificuldades_motoras TEXT NOT NULL, " +
                            "dificuldades_cognitivas TEXT NOT NULL, " +
                            "dificuldades_sensoriais TEXT NOT NULL, " +
                            "dificuldades_comunicacao TEXT NOT NULL, " +
                            "dificuldades_familiares TEXT NOT NULL, " +
                            "dificuldades_afetivas TEXT NOT NULL, " +
                            "dificuldades_raciocinio TEXT NOT NULL, " +
                            "dificuldades_avas TEXT NOT NULL, " +
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
                            "aee TEXT NOT NULL, " +
                            "psicologo TEXT NOT NULL, " +
                            "fisioterapeuta TEXT NOT NULL, " +
                            "psicopedagogo TEXT NOT NULL, " +
                            "terapeuta_ocupacional TEXT NOT NULL, " +
                            "educacao_fisica TEXT NOT NULL, " +
                            "estimulacao_precoce TEXT NOT NULL, " +
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
                            "fala_nome TEXT NOT NULL, " +
                            "fala_nascimento TEXT NOT NULL, " +
                            "le_palavras TEXT NOT NULL, " +
                            "fala_telefone TEXT NOT NULL, " +
                            "emite_respostas TEXT NOT NULL, " +
                            "transmite_recados TEXT NOT NULL, " +
                            "fala_endereco TEXT NOT NULL, " +
                            "fala_nome_pais TEXT NOT NULL, " +
                            "compreende_ordens TEXT NOT NULL, " +
                            "expoe_ideias TEXT NOT NULL, " +
                            "reconta_historia TEXT NOT NULL, " +
                            "usa_sistema_ca TEXT NOT NULL, " +
                            "relata_fatos TEXT NOT NULL, " +
                            "pronuncia_letras TEXT NOT NULL, " +
                            "verbaliza_musicas TEXT NOT NULL, " +
                            "interpreta_historias TEXT NOT NULL, " +
                            "formula_perguntas TEXT NOT NULL, " +
                            "utiliza_gestos TEXT NOT NULL, " +
                            // Areas Afetivas/Sociais
                            "demonstra_cooperacao TEXT NOT NULL, " +
                            "timido TEXT NOT NULL, " +
                            "birra TEXT NOT NULL, " +
                            "pede_ajuda TEXT NOT NULL, " +
                            "ri TEXT NOT NULL, " +
                            "compartilha TEXT NOT NULL, " +
                            "demonstra_amor TEXT NOT NULL, " +
                            "chora TEXT NOT NULL, " +
                            "interage TEXT NOT NULL, " +
                            // Areas Sensoriais
                            "detalhes_gravura TEXT NOT NULL, " +
                            "reconhece_vozes TEXT NOT NULL, " +
                            "reconhece_cancoes TEXT NOT NULL, " +
                            "percebe_texturas TEXT NOT NULL, " +
                            "percebe_cores TEXT NOT NULL, " +
                            "discrimina_sons TEXT NOT NULL, " +
                            "discrimina_odores TEXT NOT NULL, " +
                            "aceita_texturas TEXT NOT NULL, " +
                            "percepcao_formas TEXT NOT NULL, " +
                            "identifica_direcao_sons TEXT NOT NULL, " +
                            "discrimina_sabores TEXT NOT NULL, " +
                            "acompanha_luz TEXT NOT NULL, " +
                            // Areas Motoras
                            "movimento_pinca TEXT NOT NULL, " +
                            "amassa_papel TEXT NOT NULL, " +
                            "cai_facilmente TEXT NOT NULL, " +
                            "encaixa_pecas TEXT NOT NULL, " +
                            "recorta TEXT NOT NULL, " +
                            "une_pontos TEXT NOT NULL, " +
                            "corre TEXT NOT NULL, " +
                            "empilha TEXT NOT NULL, " +
                            "agitacao_motora TEXT NOT NULL, " +
                            "anda_reto TEXT NOT NULL, " +
                            "sobe_escada TEXT NOT NULL, " +
                            "arremessa_bola TEXT NOT NULL, " +
                            // AVDs
                            "usa_sanitario TEXT NOT NULL, " +
                            "penteia_cabelo TEXT NOT NULL, " +
                            "veste_se TEXT NOT NULL, " +
                            "lava_maos TEXT NOT NULL, " +
                            "banha_se TEXT NOT NULL, " +
                            "calca_se TEXT NOT NULL, " +
                            "reconhece_roupas TEXT NOT NULL, " +
                            "abre_torneira TEXT NOT NULL, " +
                            "escova_dentes TEXT NOT NULL, " +
                            "da_nos TEXT NOT NULL, " +
                            "abotoa_roupas TEXT NOT NULL, " +
                            "identifica_partes_corpo TEXT NOT NULL, " +
                            // Niveis de aprendizagem
                            "garatujas TEXT NOT NULL, " +
                            "silabico_alfabetico TEXT NOT NULL, " +
                            "alfabetico TEXT NOT NULL, " +
                            "pre_silabico TEXT NOT NULL, " +
                            "silabico TEXT NOT NULL, " +
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
                            "autonomia TEXT NOT NULL, " +
                            "interacao_professora TEXT NOT NULL, " +
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