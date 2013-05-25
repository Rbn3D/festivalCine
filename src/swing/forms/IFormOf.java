package swing.forms;

import java.util.List;

import dao.DBObject;
import dao.IFestivalDAO.TipoConsulta;
import proceso.AppController;
import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;

/**
 * Interfaz destinada a abstraer el comportamiento de un formulario de inserción/edición
 * 
 * @author Ruben
 *
 * @param <B> El tipo de dato del objeto representado por el formulario
 */
public interface IFormOf<B extends DBObject> 
{
	/**
	 * Muestra el formulario al usuario
	 * @param tipo El tipo de operacion (insertar o modificar)
	 * @param valorIncial el valor principal del formulario
	 */
	public void propmt(TipoConsulta tipo, B valorIncial );
	/**
	 * Añade un escuchador al evento de formulario modificado
	 * @param l El escuchador
	 */
	public void addFormListener(FormChangeListener<B>l);
	/**
	 * Elimina un escuchador del evento de formulario modificado
	 * @param l El escuchador
	 */
	public void removeFormListener(FormChangeListener<B> l);
	/**
	 * Valor actual del objeto representado
	 * @return
	 */
	public B getValue();
	/**
	 * Asigna el valor actual, y actualiza el formulario para que muestre el nuevo dato
	 * @param value Nuevo valor
	 */
	public void setValue(B value);
	/**
	 * Comprueba si el valor introducido por el usuario es válido para su inserción / modificación
	 * @param value
	 * @return True si el dato es válido, false en caso contrario
	 */
	public boolean isValid(B value);
}
