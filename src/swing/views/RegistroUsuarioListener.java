package swing.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.IFestivalDAO.TipoConsulta;
import datos.Usuario;

import swing.forms.RegistroUsuarioForm;

/**
 * Listener que abre el formulario de registro de Usuarios
 * @author Ruben
 *
 */
public class RegistroUsuarioListener implements ActionListener {

	public RegistroUsuarioListener(LoginPanel loginPanel) {
		this.loginPanel = loginPanel;
	}
	
	private LoginPanel loginPanel;
	
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}
	public void setLoginPanel(LoginPanel loginPanel) {
		this.loginPanel = loginPanel;
	}
	/**
	 * Abre el formulario de registro
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		new RegistroUsuarioForm(loginPanel.getAppController(), loginPanel).propmt(TipoConsulta.insertar, new Usuario());
	}

}
