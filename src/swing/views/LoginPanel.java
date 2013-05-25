package swing.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import sun.awt.image.ImageAccessException;
import swing.components.*;
import swing.forms.*;
import proceso.*;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import org.formbuilder.*;
import org.formbuilder.mapping.typemapper.impl.StringToTextFieldMapper;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import net.miginfocom.swing.MigLayout;
/**
 * Panel de login de la aplicación. Es el primer panel mostrado por la aplicación
 * @author Ruben
 *
 */
public class LoginPanel extends FestivalAppPanel 
{
	/**
	 * Label de titulo
	 */
	private FestivalLabel lblTitle1 = new FestivalLabel("Festival de Cine", Color.white, 50f);
	/**
	 * Lanel de iniciar sesión
	 */
	private FestivalLabel lblTitle2 = new FestivalLabel("Iniciar sesión", Color.white, 35f);
	/**
	 * Formulario de login
	 */
	private Form<LoginBean> formLogin = FormBuilder.map(LoginBean.class).useForProperty("password", new StringToPasswordFieldMapper()).buildForm();
	/**
	 * Boton de login
	 */
	private JButton btnLogin;
	/**
	 * Label de errores
	 */
	private JLabel lblErrors = new JLabel();
	/**
	 * Botón de registro
	 */
	private JLinkButton registroButton = new JLinkButton("¿No tienes cuenta? ¡Regístrate!");
	
	public Form<LoginBean> getFormLogin() {
		return formLogin;
	}
	public JLabel getLblErrors() {
		return lblErrors;
	}
	
	
	public LoginPanel(AppController appController)
	{	
		File dir = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		btnLogin = new JButton("Login", new ImageIcon(dir.getParent() + "/res/images/LoginArrow.png"));
		setAppController(appController);
		setLayout(new MigLayout("wrap, fill, gapy 10","[grow,left][grow,center][grow,right]"));
		add(lblTitle1, "cell 1 1,center");
		add(lblTitle2, "cell 1 2,center");
		JComponent form = formLogin.asComponent();
		FormUtilities.fixForm(form);
		add(form, "cell 1 3, center");
		btnLogin.setToolTipText("Login");
		btnLogin.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogin.setOpaque(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(new Color(0,0,0,0));
		btnLogin.addActionListener(new LoginPanelActionListiner(this));
		add(btnLogin, "cell 2 3");
		lblErrors.setForeground(Color.RED.darker());
		add(lblErrors, "cell 1 4");
		registroButton.addActionListener(new RegistroUsuarioListener(this));
		add(registroButton, "cell 1 5");
	}
}
