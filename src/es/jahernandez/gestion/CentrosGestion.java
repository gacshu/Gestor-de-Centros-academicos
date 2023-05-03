/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import es.jahernandez.accesodatos.CentrosDAO;
import es.jahernandez.datos.CentrosVO;
import es.jahernandez.datos.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JuanAlberto
 */
public class CentrosGestion 
{
    //Método que devuelve los datos a mostrar en los combos de centros
    public static Vector datComCentros()
    {

        Connection        con        = null;
        Vector            listCentros = new Vector();

        try
        {
            con = Conexion.conectar();
            
            listCentros = CentrosDAO.datComCentros(con);

            Conexion.desconectar(con);

            return listCentros;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve el nombre de un centro
    public static String nomCentro(String idCen)
    {

        Connection        con     = null;
        
        String            strCen  = "";

        try
        {
            con = Conexion.conectar();
            strCen = CentrosDAO.nomCentro(idCen,con);
            Conexion.desconectar(con);

            return strCen;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }
    
    //Método que devuelve los datos de un centro
    public static CentrosVO datCentro(int idCen)
    {

        Connection       con      = null;
        CentrosVO        cenVO    = null;

        try
        {
            con = Conexion.conectar();
            
            cenVO = CentrosDAO.datCentro(idCen, con);
            
            Conexion.desconectar(con);

            return cenVO;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    
    //Devuelve el nuevo código de centtro generado
    public static int generarNuevoCodCentro()
    {
        Connection        con            = null;
    
        int               nuevoCod       = -1;

        try
        {
            con = Conexion.conectar();
            
            nuevoCod = CentrosDAO.generarNuevoCodCentro(con);
            
            Conexion.desconectar(con);

            return nuevoCod;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }

    }
    
    //Método que guarda un Centro
    public static int guardarCentro(CentrosVO cenVO)
    {
        Connection        con             = null;
        
        int               regActualizados = 0;
        
        try
        {
           con = Conexion.conectar();
           
           regActualizados = CentrosDAO.guardarCentro(cenVO, con);
           
           Conexion.desconectar(con);

           return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
    
    //Edita el registro de un Centro
    public static int editarCentro(CentrosVO cenVO)
    {

        Connection        con             = null;
   
        int               regActualizados = -1;

        try
        {

            con = Conexion.conectar();
            
            regActualizados = CentrosDAO.editarCentro(cenVO, con);
            
            Conexion.desconectar(con);

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

}
