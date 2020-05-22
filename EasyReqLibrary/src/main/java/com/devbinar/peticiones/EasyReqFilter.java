package com.devbinar.peticiones;

import android.content.Context;

import com.android.volley.VolleyError;

public class EasyReqFilter {

    //filter than do case of response
    public void Filter_response(Context context, String response, int code_request, EasyReq.Event event){

    }

    //filter than do case of error
    public void Filter_error(Context context, VolleyError volleyError, int code_request, EasyReq.Event event){

    }
}
