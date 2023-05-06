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
public class EdicionesVO
{
    private String   idEdi;
    private String   idCur;
    private String   idNiv;
    private Date     fecIn;
    private Date     fecFi;
    private int      numPla;
    private int      numHor;
    private int      horIn;
    private int      horFi;
    private double   precioM;
    private double   precioR;
    private double   precioT;
    private boolean  hayLun;
    private boolean  hayMar;
    private boolean  hayMie;
    private boolean  hayJue;
    private boolean  hayVie;
    private boolean  haySab;
    private int      codCen;
    private int      minIn;
    private int      minFin;
    private int      horDis;
    private int      horTelef;
    private String   idAula;
    private String   bonif;
    private boolean  plazos;
    private boolean  ene;
    private boolean  feb;
    private boolean  mar;
    private boolean  abr;
    private boolean  may;
    private boolean  jun;
    private boolean  jul;
    private boolean  ago;
    private boolean  sep;
    private boolean  oct;
    private boolean  nov;
    private boolean  dic;
    private String   aplaz;
    private String   descripcion;
    private String   idProf;



    public EdicionesVO()
    {
        idEdi      = "";
        idCur      = "";
        idNiv      = "";
        fecIn      = new GregorianCalendar(1900,0,1).getTime();
        fecFi      = new GregorianCalendar(1900,0,1).getTime();
        numPla     = 0;
        numHor     = 0;
        horIn      = 0;
        horFi      = 0;
        precioM    = 0;
        precioR    = 0;
        precioT    = 0;
        hayLun     = false;
        hayMar     = false;
        hayMie     = false;
        hayJue     = false;
        hayVie     = false;
        haySab     = false;
        codCen     = 0;
        minIn      = 0;
        minFin     = 0;
        horDis     = 0;
        horTelef   = 0;
        idAula     = "";
        bonif      = "";
        plazos     = false;
        ene        = false;
        feb        = false;
        mar        = false;
        abr        = false;
        may        = false;
        jun        = false;
        jul        = false;
        ago        = false;
        sep        = false;
        oct        = false;
        nov        = false;
        dic        = false;
        aplaz      = "";
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
     * @return the fecIn
     */
    public Date getFecIn()
    {
        return fecIn;
    }

    /**
     * @param fecIn the fecIn to set
     */
    public void setFecIn(Date fecIn)
    {
        this.fecIn = fecIn;
    }

    /**
     * @return the fecFi
     */
    public Date getFecFi()
    {
        return fecFi;
    }

    /**
     * @param fecFi the fecFi to set
     */
    public void setFecFi(Date fecFi)
    {
        this.fecFi = fecFi;
    }

    /**
     * @return the numPla
     */
    public int getNumPla()
    {
        return numPla;
    }

    /**
     * @param numPla the numPla to set
     */
    public void setNumPla(int numPla)
    {
        this.numPla = numPla;
    }

    /**
     * @return the numHor
     */
    public int getNumHor()
    {
        return numHor;
    }

    /**
     * @param numHor the numHor to set
     */
    public void setNumHor(int numHor)
    {
        this.numHor = numHor;
    }

    /**
     * @return the horIn
     */
    public int getHorIn()
    {
        return horIn;
    }

    /**
     * @param horIn the horIn to set
     */
    public void setHorIn(int horIn)
    {
        this.horIn = horIn;
    }

    /**
     * @return the horFi
     */
    public int getHorFi()
    {
        return horFi;
    }

    /**
     * @param horFi the horFi to set
     */
    public void setHorFi(int horFi)
    {
        this.horFi = horFi;
    }

    /**
     * @return the precioM
     */
    public double getPrecioM()
    {
        return precioM;
    }

    /**
     * @param precioM the precioM to set
     */
    public void setPrecioM(double precioM)
    {
        this.precioM = precioM;
    }

    /**
     * @return the precioR
     */
    public double getPrecioR()
    {
        return precioR;
    }

    /**
     * @param precioR the precioR to set
     */
    public void setPrecioR(double precioR)
    {
        this.precioR = precioR;
    }

    /**
     * @return the precioT
     */
    public double getPrecioT()
    {
        return precioT;
    }

    /**
     * @param precioT the precioT to set
     */
    public void setPrecioT(double precioT)
    {
        this.precioT = precioT;
    }

    /**
     * @return the hayLun
     */
    public boolean isHayLun()
    {
        return hayLun;
    }

    /**
     * @param hayLun the hayLun to set
     */
    public void setHayLun(boolean hayLun)
    {
        this.hayLun = hayLun;
    }

    /**
     * @return the hayMar
     */
    public boolean isHayMar()
    {
        return hayMar;
    }

    /**
     * @param hayMar the hayMar to set
     */
    public void setHayMar(boolean hayMar)
    {
        this.hayMar = hayMar;
    }

    /**
     * @return the hayMie
     */
    public boolean isHayMie()
    {
        return hayMie;
    }

    /**
     * @param hayMie the hayMie to set
     */
    public void setHayMie(boolean hayMie)
    {
        this.hayMie = hayMie;
    }

    /**
     * @return the hayJue
     */
    public boolean isHayJue()
    {
        return hayJue;
    }

    /**
     * @param hayJue the hayJue to set
     */
    public void setHayJue(boolean hayJue)
    {
        this.hayJue = hayJue;
    }

    /**
     * @return the hayVie
     */
    public boolean isHayVie()
    {
        return hayVie;
    }

    /**
     * @param hayVie the hayVie to set
     */
    public void setHayVie(boolean hayVie)
    {
        this.hayVie = hayVie;
    }

    /**
     * @return the haySab
     */
    public boolean isHaySab()
    {
        return haySab;
    }

    /**
     * @param haySab the haySab to set
     */
    public void setHaySab(boolean haySab)
    {
        this.haySab = haySab;
    }

    /**
     * @return the codCen
     */
    public int getCodCen()
    {
        return codCen;
    }

    /**
     * @param codCen the codCen to set
     */
    public void setCodCen(int codCen)
    {
        this.codCen = codCen;
    }

    /**
     * @return the minIn
     */
    public int getMinIn()
    {
        return minIn;
    }

    /**
     * @param minIn the minIn to set
     */
    public void setMinIn(int minIn)
    {
        this.minIn = minIn;
    }

    /**
     * @return the minFin
     */
    public int getMinFin()
    {
        return minFin;
    }

    /**
     * @param minFin the minFin to set
     */
    public void setMinFin(int minFin)
    {
        this.minFin = minFin;
    }

    /**
     * @return the horDis
     */
    public int getHorDis()
    {
        return horDis;
    }

    /**
     * @param horDis the horDis to set
     */
    public void setHorDis(int horDis)
    {
        this.horDis = horDis;
    }

    /**
     * @return the horTelef
     */
    public int getHorTelef()
    {
        return horTelef;
    }

    /**
     * @param horTelef the horTelef to set
     */
    public void setHorTelef(int horTelef)
    {
        this.horTelef = horTelef;
    }

    /**
     * @return the idAula
     */
    public String getIdAula()
    {
        return idAula;
    }

    /**
     * @param idAula the idAula to set
     */
    public void setIdAula(String idAula)
    {
        this.idAula = idAula;
    }

    /**
     * @return the bonif
     */
    public String getBonif()
    {
        return bonif;
    }

    /**
     * @param bonif the bonif to set
     */
    public void setBonif(String bonif)
    {
        this.bonif = bonif;
    }

    /**
     * @return the plazos
     */
    public boolean isPlazos()
    {
        return plazos;
    }

    /**
     * @param plazos the plazos to set
     */
    public void setPlazos(boolean plazos)
    {
        this.plazos = plazos;
    }

    /**
     * @return the ene
     */
    public boolean isEne()
    {
        return ene;
    }

    /**
     * @param ene the ene to set
     */
    public void setEne(boolean ene)
    {
        this.ene = ene;
    }

    /**
     * @return the feb
     */
    public boolean isFeb()
    {
        return feb;
    }

    /**
     * @param feb the feb to set
     */
    public void setFeb(boolean feb)
    {
        this.feb = feb;
    }

    /**
     * @return the mar
     */
    public boolean isMar()
    {
        return mar;
    }

    /**
     * @param mar the mar to set
     */
    public void setMar(boolean mar)
    {
        this.mar = mar;
    }

    /**
     * @return the abr
     */
    public boolean isAbr()
    {
        return abr;
    }

    /**
     * @param abr the abr to set
     */
    public void setAbr(boolean abr)
    {
        this.abr = abr;
    }

    /**
     * @return the may
     */
    public boolean isMay()
    {
        return may;
    }

    /**
     * @param may the may to set
     */
    public void setMay(boolean may)
    {
        this.may = may;
    }

    /**
     * @return the jun
     */
    public boolean isJun()
    {
        return jun;
    }

    /**
     * @param jun the jun to set
     */
    public void setJun(boolean jun)
    {
        this.jun = jun;
    }

    /**
     * @return the jul
     */
    public boolean isJul()
    {
        return jul;
    }

    /**
     * @param jul the jul to set
     */
    public void setJul(boolean jul)
    {
        this.jul = jul;
    }

    /**
     * @return the ago
     */
    public boolean isAgo()
    {
        return ago;
    }

    /**
     * @param ago the ago to set
     */
    public void setAgo(boolean ago)
    {
        this.ago = ago;
    }

    /**
     * @return the sep
     */
    public boolean isSep()
    {
        return sep;
    }

    /**
     * @param sep the sep to set
     */
    public void setSep(boolean sep)
    {
        this.sep = sep;
    }

    /**
     * @return the oct
     */
    public boolean isOct()
    {
        return oct;
    }

    /**
     * @param oct the oct to set
     */
    public void setOct(boolean oct)
    {
        this.oct = oct;
    }

    /**
     * @return the nov
     */
    public boolean isNov()
    {
        return nov;
    }

    /**
     * @param nov the nov to set
     */
    public void setNov(boolean nov)
    {
        this.nov = nov;
    }

    /**
     * @return the dic
     */
    public boolean isDic()
    {
        return dic;
    }

    /**
     * @param dic the dic to set
     */
    public void setDic(boolean dic)
    {
        this.dic = dic;
    }

    /**
     * @return the aplaz
     */
    public String getAplaz()
    {
        return aplaz;
    }

    /**
     * @param aplaz the aplaz to set
     */
    public void setAplaz(String aplaz)
    {
        this.aplaz = aplaz;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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


}
