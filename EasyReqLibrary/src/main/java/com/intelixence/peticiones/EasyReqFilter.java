package com.intelixence.peticiones;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EasyReqFilter {

    //filter than do case of response
    public void Filter_response(Context context, String response, int code_request, EasyReq.Event event){

    }

    //filter than do case of error
    public void Filter_error(Context context, VolleyError volleyError, int code_request, EasyReq.Event event){

    }
}
