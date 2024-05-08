package org.simulador.es.controller;

import javafx.beans.property.MapProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.simulador.es.dao.CaidaLibreDAO;
import org.simulador.es.data.LocalStorage;
import org.simulador.es.model.CaidaLibreModel;
import org.simulador.es.model.MruModel;
import util.General;

import java.net.URL;
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
    private TableView<CaidaLibreModel> tablaMovimiento;
    private CaidaLibreDAO caidaLibreDAO;

    public void llenarTabla(){
        tableColumnTiempo.setCellValueFactory(e-> e.getValue().tiempoProperty());
        tableColumnVelocidad.setCellValueFactory(e->e.getValue().velocidadProperty());
        tablaMovimiento.setItems(caidaLibreDAO.getList());
    }
    public void llenarGraficoCaidaLibre(MapProperty<?extends Number,?extends Number> mapProperty) {
        velocidadTiempoCaidaLibre.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        posicionTiempoCaidaLibre.get().entrySet().removeIf(k->k.getKey()>General.tiempoAnimacion);
        aceleracionCaidaLibre.get().entrySet().removeIf(k->k.getKey()>General.tiempoAnimacion);
        caidaLibreDAO.getList().clear();
        lineChartVelocidadTiempo.getData().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        mapProperty.forEach((k, v) -> {
            dataSeries.getData().add(new XYChart.Data<>(k, v));
            caidaLibreDAO.add(new CaidaLibreModel((int) k, (double) v));
        });
        lineChartVelocidadTiempo.getData().addAll(dataSeries);
    }

    void eventoGraficoSeleccionado(){
        tipoInformacionChoiceBox.setOnAction(e->{
            String tipo = tipoInformacionChoiceBox.getSelectionModel().getSelectedItem();
            switch (tipo){
                case "Velocidad vs Tiempo"->{
                    tableColumnVelocidad.setText("Velocidad");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad");
                    llenarGraficoCaidaLibre(velocidadTiempoCaidaLibre);
                }
                case "Posición vs Tiempo"->{
                    tableColumnVelocidad.setText("Posición");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Posición");
                    llenarGraficoCaidaLibre(LocalStorage.posicionTiempoCaidaLibre);
                }
                case "Aceleración vs Tiempo"->{
                    tableColumnVelocidad.setText("Aceleración");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Aceleración");
                    llenarGraficoCaidaLibre(LocalStorage.aceleracionCaidaLibre);
                }
                default -> throw new IllegalArgumentException();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        caidaLibreDAO = new CaidaLibreDAO();
        eventoGraficoSeleccionado();
        llenarGraficoCaidaLibre(velocidadTiempoCaidaLibre);
        llenarTabla();
    }

}
