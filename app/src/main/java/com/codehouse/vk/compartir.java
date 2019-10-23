package com.codehouse.vk;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class compartir extends AppCompatActivity {

    EditText et_correo;
    String fin;
    String inicio;
    CheckBox unicouso;
    String multiuso;

    String in;
    String out;


    TextView titulo;
    Button btn_compartir;
    String id_dispositivo;
    String nombre_dispositivo;
    ConstraintLayout cl1;
    String token;
    String url;
    private int mYear,mMonth,mDay;
    private int mYear2,mMonth2,mDay2;

    private void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN","NO EXISTE");
        url = preferences.getString("URL","NONE");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);
        cargarPreferencias();

        id_dispositivo = getIntent().getStringExtra("id");
        nombre_dispositivo = getIntent().getStringExtra("dispositivo");

        et_correo = (EditText) findViewById(R.id.correocompartir);
        btn_compartir = (Button) findViewById(R.id.compartirbtn);

        titulo = (TextView) findViewById(R.id.titulo);
        cl1=  (ConstraintLayout) findViewById(R.id.cl1);
        titulo.setText(nombre_dispositivo);

        multiuso = "true";

        unicouso = (CheckBox) findViewById(R.id.uso);
        unicouso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unicouso.isChecked()){
                    multiuso = "false";
                    Toast.makeText(compartir.this, "Solo se podra utilizar una vez", Toast.LENGTH_SHORT).show();
                }else{
                    multiuso = "true";
                    Toast.makeText(compartir.this, "Se podra utilizar siempre", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final ImageButton pickDate = (ImageButton) findViewById(R.id.pick_date);
        final TextView textView = (TextView) findViewById(R.id.date);

        final TextView textView2 = (TextView) findViewById(R.id.date2);

        textView2.setEnabled(false);



        textView.setEnabled(false);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                textView.setText(sdf.format(myCalendar.getTime()));
                inicio = sdf.format(myCalendar.getTime());
            }


        };

        pickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(compartir.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear,mMonth,mDay);

                                textView.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });


    final Calendar myCalendar2 = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            // myCalendar.add(Calendar.DATE, 0);
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            textView2.setText(sdf.format(myCalendar2.getTime()));
            fin = sdf.format(myCalendar2.getTime());
        }
    };

    final ImageButton pickDate2 = (ImageButton) findViewById(R.id.pick_date2);

        pickDate2.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            final Calendar c2 = Calendar.getInstance();
            mYear2 = c2.get(Calendar.YEAR);
            mMonth2 = c2.get(Calendar.MONTH);
            mDay2 = c2.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(compartir.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox

                            if (year < mYear2)
                                view.updateDate(mYear2,mMonth2,mDay2);

                            if (monthOfYear < mMonth2 && year == mYear2)
                                view.updateDate(mYear2,mMonth2,mDay2);

                            if (dayOfMonth < mDay2 && year == mYear2 && monthOfYear == mMonth2)
                                view.updateDate(mYear2,mMonth2,mDay2);

                            textView2.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();

        }
    });

        btn_compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cl1.setVisibility(View.VISIBLE);


                try {

                    //  Block of code to try
                    String[] parts = textView.getText().toString().split("-");
                    in = parts[2] + "-" + parts[1] + "-" + parts[0] ;

                    String[] parts2 = textView2.getText().toString().split("-");
                    out = parts2[2] + "-" +  parts2[1] + "-" + parts2[0] ;

                }
                catch(Exception e) {
                    in = "";
                    out = "";
                }


                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("TOKEN", token);
                jsonObject.addProperty("CORREO", et_correo.getText().toString());
                jsonObject.addProperty("DISPOSITIVO_ID", Integer.parseInt(id_dispositivo));
                jsonObject.addProperty("FECHA_INICIO", in);
                jsonObject.addProperty("FECHA_EXPIRACION",out);
                jsonObject.addProperty("MULTIUSO", multiuso);

                final String result = jsonObject.toString();
                System.out.println(result);

                //String a = "{ 'USUARIO': " + et_user.getText().toString() + ", 'CONTRASENA': " + et_password.getText().toString() + "}";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService postService = retrofit.create(apiService.class);


                Call<statusObjeto> call = postService.compartir(jsonObject);

                call.enqueue(new Callback<statusObjeto>() {
                    @Override
                    public void onResponse(Call<statusObjeto> call, Response<statusObjeto> response) {
                        if(response.body().getSTATUS().equals("OK")){
                            cl1.setVisibility(View.GONE);
                            //loading_loggin.setVisibility(View.VISIBLE);
                            //et_user.setEnabled(false);
                            //et_password.setEnabled(false);
                            //btn_loggin.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Se ha creado con exito", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),menuPrincipal.class);
                            startActivity(i);
                            finish();
                        } else {
                            cl1.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Existe un error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<statusObjeto> call, Throwable t) {
                        cl1.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });

}}
