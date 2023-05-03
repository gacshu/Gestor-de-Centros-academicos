/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class NivelForVO
{
    private int    idNIvel;
    private String nomNivel ;


    public NivelForVO()
    {
        idNIvel  = 0;
        nomNivel = "";
    }

    /**
     * @return the idNIvel
     */
    public int getIdNIvel()
    {
        return idNIvel;
    }

    /**
     * @param idNIvel the idNIvel to set
     */
    public void setIdNIvel(int idNIvel)
    {
        this.idNIvel = idNIvel;
    }

    /**
     * @return the nomNivel
     */
    public String getNomNivel()
    {
        return nomNivel;
    }

    /**
     * @param nomNivel the nomNivel to set
     */
    public void setNomNivel(String nomNivel)
    {
        this.nomNivel = nomNivel;
    }




}
