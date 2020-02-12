package com.intelixence.easyreq;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.intelixence.peticiones.EasyReqFilter;
import com.intelixence.peticiones.EasyReq;

public class Autoclasificar extends EasyReqFilter {

    @Override
    public void Gestionar_respuesta(Context contexto, String response, int codigo_peticion, EasyReq.EventoPeticion eventoPeticion) {
        Toast.makeText(contexto,"Autoclasificar_respuesta: "+response,Toast.LENGTH_LONG).show();
        eventoPeticion.Respuesta(response,codigo_peticion);
    }

    @Override
    public void Gestionar_error(Context contexto, VolleyError volleyError, int codigo_peticion , EasyReq.EventoPeticion eventoPeticion) {
        Toast.makeText(contexto,"Autoclasificar_error: "+volleyError.toString(),Toast.LENGTH_LONG).show();
        eventoPeticion.Error(volleyError, codigo_peticion);
    }
}
