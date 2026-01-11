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
        
        if (aluno.getData_nascimento() != null && !aluno.getData_nascimento().isEmpty()) {
            try {
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNascimento = LocalDate.parse(aluno.getData_nascimento(), formatador);
                
                if (dataNascimento.isAfter(LocalDate.now())) {
                    throw new Exception("A data de nascimento não pode ser no futuro.");
                }
            } catch (Exception e) {
                // Se a data for inválida mas não for erro de futuro, decide se lança ou ignora
                if (e.getMessage().contains("futuro")) throw e;
                // Caso contrário, pode ser formato inválido, assumimos que se o user digitou algo, deve estar certo ou ser validado no front.
                // Mas aqui estamos protegendo o backend.
            }
        }

        if (aluno.getCpf() != null && !aluno.getCpf().isEmpty()) {
            if (!isCPFValido(aluno.getCpf())) {
                throw new Exception("CPF do aluno inválido.");
            }
        }

        if (responsavel.getCpf() != null && !responsavel.getCpf().isEmpty()) {
            if (!isCPFValido(responsavel.getCpf())) {
                throw new Exception("CPF do responsável inválido.");
            }
        }

        try {
            // Set sync status to 0 to trigger sync later if needed
            aluno.setSincronizado(0);
            endereco.setSincronizado(0);
            responsavel.setSincronizado(0);

            // Gerenciar Endereço (Atualizar ou Criar)
            if (endereco.getId() == null || endereco.getId().isEmpty()) {
                String novoEndId = UUID.randomUUID().toString();
                endereco.setId(novoEndId);
                enderecoRepo.salvar(endereco);
                aluno.setEndereco_id(novoEndId);
            } else {
                enderecoRepo.atualizar(endereco);
            }

            // Gerenciar Responsável (Atualizar ou Criar)
            if (responsavel.getId() == null || responsavel.getId().isEmpty()) {
                String novoRespId = UUID.randomUUID().toString();
                responsavel.setId(novoRespId);
                responsavel.setEducando_id(aluno.getId());
                responsavelRepo.salvar(responsavel);
            } else {
                responsavelRepo.atualizar(responsavel);
            }

            // Atualizar Aluno
            educandoRepo.atualizar(aluno);

            System.out.println("Atualização realizada com sucesso para: " + aluno.getNome());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o aluno: " + e.getMessage());
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