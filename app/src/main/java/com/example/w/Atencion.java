package com.example.w;

import java.io.Serializable;

public class Atencion implements Serializable {
    private String nombreCliente;
    private String tipo;
    private String resultado;
    private String observacion;
    private String usuario;
    private String fechaHora;
    private String fechaModificacion; // NUEVO

    public Atencion(String nombreCliente, String tipo, String resultado, String observacion, String usuario, String fechaHora) {
        this.nombreCliente = nombreCliente;
        this.tipo = tipo;
        this.resultado = resultado;
        this.observacion = observacion;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.fechaModificacion = ""; // Por defecto
    }

    // Getters
    public String getNombreCliente() {
        return nombreCliente;
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

    public String getUsuario() {
        return usuario;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    // Setters
    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
