package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.*;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.EducandoService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private final EducandoService educandoService = new EducandoService();

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        adicionarMascaraCPF(cpfAluno);
        adicionarMascaraCPF(cpfRespon);
        adicionarMascaraTelefone(contatoRespon);
        adicionarMascaraCep(endCEP);
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
                Endereco endereco = new Endereco();
                endereco.setUf(endUF.getValue());
                endereco.setCidade(endCidade.getText());
                endereco.setBairro(endBairro.getText());
                endereco.setLogradouro(endRua.getText());
                endereco.setNumero(endNum.getText());
                endereco.setComplemento(endComplemento.getText());
                endereco.setCep(endCEP.getText());

                Educando aluno = new Educando();
                aluno.setNome(nomeAluno.getText());
                aluno.setCpf(cpfAluno.getText());
                aluno.setData_nascimento(dtNascAluno.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                aluno.setGenero(generoAluno.getValue());
                aluno.setGrau_ensino(grauEscAluno.getValue());
                aluno.setEscola(escolaAluno.getText());
                aluno.setCid(cidAluno.getText());
                aluno.setNis(nisAluno.getText());
                aluno.setObservacoes(obsAluno.getText());

                Responsavel responsavel = new Responsavel();
                responsavel.setNome(nomeRespon.getText());
                responsavel.setCpf(cpfRespon.getText());
                responsavel.setParentesco(parentescoRespon.getValue());
                responsavel.setTelefone(contatoRespon.getText());

                if (educandoService.cadastrarNovoAluno(aluno, endereco, responsavel)) {
                    exibirAlertaSucesso();
                    limparCampos();
                } else {
                    ErrorForm.setText("Erro ao salvar no banco de dados.");
                }
            } catch (Exception e) {
                ErrorForm.setText("Erro ao cadastrar aluno: " + e.getMessage());
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

    private void exibirAlertaSucesso() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Aluno cadastrado com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void btnCancelaCadastroClick() {
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
    }

    @FXML private void btnSairClick() { AuthService.logout(); GerenciadorTelas.trocarTela("tela-de-login.fxml"); }
    @FXML private void btnInicioClick() { GerenciadorTelas.trocarTela("tela-inicio-coord.fxml"); }
    @FXML private void btnTurmasClick() { /* Navegar para turmas */ }
    @FXML private void btnProfessoresClick() { /* Navegar para professores */ }
    @FXML private void btnAlunosClick() { GerenciadorTelas.trocarTela("tela-alunos.fxml"); }

    private void limparCampos() {
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