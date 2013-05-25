package swing.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import proceso.AppController;

/**
 * Botón utilizado en los formularios que permiten navegar al panel anterior
 * @author Ruben
 *
 */
public class BackButtonNavgationListener implements ActionListener {

	public BackButtonNavgationListener(AppController controller, FestivalAppPanel panelPrevio) 
	{
		this.appController = controller;
		this.panelPrevio = panelPrevio;
	}
	
	private AppController appController;
	private FestivalAppPanel panelPrevio;
	
	public AppController getAppController() {
		return appController;
	}
	public void setAppController(AppController appController) {
		this.appController = appController;
	}
	public FestivalAppPanel getPanelPrevio() {
		return panelPrevio;
	}
	public void setPanelPrevio(FestivalAppPanel panelPrevio) {
		this.panelPrevio = panelPrevio;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		appController.getMainView().setPanelActivo(panelPrevio);
	}

}
