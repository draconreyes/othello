package com.example.othello.controlador;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.othello.R;
import com.example.othello.modelo.Tablero;

public class Controlador_casillas {
    private ConstraintLayout casillas[][];
    public Controlador_casillas(ConstraintLayout casillas[][]){
        this.casillas=casillas;
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++){
                final int finalI = i;
                final int finalJ = j;
                casillas[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("i="+ finalI +"j="+finalJ);
                    }
                });
            }
        }

    }

}
