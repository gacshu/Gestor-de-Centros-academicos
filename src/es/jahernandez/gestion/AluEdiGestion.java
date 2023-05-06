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

import es.jahernandez.accesodatos.AluEdiDAO;
import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.EdicionesVO;


/**
 *
 * @author JuanAlberto
 */
public class AluEdiGestion
{
    //Método que guarda un nuevo registro en la base de datos
    public static int guardarMatAlu(AluEdiVO aluEdiVO)
    {
        Connection          con = null;
        int                 regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AluEdiDAO.guardarMatAlu(aluEdiVO , con);
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
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de alumno-edición de un alumno dado
    public static Vector devAluEdi(String codAlu)
    {
        Connection        con         = null;
        Vector            listaAluEdi = new Vector();

        try
        {
            con = Conexion.conectar();

            listaAluEdi = AluEdiDAO.devAluEdi(codAlu, con);

            Conexion.desconectar(con);

            return listaAluEdi;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los datos de alumno-edición de una edicion y alumno dados
    public static AluEdiVO devDatAluEdi(String codAlu, String codEdi)
    {
        Connection        con         = null;
        AluEdiVO          datAluEdi   = null;

        try
        {
            con = Conexion.conectar();
            datAluEdi = AluEdiDAO.devDatAluEdi(codAlu, codEdi, con);
            Conexion.desconectar(con);

            return datAluEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que da de baja/alta congela/descongela a un alumno en una edición
    public static int bajaMatAlu(AluEdiVO aluEdiVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AluEdiDAO.bajaMatAlu(aluEdiVO , con);
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
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve si un alumno esta matriculado en una edición de un determinado curso
    public static boolean estaAluMatCur(String codCur, String codAlu)
    {
        Connection       con         = null;
        boolean          respuesta   = false;

        try
        {
            con = Conexion.conectar();
            respuesta = AluEdiDAO.estaAluMatCur(codCur, codAlu, con);
            Conexion.desconectar(con);

            return respuesta;

        }

        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }

    }

    //Método que devuelve los datos de alumno-edición de los alumnos que tienen que pagar recibo
    public static Vector devRecAluEdi()
    {
        Connection       con            = null;
        Vector           listaAluEdi    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAluEdi = AluEdiDAO.devRecAluEdi(con);
            Conexion.desconectar(con);

            return listaAluEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los alumnos inscritos en una edición
    public static Vector devAluMatEdi(String codEdi)
    {
        Connection        con         = null;
        Vector            listaAluEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAluEdi = AluEdiDAO.devAluMatEdi(codEdi, con);
            Conexion.desconectar(con);

            return listaAluEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve el número de matriculas que ha tenido un alumno
    public static int devNumMat(String codAlu)
    {
        Connection        con              = null;
        int               numeroMatriculas = 0;

        try
        {
            con = Conexion.conectar();
            numeroMatriculas = AluEdiDAO.devNumMat(codAlu, con);
            Conexion.desconectar(con);

            return numeroMatriculas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }


    }

    //Método que devuelve los datos de alumno-edición de los alumnos que tienen recibo pendiente
    public static Vector devRecPendAluEdi()
    {
        Connection        con         = null;
        Vector            listaAluEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAluEdi = AluEdiDAO.devRecPendAluEdi(con);
            Conexion.desconectar(con);

            return listaAluEdi;
        }
       catch (Exception exc)
       {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los alumnos inscritos en un grupo
    public static Vector devAluGruMatEdi(EdicionesVO ediVO)
    {
        Connection        con         = null;
        Vector            listaAluEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAluEdi = AluEdiDAO.devAluGruMatEdi(ediVO, con);
            Conexion.desconectar(con);

            return listaAluEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que elimina los datos de alumno de una edición
    public static int eliminarDatosMatEdi(String codEdi)
    {
        Connection        con    = null;
        int               devRes = 0;

        try
        {
            con = Conexion.conectar();
            devRes = AluEdiDAO.eliminarDatosMatEdi(codEdi, con);
            Conexion.desconectar(con);

            return devRes;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve el número de alumnos inscritos en una edición
    public static int devNumAluEdi(String codEdi)
    {
        Connection        con        = null;
        int               numAlumnos = 0;

        try
        {
            con = Conexion.conectar();
            numAlumnos = AluEdiDAO.devNumAluEdi(codEdi, con);
            Conexion.desconectar(con);

            return numAlumnos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }


    }

    //Método que devuelve los alumnos inscritos en una edición dados de baja
    public static Vector devAluMatBajEdi(String codEdi)
    {
        Connection        con         = null;
        Vector           listaAluEdi  = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAluEdi = AluEdiDAO.devAluMatBajEdi(codEdi, con);
            Conexion.desconectar(con);

            return listaAluEdi;
        }
       catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los alumnos de baja en un grupo
    public static Vector devAluBajGruMatEdi(EdicionesVO ediVO)
    {
        Connection        con         = null;
        Vector            listaAluEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAluEdi = AluEdiDAO.devAluBajGruMatEdi(ediVO, con);
            Conexion.desconectar(con);

            return listaAluEdi;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
     }

    //Método que devuelve si una edición tiene alumunos
    public static boolean edicionTieneAlumnos(String codEdi)
    {
        Connection        con         = null;
        boolean           hayAlumnos  = false;

        try
        {
            con = Conexion.conectar();
            hayAlumnos = AluEdiDAO.edicionTieneAlumnos(codEdi , con);
            Conexion.desconectar(con);

            return hayAlumnos;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }
    }
}
