/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class ModulosVO 
{
    private String codMod;
    private String nombre;
    private String descripcion;
    private int    numHoras;
    private String codCur;
    private String codArea;

    /**
     * @return the codMod
     */
    public String getCodMod() 
    {
        return codMod;
    }

    /**
     * @param codMod the codMod to set
     */
    public void setCodMod(String codMod) 
    {
        this.codMod = codMod;
    }

    /**
     * @return the nombre
     */
    public String getNombre() 
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the numHoras
     */
    public int getNumHoras() {
        return numHoras;
    }

    /**
     * @param numHoras the numHoras to set
     */
    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }

    /**
     * @return the codCurs
     */
    public String getCodCur() {
        return codCur;
    }

    /**
     * @param codCurs the codCurs to set
     */
    public void setCodCur(String codCur) {
        this.codCur = codCur;
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
