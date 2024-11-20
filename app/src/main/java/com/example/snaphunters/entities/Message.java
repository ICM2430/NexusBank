package com.example.snaphunters.entities;

public class Message {
    private String mensaje;
    private String enviadoPor;
    private String recibidoPor;

    public Message(String mensaje, String enviadoPor, String recibidoPor) {
        this.mensaje = mensaje;
        this.enviadoPor = enviadoPor;
        this.recibidoPor = recibidoPor;
    }
    public Message(){

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEnviadoPor() {
        return enviadoPor;
    }

    public void setEnviadoPor(String enviadoPor) {
        this.enviadoPor = enviadoPor;
    }

    public String getRecibidoPor() {
        return recibidoPor;
    }

    public void setRecibidoPor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }
}
