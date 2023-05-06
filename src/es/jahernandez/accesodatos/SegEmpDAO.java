/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.SegEmpVO;
import es.jahernandez.gestion.SegEmpGestion;
import es.jahernandez.tablas.TablaSeguimientoEmpresas;

/**
 *
 * @author Alberto
 */
public class SegEmpDAO
{
    //Método que devuelve los datos de un seguimiento
    public static SegEmpVO devolverDatosSeg(String codSeg, Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " , "
                                          + TablaSeguimientoEmpresas.CODEMPRESA     + " , "
                                          + TablaSeguimientoEmpresas.INCIDENCIAS    + " , "
                                          + TablaSeguimientoEmpresas.FECHA          + " , "
                                          + TablaSeguimientoEmpresas.USUARIO        +
                                " FROM "  + TablaSeguimientoEmpresas.TABLA          +
                                " WHERE " + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " = ? " ;

        SegEmpVO datSeg = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros de la sentencia sql
            ps.setString(1, codSeg);

            rs = ps.executeQuery();

            if (rs.next())
            {
                datSeg = new SegEmpVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientoEmpresas.CODSEGUIMIENTO));
                datSeg.setIdEmp      (rs.getString(TablaSeguimientoEmpresas.CODEMPRESA));
                datSeg.setIncidencias(rs.getString(TablaSeguimientoEmpresas.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientoEmpresas.FECHA));
                datSeg.setUsuario    (rs.getString(TablaSeguimientoEmpresas.USUARIO));
            }

            rs.close();
            ps.close();

            return datSeg;

        }
        catch (Exception exp)
        {
            try
            {
                rs.close();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exp;
        }
    }

    //Método que devuelve los datos de todos los seguimientos
    public static Vector devolverTodosSeg(Connection con) throws Exception
    {
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        String            sql = "SELECT " + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " , "
                                          + TablaSeguimientoEmpresas.CODEMPRESA     + " , "
                                          + TablaSeguimientoEmpresas.INCIDENCIAS    + " , "
                                          + TablaSeguimientoEmpresas.FECHA          + " , "
                                          + TablaSeguimientoEmpresas.USUARIO        +
                                " FROM "  + TablaSeguimientoEmpresas.TABLA;

        SegEmpVO          datSeg   = null;

        Vector            listaSeg = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datSeg = new SegEmpVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientoEmpresas.CODSEGUIMIENTO));
                datSeg.setIdEmp      (rs.getString(TablaSeguimientoEmpresas.CODEMPRESA));
                datSeg.setIncidencias(rs.getString(TablaSeguimientoEmpresas.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientoEmpresas.FECHA ));
                datSeg.setUsuario    (rs.getString(TablaSeguimientoEmpresas.USUARIO));

                listaSeg.addElement(datSeg);
            }

            rs.close();
            ps.close();

            return listaSeg;
        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }

    //Método que genera un nuevo código de seguimiento
    public static String generarNuevoCodSeg()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datSeg = SegEmpGestion.devolverTodosSeg();


        while (enc)
        {
            contCar = (""+ datSeg.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datSeg.size() + avc);
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 10)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datSeg = SegEmpGestion.devolverTodosSeg();
            for (int ind = 0; ind < datSeg.size(); ind++)
            {
                SegEmpVO segVO = (SegEmpVO) datSeg.elementAt(ind);
                if (segVO.getCodSeg().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarSeg(SegEmpVO segVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = 0;
        String            nueCodSeg       = generarNuevoCodSeg();

        String            sql             = "INSERT INTO " + TablaSeguimientoEmpresas.TABLA          + " ( "
                                                           + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " , "
                                                           + TablaSeguimientoEmpresas.CODEMPRESA     + " , "
                                                           + TablaSeguimientoEmpresas.INCIDENCIAS    + " , "
                                                           + TablaSeguimientoEmpresas.FECHA          + " , "
                                                           + TablaSeguimientoEmpresas.USUARIO        + " ) " +
                                            " VALUES (?,?,?,?,?)";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros a la consulta sql
            ps.setString(1, nueCodSeg);
            ps.setString(2, segVO.getIdEmp());
            ps.setString(3, segVO.getIncidencias());
            ps.setDate(4, new Date(segVO.getFecha().getTime()));
            ps.setString(5, segVO.getUsuario());

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
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Edita el registro de un seguimiento
    public static int editaSeg(SegEmpVO segVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = 0;

        String            sql             = "UPDATE " + TablaSeguimientoEmpresas.TABLA          +
                                            " SET "   + TablaSeguimientoEmpresas.INCIDENCIAS    + " = ? , "
                                                      + TablaSeguimientoEmpresas.FECHA          + " = ? , "
                                                      + TablaSeguimientoEmpresas.USUARIO        + " = ?   " +
                                            " WHERE " + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " = ? ";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasasn los parámetros a la consulta sql
            ps.setString(1, segVO.getIncidencias());
            ps.setDate  (2, new Date(segVO.getFecha().getTime()));
            ps.setString(3, segVO.getUsuario());
            ps.setString(4, segVO.getCodSeg());

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
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de seguimiento de una empresa
    public static Vector devolverSegEmp(String codEmp, Connection con) throws Exception
    {
       PreparedStatement ps       = null;
       ResultSet         rs       = null;

       String            sql      = "SELECT "    + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " , "
                                                 + TablaSeguimientoEmpresas.CODEMPRESA     + " , "
                                                 + TablaSeguimientoEmpresas.INCIDENCIAS    + " , "
                                                 + TablaSeguimientoEmpresas.FECHA          + " , "
                                                 + TablaSeguimientoEmpresas.USUARIO        +
                                    " FROM "     + TablaSeguimientoEmpresas.TABLA          +
                                    " WHERE "    + TablaSeguimientoEmpresas.CODEMPRESA     + " = ? " +
                                    " ORDER BY " + TablaSeguimientoEmpresas.FECHA          + " DESC";

       SegEmpVO          datSeg   = null;
       Vector            listaSeg = new Vector();

       try
       {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codEmp);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datSeg = new SegEmpVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientoEmpresas.CODSEGUIMIENTO));
                datSeg.setIdEmp      (rs.getString(TablaSeguimientoEmpresas.CODEMPRESA));
                datSeg.setIncidencias(rs.getString(TablaSeguimientoEmpresas.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientoEmpresas.FECHA ));
                datSeg.setUsuario    (rs.getString(TablaSeguimientoEmpresas.USUARIO));

                listaSeg.addElement(datSeg);
            }

            rs.close();
            ps.close();

            return listaSeg;
       }
       catch (Exception exp)
       {
            try
            {
                rs.close();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exp;
       }
    }

    //Método que elimina todos los seguimientos de una empresa
    public static int eliminaSegEmp(String codEmp, Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        int               regActualizados = 0;

        String            sql             = "DELETE FROM " + TablaSeguimientoEmpresas.TABLA      +
                                            " WHERE "      + TablaSeguimientoEmpresas.CODEMPRESA + " = ? ";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codEmp);

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
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que elimina un registro de seguimiento
    public static int eliminaSeg(String codSeg, Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        int               regActualizados = 0;

        String            sql             = "DELETE FROM " + TablaSeguimientoEmpresas.TABLA          +
                                            " WHERE "      + TablaSeguimientoEmpresas.CODSEGUIMIENTO + " = ? ";
        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codSeg);

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
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve el número de seguimientos de una empresa
    public static int devolverNumSeg(String codEmp, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            sql            = "SELECT COUNT(" + TablaSeguimientoEmpresas.CODEMPRESA + ")" +
                                           " FROM "        + TablaSeguimientoEmpresas.TABLA      +
                                           " WHERE "       + TablaSeguimientoEmpresas.CODEMPRESA + " = ? ";
        int               numSeg         = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros de la consulta sql
            ps.setString(1, codEmp);

            rs = ps.executeQuery();

            if(rs.next())
            {
                numSeg = rs.getInt(1);
            }

            rs.close();
            ps.close();

            return numSeg;
        }

        catch (Exception exp)
        {
            try
            {
               rs.close();
               ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SegEmpDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exp;
        }
    }
}

