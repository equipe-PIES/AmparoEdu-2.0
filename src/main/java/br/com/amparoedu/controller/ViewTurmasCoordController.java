package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewTurmasCoordController {

    @FXML private Label nameUser;
    @FXML private Label cargoUser;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;
    
    @FXML private Button inicioButton;
    @FXML private Button turmasButton;
    @FXML private Button professoresButton;
    @FXML private Button alunosButton;
    @FXML private Button sairButton;
    
    @FXML private ChoiceBox<String> filterTipo;
    @FXML private ChoiceBox<String> filterOpcoes;
    @FXML private TextField buscarTurma;
    @FXML private Button buscarTurmaButton;

    private TurmaRepository turmaRepo = new TurmaRepository();
    private ProfessorRepository professorRepo = new ProfessorRepository();
    
    private List<Turma> todasTurmas;
    private List<Professor> todosProfessores;
    private Map<String, String> mapProfessorNomeId = new HashMap<>();

    @FXML
    public void initialize() {
        configurarUsuario();
        indicadorDeTela.setText("Turmas");
        
        carregarDadosIniciais();
        configurarFiltros();
        
        // Carrega inicialmente todas as turmas
        atualizarCards(todasTurmas);
    }

    private void configurarUsuario() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nameUser.setText(logado.getEmail());
            cargoUser.setText(logado.getTipo());
        }
    }
    
    private void carregarDadosIniciais() {
        todasTurmas = turmaRepo.listarTodas();
        todosProfessores = professorRepo.listarTodos();
        
        mapProfessorNomeId.clear();
        for (Professor p : todosProfessores) {
            mapProfessorNomeId.put(p.getNome(), p.getId());
        }
    }

    private void configurarFiltros() {
        // Configura filterTipo
        ObservableList<String> tipos = FXCollections.observableArrayList(
            "Sem filtro", 
            "Grau de Escolaridade", 
            "Professor Responsável"
        );
        filterTipo.setItems(tipos);
        filterTipo.setValue("Sem filtro");

        filterTipo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            atualizarOpcoesFiltro(newVal);
            aplicarFiltros();
        });

        filterOpcoes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            aplicarFiltros();
        });

        buscarTurma.textProperty().addListener((obs, oldVal, newVal) -> {
            aplicarFiltros();
        });
        
        buscarTurmaButton.setOnAction(e -> aplicarFiltros());
    }

    private void atualizarOpcoesFiltro(String tipo) {
        ObservableList<String> opcoes = FXCollections.observableArrayList();
        opcoes.add("Todas as opções");

        if ("Grau de Escolaridade".equals(tipo)) {
            opcoes.addAll("Educação Infantil", "Ensino Fundamental I", "Ensino Fundamental II", "Ensino Médio", "Outro");
        } else if ("Professor Responsável".equals(tipo)) {
            List<String> nomesProfs = todosProfessores.stream()
                .map(Professor::getNome)
                .sorted()
                .collect(Collectors.toList());
            opcoes.addAll(nomesProfs);
        }

        filterOpcoes.setItems(opcoes);
        filterOpcoes.setValue("Todas as opções");
    }

    private void aplicarFiltros() {
        String textoBusca = buscarTurma.getText() == null ? "" : buscarTurma.getText().toLowerCase();
        String tipoFiltro = filterTipo.getValue();
        String opcaoFiltro = filterOpcoes.getValue();
        
        if (opcaoFiltro == null) opcaoFiltro = "Todas as opções";

        final String finalOpcaoFiltro = opcaoFiltro;

        List<Turma> turmasFiltradas = todasTurmas.stream()
            .filter(t -> {
                // Filtro por nome (sempre ativo)
                boolean matchNome = (t.getNome() != null && t.getNome().toLowerCase().contains(textoBusca));
                
                // Filtros específicos
                boolean matchFiltroSecundario = true;
                
                if (!"Sem filtro".equals(tipoFiltro) && !"Todas as opções".equals(finalOpcaoFiltro)) {
                    if ("Grau de Escolaridade".equals(tipoFiltro)) {
                        matchFiltroSecundario = finalOpcaoFiltro.equals(t.getGrau_ensino());
                    } else if ("Professor Responsável".equals(tipoFiltro)) {
                        String idProf = mapProfessorNomeId.get(finalOpcaoFiltro);
                        matchFiltroSecundario = idProf != null && idProf.equals(t.getProfessor_id());
                    }
                }
                
                return matchNome && matchFiltroSecundario;
            })
            .collect(Collectors.toList());
            
        atualizarCards(turmasFiltradas);
    }

    private void atualizarCards(List<Turma> turmas) {
        containerCards.getChildren().clear();

        if (turmas.isEmpty()) {
            Label vazio = new Label("Nenhuma turma encontrada.");
            vazio.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            containerCards.getChildren().add(vazio);
            return;
        }

        for (Turma turma : turmas) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-turma-edit.fxml"));
                Node card = loader.load();

                CardTurmaEditController controller = loader.getController();
                controller.configurarDados(turma);

                containerCards.getChildren().add(card);
            } catch (IOException e) {
                System.err.println("Erro ao carregar card de turma: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleInicioButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
    }

    @FXML
    private void handleTurmasButtonAction(ActionEvent event) {
        // Reseta filtros
        buscarTurma.clear();
        filterTipo.setValue("Sem filtro");
        carregarDadosIniciais(); // Recarrega do banco
        atualizarCards(todasTurmas);
    }

    @FXML
    private void handleProfessoresButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("view-profs-coord.fxml");
    }

    @FXML
    private void handleAlunosButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("view-alunos-coord.fxml");
    }

    @FXML
    private void handleSairButtonAction(ActionEvent event) {
        AuthService.logout();
        GerenciadorTelas.trocarTela("tela-de-login.fxml");
    }
}
