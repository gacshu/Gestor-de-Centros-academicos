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
public class EmpresasVO
{
    private String  idEmpresa;
    private String  nombreEmpresa;
    private String  cifEmpresa;
    private String  dirEmpresa;
    private String  codPostal;
    private String  pobEmpresa;
    private String  codProv;
    private String  telEmpresa;
    private String  faxEmpresa;
    private String  mailEmpresa;
    private String  resEmpresa;
    private int     codAct;
    private String  nomComercial;
    private int     cnae;
    private int     numEmp;
    private String  volNeg;
    private String  impExp;
    private String  car1;
    private String  car2;
    private String  resEmp2;
    private boolean convenioAd;
    private Date    fecCon;
    private boolean datAct;
    private int     cuota;
    private boolean esCliente;
    private String  dirCom;
    private String  pobCom;
    private String  proCom;
    private String  codPosCom;
    private boolean autCesDat;
    private boolean accArco;
    private Date    fecAccArc;
    private boolean recArco;
    private Date    fecRecArc;
    private Date    fecCanArc;
    private boolean canArco;
    private boolean opoArco;
    private Date    fecOpoArc;

    public EmpresasVO()
    {
        idEmpresa      = "";
        nombreEmpresa  = "";
        cifEmpresa     = "";
        dirEmpresa     = "";
        codPostal      = "";
        pobEmpresa     = "";
        codProv        = "";
        telEmpresa     = "";
        faxEmpresa     = "";
        mailEmpresa    = "";
        resEmpresa     = "";
        codAct         = -1;
        nomComercial   = "";
        cnae           = 0;
        numEmp         = 0;
        volNeg         = "";
        impExp         = "";
        car1           = "";
        car2           = "";
        resEmp2        = "";
        convenioAd     = false;
        fecCon         = new GregorianCalendar(1900,0,1).getTime();
        datAct         = false;
        cuota          = 0;
        esCliente      = false;
        dirCom         = "";
        pobCom         = "";
        proCom         = "";
        codPosCom      = "";
        autCesDat      = false;
        accArco        = false;
        fecAccArc      = new GregorianCalendar(1900,0,1).getTime();
        recArco        = false;
        fecRecArc      = new GregorianCalendar(1900,0,1).getTime();
        fecCanArc      = new GregorianCalendar(1900,0,1).getTime();
        canArco        = false;
        opoArco        = false;
        fecOpoArc      = new GregorianCalendar(1900,0,1).getTime();
    }

