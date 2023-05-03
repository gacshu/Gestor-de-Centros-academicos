/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import es.jahernandez.accesodatos.ActividadesDAO;
import es.jahernandez.accesodatos.AdapCurricularDAO;
import es.jahernandez.datos.ActividadesVO;
import es.jahernandez.datos.AdapCurricularVO;
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
public class AdapCurricularGestion 
{
    //Método que devuelve los datos de una adaptación curricular
    public static AdapCurricularVO devolverDatosAdapCur(String codAdapCurricular)
    {
        Connection        con            = null;
        AdapCurricularVO  adapCurVO      = new AdapCurricularVO();

        try
        {
            con = Conexion.conectar();           
            adapCurVO = AdapCurricularDAO.devolverDatosAdapCur(codAdapCurricular, con);
            Conexion.desconectar(con);

            return adapCurVO;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve todos los registro de adaptación curricular
    public static Vector devolverTodosAdapCur()
    {
        Connection        con            = null;
        Vector            listaAdapCur   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAdapCur = AdapCurricularDAO.devolverTodosAdapCur(con);
            Conexion.desconectar(con);

            return listaAdapCur;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarAdapCur(AdapCurricularVO adapCurVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AdapCurricularDAO.guardarAdapCur(adapCurVO , con);
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
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de adaptación curricular
    public static String nueCodAdapCur()
    {
        String  codIntrod  = "";

        codIntrod = AdapCurricularDAO.nueCodAdapCur();

        return codIntrod;
    }

    //Método que devuelve los datos de adaptación curricular de un alumno
    public static Vector devolverAdapCurAlu(String codAlu )
    {
        Connection        con          = null;

        Vector            listaAdapCur = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAdapCur = AdapCurricularDAO.devolverAdapCurAlu(codAlu, con );
            Conexion.desconectar(con);

            return listaAdapCur;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
     //Edita el registro de una adaptación curricular
    public static int editaAdapCur(AdapCurricularVO adapCurVO)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AdapCurricularDAO.editaAdapCur(adapCurVO, con);
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
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra el registro de una adaptación curricular
    public static int eliminaAdapCur(String codAdapCur)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        
        try
        {
            con = Conexion.conectar();
            regActualizados = AdapCurricularDAO.eliminaAdapCur(codAdapCur, con);
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
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }    
    
    //Borra las adaptaciones curriculares de un alumno
    public static int eliminaAdapCurAlumno(String codAlu)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        
        try
        {
            con = Conexion.conectar();
            regActualizados = AdapCurricularDAO.eliminaAdapCurAlumno(codAlu, con);
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
                Logger.getLogger(AdapCurricularGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }       
}
