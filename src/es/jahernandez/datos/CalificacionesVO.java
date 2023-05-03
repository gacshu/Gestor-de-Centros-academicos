/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author JuanAlberto
 */
public class CalificacionesVO 
{
    private String idEdi;  
    private String idMod;
    private String idAlu;
    private int    evaluacion;
    private Date   fecha;
    private int    nota;
  
    public CalificacionesVO()
    {
        idEdi       = "";  
        idMod       = "";
        idAlu       = "";
        evaluacion  = -1;
        fecha       = new GregorianCalendar(1900,0,1).getTime();
        nota        = -1;
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
     * @return the evaluacion
     */
    public int getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
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
     * @return the nota
     */
    public int getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(int nota) {
        this.nota = nota;
    }
    
    public String devolverClave()
    {
        return idEdi + idMod + idAlu + new DecimalFormat("00").format(evaluacion);
    }
}
