package com.aplicacion.pm2e2grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.aplicacion.pm2e2grupo5.Clases.Contacts;
import com.aplicacion.pm2e2grupo5.Configuration.Configuration;
import com.aplicacion.pm2e2grupo5.Screen2.AdapterList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {

    Button btnLAtras, btnEliminar, btnActualizar;
    ListView lista;
    EditText buscar;
    ArrayList<Contacts> contacts = new ArrayList<>();
    RequestQueue requestQueue;
    Contacts cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        init();
    }

    private void init(){
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnLAtras = findViewById(R.id.btnAtras);
        lista = findViewById(R.id.txtLLista);
        buscar = findViewById(R.id.txtBuscar);
        requestQueue = Volley.newRequestQueue(this);
        jsonRequest();
        try {
            lista.setAdapter(new AdapterList(this, contacts));
        } catch (Exception ex) {
            message(ex.getMessage());
        }
    }

    private void jsonRequest(){
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, Configuration.Endpoint_get_all_contacts, null,
                response -> {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objeto = response.getJSONObject(i);
                                cont = new Contacts();
                                cont.setId(objeto.getString("id"));
                                cont.setNombre(objeto.getString("nombre"));
                                cont.setTelefono(objeto.getString("telefono"));
                                cont.setLatitud(objeto.getString("latitude"));
                                cont.setLongitud(objeto.getString("longitude"));
                                cont.setFirma((byte[]) objeto.get("firma"));
                                contacts.add(cont);
                            } catch (JSONException e) {
                                message(e.getMessage());
                            }
                        }
                    }
                },
                error -> message(error.getMessage())
        );
        requestQueue.add(jsonRequest);
    }

    public void message(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}