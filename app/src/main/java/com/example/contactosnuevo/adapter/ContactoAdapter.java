package com.example.contactosnuevo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contactosnuevo.R;
import com.example.contactosnuevo.models.ContactoModel;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {
    private final Context context;
    private ContactoModel model;
    private ArrayList <ContactoModel>list;

    public ContactoAdapter(Context context, ArrayList<ContactoModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView=view;
        if(view==null){
            //Crear una variable LayoutInflater (instancia para archivos xml)
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView =inflater.inflate(R.layout.item_contacto,viewGroup,false); //Busca el elemento que se acaba de crear e
                                                                                            // ViewGroup

        }

        TextView tv_item_contacto=itemView.findViewById(R.id.tv_item_contacto);
        model=list.get(i);//Obtiene la posicion del elemento de la lista
        tv_item_contacto.setText(model.get_nombre()); //Se le asigna el nombre al item de la posicion anterior
        return itemView;
    }
}
