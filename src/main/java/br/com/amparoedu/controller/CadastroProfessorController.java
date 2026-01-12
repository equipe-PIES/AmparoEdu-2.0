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
    @FXML private Label erroEscolhaSenha;
    @FXML private Label nomeUsuario, cargoUsuario; 
    @FXML private Button cadastroProfBt, btnCancelCadastro; 

    private final ProfessorService professorService = new ProfessorService();
    
    private static Professor professorEdicao;
    private static Usuario usuarioEdicao;

    public static void setDadosEdicao(Professor professor, Usuario usuario) {
        professorEdicao = professor;
        usuarioEdicao = usuario;
    }

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        adicionarMascaraCPF(cpfProf);
        
        if (professorEdicao != null) {
            preencherCamposEdicao();
        } else {
            if (cadastroProfBt != null) cadastroProfBt.setText("Cadastrar");
        }
    }
    
    private void preencherCamposEdicao() {
        nomeProf.setText(professorEdicao.getNome());
        cpfProf.setText(professorEdicao.getCpf());
        
        if (professorEdicao.getData_nascimento() != null && !professorEdicao.getData_nascimento().isEmpty()) {
            try {
                dtNascProf.setValue(java.time.LocalDate.parse(professorEdicao.getData_nascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (Exception e) {
                // Ignora data inválida
            }
        }
        
        generoProf.setValue(professorEdicao.getGenero());
        obsProf.setText(professorEdicao.getObservacoes());
        
        if (usuarioEdicao != null) {
            emailProf.setText(usuarioEdicao.getEmail());
            // Senha não é preenchida por segurança, só altera se o usuário digitar nova
        }
        
        if (cadastroProfBt != null) {
            cadastroProfBt.setText("Salvar Alterações");
        }
    }

    private void configurarTopo() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
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
                
                boolean isEdicao = (professorEdicao != null);
                Professor professor = isEdicao ? professorEdicao : new Professor();
                Usuario usuario = isEdicao ? usuarioEdicao : new Usuario();

                professor.setNome(nomeProf.getText());
                professor.setCpf(cpfProf.getText());
                if (dtNascProf.getValue() != null) {
                    professor.setData_nascimento(dtNascProf.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } else {
                    professor.setData_nascimento(null);
                }
                professor.setGenero(generoProf.getValue());
                professor.setObservacoes(obsProf.getText());

                usuario.setEmail(emailProf.getText());
                
                // Só atualiza a senha se o campo não estiver vazio ou se for novo cadastro
                if (!passwordProf.getText().isEmpty()) {
                    usuario.setSenha(passwordProf.getText());
                }
                usuario.setTipo("Professor"); 

                boolean sucesso;
                if (isEdicao) {
                    sucesso = professorService.atualizarProfessor(professor, usuario);
                } else {
                    sucesso = professorService.cadastrarNovoProfessor(professor, usuario);
                }

                if (sucesso) {
                    exibirAlertaSucesso(isEdicao ? "Dados atualizados com sucesso!" : "Professor cadastrado com sucesso!");
                    limparCampos();
                } else {
                    erroEscolhaSenha.setText("Erro ao salvar no banco de dados.");
                }
            } catch (Exception e) {
                erroEscolhaSenha.setText("Erro ao salvar professor: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean validarCampos() {
        // Se for edição, senha é opcional
        boolean senhaObrigatoria = (professorEdicao == null);
        
        if (nomeProf.getText().isEmpty() || cpfProf.getText().isEmpty() || emailProf.getText().isEmpty()) {
             erroEscolhaSenha.setText("Por favor, preencha os campos obrigatórios (Nome, CPF, Email).");
             return false;
        }
        
        if (senhaObrigatoria && passwordProf.getText().isEmpty()) {
            erroEscolhaSenha.setText("A senha é obrigatória para novos cadastros.");
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

    private void limparCampos() {
        professorEdicao = null;
        usuarioEdicao = null;
        nomeProf.clear();
        cpfProf.clear();
        dtNascProf.setValue(null);
        generoProf.setValue(null);
        obsProf.clear();
        emailProf.clear();
        passwordProf.clear();
        confirmPasswordProf.clear();
        erroEscolhaSenha.setText("");
        if (cadastroProfBt != null) cadastroProfBt.setText("Cadastrar");
    }

    @FXML
    private void handleCancelCadastroAction() {
        limparCampos();
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml"); 
    }

    // Navegação Topo
    @FXML private void handleSairButtonAction() { AuthService.logout(); GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml"); }
    @FXML private void handleInicioButtonAction() { GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml"); }
    @FXML private void handleTurmasButtonAction() { GerenciadorTelas.getInstance().trocarTela("view-turmas-coord.fxml"); }
    @FXML private void handleProfessoresButtonAction() { GerenciadorTelas.getInstance().trocarTela("view-profs-coord.fxml"); }
    @FXML private void handleAlunosButtonAction() { GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml"); }
}