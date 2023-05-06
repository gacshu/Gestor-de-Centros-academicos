/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class AulasVO
{
    private String  idAula;
    private String  nombre;
    private int     idCen;
    private int     numPla;
    private String  descripcion;
    private boolean esAulInf;
    private boolean tieneProy;
    private boolean tieneTV;
    private boolean tieneAC;
    private boolean tieneImp;
    private boolean tieneInt;



    public AulasVO()
    {
        idAula      = "";
        nombre      = "";
        idCen       = 0;
        numPla      = -1;
        descripcion = "";
        esAulInf    = false;
        tieneProy   = false;
        tieneTV     = false;
        tieneAC     = false;
        tieneImp    = false;
        tieneInt    = false;
    }




    /**
     * @return the idAula
     */
    public String getIdAula()
    {
        return idAula;
    }

    /**
     * @param idAula the idAula to set
     */
    public void setIdAula(String idAula)
    {
        this.idAula = idAula;
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
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the idCen
     */
    public int getIdCen()
    {
        return idCen;
    }

    /**
     * @param idCen the idCen to set
     */
    public void setIdCen(int idCen)
    {
        this.idCen = idCen;
    }

    /**
     * @return the numPla
     */
    public int getNumPla()
    {
        return numPla;
    }

    /**
     * @param numPla the numPla to set
     */
    public void setNumPla(int numPla)
    {
        this.numPla = numPla;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion()
    {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    /**
     * @return the esAulInf
     */
    public boolean isEsAulInf()
    {
        return esAulInf;
    }

    /**
     * @param esAulInf the esAulInf to set
     */
    public void setEsAulInf(boolean esAulInf)
    {
        this.esAulInf = esAulInf;
    }

    /**
     * @return the tieneProy
     */
    public boolean isTieneProy()
    {
        return tieneProy;
    }

    /**
     * @param tieneProy the tieneProy to set
     */
    public void setTieneProy(boolean tieneProy)
    {
        this.tieneProy = tieneProy;
    }

    /**
     * @return the tieneTV
     */
    public boolean isTieneTV()
    {
        return tieneTV;
    }

    /**
     * @param tieneTV the tieneTV to set
     */
    public void setTieneTV(boolean tieneTV)
    {
        this.tieneTV = tieneTV;
    }

    /**
     * @return the tieneAC
     */
    public boolean isTieneAC()
    {
        return tieneAC;
    }

    /**
     * @param tieneAC the tieneAC to set
     */
    public void setTieneAC(boolean tieneAC)
    {
        this.tieneAC = tieneAC;
    }

    /**
     * @return the tieneImp
     */
    public boolean isTieneImp()
    {
        return tieneImp;
    }

    /**
     * @param tieneImp the tieneImp to set
     */
    public void setTieneImp(boolean tieneImp)
    {
        this.tieneImp = tieneImp;
    }

    /**
     * @return the tieneInt
     */
    public boolean isTieneInt()
    {
        return tieneInt;
    }

    /**
     * @param tieneInt the tieneInt to set
     */
    public void setTieneInt(boolean tieneInt)
    {
        this.tieneInt = tieneInt;
    }
}
