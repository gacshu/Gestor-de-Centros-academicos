/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author JuanAlberto
 */
public class AreasVO
{
    private String codArea;
    private String nomArea;

    public AreasVO()
    {
        codArea = "";
        nomArea = "";
    }

    /**
     * @return the codArea
     */
    public String getCodArea()
    {
        return codArea;
    }

    /**
     * @param codArea the codArea to set
     */
    public void setCodArea(String codArea)
    {
        this.codArea = codArea;
    }

    /**
     * @return the nomArea
     */
    public String getNomArea()
    {
        return nomArea;
    }

    /**
     * @param nomArea the nomArea to set
     */
    public void setNomArea(String nomArea)
    {
        this.nomArea = nomArea;
    }

}
