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

import es.jahernandez.datos.EdiModProfAulaVO;
import es.jahernandez.tablas.TablaEdiModProfAula;

/**
 *
 * @author JuanAlberto
 */
public class EdiModProfAulaDAO
{
    //Método que devuelve los datos de un módulo de una edición
    public static EdiModProfAulaVO devolverDatosModEdi(String codMod, String codEdi, Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaEdiModProfAula.CODEDI       + " , "
                                          + TablaEdiModProfAula.CODMOD       + " , "
                                          + TablaEdiModProfAula.CODPROF      + " , "
                                          + TablaEdiModProfAula.CODAULA      + " , "
                                          + TablaEdiModProfAula.FECHAINICIO  + " , "
                                          + TablaEdiModProfAula.FECHAFIN     + " , "
                                          + TablaEdiModProfAula.HORAINICIO   + " , "
                                          + TablaEdiModProfAula.HORAFIN      + " , "
                                          + TablaEdiModProfAula.CLASELUNES   + " , "
                                          + TablaEdiModProfAula.CLASEMARTES  + " , "
                                          + TablaEdiModProfAula.CLASEMIERC   + " , "
                                          + TablaEdiModProfAula.CLASEJUEVES  + " , "
                                          + TablaEdiModProfAula.CLASEVIERNES + " , "
                                          + TablaEdiModProfAula.CLASESABADO  +
                                " FROM "  + TablaEdiModProfAula.TABLA        +
                                " WHERE " + TablaEdiModProfAula.CODMOD       + " = ? AND "
                                          + TablaEdiModProfAula.CODEDI       + " = ? ";

        EdiModProfAulaVO  datEMPA = new EdiModProfAulaVO();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codMod);
            ps.setString(2, codEdi);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                datEMPA = new EdiModProfAulaVO();

