/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.gestion;
import es.jahernandez.accesodatos.AluEdiDAO;
import es.jahernandez.accesodatos.AlumnosDAO;
import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.DatosBusquedaAlumnos;
import es.jahernandez.datos.EdicionesVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanAlberto
 */
public class AlumnosGestion 
{           
        
    //Método que devuelve los datos de búsqueda de alumnos con curso
    public static Vector devolverDatosConsulta(String cadenaConsulta)
    {
        Connection       con          = null;
        Vector           listaAlumnos = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAlumnos = AlumnosDAO.devolverDatosConsulta(cadenaConsulta, con);
            Conexion.desconectar(con);

            return listaAlumnos;

        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve los datos de búsqueda de alumnos
    public static Vector devolverDatosConsAlumno(DatosBusquedaAlumnos datBA)
    {
        Connection        con            = null;
        Vector            listaAlumnos   = new Vector();

        try
        {
            con = Conexion.conectar();    
            listaAlumnos = AlumnosDAO.devolverDatosConsAlumno(datBA, con);
            Conexion.desconectar(con);

            return listaAlumnos;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve los datos de búsqueda de alumnos por curso
    public static Vector devolverDatosConsCurso(String cadenaConsulta)
    {
        Connection        con          = null;
        Vector            listaAlumnos = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAlumnos = AlumnosDAO.devolverDatosConsCurso(cadenaConsulta, con);
            Conexion.desconectar(con);
            
            return listaAlumnos;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los datos de un alumno
    public static AlumnosVO devolverDatosAlumno(String codAlu)
    {
        Connection        con          = null;
        AlumnosVO         datAlu       = null;

        try
        {
            con = Conexion.conectar();
            datAlu = AlumnosDAO.devolverDatosAlumno(codAlu, con);
            Conexion.desconectar(con);

            return datAlu;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que genera un código nuevo de alumno
    public static String generarNuevoCodAluB()
    {
        String  codIntrod = "";
        codIntrod = AlumnosDAO.generarNuevoCodAluB();
        
        return codIntrod;
    }

    //Método que devuelve los datos de alumnos
    public static Vector devolverTodosAlu()
    {
        Connection        con          = null;
        Vector            listaAlumnos = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAlumnos = AlumnosDAO.devolverTodosAlu(con);
            Conexion.desconectar(con);

            return listaAlumnos;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    //Método que devuelve los datos de alumnos por orden alfabético
    public static Vector devolverTodosAluOrd()
    {
        Connection        con          = null;
        
        Vector            listaAlumnos = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAlumnos = AlumnosDAO.devolverTodosAluOrd(con);
            Conexion.desconectar(con);

            return listaAlumnos;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }                
                
    //Edita el registro de un interesado
    public static int editaAlumno(AlumnosVO aluVO)
    {

        Connection          con             = null;
        int                 regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AlumnosDAO.editaAlumno(aluVO, con);
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
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -2;
        }
    }

    //Borra el registro de un alumno
    public static int borraRegAlu(String codAlu)
    {
        Connection        con             = null;
        int               regActualizados = 0;

       try
        {
            con = Conexion.conectar();
            regActualizados = AlumnosDAO.borraRegAlu(codAlu, con);
            Conexion.desconectar(con);

            return regActualizados; //Todo borrado correctamente
               
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Devuelve el VALOR MAXIMO de codigo alumno
    public static String devuelveMaxAlu()
    {
        Connection        con        = null;
        String            nombreDoc  = "";

        try
        {
            con = Conexion.conectar();
            nombreDoc = AlumnosDAO.devuelveMaxAlu(con);
            Conexion.desconectar(con);

            return  nombreDoc;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }

    //Da de alta un nuevo alumno
    public static String insertaAlumno(AlumnosVO aluVO)
    {
        Connection        con             = null;
        String            codNueAlu       = "";

        try
        {
            con = Conexion.conectar();
            codNueAlu = AlumnosDAO.insertaAlumno(aluVO, con);
            Conexion.desconectar(con);

            return  codNueAlu;
        }
        catch (Exception exc)
        {
            try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "-2";
        }
    }
    
    //Genera un nuevo código de Alumno
    public static String generarNuevoCodAlu()
    {
        String  codIntrod = "";
        
        codIntrod = AlumnosDAO.generarNuevoCodAlu();
        
        return codIntrod;
    }

    //Devuelve la provincia del alumno
    public static String devolverNombreProv(String codProv)
    {
        Connection        con             = null;
        String            nombreProvincia = "";

        try
        {
           con = Conexion.conectar();
           nombreProvincia = AlumnosDAO.devolverNombreProv(codProv, con);
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
                Logger.getLogger(AluEdiGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve si ya existe el dato identificativo de un alumno
    public static boolean existeDniAlumno(String numDoc)
    {
        Connection       con           = null;
        boolean          existeDNI     = false;

        try
        {
            con = Conexion.conectar();
            existeDNI = AlumnosDAO.existeDniAlumno(numDoc, con);
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
                Logger.getLogger(AlumnosGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }

}
