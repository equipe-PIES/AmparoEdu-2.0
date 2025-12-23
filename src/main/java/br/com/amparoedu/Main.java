package br.com.amparoedu;

import br.com.amparoedu.backend.repository.DatabaseConfig;
import br.com.amparoedu.backend.repository.PopularDatabase;
import br.com.amparoedu.view.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Prepara o Banco e os dados iniciais
        DatabaseConfig.inicializarBanco();
        PopularDatabase.popularDadosIniciais();

        // 2. Configura o gerenciador de telas
        GerenciadorTelas.setStage(primaryStage);
        primaryStage.setTitle("AmparoEdu - Sistema de Gestão Escolar");

        // 3. Abre a tela de login
        GerenciadorTelas.trocarTela("tela-de-login.fxml");

        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}