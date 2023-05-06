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

import es.jahernandez.accesodatos.TrastornosDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.TrastornosVO;

/**
 *
 * @author Alberto
 */
public class TrastornosGestion
{
    //Método que devuelve los datos de un trastorno
    public static TrastornosVO devolverDatosTrastorno(String codTrastorno)
    {
        Connection        con            = null;
        TrastornosVO      trastVO        = new TrastornosVO();

        try
        {
            con = Conexion.conectar();
            trastVO = TrastornosDAO.devolverDatosTrastorno(codTrastorno,con);
            Conexion.desconectar(con);

            return trastVO;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TrastornosGestion .class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve todos los registro de trastorno
    public static Vector devolverTodosTrastornos()
    {
        Connection        con          = null;
        Vector            listaTrast   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaTrast = TrastornosDAO.devolverTodosTrastornos(con);
            Conexion.desconectar(con);

            return listaTrast;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TrastornosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarTrastorno(TrastornosVO trastVO)
    {
        Connection        con            = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = TrastornosDAO.guardarTrastorno(trastVO,con);
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
                Logger.getLogger(TrastornosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de trastorno
    public static String nueCodTrast()
    {
        return TrastornosDAO.nueCodTrast();
    }

    //Método que devuelve los datos de trastorno de un alumno
    public static Vector devolverTrastAlu(String codAlu )
    {
        Connection        con          = null;
        Vector            listaTrast   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaTrast = TrastornosDAO.devolverTrastAlu(codAlu,con);
            Conexion.desconectar(con);

            return listaTrast;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TrastornosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

     //Edita el registro de un trastorno
    public static int editaTrastorno(TrastornosVO trastVO)
    {
        Connection        con = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = TrastornosDAO.editaTrastorno(trastVO,con);
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
                Logger.getLogger(TrastornosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra el registro de un trastorno
    public static int eliminaTrastorno(String codTrastorno)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = TrastornosDAO.eliminaTrastorno(codTrastorno,con);
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
                Logger.getLogger(TrastornosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra los trastornos de un alumno
    public static int eliminaTrastornosAlumno(String codAlu)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = TrastornosDAO.eliminaTrastornosAlumno(codAlu,con);
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
                Logger.getLogger(TrastornosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }
}
