package com.example.contactosnuevo;

import android.os.Bundle;

import com.example.contactosnuevo.models.ContactoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class DetalleActivity extends AppCompatActivity {

    private TextView tv_detalle_nombre, tv_detalle_numero, tv_detalle_contactoConfianza;
    private ContactoModel model;

    private final String text_reference="contactos";
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference reference= database.getReference(text_reference);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);

        //Inicializar variables
        tv_detalle_nombre=findViewById(R.id.tv_detalle_nombre);
        tv_detalle_numero=findViewById(R.id.tv_detalle_numero);
        tv_detalle_contactoConfianza=findViewById(R.id.tv_detalle_contactoConfianza);
        model= new ContactoModel();


        String id=getIntent().getStringExtra("id");
       reference.child(id).addValueEventListener(new ValueEventListener() {//Obtener los hijos mediante id
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //Selecc
               model=dataSnapshot.getValue(ContactoModel.class);
               tv_detalle_nombre.setText(model.get_nombre());
               tv_detalle_numero.setText(model.get_numero());
               tv_detalle_contactoConfianza.setText(model.get_contactoConfianza());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });





    }

}
