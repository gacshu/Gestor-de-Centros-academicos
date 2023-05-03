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
public class HisRecVO
{
    private String  idEdi;
    private String  idAlu;
    private String  numRec;
    private Date    fecExp;
    private boolean pagado;
    private String  idCur;



    public HisRecVO()
    {
        idEdi  = "";
        idAlu  = "";
        numRec = "";
        fecExp = new GregorianCalendar(1900,0,1).getTime();
        pagado = false;
        idCur  = "";

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
     * @return the numRec
     */
    public String getNumRec()
    {
        return numRec;
    }

    /**
     * @param numRec the numRec to set
     */
    public void setNumRec(String numRec)
    {
        this.numRec = numRec;
    }

    /**
     * @return the fecExp
     */
    public Date getFecExp()
    {
        return fecExp;
    }

    /**
     * @param fecExp the fecExp to set
     */
    public void setFecExp(Date fecExp)
    {
        this.fecExp = fecExp;
    }

    /**
     * @return the pagado
     */
    public boolean isPagado()
    {
        return pagado;
    }

    /**
     * @param pagado the pagado to set
     */
    public void setPagado(boolean pagado)
    {
        this.pagado = pagado;
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
}
