/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class ConUsuVO
{
    private String  usuario;
    private String  password;
    private int     nivelAcceso;
    private String  nombre;
    private int     idCentro;
    private boolean activo;
    private String  idProf;



    //Constructor
    public ConUsuVO()
    {
        usuario     = "";
        password    = "";
        nivelAcceso = 0;
        nombre      = "";
        idCentro    = 0;
        activo      = false;
        idProf      = "";
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

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the nivelAcceso
     */
    public int getNivelAcceso()
    {
        return nivelAcceso;
    }

    /**
     * @param nivelAcceso the nivelAcceso to set
     */
    public void setNivelAcceso(int nivelAcceso)
    {
        this.nivelAcceso = nivelAcceso;
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
     * @return the idCentro
     */
    public int getIdCentro()
    {
        return idCentro;
    }

    /**
     * @param idCentro the idCentro to set
     */
    public void setIdCentro(int idCentro)
    {
        this.idCentro = idCentro;
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
