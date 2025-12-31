package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
		// navegação ou modal de detalhes do educando
	}

	@FXML
	private void btnVerProgressoClick() {
		// fluxo para abrir progresso/relatórios do educando
	}
}
