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

import es.jahernandez.datos.TipTrastVO;
import es.jahernandez.gestion.TipTrastGestion;
import es.jahernandez.tablas.TablaTipTrast;

/**
 *
 * @author JuanAlberto
 */
public class TipTrastDAO
{
     //Método que devuelve los datos de tipo Trastorno
    public static Vector devolverTodTipTrast(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaTipTrast.CODTIPTRAS + " , "
                                                     + TablaTipTrast.DESCRIP    +
                                           " FROM "  + TablaTipTrast.TABLA;

        TipTrastVO        tipTrasVO      = null;
        Vector            listaTipTras   = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                tipTrasVO = new TipTrastVO();

                tipTrasVO.setCodTipTrast(rs.getString(TablaTipTrast.CODTIPTRAS));
                tipTrasVO.setDescrip    (rs.getString(TablaTipTrast.DESCRIP));

                listaTipTras.add(tipTrasVO);
            }

            rs.close();
            ps.close();

            return listaTipTras;
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
                Logger.getLogger(TipTrastDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve el nombre de un tipo de trastorno
    public static String devuelveNombreTipoTrastorno(String codTrast,Connection con) throws Exception
    {
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        String            sql       = "SELECT " + TablaTipTrast.DESCRIP +
                                      " FROM "  + TablaTipTrast.TABLA   +
                                      " WHERE " + TablaTipTrast.CODTIPTRAS + " = ?";

        String            nomTrast  = "";

        try
        {
            ps  = con.prepareStatement(sql);

            ps.setString(1, codTrast);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                nomTrast = rs.getString(TablaTipTrast.DESCRIP);
            }

            rs.close();
            ps.close();

            return nomTrast;
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
                Logger.getLogger(TipTrastDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Genera un nuevo código de tipo trastorno
    public static String generarNuevoCodTipTrast()
    {
        boolean enc        = true;
        int     avc        = 1;
        int     contCar;
        String  codIntrod  = "";

        Vector  datTipTras = TipTrastGestion.devolverTodTipTrast();

        while (enc)
        {
            contCar = new Integer(datTipTras.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datTipTras.size() + avc).toString();
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 4)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datTipTras = TipTrastGestion.devolverTodTipTrast();
            for (int ind = 0; ind < datTipTras.size(); ind++)
            {
                TipTrastVO tipTrasVO = (TipTrastVO) datTipTras.elementAt(ind);
                if (tipTrasVO.getCodTipTrast().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;
        }
        return codIntrod;
    }

    //Método que guarda un tipo trastorno
    public static int guardarTipTrast(TipTrastVO tipTrasVO,Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        int               regActualizados = 0;
        String            nueCodTipTrast  = generarNuevoCodTipTrast();

        String            sql             = "INSERT INTO " + TablaTipTrast.TABLA      + " ( "
                                                           + TablaTipTrast.CODTIPTRAS + " , "
                                                           + TablaTipTrast.DESCRIP    + " ) " +
                                            " VALUES (?,?)";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros a la consulta sql
            ps.setString(1, nueCodTipTrast);
            ps.setString(2, tipTrasVO.getDescrip());

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
                Logger.getLogger(TipTrastDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Edita el registro de un tipo de trastorno
    public static int editarTipTrast(TipTrastVO tipTrastVO,Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = 0;

        String            sql             = "UPDATE " + TablaTipTrast.TABLA      +
                                            " SET "   + TablaTipTrast.DESCRIP    + " = ? " +
                                            " WHERE " + TablaTipTrast.CODTIPTRAS + " = ? ";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasasn los parámetros a la consulta sql
            ps.setString(1, tipTrastVO.getDescrip());
            ps.setString(2, tipTrastVO.getCodTipTrast());

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
                Logger.getLogger(TipTrastDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }
}
