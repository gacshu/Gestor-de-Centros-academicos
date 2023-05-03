/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;
import es.jahernandez.accesodatos.SegEmpDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.SegEmpVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto
 */
public class SegEmpGestion 
{
    //Método que devuelve los datos de un seguimiento
    public static SegEmpVO devolverDatosSeg(String codSeg)
    {
        Connection        con = null;
        SegEmpVO datSeg = null;

        try
        {
            con = Conexion.conectar();
            datSeg = SegEmpDAO.devolverDatosSeg(codSeg,con);
            Conexion.desconectar(con);

            return datSeg;
        }
        catch (Exception exp)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve los datos de todos los seguimientos
    public static Vector devolverTodosSeg()
    {

        Connection        con      = null;
        Vector            listaSeg = new Vector();

        try
        {
            con = Conexion.conectar();
            listaSeg = SegEmpDAO.devolverTodosSeg(con);
            Conexion.desconectar(con);

            return listaSeg;
        }
        catch (Exception exp)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que genera un nuevo código de seguimiento
    public static String generarNuevoCodSeg()
    {
        return SegEmpDAO.generarNuevoCodSeg();
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarSeg(SegEmpVO segVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;
        
        try
        {
            con = Conexion.conectar();
            regActualizados = SegEmpDAO.guardarSeg(segVO,con);
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
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Edita el registro de un seguimiento
    public static int editaSeg(SegEmpVO segVO)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {

            con = Conexion.conectar();
            regActualizados = SegEmpDAO.editaSeg(segVO,con);
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
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los datos de seguimiento de una empresa
    public static Vector devolverSegEmp(String codEmp)
    {
       Connection        con      = null;
       Vector            listaSeg = new Vector();

       try
       {
            con = Conexion.conectar();
            listaSeg = SegEmpDAO.devolverSegEmp(codEmp,con);
            Conexion.desconectar(con);

            return listaSeg;
       }
       catch (Exception exp)
       {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
       }
    }

    //Método que elimina todos los seguimientos de una empresa
    public static int eliminaSegEmp(String codEmp)
    {

        Connection        con             = null;
        int               regActualizados = 0;
        
        try
        {
            con = Conexion.conectar();
            regActualizados = SegEmpDAO.eliminaSegEmp(codEmp,con);
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
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
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
            regActualizados = SegEmpDAO.eliminaSeg(codSeg,con);
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
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve el número de seguimientos de una empresa
    public static int devolverNumSeg(String codEmp)
    {
        Connection        con            = null;
        int               numSeg         = 0;

        try
        {
            con = Conexion.conectar();
            numSeg = SegEmpDAO.devolverNumSeg(codEmp,con);
            Conexion.desconectar(con);

            return numSeg;
        }

        catch (Exception exp)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
}
