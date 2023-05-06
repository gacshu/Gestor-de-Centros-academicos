/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.accesodatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.jahernandez.datos.DatosBusqProfVO;
import es.jahernandez.datos.ProfesoresVO;
import es.jahernandez.gestion.ProfesoresGestion;
import es.jahernandez.tablas.TablaProfArea;
import es.jahernandez.tablas.TablaProfesores;
import es.jahernandez.tablas.TablaProvincias;

/**
 *
 * @author JuanAlberto
 */
public class ProfesoresDAO
{
    //Método que devuelve los datos de búsqueda de profesores
    public static Vector devolverDatosConsultaProf(DatosBusqProfVO datBus, Connection con) throws Exception
    {
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        Vector            listaProf      = new Vector();

        ProfesoresVO      datResBus      = null;

        String            cadenaConsulta =  "SELECT " + TablaProfesores.CODPROFESOR   + " , "
                                                      + TablaProfesores.NOMBRE        + " , "
                                                      + TablaProfesores.APELLIDOS     + " , "
                                                      + TablaProfesores.CODTIPDOC     + " , "
                                                      + TablaProfesores.NUMDOC        + " , "
                                                      + TablaProfesores.FECHANAC      + " , "
                                                      + TablaProfesores.FECHAALTA     + " , "
                                                      + TablaProfesores.DIRECCION     + " , "
                                                      + TablaProfesores.LOCALIDAD     + " , "
                                                      + TablaProfesores.CODPROV       + " , "
                                                      + TablaProfesores.CODPOSTAL     + " , "
                                                      + TablaProfesores.OBSERVACIONES + " , "
                                                      + TablaProfesores.TELEFONO      + " , "
                                                      + TablaProfesores.MOVIL         + " , "
                                                      + TablaProfesores.MAIL          + " , "
                                                      + TablaProfesores.ACTIVO        +
                                            " FROM  " + TablaProfesores.TABLA ;

        //Se construye la cadena de búsqueda
        if (datBus.getNombre().equals("") && datBus.getApellidos().equals("")  &&
            datBus.getNumDoc().equals("") && datBus.getCodArea().equals("-1") && ! datBus.isActivo())
        {

        	cadenaConsulta = cadenaConsulta + "";
        }
        else
        {
            cadenaConsulta = cadenaConsulta + " WHERE ";
            //Se construye la condición de búsqueda

            if (! datBus.getNombre().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaProfesores.NOMBRE + " LIKE '%" + datBus.getNombre().toUpperCase() + "%' AND ";
            }

            if (! datBus.getApellidos().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaProfesores.APELLIDOS + " LIKE '%" + datBus.getApellidos().toUpperCase() + "%' AND ";
            }

            if (! datBus.getNumDoc().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaProfesores.NUMDOC + " LIKE '%" + datBus.getNumDoc().toUpperCase() + "%' AND ";
            }

            if (datBus.isActivo())
            {
                cadenaConsulta = cadenaConsulta + TablaProfesores.ACTIVO + " = 1  AND ";
            }

            if (! datBus.getCodArea().equals("-1"))
            {
                cadenaConsulta = cadenaConsulta + TablaProfesores.CODPROFESOR + " IN ( " +
                                            " SELECT " + TablaProfArea.CODPROF +
                                            " FROM "   + TablaProfArea.TABLA   +
                                            " WHERE "  + TablaProfArea.CODAREA + " = '" + datBus.getCodArea() + "') AND" ;
            }


            cadenaConsulta = cadenaConsulta.substring(0, cadenaConsulta.length()- 4);
        }

        cadenaConsulta = cadenaConsulta + " ORDER BY "  + TablaProfesores.APELLIDOS;


        try
        {
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datResBus = new ProfesoresVO();

                datResBus.setIdProf   (rs.getString (TablaProfesores.CODPROFESOR));
                datResBus.setNombre   (rs.getString (TablaProfesores.NOMBRE));
                datResBus.setApellidos(rs.getString (TablaProfesores.APELLIDOS));
                datResBus.setCodDoc   (rs.getInt    (TablaProfesores.CODTIPDOC));
                datResBus.setNumDoc   (rs.getString (TablaProfesores.NUMDOC));
                datResBus.setFecNac   (rs.getDate   (TablaProfesores.FECHANAC));
                datResBus.setFecAlt   (rs.getDate   (TablaProfesores.FECHAALTA));
                datResBus.setDireccion(rs.getString (TablaProfesores.DIRECCION));
                datResBus.setLocalidad(rs.getString (TablaProfesores.LOCALIDAD));
                datResBus.setCodProv  (rs.getString (TablaProfesores.CODPROV));
                datResBus.setCodPos   (rs.getString (TablaProfesores.CODPOSTAL));
                datResBus.setObserv   (rs.getString (TablaProfesores.OBSERVACIONES));
                datResBus.setTelef    (rs.getString (TablaProfesores.TELEFONO));
                datResBus.setMov      (rs.getString (TablaProfesores.MOVIL));
                datResBus.setEmail    (rs.getString (TablaProfesores.MAIL));
                datResBus.setActivo   (rs.getBoolean(TablaProfesores.ACTIVO));

                listaProf.addElement(datResBus);
            }

            rs.close();
            ps.close();

            return listaProf;
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
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de un profesor
    public static ProfesoresVO devolverDatosProfesor(String codProf, Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        ProfesoresVO      datProf      = null;

        String            sql          = "SELECT " + TablaProfesores.CODPROFESOR   + " , "
                                                   + TablaProfesores.NOMBRE        + " , "
                                                   + TablaProfesores.APELLIDOS     + " , "
                                                   + TablaProfesores.CODTIPDOC     + " , "
                                                   + TablaProfesores.NUMDOC        + " , "
                                                   + TablaProfesores.FECHANAC      + " , "
                                                   + TablaProfesores.FECHAALTA     + " , "
                                                   + TablaProfesores.DIRECCION     + " , "
                                                   + TablaProfesores.LOCALIDAD     + " , "
                                                   + TablaProfesores.CODPROV       + " , "
                                                   + TablaProfesores.CODPOSTAL     + " , "
                                                   + TablaProfesores.OBSERVACIONES + " , "
                                                   + TablaProfesores.TELEFONO      + " , "
                                                   + TablaProfesores.MOVIL         + " , "
                                                   + TablaProfesores.MAIL          + " , "
                                                   + TablaProfesores.ACTIVO        +
                                         " FROM  " + TablaProfesores.TABLA         +
                                         " WHERE " + TablaProfesores.CODPROFESOR   + " = ?";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se añaden los parámetros a la consulta sql
            ps.setString(1,codProf);

            rs = ps.executeQuery();

            if (rs.next())
            {

                datProf = new ProfesoresVO();

                datProf.setIdProf   (rs.getString (TablaProfesores.CODPROFESOR));
                datProf.setNombre   (rs.getString (TablaProfesores.NOMBRE));
                datProf.setApellidos(rs.getString (TablaProfesores.APELLIDOS));
                datProf.setCodDoc   (rs.getInt    (TablaProfesores.CODTIPDOC));
                datProf.setNumDoc   (rs.getString (TablaProfesores.NUMDOC));
                datProf.setFecNac   (rs.getDate   (TablaProfesores.FECHANAC));
                datProf.setFecAlt   (rs.getDate   (TablaProfesores.FECHAALTA));
                datProf.setDireccion(rs.getString (TablaProfesores.DIRECCION));
                datProf.setLocalidad(rs.getString (TablaProfesores.LOCALIDAD));
                datProf.setCodProv  (rs.getString (TablaProfesores.CODPROV));
                datProf.setCodPos   (rs.getString (TablaProfesores.CODPOSTAL));
                datProf.setObserv   (rs.getString (TablaProfesores.OBSERVACIONES));
                datProf.setTelef    (rs.getString (TablaProfesores.TELEFONO));
                datProf.setMov      (rs.getString (TablaProfesores.MOVIL));
                datProf.setEmail    (rs.getString (TablaProfesores.MAIL));
                datProf.setActivo   (rs.getBoolean(TablaProfesores.ACTIVO));
            }

            rs.close();
            ps.close();

            return datProf;
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
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve los datos de todos los profesores
    public static Vector devolverTodosProf(Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        String            sql          = "SELECT " + TablaProfesores.CODPROFESOR   + " , "
                                                   + TablaProfesores.NOMBRE        + " , "
                                                   + TablaProfesores.APELLIDOS     + " , "
                                                   + TablaProfesores.CODTIPDOC     + " , "
                                                   + TablaProfesores.NUMDOC        + " , "
                                                   + TablaProfesores.FECHANAC      + " , "
                                                   + TablaProfesores.FECHAALTA     + " , "
                                                   + TablaProfesores.DIRECCION     + " , "
                                                   + TablaProfesores.LOCALIDAD     + " , "
                                                   + TablaProfesores.CODPROV       + " , "
                                                   + TablaProfesores.CODPOSTAL     + " , "
                                                   + TablaProfesores.OBSERVACIONES + " , "
                                                   + TablaProfesores.TELEFONO      + " , "
                                                   + TablaProfesores.MOVIL         + " , "
                                                   + TablaProfesores.MAIL          + " , "
                                                   + TablaProfesores.ACTIVO        +
                                         " FROM  " + TablaProfesores.TABLA         ;

        ProfesoresVO      datProf      = null;
        Vector            listaProf    = new Vector();

        try
        {
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next() )
            {
                datProf = new ProfesoresVO();

                datProf.setIdProf   (rs.getString (TablaProfesores.CODPROFESOR));
                datProf.setNombre   (rs.getString (TablaProfesores.NOMBRE));
                datProf.setApellidos(rs.getString (TablaProfesores.APELLIDOS));
                datProf.setCodDoc   (rs.getInt    (TablaProfesores.CODTIPDOC));
                datProf.setNumDoc   (rs.getString (TablaProfesores.NUMDOC));
                datProf.setFecNac   (rs.getDate   (TablaProfesores.FECHANAC));
                datProf.setFecAlt   (rs.getDate   (TablaProfesores.FECHAALTA));
                datProf.setDireccion(rs.getString (TablaProfesores.DIRECCION));
                datProf.setLocalidad(rs.getString (TablaProfesores.LOCALIDAD));
                datProf.setCodProv  (rs.getString (TablaProfesores.CODPROV));
                datProf.setCodPos   (rs.getString (TablaProfesores.CODPOSTAL));
                datProf.setObserv   (rs.getString (TablaProfesores.OBSERVACIONES));
                datProf.setTelef    (rs.getString (TablaProfesores.TELEFONO));
                datProf.setMov      (rs.getString (TablaProfesores.MOVIL));
                datProf.setEmail    (rs.getString (TablaProfesores.MAIL));
                datProf.setActivo   (rs.getBoolean(TablaProfesores.ACTIVO));


                listaProf.addElement(datProf);
            }

            rs.close();
            ps.close();

            return listaProf;
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
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Edita el registro de un profesor
    public static int editaProfesor(ProfesoresVO profVO,Connection con) throws Exception
    {
        PreparedStatement   ps       = null;
        ProfesoresVO        profPrev = new ProfesoresVO();

        int                 regActualizados = 0;

        String              sql = "UPDATE " + TablaProfesores.TABLA + " SET "     +
                                              TablaProfesores.NOMBRE        + " = ? , "   +
                                              TablaProfesores.APELLIDOS     + " = ? , "   +
                                              TablaProfesores.CODTIPDOC     + " = ? , "   +
                                              TablaProfesores.NUMDOC        + " = ? , "   +
                                              TablaProfesores.FECHANAC      + " = ? , "   +
                                              TablaProfesores.FECHAALTA     + " = ? , "   +
                                              TablaProfesores.DIRECCION     + " = ? , "   +
                                              TablaProfesores.LOCALIDAD     + " = ? , "   +
                                              TablaProfesores.CODPROV       + " = ? , "   +
                                              TablaProfesores.CODPOSTAL     + " = ? , "   +
                                              TablaProfesores.OBSERVACIONES + " = ? , "   +
                                              TablaProfesores.TELEFONO      + " = ? , "   +
                                              TablaProfesores.MOVIL         + " = ? , "   +
                                              TablaProfesores.MAIL          + " = ? , "   +
                                              TablaProfesores.ACTIVO        + " = ?   "   +
                                  " WHERE " + TablaProfesores.CODPROFESOR   + " = ?";

        profPrev = ProfesoresGestion.devolverDatosProfesor(profVO.getIdProf());

        if (!profPrev.getNumDoc().trim().equals(profVO.getNumDoc().trim()) && existeDniProfesor(profVO.getNumDoc(),con))
        {
            return -1; //Se comprueba no se duplique el número de documento
        }

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString ( 1, profVO.getNombre());
            ps.setString ( 2, profVO.getApellidos());
            ps.setInt    ( 3, profVO.getCodDoc());
            ps.setString ( 4, profVO.getNumDoc());
            ps.setDate   ( 5, new java.sql.Date(profVO.getFecNac().getTime()));
            ps.setDate   ( 6, new java.sql.Date(profVO.getFecAlt().getTime()));
            ps.setString ( 7, profVO.getDireccion());
            ps.setString ( 8, profVO.getLocalidad());
            ps.setString ( 9, profVO.getCodProv());
            ps.setString (10, profVO.getCodPos());
            ps.setString (11, profVO.getObserv());
            ps.setString (12, profVO.getTelef());
            ps.setString (13, profVO.getMov());
            ps.setString (14, profVO.getEmail());
            ps.setBoolean(15, profVO.isActivo());
            ps.setString (16, profVO.getIdProf());

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
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Borra el registro de un profesor
    //**Retocar
    public static int borraRegProf(String codProf,Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        int               regActualizados = 0;

        String            sql             = "DELETE FROM " + TablaProfesores.TABLA +
                                            " WHERE "      + TablaProfesores.CODPROFESOR + " = ?";


        //Se comprueba que el profesor no esté asociado a ningún módulo
        /*if (AluEdiDAO.devNumMat(codAlu) > 0)
        {
            return -3; //El alumno no se borra porque tiene matrículas
        }
        if (AluEdiDAO.devNumMat(codAlu) == -1)
        {
            return -1; //El profesor no se borra porque no se pudo comprobar si tiene módulos
        }*/

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codProf);

            regActualizados = ps.executeUpdate();

            ps.close();

            //eliminar datos areas de profesor
            //if (AreaProgDAO.borraAreasProf(codProf) >= 0)
            //{

                return regActualizados; //Todo borrado correctamente

            //}
            //else
            //{
            //    return -2; //Datos de area no borrados
            //}

        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Da de alta un nuevo profesor
    public static String insertaProfesor(ProfesoresVO profVO,Connection con) throws Exception
    {
        PreparedStatement ps              = null;

        String            sql             = "INSERT INTO " + TablaProfesores.TABLA + " ("
                                                           + TablaProfesores.CODPROFESOR   + " , "
                                                           + TablaProfesores.NOMBRE        + " , "
                                                           + TablaProfesores.APELLIDOS     + " , "
                                                           + TablaProfesores.CODTIPDOC     + " , "
                                                           + TablaProfesores.NUMDOC        + " , "
                                                           + TablaProfesores.FECHANAC      + " , "
                                                           + TablaProfesores.FECHAALTA     + " , "
                                                           + TablaProfesores.DIRECCION     + " , "
                                                           + TablaProfesores.LOCALIDAD     + " , "
                                                           + TablaProfesores.CODPROV       + " , "
                                                           + TablaProfesores.CODPOSTAL     + " , "
                                                           + TablaProfesores.OBSERVACIONES + " , "
                                                           + TablaProfesores.TELEFONO      + " , "
                                                           + TablaProfesores.MOVIL         + " , "
                                                           + TablaProfesores.MAIL          + " , "
                                                           + TablaProfesores.ACTIVO        + " ) "+

                                            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        int               regActualizados = 0;

        //Se comprueba que no exista el número de documento del profesor en la base de datos
        if (existeDniProfesor(profVO.getNumDoc(),con))
        {
            return "-1";
        }

        try
        {
            ps  = con.prepareStatement(sql);

            profVO.setIdProf(generarNuevoCodProf());
            profVO.setFecAlt(new Date(System.currentTimeMillis()));
            profVO.setActivo(true); //Profesor activo por defecto cuando se da de alta

            //Se pasan los parámetros de la consulta sql
            ps.setString ( 1, profVO.getIdProf());
            ps.setString ( 2, profVO.getNombre());
            ps.setString ( 3, profVO.getApellidos());
            ps.setInt    ( 4, profVO.getCodDoc());
            ps.setString ( 5, profVO.getNumDoc());
            ps.setDate   ( 6, new java.sql.Date(profVO.getFecNac().getTime()));
            ps.setDate   ( 7, new java.sql.Date(profVO.getFecAlt().getTime()));
            ps.setString ( 8, profVO.getDireccion());
            ps.setString ( 9, profVO.getLocalidad());
            ps.setString (10, profVO.getCodProv());
            ps.setString (11, profVO.getCodPos());
            ps.setString (12, profVO.getObserv());
            ps.setString (13, profVO.getTelef());
            ps.setString (14, profVO.getMov());
            ps.setString (15, profVO.getEmail());
            ps.setBoolean(16, profVO.isActivo());


            regActualizados = ps.executeUpdate();

            ps.close();

            if(regActualizados>0)
            {
                return  profVO.getIdProf(); //regActualizados;
            }
            else
            {
                return "0";
            }
        }
        catch (Exception exc)
        {
             try
            {
                ps.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Genera un nuevo código de Profesor
    public static String generarNuevoCodProf()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector datProf    = ProfesoresGestion.devolverTodosProf();

        while (enc)
        {
            contCar = ("" + datProf.size()).length();

            if (contCar > 0)
            {
                codIntrod = "" + (datProf.size() + avc);
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 6)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datProf = ProfesoresGestion.devolverTodosProf();
            for (int ind = 0; ind < datProf.size(); ind++)
            {
                ProfesoresVO profVO = (ProfesoresVO) datProf.elementAt(ind);
                if (profVO.getIdProf().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;
        }
        return codIntrod;
    }

    //Devuelve la provincia del profesor
    public static String devolverNombreProv(String codProv,Connection con) throws Exception
    {
        PreparedStatement ps              = null;
        ResultSet         rs              = null;

        String            sql             = "SELECT " + TablaProvincias.NOMBRE  +
                                            " FROM  " + TablaProvincias.TABLA   +
                                            " WHERE " + TablaProvincias.CODPROV + " = ? ";

        String            nombreProvincia = "";

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codProv);

            rs = ps.executeQuery();

            if (rs.next())
            {
                nombreProvincia = rs.getString(TablaProvincias.NOMBRE);
            }

            rs.close();
            ps.close();

            return nombreProvincia;
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
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }

    //Método que devuelve si ya existe el dato identificativo de un alumno
    public static boolean existeDniProfesor(String numDoc,Connection con) throws Exception
    {
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        String            sql          = "SELECT " + TablaProfesores.CODPROFESOR +
                                         " FROM  " + TablaProfesores.TABLA       +
                                         " WHERE " + TablaProfesores.NUMDOC      + " = ? ";

        boolean          existeDNI     = false;

        //Se comprueba que el número de documento no sea una cadena vacía
        if (numDoc.trim().equals(""))
        {
            return false;
        }

        try
        {
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1,numDoc);

            rs = ps.executeQuery();

            if (rs.next())
            {
                existeDNI = true;
            }
            else
            {
                existeDNI = false;
            }

            rs.close();
            ps.close();

            return existeDNI;

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
                Logger.getLogger(ProfesoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            throw exc;
        }
    }
}
