package org.simulador.es.data;

import lombok.Getter;

public enum Gravedad {
    GRAVEDAD();
    public static final double TIERRA = 9.81;
    public static final double MARTE = 3.721;
    public static final double LUNA = 1.622;
    public static final double JUPITER = 24.79;
    public static final double SATURNO = 10.44;
    Gravedad(){

    }
}
