/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.ActividadesVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.gestion.ActividadesGestion;

import es.jahernandez.tablas.TablaActividades; 

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
public class ActividadesDAO
{
    //Devuelve el VALOR MAXIMO de código de código de Actividad
    public static int devuelveMaxAct(Connection con) throws Exception
    {
        //Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT MAX(CONVERT(INT,idAct)) FROM TbAct";
        String            cadenaConsulta = "SELECT MAX(" + TablaActividades.CODIGO + ") AS " + TablaActividades.CODIGO +  
                                           " FROM "      + TablaActividades.TABLA;
        
        int               valorMax       = 0;

        try
        {
            //con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);
            rs = ps.executeQuery();

            if(rs.next())
            {
                valorMax = rs.getInt(TablaActividades.CODIGO);
            }

            rs.close();
            ps.close();
            //Conexion.desconectar(con);

            return valorMax;

        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
                
                
                //Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw  exc;
            
        }

    }

    //Devuelve un nuevo código de actividad
    public static int nuevoCodigo()
    {
        int nueCodActividad = 0;
        
        try
        {
            nueCodActividad = ActividadesGestion.devuelveMaxAct();
            
            return nueCodActividad + 1;
            
        }
        catch(Exception exc)
        {
            return -1;
        }

    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarTipoAct(ActividadesVO actVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            cadenaConsulta  = "INSERT INTO " + TablaActividades.TABLA + " (" 
                                                           + TablaActividades.CODIGO + " , " 
                                                           + TablaActividades.NOMBRE + " ) " + 
                                            " VALUES(?,?)";
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros de la consulta sql
            ps.setInt(1,nuevoCodigo());
            ps.setString(2, actVO.getNombreActividad());

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
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw  exc;
        }
    }

    //Edita el registro de una actividad
    public static int editaTipoAct(ActividadesVO actVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        //String            cadenaConsulta  = "UPDATE TbAct SET  NOMACT = ? WHERE IDACT = ?";
        String            cadenaConsulta  = "UPDATE " + TablaActividades.TABLA  + 
                                            " SET "   + TablaActividades.NOMBRE + " = ? " + 
                                            " WHERE " + TablaActividades.CODIGO + " = ?";
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, actVO.getNombreActividad());
            ps.setInt(2,actVO.getCodActividad());

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
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Borra el registro de una actividad
    public static int borraActividad(int idAct, Connection con ) throws Exception
    {
        PreparedStatement ps              = null;

        //String            cadenaConsulta  = "DELETE FROM TbAct WHERE idAct = ?";
        String            cadenaConsulta  = "DELETE FROM " + TablaActividades.TABLA  + 
                                            " WHERE "      + TablaActividades.CODIGO + " = ?";
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros de la consulta sql
            ps.setInt(1,idAct);

            regActualizados = ps.executeUpdate();

            ps.close();

            return regActualizados;
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
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Devuelve el nombre de tipo de actividad
    public static String devuelveNombreActividad(int idActividad, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT NomAct FROM TbAct WHERE IdAct = ? ";
        String            cadenaConsulta = "SELECT " + TablaActividades.NOMBRE + 
                                           " FROM "  + TablaActividades.TABLA  + 
                                           " WHERE " + TablaActividades.CODIGO + " = ? ";
        
        String            nombAct        = "";

       try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se le pasan los parámetros a la consulta
            ps.setInt(1, idActividad);

            rs = ps.executeQuery();

            if(rs.next())
            {
                nombAct = rs.getString(TablaActividades.NOMBRE);
            }

            rs.close();
            ps.close();
           
            return nombAct;

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
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }


    }

    //Método que devuelve los datos a mostrar en los combos de Actividades
    public static Vector devolverDatActividades(Connection con) throws Exception
    {
        PreparedStatement ps            = null;
        ResultSet         rs            = null;

        //String            sql           = "SELECT NomAct, IdAct FROM TbACt ORDER BY NomAct";
        String            sql           = "SELECT "    + TablaActividades.NOMBRE + "," 
                                                       + TablaActividades.CODIGO + 
                                          " FROM "     + TablaActividades.TABLA  + 
                                          " ORDER BY " + TablaActividades.NOMBRE;
        Vector            listaAct      = new Vector();
        ActividadesVO     actVO         = null;

        try
        {
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                actVO = new ActividadesVO();

                actVO.setNombreActividad(rs.getString(TablaActividades.NOMBRE));
                actVO.setCodActividad   (rs.getInt   (TablaActividades.CODIGO));

                listaAct.addElement(actVO);
            }

            rs.close();
            ps.close();

            return listaAct;
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
                Logger.getLogger(ActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
}
