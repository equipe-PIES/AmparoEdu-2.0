package br.com.amparoedu.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GerenciadorTelas {
    private static Stage stage;

    // Configura o Stage principal
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
        stage.setMaximized(false);
    }

    public static void trocarTela(String fxmlFile) {
        try {
            String path = "/view/screens/" + fxmlFile;
            var resource = GerenciadorTelas.class.getResource(path);
            if (resource == null) {
                throw new IOException("Arquivo FXML não encontrado: " + path);
            }
            Parent root = FXMLLoader.load(resource);
            
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
            
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a tela: " + fxmlFile);
            e.printStackTrace();
            // Tenta mostrar um alerta visual se possível
            try {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Erro de Navegação");
                alert.setHeaderText("Não foi possível carregar a tela " + fxmlFile);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                // Ignora erro ao mostrar alerta
            }
        }
    }
}