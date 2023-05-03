/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.datos;

/**
 *
 * @author Alberto
 */
public class DatosCmbEmp
{

    private String idEmp  ;
    private String nombEmp;

    public DatosCmbEmp()
    {
        idEmp   = "";
        nombEmp = "";
    }

    /**
     * @return the idEmp
     */
    public String getIdEmp()
    {
        return idEmp;
    }

    /**
     * @param idEmp the idEmp to set
     */
    public void setIdEmp(String idEmp)
    {
        this.idEmp = idEmp;
    }

    /**
     * @return the nombEmp
     */
    public String getNombEmp()
    {
        return nombEmp;
    }

    /**
     * @param nombEmp the nombEmp to set
     */
    public void setNombEmp(String nombEmp)
    {
        this.nombEmp = nombEmp;
    }


}
