package dao;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class BeanDAO {

	/**
	 * Contiene el nombre de la clase Driver.
	 * @uml.property  name="driver"
	 */
	private String driver = "com.mysql.jdbc.Driver";

	/**
	 * URL base para el acceso a la base de datos
	 * @uml.property  name="urlBase"
	 */
	private String urlBase = "jdbc:mysql://localhost:3306/";

	/**
	 * Nombre de la base datos
	 * @uml.property  name="baseDatos"
	 */
	private String baseDatos = null;

	/**
	 * Nombre del usuario con el que se accederá a la base de datos
	 * @uml.property  name="usuario"
	 */
	private String usuario = null;

	/**
	 * Clave de usuario
	 * @uml.property  name="clave"
	 */
	private String clave = null;

	/**
	 * Representa una conexión la base de datos
	 * @uml.property  name="conexion"
	 */
	private Connection conexion = null;
	
	/**
	 * Construye un bean de DAO, obteniendo automáticamente los datos de conexión
	 * del archivo de propiedades 'bd.properties'
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public BeanDAO() throws IOException, SQLException, ClassNotFoundException{
		Properties props = new Properties();
		props.load(BeanDAO.class.getResourceAsStream("bd.properties"));
		
		this.driver = props.getProperty("bd.driver");
		this.urlBase = props.getProperty("bd.urlBase");
		this.baseDatos = props.getProperty("bd.baseDatos");
		this.usuario = props.getProperty("bd.usuario");
		this.clave = props.getProperty("bd.clave");
		getConexion();
	}
	
	/**
	 * Construye un bean de DAO a partir de driver, url base, 
	 * base de datos, nombre de usuario y clave
	 */
	public BeanDAO(String driver, String urlBase, String baseDatos, String usuario, String clave){
		this.driver = driver;
		this.urlBase = urlBase;
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}

	/**
	 * Construye un bean de DAO a partir de url base, 
	 * base de datos, nombre de usuario y clave
	 */
	public BeanDAO(String urlBase, String baseDatos, String usuario, String clave){
		this.urlBase = urlBase;
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}
				
	/**
	 * Construye un bean de DAO a partir de  
	 * base de datos, nombre de usuario y clave
	 */
	public BeanDAO(String baseDatos, String usuario, String clave){
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}
	/**
	 * Devuelve el objeto Connection asociado al bean
	 * de servicio. Si no existe previamente, lo crea.
	 * @return  Devuelve la conexión.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @uml.property  name="conexion"
	 */
	public Connection getConexion() throws SQLException, ClassNotFoundException {
		if (conexion==null)
			Class.forName(driver);
			conexion = DriverManager.getConnection(urlBase + baseDatos, usuario, clave);
		return conexion;
	}
		
	/**
	 * Cierra la conexión actual.
	 * @return Devuelve true si se ha cerrado correctamente la conexión, false en caso contrario.
	 */
	public boolean close(){
		boolean ok = true;
		if (conexion!=null)
			try {
				conexion.close();
			} catch (SQLException e) {
				ok=false;
			}
		return ok;	
	}
		
	
	/**
	 * Getter of the property <tt>driver</tt>
	 * @return  Returns the driver.
	 * @uml.property  name="driver"
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * Setter of the property <tt>driver</tt>
	 * @param driver  The driver to set.
	 * @uml.property  name="driver"
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * Getter of the property <tt>urlBase</tt>
	 * @return  Returns the urlBase.
	 * @uml.property  name="urlBase"
	 */
	public String getUrlBase() {
		return urlBase;
	}

	/**
	 * Setter of the property <tt>urlBase</tt>
	 * @param urlBase  The urlBase to set.
	 * @uml.property  name="urlBase"
	 */
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	/**
	 * Getter of the property <tt>baseDatos</tt>
	 * @return  Returns the baseDatos.
	 * @uml.property  name="baseDatos"
	 */
	public String getBaseDatos() {
		return baseDatos;
	}

	/**
	 * Setter of the property <tt>baseDatos</tt>
	 * @param baseDatos  The baseDatos to set.
	 * @uml.property  name="baseDatos"
	 */
	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}
	/**
	 * Getter of the property <tt>usuario</tt>
	 * @return  Returns the usuario.
	 * @uml.property  name="usuario"
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Setter of the property <tt>usuario</tt>
	 * @param usuario  The usuario to set.
	 * @uml.property  name="usuario"
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * Getter of the property <tt>clave</tt>
	 * @return  Returns the clave.
	 * @uml.property  name="clave"
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Setter of the property <tt>clave</tt>
	 * @param clave  The clave to set.
	 * @uml.property  name="clave"
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
}
