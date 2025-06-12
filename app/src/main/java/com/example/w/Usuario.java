package com.example.w;

public class Usuario {
    public String username;
    public String password;
    public String rol;

    // 🔹 Solo aplica si el usuario tiene rol "ejecutivo"
    public String supervisorAsignado = "";

    // Constructor original (NO SE TOCA)
    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    // ✅ GETTERS NECESARIOS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public String getSupervisorAsignado() {
        return supervisorAsignado;
    }

    // (Opcional) SETTER si deseas permitir cambiar el supervisor
    public void setSupervisorAsignado(String supervisorAsignado) {
        this.supervisorAsignado = supervisorAsignado;
    }
}
