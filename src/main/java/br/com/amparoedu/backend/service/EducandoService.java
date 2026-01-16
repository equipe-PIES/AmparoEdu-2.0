package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.Responsavel;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.EnderecoRepository;
import br.com.amparoedu.backend.repository.ResponsavelRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.lang.Exception;

public class EducandoService {
    private final EducandoRepository educandoRepo = new EducandoRepository();
    private final EnderecoRepository enderecoRepo = new EnderecoRepository();
    private final ResponsavelRepository responsavelRepo = new ResponsavelRepository();

    public boolean cadastrarNovoAluno(Educando aluno, Endereco endereco, Responsavel responsavel) throws Exception {
        
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(aluno.getData_nascimento(), formatador);
        // Validação da data de nascimento
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new Exception("A data de nascimento não pode ser no futuro.");
        }

        // Validação de CPF
        if (!isCPFValido(aluno.getCpf()) || !isCPFValido(responsavel.getCpf())) {
            throw new Exception("Um dos CPFs informados é inválido.");
        }
        try {
            // Gera IDs únicos
            String enderecoId = UUID.randomUUID().toString();
            String alunoId = UUID.randomUUID().toString();
            String responsavelId = UUID.randomUUID().toString();

            // Define o ID e sincronização do endereço
            endereco.setId(enderecoId);
            endereco.setSincronizado(0);

            // Vincula o aluno ao endereço criado
            aluno.setId(alunoId);
            aluno.setEndereco_id(enderecoId);
            aluno.setSincronizado(0);

            // Vincula o responsável ao aluno criado
            responsavel.setId(responsavelId);
            responsavel.setEducando_id(alunoId);
            responsavel.setSincronizado(0);

            // Salva os dados no banco
            enderecoRepo.salvar(endereco);
            educandoRepo.salvar(aluno);
            responsavelRepo.salvar(responsavel);

            System.out.println("Cadastro realizado com sucesso para: " + aluno.getNome());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar o aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarAluno(Educando aluno, Endereco endereco, Responsavel responsavel) throws Exception {
        
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(aluno.getData_nascimento(), formatador);
        // Validação da data de nascimento
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new Exception("A data de nascimento não pode ser no futuro.");
        }

        // Validação de CPF
        if (!isCPFValido(aluno.getCpf()) || !isCPFValido(responsavel.getCpf())) {
            throw new Exception("Um dos CPFs informados é inválido.");
        }
        try {
            // Atualiza sincronização para pendente
            endereco.setSincronizado(0);
            aluno.setSincronizado(0);
            responsavel.setSincronizado(0);

            // Atualiza os dados no banco
            enderecoRepo.atualizar(endereco);
            educandoRepo.atualizar(aluno);
            responsavelRepo.atualizar(responsavel);

            System.out.println("Atualização realizada com sucesso para: " + aluno.getNome());
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirEducando(String educandoId) {
        try {
            // Busca o educando
            Educando educando = educandoRepo.buscarPorId(educandoId);
            if (educando == null) {
                return false;
            }
            
            // Marca o educando como excluído
            educando.setExcluido(1);
            educando.setSincronizado(0);
            educandoRepo.atualizar(educando);
            
            // Marca o responsável como excluído
            Responsavel responsavel = responsavelRepo.buscarPorEducando(educandoId);
            if (responsavel != null) {
                responsavel.setExcluido(1);
                responsavel.setSincronizado(0);
                responsavelRepo.atualizar(responsavel);
            }
            
            // Marca o endereço como excluído
            Endereco endereco = enderecoRepo.buscarPorEducando(educandoId);
            if (endereco != null) {
                endereco.setExcluido(1);
                endereco.setSincronizado(0);
                enderecoRepo.atualizar(endereco);
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validação básica de CPF
    private boolean isCPFValido(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;
        try {
            for (int j = 9; j < 11; j++) {
                int soma = 0, peso = j + 1;
                for (int i = 0; i < j; i++) soma += (cpf.charAt(i) - '0') * peso--;
                int digito = 11 - (soma % 11);
                if (digito > 9) digito = 0;
                if (digito != (cpf.charAt(j) - '0')) return false;
            }
            return true;
        } catch (Exception e) { return false; }
    }
}