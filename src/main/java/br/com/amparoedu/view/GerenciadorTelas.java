package br.com.amparoedu.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GerenciadorTelas {
    private static Stage stage;

    // Configura o Stage principal
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
        stage.setMaximized(false);
    }

    public static FXMLLoader getLoader(String fxmlFile) {
        return new FXMLLoader(GerenciadorTelas.class.getResource("/view/screens/" + fxmlFile));
    }

    // Define a raiz da cena atual
    public static void setRaiz(Parent root) {
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.centerOnScreen();
        stage.show();
    }
    // Troca para uma nova tela
    public static void trocarTela(String fxmlFile) {
        try {
            String path = "/view/screens/" + fxmlFile;
            Parent root = FXMLLoader.load(GerenciadorTelas.class.getResource(path));
            
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
            
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + fxmlFile);
            e.printStackTrace();
        }
    }
}