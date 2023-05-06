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

import es.jahernandez.accesodatos.ConUsoDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author JuanAlberto
 */
public class ConUsoGestion
{
    //Método que devuelve los datos de control de un usuario activo
    public static ConUsuVO buscarUsuario(String user, String password)
    {
        Connection        con        = null;
        ConUsuVO          conUsoVO   = null;

        try
        {
            con = Conexion.conectar();

            conUsoVO = ConUsoDAO.buscarUsuario(user, password,con);

            Conexion.desconectar(con);

            return conUsoVO;

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

    //Método que devuelve los datos de control de todos los usuarios que no son administradores
    public static Vector devTodUsuarios()
    {

        Connection        con        = null;
        Vector            listUsers  = new Vector();

        try
        {
            con = Conexion.conectar();

            listUsers = ConUsoDAO.devTodUsuarios(con);

            Conexion.desconectar(con);

            return listUsers;

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

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarUser(ConUsuVO conUsVO, String usuario)
    {
        Connection        con        = null;
        int               resulSql   = 0;

        try
        {
           con = Conexion.conectar();

           resulSql = ConUsoDAO.guardarUser(conUsVO, usuario,con);

           Conexion.desconectar(con);

           return resulSql;

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

    //Método que elimina un registro en la base de datos
    public static int borrarUser(String login, String usuario)
    {
        Connection con        = null;
        int        resulSql   = 0;

        try
        {
            con = Conexion.conectar();

            resulSql = ConUsoDAO.borrarUser(login, usuario,con);

            Conexion.desconectar(con);


            return resulSql;

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

    //Método que devuelve los datos de un usuario
    public static ConUsuVO devDatUsuario(String user, String usuario)
    {
        Connection        con        = null;
        ConUsuVO          conUsoVO   = null;

        try
        {
            con = Conexion.conectar();

            conUsoVO = ConUsoDAO.devDatUsuario(user, usuario,con);

            Conexion.desconectar(con);

            return conUsoVO;

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

    //Método que edita los datos de un usuario
    public static int editaUser(ConUsuVO conVO, String usuario)
    {
        Connection        con        = null;
        int               resulSql   = 0;

        try
        {
            con = Conexion.conectar();

            resulSql = ConUsoDAO.editaUser(conVO, usuario,con);

            Conexion.desconectar(con);

            return resulSql;
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

}
