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
        // Dados de teste para os documentos. Insira o id do educando que será relacionado e o do professor
        //PopularDatabase.adicionarAnamnese("8a26add8-f9c1-40de-b4c5-ac2c63fc68a0", "2");
        //PopularDatabase.adicionarPDI("8a26add8-f9c1-40de-b4c5-ac2c63fc68a0", "2");
        //PopularDatabase.adicionarPAEE("8a26add8-f9c1-40de-b4c5-ac2c63fc68a0", "2");
        //PopularDatabase.adicionarDI("8a26add8-f9c1-40de-b4c5-ac2c63fc68a0", "2");
        //PopularDatabase.adicionarRI("8a26add8-f9c1-40de-b4c5-ac2c63fc68a0", "2");
        
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