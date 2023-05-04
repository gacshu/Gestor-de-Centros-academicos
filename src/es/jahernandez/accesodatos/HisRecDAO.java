/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.HisRecVO;

import es.jahernandez.tablas.TablaAlumnosEdiciones;
import es.jahernandez.tablas.TablaHistoricoRecibos;
import es.jahernandez.tablas.TablaEdiciones;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

/**
 *
 * @author Alberto
 */
public class HisRecDAO
{
//Método que guarda un nuevo registro en la base de datos
    public static int guardarHisRec(HisRecVO hisRecVO, Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            sql             = "INSERT INTO " + TablaHistoricoRecibos.TABLA      + " ( " 
                                                           + TablaHistoricoRecibos.CODEDICION + " , " 
                                                           + TablaHistoricoRecibos.CODALUMNO  + " , "
                                                           + TablaHistoricoRecibos.NUMRECIBO  + " , "
                                                           + TablaHistoricoRecibos.FECHAEXP   + " , "  
                                                           + TablaHistoricoRecibos.PAGADO     + " , " 
                                                           + TablaHistoricoRecibos.CODCURSO   + " ) " +
                                            " VALUES(?,?,?,?,?,?)";

        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);

            // Se pasan los parámetros de la consula sql
            ps.setString (1, hisRecVO.getIdEdi());
            ps.setString (2, hisRecVO.getIdAlu());
            ps.setString (3, hisRecVO.getNumRec());
            ps.setDate   (4, new Date(hisRecVO.getFecExp().getTime()));
            ps.setBoolean(5, hisRecVO.isPagado());
            ps.setString (6, hisRecVO.getIdCur());

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
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de alumno-edición de los alumnos que tienen que pagar recibo, consulta de historico
    //pagado    0 si no se quiere filtrar la busqueda por pagados
    //pagado    1 si se quieren visualizar los recibos pagados
    //pagado    2 si se quieren visualizar los recibos no pagados
    //Domicili  1 busqueda de recibos domiciliados
    public static Vector devRecHisAluEdi(String idCur, String fecIn1, String fecIn2, String codAlu, int pagado,int centro,int domicil, Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        ResultSet         rs              = null;

        Vector            listaHisRec     = new Vector();

        HisRecVO          datHisRec       = null;

        String condEdic = "WHERE ";

        if (! idCur.trim().equals(""))
        {
            condEdic = condEdic + TablaHistoricoRecibos.CODCURSO + " = '"       + idCur  + "' AND ";
        }

        if ( ! fecIn1.trim().equals("") && ! fecIn2.trim().equals(""))
        {
            condEdic = condEdic + TablaHistoricoRecibos.FECHAEXP + " BETWEEN '" + fecIn1 + " ' AND '" + fecIn2 + "' AND ";
        }
        else
        {
            if (! fecIn1.trim().equals(""))
            {
                condEdic = condEdic +  TablaHistoricoRecibos.FECHAEXP +  " >='" + fecIn1 + "' AND ";
            }
        }

        if (! codAlu.trim().equals(""))
        {
            condEdic = condEdic +  TablaHistoricoRecibos.CODALUMNO + " = '" + codAlu + "' AND ";
        }

        if (pagado == 1)
        {
            condEdic = condEdic +  TablaHistoricoRecibos.PAGADO   + " = true AND ";
        }


        if (pagado == 2)
        {
            condEdic = condEdic +  TablaHistoricoRecibos.PAGADO + " = false  AND ";
        }

        if (domicil == 1)
        {
            condEdic = condEdic + "(LTRIM(" + TablaAlumnosEdiciones.CODEDI + "+" + TablaAlumnosEdiciones.CODALU + ") IN " +
                                  "(SELECT  LTRIM(" + TablaAlumnosEdiciones.CODEDI + "+" + TablaAlumnosEdiciones.CODALU + ") AS EXPR1 " +
                                  " FROM " + TablaAlumnosEdiciones.TABLA  +
                                  " WHERE (LTRIM(" + TablaAlumnosEdiciones.NUMCUENTA + ") <> ''))) AND ";
        }

        if (centro != 0)
        {
            condEdic = condEdic +  TablaEdiciones.CODEDI    + " IN " +
                       "(SELECT  " + TablaEdiciones.CODEDI  + 
                       " FROM "  + TablaEdiciones.TABLA     +
                       " WHERE " + TablaEdiciones.CODCENTRO + " = " + centro + ") AND " ;
        }


        condEdic = condEdic.substring(0, condEdic.length() - 4);

        if(condEdic.length() <5)
        {
            condEdic = "";
        }

        String sql = "SELECT  "   + TablaHistoricoRecibos.CODEDICION + " , " 
                                  + TablaHistoricoRecibos.CODALUMNO  + " , "
                                  + TablaHistoricoRecibos.NUMRECIBO  + " , "
                                  + TablaHistoricoRecibos.FECHAEXP   + " , "  
                                  + TablaHistoricoRecibos.PAGADO     + " , " 
                                  + TablaHistoricoRecibos.CODCURSO   + 
                     " FROM "     + TablaHistoricoRecibos.TABLA      + "   " +   
                     condEdic     +  //WHERE
                     " ORDER BY " + TablaHistoricoRecibos.CODEDICION;                               
        try
        {
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datHisRec = new HisRecVO();

                datHisRec.setIdEdi (rs.getString (TablaHistoricoRecibos.CODEDICION));
                datHisRec.setIdAlu (rs.getString (TablaHistoricoRecibos.CODALUMNO));
                datHisRec.setNumRec(rs.getString (TablaHistoricoRecibos.NUMRECIBO));
                datHisRec.setFecExp(rs.getDate   (TablaHistoricoRecibos.FECHAEXP));
                datHisRec.setPagado(rs.getBoolean(TablaHistoricoRecibos.PAGADO));
                datHisRec.setIdCur (rs.getString (TablaHistoricoRecibos.CODCURSO));

                listaHisRec.addElement(datHisRec);
            }

            rs.close();
            ps.close();
            
            return listaHisRec;

        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que cambia el estado de pagado de un recibo
    public static int cambEstadoPagado(String numRec, String codAlu, boolean estado , Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            sql             = "UPDATE " + TablaHistoricoRecibos.TABLA     + 
                                            " SET "   + TablaHistoricoRecibos.PAGADO    + " = ? " + 
                                            " WHERE " + TablaHistoricoRecibos.NUMRECIBO + " = ? AND " 
                                                      + TablaHistoricoRecibos.CODALUMNO + " = ? ";
        
        int               regActualizados = 0;

        try
        {
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros de la consulta sql
            ps.setBoolean(1, estado );
            ps.setString (2, numRec );
            ps.setString (3, codAlu );
            
            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

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
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de un recibo
    public static HisRecVO devDatRecHis(String codAlu, String numRec, Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        ResultSet         rs              = null;
        
        String            sql             = "SELECT  "   + TablaHistoricoRecibos.CODEDICION + " , " 
                                                         + TablaHistoricoRecibos.CODALUMNO  + " , "
                                                         + TablaHistoricoRecibos.NUMRECIBO  + " , "
                                                         + TablaHistoricoRecibos.FECHAEXP   + " , "  
                                                         + TablaHistoricoRecibos.PAGADO     + " , " 
                                                         + TablaHistoricoRecibos.CODCURSO   + 
                                            " FROM "     + TablaHistoricoRecibos.TABLA      +   
                                            " WHERE "    + TablaHistoricoRecibos.NUMRECIBO  + " = ? AND " 
                                                         + TablaHistoricoRecibos.CODALUMNO  + " = ? ";
        
        HisRecVO          datHisRec       = null;

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, numRec);
            ps.setString(2, codAlu);

            rs = ps.executeQuery();

            if (rs.next())
            {
                datHisRec = new HisRecVO();

                datHisRec.setIdEdi (rs.getString (TablaHistoricoRecibos.CODEDICION));
                datHisRec.setIdAlu (rs.getString (TablaHistoricoRecibos.CODALUMNO));
                datHisRec.setNumRec(rs.getString (TablaHistoricoRecibos.NUMRECIBO));
                datHisRec.setFecExp(rs.getDate   (TablaHistoricoRecibos.FECHAEXP));
                datHisRec.setPagado(rs.getBoolean(TablaHistoricoRecibos.PAGADO));
                datHisRec.setIdCur (rs.getString (TablaHistoricoRecibos.CODCURSO));
            }
            
            rs.close();
            ps.close();
            
            return datHisRec;
        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve el número de recibos de un mes
    public static int devNumRecMes(String fecha, Connection con) throws Exception //fecha en formato aaaa/m
    {
        PreparedStatement       ps       = null;
        ResultSet               rs       = null;

        String                  sql      = "SELECT COUNT(" + TablaHistoricoRecibos.CODEDICION + ") AS EXPR1 " + 
                                           " FROM "        + TablaHistoricoRecibos.TABLA      + 
                                           " WHERE "       + TablaHistoricoRecibos.NUMRECIBO  + " LIKE '"     + fecha + "%'";
                
        int                     numRec   = 0;

        try
        {
            ps  = con.prepareStatement(sql);
            
            rs  = ps.executeQuery();
            
            if(rs.next())
            {
                numRec = rs.getInt(1);
            }

            rs.close();
            ps.close();
            
            return numRec;
        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }

    //Método que devuelve si se ha generado un recibo del mes actual de un curso de un alumno
    public static boolean  reciboGenerado(String codAlu, String codEdi, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;
        
        String            mesAc          = new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()));   
        String            annoAc         = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));  

        boolean           reciboGenerado = true;

        String           fecMesAct       = "01/" +  new Integer(new GregorianCalendar().get(Calendar.MONTH) + 1).toString() +
                                                "/" + new Integer(new GregorianCalendar().get(Calendar.YEAR) + 1).toString();
        String           fecMesSig       = "";
        int              mesSig          = new GregorianCalendar().get(Calendar.MONTH) + 2; //Se añanden 2 porque el mes empieza en 0
        int              annoSig         = new GregorianCalendar().get(Calendar.YEAR);

        String           sql             = "SELECT " + TablaHistoricoRecibos.CODEDICION + " , " 
                                                     + TablaHistoricoRecibos.CODALUMNO  + " , "
                                                     + TablaHistoricoRecibos.NUMRECIBO  + " , "
                                                     + TablaHistoricoRecibos.FECHAEXP   + " , "  
                                                     + TablaHistoricoRecibos.PAGADO     + " , " 
                                                     + TablaHistoricoRecibos.CODCURSO   + 
                                           " FROM "  + TablaHistoricoRecibos.TABLA      +                                                   
                                           " WHERE " + TablaHistoricoRecibos.CODEDICION + " = ? AND " 
                                                     + TablaHistoricoRecibos.CODALUMNO  + " = ? AND "
                                                     + TablaHistoricoRecibos.FECHAEXP   + " > ? AND " 
                                                     + TablaHistoricoRecibos.FECHAEXP   + " < ? AND " 
                                                     + TablaHistoricoRecibos.NUMRECIBO  + " LIKE '" + annoAc + "/" + mesAc + "%'";   

            
        
        
        if (mesSig > 12)
        {
            mesSig = 1;
            annoSig++;
        }

        fecMesSig = "01/" + mesSig + "/" + annoSig;

        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);
            ps.setString(2, codAlu);
            ps.setDate  (3, new Date(df.parse(fecMesAct).getTime()));
            ps.setDate  (4, new Date(df.parse(fecMesSig).getTime()));

