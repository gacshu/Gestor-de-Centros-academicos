/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class ActividadesVO
{
    private int    codActividad;
    private String nombreActividad;

    public ActividadesVO()
    {
        codActividad    = 0;
        nombreActividad = "";
    }

    /**
     * @return the codActividad
     */
    public int getCodActividad()
    {
        return codActividad;
    }

    /**
     * @param codActividad the codActividad to set
     */
    public void setCodActividad(int codActividad)
    {
        this.codActividad = codActividad;
    }

    /**
     * @return the nombreActividad
     */
    public String getNombreActividad()
    {
        return nombreActividad;
    }

    /**
     * @param nombreActividad the nombreActividad to set
     */
    public void setNombreActividad(String nombreActividad)
    {
        this.nombreActividad = nombreActividad;
    }

}
