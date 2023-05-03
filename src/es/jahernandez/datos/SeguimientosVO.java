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
public class SeguimientosVO
{
    private String codSeg;
    private String idAlu;
    private String idCur;
    private String incidencias;
    private Date   fecha;
    private String usuario;

    public SeguimientosVO()
    {
        //
        // TODO: Agregar aquí la lógica del constructor
        //

        codSeg      = "";
        idAlu       = "";
        idCur       = "";
        incidencias = "";
        fecha       = new GregorianCalendar(1900,0,1).getTime();
        usuario     = "";
    }

    /**
     * @return the codSeg
     */
    public String getCodSeg()
    {
        return codSeg;
    }

    /**
     * @param codSeg the codSeg to set
     */
    public void setCodSeg(String codSeg)
    {
        this.codSeg = codSeg;
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
    public String getIdCur()
    {
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
     * @return the incidencias
     */
    public String getIncidencias()
    {
        return incidencias;
    }

    /**
     * @param incidencias the incidencias to set
     */
    public void setIncidencias(String incidencias)
    {
        this.incidencias = incidencias;
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

    /**
     * @return the usuario
     */
    public String getUsuario()
    {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }
}
