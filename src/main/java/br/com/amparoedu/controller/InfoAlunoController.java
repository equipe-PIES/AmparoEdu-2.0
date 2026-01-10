package br.com.amparoedu.controller;

import br.com.amparoedu.backend.model.Educando;
import br.com.amparoedu.backend.model.Endereco;
import br.com.amparoedu.backend.model.PDI;
import br.com.amparoedu.backend.model.Responsavel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfoAlunoController {
    @FXML private Label nomeLabel;
    @FXML private Label dataNascimentoLabel;
    @FXML private Label cpfLabel;
    @FXML private Label generoLabel;
    @FXML private Label cidLabel;
    @FXML private Label nisLabel;
    @FXML private Label grauEscolaridadeLabel;
    @FXML private Label escolaLabel;
    @FXML private Label observacoesLabel;
    @FXML private Label nomeResponsavelLabel;
    @FXML private Label parentescoLabel;
    @FXML private Label cpfResponsavelLabel;
    @FXML private Label contatoLabel;
    @FXML private Label enderecoLabel;
    @FXML private Label horarioAtendimentoLabel;
    @FXML private Label periodoAEELabel;

    private Educando educando;
    private Responsavel responsavel;
    private Endereco endereco;
    private PDI pdi;

    public void configurarDados(Educando educando, Responsavel responsavel, Endereco endereco, PDI pdi) {
        this.educando = educando;
        this.responsavel = responsavel;
        this.endereco = endereco;
        this.pdi = pdi;

        preencherEducando();
        preencherResponsavel();
        preencherEndereco();
        preencherPdi();
    }

    private void preencherEducando() {
        if (educando == null) return;
        nomeLabel.setText(safe(educando.getNome()));
        dataNascimentoLabel.setText(safe(educando.getData_nascimento()));
        cpfLabel.setText(safe(educando.getCpf()));
        generoLabel.setText(safe(educando.getGenero()));
        cidLabel.setText(safe(educando.getCid()));
        nisLabel.setText(safe(educando.getNis()));
        grauEscolaridadeLabel.setText(safe(educando.getGrau_ensino()));
        escolaLabel.setText(safe(educando.getEscola()));
        observacoesLabel.setText(safe(educando.getObservacoes()));
    }

    private void preencherResponsavel() {
        if (responsavel == null) return;
        nomeResponsavelLabel.setText(safe(responsavel.getNome()));
        parentescoLabel.setText(safe(responsavel.getParentesco()));
        cpfResponsavelLabel.setText(safe(responsavel.getCpf()));
        contatoLabel.setText(safe(responsavel.getTelefone()));
    }

    private void preencherEndereco() {
        if (endereco == null) return;
        String complemento = endereco.getComplemento();
        String texto = String.format("%s, %s - %s, %s/%s%s",
                safe(endereco.getLogradouro()),
                safe(endereco.getNumero()),
                safe(endereco.getBairro()),
                safe(endereco.getCidade()),
                safe(endereco.getUf()),
                (complemento == null || complemento.isBlank()) ? "" : " (" + complemento + ")");
        enderecoLabel.setText(texto);
    }

    private void preencherPdi() {
        horarioAtendimentoLabel.setText(pdi != null ? safe(pdi.getHorarioAtendimento()) : "-");
        periodoAEELabel.setText(pdi != null ? safe(pdi.getPeriodoAee()) : "-");
    }

    private String safe(String value) {
        return (value == null || value.isBlank()) ? "-" : value;
    }

    @FXML
    private void btnFecharClick() {
        Stage stage = (Stage) nomeLabel.getScene().getWindow();
        stage.close();
    }
}

