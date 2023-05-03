/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Alberto
 */
public class CursosAluVO
{
    private String idAlu;
    private String idCur;
    private String idNiv;
    private Date   fecha;

    public CursosAluVO()
    {
        idAlu = "";
        idCur = "";
        idNiv = "";
        fecha = new GregorianCalendar(1900,0,1).getTime();
    }

    /**
     * @return the idAlu
     */
    public String getIdAlu()
    {
        return idAlu;
    }

    /**
     * @param idAlu the idAlu to set
     */
    public void setIdAlu(String idAlu)
    {
        this.idAlu = idAlu;
    }

    /**
     * @return the idCur
     */
    public String getIdCur() {
        return idCur;
    }

    /**
     * @param idCur the idCur to set
     */
    public void setIdCur(String idCur)
    {
        this.idCur = idCur;
    }

    /**
     * @return the idNiv
     */
    public String getIdNiv()
    {
        return idNiv;
    }

    /**
     * @param idNiv the idNiv to set
     */
    public void setIdNiv(String idNiv)
    {
        this.idNiv = idNiv;
    }

    /**
     * @return the fecha
     */
    public Date getFecha()
    {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

}
