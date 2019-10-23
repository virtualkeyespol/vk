package com.codehouse.vk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class dispositivoItem extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] dnombre;
    private final String[] destado;
    private final String[] dserie;

    //private final Integer[] imgid;

    public dispositivoItem(Activity context, String[] dnombre, String[] destado, String[] dserie) {
        super(context, R.layout.item_dispositivos, dnombre);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.dnombre = dnombre;
        this.destado = destado; //propietario
        //this.subtitle=subtitle;
        //this.imgid=imgid;

        this.dserie = dserie; //modelo
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_dispositivos, null,true);

        TextView titleText1 = (TextView) rowView.findViewById(R.id.disnombre);
        TextView titleText2 = (TextView) rowView.findViewById(R.id.dismodelo);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText1.setText(dnombre[position]);
        titleText2.setText(dserie[position]);
        //imageView.setImageResource(imgid[position]);
        //subtitleText.setText(subtitle[position]);

        return rowView;

    };

}
