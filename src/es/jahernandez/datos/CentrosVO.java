/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class CentrosVO
{
    private int    idCentro;
    private String nombreCentro;

    public CentrosVO()
    {
        idCentro     = 0;
        nombreCentro = "";
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
     * @return the nombreCentro
     */
    public String getNombreCentro()
    {
        return nombreCentro;
    }

    /**
     * @param nombreCentro the nombreCentro to set
     */
    public void setNombreCentro(String nombreCentro)
    {
        this.nombreCentro = nombreCentro;
    }


}
