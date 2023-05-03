/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class CursosVO
{
    private String idCur;
    private String nomCur;
    private int    tipCur;
    private int    cenCurso;
    private String contenido;


    public CursosVO()
    {
        idCur      = "";
        nomCur     = "";
        tipCur     = 0;
        cenCurso   = 0;
        contenido  = "";
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

    /**
     * @return the nomCur
     */
    public String getNomCur() {
        return nomCur;
    }

    /**
     * @param nomCur the nomCur to set
     */
    public void setNomCur(String nomCur) {
        this.nomCur = nomCur;
    }

    /**
     * @return the tipCur
     */
    public int getTipCur() {
        return tipCur;
    }

    /**
     * @param tipCur the tipCur to set
     */
    public void setTipCur(int tipCur) {
        this.tipCur = tipCur;
    }

    /**
     * @return the cenCurso
     */
    public int getCenCurso() {
        return cenCurso;
    }

    /**
     * @param cenCurso the cenCurso to set
     */
    public void setCenCurso(int cenCurso) {
        this.cenCurso = cenCurso;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
