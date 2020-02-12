package com.intelixence.peticiones;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class EasyReqFunctions {

    public static String dosguiones = "--";
    public static String finlinea = "\r\n";
    public static String limite = "apiclient-"+System.currentTimeMillis();
    public static Bitmap bitmap;

    public static String parsear_url(String url){
        String[] url_parametros = url.split("\\?");
        if(url_parametros.length > 1){
            String[] parametros = url_parametros[1].split("&");
            if(parametros.length > 0){
                String parametros_parse = "";
                for (int i = 0; i < parametros.length; i++){
                    if(i == 0){
                        String[] parametro_valor = parametros[i].split("=");
                        if(parametro_valor.length > 1){
                            parametros_parse = URLEncoder.encode(parametro_valor[0])+"="+URLEncoder.encode(parametro_valor[1]);
                        }else{
                            parametros_parse = URLEncoder.encode(parametro_valor[0])+"="+"";
                        }
                    }else{
                        String[] parametro_valor = parametros[i].split("=");
                        if(parametro_valor.length > 1){
                            parametros_parse = parametros_parse+"&"+URLEncoder.encode(parametro_valor[0])+"="+URLEncoder.encode(parametro_valor[1]);
                        }else{
                            parametros_parse = parametros_parse+"&"+URLEncoder.encode(parametro_valor[0])+"=";
                        }
                    }
                }
                return url_parametros[0]+"?"+parametros_parse;
            }else{
                return url;
            }
        }else{
            return url;
        }
    }

    public static void nuevo_limite(){
        limite = "apiclient-"+System.currentTimeMillis();
    }

    public static void AnalizarTexto(DataOutputStream dataOutputStream, Map<String, String> params, String encoding) throws IOException {
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                TextoMultiparte(dataOutputStream, entry.getKey(), entry.getValue());
            }
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + encoding, uee);
        }
    }

    public static void AnalizarArchivo(DataOutputStream dataOutputStream, Map<String, EasyReqFile> data) throws IOException {
        for (Map.Entry<String, EasyReqFile> entry : data.entrySet()) {
            ArchivosMultiparte(dataOutputStream, entry.getValue(), entry.getKey());
        }
    }

    public static void TextoMultiparte(DataOutputStream dataOutputStream, String nombre_parametro, String valor_parametro) throws IOException {
        dataOutputStream.writeBytes(dosguiones+limite+finlinea);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\""+nombre_parametro+"\""+finlinea);
        dataOutputStream.writeBytes(finlinea);
        dataOutputStream.writeBytes(valor_parametro+finlinea);
    }

    public static void ArchivosMultiparte(DataOutputStream dataOutputStream, EasyReqFile easyReqFile, String inputName) throws IOException {
        dataOutputStream.writeBytes(dosguiones + limite + finlinea);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                inputName + "\"; filename=\"" + easyReqFile.getNombre_archivo() + "\"" + finlinea);
        if (easyReqFile.getTipo() != null && !easyReqFile.getTipo().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + easyReqFile.getTipo() + finlinea);
        }
        dataOutputStream.writeBytes(finlinea);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(easyReqFile.getContenido());
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(finlinea);
    }

    public static byte[] Bitmap_to_byte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
