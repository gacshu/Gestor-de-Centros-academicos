/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.accesodatos.ControlRecDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ControlRecVO;


/**
 *
 * @author Alberto
 */
public class ControlRecGestion
{
    //Devuelve si han generado los recibos de un mes en un determinado centro
    public static boolean generadosRecMes(String fecha, int codCen)
    {
        Connection       con   = null;
        boolean          recGen = false;

        try
        {
            con = Conexion.conectar();
            recGen = ControlRecDAO.generadosRecMes(fecha, codCen, con);
            return recGen;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ControlRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
}

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarControlRec(ControlRecVO contRecVO)
    {
        Connection        con    = null;
        int               regAct = 0;

        try
        {
            con = Conexion.conectar();
            regAct = ControlRecDAO.guardarControlRec(contRecVO, con);
            Conexion.desconectar(con);

            return regAct;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ControlRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }
    }

    //Devuelve si si han generado los recibos
    public static boolean generadosRec(String fecha)
    {
        Connection        con    = null;
        boolean           recGen = false;

        try
        {
            con = Conexion.conectar();
            recGen = ControlRecDAO.generadosRec(fecha, con);

            return recGen;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ControlRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }

}
