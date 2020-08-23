package com.example.othello.modelo;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.othello.R;
import com.example.othello.db.Database;
import com.example.othello.modelo.Tablero;
import com.example.othello.controlador.Controlador_casillas;
import com.example.othello.othello.Juego;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Partidad  implements Observer ,ValueEventListener {
    private Tablero tablero;
    private Controlador_casillas controlador;
    private Context contexto;
    private Marcador marcador;
    private int color_jugador;
    private  static  int turno;
    private String id_partidad;
    private   Database db;
    private Cordenada cordenada;
    public Partidad (Juego juego, Context contexto,int turno , String id_partidad) {
        tablero = new Tablero(juego, contexto);
        controlador = new Controlador_casillas(tablero.getCasillas());
        marcador=  new Marcador(juego, contexto);
        this.color_jugador=turno;
        this.turno=Tablero.NEGRA;
        this.id_partidad=id_partidad;
        db = new Database();
        marcador.Turno(turno);
        this.contexto = contexto;
        controlador.addObserver(this);
        tablero.addObserver(juego);
        Query q =db.getReference().child("partidad").orderByKey().equalTo(id_partidad);
        q.addValueEventListener(this);
    }
    @Override
    public void update(Observable observable, Object o) {
        ArrayList<Object> args = (ArrayList<Object>)  o;
        Cordenada cordenada=(Cordenada) args.get(0);
        int[][] tablero_numero= this.tablero.gettablero();
        if(tablero_numero[cordenada.geti()][cordenada.getj()] == this.tablero.POSIBLE  && turno==color_jugador){
                tablero.agregar_ficha(cordenada.geti(),cordenada.getj(),turno,color_jugador,contexto);
                System.out.println("ENTRO A ENVIAR CORDENADA ");
                marcador.Turno(turno);
                if(turno==Tablero.NEGRA){
                    turno=Tablero.BLANCA;
                }else{
                    turno=Tablero.NEGRA;
                }
                db.Agregar_cordenada_turno(id_partidad,cordenada,turno);
        }
        marcador.Actualizar_marcador(tablero.get_n_negras(),tablero.get_n_blancas());
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            for( DataSnapshot sn :snapshot.getChildren()){
                    long turno_long=(long)sn.child("turno").getValue();
                    turno=(int) turno_long;
                    marcador.Turno(turno);
                    if( turno==color_jugador && sn.child("cordenada").child("i").exists() && sn.child("cordenada").child("j").exists() ){
                        System.out.println("SI ENTRO-----------------");
                        long i=(long)sn.child("cordenada").child("i").getValue();
                        long j=(long)sn.child("cordenada").child("j").getValue();
                        cordenada= new Cordenada((int)i,(int)j);
                        System.out.println("DIEGO ESCUCHA  : i:"+i+" j:"+j);
                        int temporal=0;
                        int[][] tablero_numero= this.tablero.gettablero();
                        if(tablero_numero[cordenada.geti()][cordenada.getj()] == this.tablero.POSIBLE ){
                            if(turno==Tablero.NEGRA){
                                temporal=Tablero.BLANCA;
                            }else{
                                temporal=Tablero.NEGRA;
                            }
                            tablero.agregar_ficha(cordenada.geti(),cordenada.getj(),temporal,color_jugador,contexto);
                            marcador.Actualizar_marcador(tablero.get_n_negras(),tablero.get_n_blancas());
                        }

                    }else{
                        System.out.println("NO ENTRO-----------------");
                        System.out.println("TURNO-----------------"+turno);
                        System.out.println("TURNO_JUGADOR-----------------"+color_jugador);
                    }

            }
        }
    }



    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

}
