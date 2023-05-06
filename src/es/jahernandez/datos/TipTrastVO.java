/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author JuanAlberto
 */
public class TipTrastVO
{
    private String codTipTrast;
    private String descrip;

    public TipTrastVO()
    {
        codTipTrast = "";
        descrip     = "";
    }

    /**
     * @return the codTipTrast
     */
    public String getCodTipTrast() {
        return codTipTrast;
    }

    /**
     * @param codTipTrast the codTipTrast to set
     */
    public void setCodTipTrast(String codTipTrast) {
        this.codTipTrast = codTipTrast;
    }

    /**
     * @return the descrip
     */
    public String getDescrip() {
        return descrip;
    }

    /**
     * @param descrip the descrip to set
     */
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

}
