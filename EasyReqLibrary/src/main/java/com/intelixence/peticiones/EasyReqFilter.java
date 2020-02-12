package com.intelixence.peticiones;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EasyReqFilter {

    /**
     * utiliza extends en tu clase java
     * la funcion de esta clase es la autogestion de respuestas o errores para evitar codigo repetitivo y desarrollo agil
     * reduce la brecha de errores
     */

    //ej arreglo: {"arreglo":"1"}{"arreglo":"2"}{"respuesta":"0"}
    //respuesta 0 = accion
    //respuesta 1 = no se puedo realizar la accion
    //respuesta 2 = parametros incompletos
    public void Gestionar_respuesta(Context contexto, String response, int codigo_peticion, EasyReq.EventoPeticion eventoPeticion){
        try {
            //Concatenamos en un arreglo el objeto response
            JSONArray datos = new JSONObject(response).getJSONArray("datos");
            //obtenemos la ultima respuesta, tener en cuenta que el tamaño(lenght) del arreglo es 3 y los arreglos comienzan en 0
            //asi que debemos restarle 1 y nos dara 2, [0] = {"arreglo":"1"}, [1] = {"arreglo":"2"}, [2] = {"respuesta":"0"} y obtendriamos la respuesta
            JSONObject respuesta = datos.getJSONObject(datos.length()-1);
            switch (respuesta.getString("respuesta")){
                case "0":
                    //esto se te retornara en la clase donde hagas la peticion
                    eventoPeticion.Respuesta(response, codigo_peticion);
                    break;
                case "1":
                    //Mostramos un mensaje cada vez que no se realize algo
                    Toast.makeText(contexto,"No se realizo", Toast.LENGTH_LONG);
                    break;
                case "2":
                    //Mostramos un texto cada vez que los parametros esten incompletos
                    Toast.makeText(contexto,"Parametros incompletos", Toast.LENGTH_LONG);
                    break;
            }
        } catch (JSONException e) {
            //error en proceso con manejo de json se puede quitar con un throws JSONException
        }
    }

    //verificamos que tenga activada la red y que tenga internet
    public void Gestionar_error(Context contexto, VolleyError volleyError, int codigo_peticion, EasyReq.EventoPeticion eventoPeticion){
        if (EasyReqNet.habilitado(contexto)){
            if (EasyReqNet.acceso_internet(contexto)){
                //la peticion no llega a el servidor
                Toast.makeText(contexto,"Sin respuesta del servidor", Toast.LENGTH_LONG);
            }else{
                //sin internet
                Toast.makeText(contexto,"Sin acceso a internet", Toast.LENGTH_LONG);
            }
        }else{
            //wifi o datos deshabilitados
            Toast.makeText(contexto,"Activa el wifi o datos", Toast.LENGTH_LONG);
        }
    }
}
