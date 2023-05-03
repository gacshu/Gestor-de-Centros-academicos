/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class DatosBusquedaAlumnos 
{
    private String numDoc;
    private String apellidos;
    private String nombre;
    private String eMail;
    private String codPost;
    private String nivFor;
    private String codEmp;
    private String desempleado;
    private String NoIntCur;

    
    public DatosBusquedaAlumnos()
    {
        numDoc      = "";
        apellidos   = "";
        nombre      = "";
        eMail       = "";
        codPost     = "";
        nivFor      = "";
        codEmp      = "";
        desempleado = "";
        NoIntCur    = "";
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
     * @return the eMail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * @param eMail the eMail to set
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * @return the codPost
     */
    public String getCodPost() {
        return codPost;
    }

    /**
     * @param codPost the codPost to set
     */
    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    /**
     * @return the nivFor
     */
    public String getNivFor() {
        return nivFor;
    }

    /**
     * @param nivFor the nivFor to set
     */
    public void setNivFor(String nivFor) {
        this.nivFor = nivFor;
    }

    /**
     * @return the codEmp
     */
    public String getCodEmp() {
        return codEmp;
    }

    /**
     * @param codEmp the codEmp to set
     */
    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }

    /**
     * @return the desempleado
     */
    public String getDesempleado() {
        return desempleado;
    }

    /**
     * @param desempleado the desempleado to set
     */
    public void setDesempleado(String desempleado) {
        this.desempleado = desempleado;
    }

    /**
     * @return the NoIntCur
     */
    public String getNoIntCur() {
        return NoIntCur;
    }

    /**
     * @param NoIntCur the NoIntCur to set
     */
    public void setNoIntCur(String NoIntCur) {
        this.NoIntCur = NoIntCur;
    }
}
