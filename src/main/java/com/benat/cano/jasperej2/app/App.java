package com.benat.cano.jasperej2.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Clase principal de la aplicación JavaFX.
 */
public class App extends Application {
    /**
     * Método de inicio que configura y muestra la ventana principal de la aplicación.
     *
     * @param stage El escenario principal de la aplicación proporcionado por JavaFX.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/benat/cano/jasperej2/fxml/agenda.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        try {
            Image img = new Image(getClass().getResource("/com/benat/cano/jasperej2/img/agenda.png").toString());
            stage.getIcons().add(img);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
        stage.setTitle("AGENDA");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            // Confirmar si es necesario realizar acciones antes de cerrar
            System.out.println("Cerrando la aplicación...");
            Platform.exit(); // Termina la ejecución de JavaFX
            System.exit(0);  // Asegura que el proceso se detenga completamente
        });
        stage.show();
    }
    /**
     * Método principal de la aplicación. Llama a {@link javafx.application.Application#launch()} para iniciar JavaFX.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch();
    }
}
