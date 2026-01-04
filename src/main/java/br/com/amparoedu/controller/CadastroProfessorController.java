package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.ProfessorService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;

public class CadastroProfessorController {

    @FXML private TextField nomeProf, cpfProf, emailProf;
    @FXML private DatePicker dtNascProf;
    @FXML private ChoiceBox<String> generoProf;
    @FXML private TextArea obsProf;
    @FXML private PasswordField passwordProf, confirmPasswordProf;
    @FXML private Label nameUser, cargoUser, erroEscolhaSenha;
    @FXML private Button cadastroProfBt, cancelCadastroBt;

    private final ProfessorService professorService = new ProfessorService();

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        adicionarMascaraCPF(cpfProf);
    }

    private void configurarTopo() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nameUser.setText(logado.getEmail());
            cargoUser.setText(logado.getTipo());
        }
    }

    private void popularChoiceBoxes() {
        generoProf.setItems(FXCollections.observableArrayList("Masculino", "Feminino", "Outro"));
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

    @FXML
    private void handleCadastroProfClick() {
        erroEscolhaSenha.setText(""); // Limpa mensagem de erro anterior

        if (validarCampos()) {
            try {
                if (!passwordProf.getText().equals(confirmPasswordProf.getText())) {
                    erroEscolhaSenha.setText("As senhas não coincidem.");
                    return;
                }

                Professor professor = new Professor();
                professor.setNome(nomeProf.getText());
                professor.setCpf(cpfProf.getText());
                if (dtNascProf.getValue() != null) {
                    professor.setData_nascimento(dtNascProf.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                professor.setGenero(generoProf.getValue());
                professor.setObservacoes(obsProf.getText());

                Usuario usuario = new Usuario();
                usuario.setEmail(emailProf.getText());
                usuario.setSenha(passwordProf.getText());
                usuario.setTipo("Professor"); 

                if (professorService.cadastrarNovoProfessor(professor, usuario)) {
                    exibirAlertaSucesso();
                    limparCampos();
                } else {
                    erroEscolhaSenha.setText("Erro ao salvar no banco de dados.");
                }
            } catch (Exception e) {
                erroEscolhaSenha.setText("Erro ao cadastrar professor: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean validarCampos() {
        if (nomeProf.getText().isEmpty() || cpfProf.getText().isEmpty() || dtNascProf.getValue() == null || emailProf.getText().isEmpty() || passwordProf.getText().isEmpty()) {
            erroEscolhaSenha.setText("Por favor, preencha todos os campos obrigatórios.");
            return false;
        }
        return true;
    }

    private void exibirAlertaSucesso() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Professor cadastrado com sucesso!");
        alert.showAndWait();
    }

    private void limparCampos() {
        nomeProf.clear();
        cpfProf.clear();
        dtNascProf.setValue(null);
        generoProf.setValue(null);
        obsProf.clear();
        emailProf.clear();
        passwordProf.clear();
        confirmPasswordProf.clear();
        erroEscolhaSenha.setText("");
    }

    @FXML
    private void handleCancelCadastroAction() {
        limparCampos();
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml"); 
    }

    // Navegação Topo
    @FXML private void handleSairButtonAction() { AuthService.logout(); GerenciadorTelas.trocarTela("tela-de-login.fxml"); }
    @FXML private void handleInicioButtonAction() { GerenciadorTelas.trocarTela("tela-inicio-coord.fxml"); }
    @FXML private void handleTurmasButtonAction() { GerenciadorTelas.trocarTela("view-turmas-coord.fxml"); }
    @FXML private void handleProfessoresButtonAction() { GerenciadorTelas.trocarTela("view-profs-coord.fxml"); }
    @FXML private void handleAlunosButtonAction() { GerenciadorTelas.trocarTela("view-alunos-coord.fxml"); }
}
