package datos;

import dao.DBObject;

/**
 * Representa a un usuario en la aplicación
 * @author  Rubén
 */
public class Usuario extends DBObject
{
	/**
	 * Crea un usuario sin ningun parámetro por defecto
	 */
	public Usuario()
	{
		
	}
	/**
	 * Crea un usuario a partir de éstos parámetros	
	 * @param login El nombre de usuario
	 * @param password La contraseña
	 * @param tipoUsuario El tipo de usuario
	 */
	public Usuario(String login, String password, ETipoUsuario tipoUsuario) {
		this.login = login;
		this.password = password;
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * Crea un usuario a partir de éstos parámetros
	 * @param login El nombre de usuario
	 * @param password La contraseña
	 * @param dni El DNI
	 * @param tipoUsuario El tipo de usuario
	 */
	public Usuario(String login, String password, Dni dni,
			ETipoUsuario tipoUsuario) {
		this.login = login;
		this.password = password;
		this.dni = dni;
		this.tipoUsuario = tipoUsuario;
	}



	/**
	 * Nombre de usuario, debe tener más de 4 carácteres
	 * @uml.property  name="login"
	 */
	private String login;
	/**
	 * Contraseña, debe tener más de 4 carácteres
	 * @uml.property  name="password"
	 */
	private String password;
	/**
	 * El DNI del usuario
	 * @uml.property  name="dni"
	 */
	private Dni dni;
	/**
	 * El tipo de usuario
	 * @uml.property  name="tipoUsuario"
	 * @uml.associationEnd  
	 */
	private ETipoUsuario tipoUsuario;
	
	/**
	 * Getter de la propiedad login
	 * @uml.property  name="login"
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * Setter de la propiedad login
	 * @uml.property  name="login"
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * Getter de la propiedad password
	 * @uml.property  name="password"
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Setter de la propiedad password
	 * @uml.property  name="password"
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Getter de la propiedad dni
	 * @uml.property  name="dni"
	 */
	public Dni getDni() {
		return dni;
	}
	/**
	 * Setter de la propiedad dni
	 * @uml.property  name="dni"
	 */
	public void setDni(Dni dni) {
		this.dni = dni;
	}
	/**
	 * Getter de la propiedad tipoUsuario
	 * @uml.property  name="tipoUsuario"
	 */
	public ETipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	/**
	 * Setter de la propiedad tipoUsuario
	 * @uml.property  name="tipoUsuario"
	 */
	public void setTipoUsuario(ETipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	
}
