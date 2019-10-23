package com.codehouse.vk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class registroItem extends ArrayAdapter<String> {
    private final Activity context;

    private final String[] nombre_dispositivo;
    private final String[] modelo;
    private final String[] propietario_nombre;
    private final String[] fecha;

    public registroItem(Activity context,
                        String[] nombre_dispositivo,
                        String[] modelo,
                        String[] propietario_nombre,
                        String[] fecha) {
        super(context, R.layout.item_registros, nombre_dispositivo);
        // TODO Auto-generated constructor stub

        this.context=context;

        this.nombre_dispositivo = nombre_dispositivo;
        this.modelo = modelo;
        this.propietario_nombre = propietario_nombre;

        this.fecha = fecha;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_registros, null,true);

        TextView titleText1 = (TextView) rowView.findViewById(R.id.drnombre);
        TextView titleText2 = (TextView) rowView.findViewById(R.id.llpropietario);
        TextView titleText3 = (TextView) rowView.findViewById(R.id.dpropietario);
        TextView titleText4 = (TextView) rowView.findViewById(R.id.rfecha);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        titleText1.setText(nombre_dispositivo[position]);
        titleText2.setText(propietario_nombre[position]);
        titleText3.setText(modelo[position]);
        titleText4.setText(fecha[position]);
        //imageView.setImageResource(imgid[position]);
        //subtitleText.setText(subtitle[position]);
        return rowView;

    };

}