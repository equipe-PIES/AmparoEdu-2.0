package br.com.amparoedu.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class ViewAlunosCoordController {

    @FXML private Label nomeUsuario;
    @FXML private Label cargoUsuario;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;
    @FXML private TextField buscarAluno;
    @FXML private Button btnSair;
    @FXML private Button btnBuscarAluno;
    @FXML private ChoiceBox<String> filterTipo;
    @FXML private ChoiceBox<String> filterOpcoes;

    private final EducandoRepository educandoRepo = new EducandoRepository();
    private List<Educando> todosAlunos;

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
        
        if (indicadorDeTela != null) {
            indicadorDeTela.setText("Alunos Cadastrados");
        }
        
        configurarFiltros();
        carregarAlunos();
        
        // Adiciona listener para busca em tempo real (ao digitar)
        if (buscarAluno != null) {
            buscarAluno.textProperty().addListener((observable, oldValue, newValue) -> {
                aplicarFiltros();
            });
        }
        
        // Configura ação do botão buscar (redundante, mas útil para UX)
        if (btnBuscarAluno != null) {
            btnBuscarAluno.setOnAction(event -> aplicarFiltros());
        }
    }

    private void configurarFiltros() {
        if (filterTipo != null && filterOpcoes != null) {
            filterTipo.setItems(FXCollections.observableArrayList("Sem filtro", "Grau de Escolaridade"));
            filterTipo.setValue("Sem filtro");
            
            // Inicialmente desabilita opções
            filterOpcoes.setDisable(true);
            
            filterTipo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if ("Grau de Escolaridade".equals(newVal)) {
                    filterOpcoes.setDisable(false);
                    filterOpcoes.setItems(FXCollections.observableArrayList(
                        "Educação Infantil", 
                        "Ensino Fundamental I", 
                        "Ensino Fundamental II", 
                        "Ensino Médio", 
                        "EJA",
                        "Outro"
                    ));
                } else {
                    filterOpcoes.setDisable(true);
                    filterOpcoes.setValue(null);
                    filterOpcoes.setItems(FXCollections.observableArrayList());
                }
                aplicarFiltros();
            });
            
            filterOpcoes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                aplicarFiltros();
            });
        }
    }

    private void carregarAlunos() {
        todosAlunos = educandoRepo.listarTodos();
        exibirAlunos(todosAlunos);
    }

    private void exibirAlunos(List<Educando> alunos) {
        containerCards.getChildren().clear();
        
        for (Educando aluno : alunos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-aluno-edit.fxml"));
                Node card = loader.load();

                CardAlunoEditController controller = loader.getController();
                controller.setEducando(aluno);

                containerCards.getChildren().add(card);
            } catch (IOException e) {
                System.err.println("Erro ao carregar card do aluno: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void aplicarFiltros() {
        if (todosAlunos == null) return;
        
        String termoBusca = (buscarAluno != null) ? buscarAluno.getText() : "";
        String tipoFiltro = (filterTipo != null) ? filterTipo.getValue() : "Sem filtro";
        String opcaoFiltro = (filterOpcoes != null) ? filterOpcoes.getValue() : null;
        
        List<Educando> filtrados = todosAlunos.stream()
            .filter(a -> {
                // Filtro de nome
                if (termoBusca != null && !termoBusca.isEmpty()) {
                    if (!a.getNome().toLowerCase().contains(termoBusca.toLowerCase())) {
                        return false;
                    }
                }
                
                // Filtro de Escolaridade
                if ("Grau de Escolaridade".equals(tipoFiltro) && opcaoFiltro != null && !opcaoFiltro.isEmpty()) {
                    String grauAluno = a.getGrau_ensino();
                    if (grauAluno == null || !grauAluno.equals(opcaoFiltro)) {
                        return false;
                    }
                }
                
                return true;
            })
            .collect(Collectors.toList());
            
        exibirAlunos(filtrados);
    }

    // Método antigo mantido apenas por compatibilidade temporária se necessário, 
    // mas redirecionando para a nova lógica
    private void filtrarAlunos(String termo) {
        aplicarFiltros();
    }

    @FXML
    private void btnInicioClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml");
    }

    @FXML
    private void btnTurmasClick() {
        GerenciadorTelas.getInstance().trocarTela("view-turmas-coord.fxml");
    }

    @FXML
    private void btnProfessoresClick() {
        GerenciadorTelas.getInstance().trocarTela("view-profs-coord.fxml");
    }

    @FXML
    private void btnAlunosClick() {
    }

    @FXML
    private void handleSairButtonAction() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
}
