package com.devbinar.peticiones;

public class EasyReqFile {

    private String name_file;
    private byte[] content;
    private String type;

    public EasyReqFile(String name_file, byte[] content, String type) {
        this.name_file = name_file;
        this.content = content;
        this.type = type;
    }

    public String getName_file() {
        return name_file;
    }

    public byte[] getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
