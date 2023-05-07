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

import es.jahernandez.datos.NivelForVO;
import es.jahernandez.tablas.TablaNivelFormativo;


/**
 *
 * @author Alberto
 */
public class NivelForDAO
{
     //Método que devuelve los datos a mostrar en los combos de nivel formativo
    public static Vector datComNivFor(Connection con) throws Exception
    {
        
        ResultSet         rs         = null;

        String            sql        = "SELECT "    + TablaNivelFormativo.CODNIVEL + " , "
                                                    + TablaNivelFormativo.NOMBRE   +
                                       " FROM  "    + TablaNivelFormativo.TABLA    +
                                       " ORDER BY " + TablaNivelFormativo.NOMBRE;

        NivelForVO        nivForVO   = null;

        Vector            listNivFor = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {
            rs  = ps.executeQuery();

            while (rs.next())
            {
                nivForVO = new NivelForVO();
                nivForVO.setIdNIvel (rs.getInt   (TablaNivelFormativo.CODNIVEL));
                nivForVO.setNomNivel(rs.getString(TablaNivelFormativo.NOMBRE));

                listNivFor.addElement(nivForVO);
            }

            rs.close();
            ps.close();
           }
            return listNivFor;

        }
        catch (Exception exc)
        {
           

            throw exc;
        }
    }

    //Método que devuelve el nombre de un nivel formativo
    public static String nomNivFor(String idNivFor,Connection con) throws Exception
    {
        
        ResultSet         rs         = null;

        String            sql        = "SELECT " + TablaNivelFormativo.NOMBRE   +
                                       " FROM "  + TablaNivelFormativo.TABLA    +
                                       " WHERE " + TablaNivelFormativo.CODNIVEL + " = ? ";

        String            strNomNF   = "";

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Se le pasan los parámetros a la consulta sql
            ps.setInt(1, new Integer(idNivFor).intValue());

            rs  = ps.executeQuery();


            if (rs.next())
            {
                strNomNF = rs.getString(TablaNivelFormativo.NOMBRE);
            }

            rs.close();
            ps.close();
           }
            return strNomNF;

        }
        catch (Exception exc)
        {
           

            throw exc;
        }
    }
}
