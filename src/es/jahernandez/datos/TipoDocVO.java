/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class TipoDocVO
{
    private int    idTipDoc;
    private String nombTipDoc;


    public TipoDocVO()
    {
        idTipDoc = 0;
        nombTipDoc = "";
    }

    /**
     * @return the idTipDoc
     */
    public int getIdTipDoc()
    {
        return idTipDoc;
    }

    /**
     * @param idTipDoc the idTipDoc to set
     */
    public void setIdTipDoc(int idTipDoc)
    {
        this.idTipDoc = idTipDoc;
    }

    /**
     * @return the nombTipDoc
     */
    public String getNombTipDoc() {
        return nombTipDoc;
    }

    /**
     * @param nombTipDoc the nombTipDoc to set
     */
    public void setNombTipDoc(String nombTipDoc)
    {
        this.nombTipDoc = nombTipDoc;
    }



}
