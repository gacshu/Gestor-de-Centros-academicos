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

import es.jahernandez.accesodatos.ProfAreaDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ProfAreaVO;

/**
 *
 * @author Alberto
 */
public class ProfAreaGestion
{
    //Método que devuelve los datos de ProfesorArea
    public static Vector devolverTodosProfArea()
    {
        Connection       con            = null;
        Vector           listaProfArea  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaProfArea = ProfAreaDAO.devolverTodosProfArea(con);
            Conexion.desconectar(con);

            return listaProfArea;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve las areas de un profesor
    public static Vector devolverAreasProf(String codProf)
    {
        Connection       con            = null;
        Vector           listaProfArea  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaProfArea = ProfAreaDAO.devolverAreasProf(codProf,con);
            Conexion.desconectar(con);

            return listaProfArea;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }


     //Método que devuelve los nombre de lass áreas de un profesor
    public static String devolverNombresAreasProf(String codProf)
    {
        Connection       con          = null;
        String           nombresAreas = "";

        try
        {
            con = Conexion.conectar();
            nombresAreas = ProfAreaDAO.devolverNombresAreasProf(codProf,con);
            Conexion.desconectar(con);

            return nombresAreas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los profesores de un area
    public static Vector devolverProfArea(String codArea)
    {
        Connection       con            = null;
        Vector           listaProfArea  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaProfArea = ProfAreaDAO.devolverProfArea(codArea,con);
            Conexion.desconectar(con);

            return listaProfArea;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarProfArea(ProfAreaVO profAreaVO)
    {
        Connection con             = null;
        int        regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = ProfAreaDAO.guardarProfArea(profAreaVO,con);
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
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Elimina el registro de profesorArea
    public static int eliminaProfArea(String codProf, String codArea)
    {
        Connection        con    = null;
        int               regAct = 0;

        try
        {
            con = Conexion.conectar();
            regAct = ProfAreaDAO.eliminaProfArea(codProf, codArea,con);
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
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Elimina los registros de un profesor
    public static int eliminaAreasProf(String codProf)
    {
        Connection        con    = null;
        int               regAct = 0;

        try
        {
            con = Conexion.conectar();
            regAct = ProfAreaDAO.eliminaAreasProf(codProf,con);
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
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Elimina los registros de un área
    public static int eliminaProfArea(String codArea)
    {
        Connection        con    = null;
        int               regAct = 0;

        try
        {
            con = Conexion.conectar();
            regAct = ProfAreaDAO.eliminaProfArea(codArea, con);
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
                Logger.getLogger(ProfAreaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }
}

