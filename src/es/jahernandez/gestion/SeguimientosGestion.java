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

import es.jahernandez.accesodatos.SeguimientosDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.SeguimientosVO;

/**
 *
 * @author Alberto
 */
public class SeguimientosGestion
{
    //Método que devuelve los datos de un seguimiento
    public static SeguimientosVO devolverDatosSeg(String codSeg)
    {
        Connection        con    = null;
        SeguimientosVO    datSeg = null;

        try
        {
            con = Conexion.conectar();
            datSeg = SeguimientosDAO.devolverDatosSeg(codSeg,con);
            Conexion.desconectar(con);

            return datSeg;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve los datos de seguimiento
    public static Vector devolverTodosSeg()
    {
        Connection        con      = null;
        Vector            listaSeg = new Vector();

        try
        {
            con = Conexion.conectar();
            listaSeg = SeguimientosDAO.devolverTodosSeg(con);
            Conexion.desconectar(con);

             return listaSeg;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que genera un nuevo código de seguimiento
    public static String generarNuevoCodSeg()
    {
        return SeguimientosDAO.generarNuevoCodSeg();
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarSeg(SeguimientosVO segVO)
    {
        Connection con             = null;
        int        regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = SeguimientosDAO.guardarSeg(segVO,con);
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
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Edita el registro de un seguimiento
    public static int editaSeg(SeguimientosVO segVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = SeguimientosDAO.editaSeg(segVO,con);
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
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de seguimiento de un alumno
    public static Vector devolverSegAlu(String codAl)
    {
        Connection        con       = null;
        Vector            listaSeg  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaSeg = SeguimientosDAO.devolverSegAlu(codAl,con);
            Conexion.desconectar(con);

            return listaSeg;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve los datos de seguimiento de un curso
    public static Vector devolverSegCur(String codCur)
    {
        Connection        con      = null;
        Vector            listaSeg = new Vector();

        try
        {
            con = Conexion.conectar();
            listaSeg = SeguimientosDAO.devolverSegCur(codCur,con);
            Conexion.desconectar(con);

            return listaSeg;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que elimina todos los seguimientos de un alumno
    public static int eliminaSegAlu(String codAlu)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = SeguimientosDAO.eliminaSegAlu(codAlu,con);
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
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que elimina todos los seguimientos de un curso
    public static int eliminaSegCur(String codCur)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = SeguimientosDAO.eliminaSegCur(codCur, con);
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
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que elimina un registro de seguimiento
    public static int eliminaSeg(String codSeg)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = SeguimientosDAO.eliminaSeg(codSeg,con);
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
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve el número de seguimientos de un alumno
    public static int devolverNumSeg(String codAlu)
    {
        Connection        con    = null;
        int               numSeg = 0;

        try
        {
            con = Conexion.conectar();
            numSeg = SeguimientosDAO.devolverNumSeg(codAlu,con);
            Conexion.desconectar(con);

            return numSeg;
        }

        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SeguimientosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
}
