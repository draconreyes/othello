package com.example.othello.modelo;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.othello.R;
import com.example.othello.controlador.Controlador_casillas;
import com.example.othello.othello.Juego;

public class Tablero {
    public static final int ANCHO=8;
    public static final int ALTO =8;
    public static final int BLANCA =1;
    public static final int NEGRA =2;
    private int [][] tablero;
    private ConstraintLayout casillas[][];
    private GridLayout grilla;
    public Tablero(Juego juego, Context contexto) {
        this.tablero = new int[ALTO][ANCHO];
        this.grilla=juego.findViewById(R.id.grilla);
        Point size = new Point();
        Display display = juego.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        this.casillas = new ConstraintLayout[this.ALTO][this.ANCHO];
        grilla.setRowCount(Tablero.ALTO);
        grilla.setColumnCount(Tablero.ANCHO);
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++){
                casillas[i][j] = new ConstraintLayout(juego);
                casillas[i][j].setBackgroundResource(R.drawable.recuadro);
                casillas[i][j].setMinHeight((int) (height * 0.65 / Tablero.ALTO));
                casillas[i][j].setMinWidth((int) (width * 0.85 / Tablero.ANCHO));
                casillas[i][j].setMaxHeight((int) (height * 0.65 / Tablero.ALTO));
                casillas[i][j].setMaxWidth((int) (width * 0.85 / Tablero.ANCHO));
                grilla.addView(casillas[i][j]);
            }
        }
    }

    public ConstraintLayout[][] getCasillas(){
        return casillas;
    }
}
