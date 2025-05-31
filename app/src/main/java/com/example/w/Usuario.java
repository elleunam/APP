package com.example.w;

public class Usuario {
    public String username;
    public String password;
    public String rol;
    public String dni = "", banco = "", distrito = "";

    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }
}
