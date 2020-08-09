package com.example.othello.modelo;

import android.content.Context;
import android.widget.TextView;

import com.example.othello.R;
import com.example.othello.othello.Juego;

public class Marcador {
    private TextView marcador_blancas;
    private TextView marcador_negras;

    public Marcador(Juego juego, Context contexto){
        marcador_blancas= juego.findViewById(R.id.puntuacion_blancas);
        marcador_negras= juego.findViewById(R.id.puntuacion_negras);
        marcador_blancas.setText("2");
        marcador_negras.setText("2");
    }
    public void  Actualizar_marcador(int negras, int blancas){
        this.marcador_blancas.setText(String.valueOf(blancas));
        this.marcador_negras.setText(String.valueOf(negras));
    }

}
