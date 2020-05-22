# Welcome to EasyReq 

This library is based in volley, is ideal for save up time of work and development, have 5 functions that are:

* GET

* POST_JSON

* POST_FORM_URL_ENCODED

* POST_MULTIPART_FORM_DATA

* READ_IMAGE


## Start

1. Add the dependencies a gradle that are:

* implementation 'com.devbinar.community:EasyReqLibrary:0.11'

* implementation 'com.android.volley:volley:1.1.1'

2. sync gradle

## Extends the class EasyReqFilter

This class is in charge of make filter general for all request, for example when the dispositive can't access to the server and verify if is problem of the dispositivo or server.

```
    public class CustomEasyReqFilter extends EasyReqFilter {
    
        //filter than do case of response
        public void Filter_response(Context context, String response, int code_request, EasyReq.Event event){

        }

        //filter than do case of error
        public void Filter_error(Context context, VolleyError volleyError, int code_request, EasyReq.Event event){

        }
    }
```

## Implementation
* Can management all request alone using new EasyReq.Event and EasyReq.State as a continuation:
    
```
    EasyReq.GET(context, url, CustomEasyReqFilter, 0, new EasyReq.Event() {
        @Override
        public void Response(String response, int code_request) {
            //response next to EasyReqFilter
        }

        @Override
        public void Error(VolleyError error, int code_request) {
            //error next to EasyReqFilter
        }
    }, new EasyReq.State() {
        @Override
        public void Start() {
            //example show progressbar
        }

        @Override
        public void End() {
            //example hide progressbar
        }
    }, 25000);
```
    
* Or use this and implements in your class.
    
```
    public class CustomClass implements EasyReq.Event, EasyReq.State {
    
        public void api_request(Context context, String url){
            EasyReq.GET(context, url, CustomEasyReqFilter, 0, this, this, 25000);
        }
        
        @Override
        public void Response(String response, int code_request) {
            //response next to EasyReqFilter
        }

        @Override
        public void Error(VolleyError error, int code_request) {
            //error next to EasyReqFilter
        }
        
        @Override
        public void Start() {
            //example show progressbar
        }

        @Override
        public void End() {
            //example hide progressbar
        }
    }
```
    
## Request with yours parameters

* GET:
    
    ```
    EasyReq.GET(Context context, String url, EasyReqFilter easyReqFilter, int code_request, Event event, State state, int timeout);
    ```
    
* POST JSON:
    
    ```
    JSONObject parameters = new JSONObject();
    parameters.put("param_1", "value");
    
    EasyReq.POST_JSON(Context context, String url, EasyReqFilter easyReqFilter, int code_request, JSONObject parameters, Event event, State state, int timeout);
    ```
    
* POST FORM URL ENCODED:
    
    ```
    Map<String, String> parameters = new HashMap<>();
    parameters.put("param_1", "value");
    
    EasyReq.POST_FORM_URL_ENCODED(Context context, String url, EasyReqFilter easyReqFilter, int code_request, Map<String, String> parameters, Event event, State state, int timeout);
    ```
    
* POST MULTIPART FORM DATA:
    
    ```
    Map<String, String> parameters = new HashMap<>();
    parameters.put("param_1", "value");
    
    Map<String, EasyReqFile> files = new HashMap<>();
    files.put("img_1", new EasyReqFile("img_1", EasyReqFunctions.bitmap_to_byte(bitmap), "image/png"));
    
    EasyReq.POST_MULTIPART_FORM_DATA(Context context, String url, EasyReqFilter easyReqFilter, int code_request, Map<String, String> parameters, Map<String, EasyReqFile> files, Event event, State state, int timeout);
    ```
    
* READ IMAGE:
    
    ```
    EasyReq.READ_IMAGE(String url, new EasyReq.EventReadImage() {
        @Override
        public void Start() {
            //example show progressbar
        }

        @Override
        public void Downloaded(Bitmap bitmap) {
            //example hide progressbar and show screen of request completed
        }

        @Override
        public void Error(String error) {
            //example hide progressbar and show modal of error
        }
    });
    ```
