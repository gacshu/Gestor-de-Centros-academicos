/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author JuanAlberto
 */
public class ProfAreaVO 
{
    private String codProf;
    private String codArea;
    
    public ProfAreaVO()
    {
        codProf = "";
        codArea = "";
    }

    /**
     * @return the codProf
     */
    public String getCodProf() {
        return codProf;
    }

    /**
     * @param codProf the codProf to set
     */
    public void setCodProf(String codProf) {
        this.codProf = codProf;
    }

    /**
     * @return the codArea
     */
    public String getCodArea() {
        return codArea;
    }

    /**
     * @param codArea the codArea to set
     */
    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }
    
    
}