    /**
     * @return the idEmpresa
     */
    public String getIdEmpresa()
    {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(String idEmpresa)
    {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the nombreEmpresa
     */
    public String getNombreEmpresa()
    {
        return nombreEmpresa;
    }

    /**
     * @param nombreEmpresa the nombreEmpresa to set
     */
    public void setNombreEmpresa(String nombreEmpresa)
    {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * @return the cifEmpresa
     */
    public String getCifEmpresa()
    {
        return cifEmpresa;
    }

    /**
     * @param cifEmpresa the cifEmpresa to set
     */
    public void setCifEmpresa(String cifEmpresa)
    {
        this.cifEmpresa = cifEmpresa;
    }

    /**
     * @return the dirEmpresa
     */
    public String getDirEmpresa()
    {
        return dirEmpresa;
    }

    /**
     * @param dirEmpresa the dirEmpresa to set
     */
    public void setDirEmpresa(String dirEmpresa)
    {
        this.dirEmpresa = dirEmpresa;
    }

    /**
     * @return the codPostal
     */
    public String getCodPostal()
    {
        return codPostal;
    }

    /**
     * @param codPostal the codPostal to set
     */
    public void setCodPostal(String codPostal)
    {
        this.codPostal = codPostal;
    }

    /**
     * @return the pobEmpresa
     */
    public String getPobEmpresa()
    {
        return pobEmpresa;
    }

    /**
     * @param pobEmpresa the pobEmpresa to set
     */
    public void setPobEmpresa(String pobEmpresa)
    {
        this.pobEmpresa = pobEmpresa;
    }

    /**
     * @return the codProv
     */
    public String getCodProv()
    {
        return codProv;
    }

    /**
     * @param codProv the codProv to set
     */
    public void setCodProv(String codProv)
    {
        this.codProv = codProv;
    }

    /**
     * @return the telEmpresa
     */
    public String getTelEmpresa()
    {
        return telEmpresa;
    }

    /**
     * @param telEmpresa the telEmpresa to set
     */
    public void setTelEmpresa(String telEmpresa)
    {
        this.telEmpresa = telEmpresa;
    }

    /**
     * @return the faxEmpresa
     */
    public String getFaxEmpresa()
    {
        return faxEmpresa;
    }

    /**
     * @param faxEmpresa the faxEmpresa to set
     */
    public void setFaxEmpresa(String faxEmpresa)
    {
        this.faxEmpresa = faxEmpresa;
    }

    /**
     * @return the mailEmpresa
     */
    public String getMailEmpresa()
    {
        return mailEmpresa;
    }

    /**
     * @param mailEmpresa the mailEmpresa to set
     */
    public void setMailEmpresa(String mailEmpresa)
    {
        this.mailEmpresa = mailEmpresa;
    }

    /**
     * @return the resEmpresa
     */
    public String getResEmpresa()
    {
        return resEmpresa;
    }

    /**
     * @param resEmpresa the resEmpresa to set
     */
    public void setResEmpresa(String resEmpresa)
    {
        this.resEmpresa = resEmpresa;
    }

    /**
     * @return the codAct
     */
    public int getCodAct()
    {
        return codAct;
    }

    /**
     * @param codAct the codAct to set
     */
    public void setCodAct(int codAct)
    {
        this.codAct = codAct;
    }

    /**
     * @return the nomComercial
     */
    public String getNomComercial()
    {
        return nomComercial;
    }

    /**
     * @param nomComercial the nomComercial to set
     */
    public void setNomComercial(String nomComercial)
    {
        this.nomComercial = nomComercial;
    }

    /**
     * @return the cnae
     */
    public int getCnae()
    {
        return cnae;
    }

    /**
     * @param cnae the cnae to set
     */
    public void setCnae(int cnae)
    {
        this.cnae = cnae;
    }

    /**
     * @return the numEmp
     */
    public int getNumEmp()
    {
        return numEmp;
    }

    /**
     * @param numEmp the numEmp to set
     */
    public void setNumEmp(int numEmp)
    {
        this.numEmp = numEmp;
    }

    /**
     * @return the volNeg
     */
    public String getVolNeg()
    {
        return volNeg;
    }

    /**
     * @param volNeg the volNeg to set
     */
    public void setVolNeg(String volNeg)
    {
        this.volNeg = volNeg;
    }

    /**
     * @return the impExp
     */
    public String getImpExp()
    {
        return impExp;
    }

    /**
     * @param impExp the impExp to set
     */
    public void setImpExp(String impExp)
    {
        this.impExp = impExp;
    }

    /**
     * @return the car1
     */
    public String getCar1()
    {
        return car1;
    }

    /**
     * @param car1 the car1 to set
     */
    public void setCar1(String car1)
    {
        this.car1 = car1;
    }

    /**
     * @return the car2
     */
    public String getCar2()
    {
        return car2;
    }

    /**
     * @param car2 the car2 to set
     */
    public void setCar2(String car2)
    {
        this.car2 = car2;
    }

    /**
     * @return the resEmp2
     */
    public String getResEmp2()
    {
        return resEmp2;
    }

    /**
     * @param resEmp2 the resEmp2 to set
     */
    public void setResEmp2(String resEmp2)
    {
        this.resEmp2 = resEmp2;
    }

    /**
     * @return the convenioAd
     */
    public boolean isConvenioAd()
    {
        return convenioAd;
    }

    /**
     * @param convenioAd the convenioAd to set
     */
    public void setConvenioAd(boolean convenioAd)
    {
        this.convenioAd = convenioAd;
    }

    /**
     * @return the fecCon
     */
    public Date getFecCon()
    {
        return fecCon;
    }

    /**
     * @param fecCon the fecCon to set
     */
    public void setFecCon(Date fecCon)
    {
        this.fecCon = fecCon;
    }

    /**
     * @return the datAct
     */
    public boolean isDatAct()
    {
        return datAct;
    }

    /**
     * @param datAct the datAct to set
     */
    public void setDatAct(boolean datAct)
    {
        this.datAct = datAct;
    }

    /**
     * @return the cuota
     */
    public int getCuota()
    {
        return cuota;
    }

    /**
     * @param cuota the cuota to set
     */
    public void setCuota(int cuota)
    {
        this.cuota = cuota;
    }

    /**
     * @return the esCliente
     */
    public boolean isEsCliente()
    {
        return esCliente;
    }

    /**
     * @param esCliente the esCliente to set
     */
    public void setEsCliente(boolean esCliente)
    {
        this.esCliente = esCliente;
    }

    /**
     * @return the dirCom
     */
    public String getDirCom()
    {
        return dirCom;
    }

    /**
     * @param dirCom the dirCom to set
     */
    public void setDirCom(String dirCom)
    {
        this.dirCom = dirCom;
    }

    /**
     * @return the pobCom
     */
    public String getPobCom()
    {
        return pobCom;
    }

    /**
     * @param pobCom the pobCom to set
     */
    public void setPobCom(String pobCom)
    {
        this.pobCom = pobCom;
    }

    /**
     * @return the proCom
     */
    public String getProCom()
    {
        return proCom;
    }

    /**
     * @param proCom the proCom to set
     */
    public void setProCom(String proCom)
    {
        this.proCom = proCom;
    }

    /**
     * @return the codPosCom
     */
    public String getCodPosCom() {
        return codPosCom;
    }

    /**
     * @param codPosCom the codPosCom to set
     */
    public void setCodPosCom(String codPosCom)
    {
        this.codPosCom = codPosCom;
    }

    /**
     * @return the autCesDat
     */
    public boolean isAutCesDat()
    {
        return autCesDat;
    }

    /**
     * @param autCesDat the autCesDat to set
     */
    public void setAutCesDat(boolean autCesDat)
    {
        this.autCesDat = autCesDat;
    }

    /**
     * @return the accArco
     */
    public boolean isAccArco()
    {
        return accArco;
    }

    /**
     * @param accArco the accArco to set
     */
    public void setAccArco(boolean accArco)
    {
        this.accArco = accArco;
    }

    /**
     * @return the fecAccArc
     */
    public Date getFecAccArc()
    {
        return fecAccArc;
    }

    /**
     * @param fecAccArc the fecAccArc to set
     */
    public void setFecAccArc(Date fecAccArc)
    {
        this.fecAccArc = fecAccArc;
    }

    /**
     * @return the recArco
     */
    public boolean isRecArco()
    {
        return recArco;
    }

    /**
     * @param recArco the recArco to set
     */
    public void setRecArco(boolean recArco)
    {
        this.recArco = recArco;
    }

    /**
     * @return the fecRecArc
     */
    public Date getFecRecArc()
    {
        return fecRecArc;
    }

    /**
     * @param fecRecArc the fecRecArc to set
     */
    public void setFecRecArc(Date fecRecArc)
    {
        this.fecRecArc = fecRecArc;
    }

    /**
     * @return the fecCanArc
     */
    public Date getFecCanArc()
    {
        return fecCanArc;
    }

    /**
     * @param fecCanArc the fecCanArc to set
     */
    public void setFecCanArc(Date fecCanArc)
    {
        this.fecCanArc = fecCanArc;
    }

    /**
     * @return the canArco
     */
    public boolean isCanArco()
    {
        return canArco;
    }

    /**
     * @param canArco the canArco to set
     */
    public void setCanArco(boolean canArco)
    {
        this.canArco = canArco;
    }

    /**
     * @return the opoArco
     */
    public boolean isOpoArco()
    {
        return opoArco;
    }

    /**
     * @param opoArco the opoArco to set
     */
    public void setOpoArco(boolean opoArco)
    {
        this.opoArco = opoArco;
    }

    /**
     * @return the fecOpoArc
     */
    public Date getFecOpoArc()
    {
        return fecOpoArc;
    }

    /**
     * @param fecOpoArc the fecOpoArc to set
     */
    public void setFecOpoArc(Date fecOpoArc)
    {
        this.fecOpoArc = fecOpoArc;
    }


}
