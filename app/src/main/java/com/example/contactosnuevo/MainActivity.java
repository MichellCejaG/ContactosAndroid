package com.example.contactosnuevo;

import android.content.Intent;
import android.os.Bundle;

import com.example.contactosnuevo.adapter.ContactoAdapter;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ListView lv_main_contactos;
   private FloatingActionButton fab_main_nuevo;
   private ArrayList <ContactoModel> list;
   private ContactoModel model;



    private final String text_reference="contactos";
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference reference= database.getReference(text_reference);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        fab_main_nuevo=findViewById(R.id.fab_main_nuevo);
        lv_main_contactos=findViewById(R.id.lv_main_contactos);
        list = new ArrayList<>();
        model = new ContactoModel();




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//Se ejecuta cuando algo cambia en los hijos,nietos o si hay problemas
                list = new ArrayList<>();                                   /*Todo lo que se recibe de firebase siempre es DataSnapshot*/
                for(DataSnapshot child: dataSnapshot.getChildren()){ //Se recorre el DataSnapshot y se encuentra otro DataSnapshot pero como hijo
                    model=child.getValue(ContactoModel.class);  //Los hijos que recibe los convierte en ContactoModel
                    list.add(model);
                }
                lv_main_contactos.setAdapter(new ContactoAdapter(MainActivity.this, list));
                //A traves de setAdapter se llama a ContactoAdapter para tener listo el evento de ListView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error en Firebase", Toast.LENGTH_SHORT).show();

            }
        });

        lv_main_contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                model= (ContactoModel) adapterView.getItemAtPosition(i);
                Intent detalle = new Intent(MainActivity.this,DetalleActivity.class);
                detalle.putExtra("id",model.get_id());
                startActivity(detalle);
            }
        });


        fab_main_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(nuevo);//Vista para registrar un contacto
            }
        });






       /* final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        //arrayAdapter.addAll();
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner.setAdapter(ArrayAdapter<String>(getApplicationContext()));
            }
        });*/


    }



}
