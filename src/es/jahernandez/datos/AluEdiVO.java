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
public class AluEdiVO
{
    private String   idAlu;
    private String   idEdi;
    private Date     fecAlta;
    private boolean  esBaja;
    private String   observ;
    private String   numCuenta;
    private boolean  esCong;


    public AluEdiVO()
    {
        idAlu       = "";
        idEdi       = "";
        fecAlta     = new GregorianCalendar(1900,0,1).getTime();
        esBaja      = false;
        observ      = "";
        numCuenta   = "";
        esCong      = false;
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
     * @return the idEdi
     */
    public String getIdEdi()
    {
        return idEdi;
    }

    /**
     * @param idEdi the idEdi to set
     */
    public void setIdEdi(String idEdi)
    {
        this.idEdi = idEdi;
    }

    /**
     * @return the fecAlta
     */
    public Date getFecAlta()
    {
        return fecAlta;
    }

    /**
     * @param fecAlta the fecAlta to set
     */
    public void setFecAlta(Date fecAlta)
    {
        this.fecAlta = fecAlta;
    }

    /**
     * @return the esBaja
     */
    public boolean isEsBaja()
    {
        return esBaja;
    }

    /**
     * @param esBaja the esBaja to set
     */
    public void setEsBaja(boolean esBaja)
    {
        this.esBaja = esBaja;
    }

    /**
     * @return the observ
     */
    public String getObserv()
    {
        return observ;
    }

    /**
     * @param observ the observ to set
     */
    public void setObserv(String observ)
    {
        this.observ = observ;
    }

    /**
     * @return the numCuenta
     */
    public String getNumCuenta()
    {
        return numCuenta;
    }

    /**
     * @param numCuenta the numCuenta to set
     */
    public void setNumCuenta(String numCuenta)
    {
        this.numCuenta = numCuenta;
    }

    /**
     * @return the esCong
     */
    public boolean isEsCong()
    {
        return esCong;
    }

    /**
     * @param esCong the esCong to set
     */
    public void setEsCong(boolean esCong)
    {
        this.esCong = esCong;
    }

}