            rs = ps.executeQuery();

            if (rs.next())
            {
                reciboGenerado =  true;
            }
            else
            {
                reciboGenerado = false;
            }

            rs.close();
            ps.close();
            
            return reciboGenerado;

        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de alumno-edición de los recibos generados pero no impresos
    public static Vector devRecGenNoImp(Connection con) throws Exception
    {
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        String            sql         = "SELECT "    + TablaHistoricoRecibos.CODEDICION + " , " 
                                                     + TablaHistoricoRecibos.CODALUMNO  + " , "
                                                     + TablaHistoricoRecibos.NUMRECIBO  + " , "
                                                     + TablaHistoricoRecibos.FECHAEXP   + " , "  
                                                     + TablaHistoricoRecibos.PAGADO     + " , " 
                                                     + TablaHistoricoRecibos.CODCURSO   + 
                                        " FROM "     + TablaHistoricoRecibos.TABLA      + 
                                        " WHERE "    + TablaHistoricoRecibos.FECHAEXP   + " >= ? AND " 
                                                     + TablaHistoricoRecibos.FECHAEXP   + " <  ? "     +
                                        " ORDER BY " + TablaHistoricoRecibos.CODEDICION;
        
        
        Vector           listaHisRec = new Vector();
        HisRecVO         datHisRec   = null;
        
        
        int              mesAct      = new GregorianCalendar().get(Calendar.MONTH) + 1;
        int              annoAc      = new GregorianCalendar().get(Calendar.YEAR) + 1;
        int              mesSig      = mesAct + 1;
        int              annoSi      = annoAc;
        String           fecAct      = "";
        String           fecSig      = "";

        if (mesSig > 12)
        {
            mesSig = 1;
            annoSi = annoSi + 1;
        }

        fecAct = "01/" + mesAct + "/" + annoAc;
        fecSig = "01/" + mesSig + "/" + annoSi;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            ps  = con.prepareStatement(sql);
                        
            //Se pasan los parámetros de la consulta sql
            ps.setDate(1, new Date(df.parse(fecAct).getTime()));
            ps.setDate(2, new Date(df.parse(fecSig).getTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                datHisRec = new HisRecVO();


                datHisRec.setIdEdi (rs.getString (TablaHistoricoRecibos.CODEDICION ));
                datHisRec.setIdAlu (rs.getString (TablaHistoricoRecibos.CODALUMNO));
                datHisRec.setNumRec(rs.getString (TablaHistoricoRecibos.NUMRECIBO));
                datHisRec.setFecExp(rs.getDate   (TablaHistoricoRecibos.FECHAEXP));
                datHisRec.setPagado(rs.getBoolean(TablaHistoricoRecibos.PAGADO));
                datHisRec.setIdCur (rs.getString (TablaHistoricoRecibos.CODCURSO));

                listaHisRec.addElement(datHisRec);
            }

            rs.close();
            ps.close();
        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

        return listaHisRec;
    }

    //Método que elimina los datos de historico de una edición
    public static int eliminarDatosHisEdi(String codEdi,Connection con) throws Exception
    {
        PreparedStatement ps      = null;

        int               devRes  = 0;

        String            sql     = "DELETE FROM " + TablaHistoricoRecibos.TABLA      +
                                    " WHERE "      + TablaHistoricoRecibos.CODEDICION + " = ? ";
                
        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);

            devRes = ps.executeUpdate();

            ps.close();
            
            return devRes;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }
    
    //Método que devuelve ell número de recibos generados un mes
    public static int  numeroRecGenMes(String annoBus, String mesBus,Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        int              numRecGen       = 0;
       
        String           sql             = "SELECT COUNT(" + TablaHistoricoRecibos.NUMRECIBO + ")"  +
                                           " FROM " + TablaHistoricoRecibos.TABLA +
                                           " WHERE " + TablaHistoricoRecibos.NUMRECIBO + " LIKE '" + annoBus + "/" + mesBus + "%'";
        try
        {
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next())
            {
                numRecGen =  rs.getInt(1);
            }
            
            rs.close();
            ps.close();
            
            return numRecGen;

        }
        catch (Exception exc)
        {
            try
            {
                rs.next();
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HisRecDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }

    }   
}
