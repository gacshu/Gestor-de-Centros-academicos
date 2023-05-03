/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ConUsuVO;

import es.jahernandez.tablas.TablaControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Alberto
 */
public class ConUsoDAO
{
    //Método que devuelve los datos de control de un usuario activo
    public static ConUsuVO buscarUsuario(String user, String password, Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String            sql        = "SELECT " + TablaControl.USUARIO   + "," 
                                                 + TablaControl.PASSWORD  + ","
                                                 + TablaControl.ACCESO    + ","
                                                 + TablaControl.NOMBRE    + ","
                                                 + TablaControl.CODCENTRO + ","
                                                 + TablaControl.ACTIVO    + ","
                                                 + TablaControl.CODPROF   +                                                                                                             
                                       " FROM "  + TablaControl.TABLA     +    
                                       " WHERE " + TablaControl.USUARIO   + " = ? AND "
                                                 + TablaControl.PASSWORD  + " = ? AND "
                                                 + TablaControl.ACTIVO    + " = 1";

        ConUsuVO          conUsoVO   = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, user);
            ps.setString(2, DigestUtils.md5Hex(password));

            rs = ps.executeQuery();

            if (rs.next())
            {
                conUsoVO = new ConUsuVO();
                conUsoVO.setUsuario    (rs.getString (TablaControl.USUARIO));
                conUsoVO.setPassword   (rs.getString (TablaControl.PASSWORD));
                conUsoVO.setNivelAcceso(rs.getInt    (TablaControl.ACCESO));
                conUsoVO.setNombre     (rs.getString (TablaControl.NOMBRE));
                conUsoVO.setIdCentro   (rs.getInt    (TablaControl.CODCENTRO));
                conUsoVO.setActivo     (rs.getBoolean(TablaControl.ACTIVO));
                conUsoVO.setIdProf     (rs.getString (TablaControl.CODPROF));
            }
            rs.close();
            ps.close();
       
