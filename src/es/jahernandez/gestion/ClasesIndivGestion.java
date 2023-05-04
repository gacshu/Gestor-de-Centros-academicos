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

import es.jahernandez.accesodatos.ClasesIndivDAO;
import es.jahernandez.datos.ClasesIndivVO;
import es.jahernandez.datos.Conexion;


/**
 *
 * @author JuanAlberto
 */
public class ClasesIndivGestion 
{
    //Método que devuelve los datos de una clase individual
    public static ClasesIndivVO devolverDatosClaseIndiv(String codClasInd)
    {
        Connection        con            = null;

        ClasesIndivVO     claIndVO       = new ClasesIndivVO();

        try
        {
            con = Conexion.conectar();
            
            claIndVO = ClasesIndivDAO.devolverDatosClaseIndiv(codClasInd, con);
            
            Conexion.desconectar(con);

            return claIndVO;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivGestion .class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve todos los registro de clases individuales
    public static Vector devolverTodasClasesIndividuales()
    {
        Connection        con            = null;
        Vector            listaClaInd    = new Vector();

        try
        {
            con = Conexion.conectar();
            
            listaClaInd = ClasesIndivDAO.devolverTodasClasesIndividuales(con);
                    
            Conexion.desconectar(con);

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarClasInd(ClasesIndivVO claIndVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            
            regActualizados = ClasesIndivDAO.guardarClasInd(claIndVO, con);
      
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
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de clase individual
    public static String nueCodClasInd()
    {
        String  codIntrod = "";

        codIntrod = ClasesIndivDAO.nueCodClasInd();
        
        return codIntrod;
    }

    //Método que devuelve los datos de clases individuales de un alumno
    public static Vector devolverClasesIndAlu(String codAlu )
    {
        Connection        con          = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            con = Conexion.conectar();
            
            listaClaInd = ClasesIndivDAO.devolverClasesIndAlu(codAlu, con);
            
            Conexion.desconectar(con);

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
     //Edita el registro de una clase individual
    public static int editarClaseInd(ClasesIndivVO claIndVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            
            regActualizados = ClasesIndivDAO.editarClaseInd(claIndVO,con);
            
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
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra el registro de una clase individual
    public static int eliminaClaseInd(String codClaInd)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        
        try
        {
            con = Conexion.conectar();
           
            regActualizados = ClasesIndivDAO.eliminaClaseInd(codClaInd, con);

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
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }     
    
    //Borra las clases individuales de un alumno
    public static int eliminarClasIndAlumno(String codAlu)
    {

        Connection        con             = null;
        int               regActualizados = 0;

        
        try
        {
            con = Conexion.conectar();
            
            regActualizados = ClasesIndivDAO.eliminarClasIndAlumno(codAlu, con);
            
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
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    } 
    
    //Método que devuelve si un alumno tiene clases individuales
    public static boolean tieneAluClasesInd(String codAlu )
    {
        Connection con            = null;
        boolean    aluTienClases  = false; 
     
        try
        {
            con = Conexion.conectar();
            aluTienClases = ClasesIndivDAO.tieneAluClasesInd(codAlu, con);
            Conexion.desconectar(con);

            return aluTienClases;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }
    
    //Método que devuelve los datos de clases individuales de un alumno y un mes y año concreto ordenasdos for fecha
    public static Vector devolverClasesIndAluMes(String codAlu, String strFecMesAnno)
    {
        Connection        con          = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            con = Conexion.conectar();
           
            listaClaInd = ClasesIndivDAO.devolverClasesIndAluMes(codAlu, strFecMesAnno, con);
            
            Conexion.desconectar(con);

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Método que devuelve los datos de clases individuales de un mes y año concreto ordenados por alumno y fecha
    public static Vector devolverClasesIndMes(String strFecMesAnno)
    {
        Connection        con          = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            con = Conexion.conectar();
            
            listaClaInd = ClasesIndivDAO.devolverClasesIndMes(strFecMesAnno, con);
            
            Conexion.desconectar(con);

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Método que devuelve el importe total de las clases de un alumno en un mes
    public static float devolverImporteClaseAluMes(String codAlu, String strFecMesAnno)
    {
        Connection con      = null;
        float      impTotal = 0; 

        try
        {
            con = Conexion.conectar();
            
            impTotal = ClasesIndivDAO.devolverImporteClaseAluMes(codAlu, strFecMesAnno, con);
            
            Conexion.desconectar(con);

            return impTotal;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }
    
    //Método que devuelve los datos de clases individuales de un profesor
    public static Vector devolverClasesIndProf(String codProf )
    {
        Connection        con          = null;
        Vector            listaClaInd  = new Vector();

        try
        {
            con = Conexion.conectar();
            
            listaClaInd = ClasesIndivDAO.devolverClasesIndProf(codProf,con);
            
            Conexion.desconectar(con);

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Método que devuelve los datos de clases individuales de un profesor y un mes y año concreto ordenados for fecha
    public static Vector devolverClasesIndProfMes(String codProf, String strFecMesAnno)
    {
        Connection con          = null;
        Vector     listaClaInd  = new Vector();

        try
        {
            con = Conexion.conectar();
           
            listaClaInd = ClasesIndivDAO.devolverClasesIndProfMes(codProf, strFecMesAnno, con);
            
            Conexion.desconectar(con);

            return listaClaInd;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ClasesIndivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
}
