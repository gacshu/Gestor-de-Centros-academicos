package es.jahernandez.datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* Esta clase realiza la conexión a una base de datos ODBC  mediante el driver puente JDBC-ODBC.
* Tiene los m�todos de conexi�n y desconexi�n.<br>
* Ejemplo:<br>
* <pre>
* try {
*     Connection con = Conexion.conectar();
*     ...
*     // Codigo de utilizaci�n de la base de datos
*     ...
*     Conexion.desconectar(con);
* } catch(java.lang.ClassNotFoundException e) {
*      // Manejo de excepci�n
* } catch (java.sql.SQLException e) {
*      // Manejo de excepci�n
* }	
* </pre>
* Fecha: 7/11/2002
* @author Juan Alberto Hern�ndez Alvarado
* @see java.sql.Connection
*/
public class Conexion 
{

    /*private static final String dsn  = "gestcenacad";
    private static final String url  = "localhost";
    //private static final String user = "admin";
    //private static final String pwd  = "admin";

    private static final String user = "admi";
    private static final String pwd  = "181654";
      */  
               

/**
* Este m�todo realiza una conexi�n a la base de datos.
* @return Connection Devuelve un objeto <code>Connection</code> con una conexi�n abierta con la base
* de datos, en caso de fallar devolver� null.
* @exception java.lang.ClassNotFoundException Si no se encuentra el driver arroja esta excepci�n.
* @exception java.sql.SQLException Si se produce alg�n error sql se arroja esta excepci�n.
*/	
    public static Connection conectarAccess() throws java.sql.SQLException, java.lang.ClassNotFoundException 
    {
		Connection con = null;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con = DriverManager.getConnection("jdbc:odbc:"+ InformacionConf.dsn ,InformacionConf.user, InformacionConf.pwd);
		return con;		
    }

  
  
  
    public static Connection conectar() throws java.sql.SQLException, java.lang.ClassNotFoundException 
     {
		Connection con = null;
		//Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://" + InformacionConf.url +"/"+ InformacionConf.dsn,InformacionConf.user, InformacionConf.pwd);
                
		return con;		
     }

/**
* Este m�todo realiza la desconexi�n de la base de datos.
* @exception java.sql.SQLException Arroja esta excepci�n si se produce alg�n error sql durante la desconexi�n.
*/	
    public static void desconectar(Connection con) throws java.sql.SQLException 
    {
            if(con!=null)
            con.close();
    }
}
