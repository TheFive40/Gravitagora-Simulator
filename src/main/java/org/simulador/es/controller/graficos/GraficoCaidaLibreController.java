package org.simulador.es.controller.graficos;

import javafx.beans.property.MapProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.simulador.es.dao.CaidaLibreDAO;
import org.simulador.es.data.LocalStorage;
import org.simulador.es.data.RegresionLineal;
import org.simulador.es.model.CaidaLibreModel;
import util.General;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.simulador.es.data.LocalStorage.*;

public class GraficoCaidaLibreController implements Initializable {

    @FXML
    private LineChart<Number, Number> lineChartVelocidadTiempo;

    @FXML
    private TableColumn<CaidaLibreModel, Number> tableColumnTiempo;

    @FXML
    private TableColumn<CaidaLibreModel, Number> tableColumnVelocidad;
    @FXML
    private ChoiceBox<String> tipoInformacionChoiceBox;
    @FXML
    private Label labelEcuacion;

    @FXML
    private Button btnGuardarDatos;
    @FXML
    private VBox contenedorPrincipal;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<CaidaLibreModel> tablaMovimiento;
    private CaidaLibreDAO caidaLibreDAO;
    private RegresionLineal regresionLineal;

    public void llenarTabla () {
        tableColumnTiempo.setCellValueFactory ( e -> e.getValue ( ).tiempoProperty ( ) );
        tableColumnVelocidad.setCellValueFactory ( e -> e.getValue ( ).velocidadProperty ( ) );
        tablaMovimiento.setItems ( caidaLibreDAO.getList ( ) );
    }

    public void llenarGraficoCaidaLibre ( MapProperty<? extends Number, ? extends Number> mapProperty ) {
        regresionLineal = new RegresionLineal ( );
        velocidadTiempoCaidaLibre.get ( ).entrySet ( ).removeIf ( k -> k.getKey ( ) > General.tiempoAnimacion );
        posicionTiempoCaidaLibre.get ( ).entrySet ( ).removeIf ( k -> k.getKey ( ) > General.tiempoAnimacion );
        aceleracionCaidaLibre.get ( ).entrySet ( ).removeIf ( k -> k.getKey ( ) > General.tiempoAnimacion );
        caidaLibreDAO.getList ( ).clear ( );
        lineChartVelocidadTiempo.getData ( ).clear ( );
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<> ( );
        List<Double> x = new ArrayList<> ( );
        List<Integer> y = new ArrayList<> ( );

        mapProperty.forEach ( ( k, v ) -> {
            x.add ( (Double) v );
            y.add ( (Integer) k );
            dataSeries.getData ( ).add ( new XYChart.Data<> ( k, v ) );
            caidaLibreDAO.add ( new CaidaLibreModel ( (int) k, (double) v ) );
        } );
        regresionLineal.setRegresionLineal ( x, y );
        Tooltip tooltip = new Tooltip ( regresionLineal.obtenerEcuacion ( ) );
        scrollPane.setTooltip ( tooltip );
        lineChartVelocidadTiempo.getData ( ).addAll ( dataSeries );
    }

    void eventoGraficoSeleccionado () {
        tipoInformacionChoiceBox.setOnAction ( e -> {
            String tipo = tipoInformacionChoiceBox.getSelectionModel ( ).getSelectedItem ( );
            switch (tipo) {
                case "Velocidad vs Tiempo" -> {
                    tableColumnVelocidad.setText ( "Velocidad (m/s)" );
                    lineChartVelocidadTiempo.getYAxis ( ).setLabel ( "Velocidad (m/s)" );
                    llenarGraficoCaidaLibre ( velocidadTiempoCaidaLibre );
                }
                case "Posición vs Tiempo" -> {
                    tableColumnVelocidad.setText ( "Posición (m)" );
                    lineChartVelocidadTiempo.getYAxis ( ).setLabel ( "Posición (m)" );
                    llenarGraficoCaidaLibre ( LocalStorage.posicionTiempoCaidaLibre );
                }
                case "Aceleración vs Tiempo" -> {
                    tableColumnVelocidad.setText ( "Aceleración (m/s²)" );
                    lineChartVelocidadTiempo.getYAxis ( ).setLabel ( "Aceleración (m/s²)" );
                    llenarGraficoCaidaLibre ( LocalStorage.aceleracionCaidaLibre );
                }
                default -> throw new IllegalArgumentException ( );
            }
        } );
    }

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        caidaLibreDAO = new CaidaLibreDAO ( );
        eventoGraficoSeleccionado ( );
        llenarGraficoCaidaLibre ( velocidadTiempoCaidaLibre );
        llenarTabla ( );
    }

}
