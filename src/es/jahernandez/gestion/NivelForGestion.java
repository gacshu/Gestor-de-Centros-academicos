/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.accesodatos.NivelForDAO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author Alberto
 */
public class NivelForGestion
{
     //Método que devuelve los datos a mostrar en los combos de nivel formativo
    public static Vector datComNivFor()
    {
        Connection        con        = null;
        Vector            listNivFor = new Vector();

        try
        {
            con = Conexion.conectar();
            listNivFor = NivelForDAO.datComNivFor(con);
            Conexion.desconectar(con);

            return listNivFor;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(NivelForGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve el nombre de un nivel formativo
    public static String nomNivFor(String idNivFor)
    {

        Connection        con        = null;
        String            strNomNF   = "";

        try
        {
            con = Conexion.conectar();
            strNomNF = NivelForDAO.nomNivFor(idNivFor,con);
            Conexion.desconectar(con);

            return strNomNF;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(NivelForGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }
}
