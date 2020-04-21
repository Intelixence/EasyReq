package com.intelixence.peticiones;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

public class EasyReqLastRequest {

    int type;
    private Context context;
    private String url;
    private EasyReqFilter easyReqFilter;
    private int code_request;
    private JSONObject parameters_json;
    private Map<String, String> parameters_map;
    private Map<String, EasyReqFile> files;
    private EasyReq.Event event;
    private EasyReq.State state;
    private int timeout;

    public EasyReqLastRequest(int type, Context context, String url, EasyReqFilter easyReqFilter, int code_request, JSONObject parameters_json, Map<String, String> parameters_map, Map<String, EasyReqFile> files, EasyReq.Event event, EasyReq.State state, int timeout) {
        this.type = type;
        this.context = context;
        this.url = url;
        this.easyReqFilter = easyReqFilter;
        this.code_request = code_request;
        this.parameters_json = parameters_json;
        this.parameters_map = parameters_map;
        this.files = files;
        this.event = event;
        this.state = state;
        this.timeout = timeout;
    }

    public int getType() {
        return type;
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public EasyReqFilter getEasyReqFilter() {
        return easyReqFilter;
    }

    public int getCode_request() {
        return code_request;
    }

    public JSONObject getParameters_json() {
        return parameters_json;
    }

    public Map<String, String> getParameters_map() {
        return parameters_map;
    }

    public Map<String, EasyReqFile> getFiles() {
        return files;
    }

    public EasyReq.Event getEvent() {
        return event;
    }

    public EasyReq.State getState() {
        return state;
    }

    public int getTimeout() {
        return timeout;
    }
}
