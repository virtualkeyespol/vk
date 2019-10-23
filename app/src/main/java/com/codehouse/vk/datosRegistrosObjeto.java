package com.codehouse.vk;

import java.util.List;

public class datosRegistrosObjeto {
    private List<registroObjeto> RESPUESTA;
    private String STATUS;

    public List<registroObjeto> getRESPUESTA() {
        return RESPUESTA;
    }

    public void setRESPUESTA(List<registroObjeto> RESPUESTA) {
        this.RESPUESTA = RESPUESTA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
