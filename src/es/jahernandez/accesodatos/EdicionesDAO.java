/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.gestion.AluEdiGestion;
import es.jahernandez.gestion.EdiModProfAulaGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.HisRecGestion;

import es.jahernandez.tablas.TablaEdiciones;
import es.jahernandez.tablas.TablaAlumnosEdiciones;

import java.sql.Connection;
import java.sql.Date;
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
public class EdicionesDAO
{
    //Método que genera un nuevo código de Edición
    public static String generarNuevoCodEdi()
    {
        boolean enc      = true;
        int     avc      = 1;
        int     contCar;
        String codIntrod = "";

        Vector datEdi = EdicionesGestion.devolverTodosEdi();

        while (enc)
        {
            contCar = ("" + datEdi.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datEdi.size() + avc);
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
            datEdi = EdicionesGestion.devolverTodosEdi();
            for (int ind = 0; ind < datEdi.size(); ind++)
            {
                EdicionesVO ediVO = (EdicionesVO) datEdi.elementAt(ind);
                if (ediVO.getIdEdi().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }
        return codIntrod;
    }

    //Método que devuelve los datos de edición de todas las ediciones
    public static Vector devolverTodosEdi(Connection con) throws Exception
    {
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        String            sql      = "SELECT " + TablaEdiciones.CODEDI       + " , "
                                               + TablaEdiciones.CODCURSO     + " , " 
                                               + TablaEdiciones.CODNIVEL     + " , "
                                               + TablaEdiciones.FECHAINICIO  + " , " 
                                               + TablaEdiciones.FECHAFIN     + " , " 
                                               + TablaEdiciones.PLAZAS       + " , " 
                                               + TablaEdiciones.HORAS        + " , "
                                               + TablaEdiciones.HORAINICIO   + " , " 
                                               + TablaEdiciones.HORAFIN      + " , " 
                                               + TablaEdiciones.PRECIOMATRIC + " , " 
                                               + TablaEdiciones.PRECIORECIBO + " , " 
                                               + TablaEdiciones.PRECIOTOTAL  + " , " 
                                               + TablaEdiciones.CLASELUNES   + " , " 
                                               + TablaEdiciones.CLASEMARTES  + " , " 
                                               + TablaEdiciones.CLASEMIERC   + " , " 
                                               + TablaEdiciones.CLASEJUEVES  + " , " 
                                               + TablaEdiciones.CLASEVIERNES + " , "
                                               + TablaEdiciones.CLASESABADO  + " , " 
                                               + TablaEdiciones.CODCENTRO    + " , "
                                               + TablaEdiciones.MINUTOINICIO + " , "
                                               + TablaEdiciones.MINUTOFIN    + " , "
                                               + TablaEdiciones.HORASDISTAN  + " , "
                                               + TablaEdiciones.HORASTELEF   + " , "
                                               + TablaEdiciones.CODAULA      + " , "
                                               + TablaEdiciones.BONIFICADO   + " , "
                                               + TablaEdiciones.PLAZOS       + " , "
                                               + TablaEdiciones.PAGOENERO    + " , "
                                               + TablaEdiciones.PAGOFEBRERO  + " , "
                                               + TablaEdiciones.PAGOMARZO    + " , "
                                               + TablaEdiciones.PAGOABRIL    + " , "
                                               + TablaEdiciones.PAGOMAYO     + " , "
                                               + TablaEdiciones.PAGOJUNIO    + " , "
                                               + TablaEdiciones.PAGOJULIO    + " , "
                                               + TablaEdiciones.PAGOAGOSTO   + " , "
                                               + TablaEdiciones.PAGOSEPT     + " , "
                                               + TablaEdiciones.PAGOOCTUBRE  + " , "
                                               + TablaEdiciones.PAGONOVIEM   + " , "
                                               + TablaEdiciones.PAGODICIEMB  + " , "
                                               + TablaEdiciones.APLAZADO     + " , "
                                               + TablaEdiciones.DESCRIPCION  + " , "
                                               + TablaEdiciones.CODPROF      +
                
                                     " FROM "  + TablaEdiciones.TABLA;
                
        Vector            listaEdi = new Vector();

        EdicionesVO       datEdi   = null;

        try
        {
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEdi = new EdicionesVO();

                datEdi.setIdEdi      (rs.getString (TablaEdiciones.CODEDI));
                datEdi.setIdCur      (rs.getString (TablaEdiciones.CODCURSO));
                datEdi.setIdNiv      (rs.getString (TablaEdiciones.CODNIVEL));
                datEdi.setFecIn      (rs.getDate   (TablaEdiciones.FECHAINICIO));
                datEdi.setFecFi      (rs.getDate   (TablaEdiciones.FECHAFIN));
                datEdi.setNumPla     (rs.getInt    (TablaEdiciones.PLAZAS));
                datEdi.setNumHor     (rs.getInt    (TablaEdiciones.HORAS));
                datEdi.setHorIn      (rs.getInt    (TablaEdiciones.HORAINICIO));
                datEdi.setHorFi      (rs.getInt    (TablaEdiciones.HORAFIN));
                datEdi.setPrecioM    (rs.getDouble (TablaEdiciones.PRECIOMATRIC));
                datEdi.setPrecioR    (rs.getDouble (TablaEdiciones.PRECIORECIBO));
                datEdi.setPrecioT    (rs.getDouble (TablaEdiciones.PRECIOTOTAL));
                datEdi.setHayLun     (rs.getBoolean(TablaEdiciones.CLASELUNES));
                datEdi.setHayMar     (rs.getBoolean(TablaEdiciones.CLASEMARTES));
                datEdi.setHayMie     (rs.getBoolean(TablaEdiciones.CLASEMIERC));
                datEdi.setHayJue     (rs.getBoolean(TablaEdiciones.CLASEJUEVES));
                datEdi.setHayVie     (rs.getBoolean(TablaEdiciones.CLASEVIERNES));
                datEdi.setHaySab     (rs.getBoolean(TablaEdiciones.CLASESABADO));
                datEdi.setCodCen     (rs.getInt    (TablaEdiciones.CODCENTRO));
                datEdi.setMinIn      (rs.getInt    (TablaEdiciones.MINUTOINICIO));
                datEdi.setMinFin     (rs.getInt    (TablaEdiciones.MINUTOFIN));
                datEdi.setHorDis     (rs.getInt    (TablaEdiciones.HORASDISTAN));
                datEdi.setHorTelef   (rs.getInt    (TablaEdiciones.HORASTELEF));
                datEdi.setIdAula     (rs.getString (TablaEdiciones.CODAULA));
                datEdi.setBonif      (rs.getString (TablaEdiciones.BONIFICADO));
                datEdi.setPlazos     (rs.getBoolean(TablaEdiciones.PLAZOS));
                datEdi.setEne        (rs.getBoolean(TablaEdiciones.PAGOENERO));
                datEdi.setFeb        (rs.getBoolean(TablaEdiciones.PAGOFEBRERO));
                datEdi.setMar        (rs.getBoolean(TablaEdiciones.PAGOMARZO));
                datEdi.setAbr        (rs.getBoolean(TablaEdiciones.PAGOABRIL));
                datEdi.setMay        (rs.getBoolean(TablaEdiciones.PAGOMAYO));
                datEdi.setJun        (rs.getBoolean(TablaEdiciones.PAGOJUNIO));
                datEdi.setJul        (rs.getBoolean(TablaEdiciones.PAGOJULIO));
                datEdi.setAgo        (rs.getBoolean(TablaEdiciones.PAGOAGOSTO));
                datEdi.setSep        (rs.getBoolean(TablaEdiciones.PAGOSEPT));
                datEdi.setOct        (rs.getBoolean(TablaEdiciones.PAGOOCTUBRE));
                datEdi.setNov        (rs.getBoolean(TablaEdiciones.PAGONOVIEM));
                datEdi.setDic        (rs.getBoolean(TablaEdiciones.PAGODICIEMB));
                datEdi.setAplaz      (rs.getString (TablaEdiciones.APLAZADO));
                datEdi.setDescripcion(rs.getString (TablaEdiciones.DESCRIPCION));
                datEdi.setIdProf     (rs.getString (TablaEdiciones.CODPROF));

                listaEdi.addElement(datEdi);
            }

            rs.close();
            ps.close();

            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    //Devuelve el código de edición generado, si se inserta correctamente
    public static String guardarEdicion(EdicionesVO ediVO,Connection con) throws Exception
    {
        PreparedStatement ps              = null;         
        int               regActualizados = 0;
        
        String            nueCodEdi       = generarNuevoCodEdi();
        
        String            sql = "INSERT INTO " + TablaEdiciones.TABLA  + "(" + TablaEdiciones.CODEDI       + " , "  
                                                                             + TablaEdiciones.CODCURSO     + " , "
                                                                             + TablaEdiciones.CODNIVEL     + " , "  
                                                                             + TablaEdiciones.FECHAINICIO  + " , "
                                                                             + TablaEdiciones.FECHAFIN     + " , "  
                                                                             + TablaEdiciones.PLAZAS       + " , " 
                                                                             + TablaEdiciones.HORAS        + " , "
                                                                             + TablaEdiciones.HORAINICIO   + " , "
                                                                             + TablaEdiciones.HORAFIN      + " , "
                                                                             + TablaEdiciones.PRECIOMATRIC + " , "
                                                                             + TablaEdiciones.PRECIORECIBO + " , "
                                                                             + TablaEdiciones.PRECIOTOTAL  + " , "
                                                                             + TablaEdiciones.CLASELUNES   + " , "
                                                                             + TablaEdiciones.CLASEMARTES  + " , "
                                                                             + TablaEdiciones.CLASEMIERC   + " , "
                                                                             + TablaEdiciones.CLASEJUEVES  + " , "
                                                                             + TablaEdiciones.CLASEVIERNES + " , "
                                                                             + TablaEdiciones.CLASESABADO  + " , "
                                                                             + TablaEdiciones.CODCENTRO    + " , "
                                                                             + TablaEdiciones.MINUTOINICIO + " , " 
                                                                             + TablaEdiciones.MINUTOFIN    + " , "
                                                                             + TablaEdiciones.HORASDISTAN  + " , "
                                                                             + TablaEdiciones.HORASTELEF   + " , " 
                                                                             + TablaEdiciones.CODAULA      + " , "
                                                                             + TablaEdiciones.BONIFICADO   + " , "
                                                                             + TablaEdiciones.PLAZOS       + " , "
                                                                             + TablaEdiciones.PAGOENERO    + " , "
                                                                             + TablaEdiciones.PAGOFEBRERO  + " , " 
                                                                             + TablaEdiciones.PAGOMARZO    + " , "
                                                                             + TablaEdiciones.PAGOABRIL    + " , "
                                                                             + TablaEdiciones.PAGOMAYO     + " , "
                                                                             + TablaEdiciones.PAGOJUNIO    + " , "
                                                                             + TablaEdiciones.PAGOJULIO    + " , "     
                                                                             + TablaEdiciones.PAGOAGOSTO   + " , "
                                                                             + TablaEdiciones.PAGOSEPT     + " , "
                                                                             + TablaEdiciones.PAGOOCTUBRE  + " , "
                                                                             + TablaEdiciones.PAGONOVIEM   + " , "
                                                                             + TablaEdiciones.PAGODICIEMB  + " , "
                                                                             + TablaEdiciones.APLAZADO     + " , " 
                                                                             + TablaEdiciones.DESCRIPCION  + " ,  " 
                                                                             + TablaEdiciones.CODPROF      + ")  " +
               
                               " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la sql
            ps.setString ( 1, nueCodEdi);
            ps.setString ( 2, ediVO.getIdCur());
            ps.setString ( 3, ediVO.getIdNiv());
            ps.setDate   ( 4, new Date(ediVO.getFecIn().getTime()));
            ps.setDate   ( 5, new Date(ediVO.getFecFi().getTime()));
            ps.setInt    ( 6, ediVO.getNumPla());
            ps.setInt    ( 7, ediVO.getNumHor());
            ps.setInt    ( 8, ediVO.getHorIn());
            ps.setInt    ( 9, ediVO.getHorFi());
            ps.setDouble (10, ediVO.getPrecioM());
            ps.setDouble (11, ediVO.getPrecioR());
            ps.setDouble (12, ediVO.getPrecioT());
            ps.setBoolean(13, ediVO.isHayLun());
            ps.setBoolean(14, ediVO.isHayMar());
            ps.setBoolean(15, ediVO.isHayMie());
            ps.setBoolean(16, ediVO.isHayJue());
            ps.setBoolean(17, ediVO.isHayVie());
            ps.setBoolean(18, ediVO.isHaySab());
            ps.setInt    (19, ediVO.getCodCen());
            ps.setInt    (20, ediVO.getMinIn());
            ps.setInt    (21, ediVO.getMinFin());
            ps.setInt    (22, ediVO.getHorDis());
            ps.setInt    (23, ediVO.getHorTelef());
            ps.setString (24, ediVO.getIdAula());
            ps.setString (25, ediVO.getBonif());
            ps.setBoolean(26, ediVO.isPlazos());
            ps.setBoolean(27, ediVO.isEne());
            ps.setBoolean(28, ediVO.isFeb());
            ps.setBoolean(29, ediVO.isMar());
            ps.setBoolean(30, ediVO.isAbr());
            ps.setBoolean(31, ediVO.isMay());
            ps.setBoolean(32, ediVO.isJun());
            ps.setBoolean(33, ediVO.isJul());
            ps.setBoolean(34, ediVO.isAgo());
            ps.setBoolean(35, ediVO.isSep());
            ps.setBoolean(36, ediVO.isOct());
            ps.setBoolean(37, ediVO.isNov());
            ps.setBoolean(38, ediVO.isDic());
            ps.setString (39, ediVO.getAplaz());
            ps.setString (40, ediVO.getDescripcion());
            ps.setString (41, ediVO.getIdProf());
            
            
            regActualizados = ps.executeUpdate();

            ps.close();
            
            if (regActualizados > 0)
            {
                return nueCodEdi;
            }
            else
            {
                return null;
            }
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de edición de una edición
    public static EdicionesVO devolverDatosEdi(String codEdi,Connection con) throws Exception
    {
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        String            sql     = "SELECT " + TablaEdiciones.CODEDI       + " , "
                                              + TablaEdiciones.CODCURSO     + " , " 
                                              + TablaEdiciones.CODNIVEL     + " , "
                                              + TablaEdiciones.FECHAINICIO  + " , " 
                                              + TablaEdiciones.FECHAFIN     + " , " 
                                              + TablaEdiciones.PLAZAS       + " , " 
                                              + TablaEdiciones.HORAS        + " , "
                                              + TablaEdiciones.HORAINICIO   + " , " 
                                              + TablaEdiciones.HORAFIN      + " , " 
                                              + TablaEdiciones.PRECIOMATRIC + " , " 
                                              + TablaEdiciones.PRECIORECIBO + " , " 
                                              + TablaEdiciones.PRECIOTOTAL  + " , " 
                                              + TablaEdiciones.CLASELUNES   + " , " 
                                              + TablaEdiciones.CLASEMARTES  + " , " 
                                              + TablaEdiciones.CLASEMIERC   + " , " 
                                              + TablaEdiciones.CLASEJUEVES  + " , " 
                                              + TablaEdiciones.CLASEVIERNES + " , "
                                              + TablaEdiciones.CLASESABADO  + " , " 
                                              + TablaEdiciones.CODCENTRO    + " , "
                                              + TablaEdiciones.MINUTOINICIO + " , "
                                              + TablaEdiciones.MINUTOFIN    + " , "
                                              + TablaEdiciones.HORASDISTAN  + " , "
                                              + TablaEdiciones.HORASTELEF   + " , "
                                              + TablaEdiciones.CODAULA      + " , "
                                              + TablaEdiciones.BONIFICADO   + " , "
                                              + TablaEdiciones.PLAZOS       + " , "
                                              + TablaEdiciones.PAGOENERO    + " , "
                                              + TablaEdiciones.PAGOFEBRERO  + " , "
                                              + TablaEdiciones.PAGOMARZO    + " , "
                                              + TablaEdiciones.PAGOABRIL    + " , "
                                              + TablaEdiciones.PAGOMAYO     + " , "
                                              + TablaEdiciones.PAGOJUNIO    + " , "
                                              + TablaEdiciones.PAGOJULIO    + " , "
                                              + TablaEdiciones.PAGOAGOSTO   + " , "
                                              + TablaEdiciones.PAGOSEPT     + " , "
                                              + TablaEdiciones.PAGOOCTUBRE  + " , "
                                              + TablaEdiciones.PAGONOVIEM   + " , "
                                              + TablaEdiciones.PAGODICIEMB  + " , "
                                              + TablaEdiciones.APLAZADO     + " , "
                                              + TablaEdiciones.DESCRIPCION  + " , "   
                                              + TablaEdiciones.CODPROF      +   
                
                                    " FROM "  + TablaEdiciones.TABLA        +
                                    " WHERE " + TablaEdiciones.CODEDI       + " = ?";
        
        EdicionesVO       datEdi   = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                datEdi = new EdicionesVO();

                datEdi.setIdEdi      (rs.getString (TablaEdiciones.CODEDI));
                datEdi.setIdCur      (rs.getString (TablaEdiciones.CODCURSO));
                datEdi.setIdNiv      (rs.getString (TablaEdiciones.CODNIVEL));
                datEdi.setFecIn      (rs.getDate   (TablaEdiciones.FECHAINICIO));
                datEdi.setFecFi      (rs.getDate   (TablaEdiciones.FECHAFIN));
                datEdi.setNumPla     (rs.getInt    (TablaEdiciones.PLAZAS));
                datEdi.setNumHor     (rs.getInt    (TablaEdiciones.HORAS));
                datEdi.setHorIn      (rs.getInt    (TablaEdiciones.HORAINICIO));
                datEdi.setHorFi      (rs.getInt    (TablaEdiciones.HORAFIN));
                datEdi.setPrecioM    (rs.getDouble (TablaEdiciones.PRECIOMATRIC));
                datEdi.setPrecioR    (rs.getDouble (TablaEdiciones.PRECIORECIBO));
                datEdi.setPrecioT    (rs.getDouble (TablaEdiciones.PRECIOTOTAL));
                datEdi.setHayLun     (rs.getBoolean(TablaEdiciones.CLASELUNES));
                datEdi.setHayMar     (rs.getBoolean(TablaEdiciones.CLASEMARTES));
                datEdi.setHayMie     (rs.getBoolean(TablaEdiciones.CLASEMIERC));
                datEdi.setHayJue     (rs.getBoolean(TablaEdiciones.CLASEJUEVES));
                datEdi.setHayVie     (rs.getBoolean(TablaEdiciones.CLASEVIERNES));
                datEdi.setHaySab     (rs.getBoolean(TablaEdiciones.CLASESABADO));
                datEdi.setCodCen     (rs.getInt    (TablaEdiciones.CODCENTRO));
                datEdi.setMinIn      (rs.getInt    (TablaEdiciones.MINUTOINICIO));
                datEdi.setMinFin     (rs.getInt    (TablaEdiciones.MINUTOFIN));
                datEdi.setHorDis     (rs.getInt    (TablaEdiciones.HORASDISTAN));
                datEdi.setHorTelef   (rs.getInt    (TablaEdiciones.HORASTELEF));
                datEdi.setIdAula     (rs.getString (TablaEdiciones.CODAULA));
                datEdi.setBonif      (rs.getString (TablaEdiciones.BONIFICADO));
                datEdi.setPlazos     (rs.getBoolean(TablaEdiciones.PLAZOS));
                datEdi.setEne        (rs.getBoolean(TablaEdiciones.PAGOENERO));
                datEdi.setFeb        (rs.getBoolean(TablaEdiciones.PAGOFEBRERO));
                datEdi.setMar        (rs.getBoolean(TablaEdiciones.PAGOMARZO));
                datEdi.setAbr        (rs.getBoolean(TablaEdiciones.PAGOABRIL));
                datEdi.setMay        (rs.getBoolean(TablaEdiciones.PAGOMAYO));
                datEdi.setJun        (rs.getBoolean(TablaEdiciones.PAGOJUNIO));
                datEdi.setJul        (rs.getBoolean(TablaEdiciones.PAGOJULIO));
                datEdi.setAgo        (rs.getBoolean(TablaEdiciones.PAGOAGOSTO));
                datEdi.setSep        (rs.getBoolean(TablaEdiciones.PAGOSEPT));
                datEdi.setOct        (rs.getBoolean(TablaEdiciones.PAGOOCTUBRE));
                datEdi.setNov        (rs.getBoolean(TablaEdiciones.PAGONOVIEM));
                datEdi.setDic        (rs.getBoolean(TablaEdiciones.PAGODICIEMB));
                datEdi.setAplaz      (rs.getString (TablaEdiciones.APLAZADO));
                datEdi.setDescripcion(rs.getString (TablaEdiciones.DESCRIPCION));
                datEdi.setIdProf     (rs.getString (TablaEdiciones.CODPROF));
            }

            rs.close();
            ps.close();

            return datEdi;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }

    //Método que comprueba si existen plazas libres para una edición
    public static boolean hayPlazasLibres(String codEdicion,Connection con) throws Exception
    {
        PreparedStatement   ps  = null;
        ResultSet           rs  = null;
        
        String              sql = "SELECT "   + TablaEdiciones.PLAZAS + " AS plazas," +
                                      "(SELECT COUNT(*) AS NumMat " +
                                      " FROM  "  + TablaAlumnosEdiciones.TABLA  + 
                                      " WHERE (" + TablaAlumnosEdiciones.CODEDI + " = " + TablaEdiciones.TABLA + "." + TablaEdiciones.CODEDI + ") AND (" 
                                                 + TablaAlumnosEdiciones.ESBAJA + " = false)) AS matric " +
                                  " FROM "    + TablaEdiciones.TABLA  +
                                  " WHERE ( " + TablaEdiciones.CODEDI + " = ?)";               
        
        int numPlazas = 0;
        int numMatric = 0;

        try
        {
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codEdicion);                        
            rs = ps.executeQuery();

            if (rs.next())
            {
                numPlazas = rs.getInt(1);
                numMatric = rs.getInt(2);
            }

            rs.close();
            ps.close();
            
            return numPlazas>numMatric;

        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }

    //Método que devuelve los datos de edición de las ediciones de un curso determinado
    public static Vector devolverDatEdiCur(String idCurso,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaEdiciones.CODEDI       + " , "
                                          + TablaEdiciones.CODCURSO     + " , " 
                                          + TablaEdiciones.CODNIVEL     + " , "
                                          + TablaEdiciones.FECHAINICIO  + " , " 
                                          + TablaEdiciones.FECHAFIN     + " , " 
                                          + TablaEdiciones.PLAZAS       + " , " 
                                          + TablaEdiciones.HORAS        + " , "
                                          + TablaEdiciones.HORAINICIO   + " , " 
                                          + TablaEdiciones.HORAFIN      + " , " 
                                          + TablaEdiciones.PRECIOMATRIC + " , " 
                                          + TablaEdiciones.PRECIORECIBO + " , " 
                                          + TablaEdiciones.PRECIOTOTAL  + " , " 
                                          + TablaEdiciones.CLASELUNES   + " , " 
                                          + TablaEdiciones.CLASEMARTES  + " , " 
                                          + TablaEdiciones.CLASEMIERC   + " , " 
                                          + TablaEdiciones.CLASEJUEVES  + " , " 
                                          + TablaEdiciones.CLASEVIERNES + " , "
                                          + TablaEdiciones.CLASESABADO  + " , " 
                                          + TablaEdiciones.CODCENTRO    + " , "
                                          + TablaEdiciones.MINUTOINICIO + " , "
                                          + TablaEdiciones.MINUTOFIN    + " , "
                                          + TablaEdiciones.HORASDISTAN  + " , "
                                          + TablaEdiciones.HORASTELEF   + " , "
                                          + TablaEdiciones.CODAULA      + " , "
                                          + TablaEdiciones.BONIFICADO   + " , "
                                          + TablaEdiciones.PLAZOS       + " , "
                                          + TablaEdiciones.PAGOENERO    + " , "
                                          + TablaEdiciones.PAGOFEBRERO  + " , "
                                          + TablaEdiciones.PAGOMARZO    + " , "
                                          + TablaEdiciones.PAGOABRIL    + " , "
                                          + TablaEdiciones.PAGOMAYO     + " , "
                                          + TablaEdiciones.PAGOJUNIO    + " , "
                                          + TablaEdiciones.PAGOJULIO    + " , "
                                          + TablaEdiciones.PAGOAGOSTO   + " , "
                                          + TablaEdiciones.PAGOSEPT     + " , "
                                          + TablaEdiciones.PAGOOCTUBRE  + " , "
                                          + TablaEdiciones.PAGONOVIEM   + " , "
                                          + TablaEdiciones.PAGODICIEMB  + " , "
                                          + TablaEdiciones.APLAZADO     + " , " 
                                          + TablaEdiciones.DESCRIPCION  + " , " 
                                          + TablaEdiciones.CODPROF      +    
                             " FROM "     + TablaEdiciones.TABLA        +
                             " WHERE "    + TablaEdiciones.CODCURSO     + " = ?"  + 
                             " ORDER BY " + TablaEdiciones.FECHAINICIO  + " DESC";
        Vector              listaEdi = new Vector();

        EdicionesVO         datEdi   = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se le pasa los parámetros a la consulta sql
            ps.setString(1, idCurso);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEdi = new EdicionesVO();

                datEdi.setIdEdi      (rs.getString (TablaEdiciones.CODEDI));
                datEdi.setIdCur      (rs.getString (TablaEdiciones.CODCURSO));
                datEdi.setIdNiv      (rs.getString (TablaEdiciones.CODNIVEL));
                datEdi.setFecIn      (rs.getDate   (TablaEdiciones.FECHAINICIO));
                datEdi.setFecFi      (rs.getDate   (TablaEdiciones.FECHAFIN));
                datEdi.setNumPla     (rs.getInt    (TablaEdiciones.PLAZAS));
                datEdi.setNumHor     (rs.getInt    (TablaEdiciones.HORAS));
                datEdi.setHorIn      (rs.getInt    (TablaEdiciones.HORAINICIO));
                datEdi.setHorFi      (rs.getInt    (TablaEdiciones.HORAFIN));
                datEdi.setPrecioM    (rs.getDouble (TablaEdiciones.PRECIOMATRIC));
                datEdi.setPrecioR    (rs.getDouble (TablaEdiciones.PRECIORECIBO));
                datEdi.setPrecioT    (rs.getDouble (TablaEdiciones.PRECIOTOTAL));
                datEdi.setHayLun     (rs.getBoolean(TablaEdiciones.CLASELUNES));
                datEdi.setHayMar     (rs.getBoolean(TablaEdiciones.CLASEMARTES));
                datEdi.setHayMie     (rs.getBoolean(TablaEdiciones.CLASEMIERC));
                datEdi.setHayJue     (rs.getBoolean(TablaEdiciones.CLASEJUEVES));
                datEdi.setHayVie     (rs.getBoolean(TablaEdiciones.CLASEVIERNES));
                datEdi.setHaySab     (rs.getBoolean(TablaEdiciones.CLASESABADO));
                datEdi.setCodCen     (rs.getInt    (TablaEdiciones.CODCENTRO));
                datEdi.setMinIn      (rs.getInt    (TablaEdiciones.MINUTOINICIO));
                datEdi.setMinFin     (rs.getInt    (TablaEdiciones.MINUTOFIN));
                datEdi.setHorDis     (rs.getInt    (TablaEdiciones.HORASDISTAN));
                datEdi.setHorTelef   (rs.getInt    (TablaEdiciones.HORASTELEF));
                datEdi.setIdAula     (rs.getString (TablaEdiciones.CODAULA));
                datEdi.setBonif      (rs.getString (TablaEdiciones.BONIFICADO));
                datEdi.setPlazos     (rs.getBoolean(TablaEdiciones.PLAZOS));
                datEdi.setEne        (rs.getBoolean(TablaEdiciones.PAGOENERO));
                datEdi.setFeb        (rs.getBoolean(TablaEdiciones.PAGOFEBRERO));
                datEdi.setMar        (rs.getBoolean(TablaEdiciones.PAGOMARZO));
                datEdi.setAbr        (rs.getBoolean(TablaEdiciones.PAGOABRIL));
                datEdi.setMay        (rs.getBoolean(TablaEdiciones.PAGOMAYO));
                datEdi.setJun        (rs.getBoolean(TablaEdiciones.PAGOJUNIO));
                datEdi.setJul        (rs.getBoolean(TablaEdiciones.PAGOJULIO));
                datEdi.setAgo        (rs.getBoolean(TablaEdiciones.PAGOAGOSTO));
                datEdi.setSep        (rs.getBoolean(TablaEdiciones.PAGOSEPT));
                datEdi.setOct        (rs.getBoolean(TablaEdiciones.PAGOOCTUBRE));
                datEdi.setNov        (rs.getBoolean(TablaEdiciones.PAGONOVIEM));
                datEdi.setDic        (rs.getBoolean(TablaEdiciones.PAGODICIEMB));
                datEdi.setAplaz      (rs.getString (TablaEdiciones.APLAZADO));
                datEdi.setDescripcion(rs.getString (TablaEdiciones.DESCRIPCION ));
                datEdi.setIdProf     (rs.getString (TablaEdiciones.CODPROF ));

                listaEdi.addElement(datEdi);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaEdi;
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
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }
    
      //Método que devuelve los datos de edición de las ediciones disponibles de un curso determinado
    public static Vector devolverDatEdiCurDisp(String idCurso,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaEdiciones.CODEDI       + " , "
                                          + TablaEdiciones.CODCURSO     + " , " 
                                          + TablaEdiciones.CODNIVEL     + " , "
                                          + TablaEdiciones.FECHAINICIO  + " , " 
                                          + TablaEdiciones.FECHAFIN     + " , " 
                                          + TablaEdiciones.PLAZAS       + " , " 
                                          + TablaEdiciones.HORAS        + " , "
                                          + TablaEdiciones.HORAINICIO   + " , " 
                                          + TablaEdiciones.HORAFIN      + " , " 
                                          + TablaEdiciones.PRECIOMATRIC + " , " 
                                          + TablaEdiciones.PRECIORECIBO + " , " 
                                          + TablaEdiciones.PRECIOTOTAL  + " , " 
                                          + TablaEdiciones.CLASELUNES   + " , " 
                                          + TablaEdiciones.CLASEMARTES  + " , " 
                                          + TablaEdiciones.CLASEMIERC   + " , " 
                                          + TablaEdiciones.CLASEJUEVES  + " , " 
                                          + TablaEdiciones.CLASEVIERNES + " , "
                                          + TablaEdiciones.CLASESABADO  + " , " 
                                          + TablaEdiciones.CODCENTRO    + " , "
                                          + TablaEdiciones.MINUTOINICIO + " , "
                                          + TablaEdiciones.MINUTOFIN    + " , "
                                          + TablaEdiciones.HORASDISTAN  + " , "
                                          + TablaEdiciones.HORASTELEF   + " , "
                                          + TablaEdiciones.CODAULA      + " , "
                                          + TablaEdiciones.BONIFICADO   + " , "
                                          + TablaEdiciones.PLAZOS       + " , "
                                          + TablaEdiciones.PAGOENERO    + " , "
                                          + TablaEdiciones.PAGOFEBRERO  + " , "
                                          + TablaEdiciones.PAGOMARZO    + " , "
                                          + TablaEdiciones.PAGOABRIL    + " , "
                                          + TablaEdiciones.PAGOMAYO     + " , "
                                          + TablaEdiciones.PAGOJUNIO    + " , "
                                          + TablaEdiciones.PAGOJULIO    + " , "
                                          + TablaEdiciones.PAGOAGOSTO   + " , "
                                          + TablaEdiciones.PAGOSEPT     + " , "
                                          + TablaEdiciones.PAGOOCTUBRE  + " , "
                                          + TablaEdiciones.PAGONOVIEM   + " , "
                                          + TablaEdiciones.PAGODICIEMB  + " , "
                                          + TablaEdiciones.APLAZADO     + " , " 
                                          + TablaEdiciones.DESCRIPCION  + " , "
                                          + TablaEdiciones.CODPROF      +
                             " FROM "     + TablaEdiciones.TABLA        +
                             " WHERE "    + TablaEdiciones.CODCURSO     + "  = ? AND "  
                                          + TablaEdiciones.FECHAFIN     + " >= ?    " + 
                             " ORDER BY " + TablaEdiciones.FECHAINICIO  + " DESC";
        Vector              listaEdi = new Vector();

        EdicionesVO         datEdi   = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se le pasa los parámetros a la consulta sql
            ps.setString(1, idCurso);
            ps.setDate  (2, new Date(System.currentTimeMillis()));

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEdi = new EdicionesVO();

                datEdi.setIdEdi      (rs.getString (TablaEdiciones.CODEDI));
                datEdi.setIdCur      (rs.getString (TablaEdiciones.CODCURSO));
                datEdi.setIdNiv      (rs.getString (TablaEdiciones.CODNIVEL));
                datEdi.setFecIn      (rs.getDate   (TablaEdiciones.FECHAINICIO));
                datEdi.setFecFi      (rs.getDate   (TablaEdiciones.FECHAFIN));
                datEdi.setNumPla     (rs.getInt    (TablaEdiciones.PLAZAS));
                datEdi.setNumHor     (rs.getInt    (TablaEdiciones.HORAS));
                datEdi.setHorIn      (rs.getInt    (TablaEdiciones.HORAINICIO));
                datEdi.setHorFi      (rs.getInt    (TablaEdiciones.HORAFIN));
                datEdi.setPrecioM    (rs.getDouble (TablaEdiciones.PRECIOMATRIC));
                datEdi.setPrecioR    (rs.getDouble (TablaEdiciones.PRECIORECIBO));
                datEdi.setPrecioT    (rs.getDouble (TablaEdiciones.PRECIOTOTAL));
                datEdi.setHayLun     (rs.getBoolean(TablaEdiciones.CLASELUNES));
                datEdi.setHayMar     (rs.getBoolean(TablaEdiciones.CLASEMARTES));
                datEdi.setHayMie     (rs.getBoolean(TablaEdiciones.CLASEMIERC));
                datEdi.setHayJue     (rs.getBoolean(TablaEdiciones.CLASEJUEVES));
                datEdi.setHayVie     (rs.getBoolean(TablaEdiciones.CLASEVIERNES));
                datEdi.setHaySab     (rs.getBoolean(TablaEdiciones.CLASESABADO));
                datEdi.setCodCen     (rs.getInt    (TablaEdiciones.CODCENTRO));
                datEdi.setMinIn      (rs.getInt    (TablaEdiciones.MINUTOINICIO));
                datEdi.setMinFin     (rs.getInt    (TablaEdiciones.MINUTOFIN));
                datEdi.setHorDis     (rs.getInt    (TablaEdiciones.HORASDISTAN));
                datEdi.setHorTelef   (rs.getInt    (TablaEdiciones.HORASTELEF));
                datEdi.setIdAula     (rs.getString (TablaEdiciones.CODAULA));
                datEdi.setBonif      (rs.getString (TablaEdiciones.BONIFICADO));
                datEdi.setPlazos     (rs.getBoolean(TablaEdiciones.PLAZOS));
                datEdi.setEne        (rs.getBoolean(TablaEdiciones.PAGOENERO));
                datEdi.setFeb        (rs.getBoolean(TablaEdiciones.PAGOFEBRERO));
                datEdi.setMar        (rs.getBoolean(TablaEdiciones.PAGOMARZO));
                datEdi.setAbr        (rs.getBoolean(TablaEdiciones.PAGOABRIL));
                datEdi.setMay        (rs.getBoolean(TablaEdiciones.PAGOMAYO));
                datEdi.setJun        (rs.getBoolean(TablaEdiciones.PAGOJUNIO));
                datEdi.setJul        (rs.getBoolean(TablaEdiciones.PAGOJULIO));
                datEdi.setAgo        (rs.getBoolean(TablaEdiciones.PAGOAGOSTO));
                datEdi.setSep        (rs.getBoolean(TablaEdiciones.PAGOSEPT));
                datEdi.setOct        (rs.getBoolean(TablaEdiciones.PAGOOCTUBRE));
                datEdi.setNov        (rs.getBoolean(TablaEdiciones.PAGONOVIEM));
                datEdi.setDic        (rs.getBoolean(TablaEdiciones.PAGODICIEMB));
                datEdi.setAplaz      (rs.getString (TablaEdiciones.APLAZADO));
                datEdi.setDescripcion(rs.getString (TablaEdiciones.DESCRIPCION )); 
                datEdi.setIdProf     (rs.getString (TablaEdiciones.CODPROF ));    

                listaEdi.addElement(datEdi);
            }

            rs.close();
            ps.close();
            
            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }
    
    //Edita el registro de una edición
    public static int editaEdicion(EdicionesVO ediVO,Connection con) throws Exception
    {
        PreparedStatement   ps              = null;

        int                 regActualizados = 0;
        
        String              sql = "UPDATE " + TablaEdiciones.TABLA        + 
                                  " SET "   + TablaEdiciones.CODNIVEL     + " =  ? , "    
                                            + TablaEdiciones.FECHAINICIO  + " =  ? , "
                                            + TablaEdiciones.FECHAFIN     + " =  ? , " 
                                            + TablaEdiciones.PLAZAS       + " =  ? , "  
                                            + TablaEdiciones.HORAS        + " =  ? , "
                                            + TablaEdiciones.HORAINICIO   + " =  ? , "
                                            + TablaEdiciones.HORAFIN      + " =  ? , "
                                            + TablaEdiciones.PRECIOMATRIC + " =  ? , " 
                                            + TablaEdiciones.PRECIORECIBO + " =  ? , " 
                                            + TablaEdiciones.PRECIOTOTAL  + " =  ? , "
                                            + TablaEdiciones.CLASELUNES   + " =  ? , "
                                            + TablaEdiciones.CLASEMARTES  + " =  ? , " 
                                            + TablaEdiciones.CLASEMIERC   + " =  ? , "
                                            + TablaEdiciones.CLASEJUEVES  + " =  ? , "
                                            + TablaEdiciones.CLASEVIERNES + " =  ? , "
                                            + TablaEdiciones.CLASESABADO  + " =  ? , "
                                            + TablaEdiciones.CODCENTRO    + " =  ? , "
                                            + TablaEdiciones.MINUTOINICIO + " =  ? , " 
                                            + TablaEdiciones.MINUTOFIN    + " =  ? , "
                                            + TablaEdiciones.HORASDISTAN  + " =  ? , "
                                            + TablaEdiciones.HORASTELEF   + " =  ? , "
                                            + TablaEdiciones.CODAULA      + " =  ? , "
                                            + TablaEdiciones.BONIFICADO   + " =  ? , "
                                            + TablaEdiciones.PLAZOS       + " =  ? , "
                                            + TablaEdiciones.PAGOENERO    + " =  ? , "
                                            + TablaEdiciones.PAGOFEBRERO  + " =  ? , "
                                            + TablaEdiciones.PAGOMARZO    + " =  ? , "
                                            + TablaEdiciones.PAGOABRIL    + " =  ? , "
                                            + TablaEdiciones.PAGOMAYO     + " =  ? , "
                                            + TablaEdiciones.PAGOJUNIO    + " =  ? , "
                                            + TablaEdiciones.PAGOJULIO    + " =  ? , "
                                            + TablaEdiciones.PAGOAGOSTO   + " =  ? , "
                                            + TablaEdiciones.PAGOSEPT     + " =  ? , "
                                            + TablaEdiciones.PAGOOCTUBRE  + " =  ? , "
                                            + TablaEdiciones.PAGONOVIEM   + " =  ? , "
                                            + TablaEdiciones.PAGODICIEMB  + " =  ? , " 
                                            + TablaEdiciones.DESCRIPCION  + " =  ? , " 
                                            + TablaEdiciones.CODPROF      + " =  ?   " +
                
                                " WHERE "   + TablaEdiciones.CODEDI       + " =  ? ";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan lo parámetros a la consulta sql
            ps.setString ( 1, ediVO.getIdNiv());
            ps.setDate   ( 2, new Date(ediVO.getFecIn().getTime()));
            ps.setDate   ( 3, new Date(ediVO.getFecFi().getTime()));
            ps.setInt    ( 4, ediVO.getNumPla());
            ps.setInt    ( 5, ediVO.getNumHor());
            ps.setInt    ( 6, ediVO.getHorIn());
            ps.setInt    ( 7, ediVO.getHorFi());
            ps.setDouble ( 8, ediVO.getPrecioM());
            ps.setDouble ( 9, ediVO.getPrecioR());
            ps.setDouble (10, ediVO.getPrecioT());
            ps.setBoolean(11, ediVO.isHayLun());
            ps.setBoolean(12, ediVO.isHayMar());
            ps.setBoolean(13, ediVO.isHayMie());
            ps.setBoolean(14, ediVO.isHayJue());
            ps.setBoolean(15, ediVO.isHayVie());
            ps.setBoolean(16, ediVO.isHaySab());
            ps.setInt    (17, ediVO.getCodCen());
            ps.setInt    (18, ediVO.getMinIn());
            ps.setInt    (19, ediVO.getMinFin());
            ps.setInt    (20, ediVO.getHorDis());
            ps.setInt    (21, ediVO.getHorTelef());
            ps.setString (22, ediVO.getIdAula());
            ps.setString (23, ediVO.getBonif());
            ps.setBoolean(24, ediVO.isPlazos());
            ps.setBoolean(25, ediVO.isEne());
            ps.setBoolean(26, ediVO.isFeb());
            ps.setBoolean(27, ediVO.isMar());
            ps.setBoolean(28, ediVO.isAbr());
            ps.setBoolean(29, ediVO.isMay());
            ps.setBoolean(30, ediVO.isJun());
            ps.setBoolean(31, ediVO.isJul());
            ps.setBoolean(32, ediVO.isAgo());
            ps.setBoolean(33, ediVO.isSep());
            ps.setBoolean(34, ediVO.isOct());
            ps.setBoolean(35, ediVO.isNov());
            ps.setBoolean(36, ediVO.isDic());
            ps.setString (37, ediVO.getDescripcion());
            ps.setString (38, ediVO.getIdProf());
            ps.setString (39, ediVO.getIdEdi());
            
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
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve si un curso tiene ediciones
    public static boolean devolverHayEdiCur(String idCur,Connection con) throws Exception
    {
        PreparedStatement   ps        = null;
        ResultSet           rs        = null;

        String            sql      = "SELECT " + TablaEdiciones.CODEDI       + 
                                     " FROM "  + TablaEdiciones.TABLA        +
                                     " WHERE " + TablaEdiciones.CODCURSO     + " = ?";                        
        
        boolean             hayEdiCur = false;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, idCur);

            rs = ps.executeQuery();

            if (rs.next())
            {
                hayEdiCur = true;
            }
            else
            {
                hayEdiCur = false;
            }

            rs.close();
            ps.close();
           
            return hayEdiCur;

        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que elimina los datos de una edicion
    public static int eliminarDatosEdi(String codEdi,Connection con) throws Exception
    {
        PreparedStatement   ps     = null;

        String              sql    = "DELETE FROM "  + TablaEdiciones.TABLA  + 
                                     " WHERE "       + TablaEdiciones.CODEDI + " = ? ";
                
        int                 devRes = 0;
        
        //No se puede borrar si la edición tiene alumnos
        if(AluEdiGestion.edicionTieneAlumnos(codEdi))
        {
            return -2;
        }
        
        //Se borran los datos del matricula, temario e histórico de la edición eliminada
        devRes = devRes + AluEdiGestion.eliminarDatosMatEdi(codEdi);
        devRes = devRes + EdiModProfAulaGestion.borrarModulosEdi(codEdi);
        devRes = devRes + HisRecGestion.eliminarDatosHisEdi(codEdi);

        try
        {
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);

            devRes = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            return devRes;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
        
    }

    //Método que devuelve los datos de edición de todas las ediciones que empiezan a partir de hoy
    public static Vector devolverResNueEdi(Connection con) throws Exception
    {
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        String            sql         = "SELECT " + TablaEdiciones.CODEDI       + " , "
                                                  + TablaEdiciones.CODCURSO     + " , " 
                                                  + TablaEdiciones.CODNIVEL     + " , "
                                                  + TablaEdiciones.FECHAINICIO  + " , " 
                                                  + TablaEdiciones.FECHAFIN     + " , " 
                                                  + TablaEdiciones.PLAZAS       + " , " 
                                                  + TablaEdiciones.HORAS        + " , "
                                                  + TablaEdiciones.HORAINICIO   + " , " 
                                                  + TablaEdiciones.HORAFIN      + " , " 
                                                  + TablaEdiciones.PRECIOMATRIC + " , " 
                                                  + TablaEdiciones.PRECIORECIBO + " , " 
                                                  + TablaEdiciones.PRECIOTOTAL  + " , " 
                                                  + TablaEdiciones.CLASELUNES   + " , " 
                                                  + TablaEdiciones.CLASEMARTES  + " , " 
                                                  + TablaEdiciones.CLASEMIERC   + " , " 
                                                  + TablaEdiciones.CLASEJUEVES  + " , " 
                                                  + TablaEdiciones.CLASEVIERNES + " , "
                                                  + TablaEdiciones.CLASESABADO  + " , " 
                                                  + TablaEdiciones.CODCENTRO    + " , "
                                                  + TablaEdiciones.MINUTOINICIO + " , "
                                                  + TablaEdiciones.MINUTOFIN    + " , "
                                                  + TablaEdiciones.HORASDISTAN  + " , "
                                                  + TablaEdiciones.HORASTELEF   + " , "
                                                  + TablaEdiciones.CODAULA      + " , "
                                                  + TablaEdiciones.BONIFICADO   + " , "
                                                  + TablaEdiciones.PLAZOS       + " , "
                                                  + TablaEdiciones.PAGOENERO    + " , "
                                                  + TablaEdiciones.PAGOFEBRERO  + " , "
                                                  + TablaEdiciones.PAGOMARZO    + " , "
                                                  + TablaEdiciones.PAGOABRIL    + " , "
                                                  + TablaEdiciones.PAGOMAYO     + " , "
                                                  + TablaEdiciones.PAGOJUNIO    + " , "
                                                  + TablaEdiciones.PAGOJULIO    + " , "
                                                  + TablaEdiciones.PAGOAGOSTO   + " , "
                                                  + TablaEdiciones.PAGOSEPT     + " , "
                                                  + TablaEdiciones.PAGOOCTUBRE  + " , "
                                                  + TablaEdiciones.PAGONOVIEM   + " , "
                                                  + TablaEdiciones.PAGODICIEMB  + " , "
                                                  + TablaEdiciones.APLAZADO     + " , "
                                                  + TablaEdiciones.DESCRIPCION  + " , "
                                                  + TablaEdiciones.CODPROF      +
                
                                     " FROM "     + TablaEdiciones.TABLA        +
                                     " WHERE "    + TablaEdiciones.FECHAFIN     + " >= CURDATE() " +
                                     " ORDER BY " + TablaEdiciones.CODCURSO;                
        
        Vector            listaEdi    = new Vector();

        EdicionesVO       datEdi      = null;

        try
        {
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEdi = new EdicionesVO();

                datEdi.setIdEdi      (rs.getString (TablaEdiciones.CODEDI));
                datEdi.setIdCur      (rs.getString (TablaEdiciones.CODCURSO));
                datEdi.setIdNiv      (rs.getString (TablaEdiciones.CODNIVEL));
                datEdi.setFecIn      (rs.getDate   (TablaEdiciones.FECHAINICIO));
                datEdi.setFecFi      (rs.getDate   (TablaEdiciones.FECHAFIN));
                datEdi.setNumPla     (rs.getInt    (TablaEdiciones.PLAZAS));
                datEdi.setNumHor     (rs.getInt    (TablaEdiciones.HORAS));
                datEdi.setHorIn      (rs.getInt    (TablaEdiciones.HORAINICIO));
                datEdi.setHorFi      (rs.getInt    (TablaEdiciones.HORAFIN));
                datEdi.setPrecioM    (rs.getDouble (TablaEdiciones.PRECIOMATRIC));
                datEdi.setPrecioR    (rs.getDouble (TablaEdiciones.PRECIORECIBO));
                datEdi.setPrecioT    (rs.getDouble (TablaEdiciones.PRECIOTOTAL));
                datEdi.setHayLun     (rs.getBoolean(TablaEdiciones.CLASELUNES));
                datEdi.setHayMar     (rs.getBoolean(TablaEdiciones.CLASEMARTES));
                datEdi.setHayMie     (rs.getBoolean(TablaEdiciones.CLASEMIERC));
                datEdi.setHayJue     (rs.getBoolean(TablaEdiciones.CLASEJUEVES));
                datEdi.setHayVie     (rs.getBoolean(TablaEdiciones.CLASEVIERNES));
                datEdi.setHaySab     (rs.getBoolean(TablaEdiciones.CLASESABADO));
                datEdi.setCodCen     (rs.getInt    (TablaEdiciones.CODCENTRO));
                datEdi.setMinIn      (rs.getInt    (TablaEdiciones.MINUTOINICIO));
                datEdi.setMinFin     (rs.getInt    (TablaEdiciones.MINUTOFIN));
                datEdi.setHorDis     (rs.getInt    (TablaEdiciones.HORASDISTAN));
                datEdi.setHorTelef   (rs.getInt    (TablaEdiciones.HORASTELEF));
                datEdi.setIdAula     (rs.getString (TablaEdiciones.CODAULA));
                datEdi.setBonif      (rs.getString (TablaEdiciones.BONIFICADO));
                datEdi.setPlazos     (rs.getBoolean(TablaEdiciones.PLAZOS));
                datEdi.setEne        (rs.getBoolean(TablaEdiciones.PAGOENERO));
                datEdi.setFeb        (rs.getBoolean(TablaEdiciones.PAGOFEBRERO));
                datEdi.setMar        (rs.getBoolean(TablaEdiciones.PAGOMARZO));
                datEdi.setAbr        (rs.getBoolean(TablaEdiciones.PAGOABRIL));
                datEdi.setMay        (rs.getBoolean(TablaEdiciones.PAGOMAYO));
                datEdi.setJun        (rs.getBoolean(TablaEdiciones.PAGOJUNIO));
                datEdi.setJul        (rs.getBoolean(TablaEdiciones.PAGOJULIO));
                datEdi.setAgo        (rs.getBoolean(TablaEdiciones.PAGOAGOSTO));
                datEdi.setSep        (rs.getBoolean(TablaEdiciones.PAGOSEPT));
                datEdi.setOct        (rs.getBoolean(TablaEdiciones.PAGOOCTUBRE));
                datEdi.setNov        (rs.getBoolean(TablaEdiciones.PAGONOVIEM));
                datEdi.setDic        (rs.getBoolean(TablaEdiciones.PAGODICIEMB));
                datEdi.setAplaz      (rs.getString (TablaEdiciones.APLAZADO));
                datEdi.setDescripcion(rs.getString (TablaEdiciones.DESCRIPCION));
                datEdi.setIdProf     (rs.getString (TablaEdiciones.CODPROF));

                listaEdi.addElement(datEdi);
            }

            rs.close();
            ps.close();
            
            return listaEdi;
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
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve si hay niveles asociados a una edición
    public static boolean estaNivelenEdicion(String codNivel,Connection con) throws Exception
    {
        PreparedStatement   ps        = null;
        ResultSet           rs        = null;

        String              sql       = "SELECT " + TablaEdiciones.CODNIVEL + 
                                        " FROM "  + TablaEdiciones.TABLA    +
                                        " WHERE " + TablaEdiciones.CODNIVEL + " = ?";
        
        boolean             hayNivEdi = false;
   
        try
        {
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codNivel);                        
            rs = ps.executeQuery();

            if (rs.next())
            {
               hayNivEdi = true;
            }

            rs.close();
            ps.close();
            
            return hayNivEdi;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            throw exc;
        }
    }
    
    //Método que devuelve los datos de edición de todas las ediciones de un determinado tutor
    public static Vector devolverEdiTutor(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        String            sql      = "SELECT " + TablaEdiciones.CODEDI       + " , "
                                               + TablaEdiciones.CODCURSO     + " , " 
                                               + TablaEdiciones.CODNIVEL     + " , "
                                               + TablaEdiciones.FECHAINICIO  + " , " 
                                               + TablaEdiciones.FECHAFIN     + " , " 
                                               + TablaEdiciones.PLAZAS       + " , " 
                                               + TablaEdiciones.HORAS        + " , "
                                               + TablaEdiciones.HORAINICIO   + " , " 
                                               + TablaEdiciones.HORAFIN      + " , " 
                                               + TablaEdiciones.PRECIOMATRIC + " , " 
                                               + TablaEdiciones.PRECIORECIBO + " , " 
                                               + TablaEdiciones.PRECIOTOTAL  + " , " 
                                               + TablaEdiciones.CLASELUNES   + " , " 
                                               + TablaEdiciones.CLASEMARTES  + " , " 
                                               + TablaEdiciones.CLASEMIERC   + " , " 
                                               + TablaEdiciones.CLASEJUEVES  + " , " 
                                               + TablaEdiciones.CLASEVIERNES + " , "
                                               + TablaEdiciones.CLASESABADO  + " , " 
                                               + TablaEdiciones.CODCENTRO    + " , "
                                               + TablaEdiciones.MINUTOINICIO + " , "
                                               + TablaEdiciones.MINUTOFIN    + " , "
                                               + TablaEdiciones.HORASDISTAN  + " , "
                                               + TablaEdiciones.HORASTELEF   + " , "
                                               + TablaEdiciones.CODAULA      + " , "
                                               + TablaEdiciones.BONIFICADO   + " , "
                                               + TablaEdiciones.PLAZOS       + " , "
                                               + TablaEdiciones.PAGOENERO    + " , "
                                               + TablaEdiciones.PAGOFEBRERO  + " , "
                                               + TablaEdiciones.PAGOMARZO    + " , "
                                               + TablaEdiciones.PAGOABRIL    + " , "
                                               + TablaEdiciones.PAGOMAYO     + " , "
                                               + TablaEdiciones.PAGOJUNIO    + " , "
                                               + TablaEdiciones.PAGOJULIO    + " , "
                                               + TablaEdiciones.PAGOAGOSTO   + " , "
                                               + TablaEdiciones.PAGOSEPT     + " , "
                                               + TablaEdiciones.PAGOOCTUBRE  + " , "
                                               + TablaEdiciones.PAGONOVIEM   + " , "
                                               + TablaEdiciones.PAGODICIEMB  + " , "
                                               + TablaEdiciones.APLAZADO     + " , "
                                               + TablaEdiciones.DESCRIPCION  + " , "
                                               + TablaEdiciones.CODPROF      +
                                     " FROM "  + TablaEdiciones.TABLA        +
                                     " WHERE " + TablaEdiciones.CODPROF      + " = ? " +
                                     " ORDER BY " + TablaEdiciones.FECHAINICIO + " DESC";
                     
        Vector            listaEdi = new Vector();

        EdicionesVO       datEdi   = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros
            ps.setString(1, codProf);
            
            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEdi = new EdicionesVO();

                datEdi.setIdEdi      (rs.getString (TablaEdiciones.CODEDI));
                datEdi.setIdCur      (rs.getString (TablaEdiciones.CODCURSO));
                datEdi.setIdNiv      (rs.getString (TablaEdiciones.CODNIVEL));
                datEdi.setFecIn      (rs.getDate   (TablaEdiciones.FECHAINICIO));
                datEdi.setFecFi      (rs.getDate   (TablaEdiciones.FECHAFIN));
                datEdi.setNumPla     (rs.getInt    (TablaEdiciones.PLAZAS));
                datEdi.setNumHor     (rs.getInt    (TablaEdiciones.HORAS));
                datEdi.setHorIn      (rs.getInt    (TablaEdiciones.HORAINICIO));
                datEdi.setHorFi      (rs.getInt    (TablaEdiciones.HORAFIN));
                datEdi.setPrecioM    (rs.getDouble (TablaEdiciones.PRECIOMATRIC));
                datEdi.setPrecioR    (rs.getDouble (TablaEdiciones.PRECIORECIBO));
                datEdi.setPrecioT    (rs.getDouble (TablaEdiciones.PRECIOTOTAL));
                datEdi.setHayLun     (rs.getBoolean(TablaEdiciones.CLASELUNES));
                datEdi.setHayMar     (rs.getBoolean(TablaEdiciones.CLASEMARTES));
                datEdi.setHayMie     (rs.getBoolean(TablaEdiciones.CLASEMIERC));
                datEdi.setHayJue     (rs.getBoolean(TablaEdiciones.CLASEJUEVES));
                datEdi.setHayVie     (rs.getBoolean(TablaEdiciones.CLASEVIERNES));
                datEdi.setHaySab     (rs.getBoolean(TablaEdiciones.CLASESABADO));
                datEdi.setCodCen     (rs.getInt    (TablaEdiciones.CODCENTRO));
                datEdi.setMinIn      (rs.getInt    (TablaEdiciones.MINUTOINICIO));
                datEdi.setMinFin     (rs.getInt    (TablaEdiciones.MINUTOFIN));
                datEdi.setHorDis     (rs.getInt    (TablaEdiciones.HORASDISTAN));
                datEdi.setHorTelef   (rs.getInt    (TablaEdiciones.HORASTELEF));
                datEdi.setIdAula     (rs.getString (TablaEdiciones.CODAULA));
                datEdi.setBonif      (rs.getString (TablaEdiciones.BONIFICADO));
                datEdi.setPlazos     (rs.getBoolean(TablaEdiciones.PLAZOS));
                datEdi.setEne        (rs.getBoolean(TablaEdiciones.PAGOENERO));
                datEdi.setFeb        (rs.getBoolean(TablaEdiciones.PAGOFEBRERO));
                datEdi.setMar        (rs.getBoolean(TablaEdiciones.PAGOMARZO));
                datEdi.setAbr        (rs.getBoolean(TablaEdiciones.PAGOABRIL));
                datEdi.setMay        (rs.getBoolean(TablaEdiciones.PAGOMAYO));
                datEdi.setJun        (rs.getBoolean(TablaEdiciones.PAGOJUNIO));
                datEdi.setJul        (rs.getBoolean(TablaEdiciones.PAGOJULIO));
                datEdi.setAgo        (rs.getBoolean(TablaEdiciones.PAGOAGOSTO));
                datEdi.setSep        (rs.getBoolean(TablaEdiciones.PAGOSEPT));
                datEdi.setOct        (rs.getBoolean(TablaEdiciones.PAGOOCTUBRE));
                datEdi.setNov        (rs.getBoolean(TablaEdiciones.PAGONOVIEM));
                datEdi.setDic        (rs.getBoolean(TablaEdiciones.PAGODICIEMB));
                datEdi.setAplaz      (rs.getString (TablaEdiciones.APLAZADO));
                datEdi.setDescripcion(rs.getString (TablaEdiciones.DESCRIPCION));
                datEdi.setIdProf     (rs.getString (TablaEdiciones.CODPROF));

                listaEdi.addElement(datEdi);
            }

            rs.close();
            ps.close();
           
            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
}
