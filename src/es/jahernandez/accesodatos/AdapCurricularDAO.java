/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.accesodatos;

import es.jahernandez.datos.AdapCurricularVO;
import es.jahernandez.gestion.AdapCurricularGestion;
import es.jahernandez.tablas.TablaAdapCurricular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.util.UserDataDocumentFactory;

/**
 *
 * @author JuanAlberto
 */
public class AdapCurricularDAO 
{
    //Método que devuelve los datos de una adaptación curricular
    public static AdapCurricularVO devolverDatosAdapCur(String codAdapCurricular, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        String            cadenaConsulta = "SELECT " + TablaAdapCurricular.CODADAPCUR + " , " 
                                                     + TablaAdapCurricular.CODALU     + " , "
                                                     + TablaAdapCurricular.MATERIA    + " , "
                                                     + TablaAdapCurricular.CURSO      + 
                                           " FROM "  + TablaAdapCurricular.TABLA      +
                                           " WHERE " + TablaAdapCurricular.CODADAPCUR + " = ?";
        
        AdapCurricularVO  adapCurVO      = new AdapCurricularVO();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codAdapCurricular);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                adapCurVO = new AdapCurricularVO();

                adapCurVO.setCodAdapCur(rs.getString(TablaAdapCurricular.CODADAPCUR));
                adapCurVO.setIdAlu     (rs.getString(TablaAdapCurricular.CODALU));
                adapCurVO.setMateria   (rs.getString(TablaAdapCurricular.MATERIA));
                adapCurVO.setCurso     (rs.getString(TablaAdapCurricular.CURSO));
            }

            rs.close();
            ps.close();

            return adapCurVO;

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
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }

    }

    //Método que devuelve todos los registro de adaptación curricular
    public static Vector devolverTodosAdapCur(Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT * FROM TbNiv";
        String            cadenaConsulta = "SELECT " + TablaAdapCurricular.CODADAPCUR  + " , " 
                                                     + TablaAdapCurricular.CODALU      + " , "
                                                     + TablaAdapCurricular.MATERIA     + " , "
                                                     + TablaAdapCurricular.CURSO       + 
                                           " FROM "  + TablaAdapCurricular.TABLA;
        
        AdapCurricularVO  adapCurVO      = null;
        Vector            listaAdapCur   = new Vector();

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                adapCurVO = new AdapCurricularVO();

                adapCurVO.setCodAdapCur(rs.getString(TablaAdapCurricular.CODADAPCUR));
                adapCurVO.setIdAlu     (rs.getString(TablaAdapCurricular.CODALU));
                adapCurVO.setMateria   (rs.getString(TablaAdapCurricular.MATERIA));
                adapCurVO.setCurso     (rs.getString(TablaAdapCurricular.CURSO));
                
                listaAdapCur.add(adapCurVO);
            }

            rs.close();
            ps.close();

            return listaAdapCur;
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
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarAdapCur(AdapCurricularVO adapCurVO, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        
        //String            cadenaConsulta = "INSERT INTO TbNiv (IdNiv , NomNiv,ContNiv,idCur) VALUES(?,?,?,?) ";
        String            cadenaConsulta = "INSERT INTO " + TablaAdapCurricular.TABLA      + " ( "  
                                                          + TablaAdapCurricular.CODADAPCUR + " , " 
                                                          + TablaAdapCurricular.CODALU     + " , "  
                                                          + TablaAdapCurricular.MATERIA    + " , " 
                                                          + TablaAdapCurricular.CURSO      + " ) " + 
                                           " VALUES(?,?,?,?)";
        
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, nueCodAdapCur());
            ps.setString(2, adapCurVO.getIdAlu());
            ps.setString(3, adapCurVO.getMateria());
            ps.setString(4, adapCurVO.getCurso());

            regActualizados = ps.executeUpdate();

            ps.close();
            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw  exc;
        }
    }

    //Método que genera un nuevo código de adaptación curricular
    public static String nueCodAdapCur()
    {
        boolean enc        = true;
        int     avc        = 1;
        int     contCar;
        String  codIntrod  = "";

        Vector  datAdapCur = AdapCurricularGestion.devolverTodosAdapCur();

        while (enc)
        {
        	contCar = ("" + datAdapCur.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datAdapCur.size() + avc);
                
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 8)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datAdapCur = AdapCurricularGestion.devolverTodosAdapCur();
            for (int ind = 0; ind < datAdapCur.size(); ind++)
            {
                AdapCurricularVO adapCurVO = (AdapCurricularVO) datAdapCur.elementAt(ind);
                if (adapCurVO.getCodAdapCur().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;
    }

    //Método que devuelve los datos de adaptación curricular de un alumno
    public static Vector devolverAdapCurAlu(String codAlu, Connection con ) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        String            sql          = "SELECT " + TablaAdapCurricular.CODADAPCUR + " , " 
                                                   + TablaAdapCurricular.CODALU     + " , "
                                                   + TablaAdapCurricular.MATERIA    + " , "
                                                   + TablaAdapCurricular.CURSO      + 
                                         " FROM "  + TablaAdapCurricular.TABLA      +
                                         " WHERE " + TablaAdapCurricular.CODALU     + " = ?"; 
        
        AdapCurricularVO  adapCurVO    = null;
        Vector            listaAdapCur = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codAlu);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                adapCurVO = new AdapCurricularVO();

                adapCurVO.setCodAdapCur(rs.getString(TablaAdapCurricular.CODADAPCUR));
                adapCurVO.setIdAlu     (rs.getString(TablaAdapCurricular.CODALU));
                adapCurVO.setMateria   (rs.getString(TablaAdapCurricular.MATERIA));
                adapCurVO.setCurso     (rs.getString(TablaAdapCurricular.CURSO));

                listaAdapCur.add(adapCurVO);
            }

            rs.close();
            ps.close();

            return listaAdapCur;
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
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }
    
     //Edita el registro de una adaptación curricular
    public static int editaAdapCur(AdapCurricularVO adapCurVO, Connection con) throws Exception
    {
        PreparedStatement ps  = null;

        String            sql = "UPDATE " + TablaAdapCurricular.TABLA      +
                                " SET "   + TablaAdapCurricular.MATERIA    + " = ? , " 
                                          + TablaAdapCurricular.CURSO      + " = ?   " +
                                " WHERE " + TablaAdapCurricular.CODADAPCUR + " = ?";
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, adapCurVO.getMateria());
            ps.setString(2, adapCurVO.getCurso());
            ps.setString(3, adapCurVO.getCodAdapCur());
           
            regActualizados = ps.executeUpdate();

            ps.close();

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }

    //Borra el registro de una adaptación curricular
    public static int eliminaAdapCur(String codAdapCur, Connection con) throws Exception
    {

        PreparedStatement ps              = null;

        String            sql             = "DELETE FROM " + TablaAdapCurricular.TABLA      + 
                                            " WHERE "      + TablaAdapCurricular.CODADAPCUR + " = ?";
        
        int               regActualizados = 0;

        
        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codAdapCur);
                       
            regActualizados = ps.executeUpdate();

            ps.close();

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }    
    
    //Borra las adaptaciones curriculares de una lumno
    public static int eliminaAdapCurAlumno(String codAlu , Connection con) throws Exception
    {

        PreparedStatement ps              = null;

        String            sql             = "DELETE FROM " + TablaAdapCurricular.TABLA  + 
                                            " WHERE "      + TablaAdapCurricular.CODALU + " = ?";
        
        int               regActualizados = 0;

        
        try
        {
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codAlu);
                       
            regActualizados = ps.executeUpdate();

            ps.close();

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AdapCurricularDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw exc;
        }
    }    
}