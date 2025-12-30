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
        //PopularDatabase.adicionarAnamnese("bbf20a5d-9eb7-45fa-9810-e6f2319aa35f", "2");
        //PopularDatabase.adicionarPDI("bbf20a5d-9eb7-45fa-9810-e6f2319aa35f", "2");
        //PopularDatabase.adicionarPAEE("bbf20a5d-9eb7-45fa-9810-e6f2319aa35f", "2");
        
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