                datEMPA.setIdEdi (rs.getString (TablaEdiModProfAula.CODEDI));
                datEMPA.setIdMod (rs.getString (TablaEdiModProfAula.CODMOD));
                datEMPA.setIdProf(rs.getString (TablaEdiModProfAula.CODPROF));
                datEMPA.setIdAul (rs.getString (TablaEdiModProfAula.CODAULA ));
                datEMPA.setFecIni(rs.getDate   (TablaEdiModProfAula.FECHAINICIO));
                datEMPA.setFecFin(rs.getDate   (TablaEdiModProfAula.FECHAFIN));
                datEMPA.setHorIni(rs.getString (TablaEdiModProfAula.HORAINICIO));
                datEMPA.setHorFin(rs.getString (TablaEdiModProfAula.HORAFIN));
                datEMPA.setHayLun(rs.getBoolean(TablaEdiModProfAula.CLASELUNES));
                datEMPA.setHayMar(rs.getBoolean(TablaEdiModProfAula.CLASEMARTES));
                datEMPA.setHayMie(rs.getBoolean(TablaEdiModProfAula.CLASEMIERC));
                datEMPA.setHayJue(rs.getBoolean(TablaEdiModProfAula.CLASEJUEVES));
                datEMPA.setHayVie(rs.getBoolean(TablaEdiModProfAula.CLASEVIERNES));
                datEMPA.setHaySab(rs.getBoolean(TablaEdiModProfAula.CLASESABADO));
            }

            rs.close();
            ps.close();
            return datEMPA;

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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Método que devuelve los  módulos de una edición
    public static Vector devolverModEdi(String codEdi,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaEdiModProfAula.CODEDI       + " , "
                                          + TablaEdiModProfAula.CODMOD       + " , "
                                          + TablaEdiModProfAula.CODPROF      + " , "
                                          + TablaEdiModProfAula.CODAULA      + " , "
                                          + TablaEdiModProfAula.FECHAINICIO  + " , "
                                          + TablaEdiModProfAula.FECHAFIN     + " , "
                                          + TablaEdiModProfAula.HORAINICIO   + " , "
                                          + TablaEdiModProfAula.HORAFIN      + " , "
                                          + TablaEdiModProfAula.CLASELUNES   + " , "
                                          + TablaEdiModProfAula.CLASEMARTES  + " , "
                                          + TablaEdiModProfAula.CLASEMIERC   + " , "
                                          + TablaEdiModProfAula.CLASEJUEVES  + " , "
                                          + TablaEdiModProfAula.CLASEVIERNES + " , "
                                          + TablaEdiModProfAula.CLASESABADO  +
                                " FROM "  + TablaEdiModProfAula.TABLA        +
                                " WHERE " + TablaEdiModProfAula.CODEDI       + " = ? " ;


        EdiModProfAulaVO datEMPA    = null;
        Vector           listaEMPA  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            ps.setString(1, codEdi);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datEMPA = new EdiModProfAulaVO();

                datEMPA.setIdEdi  (rs.getString (TablaEdiModProfAula.CODEDI));
                datEMPA.setIdMod  (rs.getString (TablaEdiModProfAula.CODMOD));
                datEMPA.setIdProf (rs.getString (TablaEdiModProfAula.CODPROF));
                datEMPA.setIdAul  (rs.getString (TablaEdiModProfAula.CODAULA ));
                datEMPA.setFecIni (rs.getDate   (TablaEdiModProfAula.FECHAINICIO));
                datEMPA.setFecFin (rs.getDate   (TablaEdiModProfAula.FECHAFIN));
                datEMPA.setHorIni (rs.getString (TablaEdiModProfAula.HORAINICIO));
                datEMPA.setHorFin (rs.getString (TablaEdiModProfAula.HORAFIN));
                datEMPA.setHayLun (rs.getBoolean(TablaEdiModProfAula.CLASELUNES));
                datEMPA.setHayMar (rs.getBoolean(TablaEdiModProfAula.CLASEMARTES));
                datEMPA.setHayMie (rs.getBoolean(TablaEdiModProfAula.CLASEMIERC));
                datEMPA.setHayJue (rs.getBoolean(TablaEdiModProfAula.CLASEJUEVES));
                datEMPA.setHayVie (rs.getBoolean(TablaEdiModProfAula.CLASEVIERNES));
                datEMPA.setHaySab (rs.getBoolean(TablaEdiModProfAula.CLASESABADO));


                listaEMPA.add(datEMPA);
            }

            rs.close();
            ps.close();
            return listaEMPA;
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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los  módulos de un profesor
    public static Vector devolverModProf(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaEdiModProfAula.CODEDI       + " , "
                                          + TablaEdiModProfAula.CODMOD       + " , "
                                          + TablaEdiModProfAula.CODPROF      + " , "
                                          + TablaEdiModProfAula.CODAULA      + " , "
                                          + TablaEdiModProfAula.FECHAINICIO  + " , "
                                          + TablaEdiModProfAula.FECHAFIN     + " , "
                                          + TablaEdiModProfAula.HORAINICIO   + " , "
                                          + TablaEdiModProfAula.HORAFIN      + " , "
                                          + TablaEdiModProfAula.CLASELUNES   + " , "
                                          + TablaEdiModProfAula.CLASEMARTES  + " , "
                                          + TablaEdiModProfAula.CLASEMIERC   + " , "
                                          + TablaEdiModProfAula.CLASEJUEVES  + " , "
                                          + TablaEdiModProfAula.CLASEVIERNES + " , "
                                          + TablaEdiModProfAula.CLASESABADO  +
                                " FROM "  + TablaEdiModProfAula.TABLA        +
                                " WHERE " + TablaEdiModProfAula.CODPROF      + " = ? " ;


        EdiModProfAulaVO datEMPA    = null;
        Vector           listaEMPA  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            ps.setString(1, codProf);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datEMPA = new EdiModProfAulaVO();

                datEMPA.setIdEdi  (rs.getString(TablaEdiModProfAula.CODEDI));
                datEMPA.setIdMod  (rs.getString(TablaEdiModProfAula.CODMOD));
                datEMPA.setIdProf (rs.getString(TablaEdiModProfAula.CODPROF));
                datEMPA.setIdAul  (rs.getString(TablaEdiModProfAula.CODAULA ));
                datEMPA.setFecIni (rs.getDate  (TablaEdiModProfAula.FECHAINICIO));
                datEMPA.setFecFin (rs.getDate  (TablaEdiModProfAula.FECHAFIN));
                datEMPA.setHorIni (rs.getString(TablaEdiModProfAula.HORAINICIO));
                datEMPA.setHorFin (rs.getString(TablaEdiModProfAula.HORAFIN));
                datEMPA.setHayLun(rs.getBoolean(TablaEdiModProfAula.CLASELUNES));
                datEMPA.setHayMar(rs.getBoolean(TablaEdiModProfAula.CLASEMARTES));
                datEMPA.setHayMie(rs.getBoolean(TablaEdiModProfAula.CLASEMIERC));
                datEMPA.setHayJue(rs.getBoolean(TablaEdiModProfAula.CLASEJUEVES));
                datEMPA.setHayVie(rs.getBoolean(TablaEdiModProfAula.CLASEVIERNES));
                datEMPA.setHaySab(rs.getBoolean(TablaEdiModProfAula.CLASESABADO));
                datEMPA.setHayLun(rs.getBoolean(TablaEdiModProfAula.CLASELUNES));
                datEMPA.setHayMar(rs.getBoolean(TablaEdiModProfAula.CLASEMARTES));
                datEMPA.setHayMie(rs.getBoolean(TablaEdiModProfAula.CLASEMIERC));
                datEMPA.setHayJue(rs.getBoolean(TablaEdiModProfAula.CLASEJUEVES));
                datEMPA.setHayVie(rs.getBoolean(TablaEdiModProfAula.CLASEVIERNES));
                datEMPA.setHaySab(rs.getBoolean(TablaEdiModProfAula.CLASESABADO));

                listaEMPA.add(datEMPA);
            }

            rs.close();
            ps.close();

            return listaEMPA;
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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los  módulos pendientes de un profesor
    public static Vector devolverModProfPend(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaEdiModProfAula.CODEDI       + " , "
                                          + TablaEdiModProfAula.CODMOD       + " , "
                                          + TablaEdiModProfAula.CODPROF      + " , "
                                          + TablaEdiModProfAula.CODAULA      + " , "
                                          + TablaEdiModProfAula.FECHAINICIO  + " , "
                                          + TablaEdiModProfAula.FECHAFIN     + " , "
                                          + TablaEdiModProfAula.HORAINICIO   + " , "
                                          + TablaEdiModProfAula.HORAFIN      + " , "
                                          + TablaEdiModProfAula.CLASELUNES   + " , "
                                          + TablaEdiModProfAula.CLASEMARTES  + " , "
                                          + TablaEdiModProfAula.CLASEMIERC   + " , "
                                          + TablaEdiModProfAula.CLASEJUEVES  + " , "
                                          + TablaEdiModProfAula.CLASEVIERNES + " , "
                                          + TablaEdiModProfAula.CLASESABADO  +
                                " FROM "  + TablaEdiModProfAula.TABLA        +
                                " WHERE " + TablaEdiModProfAula.CODPROF      + " =  ? AND "
                                          + TablaEdiModProfAula.FECHAFIN     + " >= ? " ;


        EdiModProfAulaVO datEMPA    = null;
        Vector           listaEMPA  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            ps.setString(1, codProf);
            ps.setDate  (2, new Date(System.currentTimeMillis()));
            rs  = ps.executeQuery();

            while(rs.next())
            {
                datEMPA = new EdiModProfAulaVO();

                datEMPA.setIdEdi  (rs.getString (TablaEdiModProfAula.CODEDI));
                datEMPA.setIdMod  (rs.getString (TablaEdiModProfAula.CODMOD));
                datEMPA.setIdProf (rs.getString (TablaEdiModProfAula.CODPROF));
                datEMPA.setIdAul  (rs.getString (TablaEdiModProfAula.CODAULA ));
                datEMPA.setFecIni (rs.getDate   (TablaEdiModProfAula.FECHAINICIO));
                datEMPA.setFecFin (rs.getDate   (TablaEdiModProfAula.FECHAFIN));
                datEMPA.setHorIni (rs.getString (TablaEdiModProfAula.HORAINICIO));
                datEMPA.setHorFin (rs.getString (TablaEdiModProfAula.HORAFIN));
                datEMPA.setHayLun (rs.getBoolean(TablaEdiModProfAula.CLASELUNES));
                datEMPA.setHayMar (rs.getBoolean(TablaEdiModProfAula.CLASEMARTES));
                datEMPA.setHayMie (rs.getBoolean(TablaEdiModProfAula.CLASEMIERC));
                datEMPA.setHayJue (rs.getBoolean(TablaEdiModProfAula.CLASEJUEVES));
                datEMPA.setHayVie (rs.getBoolean(TablaEdiModProfAula.CLASEVIERNES));
                datEMPA.setHaySab (rs.getBoolean(TablaEdiModProfAula.CLASESABADO));

                listaEMPA.add(datEMPA);
            }

            rs.close();
            ps.close();

            return listaEMPA;
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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarEdiMod(EdiModProfAulaVO empaVO,Connection con) throws Exception
    {
        PreparedStatement ps             = null;

        String            cadenaConsulta = "INSERT INTO " + TablaEdiModProfAula.TABLA        + " ( "
                                                          + TablaEdiModProfAula.CODEDI       + " , "
                                                          + TablaEdiModProfAula.CODMOD       + " , "
                                                          + TablaEdiModProfAula.CODPROF      + " , "
                                                          + TablaEdiModProfAula.CODAULA      + " , "
                                                          + TablaEdiModProfAula.FECHAINICIO  + " , "
                                                          + TablaEdiModProfAula.FECHAFIN     + " , "
                                                          + TablaEdiModProfAula.HORAINICIO   + " , "
                                                          + TablaEdiModProfAula.HORAFIN      + " , "
                                                          + TablaEdiModProfAula.CLASELUNES   + " , "
                                                          + TablaEdiModProfAula.CLASEMARTES  + " , "
                                                          + TablaEdiModProfAula.CLASEMIERC   + " , "
                                                          + TablaEdiModProfAula.CLASEJUEVES  + " , "
                                                          + TablaEdiModProfAula.CLASEVIERNES + " , "
                                                          + TablaEdiModProfAula.CLASESABADO  + " ) " +
                                           " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString ( 1, empaVO.getIdEdi());
            ps.setString ( 2, empaVO.getIdMod());
            ps.setString ( 3, empaVO.getIdProf());
            ps.setString ( 4, empaVO.getIdAul());
            ps.setDate   ( 5, new Date(empaVO.getFecIni().getTime()));
            ps.setDate   ( 6, new Date(empaVO.getFecFin().getTime()));
            ps.setString ( 7, empaVO.getHorIni());
            ps.setString ( 8, empaVO.getHorFin());
            ps.setBoolean( 9, empaVO.isHayLun());
            ps.setBoolean(10, empaVO.isHayMar());
            ps.setBoolean(11, empaVO.isHayMie());
            ps.setBoolean(12, empaVO.isHayJue());
            ps.setBoolean(13, empaVO.isHayVie());
            ps.setBoolean(14, empaVO.isHaySab());

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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

   //Método que borra un modulo de una edición
    public static int borrarEdiMod(String codMod, String  codEdi,Connection con) throws Exception
    {
       PreparedStatement   ps              = null;

        String             sql             = "DELETE FROM " + TablaEdiModProfAula.TABLA  +
                                             " WHERE "      + TablaEdiModProfAula.CODMOD + " = ? AND "
                                                            + TablaEdiModProfAula.CODEDI + " = ? ";

        int                regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la sentencia sql
            ps.setString(1, codMod);
            ps.setString(2, codEdi);

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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

     //Método que borra todos los módulos de una edición
    public static int borrarModulosEdi(String codEdi,Connection con) throws Exception
    {
        PreparedStatement   ps              = null;

        String              sql             = "DELETE FROM " + TablaEdiModProfAula.TABLA  +
                                              " WHERE "      + TablaEdiModProfAula.CODEDI + " = ? ";


        int                 regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la sentencia sql
            ps.setString(1, codEdi);

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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que edita un modulo de una edición
    public static int editarEdiModProfAula(EdiModProfAulaVO empaVO,Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = 0;

        String            sql             = "UPDATE " + TablaEdiModProfAula.TABLA        +
                                            " SET "   + TablaEdiModProfAula.CODPROF      + " = ? , "
                                                      + TablaEdiModProfAula.CODAULA      + " = ? , "
                                                      + TablaEdiModProfAula.FECHAINICIO  + " = ? , "
                                                      + TablaEdiModProfAula.FECHAFIN     + " = ? , "
                                                      + TablaEdiModProfAula.HORAINICIO   + " = ? , "
                                                      + TablaEdiModProfAula.HORAFIN      + " = ? , "
                                                      + TablaEdiModProfAula.CLASELUNES   + " = ? , "
                                                      + TablaEdiModProfAula.CLASEMARTES  + " = ? , "
                                                      + TablaEdiModProfAula.CLASEMIERC   + " = ? , "
                                                      + TablaEdiModProfAula.CLASEJUEVES  + " = ? , "
                                                      + TablaEdiModProfAula.CLASEVIERNES + " = ? , "
                                                      + TablaEdiModProfAula.CLASESABADO  + " = ?   "  +
                                            " WHERE(" + TablaEdiModProfAula.CODMOD       + " = ? AND "
                                                      + TablaEdiModProfAula.CODEDI       + " = ?)";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString ( 1, empaVO.getIdProf());
            ps.setString ( 2, empaVO.getIdAul());
            ps.setDate   ( 3, new Date(empaVO.getFecIni().getTime()));
            ps.setDate   ( 4, new Date(empaVO.getFecFin().getTime()));
            ps.setString ( 5, empaVO.getHorIni());
            ps.setString ( 6, empaVO.getHorFin());
            ps.setString ( 7, empaVO.getIdMod());
            ps.setString ( 8, empaVO.getIdEdi());
            ps.setBoolean( 9, empaVO.isHayLun());
            ps.setBoolean(10, empaVO.isHayMar());
            ps.setBoolean(11, empaVO.isHayMie());
            ps.setBoolean(12, empaVO.isHayJue());
            ps.setBoolean(13, empaVO.isHayVie());
            ps.setBoolean(14, empaVO.isHaySab());

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
                Logger.getLogger(EdiModProfAulaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

}
