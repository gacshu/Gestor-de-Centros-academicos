/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author JuanAlberto
 */
public class ProfesoresVO
{
    private String  idProf;
    private String  nombre;
    private String  apellidos;
    private int     codDoc;
    private String  numDoc;
    private Date    fecNac;
    private Date    fecAlt;
    private String  direccion;
    private String  localidad;
    private String  codProv;
    private String  codPos;
    private String  observ;
    private String  telef;
    private String  mov;
    private String  email;
    private boolean activo;

    public ProfesoresVO()
    {
        idProf    = "";
        nombre    = "";
        apellidos = "";
        codDoc    = -1;
        numDoc    = "";
        fecNac    = new GregorianCalendar(1900,0,1).getTime();
        fecAlt    = new GregorianCalendar(1900,0,1).getTime();
        direccion = "";
        localidad = "";
        codProv   = "";
        codPos    = "";
        observ    = "";
        telef     = "";
        mov       = "";
        email     = "";
        activo    = false;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the codDoc
     */
    public int getCodDoc() {
        return codDoc;
    }

    /**
     * @param codDoc the codDoc to set
     */
    public void setCodDoc(int codDoc) {
        this.codDoc = codDoc;
    }

    /**
     * @return the numDoc
     */
    public String getNumDoc() {
        return numDoc;
    }

    /**
     * @param numDoc the numDoc to set
     */
    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    /**
     * @return the fecNac
     */
    public Date getFecNac() {
        return fecNac;
    }

    /**
     * @param fecNac the fecNac to set
     */
    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    /**
     * @return the fecAlt
     */
    public Date getFecAlt() {
        return fecAlt;
    }

    /**
     * @param fecAlt the fecAlt to set
     */
    public void setFecAlt(Date fecAlt) {
        this.fecAlt = fecAlt;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad the localidad to set
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the codProv
     */
    public String getCodProv() {
        return codProv;
    }

    /**
     * @param codProv the codProv to set
     */
    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }

    /**
     * @return the codPos
     */
    public String getCodPos() {
        return codPos;
    }

    /**
     * @param codPos the codPos to set
     */
    public void setCodPos(String codPos) {
        this.codPos = codPos;
    }

    /**
     * @return the observ
     */
    public String getObserv() {
        return observ;
    }

    /**
     * @param observ the observ to set
     */
    public void setObserv(String observ) {
        this.observ = observ;
    }

    /**
     * @return the telef
     */
    public String getTelef() {
        return telef;
    }

    /**
     * @param telef the telef to set
     */
    public void setTelef(String telef) {
        this.telef = telef;
    }

    /**
     * @return the mov
     */
    public String getMov() {
        return mov;
    }

    /**
     * @param mov the mov to set
     */
    public void setMov(String mov) {
        this.mov = mov;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }


}
