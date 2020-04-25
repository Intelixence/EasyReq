package com.intelixence.peticiones;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.android.volley.DefaultRetryPolicy;
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
import java.util.ArrayList;
import java.util.Map;

public class EasyReq {

    public interface Event {
        void Response(String response, int code_request);
        void Error(VolleyError error, int code_request);
    }

    public interface State {
        void Start();
        void End();
    }

    private static EasyReqLastRequest easyReqLastRequest = null;
    private static ArrayList<EasyReqLastRequest> historyEasyReqLastRequests = new ArrayList<>();
    private static boolean enabledHistoryRequest = false;

    public static void ExecuteLastRequest(){
        if (easyReqLastRequest != null) {
            switch (easyReqLastRequest.type) {
                case 1:
                    GET(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
                case 2:
                    POST_JSON(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getParameters_json(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
                case 3:
                    POST_FORM_URL_ENCODED(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getParameters_map(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
                case 4:
                    POST_MULTIPART_FORM_DATA(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getParameters_map(), easyReqLastRequest.getFiles(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
            }
        }
    }

    public static void enabledHistoryRequests(boolean enable){
        enabledHistoryRequest = enable;
    }

    public static void ExecuteHistoryRequests(EasyReqLastRequest easyReqLastRequest){
        if (easyReqLastRequest != null) {
            switch (easyReqLastRequest.type) {
                case 1:
                    GET(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
                case 2:
                    POST_JSON(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getParameters_json(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
                case 3:
                    POST_FORM_URL_ENCODED(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getParameters_map(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
                case 4:
                    POST_MULTIPART_FORM_DATA(easyReqLastRequest.getContext(), easyReqLastRequest.getUrl(), easyReqLastRequest.getEasyReqFilter(), easyReqLastRequest.getCode_request(), easyReqLastRequest.getParameters_map(), easyReqLastRequest.getFiles(), easyReqLastRequest.getEvent(), easyReqLastRequest.getState(), easyReqLastRequest.getTimeout());
                    break;
            }
        }
    }

    public static ArrayList<EasyReqLastRequest> getHistoryRequests() {
        return historyEasyReqLastRequests;
    }

    public static void clearHistoryRequests(){
        historyEasyReqLastRequests.clear();
    }

    private static void SaveLastRequest(int type, Context context, String url, EasyReqFilter easyReqFilter, int code_request, final JSONObject parameters_json, Map<String, String> parameters_map, Map<String, EasyReqFile> files, Event event, State state, int timeout){
        easyReqLastRequest = new EasyReqLastRequest(type, context, url, easyReqFilter, code_request, parameters_json, parameters_map, files, event, state, timeout);
        if (enabledHistoryRequest){
            historyEasyReqLastRequests.add(easyReqLastRequest);
        }
    }

    public static void GET(final Context context, String url, final EasyReqFilter easyReqFilter, final int code_request, final Event event, final State state, int timeout){
        SaveLastRequest(1, context, url, easyReqFilter, code_request, null, null, null, event, state, timeout);

        if(state != null){
            state.Start();
        }

        url = EasyReqFunctions.url_parse(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_response(context, response,code_request, event);
                    }else{
                        event.Response(response,code_request);
                    }
                }
                if(state != null){
                    state.End();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_error(context, error, code_request, event);
                    } else {
                        event.Error(error, code_request);
                    }
                }
                if(state != null){
                    state.End();
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, 0, 0));
        requestQueue.add(request);
    }

    public static void POST_JSON(final Context context, String url, final EasyReqFilter easyReqFilter, final int code_request, final JSONObject parameters, final Event event, final State state, int timeout){
        SaveLastRequest(2, context, url, easyReqFilter, code_request, parameters, null, null, event, state, timeout);

        if(state != null){
            state.Start();
        }

        url = EasyReqFunctions.url_parse(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_response(context, response,code_request, event);
                    } else {
                        event.Response(response,code_request);
                    }
                }
                if(state != null){
                    state.End();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_error(context, error, code_request, event);
                    } else {
                        event.Error(error, code_request);
                    }
                }
                if(state != null){
                    state.End();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return EasyReqContent.JSON;
            }

            @Override
            public byte[] getBody(){
                return parameters.toString().getBytes();
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, 0, 0));
        requestQueue.add(request);
    }

    public static void POST_FORM_URL_ENCODED(final Context context, String url, final EasyReqFilter easyReqFilter, final int code_request, final Map<String, String> parameters, final Event event, final State state, int timeout){
        SaveLastRequest(3, context, url, easyReqFilter, code_request, null, parameters, null, event, state, timeout);

        if(state != null){
            state.Start();
        }

        url = EasyReqFunctions.url_parse(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_response(context, response,code_request, event);
                    } else {
                        event.Response(response,code_request);
                    }
                }
                if(state != null){
                    state.End();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_error(context, error, code_request, event);
                    } else {
                        event.Error(error, code_request);
                    }
                }
                if(state != null){
                    state.End();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return EasyReqContent.FORM_URL_ENCODED;
            }

            @Override
            protected Map<String, String> getParams() {
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, 0, 0));
        requestQueue.add(request);
    }

    public static void POST_MULTIPART_FORM_DATA(final Context context, String url, final EasyReqFilter easyReqFilter, final int code_request, final Map<String, String> parameters, final Map<String, EasyReqFile> files, final Event event, final State state, int timeout){
        SaveLastRequest(4, context, url, easyReqFilter, code_request, null, parameters, files, event, state, timeout);

        if(state != null){
            state.Start();
        }

        EasyReqFunctions.new_limit();
        url = EasyReqFunctions.url_parse(url);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        Request<NetworkResponse> request = new Request<NetworkResponse>(Request.Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(event != null){
                    if(easyReqFilter != null){
                        easyReqFilter.Filter_error(context, error, code_request, event);
                    } else {
                        event.Error(error, code_request);
                    }
                }
                if(state != null){
                    state.End();
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
                    if(event != null){
                        if(easyReqFilter != null){
                            easyReqFilter.Filter_response(context, response,code_request, event);
                        } else {
                            event.Response(response,code_request);
                        }
                    }
                    if(state != null){
                        state.End();
                    }
                } catch (UnsupportedEncodingException e) {
                    VolleyError volleyError = new VolleyError(e);
                    Response.error(volleyError);
                }
            }

            @Override
            public String getBodyContentType() {
                return "multipart/form-data;boundary="+ EasyReqFunctions.limit;
            }

            @Override
            public byte[] getBody() {
                ByteArrayOutputStream arreglo_body = new ByteArrayOutputStream();
                DataOutputStream data_body = new DataOutputStream(arreglo_body);

                try {
                    if (parameters != null && parameters.size() > 0) {
                        EasyReqFunctions.analyze_text(data_body, parameters, getParamsEncoding());
                    }

                    if (files != null && files.size() > 0) {
                        EasyReqFunctions.analyze_file(data_body, files);
                    }
                    data_body.writeBytes(EasyReqFunctions.two_hyphen + EasyReqFunctions.limit + EasyReqFunctions.two_hyphen + EasyReqFunctions.end_line);
                    return arreglo_body.toByteArray();
                } catch (IOException e) {
                    VolleyError volleyError = new VolleyError(e);
                    Response.error(volleyError);
                }
                return null;
            }

            protected Map<String, EasyReqFile> getByteData() {
                return null;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, 0, 0));
        requestQueue.add(request);

        if(state != null){
            state.End();
        }
    }

    public interface EventReadImage {
        void Start();
        void Downloaded(Bitmap bitmap);
        void Error(String error);
    }

    public static void READ_IMAGE(final String url, final EventReadImage eventReadImage){
        if(eventReadImage != null) {
            eventReadImage.Start();
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
                    if(eventReadImage != null) {
                        eventReadImage.Error(e.getMessage());
                    }
                }
                return bmp;
            }

            protected void onPostExecute(Bitmap resultado) {
                if(resultado != null){
                    eventReadImage.Downloaded(resultado);
                }else{
                    if(eventReadImage != null) {
                        eventReadImage.Error("Imagen no encontrada");
                    }
                }
            }
        };
        asyncTask.execute();
    }
}
