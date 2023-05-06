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

import es.jahernandez.accesodatos.FaltasDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.FaltasVO;

/**
 *
 * @author Alberto
 */
public class FaltasGestion
{
    //Método que devuelve los datos de una falta
    public static FaltasVO devolverDatosFalta(String codEdi, String codMod, String codAlu, java.util.Date fecha)
    {
        Connection        con     = null;
        FaltasVO          faltaVO = new FaltasVO();

        try
        {
            con = Conexion.conectar();
            faltaVO = FaltasDAO.devolverDatosFalta(codEdi, codMod, codAlu, fecha,con);
            Conexion.desconectar(con);

            return faltaVO;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve todos los registro de faltas
    public static Vector devolverTodasFaltas()
    {
        Connection        con            = null;
        Vector            listaFaltas   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaFaltas = FaltasDAO.devolverTodasFaltas(con);
            Conexion.desconectar(con);

            return listaFaltas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarFalta(FaltasVO faltaVO)
    {
        Connection        con            = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = FaltasDAO.guardarFalta(faltaVO,con);
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
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los datos de falta de un alumno y una edición
    public static Vector devolverFaltasAluEdi(String codAlu , String codEdi)
    {
        Connection        con          = null;
        Vector            listaFaltas  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaFaltas = FaltasDAO.devolverFaltasAluEdi(codAlu, codEdi,con);
            Conexion.desconectar(con);

            return listaFaltas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve el número de faltas de un alumno y una edición
    public static int devolverNumFaltasAluEdi(String codAlu , String codEdi)
    {
        Connection        con          = null;
        int               numFaltas    = 0;

        try
        {
            con = Conexion.conectar();
            numFaltas = FaltasDAO.devolverNumFaltasAluEdi(codAlu, codEdi,con);
            Conexion.desconectar(con);

            return numFaltas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los datos de falta de una edición
    public static Vector devolverFaltasEdi(String codEdi)
    {
        Connection        con          = null;
        Vector            listaFaltas = new Vector();

        try
        {
            con = Conexion.conectar();
            listaFaltas = FaltasDAO.devolverFaltasEdi(codEdi,con);
            Conexion.desconectar(con);

            return listaFaltas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }


    //Edita el registro de una falta
    public static int editarFalta(FaltasVO faltaVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = FaltasDAO.editarFalta(faltaVO,con);
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
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra el registro de una falta
    public static int eliminaFalta (String codEdi, String codMod, String codAlu, java.util.Date fecha)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = FaltasDAO.eliminaFalta(codEdi, codMod, codAlu, fecha, con);
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
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra las faltas de un alumno
    public static int eliminarFaltasAlumno(String codAlu)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados= FaltasDAO.eliminarFaltasAlumno(codAlu,con);
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
                Logger.getLogger(FaltasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve si un alumno tiene faltas
    public static boolean tieneAluFaltas(String codAlu )
    {
        Connection        con            = null;
        boolean            aluTieneFaltas = false;

        try
        {
            con = Conexion.conectar();
            aluTieneFaltas = FaltasDAO.tieneAluFaltas(codAlu,con);
            Conexion.desconectar(con);

            return aluTieneFaltas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(FaltasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }

}
