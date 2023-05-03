/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import es.jahernandez.accesodatos.ActividadesDAO;
import es.jahernandez.datos.ActividadesVO;
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
public class ActividadesGestion 
{
    //Devuelve el VALOR MAXIMO de código de código de Actividad
    public static int devuelveMaxAct()
    {
        Connection        con            = null;
    
        int               valorMax       = -1;

    
        try
        {
            con = Conexion.conectar();
            valorMax = ActividadesDAO.devuelveMaxAct(con);
            Conexion.desconectar(con);
            
            return  valorMax;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ActividadesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Devuelve un nuevo código de actividad
    public static int nuevoCodigo()
    {
        int nueCodActividad = 0;

        nueCodActividad = devuelveMaxAct();

        if(nueCodActividad  != -1)
        {
            return nueCodActividad + 1;
        }
        else
        {
            return -1;
        }

    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarTipoAct(ActividadesVO actVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            
            regActualizados = ActividadesDAO.guardarTipoAct(actVO,con);
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
                Logger.getLogger(ActividadesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Edita el registro de una actividad
    public static int editaTipoAct(ActividadesVO actVO)
    {
        Connection        con             = null;
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
          
            regActualizados = ActividadesDAO.editaTipoAct(actVO, con);

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
                Logger.getLogger(ActividadesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Borra el registro de una actividad
    public static int borraActividad(int idAct )
    {
        Connection        con             = null;
 
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = ActividadesDAO.borraActividad(idAct, con);
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
                Logger.getLogger(ActividadesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Devuelve el nombre de tipo de actividad
    public static String devuelveNombreActividad(int idActividad)
    {
        Connection        con            = null;
        
        String            nombAct        = "";

        try
         {
             con = Conexion.conectar();

             nombAct = ActividadesDAO.devuelveNombreActividad(idActividad, con);

             Conexion.desconectar(con);

             return nombAct;

         }
         catch (Exception exc)
         {
             try
             {
                 Conexion.desconectar(con);
             }
             catch (SQLException ex)
             {
                 Logger.getLogger(ActividadesGestion.class.getName()).log(Level.SEVERE, null, ex);
             }

             return null;
         }


    }

    //Método que devuelve los datos a mostrar en los combos de Actividades
    public static Vector devolverDatActividades()
    {
        Connection        con           = null;
        Vector            listaAct      = new Vector();
       
        try
        {
            con = Conexion.conectar();
            listaAct = ActividadesDAO.devolverDatActividades(con);
            Conexion.desconectar(con);

            return listaAct;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
}
