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
public class EdiModProfAulaVO 
{
    private String  IdEdi; 
    private String  IdMod; 
    private String  IdProf; 
    private String  IdAul; 
    private Date    fecIni; 
    private Date    fecFin; 
    private String  horIni; 
    private String  horFin; 
    private boolean hayLun;
    private boolean hayMar;
    private boolean hayMie;
    private boolean hayJue;
    private boolean hayVie;
    private boolean haySab;
    
    public EdiModProfAulaVO() 
    {
        IdEdi  = ""; 
        IdMod  = ""; 
        IdProf = "";
        IdAul  = ""; 
        fecIni = new Date(); 
        fecFin = new Date();
        horIni = ""; 
        horFin = "";        
    }

    /**
     * @return the IdEdi
     */
    public String getIdEdi() {
        return IdEdi;
    }

    /**
     * @param IdEdi the IdEdi to set
     */
    public void setIdEdi(String IdEdi) {
        this.IdEdi = IdEdi;
    }

    /**
     * @return the IdMod
     */
    public String getIdMod() {
        return IdMod;
    }

    /**
     * @param IdMod the IdMod to set
     */
    public void setIdMod(String IdMod) {
        this.IdMod = IdMod;
    }

    /**
     * @return the IdProf
     */
    public String getIdProf() {
        return IdProf;
    }

    /**
     * @param IdProf the IdProf to set
     */
    public void setIdProf(String IdProf) {
        this.IdProf = IdProf;
    }

    /**
     * @return the IdAul
     */
    public String getIdAul() {
        return IdAul;
    }

    /**
     * @param IdAul the IdAul to set
     */
    public void setIdAul(String IdAul) {
        this.IdAul = IdAul;
    }

    /**
     * @return the fecIni
     */
    public Date getFecIni() {
        return fecIni;
    }

    /**
     * @param fecIni the fecIni to set
     */
    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    /**
     * @return the fecFin
     */
    public Date getFecFin() {
        return fecFin;
    }

    /**
     * @param fecFin the fecFin to set
     */
    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    /**
     * @return the horIni
     */
    public String getHorIni() {
        return horIni;
    }

    /**
     * @param horIni the horIni to set
     */
    public void setHorIni(String horIni) {
        this.horIni = horIni;
    }

    /**
     * @return the horFin
     */
    public String getHorFin() {
        return horFin;
    }

    /**
     * @param horFin the horFin to set
     */
    public void setHorFin(String horFin) {
        this.horFin = horFin;
    }

    /**
     * @return the hayLun
     */
    public boolean isHayLun() {
        return hayLun;
    }

    /**
     * @param hayLun the hayLun to set
     */
    public void setHayLun(boolean hayLun) {
        this.hayLun = hayLun;
    }

    /**
     * @return the hayMar
     */
    public boolean isHayMar() {
        return hayMar;
    }

    /**
     * @param hayMar the hayMar to set
     */
    public void setHayMar(boolean hayMar) {
        this.hayMar = hayMar;
    }

    /**
     * @return the hayMie
     */
    public boolean isHayMie() {
        return hayMie;
    }

    /**
     * @param hayMie the hayMie to set
     */
    public void setHayMie(boolean hayMie) {
        this.hayMie = hayMie;
    }

    /**
     * @return the hayJue
     */
    public boolean isHayJue() {
        return hayJue;
    }

    /**
     * @param hayJue the hayJue to set
     */
    public void setHayJue(boolean hayJue) {
        this.hayJue = hayJue;
    }

    /**
     * @return the hayVie
     */
    public boolean isHayVie() {
        return hayVie;
    }

    /**
     * @param hayVie the hayVie to set
     */
    public void setHayVie(boolean hayVie) {
        this.hayVie = hayVie;
    }

    /**
     * @return the haySab
     */
    public boolean isHaySab() {
        return haySab;
    }

    /**
     * @param haySab the haySab to set
     */
    public void setHaySab(boolean haySab) {
        this.haySab = haySab;
    }
    
    public String devuelveDiasClase()
    {
        String strDatCur = "";
        if (isHayLun()) strDatCur = strDatCur + "L ";
        if (isHayMar()) strDatCur = strDatCur + "M ";
        if (isHayMie()) strDatCur = strDatCur + "X ";
        if (isHayJue()) strDatCur = strDatCur + "J ";
        if (isHayVie()) strDatCur = strDatCur + "V ";
        if (isHaySab()) strDatCur = strDatCur + "S ";
        
        return strDatCur;
        
    }
    
}
