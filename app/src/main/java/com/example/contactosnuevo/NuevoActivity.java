package com.example.contactosnuevo;

import android.content.Intent;
import android.os.Bundle;

import com.example.contactosnuevo.adapter.ContactoAdapter;
import com.example.contactosnuevo.models.ContactoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NuevoActivity extends AppCompatActivity {

    private EditText et_nuevo_nombre, et_nuevo_numero,sp_contacto_confianza;
    private FloatingActionButton fab_nuevo;

    private ContactoModel model;
    private Spinner spinnerContactos;
    private ArrayList <ContactoModel> list;



    //Referencia para la base da datos y creacion de la tabla
    private final String text_reference="contactos";

    //Conexion a database
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference reference= database.getReference(text_reference);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = findViewById(R.id.toolbar_nuevo);
        setSupportActionBar(toolbar);

        //Llamada de variables
        et_nuevo_nombre=findViewById(R.id.et_nuevo_nombre);
        et_nuevo_numero=findViewById(R.id.et_nuevo_numero);
        fab_nuevo=findViewById(R.id.fab_nuevo);

        model = new ContactoModel();
        list = new ArrayList<>();
        model = new ContactoModel();


        spinnerContactos = findViewById(R.id.spinner_contactos); //Spinner



        //Funcion del botón flotante
        fab_nuevo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               //Captura de informacion

               String nombre = et_nuevo_nombre.getText().toString();
               String numero = et_nuevo_numero.getText().toString();
               String contactoConfianza= spinnerContactos.getSelectedItem().toString();

                //Validar que los campos no esten vacíos
               if(!nombre.equals("")&& !numero.equals("")){
                    String id= reference.push().getKey(); //Llave vacía con id unico y pide el id

                    if(id!=null){
                        model=new ContactoModel(id,nombre,numero,contactoConfianza);
                        //A traves de child se situa en el id y en setValue manda el objeto model para guardar datos
                        reference.child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent lista = new Intent(NuevoActivity.this,MainActivity.class);
                                startActivity(lista);//Manda la lista al activity principal
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(view, "La informacion no se puede guardar",Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    }else{
                        Snackbar.make(view, "Problema al crear ID en base de datos",Snackbar.LENGTH_LONG).show();

                    }

               }else{
                   Toast.makeText(NuevoActivity.this,"Ingrese todos los datos", Toast.LENGTH_SHORT).show();
               }
           }
       }



       );
localContactos();
    }
    public void localContactos(){
        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        model=child.getValue(ContactoModel.class);
                        list.add(model);
                    }
                    spinnerContactos.setAdapter(new ContactoAdapter(NuevoActivity.this,list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
