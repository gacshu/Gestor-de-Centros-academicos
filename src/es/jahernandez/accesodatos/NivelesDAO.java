/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.NivelesVO;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.NivelesGestion;

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
public class NivelesDAO
{
    //Método que devuelve los datos de un nivel
    public static NivelesVO devolverDatosNivel(String codNiv,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaNiveles.CODNIVEL  + " , " 
                                                     + TablaNiveles.NOMBRE    + " , "
                                                     + TablaNiveles.CONTENIDO + " , "
                                                     + TablaNiveles.CODCURSO  + 
                                           " FROM "  + TablaNiveles.TABLA     +
                                           " WHERE " + TablaNiveles.CODNIVEL  + " = ?";
        
        NivelesVO         datNiv         = new NivelesVO();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codNiv);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                datNiv = new NivelesVO();

                datNiv.setIdNiv     (rs.getString(TablaNiveles.CODNIVEL));
                datNiv.setNomNiv    (rs.getString(TablaNiveles.NOMBRE));
                datNiv.setContenidos(rs.getString(TablaNiveles.CONTENIDO));
                datNiv.setCodCur    (rs.getString(TablaNiveles.CODCURSO));
            }

            rs.close();
            ps.close();

            return datNiv;

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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de nivel
    public static Vector devolverTodosNiv(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaNiveles.CODNIVEL  + " , " 
                                                     + TablaNiveles.NOMBRE    + " , "
                                                     + TablaNiveles.CONTENIDO + " , "
                                                     + TablaNiveles.CODCURSO  + 
                                           " FROM "  + TablaNiveles.TABLA;
        
        NivelesVO         datNiv         = null;
        Vector            listaNiveles   = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datNiv = new NivelesVO();

                datNiv.setIdNiv     (rs.getString(TablaNiveles.CODNIVEL));
                datNiv.setNomNiv    (rs.getString(TablaNiveles.NOMBRE));
                datNiv.setContenidos(rs.getString(TablaNiveles.CONTENIDO));
                datNiv.setCodCur    (rs.getString(TablaNiveles.CODCURSO));

                listaNiveles.add(datNiv);
            }

            rs.close();
            ps.close();

            return listaNiveles;
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarNivel(NivelesVO nivVO,Connection con) throws Exception
    {
        String            nueCodNiv      = generarNuevoCodNiv();
        PreparedStatement ps             = null;
        
        String            cadenaConsulta = "INSERT INTO " + TablaNiveles.TABLA     + " ( "  
                                                          + TablaNiveles.CODNIVEL  + " , " 
                                                          + TablaNiveles.NOMBRE    + " , "  
                                                          + TablaNiveles.CONTENIDO + " , " 
                                                          + TablaNiveles.CODCURSO  + " ) " + 
                                           " VALUES(?,?,?,?)";
        
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, nueCodNiv);
            ps.setString(2, nivVO.getNomNiv());
            ps.setString(3, nivVO.getContenidos());
            ps.setString(4, nivVO.getCodCur());

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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que genera un nuevo código de nivel
    public static String generarNuevoCodNiv()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datNiv    = NivelesGestion.devolverTodosNiv();

        while (enc)
        {
            contCar = new Integer(datNiv.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datNiv.size() + avc).toString();
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 8)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datNiv = NivelesGestion.devolverTodosNiv();
            for (int ind = 0; ind < datNiv.size(); ind++)
            {
                NivelesVO nivVO = (NivelesVO) datNiv.elementAt(ind);
                if (nivVO.getIdNiv().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }
            avc = avc + 1;
        }
        return codIntrod;
    }

    //Método que devuelve los datos de nivel de un determinado curso
    public static Vector devolverNivCur(String codCur ,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            sql          = "SELECT " + TablaNiveles.CODNIVEL  + " , " 
                                                   + TablaNiveles.NOMBRE    + " , "
                                                   + TablaNiveles.CONTENIDO + " , "
                                                   + TablaNiveles.CODCURSO  + 
                                         " FROM "  + TablaNiveles.TABLA     +
                                         " WHERE " + TablaNiveles.CODCURSO  + " = ?"; 
        NivelesVO         datNiv       = null;
        Vector            listaNiveles = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codCur);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datNiv = new NivelesVO();

                datNiv.setIdNiv     (rs.getString(TablaNiveles.CODNIVEL));
                datNiv.setNomNiv    (rs.getString(TablaNiveles.NOMBRE));
                datNiv.setContenidos(rs.getString(TablaNiveles.CONTENIDO));
                datNiv.setCodCur    (rs.getString(TablaNiveles.CODCURSO));

                listaNiveles.add(datNiv);
            }

            rs.close();
            ps.close();
            
            return listaNiveles;
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
    
     //Edita el registro de un curso
    public static int editaNivel(NivelesVO nivVO,Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "UPDATE " + TablaNiveles.TABLA     +
                                " SET "   + TablaNiveles.NOMBRE    + " = ? , " 
                                          + TablaNiveles.CONTENIDO + " = ?   " +
                                " WHERE " + TablaNiveles.CODNIVEL  + " = ?";
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, nivVO.getNomNiv());
            ps.setString(2, nivVO.getContenidos());
            ps.setString(3, nivVO.getIdNiv());
           
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Edita el registro de un curso
    public static int eliminaNivel(String codNivel,Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "DELETE FROM " + TablaNiveles.TABLA  + 
                                " WHERE "      + TablaNiveles.CODNIVEL + " = ?";
        
        int               regActualizados = 0;

        
        if(EdicionesGestion.estaNivelenEdicion(codNivel))
        {
            return -2;
        }
        
        
        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codNivel);
                       
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }        
}
