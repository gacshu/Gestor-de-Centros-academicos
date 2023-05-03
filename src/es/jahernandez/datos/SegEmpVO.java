/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

import java.util.GregorianCalendar;
import java.util.Date;

/**
 *
 * @author Alberto
 */
public class SegEmpVO
{
        private String   codSeg;
        private String   idEmp;
        private String   incidencias;
        private Date     fecha;
        private String   usuario;


    public SegEmpVO()
    {
        codSeg      = "";
        idEmp       = "";
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
     * @return the idEmp
     */
    public String getIdEmp()
    {
        return idEmp;
    }

    /**
     * @param idEmp the idEmp to set
     */
    public void setIdEmp(String idEmp)
    {
        this.idEmp = idEmp;
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
