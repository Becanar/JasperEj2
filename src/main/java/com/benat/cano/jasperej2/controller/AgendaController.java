package com.benat.cano.jasperej2.controller;

import com.benat.cano.jasperej2.db.ConectorDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Controlador para manejar eventos y lógica de la interfaz de la agenda.
 */
public class AgendaController {

    private static final Logger LOGGER = Logger.getLogger(AgendaController.class.getName());

    @FXML
    private HBox boxBotones;

    @FXML
    private VBox boxRBotones;

    @FXML
    private Button btAceptar;

    @FXML
    private RadioButton btCalculos;

    @FXML
    private Button btCancelar;

    @FXML
    private RadioButton btPersonas;

    @FXML
    private RadioButton btSub;

    @FXML
    private ToggleGroup grupoIformes;

    @FXML
    private Label lblBotones;

    @FXML
    private Label lblInformes;
    /**
     * Acción para el botón "Aceptar".
     * Genera un informe basado en la opción seleccionada por el usuario.
     *
     * @param event El evento que dispara la acción.
     */
    @FXML
    void aceptar(ActionEvent event) {

        if (btPersonas.isSelected()) {
            informe("personas.jasper", 1);
        } else if (btCalculos.isSelected()) {
            informe("calculos.jasper", 2);
        } else if (btSub.isSelected()) {
            informe("agenda.jasper", 3);
        }

    }

    /**
     * Acción para el botón "Cancelar".
     * Cierra la aplicación JavaFX.
     *
     * @param event El evento que dispara la acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
        // Cierra la aplicación
        Platform.exit();
    }
    /**
     * Genera un informe JasperReports con parámetros personalizados.
     *
     * @param jasper Nombre del archivo .jasper que define el informe.
     * @param num    Identificador para determinar los parámetros del informe.
     */
    private void informe(String jasper, int num) {
        ConectorDB db;
        try {
            db = new ConectorDB();

            InputStream reportStream = db.getClass().getResourceAsStream("/com/benat/cano/jasperej2/jasper/" + jasper);
            if (reportStream == null) {
                LOGGER.log(Level.SEVERE, "El archivo " + jasper + " no se encontró.");
                return;
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
            Map<String, Object> parameters;
            if (num == 1) {
                parameters = new HashMap<>();
            } else {
                if (num == 2) {
                    parameters = new HashMap<>();
                    LOGGER.log(Level.INFO, "Ruta de la primera imagen: {0}", db.getClass().getResource("/com/benat/cano/jasperej2/img/agenda.png").toString());
                    LOGGER.log(Level.INFO, "Ruta de la segunda imagen: {0}", db.getClass().getResource("/com/benat/cano/jasperej2/img/persona.png").toString());
                    String imagePath1 = db.getClass().getResource("/com/benat/cano/jasperej2/img/agenda.png").toString();
                    String imagePath2 = db.getClass().getResource("/com/benat/cano/jasperej2/img/persona.png").toString();
                    parameters.put("IMAGE_PATH", imagePath1);
                    parameters.put("IMAGE_PATH_2", imagePath2);
                } else {
                     parameters = new HashMap<>();
                    LOGGER.log(Level.INFO, "Ruta de la primera jasper: {0}", db.getClass().getResource("/com/benat/cano/jasperej2/jasper/email.jasper").toString());
                    LOGGER.log(Level.INFO, "Ruta de la segunda jasper: {0}", db.getClass().getResource("/com/benat/cano/jasperej2/jasper/telefonos.jasper").toString());
                    String imagePath1 = db.getClass().getResource("/com/benat/cano/jasperej2/jasper/email.jasper").toString();
                    String imagePath2 = db.getClass().getResource("/com/benat/cano/jasperej2/jasper/telefonos.jasper").toString();
                    parameters.put("SUBINF", imagePath1);
                    parameters.put("SUBINF2", imagePath2);
                }
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, db.getConnection());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);


        } catch (SQLException | JRException e) {
            LOGGER.log(Level.SEVERE, "Error al generar el informe o conectar a la base de datos.", e);
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
            Platform.exit();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Archivo no encontrado.", e);
            throw new RuntimeException(e);
        }
    }
    /**
     * Muestra un cuadro de diálogo de error con un título y un mensaje.
     *
     * @param titulo  Título del cuadro de diálogo.
     * @param mensaje Mensaje del cuadro de diálogo.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
