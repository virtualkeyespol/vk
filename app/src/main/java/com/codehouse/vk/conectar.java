package com.codehouse.vk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class conectar extends AppCompatActivity {

    String token;
    String url;
    ImageButton btnEnviar;
    TextView tll;

    //Identificador de servicio
    //private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket;
    BluetoothDevice device;

    //Si se apreta una vez el boton de conectar
    boolean estado = false;

    //Handler es un control para mensajes
    Handler bluetoothIn;

    //Estado del manejador
    final int handlerState = 0;

    String llave;
    String nombre_dispositivo;
    String mac;
    //Esto es simplemente un String normal a diferencia que al agregar una sentancia en un bucle se agrega los espacios automaticamente
//for(hasta 20 veces)
//String cadena += " " + "Dato" ---> En un string normal se debe crear el espacio y luego agregar el dato
//Con esto se traduce a = DataStringIN.append(dato);
    private StringBuilder DataStringIN = new StringBuilder();
    //Llama a la sub- clase y llamara los metodos que se encuentran dentro de esta clase
    ConexionThread MyConexionBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar);
        btnEnviar = (ImageButton)findViewById(R.id.btnenviar);
        tll = (TextView)findViewById(R.id.tll);

        llave = getIntent().getStringExtra("llave");
        nombre_dispositivo = getIntent().getStringExtra("nombre_dispositivo");
        mac = getIntent().getStringExtra("mac");
        tll.setText(nombre_dispositivo);

        ////////////////Manejador de mensajes y llamara al metodo Run///////////////////////////////
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "Dato Recibido Entero: " + readMessage, Toast.LENGTH_SHORT).show();
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        //   Toast.makeText(MainActivity.this, "Dato Recibido: " +dataInPrint, Toast.LENGTH_SHORT).show();
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }

        };
        ///////////////////////////////////////////////////

        //BOTON ENVIAR
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btAdapter = BluetoothAdapter.getDefaultAdapter();
                //Direccion mac del dispositivo a conectar
                device = btAdapter.getRemoteDevice(mac);
                //System.out.println("DISPOSITIVO " + device.getName());
                btSocket = null;
                final UUID SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

                try {
                    //Crea el socket sino esta conectado
                    if (!estado) {
                        //btSocket = createBluetoothSocket(device);
                        btAdapter.cancelDiscovery();

                        //device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID);
                        //btSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"));
                        btSocket = device.createRfcommSocketToServiceRecord(SERIAL_UUID);
                        estado = btSocket.isConnected();
                        //System.out.println("Se conecto con exito");
                        //Toast.makeText(getBaseContext(), "Se conecto con exito", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    //System.out.println("La creacción del Socket fallo");
                    //Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
                }

                // Establece la conexión con el socket Bluetooth.
                try {
                    //Realiza la conexion si no se a hecho
                    if (!estado) {
                        btAdapter.cancelDiscovery();
                        btSocket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                        btSocket.connect();
                        estado = true;
                        MyConexionBT = new ConexionThread(btSocket);
                        MyConexionBT.start();
                        //Toast.makeText(getApplicationContext(), "Conexion Realizada Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "Ya esta vinculado", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    try {
                        //Toast.makeText(getApplicationContext(), "Error:", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        btSocket.close();
                    } catch (IOException e2) { }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                if (estado) {
                    String dato = llave;
                    dato += "#";
                    MyConexionBT.write(dato);
                    Toast.makeText(getApplicationContext(), "Llave enviada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Vincule su dispositivo", Toast.LENGTH_SHORT).show();
                }

            }
        });




        //BOTON CONECTAR

        /*btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });*/

    }


    //Crea el socket
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }


    //Se debe crear una sub-clase para tambien heredar los metodos de CompaActivity y Thread juntos
//Ademas  en Run se debe ejecutar el subproceso(interrupcion)
    private class ConexionThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConexionThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            while (true) {
                // Se mantiene en modo escucha para determinar el ingreso de datos
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //Enviar los datos
        public void write(String input) {
            try {
                mmOutStream.write(input.getBytes());
            } catch (IOException e) {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();
            }
        }


    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN", "NO EXISTE");
        url = preferences.getString("URL", "NONE");

    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar);

        btn_conectar = findViewById(R.id.conectar);
        codDispositivo = findViewById(R.id.visorllave);

        btn_conectar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Codigo de Conexion Bluetooth
                cargarPreferencias();

                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("TOKEN", token);
                jsonObject.addProperty("CODIGO", getIntent().getExtras().getString("llave"));

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService postService = retrofit.create(apiService.class);


                Call<sesionObjeto> call = postService.abrir(jsonObject);

                call.enqueue(new Callback<sesionObjeto>() {
                    @Override
                    public void onResponse(Call<sesionObjeto> call, Response<sesionObjeto> response) {
                        System.out.println(response.body().getRESPUESTA());
                        if(response.body().getSTATUS().equals("OK")){
                            //loading_loggin.setVisibility(View.VISIBLE);
                            //et_user.setEnabled(false);
                            //et_password.setEnabled(false);
                            //btn_loggin.setEnabled(false);
                            Toast.makeText(getApplicationContext(), response.body().getRESPUESTA(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getRESPUESTA(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<sesionObjeto> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
                    }

                });


                Toast.makeText(getApplicationContext(), "Abierto", Toast.LENGTH_SHORT).show();
            }
        });
        }*/




    }



