/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class ListaCodPostVO
{
    private String nCodigo;
    private String nomProv;

    public ListaCodPostVO()
    {
        nCodigo = "";
        nomProv = "";
    }

    /**
     * @return the nCodigo
     */
    public String getnCodigo()
    {
        return nCodigo;
    }

    /**
     * @param nCodigo the nCodigo to set
     */
    public void setnCodigo(String nCodigo)
    {
        this.nCodigo = nCodigo;
    }

    /**
     * @return the nomProv
     */
    public String getNomProv()
    {
        return nomProv;
    }

    /**
     * @param nomProv the nomProv to set
     */
    public void setNomProv(String nomProv)
    {
        this.nomProv = nomProv;
    }



}
