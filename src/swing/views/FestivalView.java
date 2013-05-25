package swing.views;


import java.awt.BorderLayout;
import java.awt.event.WindowListener;

import javax.swing.*;
import proceso.*;
import swing.components.FestivalPanel;

/**
 * 
 * @author Ruben
 *
 * Ventana principal de la aplicación
 *
 */
public class FestivalView extends JFrame 
{
	
	public FestivalView(AppController appController, FestivalAppPanel panelInicial)
	{
		this.appController = appController;
		this.setTitle("Festival de cine");
		getContentPane().setLayout(layout);
		this.setPanelActivo(panelInicial);
		this.pack();
		this.setExtendedState(MAXIMIZED_BOTH); // Maximiza la ventana
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new CloseFestivalListener(this));
	}
	
	/**
	 * Gestor de Layout
	 */
	private BorderLayout layout = new BorderLayout();
	/**
	 * Panel actual activo
	 */
	private FestivalAppPanel panelActivo;
	/**
	 * Panel de usuario
	 */
	private FestivalUserBar pnlUsuario;
	/**
	 * Controlador de aplicación
	 */
	private AppController appController;
	
	public FestivalAppPanel getPanelActivo() {
		return panelActivo;
	}
	/**
	 * Asigna el panel activo y lo muestra al usuario
	 * @param nuevoPanel El panel a mostrar
	 */
	public void setPanelActivo(FestivalAppPanel nuevoPanel) {
		//this.panelActivo = panelActivo;
		if(this.panelActivo !=null)
			getContentPane().remove(this.panelActivo);
		getContentPane().add(nuevoPanel, BorderLayout.CENTER);
		this.panelActivo = nuevoPanel;
		getContentPane().validate();
		getContentPane().repaint();
	}
	public FestivalUserBar getPnlUsuario() {
		return pnlUsuario;
	}
	/**
	 * Asigna el panel de usuario y lo muestra al usuario
	 * @param nuevoPanel El panel de usuario a mostrar
	 */
	public void setPnlUsuario(FestivalUserBar nuevoPanel) {
		if(this.pnlUsuario !=null)
			getContentPane().remove(this.pnlUsuario);
		if(nuevoPanel != null)
		{
			getContentPane().add(nuevoPanel, BorderLayout.NORTH);
			this.pnlUsuario = nuevoPanel;
		}
		getContentPane().validate();
		getContentPane().repaint();
	}
	public AppController getAppController() {
		return appController;
	}
	public void setAppController(AppController appController) {
		this.appController = appController;
	}
	
	
	private static final long serialVersionUID = 1L;
}
