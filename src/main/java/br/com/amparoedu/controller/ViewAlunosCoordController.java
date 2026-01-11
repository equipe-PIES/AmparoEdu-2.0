package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.TurmaRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ViewAlunosCoordController {

    @FXML private Label nameUser;
    @FXML private Label cargoUser;
    @FXML private Label indicadorDeTela;
    @FXML private FlowPane containerCards;
    @FXML private Button buscarAlunoButton;
    @FXML private Button sairButton;
    @FXML private Button inicioButton;
    @FXML private Button turmasButton;
    @FXML private Button professoresButton;
    @FXML private Button alunosButton;
    @FXML private ChoiceBox<String> filterTipo;
    @FXML private ChoiceBox<String> filterOpcoes;
    @FXML private TextField buscarAluno;

    private EducandoRepository educandoRepo = new EducandoRepository();
    private TurmaRepository turmaRepo = new TurmaRepository();
    private List<Educando> todosEducandos = new ArrayList<>();
    private List<Turma> todasTurmas = new ArrayList<>();
    private Map<String, Turma> cacheTurmasPorId = new HashMap<>();

    @FXML
    public void initialize() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nameUser.setText(logado.getEmail());
            cargoUser.setText(logado.getTipo());
        }

        if (indicadorDeTela != null) {
            indicadorDeTela.setText("Alunos");
        }

        configurarFiltros();
        carregarDados();
        aplicarFiltros();
    }

    private void configurarFiltros() {
        if (filterTipo != null) {
            filterTipo.getItems().setAll("Sem filtro", "Grau de escolaridade", "Faixa etária");
            filterTipo.getSelectionModel().selectFirst();
            filterTipo.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> atualizarOpcoesFiltro());
        }
        if (filterOpcoes != null) {
            filterOpcoes.getItems().setAll("Sem opção");
            filterOpcoes.getSelectionModel().selectFirst();
            filterOpcoes.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        }
        if (buscarAluno != null) {
            buscarAluno.textProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        }
    }

    private void carregarDados() {
        todosEducandos = educandoRepo.listarTodos();
        todasTurmas = turmaRepo.listarTodas();
        
        cacheTurmasPorId.clear();
        for (Turma t : todasTurmas) {
            cacheTurmasPorId.put(t.getId(), t);
        }
        
        atualizarOpcoesFiltro();
    }

    private void atualizarOpcoesFiltro() {
        String tipo = filterTipo != null ? filterTipo.getSelectionModel().getSelectedItem() : "Sem filtro";
        if (filterOpcoes == null) return;
        List<String> opcoes = new ArrayList<>();
        opcoes.add("Sem opção");
        if ("Grau de escolaridade".equals(tipo)) {
            opcoes.addAll(todosEducandos.stream()
                    .map(Educando::getGrau_ensino)
                    .filter(s -> s != null && !s.isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList()));
        } else if ("Faixa etária".equals(tipo)) {
            // Opções fixas conforme solicitado
            opcoes.add("0-3 anos");
            opcoes.add("4-5 anos");
            opcoes.add("6-10 anos");
            opcoes.add("11-14 anos");
            opcoes.add("15-18 anos");
            opcoes.add("Outro");
        }
        filterOpcoes.getItems().setAll(opcoes);
        filterOpcoes.getSelectionModel().selectFirst();
        aplicarFiltros();
    }

    private void aplicarFiltros() {
        String texto = buscarAluno != null ? buscarAluno.getText() : "";
        String tipo = filterTipo != null ? filterTipo.getSelectionModel().getSelectedItem() : "Sem filtro";
        String opcao = filterOpcoes != null ? filterOpcoes.getSelectionModel().getSelectedItem() : "Sem opção";

        List<Educando> filtrados = todosEducandos.stream()
                .filter(e -> {
                    String nome = e.getNome() != null ? e.getNome() : "";
                    return nome.toLowerCase().contains(texto != null ? texto.toLowerCase() : "");
                })
                .filter(e -> {
                    if ("Grau de escolaridade".equals(tipo) && opcao != null && !"Sem opção".equals(opcao)) {
                        return opcao.equals(e.getGrau_ensino());
                    } else if ("Faixa etária".equals(tipo) && opcao != null && !"Sem opção".equals(opcao)) {
                        Integer idade = calcularIdade(e);
                        return checkFaixaEtaria(opcao, idade);
                    }
                    return true;
                })
                .collect(Collectors.toList());
        renderizarCards(filtrados);
    }

    private Integer calcularIdade(Educando e) {
        if (e.getData_nascimento() == null || e.getData_nascimento().isEmpty()) {
            return null;
        }
        try {
            LocalDate birthDate = LocalDate.parse(e.getData_nascimento());
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (DateTimeParseException ex) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate birthDate = LocalDate.parse(e.getData_nascimento(), formatter);
                return Period.between(birthDate, LocalDate.now()).getYears();
            } catch (DateTimeParseException ex2) {
                return null;
            }
        }
    }

    // Verifica se a idade do aluno está dentro do intervalo do filtro
    private boolean checkFaixaEtaria(String filtro, Integer idadeAluno) {
        if (idadeAluno == null) return false; // Se não tem idade calculada, não entra nos filtros numéricos
        
        if ("Outro".equals(filtro)) {
             // "Outro" captura quem está fora dos intervalos definidos (0-18)
             return idadeAluno > 18;
        }

        // Extrair range do filtro (ex: "4-5 anos" -> 4, 5)
        List<Integer> filterNums = extractNumbers(filtro);
        if (filterNums.isEmpty()) return false; 
        
        int fMin = filterNums.get(0);
        int fMax = filterNums.size() > 1 ? filterNums.get(1) : fMin;
        
        // Verifica se a idade está DENTRO do intervalo (inclusivo)
        return idadeAluno >= fMin && idadeAluno <= fMax;
    }
    
    private List<Integer> extractNumbers(String text) {
        List<Integer> numbers = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            try {
                numbers.add(Integer.parseInt(m.group()));
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return numbers;
    }

    private void renderizarCards(List<Educando> educandos) {
        containerCards.getChildren().clear();
        for (Educando educando : educandos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/screens/card-aluno-edit.fxml"));
                Node cardNode = loader.load();
                CardAlunoEditController cardController = loader.getController();
                cardController.configurarDados(educando);
                containerCards.getChildren().add(cardNode);
            } catch (IOException e) {
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
        GerenciadorTelas.trocarTela("view-turmas-coord.fxml");
    }

    @FXML
    private void handleProfessoresButtonAction(ActionEvent event) {
        GerenciadorTelas.trocarTela("view-profs-coord.fxml");
    }

    @FXML
    private void handleAlunosButtonAction(ActionEvent event) {
        // Já estamos na tela de alunos
    }

    @FXML
    private void handleSairButtonAction(ActionEvent event) {
        AuthService.logout();
        GerenciadorTelas.trocarTela("tela-de-login.fxml");
    }

    @FXML
    private void handleBuscarAlunoClick(ActionEvent event) {
        aplicarFiltros();
    }
}
