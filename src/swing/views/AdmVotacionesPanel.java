package swing.views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import dao.IFestivalDAO.TipoConsulta;
import datos.Votacion;

import net.miginfocom.swing.MigLayout;
import proceso.AppController;
import swing.components.FestivalLabel;
import swing.editors.FestivalEditor;
import swing.editors.VotacionTableModel;
import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;
import swing.forms.VotacionForm;
/**
 * Panel de administración de votaciones
 * @author Ruben
 *
 */
public class AdmVotacionesPanel extends FestivalAppPanel 
{
	/**
	 * Editor de votaciones
	 */
	private FestivalEditor<Votacion> editorVotaciones;
	/**
	 * Botón para abrir/cerrar votaciones y establecer la votación activa
	 */
	private JButton btnCambiarEstado = new JButton("Finalizar votacion");
	/**
	 * Votacion seleccionada
	 */
	private Votacion votacionActual = null;
	/**
	 * Asigna el texto del botón btnCambiarEstado
	 */
	private FormChangeListener<Votacion> changeListener = new FormChangeListener<Votacion>() {

		@Override
		public void FormChanged(FormChangeEventHandler<Votacion> handler) 
		{
			votacionActual = handler.getValue();
			if(votacionActual == null)
				btnCambiarEstado.setEnabled(false);
			else
			{
				if(votacionActual.isVotacionActiva())
				{
					if(votacionActual.isPermitirVotos())
						btnCambiarEstado.setText("Cerrar votación");
					else
						btnCambiarEstado.setText("Abrir votación");
				}
				else
				{
					btnCambiarEstado.setText("Estrablecer como votación actual");
				}
				btnCambiarEstado.setEnabled(true);
			}
		}
	};
	public AdmVotacionesPanel(AppController controller, FestivalAppPanel panelPrevio)
	{
		setAppController(controller);
		setPanelPrevio(panelPrevio);
		editorVotaciones = new FestivalEditor<Votacion>(getAppController(), new VotacionForm(controller, panelPrevio), new VotacionTableModel(getAppController()));
		setName("Votaciones");
		setOpaque(false);
		setLayout(new MigLayout("","","15"));
		add(new FestivalLabel("Votaciones registradas", Color.white, 20f),"span, wrap");
		add(editorVotaciones,"span, growx");
		add(btnCambiarEstado, "span, growx");
		btnCambiarEstado.setEnabled(false);
		editorVotaciones.addChangeListener(changeListener);
		btnCambiarEstado.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(votacionActual != null)
				{
					if(votacionActual.isVotacionActiva())
					{
						if(votacionActual.isPermitirVotos())
						{
							votacionActual.setPermitirVotos(false);
							getAppController().getCapaDAO().setVotacion(votacionActual, TipoConsulta.modificar);
						}
						else
						{
							votacionActual.setPermitirVotos(true);
							getAppController().getCapaDAO().setVotacion(votacionActual, TipoConsulta.modificar);
						}
					}
					else
					{
						getAppController().getCapaDAO().setVotacionActiva(votacionActual);
					}
				}
				btnCambiarEstado.setEnabled(false);
				editorVotaciones.updateData();
			}
		});
	}
}
