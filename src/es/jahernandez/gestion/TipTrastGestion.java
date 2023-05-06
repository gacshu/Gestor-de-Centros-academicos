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

import es.jahernandez.accesodatos.TipTrastDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.TipTrastVO;

/**
 *
 * @author Alberto
 */
public class TipTrastGestion
{
     //Método que devuelve los datos de tipo Trastorno
    public static Vector devolverTodTipTrast()
    {
        Connection        con            = null;
        Vector            listaTipTras   = new Vector();

        try
        {
            con = Conexion.conectar();
            listaTipTras = TipTrastDAO.devolverTodTipTrast(con);
            Conexion.desconectar(con);

            return listaTipTras;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipTrastGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve el nombre de un tipo de trastorno
    public static String devuelveNombreTipoTrastorno(String codTrast)
    {
        Connection        con       = null;
        String            nomTrast  = "";

        try
        {
            con = Conexion.conectar();
            nomTrast = TipTrastDAO.devuelveNombreTipoTrastorno(codTrast,con);
            Conexion.desconectar(con);

            return nomTrast;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipTrastGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Genera un nuevo código de tipo trastorno
    public static String generarNuevoCodTipTrast()
    {
        return TipTrastDAO.generarNuevoCodTipTrast();
    }

    //Método que guarda un tipo trastorno
    public static int guardarTipTrast(TipTrastVO tipTrasVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = TipTrastDAO.guardarTipTrast(tipTrasVO,con);
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
                Logger.getLogger(TipTrastGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Edita el registro de un tipo de trastorno
    public static int editarTipTrast(TipTrastVO tipTrastVO)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        try
        {

            con = Conexion.conectar();
            regActualizados = TipTrastDAO.editarTipTrast(tipTrastVO,con);
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
                Logger.getLogger(TipTrastGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
}
