/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.accesodatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.datos.ControlRecVO;
import es.jahernandez.tablas.TablaControlRecibos;

/**
 *
 * @author JuanAlberto
 */
public class ControlRecDAO
{
    //Devuelve si si han generado los recibos de un mes en un determinado centro
    public static boolean generadosRecMes(String fecha, int codCen, Connection con) throws Exception
    {

        PreparedStatement ps    = null; 

        String            sql   = "SELECT " + TablaControlRecibos.CODCENTRO + " , " 
                                            + TablaControlRecibos.FECHA     +
                                 " FROM "   + TablaControlRecibos.TABLA     +
                                 " WHERE "  + TablaControlRecibos.FECHA     + " = ? AND " 
                                            + TablaControlRecibos.CODCENTRO + " = ? "  ;
                
        ResultSet        rs     = null;
        boolean          recGen = false;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, fecha);
            ps.setInt   (2, codCen);

            rs = ps.executeQuery();

            if (rs.next()) //Existe
            {
                recGen = true;
            }
            else
            {
                recGen = false;
            }
           
            rs.close();
            ps.close();
            
            return recGen;
            
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
                Logger.getLogger(ControlRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
}


   
    //Método que guarda un nuevo registro en la base de datos
    public static int guardarControlRec(ControlRecVO contRecVO, Connection con) throws Exception
    {
        PreparedStatement ps     = null; 

        String            sql    = "INSERT INTO " + TablaControlRecibos.TABLA     + " ( " 
                                                  + TablaControlRecibos.FECHA     + " , "
                                                  + TablaControlRecibos.CODCENTRO + " ) " +
                                   "VALUES (?,?)";                     
                
        int               regAct = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, contRecVO.getFecha());
            ps.setInt   (2, contRecVO.getIdCentro());

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
                Logger.getLogger(ControlRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
    
    //Devuelve si si han generado los recibos
    public static boolean generadosRec(String fecha, Connection con) throws Exception
    {
        PreparedStatement ps     = null; 
        ResultSet         rs     = null; 

        String            sql    = "SELECT " + TablaControlRecibos.CODCENTRO + " , " 
                                             + TablaControlRecibos.FECHA     +
                                 " FROM "    + TablaControlRecibos.TABLA     +
                                 " WHERE "   + TablaControlRecibos.FECHA     + " = ? "; 
                                             
                
        boolean           recGen = false;
       
        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, fecha);

            rs = ps.executeQuery();

            if (rs.next()) //Existe
            {
                recGen = true;
            }
            else
            {
                recGen = false;
            }

            rs.close();
            ps.close();

            return recGen;
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
                Logger.getLogger(ControlRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
}

