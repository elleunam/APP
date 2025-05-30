package com.example.w;

public class Atencion {
    private String fecha;
    private String tipo;
    private String resultado;
    private String observacion;

    public Atencion(String fecha, String tipo, String resultado, String observacion) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.resultado = resultado;
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public String getObservacion() {
        return observacion;
    }
}
