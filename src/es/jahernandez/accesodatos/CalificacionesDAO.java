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

import es.jahernandez.datos.CalificacionesVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.tablas.TablaCalificaciones;


/**
 *
 * @author JuanAlberto
 */
public class CalificacionesDAO
{
    //Método que devuelve los datos de una calificacion
    public static CalificacionesVO devolverDatosCalificacion(String codEdi, String codMod, String codAlu, int evaluacion, Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaCalificaciones.CODEDI     + " , "
                                                     + TablaCalificaciones.CODMOD     + " , "
                                                     + TablaCalificaciones.CODALU     + " , "
                                                     + TablaCalificaciones.EVALUACION + " , "
                                                     + TablaCalificaciones.FECHA      + " , "
                                                     + TablaCalificaciones.NOTA       +
                                           " FROM "  + TablaCalificaciones.TABLA      +
                                           " WHERE " + TablaCalificaciones.CODEDI     + " = ? AND "
                                                     + TablaCalificaciones.CODMOD     + " = ? AND "
                                                     + TablaCalificaciones.CODALU     + " = ? AND "
                                                     + TablaCalificaciones.EVALUACION + " = ? ";

        CalificacionesVO  califVO       = new CalificacionesVO();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codEdi);
            ps.setString(2, codMod);
            ps.setString(3, codAlu);
            ps.setInt   (4, evaluacion);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                califVO = new CalificacionesVO();

