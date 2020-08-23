package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.othello.R;
import com.example.othello.db.Database;
import com.example.othello.modelo.Cordenada;
import com.example.othello.modelo.Tablero;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Usuarios_disponibles extends AppCompatActivity  {
    private String id_cuenta;
    private String id_cuenta_dos;
    private Database db;
    private LinearLayout layout;
    private FirebaseAuth.AuthStateListener   mAuthListener;;
    private Query q;
    private Query q2;
    ValueEventListener a;
    ValueEventListener b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_disponibles);
        id_cuenta=getIntent().getStringExtra("id_cuenta");
        layout=findViewById(R.id.Layout_usuarios);
        db = new Database();
        System.out.println("SE CREO..................");
        q =db.getReference().child("users").orderByChild("Conectado").equalTo(true);
        q2 =db.getReference().child("partidad").orderByChild("id_usuario2").equalTo(id_cuenta);
        b=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios_conectados(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        q.addValueEventListener(b);
        a=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Verificar_solicitud(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        q2.addValueEventListener(a );

    }

    private void Verificar_solicitud(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for( DataSnapshot sn :dataSnapshot.getChildren()){
                    if(sn.child("estado").getValue().toString().equals("1")) {
                        parar_listener();
                        Intent intent = new Intent(this, Juego.class);
                        intent.putExtra("turno", Tablero.BLANCA);
                        intent.putExtra("id_partidad", sn.getKey().toString());
                        startActivity(intent);
                    }
                }
            }

    }

    private void Usuarios_conectados(DataSnapshot dataSnapshot) {
        System.out.println("......................"+dataSnapshot.getKey());
        layout.removeAllViews();
            if(dataSnapshot.exists()){
                for( final DataSnapshot sn :dataSnapshot.getChildren()){
                    if(!sn.getKey().toString().equals(id_cuenta)){
                        TextView texto_usuario= new TextView(getBaseContext());
                        texto_usuario.setText(sn.child("username").getValue().toString());
                        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Anton-Regular.ttf");
                        texto_usuario.setTypeface(font);
                        texto_usuario.setTextSize(24);
                        texto_usuario.setTextColor( Color.argb( 255,  19, 28, 162 ));
                        texto_usuario.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                crea_click(sn.getKey().toString());
                            }
                        });
                        layout.addView(texto_usuario);

                    }
                }
            }

    }

    private void crea_click(String id_cuenta_dos) {
        parar_listener();
        Cordenada cordenada = null;
        String id_partidad=db.Crear_partidad_firebase(id_cuenta,id_cuenta_dos,cordenada,Tablero.NEGRA,1);
        Intent intent = new Intent(this, Juego.class);
        intent.putExtra("turno", Tablero.NEGRA);
        intent.putExtra("id_partidad", id_partidad);
//        Intent intent = new Intent(this, Mensaje.class);
//        intent.putExtra("mensaje", "GANASTE");
        startActivity(intent);

    }



    private void parar_listener() {
        if(a==null || b==null){
            System.out.println("ESTAN NULOSSSSSSSSSSSSSSSS...............");
        }else{
            q.removeEventListener(b);
            q2.removeEventListener(a);
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        db.Usuario_no_disponible(id_cuenta);
        parar_listener();
    }
    @Override
    protected void onResume() {
        super.onResume();
        db.Usuario_disponible(id_cuenta);
        q.addValueEventListener(b);
        q2.addValueEventListener(a);
    }



}