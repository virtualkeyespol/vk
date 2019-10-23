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

// Los activities son los controladores
// share preferences para guardar sessiones

public class MainActivity extends AppCompatActivity {

    // Declaro mis variables segun los componentes
    EditText et_user;
    EditText et_password;
    Button btn_loggin;
    Button btn_registro;
    ConstraintLayout loading_loggin;
    String token;
    String url;

    private void guardarPreferencias(String token){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TOKEN", token);
        editor.putString("URL", "http://ec2-54-233-206-161.sa-east-1.compute.amazonaws.com:8000");
        editor.commit();
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN", "NONE");
        url = preferences.getString("URL","NONE");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guardarPreferencias("null");

        // Busco por id los componentes
        et_user = findViewById(R.id.user);
        et_password = findViewById(R.id.password);
        btn_loggin = findViewById(R.id.loggin);
        loading_loggin = findViewById(R.id.cl1);
        btn_registro = findViewById(R.id.registrarme);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z = new Intent(getApplicationContext(),registro.class);
                startActivity(z);
                //finish();
            }
        });



        btn_loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading_loggin.setVisibility(View.VISIBLE);
                et_user.setEnabled(false);
                et_password.setEnabled(false);
                btn_loggin.setEnabled(false);
                btn_registro.setEnabled(false);

                cargarPreferencias();

                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("USUARIO", et_user.getText().toString());
                jsonObject.addProperty("CONTRASENA", et_password.getText().toString());

                final String result = jsonObject.toString();
                System.out.println(result);

                //String a = "{ 'USUARIO': " + et_user.getText().toString() + ", 'CONTRASENA': " + et_password.getText().toString() + "}";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService postService = retrofit.create(apiService.class);
                Call<sesionObjeto> call = postService.request(jsonObject);

                call.enqueue(new Callback<sesionObjeto>() {
                    @Override
                    public void onResponse(Call<sesionObjeto> call, Response<sesionObjeto> response) {

                        System.out.println(response.body().getRESPUESTA());

                        if(response.body().getSTATUS().equals("OK")){

                            et_user.setEnabled(true);
                            et_password.setEnabled(true);
                            btn_loggin.setEnabled(true);
                            btn_registro.setEnabled(true);
                            loading_loggin.setVisibility(View.GONE);

                            //loading_loggin.setVisibility(View.VISIBLE);
                            //et_user.setEnabled(false);
                            //et_password.setEnabled(false);
                            //btn_loggin.setEnabled(false);
                            Toast.makeText(getApplicationContext(), response.body().getRESPUESTA(), Toast.LENGTH_SHORT).show();
                            guardarPreferencias(response.body().getRESPUESTA());
                            Intent i = new Intent(getApplicationContext(),menuPrincipal.class);
                            startActivity(i);
                            finish();
                        } else {

                            et_user.setEnabled(true);
                            et_password.setEnabled(true);
                            btn_loggin.setEnabled(true);
                            btn_registro.setEnabled(true);
                            loading_loggin.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), response.body().getRESPUESTA(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<sesionObjeto> call, Throwable t) {

                        et_user.setEnabled(true);
                        et_password.setEnabled(true);
                        btn_loggin.setEnabled(true);
                        btn_registro.setEnabled(true);
                        loading_loggin.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });





    }






}
