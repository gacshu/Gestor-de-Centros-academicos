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
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.datos.ClasesIndivVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.gestion.ClasesIndivGestion;
import es.jahernandez.tablas.TablaClasesIndiv;

/**
 *
 * @author JuanAlberto
 */
public class ClasesIndivDAO
{
    //Método que devuelve los datos de una clase individual
    public static ClasesIndivVO devolverDatosClaseIndiv(String codClasInd, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaClasesIndiv.CODCLASEIND + " , "
                                                     + TablaClasesIndiv.CODCURSO    + " , "
                                                     + TablaClasesIndiv.CODALU      + " , "
                                                     + TablaClasesIndiv.FECHACLASE  + " , "
                                                     + TablaClasesIndiv.CODPROF     + " , "
                                                     + TablaClasesIndiv.TARIFA      +
                                           " FROM "  + TablaClasesIndiv.TABLA       +
                                           " WHERE " + TablaClasesIndiv.CODCLASEIND + " = ?";

        ClasesIndivVO     claIndVO       = new ClasesIndivVO();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codClasInd );

            rs  = ps.executeQuery();

            if (rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));
            }

            rs.close();
            ps.close();

            return claIndVO;

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
                Logger.getLogger(ClasesIndivDAO .class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Método que devuelve todos los registro de clases individuales
    public static Vector devolverTodasClasesIndividuales(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaClasesIndiv.CODCLASEIND + " , "
                                                     + TablaClasesIndiv.CODCURSO    + " , "
                                                     + TablaClasesIndiv.CODALU      + " , "
                                                     + TablaClasesIndiv.FECHACLASE  + " , "
                                                     + TablaClasesIndiv.CODPROF     + " , "
                                                     + TablaClasesIndiv.TARIFA      +
                                           " FROM "  + TablaClasesIndiv.TABLA       ;


        ClasesIndivVO     claIndVO       = null;
        Vector            listaClaInd    = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));

                listaClaInd.add(claIndVO);
            }

            rs.close();
            ps.close();

            return listaClaInd;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarClasInd(ClasesIndivVO claIndVO, Connection con) throws Exception
    {
        PreparedStatement ps             = null;

        String            cadenaConsulta = "INSERT INTO " + TablaClasesIndiv.TABLA       + " ( "
                                                          + TablaClasesIndiv.CODCLASEIND + " , "
                                                          + TablaClasesIndiv.CODCURSO    + " , "
                                                          + TablaClasesIndiv.CODALU      + " , "
                                                          + TablaClasesIndiv.FECHACLASE  + " , "
                                                          + TablaClasesIndiv.CODPROF     + " , "
                                                          + TablaClasesIndiv.TARIFA      + " ) " +
                                           " VALUES(?,?,?,?,?,?)";


        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString (1, nueCodClasInd());
            ps.setString (2, claIndVO.getIdCur());
            ps.setString (3, claIndVO.getIdAlu());
            ps.setDate   (4, new Date(claIndVO.getFecClase().getTime()));
            ps.setString (5, claIndVO.getIdProf());
            ps.setDouble (6, claIndVO.getTarifa());

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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que genera un nuevo código de clase individual
    public static String nueCodClasInd()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datClaInd = ClasesIndivGestion.devolverTodasClasesIndividuales();

        while (enc)
        {
            contCar = ("" + datClaInd.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datClaInd.size() + avc);
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
            datClaInd= ClasesIndivGestion.devolverTodasClasesIndividuales();
            for (int ind = 0; ind < datClaInd.size(); ind++)
            {
                ClasesIndivVO claIndVO = (ClasesIndivVO) datClaInd.elementAt(ind);
                if (claIndVO.getIdClaseInd().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;
    }

    //Método que devuelve los datos de clases individuales de un alumno
    public static Vector devolverClasesIndAlu(String codAlu, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        String            sql          = "SELECT "  + TablaClasesIndiv.CODCLASEIND + " , "
                                                    + TablaClasesIndiv.CODCURSO    + " , "
                                                    + TablaClasesIndiv.CODALU      + " , "
                                                    + TablaClasesIndiv.FECHACLASE  + " , "
                                                    + TablaClasesIndiv.CODPROF     + " , "
                                                    + TablaClasesIndiv.TARIFA      +
                                          " FROM "  + TablaClasesIndiv.TABLA       +
                                          " WHERE " + TablaClasesIndiv.CODALU      + " = ?";

        ClasesIndivVO     claIndVO     = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));

                listaClaInd.add(claIndVO);
            }

            rs.close();
            ps.close();

            return listaClaInd;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

     //Edita el registro de una clase individual
    public static int editarClaseInd(ClasesIndivVO claIndVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            sql             = "UPDATE " + TablaClasesIndiv.TABLA       +
                                            " SET "   + TablaClasesIndiv.FECHACLASE  + " = ? , "
                                                      + TablaClasesIndiv.CODPROF     + " = ? , "
                                                      + TablaClasesIndiv.TARIFA      + " = ?   " +
                                            " WHERE " + TablaClasesIndiv.CODCLASEIND + " = ?";

        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setDate   (1, new Date(claIndVO.getFecClase().getTime()));
            ps.setString (2, claIndVO.getIdProf());
            ps.setDouble (3, claIndVO.getTarifa());
            ps.setString (4, claIndVO.getIdClaseInd());

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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Borra el registro de una clase individual
    public static int eliminaClaseInd(String codClaInd, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            sql             = "DELETE FROM " + TablaClasesIndiv.TABLA       +
                                            " WHERE "      + TablaClasesIndiv.CODCLASEIND + " = ?";

        int               regActualizados = 0;


        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codClaInd);

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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Borra las clases individuales de un alumno
    public static int eliminarClasIndAlumno(String codAlu, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            sql             = "DELETE FROM " + TablaClasesIndiv.TABLA    +
                                            " WHERE "      + TablaClasesIndiv.CODALU   + " = ?";

        int               regActualizados = 0;


        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codAlu);

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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve si un alumno tiene clases individuales
    public static boolean tieneAluClasesInd(String codAlu, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        String            sql          = "SELECT "  + TablaClasesIndiv.CODCLASEIND +
                                          " FROM "  + TablaClasesIndiv.TABLA       +
                                          " WHERE " + TablaClasesIndiv.CODALU      + " = ?";

       boolean          aluTienClases  = false;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);

            rs  = ps.executeQuery();

            if(rs.next())
            {
                aluTienClases = true;
            }

            rs.close();
            ps.close();

            return aluTienClases;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de clases individuales de un alumno y un mes y año concreto ordenados for fecha
    public static Vector devolverClasesIndAluMes(String codAlu, String strFecMesAnno, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
        int               annoIn       = new Integer(strFecMesAnno.substring(2,6)).intValue();
        int               mesIn        = new Integer(strFecMesAnno.substring(0,2)).intValue();
        int               annoFi       = annoIn ;
        int               mesFi        = mesIn + 1;

        if(mesFi > 12 )
        {
            mesFi = 1;
            annoFi++;
        }

        Date              fechaIni     = new Date( new GregorianCalendar(annoIn,mesIn-1,1).getTimeInMillis() ) ;
        Date              fechaFin     = new Date( new GregorianCalendar(annoFi,mesFi-1,1).getTimeInMillis() ) ;


        String            sql          = "SELECT "     + TablaClasesIndiv.CODCLASEIND + " , "
                                                       + TablaClasesIndiv.CODCURSO    + " , "
                                                       + TablaClasesIndiv.CODALU      + " , "
                                                       + TablaClasesIndiv.FECHACLASE  + " , "
                                                       + TablaClasesIndiv.CODPROF     + " , "
                                                       + TablaClasesIndiv.TARIFA      +
                                          " FROM "     + TablaClasesIndiv.TABLA       +
                                          " WHERE "    + TablaClasesIndiv.CODALU      + "  = ? AND "
                                                       + TablaClasesIndiv.FECHACLASE  + " >= ? AND "
                                                       + TablaClasesIndiv.FECHACLASE  + " <  ? "     +
                                          " ORDER BY " + TablaClasesIndiv.FECHACLASE  ;

        ClasesIndivVO     claIndVO     = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);
            ps.setDate  (2, fechaIni);
            ps.setDate  (3, fechaFin);
            rs  = ps.executeQuery();

            while(rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));

                listaClaInd.add(claIndVO);
            }

            rs.close();
            ps.close();

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de clases individuales de un mes y año concreto ordenados por alumno y fecha
    public static Vector devolverClasesIndMes(String strFecMesAnno, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
        int               annoIn       = new Integer(strFecMesAnno.substring(2,6)).intValue();
        int               mesIn        = new Integer(strFecMesAnno.substring(0,2)).intValue();
        int               annoFi       = annoIn ;
        int               mesFi        = mesIn + 1;

        if(mesFi > 12 )
        {
            mesFi = 1;
            annoFi++;
        }

        Date              fechaIni     = new Date( new GregorianCalendar(annoIn,mesIn-1,1).getTimeInMillis() ) ;
        Date              fechaFin     = new Date( new GregorianCalendar(annoFi,mesFi-1,1).getTimeInMillis() ) ;


        String            sql          = "SELECT "    + TablaClasesIndiv.CODCLASEIND + " , "
                                                      + TablaClasesIndiv.CODCURSO    + " , "
                                                      + TablaClasesIndiv.CODALU      + " , "
                                                      + TablaClasesIndiv.FECHACLASE  + " , "
                                                      + TablaClasesIndiv.CODPROF     + " , "
                                                      + TablaClasesIndiv.TARIFA      +
                                         " FROM "     + TablaClasesIndiv.TABLA       +
                                         " WHERE "    + TablaClasesIndiv.FECHACLASE  + " >= ? AND "
                                                      + TablaClasesIndiv.FECHACLASE  + " <  ?  "    +
                                         " ORDER BY " + TablaClasesIndiv.CODALU      + " , "
                                                      + TablaClasesIndiv.FECHACLASE;

        ClasesIndivVO     claIndVO     = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setDate  (1, fechaIni);
            ps.setDate  (2, fechaFin);
            rs  = ps.executeQuery();

            while(rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));

                listaClaInd.add(claIndVO);
            }

            rs.close();
            ps.close();

            return listaClaInd;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve el importe total de las clases de un alumno en un mes
    public static float devolverImporteClaseAluMes(String codAlu, String strFecMesAnno, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
        int               annoIn       = new Integer(strFecMesAnno.substring(2,6)).intValue();
        int               mesIn        = new Integer(strFecMesAnno.substring(0,2)).intValue();
        int               annoFi       = annoIn ;
        int               mesFi        = mesIn + 1;

        if(mesFi > 12 )
        {
            mesFi = 1;
            annoFi++;
        }

        Date              fechaIni     = new Date( new GregorianCalendar(annoIn,mesIn-1,1).getTimeInMillis() ) ;
        Date              fechaFin     = new Date( new GregorianCalendar(annoFi,mesFi-1,1).getTimeInMillis() ) ;


        String            sql          = "SELECT SUM(" + TablaClasesIndiv.TARIFA      + ")" +
                                          " FROM "     + TablaClasesIndiv.TABLA       +
                                          " WHERE "    + TablaClasesIndiv.CODALU      + "  = ? AND "
                                                       + TablaClasesIndiv.FECHACLASE  + " >= ? AND "
                                                       + TablaClasesIndiv.FECHACLASE  + " <  ? "   ;


        float            impTotal      = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);
            ps.setDate  (2, fechaIni);
            ps.setDate  (3, fechaFin);
            rs  = ps.executeQuery();

            if(rs.next())
            {
                impTotal = rs.getFloat(1);
            }

            rs.close();
            ps.close();

            return impTotal;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de clases individuales de un profesor
    public static Vector devolverClasesIndProf(String codProf , Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        String            sql          = "SELECT "      + TablaClasesIndiv.CODCLASEIND + " , "
                                                        + TablaClasesIndiv.CODCURSO    + " , "
                                                        + TablaClasesIndiv.CODALU      + " , "
                                                        + TablaClasesIndiv.FECHACLASE  + " , "
                                                        + TablaClasesIndiv.CODPROF     + " , "
                                                        + TablaClasesIndiv.TARIFA      +
                                          " FROM "      + TablaClasesIndiv.TABLA       +
                                          " WHERE "     + TablaClasesIndiv.CODPROF     + " = ? " +
                                          " ORDER BY "  + TablaClasesIndiv.FECHACLASE;

        ClasesIndivVO     claIndVO     = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codProf);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));

                listaClaInd.add(claIndVO);
            }

            rs.close();
            ps.close();

            return listaClaInd;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de clases individuales de un profesor y un mes y año concreto ordenados for fecha
    public static Vector devolverClasesIndProfMes(String codProf, String strFecMesAnno, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
        int               annoIn       = new Integer(strFecMesAnno.substring(2,6)).intValue();
        int               mesIn        = new Integer(strFecMesAnno.substring(0,2)).intValue();
        int               annoFi       = annoIn ;
        int               mesFi        = mesIn + 1;

        if(mesFi > 12 )
        {
            mesFi = 1;
            annoFi++;
        }

        Date              fechaIni     = new Date( new GregorianCalendar(annoIn,mesIn-1,1).getTimeInMillis() ) ;
        Date              fechaFin     = new Date( new GregorianCalendar(annoFi,mesFi-1,1).getTimeInMillis() ) ;


        String            sql          = "SELECT "     + TablaClasesIndiv.CODCLASEIND + " , "
                                                       + TablaClasesIndiv.CODCURSO    + " , "
                                                       + TablaClasesIndiv.CODALU      + " , "
                                                       + TablaClasesIndiv.FECHACLASE  + " , "
                                                       + TablaClasesIndiv.CODPROF     + " , "
                                                       + TablaClasesIndiv.TARIFA      +
                                          " FROM "     + TablaClasesIndiv.TABLA       +
                                          " WHERE "    + TablaClasesIndiv.CODPROF     + "  = ? AND "
                                                       + TablaClasesIndiv.FECHACLASE  + " >= ? AND "
                                                       + TablaClasesIndiv.FECHACLASE  + " <  ? "     +
                                          " ORDER BY " + TablaClasesIndiv.FECHACLASE  ;

        ClasesIndivVO     claIndVO     = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codProf);
            ps.setDate  (2, fechaIni);
            ps.setDate  (3, fechaFin);
            rs  = ps.executeQuery();

            while(rs.next())
            {
                claIndVO = new ClasesIndivVO();

                claIndVO.setIdClaseInd(rs.getString (TablaClasesIndiv.CODCLASEIND));
                claIndVO.setIdCur     (rs.getString (TablaClasesIndiv.CODCURSO));
                claIndVO.setIdAlu     (rs.getString (TablaClasesIndiv.CODALU));
                claIndVO.setFecClase  (rs.getDate   (TablaClasesIndiv.FECHACLASE));
                claIndVO.setIdProf    (rs.getString (TablaClasesIndiv.CODPROF));
                claIndVO.setTarifa    (rs.getFloat  (TablaClasesIndiv.TARIFA));

                listaClaInd.add(claIndVO);
            }

            rs.close();
            ps.close();

            return listaClaInd;
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
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

}