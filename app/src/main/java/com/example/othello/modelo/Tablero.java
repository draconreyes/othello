package com.example.othello.modelo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.othello.R;
import com.example.othello.controlador.Controlador_casillas;
import com.example.othello.db.Database;
import com.example.othello.othello.Juego;
import com.example.othello.othello.Mensaje;

import java.util.ArrayList;
import java.util.Observable;

public class Tablero extends Observable {
    public static final int ANCHO=8;
    public static final int ALTO =8;
    public static final int BLANCA =1;
    public static final int NEGRA =2;
    public static final int VACIA =0;
    public static final int POSIBLE=3;
    public static final int HORIZONTAL=5;
    public static final int VERTICAL=6;
    public static final int DIAGONAL_IZQUIERDA=7;
    public static final int DIAGONAL_DERECHA=8;
    private int numero_blancas;
    private int numero_negras;
    private int [][] tablero;
    private ConstraintLayout casillas[][];
    private GridLayout grilla;
    private Context contexto;
    private Juego juego;
    public Tablero(Juego juego, Context contexto) {
        this.juego = juego;
        this.numero_blancas=2;
        this.numero_negras=2;
        this.contexto=contexto;
        this.tablero = new int[this.ALTO][this.ANCHO];
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
                if((i==3 && j==3) ||  (i==4 && j==4)){
                    ImageView blanca = new ImageView(contexto);
                    blanca.setBackgroundResource(R.drawable.blancas);
                    blanca.setMaxHeight(casillas[0][0].getMaxHeight());
                    blanca.setMinimumHeight(casillas[0][0].getMaxHeight());
                    blanca.setMinimumWidth(casillas[0][0].getMinWidth());
                    blanca.setMaxWidth(casillas[0][0].getMaxWidth());
                    casillas[i][j].addView(blanca);
                    tablero[i][j]=this.BLANCA;
                }else if((i==3 && j==4) || (i==4 && j==3)){
                    ImageView negra = new ImageView(contexto);
                    negra.setBackgroundResource(R.drawable.negras);
                    negra.setMaxHeight(casillas[0][0].getMaxHeight());
                    negra.setMinimumHeight(casillas[0][0].getMaxHeight());
                    negra.setMinimumWidth(casillas[0][0].getMinWidth());
                    negra.setMaxWidth(casillas[0][0].getMaxWidth());
                    casillas[i][j].addView(negra);
                    tablero[i][j]=this.NEGRA;
                }else{
                    tablero[i][j]=this.VACIA;
                }

                casillas[i][j].setMinHeight((int) (height * 0.50 / 8));
                casillas[i][j].setMinWidth((int) (width * 0.9 / 8));
                casillas[i][j].setMaxHeight((int) (height * 0.50 / 8));
                casillas[i][j].setMaxWidth((int) (width * 0.9 / 8));
                grilla.addView(casillas[i][j]);
            }
        }
        posibilidades(this.BLANCA);
    }
    public void agregar_ficha(int i, int j ,int turno,int color_jugador,Context contexto ){
        if(tablero[i][j]==this.POSIBLE){
            ImageView ficha = new ImageView(this.contexto);
            if(turno==this.BLANCA){
                ficha.setBackgroundResource(R.drawable.blancas);
                tablero[i][j]=this.BLANCA;
            }else{
                ficha.setBackgroundResource(R.drawable.negras);
                tablero[i][j]=this.NEGRA;
            }
            ficha.setMaxHeight(casillas[0][0].getMaxHeight());
            ficha.setMinimumHeight(casillas[0][0].getMaxHeight());
            ficha.setMinimumWidth(casillas[0][0].getMinWidth());
            ficha.setMaxWidth(casillas[0][0].getMaxWidth());
            casillas[i][j].removeAllViews();
            casillas[i][j].addView(ficha);
            quitar_posibilidades();
            verificar_fichas(i,j);
            posibilidades(turno);
            contar_fichas();
            if(fin_juego()){
                Notificar(color_jugador);

            }

        }
    }

    private void Notificar(int color_jugador) {
        ArrayList<Object> args= new ArrayList<Object>();
        String resultado;
        if(numero_blancas>numero_negras){
            if(color_jugador==this.BLANCA){
                Toast toast1 =
                        Toast.makeText(this.contexto,
                                "GANASTE", Toast.LENGTH_SHORT);
                toast1.show();
                resultado="GANASTE";
            }else{
                Toast toast1 =
                        Toast.makeText(this.contexto,
                                "PERDISTE", Toast.LENGTH_SHORT);

                toast1.show();
                resultado="PERDISTE";
            }
        }else if(numero_negras>numero_blancas){
            if(color_jugador==this.NEGRA){
                Toast toast1 =
                        Toast.makeText(this.contexto,
                                "GANASTE", Toast.LENGTH_SHORT);

                toast1.show();
                resultado="GANASTE";
            }else{
                Toast toast1 =
                        Toast.makeText(this.contexto,
                                "PERDISTE", Toast.LENGTH_SHORT);
                resultado="PERDISTE";
                toast1.show();
            }
        }else{
            Toast toast1 =
                    Toast.makeText(this.contexto,
                            "TABLAS", Toast.LENGTH_SHORT);

            toast1.show();
            resultado="TABLAS";
        }
        args.add(resultado);
        this.setChanged();
        this.notifyObservers(args);

    }

    public boolean fin_juego(){
        boolean fin_juego=true;
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++) {
                if(tablero[i][j]==this.POSIBLE) {
                  fin_juego=false;
                  return fin_juego;
                }
            }
        }
        return  fin_juego;
    }
    public void contar_fichas(){
        this.numero_blancas=0;
        this.numero_negras=0;
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++) {
                if(tablero[i][j]==this.BLANCA) {
                   this.numero_blancas++;
                }else if(tablero[i][j]==this.NEGRA){
                    this.numero_negras++;
                }
            }
        }
    }
    public void posibilidades(int turno){
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++) {
                    if(tablero[i][j]==turno && tablero[i][j]!=this.VACIA && tablero[i][j]!=this.POSIBLE) {
                        posiciones_flanco(i, j);
                    }
            }
        }
    }

    public void posiciones_flanco( int i , int j){
        System.out.println("POSICIONES");
        System.out.println("I:"+i+" j:"+j);
        System.out.println(tablero[i][j]);
        if(verificar_flancho_horizontal(i,j)){
            System.out.println("HORIZONTAL");
            if( j-1>=0 && tablero[i][j-1]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i][j-1].addView(posibilidad);
                tablero[i][j-1]=this.POSIBLE;
                System.out.println("IZQUIERDA");
            }else if(j+1<this.ANCHO && tablero[i][j+1]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i][j+1].addView(posibilidad);
                tablero[i][j+1]=this.POSIBLE;
                System.out.println("DERECHA");
            }
        }
        if(verificar_flancho_vertical(i,j)){
            if( i-1>=0 && tablero[i-1][j]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i-1][j].addView(posibilidad);
                tablero[i-1][j]=this.POSIBLE;
                System.out.println("IZQUIERDA");
            }else if(i+1<this.ANCHO && tablero[i+1][j]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i+1][j].addView(posibilidad);
                tablero[i+1][j]=this.POSIBLE;
                System.out.println("DERECHA");
            }
        }
        if(verificar_flanco_diagonal_derecha(i,j)){
            if( i+1<this.ANCHO && j-1>=0  &&tablero[i+1][j-1]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i+1][j-1].addView(posibilidad);
                tablero[i+1][j-1]=this.POSIBLE;
                System.out.println("IZQUIERDA");
            }else if(i-1>=0 && j+1<this.ANCHO &&tablero[i-1][j+1]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i-1][j+1].addView(posibilidad);
                tablero[i-1][j+1]=this.POSIBLE;
                System.out.println("DERECHA");
            }
        }
        if(verificar_flanco_diagonal_izquierda(i,j)){
            if( i-1>=0 && j-1>=0  &&tablero[i-1][j-1]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i-1][j-1].addView(posibilidad);
                tablero[i-1][j-1]=this.POSIBLE;
                System.out.println("IZQUIERDA");
            }else if(i+1<this.ANCHO && j+1<this.ANCHO &&tablero[i+1][j+1]==this.VACIA){
                ImageView posibilidad = new ImageView(contexto);
                posibilidad.setBackgroundResource(R.drawable.circulo);
                posibilidad.setMaxHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumHeight(casillas[0][0].getMaxHeight());
                posibilidad.setMinimumWidth(casillas[0][0].getMinWidth());
                posibilidad.setMaxWidth(casillas[0][0].getMaxWidth());
                casillas[i+1][j+1].addView(posibilidad);
                tablero[i+1][j+1]=this.POSIBLE;
                System.out.println("DERECHA");
            }
        }
    }


    public void quitar_posibilidades(){
        for (int i = 0; i < this.ALTO; i++){
            for (int j = 0; j < this.ANCHO; j++){
                if(tablero[i][j]==this.POSIBLE){
                    casillas[i][j].removeAllViews();
                    tablero[i][j]=this.VACIA;
                }
            }
        }

    }


    public void cambiar_color(int i, int j){
        ImageView ficha = new ImageView(this.contexto);
        if(tablero[i][j]==this.BLANCA){
            ficha.setBackgroundResource(R.drawable.negras);
            tablero[i][j]=this.NEGRA;
        }else{
            ficha.setBackgroundResource(R.drawable.blancas);
            tablero[i][j]=this.BLANCA;
        }
        ficha.setMaxHeight(casillas[0][0].getMaxHeight());
        ficha.setMinimumHeight(casillas[0][0].getMaxHeight());
        ficha.setMinimumWidth(casillas[0][0].getMinWidth());
        ficha.setMaxWidth(casillas[0][0].getMaxWidth());
        casillas[i][j].removeAllViews();
        casillas[i][j].addView(ficha);
    }

    public boolean verificar_flancho_horizontal(int i,int j){
        boolean derecha=false;
        boolean izquierda=false;
        for (int x = j+1; x < this.ANCHO; x++){
            if(tablero[i][x]!=this.VACIA && tablero[i][x] != this.POSIBLE){
                if(tablero[i][j]!=tablero[i][x]){
                    derecha=true;
                    break;
                }
            }else{
                break;
            }
        }
        for (int x = j-1; x >= 0; x--){
            if(tablero[i][x]!=this.VACIA && tablero[i][x] != this.POSIBLE){
                if(tablero[i][j]!=tablero[i][x]){
                    izquierda=true;
                    break;
                }
            }else{
                break;
            }
        }
        if(derecha || izquierda){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }
    public boolean verificar_flancho_vertical(int i,int j){
        boolean abajo=false;
        boolean arriba=false;
        for (int x = i+1; x < this.ALTO; x++){
            if(tablero[x][j]!=this.VACIA && tablero[x][j] != this.POSIBLE){
                if(tablero[x][j]!=tablero[i][j]){
                    abajo=true;
                    break;
                }
            }else{
                break;
            }
        }
        for (int x = i-1; x >= 0; x--){
            if(tablero[x][j]!=this.VACIA && tablero[x][j] != this.POSIBLE){
                if(tablero[x][j]!=tablero[i][j]){
                    arriba=true;
                    break;
                }
            }else{
                break;
            }
        }
        if(abajo || arriba){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }
    public boolean verificar_flanco_diagonal_derecha(int i,int j){
        boolean arriba=false;
        boolean abajo=false;
        for (int y = j-1 ,x=i+1; y >=0 && x <this.ALTO; y--,x++) {
            if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                if (tablero[x][y] != tablero[i][j]) {
                    abajo = true;
                    break;
                }
            } else {
                break;
            }
        }
        for (int x = i-1 ,y=j+1; x >= 0 && y< this.ALTO; x--,y++){
            if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                if (tablero[x][y] != tablero[i][j]) {
                    arriba = true;
                    break;
                }
            } else {
                break;
            }
        }
        if(arriba || abajo){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }
    public boolean verificar_flanco_diagonal_izquierda(int i,int j){
        boolean arriba=false;
        boolean abajo=false;
        for (int y = j+1 ,x=i+1; y < this.ALTO && x <this.ALTO; y++,x++) {
            if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                if (tablero[x][y] != tablero[i][j]) {
                    abajo = true;
                    break;
                }
            } else {
                break;
            }
        }
        for (int x = i-1 ,y=j-1; x >= 0 && y>=0; x--,y--){
            if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                if (tablero[x][y] != tablero[i][j]) {
                    arriba = true;
                    break;
                }
            } else {
                break;
            }
        }
        if(arriba || abajo){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }


    public void verificar_fichas(int i , int j){
        if( j+1<this.ANCHO && tablero[i][j+1]!=this.VACIA && tablero[i][j+1]!=this.POSIBLE && tablero[i][j+1]!=tablero[i][j] ){
            if(verificacion_horizontal(i,j+1) ){
                cambiar_color(i,j+1);
                System.out.println("CAMBIE FICHA COLOR: i"+i+" j:"+(j+1));
                verificar_fichas_sentido(i,j+1,this.HORIZONTAL);
            }
        }
        if( j-1>=0 && tablero[i][j-1]!=this.VACIA && tablero[i][j-1]!=this.POSIBLE && tablero[i][j-1]!=tablero[i][j] ){
            if(verificacion_horizontal(i,j-1) ){
                cambiar_color(i,j-1);
                System.out.println("CAMBIE FICHA COLOR: i"+i+" j:"+(j-1));
                verificar_fichas_sentido(i,j-1,this.HORIZONTAL);
            }
        }
        if( i+1<this.ALTO && tablero[i+1][j]!=this.VACIA && tablero[i+1][j]!=this.POSIBLE && tablero[i+1][j]!=tablero[i][j] ){
            if(verificacion_vertical(i+1,j) ){
                cambiar_color(i+1,j);
                System.out.println("CAMBIE FICHA COLOR: i"+(i+1)+" j:"+j);
                verificar_fichas_sentido(i+1,j,this.VERTICAL);
            }
        }
        if(i-1>=0 && tablero[i-1][j]!=this.VACIA && tablero[i-1][j]!=this.POSIBLE && tablero[i-1][j]!=tablero[i][j] ){
            if(verificacion_vertical(i-1,j) ){
                cambiar_color(i-1,j);
                System.out.println("CAMBIE FICHA COLOR: i"+(i-1)+" j:"+j);
                verificar_fichas_sentido(i-1,j,this.VERTICAL);
            }
        }
        if(i-1>=0 && j-1>=0 && tablero[i-1][j-1]!=this.VACIA && tablero[i-1][j-1]!=this.POSIBLE && tablero[i-1][j-1]!=tablero[i][j] ){
            if(verificacion_diagonal_izquierda(i-1,j-1) ){
                cambiar_color(i-1,j-1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i-1)+" j:"+(j-1));
                verificar_fichas_sentido(i-1,j-1,this.DIAGONAL_IZQUIERDA);
            }
        }
        if(i+1<this.ALTO && j+1<this.ANCHO && tablero[i+1][j+1]!=this.VACIA && tablero[i+1][j+1]!=this.POSIBLE && tablero[i+1][j+1]!=tablero[i][j] ){
            if(verificacion_diagonal_izquierda(i+1,j+1) ){
                cambiar_color(i+1,j+1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i+1)+" j:"+(j+1));
                verificar_fichas_sentido(i+1,j+1,this.DIAGONAL_IZQUIERDA);
            }
        }
        if(i-1>=0 && j+1<this.ANCHO && tablero[i-1][j+1]!=this.VACIA && tablero[i-1][j+1]!=this.POSIBLE && tablero[i-1][j+1]!=tablero[i][j] ){
            if(verificacion_diagonal_derecha(i-1,j+1) ){
                cambiar_color(i-1,j+1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i-1)+" j:"+(j+1));;
                verificar_fichas_sentido(i-1,j+1,this.DIAGONAL_DERECHA);
            }
        }
        if( i+1<this.ALTO && j-1>=0  && tablero[i+1][j-1]!=this.VACIA && tablero[i+1][j-1]!=this.POSIBLE && tablero[i+1][j-1]!=tablero[i][j] ){
            if(verificacion_diagonal_derecha(i+1,j-1) ){
                cambiar_color(i+1,j-1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i+1)+" j:"+(j-1));
                verificar_fichas_sentido(i+1,j-1,this.DIAGONAL_DERECHA);
            }
        }
    }
    public void verificar_fichas_sentido(int i , int j,int  sentido){
        if( j+1<this.ANCHO && tablero[i][j+1]!=this.VACIA && tablero[i][j+1]!=this.POSIBLE && tablero[i][j+1]!=tablero[i][j] && sentido==this.HORIZONTAL){
            if(verificacion_horizontal(i,j+1) ){
                cambiar_color(i,j+1);
                System.out.println("CAMBIE FICHA COLOR: i"+i+" j:"+(j+1));
                verificar_fichas_sentido(i,j+1,sentido);
            }
        }
        if( j-1>=0 && tablero[i][j-1]!=this.VACIA && tablero[i][j-1]!=this.POSIBLE && tablero[i][j-1]!=tablero[i][j] && sentido==this.HORIZONTAL){
            if(verificacion_horizontal(i,j-1) ){
                cambiar_color(i,j-1);
                System.out.println("CAMBIE FICHA COLOR: i"+i+" j:"+(j-1));
                verificar_fichas_sentido(i,j-1,sentido);
            }
        }
        if( i+1<this.ALTO && tablero[i+1][j]!=this.VACIA && tablero[i+1][j]!=this.POSIBLE && tablero[i+1][j]!=tablero[i][j] && sentido==this.VERTICAL){
            if(verificacion_vertical(i+1,j) ){
                cambiar_color(i+1,j);
                System.out.println("CAMBIE FICHA COLOR: i"+(i+1)+" j:"+j);
                verificar_fichas_sentido(i+1,j,sentido);
            }
        }
        if(i-1>=0 && tablero[i-1][j]!=this.VACIA && tablero[i-1][j]!=this.POSIBLE && tablero[i-1][j]!=tablero[i][j] && sentido==this.VERTICAL ){
            if(verificacion_vertical(i-1,j) ){
                cambiar_color(i-1,j);
                System.out.println("CAMBIE FICHA COLOR: i"+(i-1)+" j:"+j);
                verificar_fichas_sentido(i-1,j,sentido);
            }
        }
        if(i-1>=0 && j-1>=0 && tablero[i-1][j-1]!=this.VACIA && tablero[i-1][j-1]!=this.POSIBLE && tablero[i-1][j-1]!=tablero[i][j] && sentido==this.DIAGONAL_IZQUIERDA){
            if(verificacion_diagonal_izquierda(i-1,j-1) ){
                cambiar_color(i-1,j-1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i-1)+" j:"+(j-1));
                verificar_fichas_sentido(i-1,j-1,sentido);
            }
        }
        if(i+1<this.ALTO && j+1<this.ANCHO && tablero[i+1][j+1]!=this.VACIA && tablero[i+1][j+1]!=this.POSIBLE && tablero[i+1][j+1]!=tablero[i][j]&& sentido==this.DIAGONAL_IZQUIERDA ){
            if(verificacion_diagonal_izquierda(i+1,j+1) ){
                cambiar_color(i+1,j+1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i+1)+" j:"+(j+1));
                verificar_fichas_sentido(i+1,j+1,sentido);
            }
        }
        if(i-1>=0 && j+1<this.ANCHO && tablero[i-1][j+1]!=this.VACIA && tablero[i-1][j+1]!=this.POSIBLE && tablero[i-1][j+1]!=tablero[i][j] && sentido==this.DIAGONAL_DERECHA ){
            if(verificacion_diagonal_derecha(i-1,j+1) ){
                cambiar_color(i-1,j+1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i-1)+" j:"+(j+1));
                verificar_fichas_sentido(i-1,j+1,sentido);
            }
        }
        if( i+1<this.ALTO && j-1>=0  && tablero[i+1][j-1]!=this.VACIA && tablero[i+1][j-1]!=this.POSIBLE && tablero[i+1][j-1]!=tablero[i][j] && sentido==this.DIAGONAL_DERECHA ){
            if(verificacion_diagonal_derecha(i+1,j-1) ){
                cambiar_color(i+1,j-1);
                System.out.println("CAMBIE FICHA COLOR: i"+(i+1)+" j:"+(j-1));
                verificar_fichas_sentido(i+1,j-1,sentido);
            }
        }
    }


    public boolean  verificacion_diagonal_izquierda(int i, int j){
        boolean arriba=false;
        boolean abajo=false;
            for (int y = j+1 ,x=i+1; y < this.ALTO && x <this.ALTO; y++,x++) {
                if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                    if (tablero[x][y] != tablero[i][j]) {
                        abajo = true;
                        break;
                    }
                } else {
                    break;
                }
             }
        for (int x = i-1 ,y=j-1; x >= 0 && y>=0; x--,y--){
                if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                    if (tablero[x][y] != tablero[i][j]) {
                        arriba = true;
                        break;
                    }
                } else {
                    break;
                }
        }
        if(arriba && abajo){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }
    public boolean  verificacion_diagonal_derecha(int i, int j){
        boolean arriba=false;
        boolean abajo=false;
        for (int y = j-1 ,x=i+1; y >=0 && x <this.ALTO; y--,x++) {
            if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                if (tablero[x][y] != tablero[i][j]) {
                    abajo = true;
                    break;
                }
            } else {
                break;
            }
        }
        for (int x = i-1 ,y=j+1; x >= 0 && y< this.ALTO; x--,y++){
            if (tablero[x][y] != this.VACIA && tablero[x][y] != this.POSIBLE) {
                if (tablero[x][y] != tablero[i][j]) {
                    arriba = true;
                    break;
                }
            } else {
                break;
            }
        }
        if(arriba && abajo){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }



    public boolean  verificacion_horizontal(int i, int j){
        boolean derecha=false;
        boolean izquierda=false;
        for (int x = j+1; x < this.ANCHO; x++){
            if(tablero[i][x]!=this.VACIA && tablero[i][x] != this.POSIBLE){
                if(tablero[i][j]!=tablero[i][x]){
                    derecha=true;
                    break;
                }
            }else{
                break;
            }
        }
        for (int x = j-1; x >= 0; x--){
            if(tablero[i][x]!=this.VACIA && tablero[i][x] != this.POSIBLE){
                if(tablero[i][j]!=tablero[i][x]){
                    izquierda=true;
                    break;
                }
            }else{
                break;
            }
        }
        if(derecha && izquierda){
           // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }

    public boolean  verificacion_vertical(int i, int j){
        boolean abajo=false;
        boolean arriba=false;
        for (int x = i+1; x < this.ALTO; x++){
            if(tablero[x][j]!=this.VACIA && tablero[x][j] != this.POSIBLE){
                if(tablero[x][j]!=tablero[i][j]){
                    abajo=true;
                    break;
                }
            }else{
                break;
            }
        }
        for (int x = i-1; x >= 0; x--){
            if(tablero[x][j]!=this.VACIA && tablero[x][j] != this.POSIBLE){
                if(tablero[x][j]!=tablero[i][j]){
                    arriba=true;
                    break;
                }
            }else{
                break;
            }
        }
        if(abajo && arriba){
            // System.out.println("i:"+i+" j:"+j);
            System.out.println("true");
            return true;
        }else{
            //System.out.println("i:"+i+" j:"+j);
            System.out.println("false");
            return false;
        }
    }

    public ConstraintLayout[][] getCasillas(){
        return casillas;
    }
    public int[][] gettablero(){
        return tablero;
    }
    public int  get_n_blancas(){
        return this.numero_blancas;
    }
    public int  get_n_negras(){
        return this.numero_negras;
    }
}
