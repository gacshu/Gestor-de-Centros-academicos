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

import es.jahernandez.datos.FaltasVO;
import es.jahernandez.tablas.TablaFaltas;



/**
 *
 * @author JuanAlberto
 */
public class FaltasDAO
{
    //Método que devuelve los datos de una falta
    public static FaltasVO devolverDatosFalta(String codEdi, String codMod, String codAlu, java.util.Date fecha, Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaFaltas.CODEDI + " , "
                                                     + TablaFaltas.CODMOD + " , "
                                                     + TablaFaltas.CODALU + " , "
                                                     + TablaFaltas.FECHA  + " , "
                                                     + TablaFaltas.JUSTIF +
                                           " FROM "  + TablaFaltas.TABLA  +
                                           " WHERE " + TablaFaltas.CODEDI + " = ? AND "
                                                     + TablaFaltas.CODMOD + " = ? AND "
                                                     + TablaFaltas.CODALU + " = ? AND "
                                                     + TablaFaltas.FECHA  + " = ? ";

        FaltasVO          faltaVO       = new FaltasVO();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codEdi);
            ps.setString(2, codMod);
            ps.setString(3, codAlu);
            ps.setDate  (4, new Date(fecha.getTime()));

            rs  = ps.executeQuery();

            if (rs.next())
            {
                faltaVO = new FaltasVO();

                faltaVO.setIdEdi      (rs.getString (TablaFaltas.CODEDI));
                faltaVO.setIdMod      (rs.getString (TablaFaltas.CODMOD));
                faltaVO.setIdAlu      (rs.getString (TablaFaltas.CODALU));
                faltaVO.setFecha      (rs.getDate   (TablaFaltas.FECHA ));
                faltaVO.setJustificada(rs.getBoolean(TablaFaltas.JUSTIF));
            }

