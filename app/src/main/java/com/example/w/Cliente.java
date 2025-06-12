package com.example.w;

public class Cliente {
    private String nombreCliente;
    private String asignadoA;
    private boolean vistoPorUsuario;
    private boolean atendido;
    private String atendidoPor;
    private String fechaAtencion;
    private String fechaAsignacion;

    // Constructor
    public Cliente(String nombreCliente, String asignadoA) {
        this.nombreCliente = nombreCliente;
        this.asignadoA = asignadoA;
        this.vistoPorUsuario = false;
        this.atendido = false;
        this.atendidoPor = "";
        this.fechaAtencion = "";
        this.fechaAsignacion = "";
    }

    // Getters
    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public boolean isVistoPorUsuario() {
        return vistoPorUsuario;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public String getAtendidoPor() {
        return atendidoPor;
    }

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion != null ? fechaAsignacion : "(sin fecha)";
    }

    // Setters
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public void setVistoPorUsuario(boolean vistoPorUsuario) {
        this.vistoPorUsuario = vistoPorUsuario;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public void setAtendidoPor(String atendidoPor) {
        this.atendidoPor = atendidoPor;
    }

    public void setFechaAtencion(String fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
