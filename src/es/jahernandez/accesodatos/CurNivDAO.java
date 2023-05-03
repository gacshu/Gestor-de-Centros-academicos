/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.CurNivVO;
import es.jahernandez.gestion.CursosAluGestion;
import es.jahernandez.tablas.TablaActividades;

import es.jahernandez.tablas.TablaCursoNiveles;
import es.jahernandez.tablas.TablaNiveles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

/**
 *
 * @author Alberto
 */
public class CurNivDAO
{    
    //Método que guarda los niveles de un curso
    public static int guardarNivCur(String codCur, String codNiv, Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "INSERT INTO " + TablaCursoNiveles.TABLA + " (" + TablaCursoNiveles.CODCURSO + " , "
                                                                                + TablaCursoNiveles.CODNIV   + ")  " +  
                                " VALUES(?,?)";                
        int regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
            ps.setString(2, codNiv);
            
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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los niveles que tiene asociados un determinado curso
    public static Vector devolverCurNiv(String codCur, Connection con) throws Exception
    {

        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        String            sql         = "SELECT "      + TablaCursoNiveles.TABLA   + "." + TablaCursoNiveles.CODCURSO     + " , " 
                                                       + TablaCursoNiveles.TABLA   + "." + TablaCursoNiveles.CODNIV       + 
                                        " FROM "       + TablaCursoNiveles.TABLA   + 
                                        " INNER JOIN " + TablaNiveles.TABLA        + " ON " + TablaCursoNiveles.TABLA     + "."     + TablaCursoNiveles.CODNIV + " = " 
                                                                                            + TablaNiveles.TABLA          + "."     + TablaNiveles.CODNIVEL    + 
                                        " WHERE ("     + TablaCursoNiveles.TABLA   + "."    + TablaCursoNiveles.CODCURSO  + "= ?) " +
                                        " ORDER BY "   + TablaNiveles.TABLA        + "."    + TablaNiveles.NOMBRE;
        
        CurNivVO          datNivCur   = null;

        Vector            listaNivCur = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codCur);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datNivCur = new CurNivVO();

                datNivCur.setIdCur(rs.getString(TablaCursoNiveles.CODCURSO));
                datNivCur.setIdNiv(rs.getString(TablaCursoNiveles.CODNIV));

                listaNivCur.add(datNivCur);
            }

            rs.close();
            ps.close();
           
            return listaNivCur;
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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que elimina un nivel de un curso
    public static int eliminarNivCur(String codNiv, String codCur, Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "DELETE FROM " + TablaCursoNiveles.TABLA    +
                                " WHERE "      + TablaCursoNiveles.CODCURSO + " = ? AND "  
                                               + TablaCursoNiveles.CODNIV   + " = ? ";
        
        int regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
            ps.setString(2, codNiv);

            regActualizados = ps.executeUpdate();

            ps.close();
            
            //Se actualiza los niveles de interes de los  alumnos
            CursosAluGestion.ediNivCurAlu(codCur);

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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Borra los datos de nivel de un curso
    public static int borraCurNiv(String codCur, Connection con) throws Exception
    {
        PreparedStatement ps     = null;

        String            sql    = "DELETE FROM " + TablaCursoNiveles.TABLA    + 
                                   " WHERE  "     + TablaCursoNiveles.CODCURSO + " = ? ";
        
        int               regAct = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
                        
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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Borra todos los datos de un curso
    public static int borrarTodNivCur(String codCur, Connection con) throws Exception
    {
        PreparedStatement ps     = null;

        String            sql    = "DELETE FROM " + TablaCursoNiveles.TABLA    +  
                                   " WHERE  "     + TablaCursoNiveles.CODCURSO +  " = ? ";
        
        int               regAct = 0;
        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);

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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

}
