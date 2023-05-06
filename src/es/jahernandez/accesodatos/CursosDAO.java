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

import es.jahernandez.datos.CentrosVO;
import es.jahernandez.datos.CursosAluVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.datos.ResultadoBusqAlu;
import es.jahernandez.gestion.AluEdiGestion;
import es.jahernandez.gestion.CentrosGestion;
import es.jahernandez.gestion.CurNivGestion;
import es.jahernandez.gestion.CursosAluGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.SeguimientosGestion;
//import es.jahernandez.datos.AlumnosEdiVO;
import es.jahernandez.tablas.TablaAlumnos;
import es.jahernandez.tablas.TablaAlumnosEdiciones;
import es.jahernandez.tablas.TablaCursos;
import es.jahernandez.tablas.TablaCursosAlumnos;
import es.jahernandez.tablas.TablaEdiciones;

/**
 *
 * @author Alberto
 */
public class CursosDAO
{

    //Método que devuelve los datos de un curso
    public static CursosVO devolverDatosCurso(String codCur, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            sql = "SELECT " + TablaCursos.CODCURSO  + ","
                                          + TablaCursos.NOMBRE    + ","
                                          + TablaCursos.TIPOCURSO + ","
                                          + TablaCursos.CODCENTRO + ","
                                          + TablaCursos.CONTENIDO +
                                " FROM  " + TablaCursos.TABLA     +
                                " WHERE " + TablaCursos.CODCURSO  + " = ?";


        CursosVO          datCur         = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codCur);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                datCur = new CursosVO();

