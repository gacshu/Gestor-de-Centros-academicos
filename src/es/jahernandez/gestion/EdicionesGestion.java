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

import es.jahernandez.accesodatos.EdicionesDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.EdicionesVO;

/**
 *
 * @author Alberto
 */
public class EdicionesGestion
{
    //Método que genera un nuevo código de Edición
    public static String generarNuevoCodEdi()
    {
        return EdicionesDAO.generarNuevoCodEdi();
    }

    //Método que devuelve los datos de edición de todas las ediciones
    public static Vector devolverTodosEdi()
    {
        Connection con      = null;
        Vector     listaEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEdi = EdicionesDAO.devolverTodosEdi(con);
            Conexion.desconectar(con);

            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que guarda un nuevo registro en la base de datos
    //Devuelve el código de edición generado, si se inserta correctamente
    public static String guardarEdicion(EdicionesVO ediVO)
    {
        Connection        con             = null;
        String            nueCodEdi       = generarNuevoCodEdi();

        try
        {
            con = Conexion.conectar();
            nueCodEdi = EdicionesDAO.guardarEdicion(ediVO, con);
            return nueCodEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve los datos de edición de una edición
    public static EdicionesVO devolverDatosEdi(String codEdi)
    {
        Connection  con      = null;
        EdicionesVO datEdi   = null;

        try
        {
            con = Conexion.conectar();
            datEdi = EdicionesDAO.devolverDatosEdi(codEdi, con);
            Conexion.desconectar(con);

            return datEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

    }

    //Método que comprueba si existen plazas libres para una edición
    public static boolean hayPlazasLibres(String codEdicion)
    {
        Connection con             = null;
        boolean    hayPlazaslibres = false;

        try
        {
            con = Conexion.conectar();
            hayPlazaslibres = EdicionesDAO.hayPlazasLibres(codEdicion,con);
            Conexion.desconectar(con);

            return hayPlazaslibres;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }
    }

    //Método que devuelve los datos de edición de las ediciones de un curso determinado
    public static Vector devolverDatEdiCur(String idCurso)
    {
        Connection          con      = null;
        Vector              listaEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEdi = EdicionesDAO.devolverDatEdiCur(idCurso,con);
            Conexion.desconectar(con);

            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

    }

    //Método que devuelve los datos de edición de las ediciones disponibles de un curso determinado
    public static Vector devolverDatEdiCurDisp(String idCurso)
    {
        Connection          con      = null;
        Vector              listaEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEdi = EdicionesDAO.devolverDatEdiCurDisp(idCurso,con);
            Conexion.desconectar(con);

            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

    }

    //Edita el registro de una edición
    public static int editaEdicion(EdicionesVO ediVO)
    {
        Connection          con             = null;
        int                 regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = EdicionesDAO.editaEdicion(ediVO, con);
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
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve si un curso tiene ediciones
    public static boolean devolverHayEdiCur(String idCur)
    {
        Connection          con       = null;
        boolean             hayEdiCur = false;

        try
        {
            con = Conexion.conectar();
            hayEdiCur = EdicionesDAO.devolverHayEdiCur(idCur, con);
            Conexion.desconectar(con);

            return hayEdiCur;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }
    }

    //Método que elimina los datos de una edicion
    public static int eliminarDatosEdi(String codEdi)
    {
        Connection          con    = null;
        int                 devRes = 0;

        try
        {
            con = Conexion.conectar();
            devRes = EdicionesDAO.eliminarDatosEdi(codEdi, con);
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
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los datos de edición de todas las ediciones que empiezan a partir de hoy
    public static Vector devolverResNueEdi()
    {
        Connection        con         = null;
        Vector            listaEdi    = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEdi = EdicionesDAO.devolverResNueEdi(con);
            Conexion.desconectar(con);

            return listaEdi;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve si hay niveles asociados a una edición
    public static boolean estaNivelenEdicion(String codNivel)
    {
        Connection          con       = null;
        boolean             hayNivEdi = false;

        try
        {
            con = Conexion.conectar();
            hayNivEdi  = EdicionesDAO.estaNivelenEdicion(codNivel, con);
            Conexion.desconectar(con);

            return hayNivEdi;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }

    }

    //Método que devuelve los datos de edición de todas las ediciones de un determinado tutor
    public static Vector devolverEdiTutor(String codProf)
    {
        Connection        con      = null;
        Vector            listaEdi = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEdi = EdicionesDAO.devolverEdiTutor(codProf,con);
            Conexion.desconectar(con);

            return listaEdi;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdicionesGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
}
