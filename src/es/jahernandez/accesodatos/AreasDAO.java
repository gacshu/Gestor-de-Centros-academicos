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

import es.jahernandez.datos.AreasVO;
import es.jahernandez.gestion.AreasGestion;
import es.jahernandez.tablas.TablaArea;
import es.jahernandez.tablas.TablaProfArea;

/**
 *
 * @author JuanAlberto
 */
public class AreasDAO
{
     //Metodo que devuelve los datos de area
    public static Vector devolverTodAreas(Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT * FROM TbNiv";
        String            cadenaConsulta = "SELECT " + TablaArea.CODAREA  + " , "
                                                     + TablaArea.NOMBRE   +
                                           " FROM "  + TablaArea.TABLA;

        AreasVO           datArea        = null;
        Vector            listaAreas     = new Vector();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datArea = new AreasVO();

                datArea.setCodArea(rs.getString(TablaArea.CODAREA));
                datArea.setNomArea(rs.getString(TablaArea.NOMBRE));

                listaAreas.add(datArea);
            }

            rs.close();
            ps.close();
            }
            return listaAreas;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //MÃ©todo que devuelve el nombre de un Ã¡rea
    public static String devuelveNombreArea(String codArea, Connection con) throws Exception
    {
        
        ResultSet         rs        = null;

        String            sql       = "SELECT " + TablaArea.NOMBRE       +
                                      " FROM "  + TablaArea.TABLA   +
                                      " WHERE " + TablaArea.CODAREA + " = ?";

        String           nombreArea = "";

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            ps.setString(1, codArea);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                nombreArea = rs.getString(TablaArea.NOMBRE);
            }

            rs.close();
            ps.close();
           }
            return nombreArea;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Genera un nuevo cÃ³digo de Ã�rea
    public static String generarNuevoCodArea()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datArea   = AreasGestion.devolverTodAreas();

        while (enc)
        {
            contCar = ("" + datArea.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datArea.size() + avc);
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
            datArea = AreasGestion.devolverTodAreas();
            for (int ind = 0; ind < datArea.size(); ind++)
            {
                AreasVO areaVO = (AreasVO) datArea.elementAt(ind);
                if (areaVO.getCodArea().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }
        return codIntrod;
    }

    //MÃ©todo que guarda un Ã�rea
    public static int guardarArea(AreasVO areaVO, Connection con) throws Exception
    {
        

        int               regActualizados = 0;


        String            nueCodArea      = AreasGestion.generarNuevoCodArea();

        String            sql             = "INSERT INTO " + TablaArea.TABLA   + " ( "
                                                           + TablaArea.CODAREA + " , "
                                                           + TablaArea.NOMBRE  + " ) " +
                                            " VALUES (?,?)";

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Se introducen los parÃ¡metros a la consulta sql
            ps.setString(1, nueCodArea);
            ps.setString(2, areaVO.getNomArea());

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

    //Edita el registro de un Ã�rea
    public static int editarArea(AreasVO areaVO, Connection con) throws Exception
    {
        

        int               regActualizados = 0;

        String            sql             = "UPDATE " + TablaArea.TABLA   +
                                            " SET "   + TablaArea.NOMBRE  + " = ? " +
                                            " WHERE " + TablaArea.CODAREA + " = ? ";

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Se pasasn los parÃ¡metros a la consulta sql
            ps.setString(1, areaVO.getNomArea());
            ps.setString(2, areaVO.getCodArea());

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

    public static Vector devolverAreasProf(String codProf, Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaArea.CODAREA  + " , "
                                                     + TablaArea.NOMBRE   +
                                           " FROM "  + TablaArea.TABLA    +
                                           " WHERE " + TablaArea.CODAREA  + " IN (" +
                                                " SELECT " + TablaProfArea.CODAREA +
                                                " FROM "   + TablaProfArea.TABLA   +
                                                " WHERE "  + TablaProfArea.CODPROF + " = ?)";

        AreasVO           datArea        = null;
        Vector            listaAreas     = new Vector();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            ps.setString(1, codProf);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datArea = new AreasVO();

                datArea.setCodArea(rs.getString(TablaArea.CODAREA));
                datArea.setNomArea(rs.getString(TablaArea.NOMBRE));

                listaAreas.add(datArea);
            }

            rs.close();
            ps.close();
            }
            return listaAreas;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }
}
