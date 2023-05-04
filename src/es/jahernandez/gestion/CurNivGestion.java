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

import es.jahernandez.accesodatos.CurNivDAO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author Alberto
 */
public class CurNivGestion 
{
    //Método que guarda los niveles de un curso
    public static int guardarNivCur(String codCur, String codNiv)
    {
        Connection con             = null;           
        int        regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CurNivDAO.guardarNivCur(codCur, codNiv, con);
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
                Logger.getLogger(CurNivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los niveles que tiene asociados un determinado curso
    public static Vector devolverCurNiv(String codCur)
    {

        Connection con         = null;
        Vector     listaNivCur = new Vector();

        try
        {
            con = Conexion.conectar();
            listaNivCur = CurNivDAO.devolverCurNiv(codCur, con);
            Conexion.desconectar(con);

            return listaNivCur;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CurNivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que elimina un nivel de un curso
    public static int eliminarNivCur(String codNiv, String codCur)
    {
        Connection con             = null;
        int        regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CurNivDAO.eliminarNivCur(codNiv, codCur, con);
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
                Logger.getLogger(CurNivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }

    }

    //Borra los datos de nivel de un curso
    public static int borraCurNiv(String codCur)
    {

        Connection con    = null;
        int        regAct = 0;

        try
        {
            con = Conexion.conectar();
            regAct = CurNivDAO.borraCurNiv(codCur,con);
            Conexion.desconectar(con);
            return regAct;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CurNivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra todos los datos de un curso
    public static int borrarTodNivCur(String codCur)
    {
        Connection con    = null;
       int         regAct = 0;
        try
        {
            con = Conexion.conectar();
            regAct = CurNivDAO.borrarTodNivCur(codCur, con);
            Conexion.desconectar(con);

            return regAct;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }

    }
}
