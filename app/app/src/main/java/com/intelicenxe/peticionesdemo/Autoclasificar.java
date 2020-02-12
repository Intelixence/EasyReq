package com.intelicenxe.peticionesdemo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.intelixence.peticiones.AutoClasificar;
import com.intelixence.peticiones.Peticion;

public class Autoclasificar extends AutoClasificar {

    @Override
    public void Gestionar_respuesta(Context contexto, String response, int codigo_peticion, Peticion.EventoPeticion eventoPeticion) {
        Toast.makeText(contexto,"Autoclasificar_respuesta: "+response,Toast.LENGTH_LONG).show();
        eventoPeticion.Respuesta(response,codigo_peticion);
    }

    @Override
    public void Gestionar_error(Context contexto, VolleyError volleyError, int codigo_peticion ,Peticion.EventoPeticion eventoPeticion) {
        Toast.makeText(contexto,"Autoclasificar_error: "+volleyError.toString(),Toast.LENGTH_LONG).show();
        eventoPeticion.Error(volleyError, codigo_peticion);
    }
}
