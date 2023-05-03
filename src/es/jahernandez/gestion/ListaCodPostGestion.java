/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;
import es.jahernandez.accesodatos.ListaCodPostDAO;
import es.jahernandez.datos.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto
 */
public class ListaCodPostGestion 
{
    //Método que devuelve los datos a mostrar en los combos de Provincia
    public static Vector devolverDatProv()
    {
        Connection        con           = null;
        Vector            listaCP       = new Vector();
        
        try
        {
            con = Conexion.conectar();
            listaCP = ListaCodPostDAO.devolverDatProv(con);
            Conexion.desconectar(con);

            return listaCP;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ListaCodPostGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Devuelve el nombre de la provincia
    public static String devuelveNombreProv(String idProv)
    {

        Connection        con       = null;
        String            nomProv   = "";

        try
        {
            con = Conexion.conectar();
            nomProv = ListaCodPostDAO.devuelveNombreProv(idProv,con);
            Conexion.desconectar(con);

            return nomProv;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ListaCodPostGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }

    }
}
