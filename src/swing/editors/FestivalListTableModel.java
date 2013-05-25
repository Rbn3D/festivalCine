package swing.editors;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.formbuilder.annotations.UIHidden;
import org.formbuilder.annotations.UITitle;
import org.jdesktop.swingx.treetable.TreeTableModel;

import proceso.AppController;

import dao.DBObject;
import dao.IFestivalDAO;
import datos.*;

/**
 * Clase base para los tableModels de multiples elementos en la aplicación
 * @author Ruben
 *
 * @param <T> El tipo a representar (debe ser un DBObject)
 */
public abstract class FestivalListTableModel<T extends DBObject> extends AbstractTableModel
{
	/**
	 * Tipo representado
	 */
	protected T tipo;
	/**
	 * Los datos a representar
	 */
	protected ArrayList<T> data;
	/**
	 * Controlador de aplicación
	 */
	protected AppController appController;
	/**
	 * Getter de data
	 */
	public ArrayList<T> getData() {
		return data;
	}
	/**
	 * Getter de appController
	 */
	public AppController getAppController() {
		return appController;
	}
	/**
	 * Setter de appController
	 */
	public void setAppController(AppController appController) {
		this.appController = appController;
	}
	
	/**
	 * Actualiza los datos (usando la capaDAO) y dispara el evento de actualizar la tabla
	 */
	public abstract void updateData();
}
