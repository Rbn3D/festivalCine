package datos;

/**
 * Representa los tipos de usuario disponibles en la aplicación
 * @author   alumno
 */
public enum ETipoUsuario 
{
	espectador("Espectador"),
	jurado("Miembro del jurado"),
	administrador("Administrador");
	
	/**
	 * Crea un valor interno en el emun
	 * @param nombre El nombre que queremos que tenga el valor en su representación en cadena (toString())
	 */
	private ETipoUsuario(String nombre)
	{
		this.nombre = nombre;
	}
	
	/**
	 * Nombre del valor, para su representación en cadena
	 */
	private String nombre;
	
	@Override
	public String toString() {
		return nombre;
	}
}
