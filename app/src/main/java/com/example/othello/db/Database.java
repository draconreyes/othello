package com.example.othello.db;
import com.example.othello.modelo.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.othello.R;
public class Database {
    private DatabaseReference mDatabase;
    public Database(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void Usuario_disponible(String Id, String nombre, String email) {
        Usuario user = new Usuario(nombre, email);
        mDatabase.child("users").child(Id).setValue(user);
    }
}
