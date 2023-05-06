/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class NivelesVO
{
    private String idNiv;
    private String nomNiv;
    private String contenidos;
    private String codCur;


    public NivelesVO()
    {
        idNiv      = "";
        nomNiv     = "";
        contenidos = "";
        codCur     = "";
    }


    /**
     * @return the idNiv
     */
    public String getIdNiv()
    {
        return idNiv;
    }

    /**
     * @param idNiv the idNiv to set
     */
    public void setIdNiv(String idNiv)
    {
        this.idNiv = idNiv;
    }

    /**
     * @return the nomNiv
     */
    public String getNomNiv()
    {
        return nomNiv;
    }

    /**
     * @param nomNiv the nomNiv to set
     */
    public void setNomNiv(String nomNiv)
    {
        this.nomNiv = nomNiv;
    }

    /**
     * @return the contenidos
     */
    public String getContenidos()
    {
        return contenidos;
    }

    /**
     * @param contenidos the contenidos to set
     */
    public void setContenidos(String contenidos)
    {
        this.contenidos = contenidos;
    }

    /**
     * @return the codTipCur
     */
    public String getCodCur() {
        return codCur;
    }

    /**
     * @param codTipCur the codTipCur to set
     */
    public void setCodCur(String codTipCur) {
        this.codCur = codTipCur;
    }



}
