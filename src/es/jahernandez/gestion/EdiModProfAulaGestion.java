/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import es.jahernandez.accesodatos.EdiModProfAulaDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.EdiModProfAulaVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto
 */
public class EdiModProfAulaGestion 
{
     //Método que devuelve los datos de un módulo de una edición
    public static EdiModProfAulaVO devolverDatosModEdi(String codMod, String codEdi)
    {
        Connection        con = null;
        EdiModProfAulaVO  datEMPA = new EdiModProfAulaVO();

        try
        {
            con = Conexion.conectar();
            datEMPA = EdiModProfAulaDAO.devolverDatosModEdi(codMod, codEdi, con);
            Conexion.desconectar(con);

            return datEMPA;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve los  módulos de una edición
    public static Vector devolverModEdi(String codEdi)
    {
        Connection con       = null;
        Vector     listaEMPA = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEMPA = EdiModProfAulaDAO.devolverModEdi(codEdi, con);
            Conexion.desconectar(con);

            return listaEMPA;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Método que devuelve los  módulos de un profesor
    public static Vector devolverModProf(String codProf)
    {
        Connection        con = null;
        Vector           listaEMPA  = new Vector();

        try
        {
           con = Conexion.conectar();
           listaEMPA = EdiModProfAulaDAO.devolverModProf(codProf,con);
           Conexion.desconectar(con);

           return listaEMPA;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los  módulos pendientes de un profesor
    public static Vector devolverModProfPend(String codProf)
    {
        Connection con       = null;
        Vector     listaEMPA = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEMPA = EdiModProfAulaDAO.devolverModProfPend(codProf, con);
            Conexion.desconectar(con);

            return listaEMPA;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarEdiMod(EdiModProfAulaVO empaVO)
    {
        Connection        con            = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = EdiModProfAulaDAO.guardarEdiMod(empaVO, con);
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
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

   //Método que borra un modulo de una edición
    public static int borrarEdiMod(String codMod, String  codEdi)
    {
        Connection          con             = null;
        int                 regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = EdiModProfAulaDAO.borrarEdiMod(codMod, codEdi, con);
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
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
    
    //Método que borra todos los módulos de una edición
    public static int borrarModulosEdi(String codEdi)
    {
        Connection          con             = null;
        int                 regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = EdiModProfAulaDAO.borrarModulosEdi(codEdi, con);
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
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
    
    //Método que edita un modulo de una edición
    public static int editarEdiModProfAula(EdiModProfAulaVO empaVO)
    {
        Connection con             = null;
        int        regActualizados = 0;
        
        try
        {
            con = Conexion.conectar();
            regActualizados = EdiModProfAulaDAO.editarEdiModProfAula(empaVO, con);
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
                Logger.getLogger(EdiModProfAulaGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }   
}
