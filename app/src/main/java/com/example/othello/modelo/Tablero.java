package com.example.othello.modelo;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

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
    public static final int VACIA =0;
    private int [][] tablero;
    private ConstraintLayout casillas[][];
    private GridLayout grilla;
    private Context contexto;
    public Tablero(Juego juego, Context contexto) {
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
                casillas[i][j].setMinHeight((int) (height * 0.65 / Tablero.ALTO));
                casillas[i][j].setMinWidth((int) (width * 0.85 / Tablero.ANCHO));
                casillas[i][j].setMaxHeight((int) (height * 0.65 / Tablero.ALTO));
                casillas[i][j].setMaxWidth((int) (width * 0.85 / Tablero.ANCHO));
                grilla.addView(casillas[i][j]);
            }
        }
    }
    public void agregar_ficha(int i, int j ,int color){
        if(tablero[i][j]==this.VACIA){
            ImageView ficha = new ImageView(this.contexto);
            if(color==this.BLANCA){
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
            casillas[i][j].addView(ficha);
            verificar_fichas();
        }else{


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

    public void verificar_fichas(){
        for (int i = 0; i < this.ALTO; i++){
            for (int j = 0; j < this.ANCHO; j++){
                if(tablero[i][j]!=this.VACIA){
                        if(verificacion_horizontal(i,j) || verificacion_vertical(i,j) || verificacion_diagonal_izquierda(i,j) || verificacion_diagonal_derecha(i,j) ){
                            cambiar_color(i,j);
                        }
                 }
            }
        }
    }
    public boolean  verificacion_diagonal_izquierda(int i, int j){
        boolean arriba=false;
        boolean abajo=false;
            for (int y = j+1 ,x=i+1; y < this.ALTO && x <this.ALTO; y++,x++) {
                if (tablero[x][y] != this.VACIA) {
                    if (tablero[x][y] != tablero[i][j]) {
                        abajo = true;
                        break;
                    }
                } else {
                    break;
                }
             }
        for (int x = i-1 ,y=j-1; x >= 0 && y>=0; x--,y--){
                if (tablero[x][y] != this.VACIA) {
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
            if (tablero[x][y] != this.VACIA) {
                if (tablero[x][y] != tablero[i][j]) {
                    abajo = true;
                    break;
                }
            } else {
                break;
            }
        }
        for (int x = i-1 ,y=j+1; x >= 0 && y< this.ALTO; x--,y++){
            if (tablero[x][y] != this.VACIA) {
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
                if(tablero[i][x]!=this.VACIA){
                    if(tablero[i][j]!=tablero[i][x]){
                        derecha=true;
                        break;
                    }
                }else{
                    break;
                }
        }
        for (int x = j-1; x >= 0; x--){
                if(tablero[i][x]!=this.VACIA){
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
            if(tablero[x][j]!=this.VACIA){
                if(tablero[x][j]!=tablero[i][j]){
                    abajo=true;
                    break;
                }
            }else{
                break;
            }
        }
        for (int x = i-1; x >= 0; x--){
            if(tablero[x][j]!=this.VACIA){
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
}
