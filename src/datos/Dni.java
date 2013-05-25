package datos;

import swing.forms.FormUtilities;
import dao.DBObject;

/**
 * Representa un DNI en la aplicación
 * @author Ruben
 *
 */
public class Dni extends DBObject 
{
	/**
	 * Construye un DNI sin valor por defecto
	 */
	public Dni() 
	{
	}
	
	/**
	 * Construye un DNI a partir del parametro value
	 * @param value Un string con el DNI, no necesariamente válido
	 */
	public Dni(String value)
	{
		this.setValue(value);
	}
	
	/**
	 * El valor del DNI
	 */
	private String value;
	
	/**
	 * Getter del valor del DNI
	 * @return El valor de la propiedad value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Setter del valor del DNI
	 * @param value El Dni a asignar, no necesariamente válido
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Indica si el valor del DNI es válido
	 * @return True si es un DNI válido, false en caso contrario
	 */
	public boolean isValid()
	{
		return FormUtilities.isValidDni(this);
	}
	
	/**
	 * Indica si la letra del valor del DNI es válida
	 * @return True si la letra es válida, false en caso de que no lo sea o el DNI esté mal formado
	 */
	public boolean isValidLetra()
	{
		return FormUtilities.isValidLetraDNI(this);
	}
	
	/**
	 * Devuelve el valor del dni
	 */
	@Override
	public String toString() {
		return value;
	}
}
