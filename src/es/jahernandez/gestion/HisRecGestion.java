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

import es.jahernandez.accesodatos.HisRecDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.HisRecVO;

/**
 *
 * @author Alberto
 */
public class HisRecGestion
{
//Método que guarda un nuevo registro en la base de datos
    public static int guardarHisRec(HisRecVO hisRecVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = HisRecDAO.guardarHisRec(hisRecVO,con);
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
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de alumno-edición de los alumnos que tienen que pagar recibo, consulta de historico
    //pagado    0 si no se quiere filtrar la busqueda por pagados
    //pagado    1 si se quieren visualizar los recibos pagados
    //pagado    2 si se quieren visualizar los recibos no pagados
    //Domicili  1 busqueda de recibos domiciliados
    public static Vector devRecHisAluEdi(String idCur, String fecIn1, String fecIn2, String codAlu, int pagado,int centro,int domicil)
    {
        Connection        con             = null;
        Vector            listaHisRec     = new Vector();

        try
        {
            con = Conexion.conectar();
            listaHisRec = HisRecDAO.devRecHisAluEdi(idCur, fecIn1, fecIn2, codAlu, pagado, centro, domicil,con);
            Conexion.desconectar(con);

            return listaHisRec;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que cambia el estado de pagado de un recibo
    public static int cambEstadoPagado(String numRec, String codAlu, boolean estado)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = HisRecDAO.cambEstadoPagado(numRec, codAlu, estado,con);
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
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de un recibo
    public static HisRecVO devDatRecHis(String codAlu, String numRec)
    {
        Connection        con             = null;
        HisRecVO          datHisRec       = null;

        try
        {
            con = Conexion.conectar();
            datHisRec = HisRecDAO.devDatRecHis(codAlu, numRec,con);
            Conexion.desconectar(con);

            return datHisRec;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve el número de recibos de un mes
    public static int devNumRecMes(String fecha) //fecha en formato aaaa/m
    {
        Connection              con      = null;
        int                     numRec   = 0;

        try
        {
            con = Conexion.conectar();
            numRec = HisRecDAO.devNumRecMes(fecha,con);
            Conexion.desconectar(con);

            return numRec;
        }
         catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve si se ha generado un recibo del mes actual de un curso de un alumno
    public static boolean  reciboGenerado(String codAlu, String codEdi)
    {
        Connection        con            = null;
        boolean           reciboGenerado = true;

        try
        {
            con = Conexion.conectar();
            reciboGenerado = HisRecDAO.reciboGenerado(codAlu, codEdi,con);
            Conexion.desconectar(con);

            return reciboGenerado;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }
    }

    //Método que devuelve los datos de alumno-edición de los recibos generados pero no impresos
    public static Vector devRecGenNoImp()
    {
        Connection        con         = null;
        Vector           listaHisRec = new Vector();

        try
        {
            con = Conexion.conectar();
            listaHisRec = HisRecDAO.devRecGenNoImp(con);
            Conexion.desconectar(con);
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
        return listaHisRec;
    }

    //Método que elimina los datos de historico de una edición
    public static int eliminarDatosHisEdi(String codEdi)
    {
        Connection        con     = null;
        int               devRes  = 0;

        try
        {
            con = Conexion.conectar();
            devRes = HisRecDAO.eliminarDatosHisEdi(codEdi,con);
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
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve ell número de recibos generados un mes
    public static int  numeroRecGenMes(String annoBus, String mesBus)
    {
        Connection       con            = null;
        int              numRecGen      = 0;

        try
        {
            con = Conexion.conectar();
            numRecGen = HisRecDAO.numeroRecGenMes(annoBus, mesBus, con);
            Conexion.desconectar(con);

            return numRecGen;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }
}
