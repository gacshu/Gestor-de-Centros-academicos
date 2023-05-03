/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ProfAreaVO;
import es.jahernandez.gestion.AreasGestion;
import es.jahernandez.tablas.TablaProfArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

/**
 *
 * @author JuanAlberto
 */
public class ProfAreaDAO 
{
    //Método que devuelve los datos de ProfesorArea
    public static Vector devolverTodosProfArea(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaProfArea.CODPROF  + " , " 
                                                     + TablaProfArea.CODAREA  +  
                                           " FROM "  + TablaProfArea.TABLA;
        
        ProfAreaVO       datProfNiv     = null;
        Vector           listaProfArea  = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datProfNiv = new ProfAreaVO();

                datProfNiv.setCodProf(rs.getString(TablaProfArea.CODPROF));
                datProfNiv.setCodArea(rs.getString(TablaProfArea.CODAREA));
                
                listaProfArea.add(datProfNiv);
            }

            rs.close();
            ps.close();

            return listaProfArea;
        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();              
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve las areas de un profesor 
    public static Vector devolverAreasProf(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaProfArea.CODPROF + " , " 
                                                     + TablaProfArea.CODAREA +  
                                           " FROM "  + TablaProfArea.TABLA   +
                                           " WHERE " + TablaProfArea.CODPROF + " = ? ";
        
        ProfAreaVO       datProfNiv     = null;
        Vector           listaProfArea  = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            ps.setString(1, codProf);
            
            rs  = ps.executeQuery();

            while(rs.next())
            {
                datProfNiv = new ProfAreaVO();

                datProfNiv.setCodProf(rs.getString(TablaProfArea.CODPROF));
                datProfNiv.setCodArea(rs.getString(TablaProfArea.CODAREA));
                
                listaProfArea.add(datProfNiv);
            }

            rs.close();
            ps.close();

            return listaProfArea;
        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    
     //Método que devuelve los nombre de lass áreas de un profesor 
    public static String devolverNombresAreasProf(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaProfArea.CODPROF + " , " 
                                                     + TablaProfArea.CODAREA +  
                                           " FROM "  + TablaProfArea.TABLA   +
                                           " WHERE " + TablaProfArea.CODPROF + " = ? ";
        
        ProfAreaVO       datProfNiv      = null;
        String           nombresAreas    = ""; 

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            ps.setString(1, codProf);
            
            rs  = ps.executeQuery();

            while(rs.next())
            {
                nombresAreas = nombresAreas + AreasGestion.devuelveNombreArea(rs.getString(TablaProfArea.CODAREA)) + " ";
            }

            rs.close();
            ps.close();
            
            return nombresAreas;
        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            throw exc;
        }
    }
    
    //Método que devuelve los profesores de un area 
    public static Vector devolverProfArea(String codArea,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaProfArea.CODPROF + " , " 
                                                     + TablaProfArea.CODAREA +  
                                           " FROM "  + TablaProfArea.TABLA   +
                                           " WHERE " + TablaProfArea.CODAREA + " = ? ";
        
        ProfAreaVO       datProfNiv     = null;
        Vector           listaProfArea  = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            ps.setString(1, codArea);
            
            rs  = ps.executeQuery();

            while(rs.next())
            {
                datProfNiv = new ProfAreaVO();

                datProfNiv.setCodProf(rs.getString(TablaProfArea.CODPROF));
                datProfNiv.setCodArea(rs.getString(TablaProfArea.CODAREA));
                
                listaProfArea.add(datProfNiv);
            }

            rs.close();
            ps.close();

            return listaProfArea;
        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
  
    //Método que guarda un nuevo registro en la base de datos
    public static int guardarProfArea(ProfAreaVO profAreaVO,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        
        String            cadenaConsulta = "INSERT INTO " + TablaProfArea.TABLA    + " ( "  
                                                          + TablaProfArea.CODPROF  + " , "  
                                                          + TablaProfArea.CODAREA  + " ) " + 
                                           " VALUES(?,?)";
        
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, profAreaVO.getCodProf());
            ps.setString(2, profAreaVO.getCodArea());

            regActualizados = ps.executeUpdate();

            ps.close();
            
            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Elimina el registro de profesoRArea
    public static int eliminaProfArea(String codProf, String codArea,Connection con) throws Exception
    {
        PreparedStatement ps     = null;

        String            sql    = "DELETE FROM " + TablaProfArea.TABLA   + 
                                   " WHERE "      + TablaProfArea.CODPROF + " = ? AND " 
                                                  + TablaProfArea.CODAREA + " = ? " ; 
        int               regAct = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codProf);
            ps.setString(2, codArea);
                       
            regAct = ps.executeUpdate();

            ps.close();
            
            return regAct;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }   
    
    //Elimina los registros de un profesor
    public static int eliminaAreasProf(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps     = null;

        String            sql    = "DELETE FROM " + TablaProfArea.TABLA   + 
                                   " WHERE "      + TablaProfArea.CODPROF + " = ? " ; 
        int               regAct = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codProf);
                       
            regAct = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            return regAct;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }   
    
    //Elimina los registros de un área
    public static int eliminaProfArea(String codArea,Connection con) throws Exception
    {
        PreparedStatement ps     = null;

        String            sql    = "DELETE FROM " + TablaProfArea.TABLA   + 
                                   " WHERE "      + TablaProfArea.CODAREA + " = ? " ; 
        int               regAct = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codArea);
                       
            regAct = ps.executeUpdate();

            ps.close();
            
            return regAct;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfAreaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }   
}
