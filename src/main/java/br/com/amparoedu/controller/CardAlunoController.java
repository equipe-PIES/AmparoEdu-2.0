package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.Responsavel;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.repository.EnderecoRepository;
import br.com.amparoedu.backend.repository.PDIRepository;
import br.com.amparoedu.backend.repository.ResponsavelRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CardAlunoController {

	@FXML private Label nomeLabel;
	@FXML private Label idadeLabel;
	@FXML private Label cidLabel;
	@FXML private Label grauEscolaridadeLabel;
	@FXML private Button btnInfo;
	@FXML private Button btnVerProgresso;

	private Educando educando;
	private ResponsavelRepository responsavelRepo;
	private EnderecoRepository enderecoRepo;
	private PDIRepository pdiRepo;

    public CardAlunoController() {
        this.responsavelRepo = new ResponsavelRepository(); // Inicialize o repositório
        this.enderecoRepo = new EnderecoRepository(); // Inicialize o repositório
        this.pdiRepo = new PDIRepository(); // Inicialize o repositório
    }

    // Preenche o card com os dados do educando
	public void setEducando(Educando educando) {
		this.educando = educando;
		if (educando == null) {
			return;
		}

		nomeLabel.setText(nullSafe(educando.getNome()));
		idadeLabel.setText("Idade: " + calcularIdade(educando.getData_nascimento()));
		cidLabel.setText("CID: " + nullSafe(educando.getCid()));
		grauEscolaridadeLabel.setText("Grau de escolaridade: " + nullSafe(educando.getGrau_ensino()));
	}

    // Calcula a idade a partir da data de nascimento
	private String calcularIdade(String dataNascimento) {
		if (dataNascimento == null || dataNascimento.isBlank()) return "-";
		try {
			LocalDate nascimento = LocalDate.parse(dataNascimento, DateTimeFormatter.ISO_DATE);
			int anos = Period.between(nascimento, LocalDate.now()).getYears();
			return anos + " anos";
		} catch (DateTimeParseException e) {
			return "-";
		}
	}

    // Retorna uma string segura para valores nulos ou em branco
	private String nullSafe(String value) {
		return (value == null || value.isBlank()) ? "-" : value;
	}

	@FXML
	private void btnInfoClick() {
		try {
			// Buscar dados adicionais do educando
			Responsavel responsavel = responsavelRepo.buscarPorEducando(educando.getId());
			Endereco endereco = enderecoRepo.buscarPorId(educando.getEndereco_id());
			PDI pdi = pdiRepo.buscarPorEducando(educando.getId()).stream().findFirst().orElse(null);

            // Carregar a tela de informações do aluno
            FXMLLoader loader = GerenciadorTelas.getLoader("infos-aluno.fxml");
            Parent root = loader.load();

            // Configurar os dados no controlador da tela de informações
            InfoAlunoController controller = loader.getController();
            controller.configurarDados(educando, responsavel, endereco, pdi);


			// Exibir como popup modal
			Stage popup = new Stage();
			popup.initOwner(btnInfo.getScene().getWindow());
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Informações do aluno");
			popup.setScene(new Scene(root));
			popup.setResizable(false);
			popup.showAndWait();
            
        } catch (IOException e) {
            System.err.println("Erro ao abrir detalhes do aluno: " + e.getMessage());
        }
	}

	@FXML
	private void btnVerProgressoClick() {
		// fluxo para abrir progresso/relatórios do educando
	}
}
