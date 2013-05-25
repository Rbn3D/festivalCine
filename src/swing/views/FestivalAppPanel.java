package swing.views;

import proceso.AppController;
import swing.components.FestivalPanel;

/**
 * Panel base de la aplicación. Contiene un puntero al panel anterior para facilitar la navegación entre vistas
 * @author Ruben
 *
 */
public class FestivalAppPanel extends FestivalPanel 
{
	/**
	 * Controlador de aplicación
	 */
	protected AppController appController;
	/**
	 * Panel previo
	 */
	protected FestivalAppPanel panelPrevio = null;
	
	public FestivalAppPanel getPanelPrevio() {
		return panelPrevio;
	}
	public void setPanelPrevio(FestivalAppPanel panelPrevio) {
		this.panelPrevio = panelPrevio;
	}
	
	public AppController getAppController() {
		return appController;
	}
	public void setAppController(AppController appController) {
		this.appController = appController;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