            return conUsoVO;

        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }

    //Método que devuelve los datos de control de todos los usuarios que no son administradores
    public static Vector devTodUsuarios(Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String            sql        = "SELECT " + TablaControl.USUARIO   + "," 
                                                 + TablaControl.PASSWORD  + ","
                                                 + TablaControl.ACCESO    + ","
                                                 + TablaControl.NOMBRE    + ","
                                                 + TablaControl.CODCENTRO + ","
                                                 + TablaControl.ACTIVO    + ","
                                                 + TablaControl.CODPROF   + 
                                       " FROM "  + TablaControl.TABLA     +
                                       " WHERE " + TablaControl.ACCESO    + " < 9";                
        
        ConUsuVO          conUsoVO   = null;

        Vector            listUsers  = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                conUsoVO = new ConUsuVO();
                conUsoVO.setUsuario    (rs.getString (TablaControl.USUARIO));
                //conUsoVO.setPassword   (rs.getString (TablaControl.PASSWORD));
                conUsoVO.setNivelAcceso(rs.getInt    (TablaControl.ACCESO));
                conUsoVO.setNombre     (rs.getString (TablaControl.NOMBRE));
                conUsoVO.setIdCentro   (rs.getInt    (TablaControl.CODCENTRO));
                conUsoVO.setActivo     (rs.getBoolean(TablaControl.ACTIVO));
                conUsoVO.setIdProf     (rs.getString (TablaControl.CODPROF));
                
                listUsers.addElement(conUsoVO);
            }

            rs.close();
            ps.close();
            
            return listUsers;

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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }


    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarUser(ConUsuVO conUsVO, String usuario,Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        int               resulSql   = 0;

        //String            sql        = "INSERT INTO TbControl(usuario,password,nivelAcceso,nombre,idCentro) VALUES(?,?,?,?,?)";
        String            sql        = "INSERT INTO " + TablaControl.TABLA +  "(" + TablaControl.USUARIO   + " , " 
                                                                                  + TablaControl.PASSWORD  + " , "
                                                                                  + TablaControl.ACCESO    + " , "
                                                                                  + TablaControl.NOMBRE    + " , "
                                                                                  + TablaControl.CODCENTRO + " , " 
                                                                                  + TablaControl.ACTIVO    + " , "   
                                                                                  + TablaControl.CODPROF   + " ) " +  
                                       " VALUES(?,?,?,?,?,?,?)";
        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString (1, conUsVO.getUsuario());
            ps.setString (2, DigestUtils.md5Hex(conUsVO.getPassword()));
            ps.setInt    (3, conUsVO.getNivelAcceso());
            ps.setString (4, conUsVO.getNombre());
            ps.setInt    (5, conUsVO.getIdCentro());
            ps.setBoolean(6, conUsVO.isActivo());
            ps.setString (7, conUsVO.getIdProf());
            
            resulSql = ps.executeUpdate();

            ps.close();
          
           //Opciones de seguridad
           //Desactivado temporalmente
           /*
           LogSegVO logSegVO = new LogSegVO();
           logSegVO.Usuario  = usuario;
           logSegVO.Tabla    = "TbControl";
           logSegVO.Fecha    = System.DateTime.Now;
           logSegVO.Operacion = "E";

           LogSegDAO.guardarLog(logSegVO);
           */

           return resulSql;

        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que elimina un registro en la base de datos
    public static int borrarUser(String login, String usuario,Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        int               resulSql   = 0;

        String            sql        = "DELETE FROM " + TablaControl.TABLA   +
                                       " WHERE "      + TablaControl.USUARIO + " = ?";
        
        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, login);

            resulSql = ps.executeUpdate();

            ps.close();
            
            //Registro de seuuridad
            //Desactivado temporalmente
            /*LogSegVO logSegVO  = new LogSegVO();
            logSegVO.Usuario   = usuario;
            logSegVO.Tabla     = "TbControl";
            logSegVO.Fecha     = System.DateTime.Now;
            logSegVO.Operacion = "B";

            LogSegDAO.guardarLog(logSegVO);
             */
            
            return resulSql;

        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de un usuario
    public static ConUsuVO devDatUsuario(String user, String usuario,Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String              sql      = "SELECT " + TablaControl.USUARIO   + "," 
                                                 + TablaControl.PASSWORD  + ","
                                                 + TablaControl.ACCESO    + ","
                                                 + TablaControl.NOMBRE    + ","
                                                 + TablaControl.CODCENTRO + ","
                                                 + TablaControl.ACTIVO    + ","
                                                 + TablaControl.CODPROF   +
                                       " FROM "  + TablaControl.TABLA     +  
                                       " WHERE " + TablaControl.USUARIO   + " = ?";
        
        
        
        ConUsuVO          conUsoVO   = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, user);

            rs = ps.executeQuery();



            if (rs.next())
            {
                conUsoVO = new ConUsuVO();
                conUsoVO.setUsuario    (rs.getString (TablaControl.USUARIO));
                //conUsoVO.setPassword   (rs.getString (TablaControl.PASSWORD));
                conUsoVO.setNivelAcceso(rs.getInt    (TablaControl.ACCESO));
                conUsoVO.setNombre     (rs.getString (TablaControl.NOMBRE));
                conUsoVO.setIdCentro   (rs.getInt    (TablaControl.CODCENTRO));
                conUsoVO.setActivo     (rs.getBoolean(TablaControl.ACTIVO));
                conUsoVO.setIdProf     (rs.getString (TablaControl.CODPROF));
                //Control de usuarios
                //Desactivado temporalmente
                /*LogSegVO logSegVO  = new LogSegVO();
                logSegVO.Usuario   = usuario;
                logSegVO.Tabla     = "TbControl";
                logSegVO.Fecha     = System.DateTime.Now;
                logSegVO.Operacion = "L";

                LogSegDAO.guardarLog(logSegVO);
                */
            }
            rs.close();
            ps.close();
         
            return conUsoVO;

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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que edita los datos de un usuario
    public static int editaUser(ConUsuVO conVO, String usuario, Connection con) throws Exception
    {
        PreparedStatement ps         = null;
        int               resulSql   = 0;

        String            sql        = "UPDATE " + TablaControl.TABLA  + " SET " + TablaControl.PASSWORD  + " = ? , " 
                                                                                 + TablaControl.ACCESO    + " = ? , " 
                                                                                 + TablaControl.NOMBRE    + " = ? , "   
                                                                                 + TablaControl.CODCENTRO + " = ? , "  
                                                                                 + TablaControl.ACTIVO    + " = ? , "  
                                                                                 + TablaControl.CODPROF   + " = ?   " +
                                       " WHERE "                                 + TablaControl.USUARIO   + " = ?   " ;

        try
        {
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros a la consulta sql
            ps.setString (1, DigestUtils.md5Hex(conVO.getPassword()));
            ps.setInt    (2, conVO.getNivelAcceso());
            ps.setString (3, conVO.getNombre());
            ps.setInt    (4, conVO.getIdCentro());
            ps.setBoolean(5, conVO.isActivo());
            ps.setString (6, conVO.getIdProf());
            ps.setString (7, conVO.getUsuario());

            resulSql = ps.executeUpdate();

            ps.close();

            //Datos de control
            //Desactivado temporalmente
            /*LogSegVO logSegVO  = new LogSegVO();
            logSegVO.Usuario   = usuario;
            logSegVO.Tabla     = "TbControl";
            logSegVO.Fecha     = System.DateTime.Now;
            logSegVO.Operacion = "M";

            LogSegDAO.guardarLog(logSegVO);
            */
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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

}


