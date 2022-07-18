package com.aplicacion.pm2e2grupo5;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aplicacion.pm2e2grupo5.Configuration.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnContactos, btnGuardar, btnGps;

    static final int REQUESTCAMERA = 100;
    static final int TAKEPHOTO = 101;

    ImageView ObjectImage;
    String currentPhotoPath;

    Message mensaje;

    List<Message> MessageList;
    EditText nombre, telefono;
    public EditText latitud, longitud;

    public LocationManager ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContactos = (Button) findViewById(R.id.btnLista);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGps = (Button) findViewById(R.id.btnGPS);

        MessageList = new ArrayList<>();

        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        latitud = (EditText) findViewById(R.id.txtLatitud);
        longitud = (EditText) findViewById(R.id.txtLongitud);

//        ObjectImage = (ImageView) findViewById(R.id.lienzo);

        localizacion(); //LLAMAR AL METODO LOCALIZAR UBICACION

        //BOTON GUARDAR
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  CrearContacto();
            }
        });


        //BOTON CONTACTO
        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityLista.class);
                //startActivity(intent);

            }
        });

        //BOTON GPS
        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

            }
        });
    }

    public void localizacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },1001);
        }

        latitud=(EditText) findViewById(R.id.txtLatitud);
        longitud=(EditText) findViewById(R.id.txtLongitud);

        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location local = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(ubicacion != null){
            Log.d("Latitud",String.valueOf(local.getLatitude()));
            Log.d("Longitud",String.valueOf(local.getLongitude()));

            latitud.setText(String.valueOf(local.getLatitude()));
            longitud.setText(String.valueOf(local.getLongitude()));
        }
    }

    public static String ImageToBase64(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    private void CrearContacto() {
        JSONObject JSONEmple;
        String PostUrl = Configuration.Endpoint_get_all_contacts;

        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombre", nombre.getText().toString());
        parametros.put("telefono", telefono.getText().toString());
        parametros.put("latitud", latitud.getText().toString());
        parametros.put("longitud", longitud.getText().toString());
        parametros.put("firma", ImageToBase64(currentPhotoPath));

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JSONEmple = new JSONObject(parametros);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, PostUrl, JSONEmple, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i<= jsonArray.length(); i++)
                    {
                        JSONObject msg = jsonArray.getJSONObject(i);

                        if(msg.getString(getResources().getString(R.string.codigo)).equals(getResources().getString(R.string.StatusSuccess)))
                        {
                            mensaje = new Message();


                            MessageList.add(mensaje);
                        }//FIN IF

                        if(MessageList.size() > 0)
                        {
                            final AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());

                            //alerta.setTitle(MessageList.get(0).Message);
                            alerta.setIcon(R.mipmap.ic_launcher);
                            alerta.setPositiveButton(R.string.StatusSuccessText, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            alerta.show();
                        }//FIN IF
                    }//FIN FOR

                } catch (JSONException e) {

                    e.printStackTrace();

                }//FIN TRY CATCH

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



    }//FIN METODO CREAR



}//FIN