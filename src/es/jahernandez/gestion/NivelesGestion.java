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

import es.jahernandez.accesodatos.NivelesDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.NivelesVO;

/**
 *
 * @author Alberto
 */
public class NivelesGestion
{
    //Método que devuelve los datos de un nivel
    public static NivelesVO devolverDatosNivel(String codNiv)
    {
        Connection        con            = null;
        NivelesVO         datNiv         = new NivelesVO();

        try
        {
            con = Conexion.conectar();
            datNiv = NivelesDAO.devolverDatosNivel(codNiv,con);
            Conexion.desconectar(con);

            return datNiv;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(NivelesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de nivel
    public static Vector devolverTodosNiv()
    {
        Connection        con            = null;
        Vector            listaNiveles   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaNiveles = NivelesDAO.devolverTodosNiv(con);
            Conexion.desconectar(con);

            return listaNiveles;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(NivelesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarNivel(NivelesVO nivVO)
    {
        Connection        con            = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = NivelesDAO.guardarNivel(nivVO,con);
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
                Logger.getLogger(NivelesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de nivel
    public static String generarNuevoCodNiv()
    {
        return NivelesDAO.generarNuevoCodNiv();
    }

    //Método que devuelve los datos de nivel de un determinado curso
    public static Vector devolverNivCur(String codCur )
    {
        Connection        con            = null;
        Vector            listaNiveles = new Vector();

        try
        {
            con = Conexion.conectar();
            listaNiveles = NivelesDAO.devolverNivCur(codCur,con);
            Conexion.desconectar(con);

            return listaNiveles;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(NivelesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

     //Edita el registro de un curso
    public static int editaNivel(NivelesVO nivVO)
    {

        Connection        con = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = NivelesDAO.editaNivel(nivVO,con);
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
                Logger.getLogger(NivelesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Edita el registro de un curso
    public static int eliminaNivel(String codNivel)
    {

        Connection        con = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = NivelesDAO.eliminaNivel(codNivel,con);
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }
}