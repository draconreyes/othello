package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;
import com.example.othello.R;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Mensaje extends AppCompatActivity {
    private String mensaje;
    private TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        mensaje =getIntent().getStringExtra("mensaje");
        texto=findViewById(R.id.mensaje);
        texto.setText(mensaje);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Anton-Regular.ttf");
        texto.setTypeface(font);
        texto.setTextSize(48);
        texto.setTextColor( Color.argb( 255,   29, 34, 108));
        System.out.println("ENTRASTE A MENSAJE..................................");
    }
}