package com.codehouse.vk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class llaveItem extends ArrayAdapter<String> {
    private final String[] username;
    private final String[] first_name;
    private final String[] last_name;
    private final String[] modelo;
    private final String[] nombre;
    private final String[] fecha_expiracion;
    private final String[] codigo;
    private final Activity context;


//private final Integer[] imgid;

    public llaveItem(Activity context,
                     String[] username,
                     String[] first_name,
                     String[] last_name,
                     String[] modelo,
                     String[] nombre,
                     String[] fecha_expiracion,
                     String[] codigo) {
        super(context, R.layout.item_llaves, nombre);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.modelo = modelo;
        this.nombre = nombre;
        this.fecha_expiracion = fecha_expiracion;
        this.codigo = codigo;

        //this.subtitle=subtitle;
        //this.imgid=imgid
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_llaves, null,true);

        TextView titleText1 = (TextView) rowView.findViewById(R.id.llnombre);
        TextView titleText2 = (TextView) rowView.findViewById(R.id.dpropietario);
        TextView titleText3 = (TextView) rowView.findViewById(R.id.llestado);
        TextView titleText4 = (TextView) rowView.findViewById(R.id.llfecha);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText1.setText(nombre[position]);
        titleText2.setText("Propietario: " + first_name[position]);
        titleText3.setText("Modelo: " + modelo[position]);
        titleText4.setText("Válida hasta " + fecha_expiracion[position]);
        //imageView.setImageResource(imgid[position]);
        //subtitleText.setText(subtitle[position]);

        return rowView;

    };

}
