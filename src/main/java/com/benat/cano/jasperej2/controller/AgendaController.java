package com.benat.cano.jasperej2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AgendaController {

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

    @FXML
    void accionAceptar(ActionEvent event) {

    }

    @FXML
    void accionCancelar(ActionEvent event) {

    }

}
