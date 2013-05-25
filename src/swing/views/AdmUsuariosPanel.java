package swing.views;

import java.awt.Color;
import java.util.ArrayList;

import datos.Dni;
import datos.ETipoUsuario;
import datos.Usuario;
import net.miginfocom.swing.MigLayout;
import proceso.AppController;
import swing.components.FestivalLabel;
import swing.editors.DniTableModel;
import swing.editors.FestivalEditor;
import swing.editors.UsuarioTableModel;
import swing.forms.DniPrompt;
import swing.forms.RegistroUsuarioForm;
/**
 * Panel de administración de usuarios
 * @author Ruben
 *
 */
public class AdmUsuariosPanel extends FestivalAppPanel 
{
	/**
	 * Editor de usuarios
	 */
	private FestivalEditor<Usuario> editorUsuarios;
	/**
	 * Editor de DNIs
	 */
	private FestivalEditor<Dni> editorDnis;
	public AdmUsuariosPanel(AppController appController, FestivalAppPanel panelPrevio)
	{
		setAppController(appController);
		setPanelPrevio(panelPrevio);
		editorUsuarios = new FestivalEditor<Usuario>(appController, new RegistroUsuarioForm(appController, panelPrevio), new UsuarioTableModel(appController));
		editorDnis = new FestivalEditor<Dni>(appController, new DniPrompt(appController), new DniTableModel(appController));
		setName("Usuarios");
		setOpaque(false);
		setLayout(new MigLayout("","","15"));
		add(new FestivalLabel("Usuarios registrados", Color.white, 20f),"span, wrap");
		add(editorUsuarios,"span, growx");
		add(new FestivalLabel("DNIs registrados", Color.white, 20f),"span, wrap");
		add(editorDnis,"span, growx");
	}
}
