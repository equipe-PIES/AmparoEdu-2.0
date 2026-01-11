package br.com.amparoedu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class CadastroTurmaController {

    @FXML private TextField nomeTurmaField;
    @FXML private ChoiceBox<String> grauTurma;
    @FXML private ChoiceBox<String> idadeAlunos;
    @FXML private ChoiceBox<String> profRespon;
    @FXML private ChoiceBox<String> turnoTurma;
    @FXML private Label erroMensagem;
    
    // Novos componentes para gestão de alunos
    @FXML private ChoiceBox<String> filtroEscolaridade;
    @FXML private TextField buscarAlunoNome;
    @FXML private ListView<Educando> SugestoesAlunosList;
    @FXML private Button adicionarAlunoButton;
    @FXML private Button cadastroTurmaButton;
    @FXML private ListView<Educando> ListAlunosTurma;
    
    @FXML private Label nameUser;
    @FXML private Label cargoUser;

    private final TurmaService turmaService = new TurmaService();
    private final ProfessorRepository professorRepo = new ProfessorRepository();
    private final EducandoRepository educandoRepo = new EducandoRepository();
    
    private final Map<String, String> professorMap = new HashMap<>(); // Nome -> ID
    
    // Listas para manipulação dos alunos
    private ObservableList<Educando> todosAlunos;
    private ObservableList<Educando> alunosSugestao;
    private ObservableList<Educando> alunosTurma;
    private List<Educando> alunosOriginaisTurma = new ArrayList<>(); // Para rastrear remoções na edição

    private static Turma turmaEdicao;

    public static void setTurmaEdicao(Turma turma) {
        turmaEdicao = turma;
    }

    @FXML
    public void initialize() {
        configurarTopo();
        popularChoiceBoxes();
        configurarListasAlunos();
        
        if (turmaEdicao != null) {
            preencherCamposEdicao();
        } else {
            carregarTodosAlunos();
            if (cadastroTurmaButton != null) cadastroTurmaButton.setText("Cadastrar Turma");
        }
    }
    
    private void configurarListasAlunos() {
        alunosSugestao = FXCollections.observableArrayList();
        alunosTurma = FXCollections.observableArrayList();
        
        SugestoesAlunosList.setItems(alunosSugestao);
        ListAlunosTurma.setItems(alunosTurma);
        
        // Configura como os alunos aparecem na lista de sugestões (padrão)
        setCellFactory(SugestoesAlunosList);
        
        // Configura lista de alunos da turma com botão de remover
        ListAlunosTurma.setCellFactory(param -> new ListCell<Educando>() {
            private final Button btnRemover = new Button("X");
            private final Label lblNome = new Label();
            private final HBox pane = new HBox();
            private final Region spacer = new Region();

            {
                // Configuração do layout
                pane.setSpacing(10);
                pane.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(spacer, Priority.ALWAYS);
                
                btnRemover.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-min-width: 30px;");
                btnRemover.setOnAction(event -> {
                    Educando item = getItem();
                    if (item != null) {
                        removerAlunoDaTurma(item);
                    }
                });
                
                pane.getChildren().addAll(lblNome, spacer, btnRemover);
            }

            @Override
            protected void updateItem(Educando item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    lblNome.setText(item.getNome() + " (" + item.getGrau_ensino() + ")");
                    setGraphic(pane);
                    setText(null);
                }
            }
        });
        
        // ContextMenu para remover aluno da turma (mantendo como opção secundária)
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removerItem = new MenuItem("Remover da turma");
        removerItem.setOnAction(e -> removerAlunoDaTurma());
        contextMenu.getItems().add(removerItem);
        ListAlunosTurma.setContextMenu(contextMenu);
        
        // Listeners para filtros
        filtroEscolaridade.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> filtrarSugestoes());
        buscarAlunoNome.textProperty().addListener((obs, oldVal, newVal) -> filtrarSugestoes());
        
        // Ação do botão adicionar
        adicionarAlunoButton.setOnAction(e -> adicionarAlunoSelecionado());
    }
    
    private void setCellFactory(ListView<Educando> listView) {
        listView.setCellFactory(param -> new ListCell<Educando>() {
            @Override
            protected void updateItem(Educando item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (" + item.getGrau_ensino() + ")");
                }
            }
        });
    }
    
    private void carregarTodosAlunos() {
        List<Educando> lista = educandoRepo.listarTodos();
        todosAlunos = FXCollections.observableArrayList(lista);
        filtrarSugestoes();
    }

    private void filtrarSugestoes() {
        if (todosAlunos == null) return;
        
        String filtroGrau = filtroEscolaridade.getValue();
        String filtroNome = buscarAlunoNome.getText().toLowerCase();
        
        List<Educando> filtrados = todosAlunos.stream()
            .filter(aluno -> {
                // Remove alunos que já estão na turma
                boolean jaNaTurma = alunosTurma.stream().anyMatch(a -> a.getId().equals(aluno.getId()));
                if (jaNaTurma) return false;
                
                // Filtro de escolaridade
                boolean matchGrau = filtroGrau == null || filtroGrau.isEmpty() || 
                                    (aluno.getGrau_ensino() != null && aluno.getGrau_ensino().equals(filtroGrau));
                
                // Filtro de nome
                boolean matchNome = filtroNome.isEmpty() || 
                                    (aluno.getNome() != null && aluno.getNome().toLowerCase().contains(filtroNome));
                
                return matchGrau && matchNome;
            })
            .collect(Collectors.toList());
            
        alunosSugestao.setAll(filtrados);
    }
    
    private void adicionarAlunoSelecionado() {
        Educando selecionado = SugestoesAlunosList.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            alunosTurma.add(selecionado);
            filtrarSugestoes(); // Atualiza sugestões removendo o adicionado
        }
    }
    
    private void removerAlunoDaTurma(Educando aluno) {
        if (aluno != null) {
            alunosTurma.remove(aluno);
            filtrarSugestoes(); // Devolve para sugestões
        }
    }

    private void removerAlunoDaTurma() {
        removerAlunoDaTurma(ListAlunosTurma.getSelectionModel().getSelectedItem());
    }
    
    private void preencherCamposEdicao() {
        try {
            nomeTurmaField.setText(turmaEdicao.getNome());
            grauTurma.setValue(turmaEdicao.getGrau_ensino());
            idadeAlunos.setValue(turmaEdicao.getFaixa_etaria());
            turnoTurma.setValue(turmaEdicao.getTurno());
            
            // Selecionar professor
            if (turmaEdicao.getProfessor_id() != null) {
                for (Map.Entry<String, String> entry : professorMap.entrySet()) {
                    if (entry.getValue().equals(turmaEdicao.getProfessor_id())) {
                        profRespon.setValue(entry.getKey());
                        break;
                    }
                }
            }
            
            // Carregar alunos da turma e outros
            List<Educando> todos = educandoRepo.listarTodos();
            todosAlunos = FXCollections.observableArrayList(todos);
            
            // Separa os que são da turma
            List<Educando> daTurma = todos.stream()
                .filter(a -> a.getTurma_id() != null && turmaEdicao.getId() != null && 
                             turmaEdicao.getId().trim().equals(a.getTurma_id().trim()))
                .collect(Collectors.toList());
                
            System.out.println("Turma ID: " + turmaEdicao.getId());
            System.out.println("Total alunos encontrados: " + todos.size());
            System.out.println("Alunos na turma: " + daTurma.size());
            
            alunosTurma.setAll(daTurma);
            alunosOriginaisTurma.addAll(daTurma); // Salva cópia para saber quem saiu depois
            
            filtrarSugestoes();
            
            if (cadastroTurmaButton != null) cadastroTurmaButton.setText("Salvar Alterações");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao preencher campos de edição: " + e.getMessage());
        }
    }

    private void configuringTopo() {
        Usuario logado = AuthService.getUsuarioLogado();
        if (logado != null) {
            nameUser.setText(logado.getEmail());
            cargoUser.setText(logado.getTipo());
        }
    }
    
    // Alias for method name used in other controllers, ensuring consistency if needed
    private void configurarTopo() {
        configuringTopo();
    }

    private void popularChoiceBoxes() {
        grauTurma.setItems(FXCollections.observableArrayList(
            "Educação Infantil", "Ensino Fundamental I", "Ensino Fundamental II", "Ensino Médio", "Outro"
        ));
        
        idadeAlunos.setItems(FXCollections.observableArrayList(
            "0-3 anos", "4-5 anos", "6-10 anos", "11-14 anos", "15-18 anos", "Outro"
        ));
        
        turnoTurma.setItems(FXCollections.observableArrayList(
            "Manhã", "Tarde", "Integral"
        ));
        
        filtroEscolaridade.setItems(FXCollections.observableArrayList(
            "Estimulação Precoce","Educação Infantil", "Ensino Fundamental I", "Ensino Fundamental II", "Ensino Médio", "Outro"
        ));

        // Popula professores
        List<Professor> professores = professorRepo.listarTodos();
        for (Professor prof : professores) {
            String display = prof.getNome();
            professorMap.put(display, prof.getId());
            profRespon.getItems().add(display);
        }
    }

    @FXML
    private void handleCadastroTurmaAction() {
        erroMensagem.setText("");
        if (validarCampos()) {
            try {
                boolean isEdicao = (turmaEdicao != null);
                Turma turma = isEdicao ? turmaEdicao : new Turma();
                
                turma.setNome(nomeTurmaField.getText());
                turma.setGrau_ensino(grauTurma.getValue());
                turma.setFaixa_etaria(idadeAlunos.getValue());
                turma.setTurno(turnoTurma.getValue());
                
                String nomeProf = profRespon.getValue();
                if (nomeProf != null && professorMap.containsKey(nomeProf)) {
                    turma.setProfessor_id(professorMap.get(nomeProf));
                }

                turma.setQuantidade_alunos(String.valueOf(alunosTurma.size()));

                boolean sucesso;
                if (isEdicao) {
                    sucesso = turmaService.atualizarTurma(turma);
                } else {
                    sucesso = turmaService.cadastrarNovaTurma(turma);
                }

                if (sucesso) {
                    salvarRelacaoAlunos(turma);
                    exibirAlertaSucesso(isEdicao ? "Turma atualizada com sucesso!" : "Turma cadastrada com sucesso!");
                    limparCampos();
                } else {
                    erroMensagem.setText("Erro ao salvar no banco de dados.");
                }
            } catch (Exception e) {
                erroMensagem.setText("Erro ao salvar turma: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void salvarRelacaoAlunos(Turma turma) {
        String turmaId = turma.getId();
        
        // Atualiza alunos que estão na turma
        for (Educando aluno : alunosTurma) {
            aluno.setTurma_id(turmaId);
            aluno.setSincronizado(0); // Marca para sincronização
            educandoRepo.atualizar(aluno);
        }
        
        // Atualiza alunos que foram removidos (somente em edição)
        if (turmaEdicao != null) {
            for (Educando original : alunosOriginaisTurma) {
                // Se estava na lista original mas não está na atual, foi removido
                boolean aindaNaTurma = alunosTurma.stream().anyMatch(a -> a.getId().equals(original.getId()));
                
                if (!aindaNaTurma) {
                    original.setTurma_id(null);
                    original.setSincronizado(0);
                    educandoRepo.atualizar(original);
                }
            }
        }
    }

    private boolean validarCampos() {
        if (nomeTurmaField.getText().isEmpty() || 
            grauTurma.getValue() == null || 
            idadeAlunos.getValue() == null || 
            profRespon.getValue() == null || 
            turnoTurma.getValue() == null) {
            
            erroMensagem.setText("Preencha todos os campos obrigatórios.");
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
        turmaEdicao = null;
        nomeTurmaField.clear();
        grauTurma.setValue(null);
        idadeAlunos.setValue(null);
        profRespon.setValue(null);
        turnoTurma.setValue(null);
        erroMensagem.setText("");
        
        // Limpa campos de alunos
        if (alunosTurma != null) alunosTurma.clear();
        if (alunosOriginaisTurma != null) alunosOriginaisTurma.clear();
        if (buscarAlunoNome != null) buscarAlunoNome.clear();
        if (filtroEscolaridade != null) filtroEscolaridade.setValue(null);
        if (cadastroTurmaButton != null) cadastroTurmaButton.setText("Cadastrar Turma");
        carregarTodosAlunos(); // Recarrega para resetar estado
    }

    @FXML
    private void handleCancelCadastroAction() {
        limparCampos();
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
    }

    @FXML private void handleSairButtonAction() {
        AuthService.logout();
        GerenciadorTelas.trocarTela("tela-de-login.fxml");
    }

    @FXML private void handleInicioButtonAction() {
        GerenciadorTelas.trocarTela("tela-inicio-coord.fxml");
    }

    @FXML private void handleTurmasButtonAction() {
         GerenciadorTelas.trocarTela("view-turmas-coord.fxml");
    }

    @FXML private void handleProfessoresButtonAction() {
         GerenciadorTelas.trocarTela("view-profs-coord.fxml");
    }

    @FXML private void handleAlunosButtonAction() {
         GerenciadorTelas.trocarTela("view-alunos-coord.fxml");
    }
}