                datCur.setIdCur    (rs.getString(TablaCursos.CODCURSO));
                datCur.setNomCur   (rs.getString(TablaCursos.NOMBRE));
                datCur.setTipCur   (rs.getInt   (TablaCursos.TIPOCURSO));
                datCur.setCenCurso (rs.getInt   (TablaCursos.CODCENTRO));
                datCur.setContenido(rs.getString(TablaCursos.CONTENIDO));
            }

            rs.close();
            ps.close();

            return datCur;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de curso de todos los cursos
    public static Vector devolverTodosCur(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            sql = "SELECT " + TablaCursos.CODCURSO  + ","
                                          + TablaCursos.NOMBRE    + ","
                                          + TablaCursos.TIPOCURSO + ","
                                          + TablaCursos.CODCENTRO + ","
                                          + TablaCursos.CONTENIDO +
                                " FROM  " + TablaCursos.TABLA;

        CursosVO          datCur         = null;
        Vector            listaCursos    = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datCur = new CursosVO();

                datCur.setIdCur    (rs.getString(TablaCursos.CODCURSO));
                datCur.setNomCur   (rs.getString(TablaCursos.NOMBRE));
                datCur.setTipCur   (rs.getInt   (TablaCursos.TIPOCURSO));
                datCur.setCenCurso (rs.getInt   (TablaCursos.CODCENTRO));
                datCur.setContenido(rs.getString(TablaCursos.CONTENIDO));

                listaCursos.add(datCur);
            }

            rs.close();
            ps.close();
            return listaCursos;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Devuelve el VALOR MAXIMO de codigo CURSO
    public static String devuelveMaxCur(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            sql = "SELECT MAX(" + TablaCursos.CODCURSO + ")" +
                                " FROM "      + TablaCursos.TABLA;

        String            valMaxCodCurso = "";

        try
        {
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                valMaxCodCurso = rs.getString(1);
            }

            rs.close();
            ps.close();

            return valMaxCodCurso;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de búsqueda de alumnos por curso
    public static Vector devolverDatosConsCurso2(String cadenaConsulta , Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        ResultSet         rs              = null;

        ResultadoBusqAlu  resBusAlu       = null;
        Vector            listaResBusAlu  = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                resBusAlu = new ResultadoBusqAlu();

                resBusAlu.setIdAlu(rs.getString(1));
                resBusAlu.setNomAlu(rs.getString(2));
                resBusAlu.setApe1Alu(rs.getString(3));
                resBusAlu.setApe2Alu(rs.getString(4));
                resBusAlu.setMovAlu(rs.getString(4));
                resBusAlu.setEmaAlu(rs.getString(6));
                resBusAlu.setFijAlu(rs.getString(11));
                resBusAlu.setInteres(rs.getString(12));

                listaResBusAlu.add(resBusAlu);
            }

            rs.close();
            ps.close();
            return listaResBusAlu;

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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }


    //Método que devuelve los datos de búsqueda de alumnos por tipo curso, ordenados por curso
    //no devuelve los datos de los alumnos ya matriculados
    public static Vector devolverDatosAluTipCur(int tipCurso, Connection con) throws Exception
    {
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        String            sql         = "SELECT "       + TablaAlumnos.TABLA       + "."    + TablaAlumnos.CODALU          + ","
                                                        + TablaCursosAlumnos.TABLA + "."    + TablaCursosAlumnos.CODCURSO  + ","
                                                        + TablaCursosAlumnos.TABLA + "."    + TablaCursosAlumnos.CODNIVEL  +
                                         " FROM "       + TablaAlumnos.TABLA       +
                                         " INNER JOIN " + TablaCursosAlumnos.TABLA + " ON "
                                                        + TablaAlumnos.TABLA       + "."    + TablaAlumnos.CODALU          + " = "
                                                        + TablaCursosAlumnos.TABLA + "."    + TablaCursosAlumnos.CODALUMNO +
                                         " INNER JOIN " + TablaCursos.TABLA        + " ON "
                                                        + TablaCursosAlumnos.TABLA + "."    + TablaCursosAlumnos.CODCURSO  + " = "
                                                        + TablaCursos.TABLA        + "."    + TablaCursos.CODCURSO         +
                                        " WHERE ( "     + TablaCursos.TABLA        + "."    + TablaCursos.TIPOCURSO        + " = ? ) " +
                                        " ORDER BY "    + TablaCursos.TABLA        + "."    + TablaCursos.CODCURSO;

        CursosAluVO       curAluVO    = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            ps.setInt(1, tipCurso);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                curAluVO = new CursosAluVO();

                curAluVO.setIdAlu(rs.getString(1));
                curAluVO.setIdCur(rs.getString(2));
                curAluVO.setIdNiv(rs.getString(3));

                //Añade el alumno sólo si el alumno no está matriculado en el curso
                if (!AluEdiGestion.estaAluMatCur(curAluVO.getIdCur(), curAluVO.getIdAlu()))
                {
                    listaCurAlu.add(curAluVO);
                }
            }

            rs.close();
            ps.close();
            return listaCurAlu;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }


    //Método que devuelve los datos de búsqueda de alumnos por curso, ordenados por niveles
    //no devuelve los datos de los alumnos ya matriculados
    //Si no se le pasa codigo de nivel, entonces muestra los alumnos para todos  los cursos y los niveles
    //y si no, sólo los cursos para el nivel especificado
    public static Vector devolverDatosAluCur(String codCurso, String codNivel, Connection con) throws Exception
    {
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        String            sql         = "SELECT "      + TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODALUMNO + " , "
                                                       + TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODCURSO  + " , "
                                                       + TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODNIVEL  + " , "
                                                       + TablaAlumnos.TABLA       + "." + TablaAlumnos.APE1            +
                                        " FROM "       + TablaAlumnos.TABLA       +
                                        " INNER JOIN " + TablaCursosAlumnos.TABLA + " ON "
                                                       + TablaAlumnos.TABLA       + "." + TablaAlumnos.CODALU          + " = "
                                                       + TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODALUMNO +
                                        " WHERE      " + TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODCURSO  + " = ? ";


        //Se completa la sql en función de que haya o no codigo de nivel
        if(codNivel != null && ! codNivel.trim().equals(""))
        {
            sql = sql + " AND " + TablaCursosAlumnos.CODNIVEL + " = ? ";
        }

        sql = sql + " ORDER BY " + TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODNIVEL + " , "
                                 + TablaAlumnos.TABLA       + "." + TablaAlumnos.APE1;

        CursosAluVO       curAluVO    = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros
            ps.setString(1, codCurso);

            if(codNivel != null && ! codNivel.trim().equals(""))
            {
                ps.setString(2, codNivel);
             }

            rs  = ps.executeQuery();

            while (rs.next())
            {
                curAluVO = new CursosAluVO();

                curAluVO.setIdAlu(rs.getString(TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODALUMNO));
                curAluVO.setIdCur(rs.getString(TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODCURSO));
                curAluVO.setIdNiv(rs.getString(TablaCursosAlumnos.TABLA + "." + TablaCursosAlumnos.CODNIVEL));

                //Añade el alumno sólo si el alumno no está matriculado en el curso
                if (!AluEdiGestion.estaAluMatCur(curAluVO.getIdCur(), curAluVO.getIdAlu()))
                {
                    listaCurAlu.add(curAluVO);
                }
            }

            rs.close();
            ps.close();

            return listaCurAlu;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Método que devuelve los datos de búsqueda de alumnos por curso (interesados,
    //no devuelve los datos de los alumnos ya matriculados
    public static Vector devolverDatosConsCurso(String cadenaConsulta, Connection con) throws Exception
    {
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        CursosAluVO       curAluVO    = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();


            while (rs.next())
            {
                curAluVO = new CursosAluVO();

                curAluVO.setIdAlu(rs.getString(1));
                curAluVO.setIdCur(rs.getString(2));
                curAluVO.setIdNiv(rs.getString(3));

                //Añade el alumno sólo si el alumno no está matriculado en el curso
                if (!AluEdiGestion.estaAluMatCur(curAluVO.getIdCur(), curAluVO.getIdAlu()))
                {
                    listaCurAlu.add(curAluVO);
                }
            }

            rs.close();
            ps.close();

            return listaCurAlu;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Método que guarda un nuevo registro en la base de datos
    //Devuelve el código de curso generado, si se inserta correctamente
    public static String guardarCurso(CursosVO curVO , Connection con) throws Exception
    {
        String nueCodCur = generarNuevoCodCur();

        PreparedStatement ps  = null;

        String            sql = "INSERT INTO " + TablaCursos.TABLA + "(" + TablaCursos.CODCURSO  + " , "
                                                                         + TablaCursos.NOMBRE    + " , "
                                                                         + TablaCursos.TIPOCURSO + " , "
                                                                         + TablaCursos.CODCENTRO + " , "
                                                                         + TablaCursos.CONTENIDO + " ) " +
                                "VALUES(?,?,?,?,?)";



        int regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, nueCodCur);
            ps.setString(2, curVO.getNomCur());
            ps.setInt   (3, curVO.getTipCur());
            ps.setInt   (4, curVO.getCenCurso());
            ps.setString(5, curVO.getContenido());

            regActualizados = ps.executeUpdate();

            ps.close();

            if (regActualizados > 0)
            {
                return nueCodCur;
            }
            else
            {
                return "-1";
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que genera un nuevo códgo de curso
    public static String generarNuevoCodCur()
    {
        boolean enc = true;
        int     avc = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datCur = CursosGestion.devolverTodosCur();

        while (enc)
        {
            contCar = ("" + datCur.size()).length();

            if (contCar > 0)
            {
               codIntrod = "" + (datCur.size() + avc);
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
            datCur = CursosGestion.devolverTodosCur();
            for (int ind = 0; ind < datCur.size(); ind++)
            {
                CursosVO curVO = (CursosVO) datCur.elementAt(ind);
                if (curVO.getIdCur().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Edita el registro de un curso
    public static int editaCurso(CursosVO curVO, Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "UPDATE " + TablaCursos.TABLA     +
                                " SET "   + TablaCursos.NOMBRE    + " = ? , "
                                          + TablaCursos.TIPOCURSO + " = ? , "
                                          + TablaCursos.CODCENTRO + " = ? , "
                                          + TablaCursos.CONTENIDO + " = ?   " +
                                " WHERE " + TablaCursos.CODCURSO  + " = ?";

        int               regActualizados = 0;

        try
        {
           ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, curVO.getNomCur());
            ps.setInt   (2, curVO.getTipCur());
            ps.setInt   (3, curVO.getCenCurso());
            ps.setString(4, curVO.getContenido());
            ps.setString(5, curVO.getIdCur());

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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que elimina los datos de un curso
    public static int eliminarDatosCurso(String codCur, Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "DELETE FROM " + TablaCursos.TABLA    +
                                " WHERE  "     + TablaCursos.CODCURSO + " = ?";

        int               regActualizados = 0;

        boolean           hayEdiCur       = false;

        //Se comprueba que no haya ediciones del curso
        hayEdiCur = EdicionesGestion.devolverHayEdiCur(codCur);

        if(hayEdiCur)
        {
            return  -2; //No se puede borrar el curso porque tiene ediciones
        }


        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codCur);

            regActualizados = ps.executeUpdate();

            ps.close();

            //Se borran los datos del curso-interesado, seguimientos y los niveles del curso
            regActualizados = regActualizados + CursosAluGestion.borrarTodAluCur(codCur);
            regActualizados = regActualizados + CurNivGestion.borrarTodNivCur(codCur);
            regActualizados = regActualizados + SeguimientosGestion.eliminaSegCur(codCur);

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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve si hay cursos que pertenezcan a un determinado tipo
    public static boolean  hayCursosTipo(int tipCur, Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaCursos.CODCURSO  +
                                " FROM "  + TablaCursos.TABLA     +
                                " WHERE " + TablaCursos.TIPOCURSO + " = ?";

        boolean           hayCursosTip   = false;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setInt(1, tipCur);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                hayCursosTip = true;
            }
            else
            {
                hayCursosTip = false;
            }

            rs.close();
            ps.close();
            return hayCursosTip;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de búsqueda de alumnos por curso (interesados,
    //no devuelve los datos de los alumnos ya matriculados
    public static Vector devolverDatosHistMat(Connection con) throws Exception
    {
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        Vector            listaCurAlu = new Vector();

        CursosAluVO       datResBus   = null;

        String            sql        = "SELECT " + TablaAlumnos.TABLA + "." + TablaAlumnos.CODALU  + " , "
                                                 + TablaCursos.TABLA  + "." + TablaCursos.CODCURSO + " , "
                                                 + TablaAlumnos.TABLA + "." + TablaAlumnos.APE1    +
                                        " FROM " + TablaAlumnosEdiciones.TABLA + " INNER JOIN "
                                                 + TablaAlumnos.TABLA   + " ON " + TablaAlumnosEdiciones.TABLA + "." + TablaAlumnosEdiciones.CODALU + " = " + TablaAlumnos.TABLA   + "." + TablaAlumnos.CODALU   + " INNER JOIN "
                                                 + TablaEdiciones.TABLA + " ON " + TablaAlumnosEdiciones.TABLA + "." + TablaAlumnosEdiciones.CODEDI + " = " + TablaEdiciones.TABLA + "." + TablaEdiciones.CODEDI + " INNER JOIN "
                                                 + TablaCursos.TABLA    + " ON " + TablaEdiciones.TABLA        + "." + TablaEdiciones.CODCURSO      + " = " + TablaCursos.TABLA    + "." + TablaCursos.CODCURSO  +
                                        " ORDER BY " + TablaCursos.TABLA + "." + TablaCursos.CODCURSO ;

        try
        {
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datResBus = new CursosAluVO();

                datResBus.setIdAlu(rs.getString(1));
                datResBus.setIdCur(rs.getString(2));
                datResBus.setIdNiv(rs.getString(3));

                listaCurAlu.add(datResBus);

            }
            rs.close();
            ps.close();
            return listaCurAlu;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de los cursos de un determinado tipo
    public static Vector devolverDatCurTip(int codTip,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaCursos.CODCURSO  + ","
                                          + TablaCursos.NOMBRE    + ","
                                          + TablaCursos.TIPOCURSO + ","
                                          + TablaCursos.CODCENTRO + ","
                                          + TablaCursos.CONTENIDO +
                                " FROM  " + TablaCursos.TABLA     +
                                " WHERE " + TablaCursos.TIPOCURSO + " = ?";

        Vector            listaCursos    = new Vector();

        CursosVO          datCur         = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setInt(1, codTip);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                datCur = new CursosVO();

                datCur.setIdCur    (rs.getString(TablaCursos.CODCURSO));
                datCur.setNomCur   (rs.getString(TablaCursos.NOMBRE));
                datCur.setTipCur   (rs.getInt   (TablaCursos.TIPOCURSO));
                datCur.setCenCurso (rs.getInt   (TablaCursos.CODCENTRO));
                datCur.setContenido(rs.getString(TablaCursos.CONTENIDO));

                listaCursos.add(datCur);
            }

            rs.close();
            ps.close();
            return listaCursos;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de los cursos de un determinado tipo con nombre de longitud limitada para los combos
    public static Vector devolverDatCurTipCombo(int codTip,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaCursos.CODCURSO  + ","
                                          + TablaCursos.NOMBRE    + ","
                                          + TablaCursos.TIPOCURSO + ","
                                          + TablaCursos.CODCENTRO + ","
                                          + TablaCursos.CONTENIDO +
                                " FROM  " + TablaCursos.TABLA     +
                                " WHERE " + TablaCursos.TIPOCURSO + " = ?";

        Vector            listaCursos    = new Vector();

        CursosVO          datCur         = null;

        try
        {
           ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setInt(1, codTip);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                datCur = new CursosVO();

                datCur.setIdCur    (rs.getString(TablaCursos.CODCURSO));
                datCur.setNomCur   (rs.getString(TablaCursos.NOMBRE));
                datCur.setTipCur   (rs.getInt   (TablaCursos.TIPOCURSO));
                datCur.setCenCurso (rs.getInt   (TablaCursos.CODCENTRO));
                datCur.setContenido(rs.getString(TablaCursos.CONTENIDO));

                //Se limita el nombre a 50 caracteres
                if(datCur.getNomCur().length()>50) datCur.setNomCur(datCur.getNomCur().substring(0,50));


                listaCursos.add(datCur);
            }

            rs.close();
            ps.close();
            return listaCursos;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que devuelve los datos de los cursos de un determinado tipo y centro
    public static Vector devolverDatCurTipCen(int codTip, int codCentro,Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT " + TablaCursos.CODCURSO  + ","
                                          + TablaCursos.NOMBRE    + ","
                                          + TablaCursos.TIPOCURSO + ","
                                          + TablaCursos.CODCENTRO + ","
                                          + TablaCursos.CONTENIDO +
                                " FROM  " + TablaCursos.TABLA     +
                                " WHERE " + TablaCursos.TIPOCURSO + " = ? AND "
                                          + TablaCursos.CODCENTRO + " = ? ";

        Vector            listaCursos    = new Vector();

        CursosVO          datCur         = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setInt(1, codTip);
            ps.setInt(2, codCentro);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                datCur = new CursosVO();

                datCur.setIdCur    (rs.getString(TablaCursos.CODCURSO));
                datCur.setNomCur   (rs.getString(TablaCursos.NOMBRE));
                datCur.setTipCur   (rs.getInt   (TablaCursos.TIPOCURSO));
                datCur.setCenCurso (rs.getInt   (TablaCursos.CODCENTRO));
                datCur.setContenido(rs.getString(TablaCursos.CONTENIDO));

                listaCursos.add(datCur);
            }

            rs.close();
            ps.close();
            return listaCursos;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

     //Método que devuelve los datos de curso de todos los cursos
    public static Vector devolverTodosCurAlfab(Connection con) throws Exception
    {
        PreparedStatement ps  = null;
        ResultSet         rs  = null;

        String            sql = "SELECT "    + TablaCursos.CODCURSO  + ","
                                             + TablaCursos.NOMBRE    + ","
                                             + TablaCursos.TIPOCURSO + ","
                                             + TablaCursos.CODCENTRO + ","
                                             + TablaCursos.CONTENIDO +
                                " FROM  "    + TablaCursos.TABLA     +
                                " ORDER BY " + TablaCursos.NOMBRE;
        CursosVO          datCur         = null;
        Vector            listaCursos    = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datCur = new CursosVO();

                datCur.setIdCur    (rs.getString(TablaCursos.CODCURSO));
                datCur.setNomCur   (rs.getString(TablaCursos.NOMBRE));
                datCur.setTipCur   (rs.getInt   (TablaCursos.TIPOCURSO));
                datCur.setCenCurso (rs.getInt   (TablaCursos.CODCENTRO));
                datCur.setContenido(rs.getString(TablaCursos.CONTENIDO));

                listaCursos.add(datCur);
            }

            rs.close();
            ps.close();
            return listaCursos;
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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

     //Método que devuelve el centro de un curso dado
    public static Vector datCentrosCurso(String codCurso, Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String            sql        = "SELECT " + TablaCursos.CODCENTRO +
                                       " FROM  " + TablaCursos.TABLA     +
                                       " WHERE " + TablaCursos.CODCURSO  + " = ? ";
        CentrosVO         cenVO      = null;
        Vector            datCen     = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);
            ps.setString(1, codCurso);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                cenVO = CentrosGestion.datCentro(rs.getInt(TablaCursos.CODCENTRO));

                datCen.addElement(cenVO);
            }

            rs.close();
            ps.close();

            return datCen;

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
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
}
