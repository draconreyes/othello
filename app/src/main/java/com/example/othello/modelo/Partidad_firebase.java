package com.example.othello.modelo;

public class Partidad_firebase {
    private String id_usuario2;
    private String id_usuario1;
    private int turno;
    private Cordenada cordenada;
    private int estado;
    public Partidad_firebase(String id_usuario1,String id_usuario2,Cordenada cordenada,int turno,int estado){
        this.id_usuario1=id_usuario1;
        this.id_usuario2=id_usuario2;
        this.cordenada=cordenada;
        this.turno=turno;
        this.estado=estado;
    }
    public Partidad_firebase(Cordenada cordenada,int turno){
        this.cordenada=cordenada;
        this.turno=turno;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getId_usuario2() {
        return id_usuario2;
    }

    public void setId_usuario2(String id_usuario2) {
        this.id_usuario2 = id_usuario2;
    }

    public Cordenada getCordenada() {
        return cordenada;
    }

    public void setCordenada(Cordenada cordenada) {
        this.cordenada = cordenada;
    }

    public String getId_usuario1() {
        return id_usuario1;
    }

    public void setId_usuario1(String id_usuario1) {
        this.id_usuario1 = id_usuario1;
    }
}
