package br.com.amparoedu.util;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
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
    private static final Charset CP1252 = Charset.forName("Windows-1252");
    private static final int PAGE_WIDTH = 612;
    private static final int PAGE_HEIGHT = 792;
    private static final int MARGIN_LEFT = 60;
    
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
            // Gera o stream de conteúdo PDF
            StringBuilder streamBuilder = new StringBuilder();
            gerarConteudoPDF(streamBuilder, educando, ri);
            String streamContent = streamBuilder.toString();
            
            // Calcula o tamanho do stream em bytes (usando CP1252)
            byte[] streamBytes = streamContent.getBytes(CP1252);
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
            pdf.append("/MediaBox [0 0 ").append(PAGE_WIDTH).append(" ").append(PAGE_HEIGHT).append("]\n");
            pdf.append("/Contents 4 0 R\n");
            pdf.append("/Resources <<\n");
            pdf.append("/Font <<\n");
            pdf.append("/F1 <<\n");
            pdf.append("/Type /Font\n");
            pdf.append("/Subtype /Type1\n");
            pdf.append("/BaseFont /Helvetica\n");
            pdf.append("/Encoding /WinAnsiEncoding\n");
            pdf.append(">>\n");
            pdf.append("/F2 <<\n");
            pdf.append("/Type /Font\n");
            pdf.append("/Subtype /Type1\n");
            pdf.append("/BaseFont /Helvetica-Bold\n");
            pdf.append("/Encoding /WinAnsiEncoding\n");
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
                fos.write(pdf.toString().getBytes(CP1252));
            }

            return true;
        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gera o conteúdo do PDF usando helpers.
     */
    private void gerarConteudoPDF(StringBuilder stream, Educando educando, RI ri) {
        int anoAtual = LocalDate.now().getYear();
        
        // Cabeçalho centralizado
        centerX(stream, "APAPEQ", 18, PAGE_WIDTH, 740, "/F2");
        centerX(stream, "CENTRO DE ATENDIMENTO EDUCACIONAL ESPECIALIZADO", 9, PAGE_WIDTH, 722, "/F2");
        centerX(stream, "DR. MARCELLO CANDIA", 9, PAGE_WIDTH, 710, "/F2");
        centerX(stream, "AVALIACAO DESCRITIVA", 13, PAGE_WIDTH, 682, "/F2");
        centerX(stream, "ATENDIMENTO EDUCACIONAL ESPECIALIZADO - " + anoAtual, 11, PAGE_WIDTH, 664, "/F2");
        
        // Seção 1: IDENTIFICAÇÃO DO(A) ALUNO(A)
        textAt(stream, "1. IDENTIFICACAO DO(A) ALUNO(A):", MARGIN_LEFT, 636, 11, "/F2");
        
        // Linha Nome
        textAt(stream, "Nome:", MARGIN_LEFT, 616, 10, "/F2");
        String nome = safe(educando.getNome());
        textAt(stream, nome, 115, 616, 10, "/F1");
        drawLine(stream, 115, 614, 552, 0.8f);
        
        // Linha Data + Período
        textAt(stream, "Data de Nascimento:", MARGIN_LEFT, 598, 10, "/F2");
        String dataNasc = formatarData(educando.getData_nascimento());
        textAt(stream, dataNasc, 165, 598, 10, "/F1");
        drawLine(stream, 165, 596, 360, 0.8f);
        
        textAt(stream, "Periodo:", 390, 598, 10, "/F2");
        String periodo = String.valueOf(anoAtual);
        textAt(stream, periodo, 445, 598, 10, "/F1");
        drawLine(stream, 445, 596, 552, 0.8f);
        
        // Linha Diagnóstico
        textAt(stream, "Diagnostico:", MARGIN_LEFT, 580, 10, "/F2");
        String cid = safe(educando.getCid());
        textAt(stream, cid, 135, 580, 10, "/F1");
        drawLine(stream, 135, 578, 552, 0.8f);
        
        // Seção 2: DADOS FUNCIONAIS
        textAt(stream, "2. DADOS FUNCIONAIS:", MARGIN_LEFT, 548, 11, "/F2");
        
        // Texto livre de dados funcionais
        String dadosFuncionais = ri.getDados_funcionais() != null && !ri.getDados_funcionais().isBlank() 
            ? ri.getDados_funcionais() 
            : "-";
        textAt(stream, dadosFuncionais, MARGIN_LEFT, 530, 10, "/F1");
        
        // Lista de tópicos com bullet
        int yAtual = 490;
        int espacamento = 22;
        
        // FUNCIONALIDADE COGNITIVA
        bulletItem(stream, "FUNCIONALIDADE COGNITIVA:", safe(ri.getFuncionalidade_cognitiva()), yAtual);
        yAtual -= espacamento;
        
        // ALFABETIZAÇÃO E LETRAMENTO
        bulletItem(stream, "ALFABETIZACAO E LETRAMENTO:", safe(ri.getAlfabetizacao()), yAtual);
        yAtual -= espacamento;
        
        // ADAPTAÇÕES CURRICULARES
        bulletItem(stream, "ADAPTACOES CURRICULARES:", safe(ri.getAdaptacoes_curriculares()), yAtual);
        yAtual -= espacamento;
        
        // PARTICIPAÇÃO NAS ATIVIDADES PROPOSTAS
        bulletItem(stream, "PARTICIPACAO NAS ATIVIDADES PROPOSTAS:", safe(ri.getParticipacao_atividade()), yAtual);
        yAtual -= espacamento;
        
        // AUTONOMIA
        String autonomia = ri.getAutonomia() == 1 ? "Sim" : "Nao";
        bulletItem(stream, "AUTONOMIA:", autonomia, yAtual);
        yAtual -= espacamento;
        
        // INTERAÇÃO COM A PROFESSORA
        String interacao = ri.getInteracao_professora() == 1 ? "Sim" : "Nao";
        bulletItem(stream, "INTERACAO COM A PROFESSORA:", interacao, yAtual);
        yAtual -= espacamento;
        
        // ATIVIDADES DE VIDA DIÁRIA (AVDs)
        bulletItem(stream, "ATIVIDADES DE VIDA DIARIA (AVDs):", safe(ri.getAtividades_vida_diaria()), yAtual);
        yAtual -= espacamento;
        
        // Seção de Assinaturas
        // Espaço antes das assinaturas
        yAtual -= 20;
        
        // Linha de assinatura do Professor
        int linhaComprimento = 400;
        int xLinha = (PAGE_WIDTH - linhaComprimento) / 2;
        drawLine(stream, xLinha, yAtual, xLinha + linhaComprimento, 0.8f);
        yAtual -= 15;
        centerX(stream, "PROFESSOR(A) DE ATENDIMENTO EDUCACIONAL ESPECIALIZADO", 10, PAGE_WIDTH, yAtual, "/F2");
        yAtual -= 30;
        
        // Linha de assinatura do Responsável
        drawLine(stream, xLinha, yAtual, xLinha + linhaComprimento, 0.8f);
        yAtual -= 15;
        centerX(stream, "ASSINATURA DO(A) RESPONSAVEL", 10, PAGE_WIDTH, yAtual, "/F2");
        yAtual -= 30;
        
        // Campo de Data
        int linhaDataComprimento = 150;
        int xDataLinha = (PAGE_WIDTH - linhaDataComprimento) / 2;
        drawLine(stream, xDataLinha, yAtual, xDataLinha + linhaDataComprimento, 0.8f);
        yAtual -= 15;
        centerX(stream, "DATA", 10, PAGE_WIDTH, yAtual, "/F2");
        yAtual -= 40;
        
        // Rodapé - Informações da instituição
        centerX(stream, "Associacao de Pais e Amigos de Pessoas Especiais de Quixada - APAPEQ", 9, PAGE_WIDTH, yAtual, "/F1");
        yAtual -= 15;
        centerX(stream, "Rua Basilio Pinto, 2651, Combate. 63902-100/Quixada-CE", 9, PAGE_WIDTH, yAtual, "/F1");
        yAtual -= 15;
        centerX(stream, "CNAS: 8742-07/12/2003   CNPJ: 02.328.891/0001-35", 9, PAGE_WIDTH, yAtual, "/F1");
        yAtual -= 15;
        centerX(stream, "Email: apapeqqxd@gmail.com", 9, PAGE_WIDTH, yAtual, "/F1");
    }
    
    /**
     * Helper: Define fonte e tamanho.
     */
    private void setFont(StringBuilder stream, String font, int size) {
        stream.append(font).append(" ").append(size).append(" Tf\n");
    }
    
    /**
     * Helper: Posiciona texto em coordenadas absolutas.
     */
    private void textAt(StringBuilder stream, String text, int x, int y, int fontSize, String font) {
        if (text == null || text.isEmpty()) {
            text = "-";
        }
        setFont(stream, font, fontSize);
        stream.append("1 0 0 1 ").append(x).append(" ").append(y).append(" Tm\n");
        String textoEscapado = escaparPDF(text);
        stream.append("(").append(textoEscapado).append(") Tj\n");
    }
    
    /**
     * Helper: Desenha uma linha horizontal.
     */
    private void drawLine(StringBuilder stream, int x1, int y, int x2, float width) {
        stream.append("q\n"); // Save graphics state
        stream.append(width).append(" w\n"); // Largura da linha
        stream.append(x1).append(" ").append(y).append(" m\n"); // Move to
        stream.append(x2).append(" ").append(y).append(" l\n"); // Line to
        stream.append("S\n"); // Stroke
        stream.append("Q\n"); // Restore graphics state
    }
    
    /**
     * Helper: Centraliza texto horizontalmente.
     */
    private void centerX(StringBuilder stream, String text, int fontSize, int pageWidth, int y, String font) {
        if (text == null || text.isEmpty()) {
            text = "-";
        }
        // Heurística: fontSize * 0.5 por caractere
        double textWidth = text.length() * fontSize * 0.5;
        int x = (int)((pageWidth - textWidth) / 2);
        textAt(stream, text, x, y, fontSize, font);
    }
    
    /**
     * Helper: Desenha item com bullet.
     */
    private void bulletItem(StringBuilder stream, String titulo, String conteudo, int y) {
        // Prepara o conteúdo (trim e trata null/blank)
        String conteudoFinal = (conteudo != null && !conteudo.isBlank()) 
            ? conteudo.trim() 
            : "-";
        
        // Prepara o título (trim e garante que termine com ":")
        String tituloFinal = titulo != null ? titulo.trim() : "";
        if (!tituloFinal.endsWith(":")) {
            tituloFinal = tituloFinal + ":";
        }
        tituloFinal = tituloFinal + " "; // Adiciona espaço após os dois pontos
        
        // Bullet "• " (bullet + espaço) em x=90
        setFont(stream, "/F2", 14);
        stream.append("1 0 0 1 90 ").append(y).append(" Tm\n");
        stream.append("(\\267 ) Tj\n"); // • + espaço em octal (267 = 0xB7 = • em WinAnsiEncoding)
        
        // Título em negrito em x=110 (já com ": " no final)
        // Usamos textAt que já desenha o título completo com o espaço
        textAt(stream, tituloFinal, 110, y, 10, "/F2");
        
        // Calcula a posição X após o título
        // Para fonte em negrito (F2) tamanho 10, usamos estimativa ajustada
        // Fonte em negrito tende a ser ~20-25% mais larga que fonte normal
        // Usamos 0.68 por caractere para evitar sobreposição mantendo espaçamento adequado
        double larguraPorChar = 10 * 0.68; // Ajustado para fonte em negrito
        int xConteudo = 110 + (int)(tituloFinal.length() * larguraPorChar);
        
        // Conteúdo normal na mesma linha
        setFont(stream, "/F1", 10);
        stream.append("1 0 0 1 ").append(xConteudo).append(" ").append(y).append(" Tm\n");
        String conteudoEscapado = escaparPDF(conteudoFinal);
        stream.append("(").append(conteudoEscapado).append(") Tj\n");
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