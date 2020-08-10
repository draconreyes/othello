package com.example.othello.modelo;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.othello.R;
import com.example.othello.othello.Juego;

public class Marcador {
    private TextView marcador_blancas;
    private TextView marcador_negras;
    private LinearLayout layout_negra;
    private LinearLayout layout_blanca;

    public Marcador(Juego juego, Context contexto){
        marcador_blancas= juego.findViewById(R.id.puntuacion_blancas);
        marcador_negras= juego.findViewById(R.id.puntuacion_negras);
        marcador_blancas.setText("2");
        marcador_negras.setText("2");
        layout_negra=juego.findViewById(R.id.layout_negra);
        layout_blanca=juego.findViewById(R.id.layout_blanca);
    }
    public void  Actualizar_marcador(int negras, int blancas){
        this.marcador_blancas.setText(String.valueOf(blancas));
        this.marcador_negras.setText(String.valueOf(negras));
    }
    public void Turno(int turno){
        if(Tablero.BLANCA==turno){
            layout_blanca.setBackgroundColor(Color.argb( 255,  62, 240, 10 ));
            layout_negra.setBackgroundColor(Color.argb(255,  255, 16, 4));
        }else{
            layout_negra.setBackgroundColor(Color.argb(255,  62, 240, 10 ));
            layout_blanca.setBackgroundColor(Color.argb(255,  255, 16, 4));
        }
    }

}
