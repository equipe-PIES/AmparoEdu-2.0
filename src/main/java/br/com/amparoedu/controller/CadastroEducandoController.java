package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.*;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.EducandoService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastroEducandoController {

    @FXML private TextField nomeAluno, cpfAluno, escolaAluno, cidAluno, nisAluno;
    @FXML private DatePicker dtNascAluno;
    @FXML private ChoiceBox<String> generoAluno, grauEscAluno;
    @FXML private TextArea obsAluno;
    @FXML private TextField nomeRespon, cpfRespon, contatoRespon;
    @FXML private ChoiceBox<String> parentescoRespon;
    @FXML private ChoiceBox<String> endUF;
    @FXML private TextField endCidade, endCEP, endRua, endNum, endBairro, endComplemento;
    @FXML private Label nomeUsuario, cargoUsuario, ErrorForm;
    @FXML private Button btnCadastroAluno;

    private final EducandoService educandoService = new EducandoService();

    private static Educando educandoEdicao;
    private static Responsavel responsavelEdicao;
    private static Endereco enderecoEdicao;

    public static void setDadosEdicao(Educando educando, Responsavel responsavel, Endereco endereco) {
        educandoEdicao = educando;
        responsavelEdicao = responsavel;
        enderecoEdicao = endereco;
    }

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        adicionarMascaraCPF(cpfAluno);
        adicionarMascaraCPF(cpfRespon);
        adicionarMascaraTelefone(contatoRespon);
        adicionarMascaraCep(endCEP);

        if (educandoEdicao != null) {
            preencherCamposEdicao();
        }
    }

    private void preencherCamposEdicao() {
        if (educandoEdicao != null) {
            nomeAluno.setText(educandoEdicao.getNome());
            cpfAluno.setText(educandoEdicao.getCpf());
            if (educandoEdicao.getData_nascimento() != null && !educandoEdicao.getData_nascimento().isEmpty()) {
                try {
                    dtNascAluno.setValue(LocalDate.parse(educandoEdicao.getData_nascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } catch (Exception e) {
                    System.err.println("Erro ao parsear data: " + e.getMessage());
                }
            }
            generoAluno.setValue(educandoEdicao.getGenero());
            grauEscAluno.setValue(educandoEdicao.getGrau_ensino());
            escolaAluno.setText(educandoEdicao.getEscola());
            cidAluno.setText(educandoEdicao.getCid());
            nisAluno.setText(educandoEdicao.getNis());
            obsAluno.setText(educandoEdicao.getObservacoes());
            
            if (btnCadastroAluno != null) {
                btnCadastroAluno.setText("Salvar Alterações");
            }
        }
        
        if (responsavelEdicao != null) {
            nomeRespon.setText(responsavelEdicao.getNome());
            cpfRespon.setText(responsavelEdicao.getCpf());
            parentescoRespon.setValue(responsavelEdicao.getParentesco());
            contatoRespon.setText(responsavelEdicao.getTelefone());
        }
        
        if (enderecoEdicao != null) {
            endUF.setValue(enderecoEdicao.getUf());
            endCidade.setText(enderecoEdicao.getCidade());
            endCEP.setText(enderecoEdicao.getCep());
            endRua.setText(enderecoEdicao.getLogradouro());
            endNum.setText(enderecoEdicao.getNumero());
            endBairro.setText(enderecoEdicao.getBairro());
            endComplemento.setText(enderecoEdicao.getComplemento());
        }
    }

    private void adicionarMascaraCPF(TextField textField) {
        textField.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            String apenasNumeros = valorNovo.replaceAll("\\D", "");
            
            if (apenasNumeros.length() > 11) {
                apenasNumeros = apenasNumeros.substring(0, 11);
            }

            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 3 || i == 6) formatado.append(".");
                if (i == 9) formatado.append("-");
                formatado.append(apenasNumeros.charAt(i));
            }

            if (!valorNovo.equals(formatado.toString())) {
                textField.setText(formatado.toString());
                textField.positionCaret(formatado.length());
            }
        });
    }

    private void adicionarMascaraTelefone(TextField textField) {
        textField.textProperty().addListener((obs, antigo, novo) -> {
            String numeros = novo.replaceAll("\\D", "");
            if (numeros.length() > 11) numeros = numeros.substring(0, 11);
            StringBuilder f = new StringBuilder();
            int t = numeros.length();
            if (t > 0) f.append("(").append(numeros.substring(0, Math.min(t, 2)));
            if (t > 2) f.append(") ").append(numeros.substring(2, Math.min(t, 7)));
            if (t > 7) f.append("-").append(numeros.substring(7, t));
            if (!novo.equals(f.toString())) {
                textField.setText(f.toString());
                textField.positionCaret(f.length());
            }
        });
    }

    private void adicionarMascaraCep(TextField textField) {
        textField.textProperty().addListener((obs, antigo, novo) -> {
            String numeros = novo.replaceAll("\\D", "");
            if (numeros.length() > 8) numeros = numeros.substring(0, 8);
            StringBuilder f = new StringBuilder();
            int t = numeros.length();
            if (t > 5) f.append(numeros.substring(0, 5)).append("-").append(numeros.substring(5, t));
            else f.append(numeros);
            if (!novo.equals(f.toString())) {
                textField.setText(f.toString());
                textField.positionCaret(f.length());
            }
        });
    }

    private void configurarTopo() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
    }

    private void popularChoiceBoxes() {
        generoAluno.setItems(FXCollections.observableArrayList("Masculino", "Feminino", "Outro"));
        grauEscAluno.setItems(FXCollections.observableArrayList("Estimulação Precoce","Educação Infantil", "Ensino Fundamental I", "Ensino Fundamental II", "Ensino Médio", "Outro"));
        parentescoRespon.setItems(FXCollections.observableArrayList("Pai", "Mãe", "Avô/Avó", "Tio/Tia", "Outro"));
        endUF.setItems(FXCollections.observableArrayList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"));
    }

    @FXML
    private void btnCadastroAlunoClick() {
        if (validarCampos()) {
            try {
                // Se estiver editando, usa os objetos existentes, caso contrário cria novos
                Endereco endereco = (enderecoEdicao != null) ? enderecoEdicao : new Endereco();
                endereco.setUf(endUF.getValue());
                endereco.setCidade(endCidade.getText());
                endereco.setBairro(endBairro.getText());
                endereco.setLogradouro(endRua.getText());
                endereco.setNumero(endNum.getText());
                endereco.setComplemento(endComplemento.getText());
                endereco.setCep(endCEP.getText());

                Educando aluno = (educandoEdicao != null) ? educandoEdicao : new Educando();
                aluno.setNome(nomeAluno.getText());
                aluno.setCpf(cpfAluno.getText());
                if (dtNascAluno.getValue() != null) {
                    aluno.setData_nascimento(dtNascAluno.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                aluno.setGenero(generoAluno.getValue());
                aluno.setGrau_ensino(grauEscAluno.getValue());
                aluno.setEscola(escolaAluno.getText());
                aluno.setCid(cidAluno.getText());
                aluno.setNis(nisAluno.getText());
                aluno.setObservacoes(obsAluno.getText());

                Responsavel responsavel = (responsavelEdicao != null) ? responsavelEdicao : new Responsavel();
                responsavel.setNome(nomeRespon.getText());
                responsavel.setCpf(cpfRespon.getText());
                responsavel.setParentesco(parentescoRespon.getValue());
                responsavel.setTelefone(contatoRespon.getText());

                boolean sucesso;
                if (educandoEdicao != null) {
                    sucesso = educandoService.atualizarAluno(aluno, endereco, responsavel);
                    if (sucesso) {
                        exibirAlertaSucesso("As alterações foram realizadas com sucesso");
                        limparCampos();
                        GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml");
                    }
                } else {
                    sucesso = educandoService.cadastrarNovoAluno(aluno, endereco, responsavel);
                    if (sucesso) {
                        exibirAlertaSucesso("Aluno cadastrado com sucesso!");
                        limparCampos();
                    }
                }

                if (!sucesso) {
                    ErrorForm.setText("Erro ao salvar no banco de dados.");
                }
            } catch (Exception e) {
                ErrorForm.setText("Erro ao processar aluno: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean validarCampos() {
        if (nomeAluno.getText().isEmpty() || cpfAluno.getText().isEmpty() || dtNascAluno.getValue() == null) {
            ErrorForm.setText("Por favor, preencha os dados básicos do aluno.");
            return false;
        }
        return true;
    }

    private void exibirAlertaSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void btnCancelaCadastroClick() {
        limparCampos();
        GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml");
    }

    @FXML private void btnSairClick() { AuthService.logout(); GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml"); }
    @FXML private void btnInicioClick() { GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml"); }
    @FXML private void btnTurmasClick() { /* Navegar para turmas */ }
    @FXML private void btnProfessoresClick() { /* Navegar para professores */ }
    @FXML private void btnAlunosClick() { GerenciadorTelas.getInstance().trocarTela("tela-alunos.fxml"); }

    private void limparCampos() {
        educandoEdicao = null;
        responsavelEdicao = null;
        enderecoEdicao = null;
        
        nomeAluno.clear();
        cpfAluno.clear();
        escolaAluno.clear();
        cidAluno.clear();
        nisAluno.clear();
        dtNascAluno.setValue(null);
        generoAluno.setValue(null);
        grauEscAluno.setValue(null);
        obsAluno.clear();
        nomeRespon.clear();
        cpfRespon.clear();
        contatoRespon.clear();
        parentescoRespon.setValue(null);
        endUF.setValue(null);
        endCidade.clear();
        endCEP.clear();
        endRua.clear();
        endNum.clear();
        endBairro.clear();
        endComplemento.clear();
        
        if (btnCadastroAluno != null) {
            btnCadastroAluno.setText("Cadastrar aluno(a)");
        }
    }
}
