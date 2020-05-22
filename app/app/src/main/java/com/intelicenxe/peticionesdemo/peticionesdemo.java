package com.intelicenxe.peticionesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.devbinar.peticiones.AutoClasificar;
import com.devbinar.peticiones.Funciones;
import com.devbinar.peticiones.ParteDatos;
import com.devbinar.peticiones.Peticion;
import com.devbinar.peticionesdemo.R;

public class peticionesdemo extends AppCompatActivity {
    EditText url,parametros,log,data;
    Button seleccionar_imagen,enviar;
    Spinner protocolo;
    CheckBox autoclasificar;
    ImageView vista_previa,imagen_cargada;
    Bitmap imagen_seleccionada = null;
    ProgressBar carga_imagen;
    AutoClasificar autoClasificar = null;
    private static final int SELECT_FILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticionesdemo);
        url = findViewById(R.id.ap_tv_url);
        parametros = findViewById(R.id.ap_tv_parametros_post);
        seleccionar_imagen = findViewById(R.id.ap_btn_seleccionar_imagen);
        vista_previa = findViewById(R.id.ap_iv_vista_previa_imagen);
        protocolo = findViewById(R.id.ap_sp_protocolo);
        enviar = findViewById(R.id.ap_btn_enviar);
        log = findViewById(R.id.ap_tv_log);
        data = findViewById(R.id.ap_tv_data);
        imagen_cargada = findViewById(R.id.ap_iv_vista_imagen_cargada);
        carga_imagen = findViewById(R.id.ap_pb_carga_imagen);
        autoclasificar = findViewById(R.id.ap_cb_autoclasificar);
        seleccionar_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log.setText("");
                data.setText("");
                imagen_cargada.setImageResource(android.R.drawable.ic_menu_gallery);
                if(autoclasificar.isChecked()){
                    autoClasificar = new Autoclasificar();
                }else{
                    autoClasificar = null;
                }
                switch (protocolo.getSelectedItemPosition()){
                    case 0: {
                        Peticion.GET(peticionesdemo.this, url.getText().toString(), autoClasificar,0,new Peticion.EventoPeticion() {
                            @Override
                            public void Respuesta(String respuesta, int codigo_peticion) {
                                data.setText(respuesta);
                            }

                            @Override
                            public void Error(VolleyError error, int codigo_peticion) {
                                log.setText(error.toString());
                            }
                        },null);
                    }
                        break;
                    case 1: {
                        JSONObject map_parametros = new JSONObject();
                        String[] split_parametros = parametros.getText().toString().split("&");
                        if (split_parametros.length > 0 && parametros.getText().toString().length() > 0) {
                            for (int i = 0; i < split_parametros.length; i++) {
                                String[] split_parametro = split_parametros[i].split("=");
                                if(split_parametro.length > 1){
                                    try {
                                        map_parametros.put(split_parametro[0], split_parametro[1]);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Peticion.POST_JSON(peticionesdemo.this, url.getText().toString(),autoClasificar,0, map_parametros, new Peticion.EventoPeticion() {
                            @Override
                            public void Respuesta(String respuesta, int codigo_peticion) {
                                data.setText(respuesta);
                            }

                            @Override
                            public void Error(VolleyError error, int codigo_peticion) {
                                log.setText(error.toString());
                            }
                        }, null);
                    }
                        break;
                    case 2: {
                        Map<String, String> map_parametros = new HashMap<>();
                        String[] split_parametros = parametros.getText().toString().split("&");
                        if (split_parametros.length > 0 && parametros.getText().toString().length() > 0) {
                            for (int i = 0; i < split_parametros.length; i++) {
                                String[] split_parametro = split_parametros[i].split("=");
                                if(split_parametro.length > 1){
                                    map_parametros.put(split_parametro[0], split_parametro[1]);
                                }
                            }
                        }
                        Peticion.POST_FORM_URL_ENCODED(peticionesdemo.this, url.getText().toString(), autoClasificar,0,map_parametros, new Peticion.EventoPeticion() {
                            @Override
                            public void Respuesta(String respuesta, int codigo_peticion) {
                                data.setText(respuesta);
                            }

                            @Override
                            public void Error(VolleyError error, int codigo_peticion) {
                                log.setText(error.toString());
                            }
                        }, null);
                        break;
                    }
                    case 3:
                        Map<String, String> map_parametros = new HashMap<>();
                        String[] split_parametros = parametros.getText().toString().split("&");
                        if (split_parametros.length > 0 && parametros.getText().toString().length() > 0) {
                            for (int i = 0; i < split_parametros.length; i++) {
                                String[] split_parametro = split_parametros[i].split("=");
                                if(split_parametro.length > 1){
                                    map_parametros.put(split_parametro[0], split_parametro[1]);
                                }
                            }
                        }
                        Map<String, ParteDatos> archivos = new HashMap<>();
                        archivos.put("imagen_prueba",new ParteDatos("imagen_prueba", Funciones.Bitmap_to_byte(imagen_seleccionada),"image/png"));
                        Peticion.POST_MULTIPART_FORM_DATA(peticionesdemo.this, url.getText().toString(), autoClasificar,0,map_parametros, archivos,new Peticion.EventoPeticion() {
                            @Override
                            public void Respuesta(String respuesta, int codigo_peticion) {
                                data.setText(respuesta);
                            }

                            @Override
                            public void Error(VolleyError error, int codigo_peticion) {
                                log.setText(error.toString());
                            }
                        }, null);
                        break;
                    case 4:
                        Peticion.READ_IMAGE(peticionesdemo.this, url.getText().toString(), new Peticion.EventoReadImage() {
                            @Override
                            public void Start() {
                                carga_imagen.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void Downloaded(Bitmap resultado) {
                                carga_imagen.setVisibility(View.GONE);
                                imagen_cargada.setImageBitmap(resultado);
                            }

                            @Override
                            public void Error(String error) {
                                carga_imagen.setVisibility(View.GONE);
                                log.setText(error);
                            }
                        });
                        break;
                }
            }
        });
    }

    public void abrirGaleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), SELECT_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;
        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {
                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            imagen_seleccionada = BitmapFactory.decodeStream(imageStream);
                            vista_previa.setImageBitmap(imagen_seleccionada);
                        }
                    }
                }
                break;
        }
    }
}
