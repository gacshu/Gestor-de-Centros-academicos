/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author JuanAlberto
 */
public class FaltasVO 
{
    private String  idEdi;  
    private String  idMod;
    private String  idAlu;
    private Date    fecha;
    private boolean justificada;

    public FaltasVO()
    {
        idEdi       = "";
        idMod       = "";
        idAlu       = "";
        fecha       = new GregorianCalendar(1900,0,1).getTime();
        justificada = false;
    }

    /**
     * @return the idEdi
     */
    public String getIdEdi() {
        return idEdi;
    }

    /**
     * @param idEdi the idEdi to set
     */
    public void setIdEdi(String idEdi) {
        this.idEdi = idEdi;
    }

    /**
     * @return the idMod
     */
    public String getIdMod() {
        return idMod;
    }

    /**
     * @param idMod the idMod to set
     */
    public void setIdMod(String idMod) {
        this.idMod = idMod;
    }

    /**
     * @return the idAlu
     */
    public String getIdAlu() {
        return idAlu;
    }

    /**
     * @param idAlu the idAlu to set
     */
    public void setIdAlu(String idAlu) {
        this.idAlu = idAlu;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the justificada
     */
    public boolean isJustificada() {
        return justificada;
    }

    /**
     * @param justificada the justificada to set
     */
    public void setJustificada(boolean justificada) {
        this.justificada = justificada;
    }
    
    
    public String devolverClave()
    {
        return idEdi + idMod + idAlu + new SimpleDateFormat("ddMMyyyy").format(fecha); 
    }
            

}
