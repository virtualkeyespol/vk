package com.codehouse.vk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registro extends AppCompatActivity {
    String token;
    String url;
    EditText name;
    EditText lastname;
    EditText mail;
    EditText password;
    Button btn_registrar;
    ConstraintLayout loading_loggin;


    private void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN", "NONE");
        url = preferences.getString("URL","NONE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cargarPreferencias();

        name = findViewById(R.id.nombres);
        lastname = findViewById(R.id.apellidos);
        password = findViewById(R.id.clave);
        mail = findViewById(R.id.correocompartir);
        btn_registrar = findViewById(R.id.registrar);
        loading_loggin = findViewById(R.id.cl1);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarPreferencias();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("NOMBRES", name.getText().toString());
                jsonObject.addProperty("APELLIDOS", lastname.getText().toString());
                jsonObject.addProperty("CONTRASENA",password.getText().toString());
                jsonObject.addProperty("CORREO", mail.getText().toString());

                final String result = jsonObject.toString();
                System.out.println(result);

                //String a = "{ 'USUARIO': " + et_user.getText().toString() + ", 'CONTRASENA': " + et_password.getText().toString() + "}";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService postService = retrofit.create(apiService.class);


                Call<statusObjeto> call = postService.registrarme(jsonObject);

                call.enqueue(new Callback<statusObjeto>() {
                    @Override
                    public void onResponse(Call<statusObjeto> call, Response<statusObjeto> response) {

                        if(response.body().getSTATUS().equals("OK")){

                            Toast.makeText(getApplicationContext(), "Se registro con exito", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Existe un problema", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<statusObjeto> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
                    }

                });



            }
        });

    }
}
