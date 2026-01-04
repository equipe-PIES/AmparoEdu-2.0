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
    @FXML private Button cadastroBtn;

    private final EducandoService educandoService = new EducandoService();
    
    // Campos estáticos para edição
    private static Educando alunoEdicao;
    private static Endereco enderecoEdicao;
    private static Responsavel responsavelEdicao;

    public static void setDadosEdicao(Educando aluno, Endereco endereco, Responsavel responsavel) {
        alunoEdicao = aluno;
        enderecoEdicao = endereco;
        responsavelEdicao = responsavel;
    }

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        adicionarMascaraCPF(cpfAluno);
        adicionarMascaraCPF(cpfRespon);
        adicionarMascaraTelefone(contatoRespon);
        adicionarMascaraCep(endCEP);
        
        if (alunoEdicao != null) {
            preencherCamposEdicao();
        }
    }

    private void preencherCamposEdicao() {
        // Preencher dados do aluno
        nomeAluno.setText(alunoEdicao.getNome());
        cpfAluno.setText(alunoEdicao.getCpf());
        dtNascAluno.setValue(LocalDate.parse(alunoEdicao.getData_nascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        generoAluno.setValue(alunoEdicao.getGenero());
        grauEscAluno.setValue(alunoEdicao.getGrau_ensino());
        escolaAluno.setText(alunoEdicao.getEscola());
        cidAluno.setText(alunoEdicao.getCid());
        nisAluno.setText(alunoEdicao.getNis());
        obsAluno.setText(alunoEdicao.getObservacoes());

        // Preencher dados do responsável
        if (responsavelEdicao != null) {
            nomeRespon.setText(responsavelEdicao.getNome());
            cpfRespon.setText(responsavelEdicao.getCpf());
            parentescoRespon.setValue(responsavelEdicao.getParentesco());
            contatoRespon.setText(responsavelEdicao.getTelefone());
        }

        // Preencher dados do endereço
        if (enderecoEdicao != null) {
            endUF.setValue(enderecoEdicao.getUf());
            endCidade.setText(enderecoEdicao.getCidade());
            endCEP.setText(enderecoEdicao.getCep());
            endRua.setText(enderecoEdicao.getLogradouro());
            endNum.setText(enderecoEdicao.getNumero());
            endBairro.setText(enderecoEdicao.getBairro());
            endComplemento.setText(enderecoEdicao.getComplemento());
        }
        
        // Alterar texto do botão (se houver referência, senão apenas o comportamento muda)
        // Como o botão tem ação definida no FXML, vamos manter o nome do método mas alterar a lógica
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
                boolean isEdicao = (alunoEdicao != null);
                
                Endereco endereco = isEdicao ? enderecoEdicao : new Endereco();
                endereco.setUf(endUF.getValue());
                endereco.setCidade(endCidade.getText());
                endereco.setBairro(endBairro.getText());
                endereco.setLogradouro(endRua.getText());
                endereco.setNumero(endNum.getText());
                endereco.setComplemento(endComplemento.getText());
                endereco.setCep(endCEP.getText());

                Educando aluno = isEdicao ? alunoEdicao : new Educando();
                aluno.setNome(nomeAluno.getText());
                aluno.setCpf(cpfAluno.getText());
                aluno.setData_nascimento(dtNascAluno.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                aluno.setGenero(generoAluno.getValue());
                aluno.setGrau_ensino(grauEscAluno.getValue());
                aluno.setEscola(escolaAluno.getText());
                aluno.setCid(cidAluno.getText());
                aluno.setNis(nisAluno.getText());
                aluno.setObservacoes(obsAluno.getText());

                Responsavel responsavel = isEdicao ? responsavelEdicao : new Responsavel();
                responsavel.setNome(nomeRespon.getText());
                responsavel.setCpf(cpfRespon.getText());
                responsavel.setParentesco(parentescoRespon.getValue());
                responsavel.setTelefone(contatoRespon.getText());

                boolean sucesso;
                if (isEdicao) {
                    sucesso = educandoService.atualizarAluno(aluno, endereco, responsavel);
                } else {
                    sucesso = educandoService.cadastrarNovoAluno(aluno, endereco, responsavel);
                }

                if (sucesso) {
                    exibirAlertaSucesso(isEdicao ? "Aluno atualizado com sucesso!" : "Aluno cadastrado com sucesso!");
                    limparCampos();
                } else {
                    ErrorForm.setText("Erro ao salvar no banco de dados.");
                }
            } catch (Exception e) {
                ErrorForm.setText("Erro ao salvar aluno: " + e.getMessage());
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

    private void exibirAlertaSucesso(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void btnCancelaCadastroClick() {
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
    }

    @FXML private void btnSairClick() { AuthService.logout(); GerenciadorTelas.trocarTela("tela-de-login.fxml"); }
    @FXML private void btnInicioClick() { GerenciadorTelas.trocarTela("tela-inicio-coord.fxml"); }
    @FXML private void btnTurmasClick() { GerenciadorTelas.trocarTela("view-turmas-coord.fxml"); }
    @FXML private void btnProfessoresClick() { GerenciadorTelas.trocarTela("view-profs-coord.fxml"); }
    @FXML private void btnAlunosClick() { GerenciadorTelas.trocarTela("view-alunos-coord.fxml"); }

    private void limparCampos() {
        alunoEdicao = null;
        enderecoEdicao = null;
        responsavelEdicao = null;
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
    }
}