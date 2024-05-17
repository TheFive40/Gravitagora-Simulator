package org.simulador.es.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

@Getter
@Setter
public class RegresionLineal {
    private double pendiente;
    private double intercepto;
    private double sumX = 0.0, sumY = 0.0, sumX2 = 0.0, sumXY = 0.0;
    private double promedioX, promedioY;
    private int n;
    private List<Double> x;
    private List<Integer> y;

    public RegresionLineal () {
    }

    public void setRegresionLineal ( List<Double> x, List<Integer> y ) {
        int n = x.size ( );
        for (int i = 0; i < n; i++) {
            sumX += x.get ( i );
            sumY += y.get ( i );
            sumX2 += x.get ( i ) * x.get ( i );
            sumXY += x.get ( i ) * y.get ( i );
        }
        setN ( x.size ( ) );
        setX ( x );
        setY ( y );
        pendiente = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        intercepto = (sumY - pendiente * sumX) / n;
    }

    public double obtenerPendiente () {
        return pendiente;
    }

    public double obtenerIntercepto () {
        return intercepto;
    }

    public double obtenerCoeficienteCorrelacionLineal () {
        promedioX = sumX / n;
        promedioY = sumY / n;
        List<Double> diferenciaMediasX = new ArrayList<> ( );
        List<Double> diferenciaMediasY = new ArrayList<> ( );
        List<Double> diferenciaCuadradoMediasX = new ArrayList<> ( );
        List<Double> diferenciaCuadradoMediasY = new ArrayList<> ( );
        List<Double> multiplicacionMedias = new ArrayList<> (  );

        x.forEach ( e -> {
            diferenciaMediasX.add ( (e - promedioX) );
            diferenciaCuadradoMediasX.add ( Math.pow ( (e-promedioX),2 ) );
        } );
        y.forEach ( e -> {
            diferenciaMediasY.add ( (e - promedioY) );
            diferenciaCuadradoMediasY.add ( Math.pow ( (e- promedioY),2 ) );
        } );
        int contador  = 0;
        for (Double mediasX : diferenciaMediasX) {
            multiplicacionMedias.add ( mediasX * diferenciaMediasY.get ( contador )  );
            contador++;
        }
        double sumCuadradoX = diferenciaCuadradoMediasX.stream( ).mapToDouble ( Double::doubleValue ).sum ();
        double sumCuadradoY = diferenciaCuadradoMediasY.stream ().mapToDouble ( Double::doubleValue ).sum ();
        double sumMultiplicacionMedias = multiplicacionMedias.stream ().mapToDouble ( Double::doubleValue ).sum ();
        double r = sumMultiplicacionMedias/(Math.sqrt ( Math.pow ( sumCuadradoX,2 ) ) * Math.sqrt
                ( Math.pow ( sumCuadradoY,2 )  ));
        r = BigDecimal.valueOf ( r ).setScale ( 2, RoundingMode.DOWN ).doubleValue ();
        return r;
    }

    public String obtenerEcuacion () {
        String equation = (intercepto < 0) ?
                String.format ( "y = %.2fx - %.4f", pendiente, intercepto ) : String.format ( "y = %.2fx + %.2f", pendiente, intercepto );
        return equation;
    }
}
