package com.example.w;

public class Cliente {
    public String nombreCliente;
    public String asignadoA;
    public boolean vistoPorUsuario;

    // NUEVOS CAMPOS
    public boolean atendido;              // si fue atendido
    public String atendidoPor;            // usuario que atendió
    public String fechaAtencion;          // fecha de la atención

    public Cliente(String nombreCliente, String asignadoA) {
        this.nombreCliente = nombreCliente;
        this.asignadoA = asignadoA;
        this.vistoPorUsuario = false;
        this.atendido = false;
        this.atendidoPor = "";
        this.fechaAtencion = "";
    }

    // Getters opcionales si usas en adaptador
    public boolean isAtendido() {
        return atendido;
    }

    public String getAtendidoPor() {
        return atendidoPor;
    }

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
}
