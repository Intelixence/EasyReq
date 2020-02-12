package com.intelixence.peticiones;

public class EasyReqFile {

    private String nombre_archivo;
    private byte[] contenido;
    private String tipo;

    public EasyReqFile(String nombre_archivo, byte[] contenido, String tipo) {
        this.nombre_archivo = nombre_archivo;
        this.contenido = contenido;
        this.tipo = tipo;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
