/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class TipoCursoVO
{
    private int    idTipoCurso;
    private String nomTipCurso;

    public TipoCursoVO()
    {
        idTipoCurso = -1;
        nomTipCurso = "";
    }

    /**
     * @return the idTipoCurso
     */
    public int getIdTipoCurso()
    {
        return idTipoCurso;
    }

    /**
     * @param idTipoCurso the idTipoCurso to set
     */
    public void setIdTipoCurso(int idTipoCurso)
    {
        this.idTipoCurso = idTipoCurso;
    }

    /**
     * @return the nomTipCurso
     */
    public String getNomTipCurso()
    {
        return nomTipCurso;
    }

    /**
     * @param nomTipCurso the nomTipCurso to set
     */
    public void setNomTipCurso(String nomTipCurso)
    {
        this.nomTipCurso = nomTipCurso;
    }

}
