package br.com.amparoedu.util;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.RI;

/**
 * Gerador de PDF manual para Relatório Individual (RI).
 * Gera PDF sem usar frameworks externos, apenas escrevendo a sintaxe PDF diretamente.
 */
public class RIPDFGenerator {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Gera um PDF do Relatório Individual.
     * 
     * @param educando O educando relacionado ao RI
     * @param ri O relatório individual
     * @param caminhoArquivo O caminho completo onde o PDF será salvo
     * @return true se o PDF foi gerado com sucesso, false caso contrário
     */
    public boolean gerarRelatorioIndividual(Educando educando, RI ri, String caminhoArquivo) {
        try {
            // Gera o conteúdo textual do relatório
            String textoRelatorio = gerarTextoRelatorio(educando, ri);
            
            // Gera o stream de conteúdo PDF
            String streamContent = gerarStreamConteudo(textoRelatorio);
            // Calcula o tamanho do stream em bytes (usando ISO_8859_1 para compatibilidade PDF)
            byte[] streamBytes = streamContent.getBytes(StandardCharsets.ISO_8859_1);
            int tamanhoStream = streamBytes.length;

            // Constrói o PDF
            List<Long> offsets = new ArrayList<>();
            StringBuilder pdf = new StringBuilder();
            
            // Cabeçalho PDF
            pdf.append("%PDF-1.4\n");
            
            // Objeto 1: Catalog
            offsets.add((long) pdf.length());
            pdf.append("1 0 obj\n");
            pdf.append("<<\n");
            pdf.append("/Type /Catalog\n");
            pdf.append("/Pages 2 0 R\n");
            pdf.append(">>\n");
            pdf.append("endobj\n");
            
            // Objeto 2: Pages
            offsets.add((long) pdf.length());
            pdf.append("2 0 obj\n");
            pdf.append("<<\n");
            pdf.append("/Type /Pages\n");
            pdf.append("/Kids [3 0 R]\n");
            pdf.append("/Count 1\n");
            pdf.append(">>\n");
            pdf.append("endobj\n");
            
            // Objeto 3: Page
            offsets.add((long) pdf.length());
            pdf.append("3 0 obj\n");
            pdf.append("<<\n");
            pdf.append("/Type /Page\n");
            pdf.append("/Parent 2 0 R\n");
            pdf.append("/MediaBox [0 0 612 792]\n");
            pdf.append("/Contents 4 0 R\n");
            pdf.append("/Resources <<\n");
            pdf.append("/Font <<\n");
            pdf.append("/F1 <<\n");
            pdf.append("/Type /Font\n");
            pdf.append("/Subtype /Type1\n");
            pdf.append("/BaseFont /Helvetica\n");
            pdf.append(">>\n");
            pdf.append("/F2 <<\n");
            pdf.append("/Type /Font\n");
            pdf.append("/Subtype /Type1\n");
            pdf.append("/BaseFont /Helvetica-Bold\n");
            pdf.append(">>\n");
            pdf.append(">>\n");
            pdf.append(">>\n");
            pdf.append(">>\n");
            pdf.append("endobj\n");
            
            // Objeto 4: Content Stream
            offsets.add((long) pdf.length());
            pdf.append("4 0 obj\n");
            pdf.append("<<\n");
            pdf.append("/Length ").append(tamanhoStream).append("\n");
            pdf.append(">>\n");
            pdf.append("stream\n");
            pdf.append(streamContent);
            pdf.append("endstream\n");
            pdf.append("endobj\n");
            
            // XRef table
            long xrefOffset = pdf.length();
            pdf.append("xref\n");
            pdf.append("0 ").append(offsets.size() + 1).append("\n");
            pdf.append("0000000000 65535 f \n");
            for (Long offset : offsets) {
                String offsetStr = String.format("%010d", offset);
                pdf.append(offsetStr).append(" 00000 n \n");
            }
            
            // Trailer
            pdf.append("trailer\n");
            pdf.append("<<\n");
            pdf.append("/Size ").append(offsets.size() + 1).append("\n");
            pdf.append("/Root 1 0 R\n");
            pdf.append(">>\n");
            pdf.append("startxref\n");
            pdf.append(xrefOffset).append("\n");
            pdf.append("%%EOF\n");

            // Escreve o arquivo
            try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
                fos.write(pdf.toString().getBytes(StandardCharsets.ISO_8859_1));
            }

            return true;
        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gera o conteúdo textual do relatório individual.
     */
    private String gerarTextoRelatorio(Educando educando, RI ri) {
        StringBuilder texto = new StringBuilder();
        
        // Dados do Educando
        texto.append("DADOS DO EDUCANDO\n");
        texto.append("-----------------\n");
        texto.append("Nome: ").append(safe(educando.getNome())).append("\n");
        texto.append("CPF: ").append(safe(educando.getCpf())).append("\n");
        texto.append("Data de Nascimento: ").append(formatarData(educando.getData_nascimento())).append("\n");
        texto.append("Genero: ").append(safe(educando.getGenero())).append("\n");
        texto.append("Grau de Ensino: ").append(safe(educando.getGrau_ensino())).append("\n");
        texto.append("CID: ").append(safe(educando.getCid())).append("\n");
        texto.append("NIS: ").append(safe(educando.getNis())).append("\n");
        texto.append("Escola: ").append(safe(educando.getEscola())).append("\n");
        if (educando.getObservacoes() != null && !educando.getObservacoes().isBlank()) {
            texto.append("Observacoes: ").append(educando.getObservacoes()).append("\n");
        }
        texto.append("\n");

        // Relatório Individual
        texto.append("RELATORIO INDIVIDUAL\n");
        texto.append("--------------------\n");
        texto.append("Data de Criacao: ").append(formatarData(ri.getData_criacao())).append("\n");
        texto.append("\n");

        // Dados Funcionais
        if (ri.getDados_funcionais() != null && !ri.getDados_funcionais().isBlank()) {
            texto.append("DADOS FUNCIONAIS\n");
            texto.append("----------------\n");
            texto.append(ri.getDados_funcionais()).append("\n");
            texto.append("\n");
        }

        // Funcionalidade Cognitiva
        if (ri.getFuncionalidade_cognitiva() != null && !ri.getFuncionalidade_cognitiva().isBlank()) {
            texto.append("FUNCIONALIDADE COGNITIVA\n");
            texto.append("-----------------------\n");
            texto.append(ri.getFuncionalidade_cognitiva()).append("\n");
            texto.append("\n");
        }

        // Alfabetização
        if (ri.getAlfabetizacao() != null && !ri.getAlfabetizacao().isBlank()) {
            texto.append("ALFABETIZACAO\n");
            texto.append("-------------\n");
            texto.append(ri.getAlfabetizacao()).append("\n");
            texto.append("\n");
        }

        // Adaptações Curriculares
        if (ri.getAdaptacoes_curriculares() != null && !ri.getAdaptacoes_curriculares().isBlank()) {
            texto.append("ADAPTACOES CURRICULARES\n");
            texto.append("----------------------\n");
            texto.append(ri.getAdaptacoes_curriculares()).append("\n");
            texto.append("\n");
        }

        // Participação em Atividades
        if (ri.getParticipacao_atividade() != null && !ri.getParticipacao_atividade().isBlank()) {
            texto.append("PARTICIPACAO EM ATIVIDADES\n");
            texto.append("-------------------------\n");
            texto.append(ri.getParticipacao_atividade()).append("\n");
            texto.append("\n");
        }

        // Autonomia
        texto.append("AUTONOMIA\n");
        texto.append("---------\n");
        texto.append(simNao(ri.getAutonomia())).append("\n");
        texto.append("\n");

        // Interação com Professora
        texto.append("INTERACAO COM PROFESSORA\n");
        texto.append("-----------------------\n");
        texto.append(simNao(ri.getInteracao_professora())).append("\n");
        texto.append("\n");

        // Atividades de Vida Diária
        if (ri.getAtividades_vida_diaria() != null && !ri.getAtividades_vida_diaria().isBlank()) {
            texto.append("ATIVIDADES DE VIDA DIARIA\n");
            texto.append("------------------------\n");
            texto.append(ri.getAtividades_vida_diaria()).append("\n");
            texto.append("\n");
        }

        // Rodapé
        texto.append("\n\n");
        texto.append("Relatorio gerado em: ").append(LocalDate.now().format(DATE_FORMATTER)).append("\n");
        texto.append("Sistema AmparoEdu 2.0\n");

        return texto.toString();
    }

    /**
     * Gera o stream de conteúdo PDF formatado.
     */
    private String gerarStreamConteudo(String texto) {
        StringBuilder stream = new StringBuilder();
        String[] linhas = texto.split("\n");
        int y = 750; // Posição Y inicial
        
        // Inicia o texto
        stream.append("BT\n");
        
        // Título principal
        stream.append("/F2 18 Tf\n");
        stream.append("50 ").append(y).append(" Td\n");
        String titulo = removerAcentos("RELATORIO INDIVIDUAL");
        stream.append("(").append(escaparPDF(titulo)).append(") Tj\n");
        stream.append("0 -30 Td\n");
        stream.append("/F1 11 Tf\n");
        y -= 35;
        
        // Processa todas as linhas do texto
        for (String linha : linhas) {
            // Pula linha vazia
            if (linha.trim().isEmpty()) {
                stream.append("0 -10 Td\n");
                y -= 10;
                continue;
            }
            
            // Se chegou no final da página, reinicia (simplificado - continua na mesma página)
            if (y < 50) {
                y = 750;
                stream.append("ET\n");
                stream.append("BT\n");
                stream.append("/F1 11 Tf\n");
                stream.append("50 ").append(y).append(" Td\n");
            }
            
            // Remove acentos e escapa caracteres especiais
            String linhaSemAcentos = removerAcentos(linha);
            String linhaEscapada = escaparPDF(linhaSemAcentos);
            
            // Quebra linha se muito longa (mantém mais caracteres)
            if (linhaEscapada.length() > 100) {
                // Quebra em múltiplas linhas
                int pos = 0;
                while (pos < linhaEscapada.length()) {
                    int fim = Math.min(pos + 100, linhaEscapada.length());
                    String parte = linhaEscapada.substring(pos, fim);
                    stream.append("(").append(parte).append(") Tj\n");
                    stream.append("0 -13 Td\n");
                    y -= 13;
                    pos = fim;
                }
            } else {
                stream.append("(").append(linhaEscapada).append(") Tj\n");
                stream.append("0 -13 Td\n");
                y -= 13;
            }
        }
        
        stream.append("ET\n");
        return stream.toString();
    }
    
    /**
     * Remove acentos e caracteres especiais para compatibilidade com PDF.
     */
    private String removerAcentos(String texto) {
        if (texto == null) return "";
        
        return texto
            .replace("á", "a").replace("à", "a").replace("ã", "a").replace("â", "a").replace("ä", "a")
            .replace("é", "e").replace("è", "e").replace("ê", "e").replace("ë", "e")
            .replace("í", "i").replace("ì", "i").replace("î", "i").replace("ï", "i")
            .replace("ó", "o").replace("ò", "o").replace("õ", "o").replace("ô", "o").replace("ö", "o")
            .replace("ú", "u").replace("ù", "u").replace("û", "u").replace("ü", "u")
            .replace("ç", "c")
            .replace("Á", "A").replace("À", "A").replace("Ã", "A").replace("Â", "A").replace("Ä", "A")
            .replace("É", "E").replace("È", "E").replace("Ê", "E").replace("Ë", "E")
            .replace("Í", "I").replace("Ì", "I").replace("Î", "I").replace("Ï", "I")
            .replace("Ó", "O").replace("Ò", "O").replace("Õ", "O").replace("Ô", "O").replace("Ö", "O")
            .replace("Ú", "U").replace("Ù", "U").replace("Û", "U").replace("Ü", "U")
            .replace("Ç", "C")
            .replace("º", "o").replace("ª", "a")
            .replace("°", "o");
    }
    
    /**
     * Escapa caracteres especiais para PDF.
     */
    private String escaparPDF(String texto) {
        if (texto == null) return "";
        
        return texto.replace("\\", "\\\\")
                   .replace("(", "\\(")
                   .replace(")", "\\)")
                   .replace("\r", "")
                   .replace("\n", " ");
    }

    private String safe(String valor) {
        return (valor == null || valor.isBlank()) ? "-" : valor;
    }

    private String simNao(int valor) {
        return valor == 1 ? "Sim" : "Nao";
    }

    private String formatarData(String data) {
        if (data == null || data.isBlank()) {
            return "-";
        }
        try {
            // Tenta formatar data ISO (yyyy-MM-dd)
            if (data.contains("-")) {
                LocalDate localDate = LocalDate.parse(data);
                return localDate.format(DATE_FORMATTER);
            }
            // Se já estiver formatada, retorna como está
            return data;
        } catch (Exception e) {
            return data;
        }
    }
}
