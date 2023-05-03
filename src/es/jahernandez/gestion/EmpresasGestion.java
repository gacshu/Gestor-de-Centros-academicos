/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;

import es.jahernandez.accesodatos.EmpresasDAO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.EmpresasVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto
 */
public class EmpresasGestion 
{
    //Método que devuelve los datos de todas las empresas
    public static Vector devolverTodosEmp()
    {
        Connection        con            = null;
        Vector            listaEmpresas  = new Vector();
        
        try
        {
            con = Conexion.conectar();
            listaEmpresas = EmpresasDAO.devolverTodosEmp(con);
            Conexion.desconectar(con);

            return listaEmpresas;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }       
}

    //Devuelve el VALOR MAXIMO de codigo EMPRESA
    public static String devuelveMaxEmp()
    {
        Connection        con          = null;
        String            strMaxCodEmp = "";
        
        try
        {
            con = Conexion.conectar();
            strMaxCodEmp = EmpresasDAO.devuelveMaxEmp(con);
            Conexion.desconectar(con);

            return strMaxCodEmp;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Guarda un registro con los datos de EMPRESA
    public static String guardaEmp(EmpresasVO empVO)
    {
        Connection con       = null;
        String     codNueEmp = "";            
                
        try
        {
            con = Conexion.conectar();
            codNueEmp = EmpresasDAO.guardaEmp(empVO,con);
            Conexion.desconectar(con);
            
            return codNueEmp;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "-1";
        }

    }

    //Método que genera un código nuevo de empresa
    public static String generarNuevoCodEmp1()
    {
        return EmpresasDAO.generarNuevoCodEmp1();
    }

    //Método que genera un código nuevo de empresa
     public static String generarNuevoCodEmp()
    {
        Connection        con      = null;
        String            nuevoCod = "";

        try
        {
            con = Conexion.conectar();
            nuevoCod = EmpresasDAO.generarNuevoCodEmp(con);
            Conexion.desconectar(con);
            
            return nuevoCod;
        }
        catch (Exception exc)
        {
            try
            {
               Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de empresa que se cargan en los combos
    public static Vector devolverDatosEmpCom()
    {
        Connection        con           = null;
        Vector            listaEmpresas = new Vector();

        try
        {
            con = Conexion.conectar();
            listaEmpresas = EmpresasDAO.devolverDatosEmpCom(con);
            Conexion.desconectar(con);

            return listaEmpresas;
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

    //Método que devuelve los datos de una empresa
    public static EmpresasVO devolverDatEmp(String codEmp)
    {
        Connection        con    = null;
        EmpresasVO        datEmp = null;

        try
        {
            con = Conexion.conectar();
            datEmp = EmpresasDAO.devolverDatEmp(codEmp,con);
            Conexion.desconectar(con);

            return datEmp;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que realiza busqueda de empresas
    public static Vector buscarEmpresas(EmpresasVO conBusVO)
    {
        Connection        con       = null;
        Vector            listBusq  = new Vector();
        
        try
        {
            con = Conexion.conectar();
            listBusq = EmpresasDAO.buscarEmpresas(conBusVO,con);
            Conexion.desconectar(con);

            return listBusq;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Edita el registro de una empresa
    public static int editaEmpresa(EmpresasVO empVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;
        
        try
        {
            con = Conexion.conectar();
            regActualizados = EmpresasDAO.editaEmpresa(empVO,con);
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
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve el nombre de una actividad de una empresa
    public static String devolverActividad(String codAct)
    {
        Connection        con        = null;
        String            nomActDev  = "";

        try
        {
            con = Conexion.conectar();
            nomActDev = EmpresasDAO.devolverActividad(codAct,con);
            Conexion.desconectar(con);

            return nomActDev;
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

            return "";
        }
    }

    //Método que devuelve el nombre de la provincia de una empresa
    public static String devolverProvincia(String codProv)
    {
        Connection        con       = null;
        String            nomProDev = "";

        try
        {

            con = Conexion.conectar();
            nomProDev = EmpresasDAO.devolverProvincia(codProv,con);
            return nomProDev;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }

    //Borra el registro de una empresa si no tiene empleados asociados en la base de datos
    public static int borraEmpresa(String codEmp)
    {
        Connection        con             = null;
        int               regActualizados = 0;
        
        try
        {
            con = Conexion.conectar();
            regActualizados = EmpresasDAO.borraEmpresa(codEmp,con);
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
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve si una empresa tiene alumnos
    public static boolean devolverEmpresaTieneAlu(String codEmp)
    {
        Connection        con       = null;
        boolean           empTieAlu = true;
        
        try
        {
            con = Conexion.conectar();
            empTieAlu = EmpresasDAO.devolverEmpresaTieneAlu(codEmp,con);
            Conexion.desconectar(con);
            
            return empTieAlu;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }
    }
    
    //Devuelve el nombre de la empresa
    public static String devuelveNombreEmpresa(String idEmpresa)
    {
        Connection        con        = null;
        String            nomEmpresa = "";

        try
        {
            con = Conexion.conectar();
            nomEmpresa = EmpresasDAO.devuelveNombreEmpresa(idEmpresa,con);
            Conexion.desconectar(con);

            return nomEmpresa;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EmpresasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "";
        }
    }
}