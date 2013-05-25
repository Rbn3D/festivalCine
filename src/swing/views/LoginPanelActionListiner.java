package swing.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.hibernate.validator.util.privilegedactions.GetAnnotationParameter;

import datos.Usuario;

import swing.forms.LoginBean;

/**
 * Listener que maneja el proceso de login o inicio de sesión
 * @author Ruben
 *
 */
public class LoginPanelActionListiner implements ActionListener {

	public LoginPanelActionListiner(LoginPanel loginPanel) {
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
	 * Maneja el inicio de sesión, si hay errores se los muestra al usuario, y en caso contrario
	 * inicia sesión en la aplicación mostrando al usuario el contenido que corresponde segun el tipo
	 * de usuario que ha iniciado sesión
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		JLabel errorslbl = loginPanel.getLblErrors();
		LoginBean login = loginPanel.getFormLogin().getValue();
		if((login.getNombre() == null || login.getPassword() == null) ||(login.getNombre().trim().isEmpty() || login.getPassword().trim().isEmpty()))
			errorslbl.setText("Debe especificar su nombre de usuario y contraseña para iniciar sesión.");
		else
		{
			Usuario user = loginPanel.getAppController().getCapaDAO().login(login.getNombre(), login.getPassword());
			if(user == null)
				errorslbl.setText("Error al iniciar sesión. Nombre de usuario o contreaseña incorrectos.");
			else // <- login correcto
			{
				loginPanel.getAppController().setUsuarioActual(user); // Asignamos el usuario que trabajará con la aplicación en el Controlador de aplicación
				
				switch (user.getTipoUsuario()) {
				case administrador:
						loginPanel.getAppController().getMainView().setPanelActivo(new AdminPanelPrincipal(loginPanel.getAppController()));
					break;
				default:
						loginPanel.getAppController().getMainView().setPanelActivo(new UserPanel(loginPanel.getAppController()));
					break;
				}
			}
		}
	}

}
