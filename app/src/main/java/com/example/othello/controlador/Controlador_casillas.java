package com.example.othello.controlador;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.othello.R;
import com.example.othello.modelo.Cordenada;
import com.example.othello.modelo.Tablero;

import java.util.ArrayList;
import java.util.Observable;

public class Controlador_casillas extends Observable {
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
                        Notificar_casilla(finalI,finalJ);
                    }
                });
            }
        }

    }
    public void Notificar_casilla(int i, int j){
        Cordenada cordenada= new Cordenada(i,j);
        ArrayList<Object> args= new ArrayList<Object>();
        System.out.println("Notificar_casilla");
        System.out.println("I:"+cordenada.geti()+" J:"+cordenada.getj());
        args.add(cordenada);
        this.setChanged();
        this.notifyObservers(args);
    }

}