                califVO.setIdEdi     (rs.getString (TablaCalificaciones.CODEDI));
                califVO.setIdMod     (rs.getString (TablaCalificaciones.CODMOD));
                califVO.setIdAlu     (rs.getString (TablaCalificaciones.CODALU));
                califVO.setEvaluacion(rs.getInt    (TablaCalificaciones.EVALUACION));
                califVO.setFecha     (rs.getDate   (TablaCalificaciones.FECHA));
                califVO.setNota      (rs.getInt    (TablaCalificaciones.NOTA));
            }

            rs.close();
            ps.close();
            }
            return califVO;

        }
        catch (Exception exc)
        {
            
            throw exc;
        }

    }

    //Método que devuelve todos los registro de calificaciones
    public static Vector devolverTodasCalificaciones(Connection con) throws Exception
    {
       
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaCalificaciones.CODEDI     + " , "
                                                     + TablaCalificaciones.CODMOD     + " , "
                                                     + TablaCalificaciones.CODALU     + " , "
                                                     + TablaCalificaciones.EVALUACION + " , "
                                                     + TablaCalificaciones.FECHA      + " , "
                                                     + TablaCalificaciones.NOTA       +
                                           " FROM "  + TablaCalificaciones.TABLA      ;


        CalificacionesVO  califVO       = null;
        Vector            listaCalif    = new Vector();

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            rs  = ps.executeQuery();

            while(rs.next())
            {
                califVO = new CalificacionesVO();

                califVO.setIdEdi     (rs.getString (TablaCalificaciones.CODEDI));
                califVO.setIdMod     (rs.getString (TablaCalificaciones.CODMOD));
                califVO.setIdAlu     (rs.getString (TablaCalificaciones.CODALU));
                califVO.setEvaluacion(rs.getInt    (TablaCalificaciones.EVALUACION));
                califVO.setFecha     (rs.getDate   (TablaCalificaciones.FECHA));
                califVO.setNota      (rs.getInt    (TablaCalificaciones.NOTA));

                listaCalif.add(califVO);
            }

            rs.close();
            ps.close();
            }
            return listaCalif;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarCalificacion(CalificacionesVO califVO, Connection con) throws Exception
    {
       

        String            cadenaConsulta = "INSERT INTO " + TablaCalificaciones.TABLA      + " ( "
                                                          + TablaCalificaciones.CODEDI     + " , "
                                                          + TablaCalificaciones.CODMOD     + " , "
                                                          + TablaCalificaciones.CODALU     + " , "
                                                          + TablaCalificaciones.EVALUACION + " , "
                                                          + TablaCalificaciones.FECHA      + " , "
                                                          + TablaCalificaciones.NOTA       + " ) " +
                                           " VALUES(?,?,?,?,?,?)";


        int               regActualizados = 0;

        try
        {
            try (PreparedStatement ps  = con.prepareStatement(cadenaConsulta)) {

            //Se pasan los parámetros a la consulta sql
            ps.setString (1, califVO.getIdEdi());
            ps.setString (2, califVO.getIdMod());
            ps.setString (3, califVO.getIdAlu());
            ps.setInt    (4, califVO.getEvaluacion());
            ps.setDate   (5, new Date(califVO.getFecha().getTime()));
            ps.setInt    (6, califVO.getNota());

            regActualizados = ps.executeUpdate();

            ps.close();
            }
            return regActualizados;
        }
        catch (Exception exc)
        {
            
            throw  exc;
        }
    }

    //Método que devuelve los datos de calificaciones de un alumno y una edición
    public static Vector devolverCalificacionesAluEdi(String codAlu , String codEdi, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "     + TablaCalificaciones.CODEDI     + " , "
                                                       + TablaCalificaciones.CODMOD     + " , "
                                                       + TablaCalificaciones.CODALU     + " , "
                                                       + TablaCalificaciones.EVALUACION + " , "
                                                       + TablaCalificaciones.FECHA      + " , "
                                                       + TablaCalificaciones.NOTA       +
                                          " FROM "     + TablaCalificaciones.TABLA      +
                                          " WHERE "    + TablaCalificaciones.CODALU     + " = ? AND "
                                                       + TablaCalificaciones.CODEDI     + " = ? "      +
                                          " ORDER BY " + TablaCalificaciones.EVALUACION + " , "
                                                       + TablaCalificaciones.FECHA ;

        CalificacionesVO  califVO     = null;
        Vector            listaCalif  = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);
            ps.setString(2, codEdi);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                califVO = new CalificacionesVO();

                califVO.setIdEdi     (rs.getString (TablaCalificaciones.CODEDI));
                califVO.setIdMod     (rs.getString (TablaCalificaciones.CODMOD));
                califVO.setIdAlu     (rs.getString (TablaCalificaciones.CODALU));
                califVO.setEvaluacion(rs.getInt    (TablaCalificaciones.EVALUACION));
                califVO.setFecha     (rs.getDate   (TablaCalificaciones.FECHA));
                califVO.setNota      (rs.getInt    (TablaCalificaciones.NOTA));

                listaCalif.add(califVO);
            }

            rs.close();
            ps.close();
           }
            return listaCalif;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

     //Método que devuelve los datos de calificaciones de una edición
    public static Vector devolverCalificacionesEdi(String codEdi, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "    + TablaCalificaciones.CODEDI     + " , "
                                                      + TablaCalificaciones.CODMOD     + " , "
                                                      + TablaCalificaciones.CODALU     + " , "
                                                      + TablaCalificaciones.EVALUACION + " , "
                                                      + TablaCalificaciones.FECHA      + " , "
                                                      + TablaCalificaciones.NOTA       +
                                         " FROM "     + TablaCalificaciones.TABLA      +
                                         " WHERE "    + TablaCalificaciones.CODEDI     + " = ? "      +
                                         " ORDER BY " + TablaCalificaciones.CODALU     + " , "
                                                      + TablaCalificaciones.EVALUACION + " , "
                                                      + TablaCalificaciones.FECHA ;

        CalificacionesVO  califVO     = null;
        Vector            listaCalif  = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codEdi);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                califVO = new CalificacionesVO();

                califVO.setIdEdi     (rs.getString (TablaCalificaciones.CODEDI));
                califVO.setIdMod     (rs.getString (TablaCalificaciones.CODMOD));
                califVO.setIdAlu     (rs.getString (TablaCalificaciones.CODALU));
                califVO.setEvaluacion(rs.getInt    (TablaCalificaciones.EVALUACION));
                califVO.setFecha     (rs.getDate   (TablaCalificaciones.FECHA));
                califVO.setNota      (rs.getInt    (TablaCalificaciones.NOTA));

                listaCalif.add(califVO);
            }

            rs.close();
            ps.close();
           }
            return listaCalif;
        }
        catch (Exception exc)
        {
            
            throw exc;
        }
    }

    //Método que devuelve los datos de calificaciones de un alumno y una edición y evaluacion
    public static Vector devolverCalificacionesAluEdiEva(String codAlu , String codEdi, int codEv, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "     + TablaCalificaciones.CODEDI     + " , "
                                                       + TablaCalificaciones.CODMOD     + " , "
                                                       + TablaCalificaciones.CODALU     + " , "
                                                       + TablaCalificaciones.EVALUACION + " , "
                                                       + TablaCalificaciones.FECHA      + " , "
                                                       + TablaCalificaciones.NOTA       +
                                          " FROM "     + TablaCalificaciones.TABLA      +
                                          " WHERE "    + TablaCalificaciones.CODALU     + " = ? AND "
                                                       + TablaCalificaciones.CODEDI     + " = ? AND "
                                                       + TablaCalificaciones.EVALUACION + " = ? "     +
                                          " ORDER BY " + TablaCalificaciones.FECHA ;

        CalificacionesVO  califVO     = null;
        Vector            listaCalif  = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);
            ps.setString(2, codEdi);
            ps.setInt   (3, codEv);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                califVO = new CalificacionesVO();

                califVO.setIdEdi     (rs.getString (TablaCalificaciones.CODEDI));
                califVO.setIdMod     (rs.getString (TablaCalificaciones.CODMOD));
                califVO.setIdAlu     (rs.getString (TablaCalificaciones.CODALU));
                califVO.setEvaluacion(rs.getInt    (TablaCalificaciones.EVALUACION));
                califVO.setFecha     (rs.getDate   (TablaCalificaciones.FECHA));
                califVO.setNota      (rs.getInt    (TablaCalificaciones.NOTA));

                listaCalif.add(califVO);
            }

            rs.close();
            ps.close();
           }
            return listaCalif;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

   //Método que devuelve los datos de calificaciones de una edición y evaluacion
    public static Vector devolverCalificacionesEdiEva(String codEdi, int codEv, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "     + TablaCalificaciones.CODEDI     + " , "
                                                       + TablaCalificaciones.CODMOD     + " , "
                                                       + TablaCalificaciones.CODALU     + " , "
                                                       + TablaCalificaciones.EVALUACION + " , "
                                                       + TablaCalificaciones.FECHA      + " , "
                                                       + TablaCalificaciones.NOTA       +
                                          " FROM "     + TablaCalificaciones.TABLA      +
                                          " WHERE "    + TablaCalificaciones.CODEDI     + " = ? AND "
                                                       + TablaCalificaciones.EVALUACION + " = ? "     +
                                          " ORDER BY " + TablaCalificaciones.CODALU     + " , "       +
                                                         TablaCalificaciones.FECHA ;

        CalificacionesVO  califVO     = null;
        Vector            listaCalif  = new Vector();

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codEdi);
            ps.setInt   (2, codEv);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                califVO = new CalificacionesVO();

                califVO.setIdEdi     (rs.getString (TablaCalificaciones.CODEDI));
                califVO.setIdMod     (rs.getString (TablaCalificaciones.CODMOD));
                califVO.setIdAlu     (rs.getString (TablaCalificaciones.CODALU));
                califVO.setEvaluacion(rs.getInt    (TablaCalificaciones.EVALUACION));
                califVO.setFecha     (rs.getDate   (TablaCalificaciones.FECHA));
                califVO.setNota      (rs.getInt    (TablaCalificaciones.NOTA));

                listaCalif.add(califVO);
            }

            rs.close();
            ps.close();
           }
            return listaCalif;
        }
        catch (Exception exc)
        {
           
            throw exc;
        }
    }

    //Edita el registro de una calificación
    public static int editarCalificacion(CalificacionesVO califVO, Connection con) throws Exception
    {
        

        String            sql             = "UPDATE " + TablaCalificaciones.TABLA      +
                                            " SET "   + TablaCalificaciones.FECHA      + " = ? , "
                                                      + TablaCalificaciones.NOTA       + " = ?   "   +
                                            " WHERE " + TablaCalificaciones.CODEDI     + " = ? AND "
                                                      + TablaCalificaciones.CODMOD     + " = ? AND "
                                                      + TablaCalificaciones.CODALU     + " = ? AND "
                                                      + TablaCalificaciones.EVALUACION + " = ? " ;
        int               regActualizados = 0;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setDate   (1, new Date(califVO.getFecha().getTime()));
            ps.setInt    (2, califVO.getNota());
            ps.setString (3, califVO.getIdEdi());
            ps.setString (4, califVO.getIdMod());
            ps.setString (5, califVO.getIdAlu());
            ps.setInt    (6, califVO.getEvaluacion());

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

    //Borra el registro de una calificación
    public static int eliminaCalificacion (String codEdi, String codMod, String codAlu, int evaluacion, Connection con) throws Exception
    {
        

        String            sql             = "DELETE FROM " + TablaCalificaciones.TABLA       +
                                            " WHERE "      + TablaCalificaciones.CODEDI     + " = ? AND "
                                                           + TablaCalificaciones.CODMOD     + " = ? AND "
                                                           + TablaCalificaciones.CODALU     + " = ? AND "
                                                           + TablaCalificaciones.EVALUACION + " = ? " ;

        int               regActualizados = 0;


        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta sql
            ps.setString (1, codEdi);
            ps.setString (2, codMod);
            ps.setString (3, codAlu);
            ps.setInt    (4, evaluacion);

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

    //Borra las calificaciones de un alumno
    public static int eliminarCalificAlumno(String codAlu, Connection con) throws Exception
    {
        

        String            sql             = "DELETE FROM " + TablaCalificaciones.TABLA      +
                                            " WHERE "      + TablaCalificaciones.CODALU     + " = ? " ;

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
            
            throw  exc;
        }
    }

    //Método que devuelve si un alumno tiene calificaciones
    public static boolean tieneAluClasesInd(String codAlu, Connection con) throws Exception
    {
        
        ResultSet         rs           = null;

        String            sql          = "SELECT "  + TablaCalificaciones.CODALU +
                                          " FROM "  + TablaCalificaciones.TABLA  +
                                          " WHERE " + TablaCalificaciones.CODALU + " = ?";

       boolean            aluTienCalif = false;

        try
        {
           try (PreparedStatement ps  = con.prepareStatement(sql)) {

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);

            rs  = ps.executeQuery();

            if(rs.next())
            {
                aluTienCalif = true;
            }

            rs.close();
            ps.close();
           }
            return aluTienCalif;
        }
        catch (Exception exc)
        {
           
            throw  exc;
        }
    }

}