            rs.close();
            ps.close();
            }
            return faltaVO;

        }
        catch (Exception exc)
        {
           
            throw exc;
        }

    }

    //Método que devuelve todos los registro de faltas
    public static Vector devolverTodasFaltas(Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaFaltas.CODEDI + " , "
                                                     + TablaFaltas.CODMOD + " , "
                                                     + TablaFaltas.CODALU + " , "
                                                     + TablaFaltas.FECHA  + " , "
                                                     + TablaFaltas.JUSTIF +
                                           " FROM "  + TablaFaltas.TABLA  ;

        FaltasVO          faltaVO       = null;
        Vector            listaFaltas   = new Vector();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            rs  = ps.executeQuery();

            while(rs.next())
            {
                faltaVO = new FaltasVO();

                faltaVO.setIdEdi      (rs.getString (TablaFaltas.CODEDI));
                faltaVO.setIdMod      (rs.getString (TablaFaltas.CODMOD));
                faltaVO.setIdAlu      (rs.getString (TablaFaltas.CODALU));
                faltaVO.setFecha      (rs.getDate   (TablaFaltas.FECHA ));
                faltaVO.setJustificada(rs.getBoolean(TablaFaltas.JUSTIF));

                listaFaltas.add(faltaVO);
            }

            rs.close();
            ps.close();
            }
            return listaFaltas;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarFalta(FaltasVO faltaVO, Connection con) throws Exception
    {
       

        String            cadenaConsulta = "INSERT INTO " + TablaFaltas.TABLA  + " ( "
                                                          + TablaFaltas.CODEDI + " , "
                                                          + TablaFaltas.CODMOD + " , "
                                                          + TablaFaltas.CODALU + " , "
                                                          + TablaFaltas.FECHA  + " , "
                                                          + TablaFaltas.JUSTIF + " ) "  +
                                           " VALUES(?,?,?,?,?)";


        int               regActualizados = 0;

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            //Se pasan los parámetros a la consulta sql
            ps.setString (1, faltaVO.getIdEdi());
            ps.setString (2, faltaVO.getIdMod());
            ps.setString (3, faltaVO.getIdAlu());
            ps.setDate   (4, new Date(faltaVO.getFecha().getTime()));
            ps.setBoolean(5, faltaVO.isJustificada());

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

    //Método que devuelve los datos de falta de un alumno y una edición
    public static Vector devolverFaltasAluEdi(String codAlu , String codEdi, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "    + TablaFaltas.CODEDI + " , "
                                                      + TablaFaltas.CODMOD + " , "
                                                      + TablaFaltas.CODALU + " , "
                                                      + TablaFaltas.FECHA  + " , "
                                                      + TablaFaltas.JUSTIF +
                                         " FROM "     + TablaFaltas.TABLA  +
                                         " WHERE "    + TablaFaltas.CODALU + " = ? AND "
                                                      + TablaFaltas.CODEDI + " = ? "     +
                                         " ORDER BY " + TablaFaltas.FECHA ;

        FaltasVO          faltaVO      = null;
        Vector            listaFaltas  = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);
            ps.setString(2, codEdi);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                faltaVO = new FaltasVO();

                faltaVO.setIdEdi      (rs.getString (TablaFaltas.CODEDI));
                faltaVO.setIdMod      (rs.getString (TablaFaltas.CODMOD));
                faltaVO.setIdAlu      (rs.getString (TablaFaltas.CODALU));
                faltaVO.setFecha      (rs.getDate   (TablaFaltas.FECHA ));
                faltaVO.setJustificada(rs.getBoolean(TablaFaltas.JUSTIF));

                listaFaltas.add(faltaVO);
            }

            rs.close();
            ps.close();
           }
            return listaFaltas;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Método que devuelve el número de faltas de un alumno y una edición
    public static int devolverNumFaltasAluEdi(String codAlu , String codEdi, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT COUNT(*)" +
                                         " FROM "     + TablaFaltas.TABLA  +
                                         " WHERE "    + TablaFaltas.CODALU + " = ? AND "
                                                      + TablaFaltas.CODEDI + " = ? "     ;


        int               numFaltas    = 0;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);
            ps.setString(2, codEdi);

            rs  = ps.executeQuery();

           if(rs.next())
            {
                numFaltas = rs.getInt(1);
            }

            rs.close();
            ps.close();
           }
            return numFaltas;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Método que devuelve los datos de falta de una edición
    public static Vector devolverFaltasEdi(String codEdi, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "    + TablaFaltas.CODEDI + " , "
                                                      + TablaFaltas.CODMOD + " , "
                                                      + TablaFaltas.CODALU + " , "
                                                      + TablaFaltas.FECHA  + " , "
                                                      + TablaFaltas.JUSTIF +
                                         " FROM "     + TablaFaltas.TABLA  +
                                         " WHERE "    + TablaFaltas.CODEDI + " = ? "     +
                                         " ORDER BY " + TablaFaltas.CODALU + " , "
                                                      + TablaFaltas.FECHA ;

        FaltasVO          faltaVO     = null;
        Vector            listaFaltas = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codEdi);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                faltaVO = new FaltasVO();

                faltaVO.setIdEdi      (rs.getString (TablaFaltas.CODEDI));
                faltaVO.setIdMod      (rs.getString (TablaFaltas.CODMOD));
                faltaVO.setIdAlu      (rs.getString (TablaFaltas.CODALU));
                faltaVO.setFecha      (rs.getDate   (TablaFaltas.FECHA ));
                faltaVO.setJustificada(rs.getBoolean(TablaFaltas.JUSTIF));

                listaFaltas.add(faltaVO);
            }

            rs.close();
            ps.close();
           }
            return listaFaltas;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }


    //Edita el registro de una falta
    public static int editarFalta(FaltasVO faltaVO, Connection con) throws Exception
    {
        

        String            sql             = "UPDATE " + TablaFaltas.TABLA   +
                                            " SET "   + TablaFaltas.JUSTIF  + " = ?  " +
                                            " WHERE " + TablaFaltas.CODEDI  + " = ? AND "
                                                      + TablaFaltas.CODMOD  + " = ? AND "
                                                      + TablaFaltas.CODALU  + " = ? AND "
                                                      + TablaFaltas.FECHA   + " = ? " ;
        int               regActualizados = 0;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setBoolean(1, faltaVO.isJustificada());
            ps.setString (2, faltaVO.getIdEdi());
            ps.setString (3, faltaVO.getIdMod());
            ps.setString (4, faltaVO.getIdAlu());
            ps.setDate   (5, new Date(faltaVO.getFecha().getTime()));

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

    //Borra el registro de una falta
    public static int eliminaFalta (String codEdi, String codMod, String codAlu, java.util.Date fecha, Connection con) throws Exception
    {
        

        String            sql             = "DELETE FROM " + TablaFaltas.TABLA              +
                                            " WHERE "      + TablaFaltas.CODEDI + " = ? AND "
                                                           + TablaFaltas.CODMOD + " = ? AND "
                                                           + TablaFaltas.CODALU + " = ? AND "
                                                           + TablaFaltas.FECHA  + " = ? " ;

        int               regActualizados = 0;


        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString (1, codEdi);
            ps.setString (2, codMod);
            ps.setString (3, codAlu);
            ps.setDate   (4, new Date(fecha.getTime()));

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

    //Borra las faltas de un alumno
    public static int eliminarFaltasAlumno(String codAlu, Connection con) throws Exception
    {
        

        String            sql             = "DELETE FROM " + TablaFaltas.TABLA            +
                                            " WHERE "      + TablaFaltas.CODALU + " = ? " ;

        int               regActualizados = 0;


        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString (1, codAlu);

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

    //Método que devuelve si un alumno tiene faltas
    public static boolean tieneAluFaltas(String codAlu, Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            sql            = "SELECT "  + TablaFaltas.CODALU +
                                           " FROM "   + TablaFaltas.TABLA  +
                                           " WHERE "  + TablaFaltas.CODALU + " = ?";

       boolean            aluTieneFaltas = false;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);

            rs  = ps.executeQuery();

            if(rs.next())
            {
                aluTieneFaltas = true;
            }

            rs.close();
            ps.close();
           }
            return aluTieneFaltas;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

}

