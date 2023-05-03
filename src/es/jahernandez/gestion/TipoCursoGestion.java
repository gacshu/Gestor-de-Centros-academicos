/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;
import es.jahernandez.accesodatos.TipoCursoDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.TipoCursoVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto
 */
public class TipoCursoGestion 
{
    //Método que devuelve los datos a mostrar en los combos de Tipo de Curso
    public static Vector devolverDatosTipCur()
    {
        Connection        con           = null;
        Vector            listaTipCur   = new Vector();
        
        try
        {
            con = Conexion.conectar();
            listaTipCur = TipoCursoDAO.devolverDatosTipCur(con);
            Conexion.desconectar(con);

            return listaTipCur;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoCursoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos a mostrar en los combos de Tipo de Curso
    public static TipoCursoVO devolverTipCur(int codTipo)
    {
        Connection        con           = null;
        TipoCursoVO       tipCursoVO    = null;

        try
        {
            con = Conexion.conectar();
            tipCursoVO = TipoCursoDAO.devolverTipCur(codTipo,con);
            Conexion.desconectar(con);

            return tipCursoVO;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoCursoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Mátodo que devuelve el nombre de un tipo de curso
    public static String devuelveNombreTipo(int codTipoCur) 
    {
        Connection       con       = null;
        String           nombreTip = "";

        try
        {
            con = Conexion.conectar();
            nombreTip = TipoCursoDAO.devuelveNombreTipo(codTipoCur,con);
            Conexion.desconectar(con);

            return nombreTip;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoCursoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Método que genera un nuevo código de tipo curso
    public static int generarNuevoCodTipCur()
    {
        Connection        con      = null;
        int               nuevCod  = -99;

        try
        {
            con = Conexion.conectar();
            nuevCod = TipoCursoDAO.generarNuevoCodTipCur(con);
            Conexion.desconectar(con);

            return nuevCod;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoCursoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }
    
    //Método que guarda un tipo de curso
    public static int guardarTipoCurso(TipoCursoVO tipCurVO)
    {
        Connection        con    = null;
        int               regAct = -99;

        try
        {
            con = Conexion.conectar();
            regAct = TipoCursoDAO.guardarTipoCurso(tipCurVO,con);
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
                Logger.getLogger(TipoCursoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
    
    //Edita el registro de un tipo de curso
    public static int editarTipoCurso(TipoCursoVO tipCurVO)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {

            con = Conexion.conectar();
            regActualizados = TipoCursoDAO.editarTipoCurso(tipCurVO,con);
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
                Logger.getLogger(TipoCursoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
}