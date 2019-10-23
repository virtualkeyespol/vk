package com.codehouse.vk;

import java.util.List;

public class datosDispositivosObjeto {
    private List<dispositivoObjecto> RESPUESTA;
    private String STATUS;

    public String getSTATUS() {
        return STATUS;
    }

    public List<dispositivoObjecto> getRESPUESTA() {
        return RESPUESTA;
    }

    public void setRESPUESTA(List<dispositivoObjecto> RESPUESTA) {
        this.RESPUESTA = RESPUESTA;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

}
