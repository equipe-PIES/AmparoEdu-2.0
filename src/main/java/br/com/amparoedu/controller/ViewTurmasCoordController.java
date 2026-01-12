package br.com.amparoedu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.repository.TurmaRepository;
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

public class ViewTurmasCoordController {

    @FXML private Label nomeUsuario;
    @FXML private Label cargoUsuario;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;
    @FXML private TextField buscarTurma;
    @FXML private ChoiceBox<String> filterTipo;
    @FXML private ChoiceBox<String> filterOpcoes;
    @FXML private Button btnSair;
    @FXML private Button btnBuscarTurma;

    private final TurmaRepository turmaRepo = new TurmaRepository();
    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private List<Turma> todasTurmas;
    
    // Map to store Professor Name -> ID for filtering
    private Map<String, String> professorNomeToId = new HashMap<>();

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
        
        if (indicadorDeTela != null) {
            indicadorDeTela.setText("Turmas Cadastradas");
        }
        
        carregarProfessoresMap();
        setupFilters();
        carregarTurmas();
        
        if (buscarTurma != null) {
            buscarTurma.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarTurmas();
            });
        }
        
        if (btnBuscarTurma != null) {
            btnBuscarTurma.setOnAction(event -> filtrarTurmas());
        }
    }
    
    private void carregarProfessoresMap() {
        List<Professor> professores = professorRepo.listarTodos();
        for (Professor p : professores) {
            professorNomeToId.put(p.getNome(), p.getId());
        }
    }

    private void setupFilters() {
        filterTipo.setItems(FXCollections.observableArrayList("Sem filtros", "Professor", "Grau de Escolaridade"));
        filterTipo.setValue("Sem filtros");
        filterOpcoes.setDisable(true); // Desabilita inicialmente
        
        filterTipo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            atualizarOpcoesFiltro(newVal);
            filtrarTurmas();
        });
        
        filterOpcoes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filtrarTurmas();
        });
    }

    private void atualizarOpcoesFiltro(String tipo) {
        filterOpcoes.getItems().clear();
        
        if ("Sem filtros".equals(tipo)) {
            filterOpcoes.setDisable(true);
            return;
        }
        
        filterOpcoes.setDisable(false);
        
        if ("Professor".equals(tipo)) {
            filterOpcoes.getItems().addAll(professorNomeToId.keySet().stream().sorted().collect(Collectors.toList()));
        } else if ("Grau de Escolaridade".equals(tipo)) {
            filterOpcoes.getItems().addAll(
                "Educação Infantil", "Ensino Fundamental I", "Ensino Fundamental II", "Ensino Médio", "EJA", "Outro"
            );
        }
        
        if (!filterOpcoes.getItems().isEmpty()) {
            filterOpcoes.getSelectionModel().selectFirst();
        }
    }

    private void carregarTurmas() {
        todasTurmas = turmaRepo.listarTodas();
        exibirTurmas(todasTurmas);
    }

    private void exibirTurmas(List<Turma> turmas) {
        containerCards.getChildren().clear();
        
        for (Turma turma : turmas) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-turma-edit.fxml"));
                Node card = loader.load();

                CardTurmaEditController controller = loader.getController();
                controller.setTurma(turma);

                containerCards.getChildren().add(card);
            } catch (IOException e) {
                System.err.println("Erro ao carregar card da turma: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void filtrarTurmas() {
        if (todasTurmas == null) return;
        
        String termoBusca = (buscarTurma != null) ? buscarTurma.getText() : "";
        String tipoFiltro = filterTipo.getValue();
        String opcaoFiltro = filterOpcoes.getValue();
        
        List<Turma> filtrados = todasTurmas.stream()
            .filter(t -> {
                // Filtro de nome
                if (termoBusca != null && !termoBusca.isEmpty()) {
                    if (!t.getNome().toLowerCase().contains(termoBusca.toLowerCase())) {
                        return false;
                    }
                }
                
                // Filtros específicos
                if ("Professor".equals(tipoFiltro) && opcaoFiltro != null) {
                     String profId = professorNomeToId.get(opcaoFiltro);
                     return profId != null && profId.equals(t.getProfessor_id());
                } else if ("Grau de Escolaridade".equals(tipoFiltro) && opcaoFiltro != null) {
                    return t.getGrau_ensino().equals(opcaoFiltro);
                }
                
                return true;
            })
            .collect(Collectors.toList());
            
        exibirTurmas(filtrados);
    }

    @FXML
    private void btnInicioClick() {
        GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml");
    }

    @FXML
    private void btnTurmasClick() {
        // Já estamos na tela
    }

    @FXML
    private void btnProfessoresClick() {
        GerenciadorTelas.getInstance().trocarTela("view-profs-coord.fxml");
    }

    @FXML
    private void btnAlunosClick() {
        GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml");
    }

    @FXML
    private void btnSairClick() {
        AuthService.logout();
        GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml");
    }
}
