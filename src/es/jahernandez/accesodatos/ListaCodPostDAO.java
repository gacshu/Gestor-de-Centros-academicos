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

import es.jahernandez.datos.ListaCodPostVO;
import es.jahernandez.tablas.TablaProvincias;
/**
 *
 * @author Alberto
 */
public class ListaCodPostDAO
{
    //Método que devuelve los datos a mostrar en los combos de Provincia
    public static Vector devolverDatProv(Connection con) throws Exception
    {
        
        ResultSet         rs            = null;

        String            sql           = "SELECT "    + TablaProvincias.CODPROV + " , "
                                                       + TablaProvincias.NOMBRE  +
                                          " FROM  "    + TablaProvincias.TABLA   +
                                          " ORDER BY " + TablaProvincias.CODPROV;


        Vector            listaCP       = new Vector();
        ListaCodPostVO    codPostVO     = null;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            rs  = ps.executeQuery();

            while (rs.next())
            {
                codPostVO = new ListaCodPostVO();

                codPostVO.setnCodigo(rs.getString(TablaProvincias.CODPROV));
                codPostVO.setNomProv(rs.getString(TablaProvincias.NOMBRE));

                listaCP.addElement(codPostVO);
            }

            rs.close();
            ps.close();
           }
            return listaCP;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Devuelve el nombre de la provincia
    public static String devuelveNombreProv(String idProv,Connection con) throws Exception
    {
       
        ResultSet         rs        = null;

        String            sql       = "SELECT "  + TablaProvincias.NOMBRE  +
                                       " FROM "  + TablaProvincias.TABLA   +
                                       " WHERE " + TablaProvincias.CODPROV + " = ? ";

        String            nomProv   = "";

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, idProv);

            rs = ps.executeQuery();

            if(rs.next())
            {
                nomProv = rs.getString(TablaProvincias.NOMBRE);
            }

            rs.close();
            ps.close();
           }
            return nomProv;
        }
        catch (Exception exc)
        {
           

            throw exc;
        }
    }
}
