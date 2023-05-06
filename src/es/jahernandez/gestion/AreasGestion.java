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

import es.jahernandez.accesodatos.AreasDAO;
import es.jahernandez.datos.AreasVO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author JuanAlberto
 */
public class AreasGestion
{
     //Método que devuelve los datos de Área
    public static Vector devolverTodAreas()
    {
        Connection        con            = null;
        Vector            listaAreas     = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAreas = AreasDAO.devolverTodAreas(con);
            Conexion.desconectar(con);

            return listaAreas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AreasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve el nombre de un área
    public static String devuelveNombreArea(String codArea)
    {
        Connection       con        = null;
        String           nombreArea = "";

        try
        {
            con = Conexion.conectar();
            nombreArea = AreasDAO.devuelveNombreArea(codArea, con);
            Conexion.desconectar(con);

            return nombreArea;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AreasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Genera un nuevo código de Área
    public static String generarNuevoCodArea()
    {
        String codIntrod = "";

        codIntrod = AreasDAO.generarNuevoCodArea();

        return codIntrod;
    }

    //Método que guarda un Área
    public static int guardarArea(AreasVO areaVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AreasDAO.guardarArea(areaVO, con);
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
                Logger.getLogger(AreasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Edita el registro de un Área
    public static int editarArea(AreasVO areaVO)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AreasDAO.editarArea(areaVO, con);
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
                Logger.getLogger(AreasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    public static Vector devolverAreasProf(String codProf)
    {
        Connection        con            = null;
        Vector            listaAreas     = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAreas = AreasDAO.devolverAreasProf(codProf,con);
            Conexion.desconectar(con);

            return listaAreas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AreasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
}
