package swing.forms;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import proceso.AppController;

import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;
import swing.views.FestivalAppPanel;
import dao.IFestivalDAO.TipoConsulta;
import datos.Dni;
import datos.Usuario;
/**
 * Formulario de edición de DNIs, implementado con diálogos de en entrada 
 * en lugar de paneles (ya que solo tenemos 1 dato a editar)
 * @author Ruben
 *
 */
public class DniPrompt implements IFormOf<Dni> {

	public DniPrompt(AppController appController) 
	{
		setAppController(appController);
	}
	
	
	/**
	 * Mensaje del diálogo
	 */
	private String defaultText = "Introduzca DNI";
	/**
	 * Objeto dni que encapsula el formulario
	 */
	private Dni value;
	/**
	 * Controlador de aplicación
	 */
	private AppController appController;
	/**
	 * Listeners al evento de formulario modificado
	 */
	private ArrayList<FormChangeListener<Dni>> formListeners = new ArrayList<FormChangeListener<Dni>>();
	private TipoConsulta tipoConsulta = TipoConsulta.insertar;
	
	public AppController getAppController() {
		return appController;
	}
	public void setAppController(AppController appController) {
		this.appController = appController;
	}
	
	@Override
	public void propmt(TipoConsulta tipo, Dni valorInicial) 
	{
		tipoConsulta = tipo;
		setValue(valorInicial);
		iniciarDialogo();
	}

	/**
	 * Inicializa el dialogo
	 */
	private void iniciarDialogo() 
	{
		String prop = null;
    	switch (tipoConsulta) {
		case insertar:
			prop = JOptionPane.showInputDialog("Introduzca DNI");
			while(((!FormUtilities.isValidDni(new Dni(prop))) || getAppController().getCapaDAO().existeDNI(prop)) && prop != null)
			{
				if(getAppController().getCapaDAO().existeDNI(prop))
					prop = JOptionPane.showInputDialog("El DNI ya existe. Introduzca otro.", prop);
				else
					prop = JOptionPane.showInputDialog("DNI inválido. Introduzca un DNI válido.", prop);
			}
			break;
		case modificar:
			prop = JOptionPane.showInputDialog("Introduzca DNI", value.getValue());
			while(!FormUtilities.isValidDni(new Dni(prop)) && prop != null)
			{
				prop = JOptionPane.showInputDialog("DNI inválido. Introduzca un DNI válido.", prop);
			}
			break;
    	}
		if(prop != null)
		{
			getValue().setValue(prop);
			getAppController().getCapaDAO().setDni(getValue(), tipoConsulta);
		}
		fireFormListener(new FormChangeEventHandler<Dni>(DniPrompt.this, getValue(), true));
	}
	@Override
	public void addFormListener(FormChangeListener<Dni> l) 
	{
		formListeners.add(l);
	}

	@Override
	public void removeFormListener(FormChangeListener<Dni> l) 
	{
		formListeners.remove(l);
	}

	@Override
	public Dni getValue() 
	{
		if(value == null) value = new Dni();
		return value;
	}

	@Override
	public void setValue(Dni value) 
	{
		this.value = value;
	}

	@Override
	public boolean isValid(Dni value) {
		return getValue().isValid();
	}
	
	/**
	 * Dispara el evento de formulario modificado
	 * @param handler Event handler
	 */
	protected void fireFormListener(FormChangeEventHandler<Dni> handler) 
	{
		for(FormChangeListener<Dni> lst : formListeners)
			lst.FormChanged((FormChangeEventHandler<Dni>) handler);
	}

}
