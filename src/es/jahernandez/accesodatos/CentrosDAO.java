/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.datos.CentrosVO;
import es.jahernandez.gestion.CentrosGestion;
import es.jahernandez.tablas.TablaCentros;



/**
 *
 * @author Alberto
 */
public class CentrosDAO
{
    //Método que devuelve los datos a mostrar en los combos de centros
    public static Vector datComCentros(Connection con) throws Exception
    {

        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String            sql        = "SELECT "    + TablaCentros.CODCENTRO + ","
                                                    + TablaCentros.NOMBRE    +
                                       " FROM  "    + TablaCentros.TABLA     +
                                       " ORDER BY " + TablaCentros.NOMBRE;
        CentrosVO         centroVO   = null;

        Vector            listCentros = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                centroVO = new CentrosVO();
                centroVO.setIdCentro    (rs.getInt   (TablaCentros.CODCENTRO));
                centroVO.setNombreCentro(rs.getString(TablaCentros.NOMBRE));

                listCentros.addElement(centroVO);
            }

            rs.close();
            ps.close();

            return listCentros;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve el nombre de un centro
    public static String nomCentro(String idCen, Connection con) throws Exception
    {
        PreparedStatement ps      = null;
        ResultSet         rs      = null;

        String            sql     = "SELECT " + TablaCentros.NOMBRE    +
                                    " FROM  " + TablaCentros.TABLA     +
                                    " WHERE " + TablaCentros.CODCENTRO + " = ? ";

        String            strCen  = "";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se le pasan los parámetros a la consulta sql
            ps.setInt(1, new Integer(idCen).intValue());

            rs  = ps.executeQuery();


            if (rs.next())
            {
                strCen = rs.getString(TablaCentros.NOMBRE);
            }

            rs.close();
            ps.close();

            return strCen;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de un centro
    public static CentrosVO datCentro(int idCen, Connection con) throws Exception
    {
        PreparedStatement ps      = null;
        ResultSet         rs      = null;

        String            sql     = "SELECT " + TablaCentros.CODCENTRO + " ,"
                                              + TablaCentros.NOMBRE    +
                                    " FROM  " + TablaCentros.TABLA     +
                                    " WHERE " + TablaCentros.CODCENTRO + " = ? ";

        CentrosVO        cenVO    = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se le pasan los parámetros a la consulta sql
            ps.setInt(1, idCen);

            rs  = ps.executeQuery();


            if (rs.next())
            {
                cenVO    = new CentrosVO();

                cenVO.setIdCentro    (rs.getInt   (TablaCentros.CODCENTRO));
                cenVO.setNombreCentro(rs.getString(TablaCentros.NOMBRE));
            }

            rs.close();
            ps.close();

            return cenVO;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }


    //Devuelve el nuevo código de centro generado
    public static int generarNuevoCodCentro(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT MAX(" + TablaCentros.CODCENTRO + ") AS " + TablaCentros.CODCENTRO +
                                           " FROM "      + TablaCentros.TABLA;

        int               nuevoCod       = -1;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);
            rs = ps.executeQuery();

            if(rs.next())
            {
                nuevoCod = rs.getInt(TablaCentros.CODCENTRO) + 1;
            }

            rs.close();
            ps.close();

            return nuevoCod;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }

    //Método que guarda un Centro
    public static int guardarCentro(CentrosVO cenVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = 0;


        int               nueCodCentro    = CentrosGestion.generarNuevoCodCentro();

        String            sql             = "INSERT INTO " + TablaCentros.TABLA     + " ( "
                                                           + TablaCentros.CODCENTRO + " , "
                                                           + TablaCentros.NOMBRE    + " ) " +
                                            " VALUES (?,?)";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros a la consulta sql
            ps.setInt   (1, nueCodCentro);
            ps.setString(2, cenVO.getNombreCentro());

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Edita el registro de un Centro
    public static int editarCentro(CentrosVO cenVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = -1;

        String            sql             = "UPDATE " + TablaCentros.TABLA     +
                                            " SET "   + TablaCentros.NOMBRE    + " = ? " +
                                            " WHERE " + TablaCentros.CODCENTRO + " = ? ";
        try
        {

            ps  = con.prepareStatement(sql);

            //Se pasasn los parámetros a la consulta sql
            ps.setString(1, cenVO.getNombreCentro());
            ps.setInt   (2, cenVO.getIdCentro());

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

}
