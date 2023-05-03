/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;
import es.jahernandez.accesodatos.TipoDocDAO;
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
public class TipoDocGestion 
{

    //Método que devuelve los datos a mostrar en los combos de Tipo de Documento
    public static Vector devolverDatosTipDoc()
    {
        Connection        con           = null;
        Vector            listaTipDoc   = new Vector();
        
        try
        {
            con = Conexion.conectar();
            listaTipDoc = TipoDocDAO.devolverDatosTipDoc(con);
            Conexion.desconectar(con);

            return listaTipDoc;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoDocGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Devuelve el tipo de Dni de un documento
    public static String devuelveNombreTipoDoc(int idDoc)
    {
        Connection        con       = null;
        String            nombreDoc = "";

        try
        {
            con = Conexion.conectar();
            nombreDoc = TipoDocDAO.devuelveNombreTipoDoc(idDoc,con);
            Conexion.desconectar(con);

            return nombreDoc;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoDocGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }    
}
