package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;
import com.example.othello.R;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Mensaje extends AppCompatActivity implements View.OnClickListener {
    private String mensaje;
    private TextView texto;
    private TextView volver;
    private String id_jugador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        id_jugador=getIntent().getStringExtra("id_jugador");
        mensaje =getIntent().getStringExtra("mensaje");
        texto=findViewById(R.id.mensaje);
        volver=findViewById(R.id.texto_volver);
        texto.setText(mensaje);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Anton-Regular.ttf");
        texto.setTypeface(font);
        texto.setTextSize(48);
        texto.setTextColor( Color.argb( 255,   29, 34, 108));
        volver.setText("VOLVER AL LOBY");
        volver.setTypeface(font);
        volver.setTextSize(32);
        volver.setOnClickListener(this);
        System.out.println("ENTRASTE A MENSAJE..................................");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, Usuarios_disponibles.class);
        intent.putExtra("id_cuenta", id_jugador);
        startActivity(intent);
    }
}