/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.accesodatos.CursosDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.CursosVO;

/**
 *
 * @author Alberto
 */
public class CursosGestion
{
    //Método que devuelve los datos de un curso
    public static CursosVO devolverDatosCurso(String codCur)
    {
        Connection        con            = null;
        CursosVO          datCur         = null;

        try
        {
            con = Conexion.conectar();
            datCur = CursosDAO.devolverDatosCurso(codCur,con);
            Conexion.desconectar(con);

            return datCur;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de curso de todos los cursos
    public static Vector devolverTodosCur()
    {
        Connection        con            = null;
        Vector            listaCursos    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCursos = CursosDAO.devolverTodosCur(con);
            Conexion.desconectar(con);
            return listaCursos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Devuelve el VALOR MAXIMO de codigo CURSO
    public static String devuelveMaxCur()
    {
        Connection        con            = null;
        String            valMaxCodCurso = "";

        try
        {
            con = Conexion.conectar();
            valMaxCodCurso = CursosDAO.devuelveMaxCur(con);
            Conexion.desconectar(con);

            return valMaxCodCurso;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de búsqueda de alumnos por curso
    public static Vector devolverDatosConsCurso2(String cadenaConsulta)
    {
        Connection        con             = null;
        Vector            listaResBusAlu  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaResBusAlu = CursosDAO.devolverDatosConsCurso2(cadenaConsulta,con);
            Conexion.desconectar(con);

            return listaResBusAlu;
        }
       catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }


    //Método que devuelve los datos de búsqueda de alumnos por tipo curso, ordenados por curso
    //no devuelve los datos de los alumnos ya matriculados
    public static Vector devolverDatosAluTipCur(int tipCurso)
    {
        Connection        con         = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCurAlu = CursosDAO.devolverDatosAluTipCur(tipCurso,con);
            Conexion.desconectar(con);
            return listaCurAlu;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }


    //Método que devuelve los datos de búsqueda de alumnos por curso, ordenados por niveles
    //no devuelve los datos de los alumnos ya matriculados
    //Si no se le pasa codigo de nivel, entonces muestra los alumnos para todos  los cursos y los niveles
    //y si no, sólo los cursos para el nivel especificado
    public static Vector devolverDatosAluCur(String codCurso, String codNivel)
    {
        Connection        con         = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCurAlu = CursosDAO.devolverDatosAluCur(codCurso, codNivel, con);

            Conexion.desconectar(con);
            return listaCurAlu;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve los datos de búsqueda de alumnos por curso (interesados,
    //no devuelve los datos de los alumnos ya matriculados
    public static Vector devolverDatosConsCurso(String cadenaConsulta)
    {
        Connection        con         = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCurAlu = CursosDAO.devolverDatosConsCurso(cadenaConsulta,con);
            Conexion.desconectar(con);
            return listaCurAlu;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que guarda un nuevo registro en la base de datos
    //Devuelve el código de curso generado, si se inserta correctamente
    public static String guardarCurso(CursosVO curVO)
    {
        Connection con       = null;
        String     nueCodCur = null;

        try
        {
            con = Conexion.conectar();
            nueCodCur = CursosDAO.guardarCurso(curVO, con);
            Conexion.desconectar(con);

            return nueCodCur;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "-2";
        }
    }

    //Método que genera un nuevo códgo de curso
    public static String generarNuevoCodCur()
    {
       return CursosDAO.generarNuevoCodCur();
    }

    //Edita el registro de un curso
    public static int editaCurso(CursosVO curVO)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosDAO.editaCurso(curVO, con);
            Conexion.desconectar(con);

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que elimina los datos de un curso
    public static int eliminarDatosCurso(String codCur)
    {
        Connection        con = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosDAO.eliminarDatosCurso(codCur, con);
            Conexion.desconectar(con);

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve si hay cursos que pertenezcan a un determinado tipo
    public static boolean  hayCursosTipo(int tipCur)
    {
        Connection        con          = null;
        boolean           hayCursosTip = false;

        try
        {
            con = Conexion.conectar();
            hayCursosTip = CursosDAO.hayCursosTipo(tipCur,con);
            Conexion.desconectar(con);

            return hayCursosTip;
        }
        catch (Exception exc)
        {
            try
            {
              Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }

    //Método que devuelve los datos de búsqueda de alumnos por curso (interesados,
    //no devuelve los datos de los alumnos ya matriculados
    public static Vector devolverDatosHistMat()
    {
        Connection        con         = null;
        Vector            listaCurAlu = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCurAlu = CursosDAO.devolverDatosHistMat(con);
            Conexion.desconectar(con);
            return listaCurAlu;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de los cursos de un determinado tipo
    public static Vector devolverDatCurTip(int codTip)
    {
        Connection        con         = null;
        Vector            listaCursos = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCursos = CursosDAO.devolverDatCurTip(codTip,con);
            Conexion.desconectar(con);
            return listaCursos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de los cursos de un determinado tipo con nombre de longitud limitada para los combos
    public static Vector devolverDatCurTipCombo(int codTip)
    {
        Connection        con = null;
        Vector            listaCursos    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCursos = CursosDAO.devolverDatCurTipCombo(codTip,con);
            Conexion.desconectar(con);

            return listaCursos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de los cursos de un determinado tipo y centro
    public static Vector devolverDatCurTipCen(int codTip, int codCentro)
    {
        Connection        con = null;
        Vector            listaCursos    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCursos = CursosDAO.devolverDatCurTipCen(codTip, codCentro, con);
            Conexion.desconectar(con);

            return listaCursos;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

     //Método que devuelve los datos de curso de todos los cursos
    public static Vector devolverTodosCurAlfab()
    {
        Connection        con = null;
        Vector            listaCursos    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCursos = CursosDAO.devolverTodosCurAlfab(con);
            Conexion.desconectar(con);

            return listaCursos;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

     //Método que devuelve el centro de un curso dado
    public static Vector datCentrosCurso(String codCurso)
    {

        Connection        con        = null;
        Vector            datCen     = new Vector();

        try
        {
            con = Conexion.conectar();
            datCen = CursosDAO.datCentrosCurso(codCurso , con);
            Conexion.desconectar(con);

            return datCen;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
}
