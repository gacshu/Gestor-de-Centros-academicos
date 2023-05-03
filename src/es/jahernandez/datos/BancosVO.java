/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author JuanAlberto
 */
public class BancosVO 
{
    private String idBanco;
    private String nombreBanco;

    public BancosVO() 
    {
        idBanco     = "";
        nombreBanco = "";
    }

    /**
     * @return the idBanco
     */
    public String getIdBanco() {
        return idBanco;
    }

    /**
     * @param idBanco the idBanco to set
     */
    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }
    
    
    
 
}
