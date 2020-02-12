package com.intelixence.peticiones;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class EasyReq {

    public interface EventoPeticion{
        void Respuesta(String respuesta, int codigo_peticion);
        void Error(VolleyError error, int codigo_peticion);
    }

    public interface EstadoPeticion{
        void Iniciada();
        void Terminada();
    }

    public static void GET(final Context contexto, String url, final EasyReqFilter autoclasificar, final int codigo_peticion, final EventoPeticion eventoPeticion, final EstadoPeticion estadoPeticion){
        if(estadoPeticion != null){
            estadoPeticion.Iniciada();
        }

        url = EasyReqFunctions.parsear_url(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(contexto);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_respuesta(contexto, response,codigo_peticion, eventoPeticion);
                    }else{
                        eventoPeticion.Respuesta(response,codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_error(contexto, error, codigo_peticion, eventoPeticion);
                    } else {
                        eventoPeticion.Error(error, codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        });
        requestQueue.add(request);
    }

    public static void POST_JSON(final Context contexto, String url, final EasyReqFilter autoclasificar, final int codigo_peticion, final JSONObject parametros, final EventoPeticion eventoPeticion, final EstadoPeticion estadoPeticion){
        if(estadoPeticion != null){
            estadoPeticion.Iniciada();
        }

        url = EasyReqFunctions.parsear_url(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(contexto);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_respuesta(contexto, response,codigo_peticion, eventoPeticion);
                    } else {
                        eventoPeticion.Respuesta(response,codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_error(contexto, error, codigo_peticion, eventoPeticion);
                    } else {
                        eventoPeticion.Error(error, codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return EasyReqContent.JSON;
            }

            @Override
            public byte[] getBody(){
                return parametros.toString().getBytes();
            }
        };
        requestQueue.add(request);
    }

    public static void POST_FORM_URL_ENCODED(final Context contexto, String url, final EasyReqFilter autoclasificar, final int codigo_peticion, final Map<String, String> parametros, final EventoPeticion eventoPeticion, final EstadoPeticion estadoPeticion){
        if(estadoPeticion != null){
            estadoPeticion.Iniciada();
        }

        url = EasyReqFunctions.parsear_url(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(contexto);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_respuesta(contexto, response,codigo_peticion, eventoPeticion);
                    } else {
                        eventoPeticion.Respuesta(response,codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_error(contexto, error, codigo_peticion, eventoPeticion);
                    } else {
                        eventoPeticion.Error(error, codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return EasyReqContent.FORM_URL_ENCODED;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public static void POST_MULTIPART_FORM_DATA(final Context contexto, String url, final EasyReqFilter autoclasificar, final int codigo_peticion, final Map<String, String> parametros, final Map<String, EasyReqFile> archivos, final EventoPeticion eventoPeticion, final EstadoPeticion estadoPeticion){
        if(estadoPeticion != null){
            estadoPeticion.Iniciada();
        }

        EasyReqFunctions.nuevo_limite();
        url = EasyReqFunctions.parsear_url(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(contexto);
        Request<NetworkResponse> request = new Request<NetworkResponse>(Request.Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(eventoPeticion != null){
                    if(autoclasificar != null){
                        autoclasificar.Gestionar_error(contexto, error, codigo_peticion, eventoPeticion);
                    } else {
                        eventoPeticion.Error(error, codigo_peticion);
                    }
                }
                if(estadoPeticion != null){
                    estadoPeticion.Terminada();
                }
            }
        }) {
            @Override
            protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
                try {
                    return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(NetworkResponse networkResponse) {
                try {
                    String response = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                    if(eventoPeticion != null){
                        if(autoclasificar != null){
                            autoclasificar.Gestionar_respuesta(contexto, response,codigo_peticion, eventoPeticion);
                        } else {
                            eventoPeticion.Respuesta(response,codigo_peticion);
                        }
                    }
                    if(estadoPeticion != null){
                        estadoPeticion.Terminada();
                    }
                } catch (UnsupportedEncodingException e) {
                    VolleyError volleyError = new VolleyError(e);
                    Response.error(volleyError);
                }
            }

            @Override
            public String getBodyContentType() {
                return "multipart/form-data;boundary="+ EasyReqFunctions.limite;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                ByteArrayOutputStream arreglo_body = new ByteArrayOutputStream();
                DataOutputStream data_body = new DataOutputStream(arreglo_body);

                try {
                    if (parametros != null && parametros.size() > 0) {
                        EasyReqFunctions.AnalizarTexto(data_body, parametros, getParamsEncoding());
                    }

                    if (archivos != null && archivos.size() > 0) {
                        EasyReqFunctions.AnalizarArchivo(data_body, archivos);
                    }
                    data_body.writeBytes(EasyReqFunctions.dosguiones+ EasyReqFunctions.limite+ EasyReqFunctions.dosguiones+ EasyReqFunctions.finlinea);
                    return arreglo_body.toByteArray();
                } catch (IOException e) {
                    VolleyError volleyError = new VolleyError(e);
                    Response.error(volleyError);
                }
                return null;
            }

            protected Map<String, EasyReqFile> getByteData() throws AuthFailureError {
                return null;
            }
        };
        requestQueue.add(request);

        if(estadoPeticion != null){
            estadoPeticion.Terminada();
        }
    }

    public interface EventoReadImage{
        void Start();
        void Downloaded(Bitmap resultado);
        void Error(String error);
    }

    public static void READ_IMAGE(final Context contexto, final String url, final EventoReadImage eventoReadImage){
        if(eventoReadImage != null) {
            eventoReadImage.Start();
        }
        EasyReqFunctions.bitmap = null;
        AsyncTask<String,Void,Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bmp = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    if(eventoReadImage != null) {
                        eventoReadImage.Error(e.getMessage());
                    }
                }
                return bmp;
            }

            protected void onPostExecute(Bitmap resultado) {
                if(resultado != null){
                    eventoReadImage.Downloaded(resultado);
                }else{
                    if(eventoReadImage != null) {
                        eventoReadImage.Error("Imagen no encontrada");
                    }
                }
            }
        };
        asyncTask.execute();
    }
}
