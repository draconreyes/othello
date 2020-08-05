package com.example.othello.othello;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.othello.R;
import com.example.othello.modelo.Tablero;
import com.example.othello.controlador.Controlador_casillas;

public class Partidad  {
    private Tablero tablero;
    private Controlador_casillas controlador;
    public Partidad (Juego juego, Context contexto){
        tablero=new Tablero(juego,contexto);
        controlador= new Controlador_casillas(tablero.getCasillas());
    }

}
