/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author JuanAlberto
 */
public class AdapCurricularVO 
{
    private String codAdapCur;
    private String idAlu;
    private String materia;
    private String curso;
    
    
    public AdapCurricularVO()
    {
        codAdapCur = "";
        idAlu      = "";
        materia    = "";
        curso      = "";
    }

    /**
     * @return the codAdapCur
     */
    public String getCodAdapCur() {
        return codAdapCur;
    }

    /**
     * @param codAdapCur the codAdapCur to set
     */
    public void setCodAdapCur(String codAdapCur) {
        this.codAdapCur = codAdapCur;
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
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @return the curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }
    
}
