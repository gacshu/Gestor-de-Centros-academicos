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

import es.jahernandez.datos.TrastornosVO;
import es.jahernandez.gestion.TrastornosGestion;
import es.jahernandez.tablas.TablaTrastornos;

/**
 *
 * @author JuanAlberto
 */
public class TrastornosDAO
{
    //Método que devuelve los datos de un trastorno
    public static TrastornosVO devolverDatosTrastorno(String codTrastorno, Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaTrastornos.CODTRAST   + " , "
                                                     + TablaTrastornos.CODALU     + " , "
                                                     + TablaTrastornos.CODTIPTRAS + " , "
                                                     + TablaTrastornos.MEDICADO   + " , "
                                                     + TablaTrastornos.MEDICACION +
                                           " FROM "  + TablaTrastornos.TABLA      +
                                           " WHERE " + TablaTrastornos.CODTRAST   + " = ?";

        TrastornosVO      trastVO        = new TrastornosVO();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codTrastorno );

            rs  = ps.executeQuery();

            if (rs.next())
            {
                trastVO = new TrastornosVO();

                trastVO.setCodTrastorno    (rs.getString (TablaTrastornos.CODTRAST));
                trastVO.setIdAlu           (rs.getString (TablaTrastornos.CODALU));
                trastVO.setCodTipoTrastorno(rs.getString (TablaTrastornos.CODTIPTRAS));
                trastVO.setMedicado        (rs.getBoolean(TablaTrastornos.MEDICADO));
                trastVO.setMedicacion      (rs.getString (TablaTrastornos.MEDICACION));
            }

            rs.close();
            ps.close();
            }
            return trastVO;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Método que devuelve todos los registro de trastorno
    public static Vector devolverTodosTrastornos(Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaTrastornos.CODTRAST   + " , "
                                                     + TablaTrastornos.CODALU     + " , "
                                                     + TablaTrastornos.CODTIPTRAS + " , "
                                                     + TablaTrastornos.MEDICADO   + " , "
                                                     + TablaTrastornos.MEDICACION +
                                           " FROM "  + TablaTrastornos.TABLA;

        TrastornosVO      trastVO      = null;
        Vector            listaTrast   = new Vector();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            rs  = ps.executeQuery();

            while(rs.next())
            {
                trastVO = new TrastornosVO();

                trastVO.setCodTrastorno    (rs.getString (TablaTrastornos.CODTRAST));
                trastVO.setIdAlu           (rs.getString (TablaTrastornos.CODALU));
                trastVO.setCodTipoTrastorno(rs.getString (TablaTrastornos.CODTIPTRAS));
                trastVO.setMedicado        (rs.getBoolean(TablaTrastornos.MEDICADO));
                trastVO.setMedicacion      (rs.getString (TablaTrastornos.MEDICACION));

                listaTrast.add(trastVO);
            }

            rs.close();
            ps.close();
            }
            return listaTrast;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarTrastorno(TrastornosVO trastVO, Connection con) throws Exception
    {
       

        String            cadenaConsulta = "INSERT INTO " + TablaTrastornos.TABLA      + " ( "
                                                          + TablaTrastornos.CODTRAST   + " , "
                                                          + TablaTrastornos.CODALU     + " , "
                                                          + TablaTrastornos.CODTIPTRAS + " , "
                                                          + TablaTrastornos.MEDICADO   + " , "
                                                          + TablaTrastornos.MEDICACION + " ) " +
                                           " VALUES(?,?,?,?,?)";


        int               regActualizados = 0;

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            //Se pasan los parámetros a la consulta sql
            ps.setString (1, nueCodTrast());
            ps.setString (2, trastVO.getIdAlu());
            ps.setString (3, trastVO.getCodTipoTrastorno());
            ps.setBoolean(4, trastVO.isMedicado());
            ps.setString (5, trastVO.getMedicacion());

            regActualizados = ps.executeUpdate();

            ps.close();
            }
            return regActualizados;
        }
        catch (Exception exc)
        {
          
            throw exc;
        }
    }

    //Método que genera un nuevo código de trastorno
    public static String nueCodTrast()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datTrast  = TrastornosGestion.devolverTodosTrastornos();

        while (enc)
        {
            contCar = ("" + datTrast.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datTrast.size() + avc);
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
            datTrast = TrastornosGestion.devolverTodosTrastornos();
            for (int ind = 0; ind < datTrast.size(); ind++)
            {
                TrastornosVO trastVO = (TrastornosVO) datTrast.elementAt(ind);
                if (trastVO.getCodTrastorno().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }
            avc = avc + 1;
        }

        return codIntrod;
    }

    //Método que devuelve los datos de trastorno de un alumno
    public static Vector devolverTrastAlu(String codAlu , Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "  + TablaTrastornos.CODTRAST   + " , "
                                                    + TablaTrastornos.CODALU     + " , "
                                                    + TablaTrastornos.CODTIPTRAS + " , "
                                                    + TablaTrastornos.MEDICADO   + " , "
                                                    + TablaTrastornos.MEDICACION +
                                         " FROM "   + TablaTrastornos.TABLA      +
                                         " WHERE "  + TablaTrastornos.CODALU     + " = ?";

        TrastornosVO      trastVO      = null;
        Vector            listaTrast   = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                trastVO = new TrastornosVO();

                trastVO.setCodTrastorno    (rs.getString (TablaTrastornos.CODTRAST));
                trastVO.setIdAlu           (rs.getString (TablaTrastornos.CODALU));
                trastVO.setCodTipoTrastorno(rs.getString (TablaTrastornos.CODTIPTRAS));
                trastVO.setMedicado        (rs.getBoolean(TablaTrastornos.MEDICADO));
                trastVO.setMedicacion      (rs.getString (TablaTrastornos.MEDICACION));

                listaTrast.add(trastVO);
            }

            rs.close();
            ps.close();
           }
            return listaTrast;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

     //Edita el registro de un trastorno
    public static int editaTrastorno(TrastornosVO trastVO, Connection con) throws Exception
    {
        

        String            sql = "UPDATE " + TablaTrastornos.TABLA      +
                                " SET "   + TablaTrastornos.CODTIPTRAS + " = ? , "
                                          + TablaTrastornos.MEDICADO   + " = ? , "
                                          + TablaTrastornos.MEDICACION + " = ?   " +
                                " WHERE " + TablaTrastornos.CODTRAST   + " = ?";

        int               regActualizados = 0;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString (1, trastVO.getCodTipoTrastorno());
            ps.setBoolean(2, trastVO.isMedicado());
            ps.setString (3, trastVO.getMedicacion());
            ps.setString (4, trastVO.getCodTrastorno());

            regActualizados = ps.executeUpdate();

            ps.close();
           }
            return regActualizados;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

    //Borra el registro de un trastorno
    public static int eliminaTrastorno(String codTrastorno, Connection con) throws Exception
    {
        

        String            sql             = "DELETE FROM " + TablaTrastornos.TABLA    +
                                            " WHERE "      + TablaTrastornos.CODTRAST + " = ?";

        int               regActualizados = 0;


        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codTrastorno);

            regActualizados = ps.executeUpdate();

            ps.close();
           }
            return regActualizados;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

    //Borra los trastornos de un alumno
    public static int eliminaTrastornosAlumno(String codAlu, Connection con) throws Exception
    {
        

        String            sql             = "DELETE FROM " + TablaTrastornos.TABLA    +
                                            " WHERE "      + TablaTrastornos.CODALU   + " = ?";

        int               regActualizados = 0;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codAlu);

            regActualizados = ps.executeUpdate();

            ps.close();
           }
            return regActualizados;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }
}