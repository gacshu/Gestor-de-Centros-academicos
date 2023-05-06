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

import es.jahernandez.accesodatos.CursosAluDAO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author Alberto
 */
public class CursosAluGestion
{
     //Método que guarda los cursos de interes de alumnos y su nivel
    public static int guardarCursosInteres(String codAlumno, String codCurso, String codNiv)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosAluDAO.guardarCursosInteres(codAlumno, codCurso, codNiv,con);
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
                Logger.getLogger(ConUsoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los cursos que tiene asociados un determinado alumno
    public static Vector devolverCursosAlu(String codAlu)
    {
        Connection        con            = null;
        Vector            listaCursosAlu = new Vector();

        try
        {
            con = Conexion.conectar();
            listaCursosAlu = CursosAluDAO.devolverCursosAlu(codAlu, con);
            Conexion.desconectar(con);

            return listaCursosAlu;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ConUsoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que elimina un curso de un alumno
    public static int eliminarCurAlu(String codAlumno, String codCurso,String codNiv)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosAluDAO.eliminarCurAlu(codAlumno, codCurso, codNiv, con);
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
                Logger.getLogger(CursosAluGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return 0;
        }

    }

    //Borra los datos de curso de un alumno
    public static int borraCurAlu(String codAlu)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosAluDAO.borraCurAlu(codAlu,con);
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
                Logger.getLogger(CursosAluGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Borra todos los datos de un curso
    public static int borrarTodAluCur(String codCur)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosAluDAO.borrarTodAluCur(codCur, con);
            Conexion.conectar();

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
                Logger.getLogger(CursosAluGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Edita el nivel de los alumnos, cuando un nivel se elimina de un curso
    public static int ediNivCurAlu(String codCur)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosAluDAO.ediNivCurAlu(codCur, con);
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
                Logger.getLogger(CursosAluGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Método que comprueba si ya se ha asociado un curso-nivel a  un alumno
    public static boolean compruebaAltaCurNivAlu(String codAlumno, String codCurso, String codNiv)
    {
        Connection        con            = null;
        boolean           exiCurAlu      = false;

        try
        {
            con = Conexion.conectar();
            exiCurAlu = CursosAluDAO.compruebaAltaCurNivAlu(codAlumno, codCurso, codNiv,con);

            Conexion.desconectar(con);

            return exiCurAlu;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CursosAluGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }

    }

    //Método que guarda los cursos de interes de alumnos
    public static int guardarCursosInteres(String codAlumno, String codCurso)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = CursosAluDAO.guardarCursosInteres(codAlumno, codCurso, con);
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
                Logger.getLogger(CursosAluGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return 0;
        }
    }
}
