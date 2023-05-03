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
public class AlumnosVO
{
    private String   idAlu;
    private int      tipDocAlu;
    private String   numDocAlu;
    private String   nombre;
    private String   ap1Alu;
    private String   ap2Alu;
    private String   movAlu;
    private String   fijAlu;
    private String   dirAlu;
    private String   codPosAlu;
    private String   localAlu;
    private int      nivForm;
    private String   email;
    private boolean  desemp;
    private String   empAlu;
    private String   intereses;
    private String   codProvAlu;
    private int      idCen;
    private Date     fecNac;
    private boolean  alND;
    private boolean  autCesDat;
    private boolean  autComCom;
    private String   responsable;
    private String   colegio;
    private String   curso;

    public AlumnosVO()
    {
        idAlu       = "";
        tipDocAlu   = 0;
        numDocAlu   = "";
        nombre      = "";
        ap1Alu      = "";
        ap2Alu      = "";
        movAlu      = "";
        fijAlu      = "";
        dirAlu      = "";
        codPosAlu   = "";
        localAlu    = "";
        nivForm     = 0;
        email       = "";
        desemp      = false;
        empAlu      = "";
        intereses   = "";
        codProvAlu  = "";
        idCen       = 0;
        fecNac      = new GregorianCalendar(1900,0,1).getTime();
        alND        = false;
        autCesDat   = false;
        autComCom   = false;
        responsable = "";
        colegio     = "";
        curso       = "";
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
     * @return the tipDocAlu
     */
    public int getTipDocAlu()
    {
        return tipDocAlu;
    }

    /**
     * @param tipDocAlu the tipDocAlu to set
     */
    public void setTipDocAlu(int tipDocAlu)
    {
        this.tipDocAlu = tipDocAlu;
    }

    /**
     * @return the numDocAlu
     */
    public String getNumDocAlu()
    {
        return numDocAlu;
    }

    /**
     * @param numDocAlu the numDocAlu to set
     */
    public void setNumDocAlu(String numDocAlu)
    {
        this.numDocAlu = numDocAlu;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the ap1Alu
     */
    public String getAp1Alu()
    {
        return ap1Alu;
    }

    /**
     * @param ap1Alu the ap1Alu to set
     */
    public void setAp1Alu(String ap1Alu)
    {
        this.ap1Alu = ap1Alu;
    }

    /**
     * @return the ap2Alu
     */
    public String getAp2Alu()
    {
        return ap2Alu;
    }

    /**
     * @param ap2Alu the ap2Alu to set
     */
    public void setAp2Alu(String ap2Alu)
    {
        this.ap2Alu = ap2Alu;
    }

    /**
     * @return the movAlu
     */
    public String getMovAlu()
    {
        return movAlu;
    }

    /**
     * @param movAlu the movAlu to set
     */
    public void setMovAlu(String movAlu)
    {
        this.movAlu = movAlu;
    }

    /**
     * @return the fijAlu
     */
    public String getFijAlu()
    {
        return fijAlu;
    }

    /**
     * @param fijAlu the fijAlu to set
     */
    public void setFijAlu(String fijAlu)
    {
        this.fijAlu = fijAlu;
    }

    /**
     * @return the dirAlu
     */
    public String getDirAlu()
    {
        return dirAlu;
    }

    /**
     * @param dirAlu the dirAlu to set
     */
    public void setDirAlu(String dirAlu)
    {
        this.dirAlu = dirAlu;
    }

    /**
     * @return the codPosAlu
     */
    public String getCodPosAlu()
    {
        return codPosAlu;
    }

    /**
     * @param codPosAlu the codPosAlu to set
     */
    public void setCodPosAlu(String codPosAlu)
    {
        this.codPosAlu = codPosAlu;
    }

    /**
     * @return the localAlu
     */
    public String getLocalAlu()
    {
        return localAlu;
    }

    /**
     * @param localAlu the localAlu to set
     */
    public void setLocalAlu(String localAlu)
    {
        this.localAlu = localAlu;
    }

    /**
     * @return the nivForm
     */
    public int getNivForm()
    {
        return nivForm;
    }

    /**
     * @param nivForm the nivForm to set
     */
    public void setNivForm(int nivForm)
    {
        this.nivForm = nivForm;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the desemp
     */
    public boolean isDesemp()
    {
        return desemp;
    }

    /**
     * @param desemp the desemp to set
     */
    public void setDesemp(boolean desemp)
    {
        this.desemp = desemp;
    }

    /**
     * @return the empAlu
     */
    public String getEmpAlu()
    {
        return empAlu;
    }

    /**
     * @param empAlu the empAlu to set
     */
    public void setEmpAlu(String empAlu)
    {
        this.empAlu = empAlu;
    }

    /**
     * @return the intereses
     */
    public String getIntereses()
    {
        return intereses;
    }

    /**
     * @param intereses the intereses to set
     */
    public void setIntereses(String intereses)
    {
        this.intereses = intereses;
    }

    /**
     * @return the codProvAlu
     */
    public String getCodProvAlu()
    {
        return codProvAlu;
    }

    /**
     * @param codProvAlu the codProvAlu to set
     */
    public void setCodProvAlu(String codProvAlu)
    {
        this.codProvAlu = codProvAlu;
    }

    /**
     * @return the idCen
     */
    public int getIdCen()
    {
        return idCen;
    }

    /**
     * @param idCen the idCen to set
     */
    public void setIdCen(int idCen)
    {
        this.idCen = idCen;
    }

    /**
     * @return the fecNac
     */
    public Date getFecNac()
    {
        return fecNac;
    }

    /**
     * @param fecNac the fecNac to set
     */
    public void setFecNac(Date fecNac)
    {
        this.fecNac = fecNac;
    }

    /**
     * @return the alND
     */
    public boolean isAlND()
    {
        return alND;
    }

    /**
     * @param alND the alND to set
     */
    public void setAlND(boolean alND)
    {
        this.alND = alND;
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
     * @return the autComCom
     */
    public boolean isAutComCom()
    {
        return autComCom;
    }

    /**
     * @param autComCom the autComCom to set
     */
    public void setAutComCom(boolean autComCom)
    {
        this.autComCom = autComCom;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() 
    {
        if(responsable != null)
        {
            return responsable;
        }
        else
        {
            return "";
        }
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(String responsable) { 
            this.responsable = responsable;
       
    }

    /**
     * @return the colegio
     */
    public String getColegio() {
        if(colegio != null)
        {
            return colegio;
        }
        else
        {
            return "";
        }
    }

    /**
     * @param colegio the colegio to set
     */
    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    /**
     * @return the curso
     */
    public String getCurso() {
        if(curso != null)
        {
            return curso;
        }
        else
        {    
            return "";
        }
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

}
