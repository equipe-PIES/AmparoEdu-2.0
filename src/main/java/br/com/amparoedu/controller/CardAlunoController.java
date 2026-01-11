package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.Responsavel;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.Turma;
import br.com.amparoedu.backend.repository.EnderecoRepository;
import br.com.amparoedu.backend.repository.PDIRepository;
import br.com.amparoedu.backend.repository.ResponsavelRepository;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CardAlunoController {

	@FXML private Label nomeLabel;
	@FXML private Label idadeLabel;
	@FXML private Label cidLabel;
	@FXML private Label grauEscolaridadeLabel;
	@FXML private Button btnInfo;
	@FXML private Button btnVerProgresso;

	private Educando educando;
    private Turma turma;
    private ResponsavelRepository responsavelRepo;
    private EnderecoRepository enderecoRepo;
    private PDIRepository pdiRepo;

    public CardAlunoController() {
        this.responsavelRepo = new ResponsavelRepository();
        this.enderecoRepo = new EnderecoRepository();
        this.pdiRepo = new PDIRepository();
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

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

	private Responsavel getResponsavel() {
		if (educando == null) return null;
		return responsavelRepo.buscarPorEducando(educando.getId());
	}
	private Endereco getEndereco() {
		if (educando == null) return null;
		return enderecoRepo.buscarPorEducando(educando.getId());
	}
	private PDI getPdi() {
		if (educando == null) return null;
		List<PDI> pdis = pdiRepo.buscarPorEducando(educando.getId());
		if (pdis == null || pdis.isEmpty()) return null;
		return pdis.get(0);
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
			FXMLLoader loader = GerenciadorTelas.getLoader("infos-aluno.fxml");
			Parent root = loader.load();
			InfoAlunoController controller = loader.getController();
			controller.configurarDados(this.educando, getResponsavel(), getEndereco(), getPdi());
			GerenciadorTelas.getInstance().abrirPopup(root, "Informações do Aluno");
		} catch (IOException e) {
			System.err.println("Erro ao carregar a tela de informações do aluno.");
			e.printStackTrace();
		}
	}

	@FXML
	private void btnVerProgressoClick() {
		try {
			FXMLLoader loader = GerenciadorTelas.getLoader("progresso-atendimento.fxml");
			Parent root = loader.load();
			ProgressoAtendimentoController controller = loader.getController();
			controller.setEducando(this.educando);
			GerenciadorTelas.getInstance().abrirPopup(root, "Progresso do Atendimento");
			controller.setTurma(this.turma);
		} catch (IOException e) {
			System.err.println("Erro ao carregar a tela de progresso do atendimento.");
			e.printStackTrace();
		}
	}
}
