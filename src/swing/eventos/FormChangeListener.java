package swing.eventos;

import java.util.EventListener;

import dao.DBObject;
/**
 * Listener para los eventos de cambios en formularios y editores
 * @author Ruben
 *
 * @param <T>
 */
public interface FormChangeListener<T extends DBObject> extends EventListener 
{
	public void FormChanged(FormChangeEventHandler<T> handler);
}
