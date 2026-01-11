package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.util.List;

public class ViewTurmaController {

    @FXML private Label nomeUsuario, cargoUsuario, nomeTurmaLabel, totalAlunosTurma;
    @FXML private Label grauTurma, turnoTurma, fxEtariaTurma;
    @FXML private TextField buscarAluno;
    @FXML private FlowPane containerAlunos;

    private final EducandoRepository educandoRepo = new EducandoRepository();
    private Turma turmaAtual;

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
    }

    public void setTurma(Turma turma) {
        this.turmaAtual = turma;
        preencherDadosCabecalho();
        carregarAlunos();
    }

    private void preencherDadosCabecalho() {
        nomeTurmaLabel.setText(turmaAtual.getNome());
        grauTurma.setText(turmaAtual.getGrau_ensino());
        turnoTurma.setText(turmaAtual.getTurno());
        fxEtariaTurma.setText(turmaAtual.getFaixa_etaria());
    }

    private void carregarAlunos() {
        containerAlunos.getChildren().clear();
    
        // Busca os alunos vinculados a esta turma através do repositório
        List<Educando> alunos = educandoRepo.buscarPorTurma(turmaAtual.getId());
        totalAlunosTurma.setText(String.valueOf(alunos.size()));

        for (Educando aluno : alunos) {
            try {
                // carrega o FXML do Card do Aluno
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-aluno.fxml"));
                Node card = loader.load();

                // pega o controlador do card e passa o objeto Educando
                CardAlunoController controller = loader.getController();
                controller.setEducando(aluno);
                controller.setTurma(turmaAtual);

                // armazena o objeto no card para usar na busca
                card.setUserData(aluno);

                // adiciona o card ao FlowPane da tela de visualização
                containerAlunos.getChildren().add(card);
                
            } catch (IOException e) {
                System.err.println("Erro ao carregar card do aluno: " + e.getMessage());
            }
        }
    }

    // Filtra os alunos exibidos com base no texto de busca
    @FXML
    private void btnBuscarAlunoClick() {
        String filtro = buscarAluno.getText() == null ? "" : buscarAluno.getText().toLowerCase().trim();
        for (Node node : containerAlunos.getChildren()) {
            Educando aluno = (Educando) node.getUserData();
            // Verifica o nome do aluno  
            boolean match = filtro.isEmpty() || (aluno != null && aluno.getNome().toLowerCase().contains(filtro));
            
            node.setVisible(match);
            node.setManaged(match);
        }
    }

    // Navega de volta para a tela de turmas
    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-professor.fxml");
    }

    // Realiza logout e volta para a tela de login
    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
}