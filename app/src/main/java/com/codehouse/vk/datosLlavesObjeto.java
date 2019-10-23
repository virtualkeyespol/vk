package com.codehouse.vk;

import java.util.List;

public class datosLlavesObjeto {
    private List<llaveObjeto> RESPUESTA;

    public List<llaveObjeto> getRESPUESTA() {
        return RESPUESTA;
    }

    public void setRESPUESTA(List<llaveObjeto> RESPUESTA) {
        this.RESPUESTA = RESPUESTA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    private String STATUS;
}
