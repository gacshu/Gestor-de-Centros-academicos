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

import es.jahernandez.accesodatos.AulasDAO;
import es.jahernandez.datos.AulasVO;
import es.jahernandez.datos.Conexion;

/**
 *
 * @author JuanAlberto
 */
public class AulasGestion 
{
    //Método que genera un nuevo código de Aula
    public static String generarNuevoCodAula()
    {
        String    codIntrod = "";
        
        codIntrod = AulasDAO.generarNuevoCodAula();
        
        return codIntrod;
    }

    //Método que devuelve los datos de aula de todas las aulas
    public static Vector devolverTodosAula()
    {
        Connection        con        = null;
        Vector            listaAulas = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAulas = AulasDAO.devolverTodosAula(con);
            Conexion.desconectar(con);

            return listaAulas;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AulasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

             return null;
        }

    }

    //Método que devuelve los datos de aula de un aula
    public static AulasVO devolverDatosAula(String codAul)
    {
        Connection        con        = null;
        AulasVO           datAul     = null;

        try
        {
            con = Conexion.conectar();
            datAul = AulasDAO.devolverDatosAula(codAul, con);
            Conexion.desconectar(con);

            return datAul;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AulasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

             return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    //Devuelve el código de aula generado, si se inserta correctamente
    public static String guardarAula(AulasVO aulVO)
    {
        Connection        con             = null;
        String            nueCodAul       = generarNuevoCodAula();
        
        try
        {
            con = Conexion.conectar();
            nueCodAul = AulasDAO.guardarAula(aulVO, con);
            Conexion.desconectar(con);

            return nueCodAul;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AulasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "-2";
        }

    }

    //Edita el registro de un aula
    public static int editaAula(AulasVO aulVO)
    {
        Connection        con             = null;
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            regActualizados = AulasDAO.editaAula(aulVO, con);
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
                Logger.getLogger(AulasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de aula de todas las aulas de un centro
    public static Vector devolverAulasCentro(int codCentro)
    {
        Connection        con        = null;
        Vector            listaAulas = new Vector();

        try
        {
            con = Conexion.conectar();
            listaAulas = AulasDAO.devolverAulasCentro(codCentro, con);
            Conexion.desconectar(con);

            return listaAulas;
        }
        catch (Exception exc)
        {
             try
            {
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AulasGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

             return null;
        }

    }

}
