package com.example.othello.modelo;

public class Usuario {
    public String username;
    public String email;
    public boolean Conectado ;

    public Usuario(String username, String email,boolean conectado) {
        this.username = username;
        this.email = email;
        this.Conectado=conectado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
