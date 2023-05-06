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

import es.jahernandez.accesodatos.ProfesoresDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.DatosBusqProfVO;
import es.jahernandez.datos.ProfesoresVO;

/**
 *
 * @author Alberto
 */
public class ProfesoresGestion
{
    //Método que devuelve los datos de búsqueda de profesores
    public static Vector devolverDatosConsultaProf(DatosBusqProfVO datBus)
    {
        Connection        con            = null;
        Vector            listaProf      = new Vector();

        try
        {
            con = Conexion.conectar();
            listaProf = ProfesoresDAO.devolverDatosConsultaProf(datBus,con);
            Conexion.desconectar(con);

            return listaProf;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

    }

    //Método que devuelve los datos de un profesor
    public static ProfesoresVO devolverDatosProfesor(String codProf)
    {
        Connection        con          = null;
        ProfesoresVO      datProf      = null;

        try
        {
            con = Conexion.conectar();
            datProf = ProfesoresDAO.devolverDatosProfesor(codProf,con);
            Conexion.desconectar(con);

            return datProf;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los datos de todos los profesores
    public static Vector devolverTodosProf()
    {
        Connection        con          = null;
        Vector            listaProf    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaProf = ProfesoresDAO.devolverTodosProf(con);
            Conexion.desconectar(con);

            return listaProf;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Edita el registro de un profesor
    public static int editaProfesor(ProfesoresVO profVO)
    {

        Connection          con      = null;
        int                 regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = ProfesoresDAO.editaProfesor(profVO,con);
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
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -2;
        }
    }

    //Borra el registro de un profesor
    //**Retocar
    public static int borraRegProf(String codProf)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = ProfesoresDAO.borraRegProf(codProf,con);
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
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Da de alta un nuevo profesor
    public static String insertaProfesor(ProfesoresVO profVO)
    {
        Connection con        = null;
        String     codNueProf = null;

        try
        {
            con = Conexion.conectar();
            codNueProf = ProfesoresDAO.insertaProfesor(profVO,con);
            Conexion.desconectar(con);

            return codNueProf;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "-2";
        }
    }

    //Genera un nuevo código de Profesor
    public static String generarNuevoCodProf()
    {
        return ProfesoresDAO.generarNuevoCodProf();
    }

    //Devuelve la provincia del profesor
    public static String devolverNombreProv(String codProv)
    {
        Connection        con             = null;
        String            nombreProvincia = "";

        try
        {
            con = Conexion.conectar();
            nombreProvincia = ProfesoresDAO.devolverNombreProv(codProv,con);
            Conexion.desconectar(con);

            return nombreProvincia;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve si ya existe el dato identificativo de un alumno
    public static boolean existeDniProfesor(String numDoc)
    {
        Connection       con          = null;
        boolean          existeDNI     = false;

        try
        {
            con = Conexion.conectar();
            existeDNI = ProfesoresDAO.existeDniProfesor(numDoc,con);
            Conexion.desconectar(con);

            return existeDNI;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }
}
