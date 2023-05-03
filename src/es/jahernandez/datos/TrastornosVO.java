/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author JuanAlberto
 */
public class TrastornosVO 
{
    private String  codTrastorno;
    private String  idAlu;
    private String  codTipoTrastorno;
    private boolean medicado;
    private String  medicacion;
    
    public TrastornosVO()
    {
        codTrastorno     = ""; 
        idAlu            = "";
        codTipoTrastorno = "";
        medicado         = false;
        medicacion       = ""; 
    }

    /**
     * @return the codTrastorno
     */
    public String getCodTrastorno() {
        return codTrastorno;
    }

    /**
     * @param codTrastorno the codTrastorno to set
     */
    public void setCodTrastorno(String codTrastorno) {
        this.codTrastorno = codTrastorno;
    }

    /**
     * @return the idAlu
     */
    public String getIdAlu() {
        return idAlu;
    }

    /**
     * @param idAlu the idAlu to set
     */
    public void setIdAlu(String idAlu) {
        this.idAlu = idAlu;
    }

    /**
     * @return the codTipoTrastorno
     */
    public String getCodTipoTrastorno() {
        return codTipoTrastorno;
    }

    /**
     * @param codTipoTrastorno the codTipoTrastorno to set
     */
    public void setCodTipoTrastorno(String codTipoTrastorno) {
        this.codTipoTrastorno = codTipoTrastorno;
    }

    /**
     * @return the medicado
     */
    public boolean isMedicado() {
        return medicado;
    }

    /**
     * @param medicado the medicado to set
     */
    public void setMedicado(boolean medicado) {
        this.medicado = medicado;
    }

    /**
     * @return the medicacion
     */
    public String getMedicacion() {
        return medicacion;
    }

    /**
     * @param medicacion the medicacion to set
     */
    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }
    
}
