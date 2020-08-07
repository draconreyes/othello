package com.example.othello.modelo;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.othello.R;
import com.example.othello.modelo.Tablero;
import com.example.othello.controlador.Controlador_casillas;
import com.example.othello.othello.Juego;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Partidad  implements Observer {
    private Tablero tablero;
    private Controlador_casillas controlador;
    private Context contexto;
    private int turno;
    public Partidad (Juego juego, Context contexto) {
        tablero = new Tablero(juego, contexto);
        controlador = new Controlador_casillas(tablero.getCasillas());
        this.turno=tablero.NEGRA;
        this.contexto = contexto;
        controlador.addObserver(this);
    }
    @Override
    public void update(Observable observable, Object o) {
        ArrayList<Object> args = (ArrayList<Object>)  o;
        Cordenada cordenada=(Cordenada) args.get(0);
        if(turno==Tablero.BLANCA){
            tablero.agregar_ficha(cordenada.geti(),cordenada.getj(),turno);
            turno=Tablero.NEGRA;
        }else{
            tablero.agregar_ficha(cordenada.geti(),cordenada.getj(),turno);
            turno=Tablero.BLANCA;
        }

    }
}
