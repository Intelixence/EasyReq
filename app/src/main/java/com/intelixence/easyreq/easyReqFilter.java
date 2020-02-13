package com.intelixence.easyreq;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.intelixence.peticiones.EasyReqFilter;
import com.intelixence.peticiones.EasyReq;

public class easyReqFilter extends EasyReqFilter {

    @Override
    public void Filter_response(Context context, String response, int code_request, EasyReq.Event event) {
        Toast.makeText(context,"filter than do case of response:"+response,Toast.LENGTH_LONG).show();
        event.Response(response, code_request);
    }

    @Override
    public void Filter_error(Context context, VolleyError volleyError, int code_request, EasyReq.Event event) {
        Toast.makeText(context,"filter than do case of error:"+volleyError.toString(),Toast.LENGTH_LONG).show();
        event.Error(volleyError, code_request);
    }
}
