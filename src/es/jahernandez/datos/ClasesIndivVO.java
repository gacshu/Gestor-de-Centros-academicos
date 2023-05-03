/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

import java.util.Date;

/**
 *
 * @author JuanAlberto
 */
public class ClasesIndivVO 
{
    private String idClaseInd;
    private String idCur;
    private String idAlu;
    private Date   fecClase;
    private String idProf;
    private float  tarifa;
    
    public ClasesIndivVO()
    {
        idClaseInd = "";
        idCur      = "";
        idAlu      = "";
        fecClase   = new Date();
        idProf     = "";
        tarifa     = 0f;
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
    public void setIdCur(String idCur) {
        this.idCur = idCur;
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
     * @return the fecClase
     */
    public Date getFecClase() {
        return fecClase;
    }

    /**
     * @param fecClase the fecClase to set
     */
    public void setFecClase(Date fecClase) {
        this.fecClase = fecClase;
    }

    /**
     * @return the idProf
     */
    public String getIdProf() {
        return idProf;
    }

    /**
     * @param idProf the idProf to set
     */
    public void setIdProf(String idProf) {
        this.idProf = idProf;
    }

    /**
     * @return the tarifa
     */
    public float getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa the tarifa to set
     */
    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * @return the idClaseInd
     */
    public String getIdClaseInd() {
        return idClaseInd;
    }

    /**
     * @param idClaseInd the idClaseInd to set
     */
    public void setIdClaseInd(String idClaseInd) {
        this.idClaseInd = idClaseInd;
    }

    
    
}
