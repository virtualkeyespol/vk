package com.codehouse.vk;

public class registroObjeto {
    private String dispositivo_id;
    private String nombre_dispositivo;
    private String modelo;
    private String propietario_nombre;
    private String propietario_username;
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDispositivo_id() {
        return dispositivo_id;
    }

    public void setDispositivo_id(String dispositivo_id) {
        this.dispositivo_id = dispositivo_id;
    }

    public String getNombre_dispositivo() {
        return nombre_dispositivo;
    }

    public void setNombre_dispositivo(String nombre_dispositivo) {
        this.nombre_dispositivo = nombre_dispositivo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPropietario_nombre() {
        return propietario_nombre;
    }

    public void setPropietario_nombre(String propietario_nombre) {
        this.propietario_nombre = propietario_nombre;
    }

    public String getPropietario_username() {
        return propietario_username;
    }

    public void setPropietario_username(String propietario_username) {
        this.propietario_username = propietario_username;
    }
}
