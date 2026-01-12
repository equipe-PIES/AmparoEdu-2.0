package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Professor;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.model.Usuario;
import br.com.amparoedu.backend.repository.EducandoRepository;
import br.com.amparoedu.backend.repository.ProfessorRepository;
import br.com.amparoedu.backend.service.AuthService;
import br.com.amparoedu.backend.service.TurmaService;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class CadastroTurmaController {

    @FXML private Label nomeUsuario, cargoUsuario, erroMensagem;
    @FXML private TextField nomeTurmaField, buscarAlunoNome;
    @FXML private ChoiceBox<String> grauTurma, idadeAlunos, profRespon, turnoTurma, filtroEscolaridade;
    @FXML private ListView<Educando> SugestoesAlunosList, ListAlunosTurma;
    @FXML private Button btnCadastroTurma, btnCancelCadastro, btnAdicionarAluno;

    private final TurmaService turmaService = new TurmaService();
    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();
    
    // Mapa para vincular o nome do professor ao seu ID
    private final Map<String, String> professorMap = new HashMap<>();
    
    // Listas para manipulação dos alunos
    private List<Educando> todosAlunos;
    private final ObservableList<Educando> alunosSelecionados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        configurarBotoes();
        carregarAlunos();
        configurarBuscaAlunos();
        configurarListas();
        
        ListAlunosTurma.setItems(alunosSelecionados);
        filtrarAlunos("");
    }
    
    private void configuringTopo() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nomeUsuario.setText(logado.getEmail());
            cargoUsuario.setText(logado.getTipo());
        }
    }
    
    private void configurarTopo() {
        configuringTopo();
    }

    private void configurarListas() {
        SugestoesAlunosList.setCellFactory(new Callback<ListView<Educando>, ListCell<Educando>>() {
            @Override
            public ListCell<Educando> call(ListView<Educando> param) {
                return new ListCell<Educando>() {
                    @Override
                    protected void updateItem(Educando item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };
            }
        });

        // Configura aluno da lista de alunos selecionados com botão de remover
        ListAlunosTurma.setCellFactory(new Callback<ListView<Educando>, ListCell<Educando>>() {
            @Override
            public ListCell<Educando> call(ListView<Educando> param) {
                return new ListCell<Educando>() {
                    private final Button btnRemover = new Button("X");
                    private final HBox hbox = new HBox();
                    private final Label label = new Label();
                    private final Region region = new Region();

                    {
                        hbox.setSpacing(10);
                        hbox.getChildren().addAll(label, region, btnRemover);
                        HBox.setHgrow(region, Priority.ALWAYS);
                        
                        btnRemover.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red; -fx-font-weight: bold; -fx-cursor: hand;");
                        btnRemover.setOnAction(event -> {
                            Educando educando = getItem();
                            if (educando != null) {
                                alunosSelecionados.remove(educando);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Educando item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            label.setText(item.getNome());
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void popularChoiceBoxes() {
        grauTurma.setItems(FXCollections.observableArrayList(
            "Educação Infantil", "Ensino Fundamental I", "Ensino Fundamental II", "Ensino Médio", "EJA", "Outro"
        ));
        
        idadeAlunos.setItems(FXCollections.observableArrayList(
            "0-3 anos", "4-5 anos", "6-10 anos", "11-14 anos", "15-17 anos", "18+ anos"
        ));
        
        turnoTurma.setItems(FXCollections.observableArrayList(
            "Manhã", "Tarde"
        ));
        
        filtroEscolaridade.setItems(FXCollections.observableArrayList("Sem filtro"));
        filtroEscolaridade.getItems().addAll(grauTurma.getItems());

        carregarProfessores();
    }

    private void carregarProfessores() {
        List<Professor> professores = professorRepo.listarTodos();
        if (professores != null) {
            for (Professor prof : professores) {
                professorMap.put(prof.getNome(), prof.getId());
                profRespon.getItems().add(prof.getNome());
            }
        }
    }
    
    private void carregarAlunos() {
        todosAlunos = educandoRepo.listarTodos();
    }

    private void configurarBuscaAlunos() {
        // Listener para o campo de busca
        buscarAlunoNome.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarAlunos(newValue);
        });
        
        // Listener para o filtro de escolaridade
        filtroEscolaridade.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filtrarAlunos(buscarAlunoNome.getText());
        });
    }
    
    private void filtrarAlunos(String textoBusca) {
        if (todosAlunos == null) return;
        
        String filtroGrau = filtroEscolaridade.getValue();
        
        List<Educando> resultados = todosAlunos.stream()
            .filter(a -> textoBusca == null || textoBusca.isEmpty() || a.getNome().toLowerCase().contains(textoBusca.toLowerCase()))
            .filter(a -> filtroGrau == null || filtroGrau.isEmpty() || "Sem filtro".equals(filtroGrau) || (a.getGrau_ensino() != null && a.getGrau_ensino().equals(filtroGrau)))
            .collect(Collectors.toList());
            
        SugestoesAlunosList.setItems(FXCollections.observableArrayList(resultados));
    }

    private void configurarBotoes() {
        btnCadastroTurma.setOnAction(e -> cadastrarTurma());
        btnCancelCadastro.setOnAction(e -> btnInicioClick());
        
        btnAdicionarAluno.setOnAction(e -> {
            Educando alunoSelecionado = SugestoesAlunosList.getSelectionModel().getSelectedItem();
            if (alunoSelecionado != null && !alunosSelecionados.contains(alunoSelecionado)) {
                alunosSelecionados.add(alunoSelecionado);
            }
        });
    }

    private void cadastrarTurma() {
        erroMensagem.setText("");
        
        if (!validarCampos()) {
            return;
        }

        try {
            Turma turma = new Turma();
            turma.setNome(nomeTurmaField.getText());
            turma.setGrau_ensino(grauTurma.getValue());
            turma.setFaixa_etaria(idadeAlunos.getValue());
            turma.setTurno(turnoTurma.getValue());
            
            String nomeProfessor = profRespon.getValue();
            String idProfessor = professorMap.get(nomeProfessor);
            turma.setProfessor_id(idProfessor);

            boolean sucesso = turmaService.cadastrarNovaTurma(turma);

            if (sucesso) {
                // Salvar os alunos vinculados à turma
                salvarAlunosTurma(turma.getId());
                
                exibirAlertaSucesso();
                limparCampos();
            } else {
                erroMensagem.setText("Erro ao cadastrar turma. Tente novamente.");
            }
        } catch (Exception e) {
            erroMensagem.setText("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void salvarAlunosTurma(String turmaId) {
        for (Educando aluno : alunosSelecionados) {
            turmaService.atribuirAlunoATurma(turmaId, aluno.getId());
        }
    }

    private boolean validarCampos() {
        if (nomeTurmaField.getText() == null || nomeTurmaField.getText().trim().isEmpty()) {
            erroMensagem.setText("O nome da turma é obrigatório.");
            return false;
        }
        if (grauTurma.getValue() == null) {
            erroMensagem.setText("Selecione o grau de ensino.");
            return false;
        }
        if (profRespon.getValue() == null) {
            erroMensagem.setText("Selecione um professor responsável.");
            return false;
        }
        if (turnoTurma.getValue() == null) {
            erroMensagem.setText("Selecione o turno.");
            return false;
        }
        return true;
    }

    private void exibirAlertaSucesso() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Turma cadastrada com sucesso!");
        alert.showAndWait();
    }

    private void limparCampos() {
        nomeTurmaField.clear();
        grauTurma.setValue(null);
        idadeAlunos.setValue(null);
        profRespon.setValue(null);
        turnoTurma.setValue(null);
        filtroEscolaridade.setValue(null);
        buscarAlunoNome.clear();
        alunosSelecionados.clear();
        SugestoesAlunosList.getItems().clear();
        erroMensagem.setText("");
    }

    // Navegação
    @FXML private void btnSairClick() { AuthService.logout(); GerenciadorTelas.getInstance().trocarTela("tela-de-login.fxml"); }
    @FXML private void btnInicioClick() { GerenciadorTelas.getInstance().trocarTela("tela-inicio-coord.fxml"); }
    @FXML private void btnTurmasClick() { GerenciadorTelas.getInstance().trocarTela("view-turmas-coord.fxml"); }
    @FXML private void btnProfessoresClick() { GerenciadorTelas.getInstance().trocarTela("view-profs-coord.fxml");}
    @FXML private void btnAlunosClick() { GerenciadorTelas.getInstance().trocarTela("view-alunos-coord.fxml"); }
    
    // Placeholder para compatibilidade com FXML se houver chamada direta
    @FXML private void btnAdicionarAlunoClick() { 
        btnAdicionarAluno.fire();
    }
}
