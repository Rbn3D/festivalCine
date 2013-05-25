package swing.forms;

import org.formbuilder.annotations.UITitle;

/**
 * Encapsula los datos de un login
 * @author Ruben
 *
 */
public class LoginBean 
{
	/**
	 * Nombre de usuario
	 */
	private String nombre;
	/**
	 * Contraseña
	 */
	private String password;
	
	@UITitle(value="Nombre de usuario")
	public String getNombre() {
		return nombre;
	}
	@UITitle(value="Contraseña")
	public String getPassword() {
		return password;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
