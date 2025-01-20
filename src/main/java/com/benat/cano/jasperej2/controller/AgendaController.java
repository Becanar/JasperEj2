package com.benat.cano.jasperej2.controller;

import com.benat.cano.jasperej2.db.ConectorDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class AgendaController {

    @FXML
    private Button btGraficos;

    @FXML
    private Button btProductos;

    @FXML
    private Button btSecciones;

    @FXML
    private Button btTabProductos;

    private static final Logger LOGGER = Logger.getLogger(AgendaController.class.getName());

    @FXML
    void infoGraficos(ActionEvent event) {
        informe("grafico.jasper");
    }

    @FXML
    void infoProductos(ActionEvent event) {
        informe("productos.jasper");
    }

    @FXML
    void infoSecciones(ActionEvent event) {
        informe("secciones.jasper");
    }

    @FXML
    void infoTabProductos(ActionEvent event) {
        informe("tabla.jasper");
    }

    private void informe(String jasper){
        ConectorDB db;
        try {
            db = new ConectorDB();

            InputStream reportStream = db.getClass().getResourceAsStream("/com/benat/cano/jasperej3/jasper/"+jasper);
            if (reportStream == null) {
                LOGGER.log(Level.SEVERE, "El archivo "+jasper+" no se encontró.");
                return;
            }

            LOGGER.log(Level.INFO, "Ruta de la imagen: {0}", db.getClass().getResource("/com/benat/cano/jasperej3/img/logo.jpg").toString());

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", db.getClass().getResource("/com/benat/cano/jasperej3/img/logo.jpg").toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, db.getConnection());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);


        } catch (SQLException | JRException e) {
            LOGGER.log(Level.SEVERE, "Error al generar el informe o conectar a la base de datos.", e);
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Archivo no encontrado.", e);
            throw new RuntimeException(e);
        }
    }
    /**
     * Muestra una ventana emergente con un mensaje de error.
     * @param titulo El título de la ventana emergente.
     * @param mensaje El mensaje de error a mostrar.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
