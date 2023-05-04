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

import es.jahernandez.accesodatos.CalificacionesDAO;
import es.jahernandez.datos.CalificacionesVO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author JuanAlberto
 */
public class CalificacionesGestion 
{
    //Método que devuelve los datos de una calificacion 
    public static CalificacionesVO devolverDatosCalificacion(String codEdi, String codMod, String codAlu, int evaluacion)
    {
        Connection        con           = null;
        CalificacionesVO  califVO       = new CalificacionesVO();

        try
        {
            con = Conexion.conectar();
            califVO = CalificacionesDAO.devolverDatosCalificacion(codEdi, codMod, codAlu, evaluacion, con); 
            Conexion.desconectar(con);

            return califVO;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve todos los registro de calificaciones
    public static Vector devolverTodasCalificaciones()
    {
        Connection        con           = null;
        Vector            listaCalif    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCalif = CalificacionesDAO.devolverTodasCalificaciones(con);
            Conexion.desconectar(con);

            return listaCalif;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarCalificacion(CalificacionesVO califVO)
    {
        Connection        con            = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CalificacionesDAO.guardarCalificacion(califVO, con);
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
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los datos de calificaciones de un alumno y una edición
    public static Vector devolverCalificacionesAluEdi(String codAlu , String codEdi)
    {
        Connection        con          = null;
        Vector            listaCalif  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCalif = CalificacionesDAO.devolverCalificacionesAluEdi(codAlu, codEdi, con);
            Conexion.desconectar(con);

            return listaCalif;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
     //Método que devuelve los datos de calificaciones de una edición
    public static Vector devolverCalificacionesEdi(String codEdi)
    {
        Connection        con          = null;
        Vector            listaCalif  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCalif = CalificacionesDAO.devolverCalificacionesEdi(codEdi, con);
            Conexion.desconectar(con);

            return listaCalif;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
   
    //Método que devuelve los datos de calificaciones de un alumno y una edición y evaluacion
    public static Vector devolverCalificacionesAluEdiEva(String codAlu , String codEdi, int codEv)
    {
        Connection        con         = null;
        Vector            listaCalif  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCalif = CalificacionesDAO.devolverCalificacionesAluEdiEva(codAlu, codEdi, codEv, con);
            Conexion.desconectar(con);

            return listaCalif;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
   //Método que devuelve los datos de calificaciones de una edición y evaluacion
    public static Vector devolverCalificacionesEdiEva(String codEdi, int codEv)
    {
        Connection        con          = null;
        Vector            listaCalif  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCalif = CalificacionesDAO.devolverCalificacionesEdiEva(codEdi, codEv, con);
            Conexion.desconectar(con);

            return listaCalif;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Edita el registro de una calificación
    public static int editarCalificacion(CalificacionesVO califVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CalificacionesDAO.editarCalificacion(califVO, con);
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
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra el registro de una calificación
    public static int eliminaCalificacion (String codEdi, String codMod, String codAlu, int evaluacion)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CalificacionesDAO.eliminaCalificacion(codEdi, codMod, codAlu, evaluacion, con);
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
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }     
    
    //Borra las calificaciones de un alumno
    public static int eliminarCalificAlumno(String codAlu)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        
        try
        {
            con = Conexion.conectar();
            regActualizados = CalificacionesDAO.eliminarCalificAlumno(codAlu, con);
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
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    } 
    
    //Método que devuelve si un alumno tiene calificaciones
    public static boolean tieneAluClasesInd(String codAlu )
    {
        Connection        con          = null;
        boolean           aluTienCalif = false; 
     
        try
        {
            con = Conexion.conectar();
            aluTienCalif = CalificacionesDAO.tieneAluClasesInd(codAlu, con);
            Conexion.desconectar(con);

            return aluTienCalif;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalificacionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }
    
}
