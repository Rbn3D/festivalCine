package swing.views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import proceso.AppController;

import swing.components.FestivalLabel;
import swing.components.JLinkButton;

import net.miginfocom.swing.MigLayout;

import datos.Usuario;

/**
 * Panel en el que se muestra el usuario actual (Y se le permite hacer logout),
 * situado en la parter superior de la ventana de la aplicación
 * @author Ruben
 *
 */
public class FestivalUserBar extends JPanel 
{
	/**
	 * Botón para cerrar sesión
	 */
	private JLinkButton btnLoguot = new JLinkButton("Cerrar sesión");
	public FestivalUserBar(AppController controller, Usuario usuarioActual)
	{
		this.setAppController(controller);
		this.usuarioActual = usuarioActual;
		this.setBackground(Color.black);
		this.setLayout(new MigLayout("fillx","[]push[]"));
		this.add(new FestivalLabel("Bienvenido, " + usuarioActual.getLogin(),Color.white, 18f));
		btnLoguot.setForeground(Color.white);
		btnLoguot.setOpaque(false);
		this.add(btnLoguot);
		btnLoguot.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				procesarLogout();
			}
		});
	}
	
	/**
	 * Procesa el cierre de sesión, pidiendo confirmación al usuario
	 */
	protected void procesarLogout() 
	{
		if(JOptionPane.showConfirmDialog(getAppController().getMainView(), "¿Realmente desea cerrar sesión?") == JOptionPane.YES_OPTION)
		{
			getAppController().getMainView().setPanelActivo(new LoginPanel(appController));
			getAppController().setUsuarioActual(null);
		}
	}

	/**
	 * Controlador de aplicación
	 */
	private AppController appController;
	
	public AppController getAppController() {
		return appController;
	}
	public void setAppController(AppController appController) {
		this.appController = appController;
	}
	private Usuario usuarioActual;
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
}
