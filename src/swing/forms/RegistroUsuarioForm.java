package swing.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import dao.IFestivalDAO.TipoConsulta;
import datos.*;
import proceso.AppController;
import swing.components.FestivalLabel;
import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;
import swing.views.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Formulario de registro/modificación de usuario
 * @author Ruben
 *
 */
public class RegistroUsuarioForm extends NavigableFormOf<Usuario>
{
	/**
	 * Valor actual
	 */
	private Usuario value;
	/**
	 * ComboBox con los tipos de usuario
	 */
	private JComboBox<ETipoUsuario> cmbTipoUsuario = new JComboBox<ETipoUsuario>(ETipoUsuario.values());
	/**
	 * TextField con el Login de usuario
	 */
	private JTextField txtLogin = new JTextField();
	/**
	 * Campo de contraseña
	 */
	private JPasswordField txtPass1 = new JPasswordField();
	/**
	 * Campo de repetir contraseña
	 */
	private JPasswordField txtPass2 = new JPasswordField();
	/**
	 * Campo de DNI
	 */
	private JTextField txtDni = new JTextField();
	private JLabel lblDni;
	
	public RegistroUsuarioForm(AppController controller, FestivalAppPanel panelPrevio) 
	{
		super(controller, panelPrevio);
		JPanel pnl = new JPanel(new MigLayout("fillx","150[]150","30"));
		this.setPanelPrincipal(pnl);
		pnl.add(new FestivalLabel("Registro de usuario", Color.white, 50f), "span, growx, wrap");
		pnl.add(new JLabel("Tipo de usuario"), "gap para");
		pnl.add(cmbTipoUsuario, "span, growx, wrap");
		pnl.add(new JLabel("Nombre de Usuario"), "gap para");
		pnl.add(txtLogin, "span, growx, wrap");
		pnl.add(new JLabel("Contraseña"), "gap para");
		pnl.add(txtPass1, "span, growx, wrap");
		pnl.add(new JLabel("Repita contraseña"), "gap para");
		pnl.add(txtPass2, "span, growx, wrap");
		pnl.add(lblDni = new JLabel("DNI"), "gap para");
		pnl.add(txtDni, "span, growx, wrap");
		pnl.add(pnlErrors, "span, growx");
		FormUtilities.fixForm(pnl);
		updateOptionalComps();
		this.getActionButton().setText("Registrar");
		this.getActionButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				procesarRegistro();
			}
		});
		cmbTipoUsuario.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				updateOptionalComps();
			}
		});
		if(!getAppController().isAdmin())
			cmbTipoUsuario.removeItem(ETipoUsuario.administrador);
	}
	
	/**
	 * Procesa la inserción/modificación del usuario.
	 * Si los datos no son válidos, muestra los errores, en caso contrario realiza la
	 * inserción/modifación y cierra el formulario
	 */
	protected void procesarRegistro() 
	{
		boolean valido = false;
		Usuario u = getValue();
		if(isValid(u))
		{
			if(!(txtPass1.getText().equals(txtPass2.getText())))
				errors.add("Las contraseñas no coinciden.");
			else // <- Registro valido
			{
				valido = true;
				getAppController().getCapaDAO().setUsuario(u, getTipoConsulta());
				String respuesta = null;
				switch (getTipoConsulta()) {
				case insertar:
					if(!getAppController().isAdmin())
						respuesta="Usuario registrado con éxito, ya puedes iniciar sesión con tu nuevo usuario.";
					else
						respuesta="Usuario registrado con éxito.";
					break;
				case modificar:
					respuesta="Usuario modificado con éxito.";
					break;
				}
				JOptionPane.showMessageDialog(this, respuesta);
				getBackButton().doClick(); // volver al área de login
			}
		}
		if(!valido)
			UpdateErrorLabels();
		fireFormListener(new FormChangeEventHandler<Usuario>(this, u, valido));
	}

	/**
	 * Muestra el campo de DNI en caso de que el tipo de usuario sea miembro del jurado, lo oculta
	 * en caso contrario
	 */
	private void updateOptionalComps() 
	{
		if(cmbTipoUsuario.getSelectedItem()!=null)
		{
			boolean visible= true;
			switch ((ETipoUsuario)cmbTipoUsuario.getSelectedItem()) {
			case espectador:
				visible = false;
				break;
			case administrador:
				visible = false;
				break;
			case jurado:
				visible=true;
				break;
			}
			lblDni.setVisible(visible);
			txtDni.setVisible(visible);
		}
	}

	@Override
	public Usuario getValue() 
	{
		Usuario u = new Usuario();
		try
		{
			if(value != null)
				u.setId(value.getId());
			u.setLogin(txtLogin.getText().trim());
			u.setPassword(txtPass1.getText().trim());
			u.setTipoUsuario((ETipoUsuario)cmbTipoUsuario.getSelectedItem());
			if(txtDni.getText() != null && txtDni.getText().trim().length() > 0) // Sólo comprobar si hay DNI o no, ya que la validación se hace en el método isValid(Usuario value).
				u.setDni(new Dni(txtDni.getText().trim()));
			return u;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public void setValue(Usuario newValor) 
	{
		this.value = newValor;
		if(newValor == null)
			newValor = new Usuario();
		else
		{
			
		}
		txtLogin.setText(newValor.getLogin());
		txtPass1.setText(newValor.getPassword());
		txtPass2.setText(newValor.getPassword());
		cmbTipoUsuario.setSelectedItem(newValor.getTipoUsuario());
		if(newValor.getDni()!=null)
		txtDni.setText(newValor.getDni().getValue());
	}

	@Override
	public boolean isValid(Usuario value) 
	{
		errors.clear();
		boolean valido = true;
			
		if(value.getLogin() == null || value.getLogin().trim().length() == 0)
		{
			valido = false;
			errors.add("Debe especificar un nombre de usuario que no esté en uso.");
		}
		else
		{
			if(value.getLogin().length() < 4)
			{
				valido = false;
				errors.add("El nombre de usuario debe tener un mínimo de 4 caráctes.");
			}
			else if(tipoConsulta.equals(TipoConsulta.insertar) && getAppController().getCapaDAO().existeUsuario(value.getLogin()))
			{
				valido = false;
				errors.add("El nombre de usuario '"+ value.getLogin() + "' ya está en uso.");
			}
		}
		
		if(value.getPassword() == null || value.getPassword().trim().length() == 0)
		{
			valido = false;
			errors.add("Debe especificar una contraseña.");
		}
		else if(value.getPassword().length() < 4)
		{
			valido = false;
			errors.add("La contraseña debe tener un mínimo de 4 caráctes.");
		}
				
		if(value.getTipoUsuario() == ETipoUsuario.jurado)
		{
			if(value.getDni() == null || value.getDni().getValue() == null || value.getDni().getValue().length() == 0)
			{
				valido = false;
				errors.add("Los miembros del jurado deben proporcionar su DNI, para verificar que son miembros acreditados del jurado.");
			}
			else
			{
				if(!FormUtilities.isValidDni(value.getDni()))
				{
					errors.add("El DNI tiene mal formato.");
					valido = false;
				}
				else
				{
					if(!getAppController().getCapaDAO().existeDNI(value.getDni().getValue()))
					{
						valido = false;
						errors.add("El DNI que ha proporcionado no está en la lista de DNIs acreditados como miembros del jurado.");
					}
				}
			}
		}
		else if(value.getTipoUsuario() == null)
		{
			valido = false;
			errors.add("Especifique el tipo de usuario.");
		}
		return valido;
	}

	@Override
	protected void fireFormListener(FormChangeEventHandler<Usuario> handler) 
	{
		for(FormChangeListener<Usuario> lst : formListeners)
			lst.FormChanged((FormChangeEventHandler<Usuario>) handler);
	}
}
