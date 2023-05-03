/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import es.jahernandez.accesodatos.BancosDAO;
import es.jahernandez.datos.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JuanAlberto
 */
public class BancosGestion 
{
    
    
     public static String devolverNombreBanco(String codBan)
     {
        Connection        con    = null;
        String            nomBan = "";
       
        try
        {
            con = Conexion.conectar();
            nomBan = BancosDAO.devolverNombreBanco(codBan, con);
            Conexion.desconectar(con);

            return nomBan;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(BancosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    
     }
}
