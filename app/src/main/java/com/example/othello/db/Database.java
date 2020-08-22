package com.example.othello.db;
import com.example.othello.modelo.Cordenada;
import com.example.othello.modelo.Partidad_firebase;
import com.example.othello.modelo.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.othello.R;
public class Database {
    private DatabaseReference mDatabase;
    public Database(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void Usuario_disponible(String Id, String nombre, String email) {
        Usuario user = new Usuario(nombre, email,true);
        mDatabase.child("users").child(Id).setValue(user);
    }
    public void Usuario_disponible(String Id) {
        mDatabase.child("users").child(Id).child("Conectado").setValue(true);
    }

    public void Usuario_no_disponible(String Id) {
        mDatabase.child("users").child(Id).child("Conectado").setValue(false);
    }
    public void Partidad_terminada(String Id) {
        mDatabase.child("partidad").child(Id).child("estado").setValue(0);
    }
    public void Cambiar_turno(String Id,int turno) {
        mDatabase.child("partidad").child(Id).child("turno").setValue(turno);
    }
    public void Agregar_cordenada_turno(String Id,Cordenada cordenada,int turno){
        mDatabase.child("partidad").child(Id).child("cordenada").child("i").setValue(cordenada.geti());
        mDatabase.child("partidad").child(Id).child("cordenada").child("j").setValue(cordenada.getj());
        mDatabase.child("partidad").child(Id).child("turno").setValue(turno);
    }
    public DatabaseReference getReference(){
        return this.mDatabase;
    }
    public String Crear_partidad_firebase(String id_usuario1, String id_usuario2, Cordenada cordenada,int turno,int estado) {
        Partidad_firebase partidad = new Partidad_firebase( id_usuario1,  id_usuario2,  cordenada, turno,estado);
        DatabaseReference pushedPostRef = mDatabase.push();
        String postId = pushedPostRef.getKey();
        mDatabase.child("partidad").child(postId).setValue(partidad);
        return postId;
    }
}
