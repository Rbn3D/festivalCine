package swing.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import net.miginfocom.swing.MigLayout;

import dao.DBObject;
import dao.IFestivalDAO.TipoConsulta;

import proceso.*;
import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;
import swing.views.BackButtonNavgationListener;
import swing.views.FestivalAppPanel;

/**
 * Implementación Basica de IFormOf<B> para JPanels
 * @author Ruben
 *
 * @param <B> El tipo a representar por el formulario (debe ser un DBObject)
 */
public abstract class NavigableFormOf<B extends DBObject> extends FestivalAppPanel implements IFormOf<B>
{
	public NavigableFormOf(AppController controller, FestivalAppPanel panelPrevio)
	{
		this.setAppController(controller);
		this.panelPrevio = panelPrevio;
		pnlErrors = new JPanel(new MigLayout("fillx","[]","30"));
		pnlErrors.setOpaque(false);
		setLayout(new BorderLayout(5,5));
		JPanel pnl = new JPanel();
		pnl.setOpaque(false);
		pnl.setLayout(new GridLayout(1,2,20,20));
		pnl.add(backButton);
		pnl.add(actionButton);
		add(pnl, BorderLayout.SOUTH);
		backButton.addActionListener(new BackButtonNavgationListener(controller, panelPrevio));
	}
	
	/**
	 * Panel dedicado a mostrar la lista de errores del formulario cuando
	 * los datos introducidos por el usuario no son válidos
	 */
	protected JPanel pnlErrors;
	/**
	 * Lista de errores
	 */
	protected ArrayList<String> errors = new ArrayList<String>();
	/**
	 * Escuchadores del evento de formulario cambiado
	 */
	protected ArrayList<FormChangeListener<B>> formListeners = new ArrayList<FormChangeListener<B>>();
	/**
	 * Tipo de dato representado
	 */
	private B tipo;
	/**
	 * Último id del dato representado
	 */
	protected long ultimoId;
	/**
	 * Tipo de acción del formulario
	 */
	protected TipoConsulta tipoConsulta = TipoConsulta.insertar;
	/**
	 * Panel principal del formulario
	 */
	private JPanel panelPrincipal;
	/**
	 * Botón para volver al panel anterior
	 */
	private JButton backButton = new JButton("Atrás");
	/**
	 * Botón de acción (Insertar/Modificar)
	 */
	private JButton actionButton = new JButton("Continuar");
	
	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}
	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}
	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}
	public void setPanelPrincipal(JPanel panelPrincipal) {
		this.panelPrincipal = panelPrincipal;
		add(panelPrincipal, BorderLayout.CENTER);
	}
	public JButton getBackButton() {
		return backButton;
	}
	public JButton getActionButton() {
		return actionButton;
	}
	
	@Override
	public void propmt(TipoConsulta tipo, B valorIncial) 
	{
		errors.clear();
		UpdateErrorLabels();
		setTipoConsulta(tipo);
		getAppController().getMainView().setPanelActivo(this);
		setValue(valorIncial);
	}
	
	/**
	 * Actualiza los mensajes de error del formulario
	 */
	protected void UpdateErrorLabels() 
	{
		pnlErrors.removeAll();
		for(String err : errors)
		{
			JLabel lblErr = new JLabel(err);
			lblErr.setForeground(Color.RED.darker());
			pnlErrors.add(lblErr,"wrap");
		}
		pnlErrors.revalidate();
		pnlErrors.repaint();
	}
	
	/**
	 * Dispara el evento de datos de formulario modificados
	 * @param handler Event handler conteniendo el valor modificado
	 */
	protected abstract void fireFormListener(FormChangeEventHandler<B> handler);
	
	@Override
	public void addFormListener(FormChangeListener<B> l) 
	{
		formListeners.add(l);
	}
	@Override
	public void removeFormListener(FormChangeListener<B> l) 
	{
		formListeners.remove(l);
	}
}
