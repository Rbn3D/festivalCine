package proceso;

import controlErrores.ErrorApp;
import swing.views.FestivalUserBar;
import swing.views.FestivalView;
import swing.views.LoginPanel;
import dao.*;
import datos.*;

/**
 * 
 * @author Ruben
 *
 *	Objeto dedicado a contener los objetos usados por toda la aplicación.
 *	Es el objeto principal de la aplicación
 */
public class AppController 
{
	/**
	 * Capa de acceso a datos de la aplicación, puede ser cualquier implementación de IFestivalDAO
	 */
	private IFestivalDAO capaDAO;
	/**
	 * Ventana principal de la aplicación
	 */
	private FestivalView mainView = new FestivalView(this, new LoginPanel(this));
	/**
	 * Usuario actual en la aplicación
	 */
	private Usuario usuarioActual = null;
	
	/**
	 * Inicializa la aplicación y hace visible la interfaz (punto de entrada)
	 * @throws ErrorApp
	 */
	public AppController() throws ErrorApp
	{
		capaDAO = new DAOMySQL();
		mainView.setVisible(true);
	}
	
	/**
	 * Getter de capaDAO
	 */
	public IFestivalDAO getCapaDAO() {
		return capaDAO;
	}
	/**
	 * Setter de capaDAO
	 */
	public void setCapaDAO(IFestivalDAO capaDAO) {
		this.capaDAO = capaDAO;
	}
	/**
	 * Getter de usuarioActual
	 */
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	/**
	 * Setter de usuarioActual. Actualiza automáticamente el UserBar (panel donde se muestra el usuario actual)
	 */
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
		if(usuarioActual == null)
			mainView.setPnlUsuario(null);
		else
			mainView.setPnlUsuario(new FestivalUserBar(this, usuarioActual));
	}
	/**
	 * Getter de mainView
	 */
	public FestivalView getMainView() {
		return mainView;
	}
	/**
	 * Indica si el usuario actual es administrador
	 * @return true si el usuario actual es administrador, false en caso contrario
	 */
	public boolean isAdmin()
	{
		if(getUsuarioActual() == null)
			return false;
		else
			return getUsuarioActual().getTipoUsuario() == ETipoUsuario.administrador;
	}
}
