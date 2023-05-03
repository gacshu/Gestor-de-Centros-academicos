/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class CurNivVO
{
    private String idNiv;
    private String idCur;


    public CurNivVO()
    {
        idNiv = "";
        idCur = "";
    }
            
    /**
     * @return the idNiv
     */
    public String getIdNiv() {
        return idNiv;
    }

    /**
     * @param idNiv the idNiv to set
     */
    public void setIdNiv(String idNiv) {
        this.idNiv = idNiv;
    }

    /**
     * @return the idCur
     */
    public String getIdCur() {
        return idCur;
    }

    /**
     * @param idCur the idCur to set
     */
    public void setIdCur(String idCur) {
        this.idCur = idCur;
    }

}
