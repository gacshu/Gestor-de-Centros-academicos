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

import es.jahernandez.accesodatos.ModulosDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ModulosVO;

/**
 *
 * @author Alberto
 */
public class ModulosGestion 
{
    //Método que devuelve los datos de un módulo
    public static ModulosVO devolverDatosModulo(String codMod)
    {
        Connection        con            = null;
        ModulosVO         datMod         = null;

        try
        {
            con = Conexion.conectar();
            datMod = ModulosDAO.devolverDatosModulo(codMod,con);
            Conexion.desconectar(con);

            return datMod;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de módulo
    public static Vector devolverTodosMod()
    {
        Connection        con            = null;
        Vector            listaModulos   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaModulos = ModulosDAO.devolverTodosMod(con);
            Conexion.desconectar(con);

            return listaModulos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ModulosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarModulo(ModulosVO modVO)
    {
        Connection con             = null;
        int        regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = ModulosDAO.guardarModulo(modVO,con);
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
                Logger.getLogger(ModulosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de modulo
    synchronized public static String generarNuevoCodMod()
    {
        return ModulosDAO.generarNuevoCodMod();
    }

    //Método que devuelve los módulos de un curso
    public static Vector devolverModCur(String codCur )
    {
        Connection        con            = null;
        Vector            listaModulos = new Vector();

        try
        {
            con = Conexion.conectar();
            listaModulos = ModulosDAO.devolverModCur(codCur,con);
            Conexion.desconectar(con);

            return listaModulos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ModulosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
     //Edita el registro de un módulo
    public static int editaModulo(ModulosVO modVO)
    {

        Connection        con = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = ModulosDAO.editaModulo(modVO,con);
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
                Logger.getLogger(ModulosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Elimina el registro de un módulo
    public static int eliminaModulo(String codMod)
    {

        Connection        con = null;
        int               regActualizados = 0;

        //Comprobar si existe alguna edición con este módulo
        
        try
        {
            con = Conexion.conectar();
            regActualizados = ModulosDAO.eliminaModulo(codMod,con);
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
                Logger.getLogger(ModulosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }        
    
}
