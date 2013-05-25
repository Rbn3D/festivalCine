package swing.eventos;

import dao.DBObject;
import swing.forms.IFormOf;

/**
 * Evento destinado a notificar los cambios en editores y formularios
 * @author Ruben
 *
 * @param <T>
 */
public class FormChangeEventHandler<T extends DBObject>
{
	public FormChangeEventHandler(IFormOf<T> source, T value,
			boolean needsUpdate) {
		this.source = source;
		this.value = value;
		this.needsUpdate = needsUpdate;
	}
	/**
	 * Objeto que dispara el evento
	 */
	private IFormOf<T> source;
	/**
	 * Nuevo valor del DBObject modificado
	 */
	private T value;
	/**
	 * Booleano indicando si es necesario actualizar las vistas
	 */
	private boolean needsUpdate = false;
	
	public IFormOf<T> getSource() {
		return source;
	}
	public void setSource(IFormOf<T> source) {
		this.source = source;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public boolean isNeedsUpdate() {
		return needsUpdate;
	}
	public void setNeedsUpdate(boolean needsUpdate) {
		this.needsUpdate = needsUpdate;
	}
	
	
}
