package swing.views;

import java.awt.Color;

import datos.Pelicula;
import net.miginfocom.swing.MigLayout;
import proceso.AppController;
import swing.components.FestivalLabel;
import swing.editors.FestivalEditor;
import swing.editors.PeliculaTableModel;
import swing.forms.PeliculaForm;

/**
 * Panel de administración de películas
 * @author Ruben
 *
 */
public class AdmPeliculasPanel extends FestivalAppPanel 
{
	/**
	 * Editor de películas
	 */
	private FestivalEditor<Pelicula> peliculasEditor;
	public AdmPeliculasPanel(AppController appController, FestivalAppPanel panelPrevio)
	{
		setAppController(appController);
		setPanelPrevio(panelPrevio);
		peliculasEditor = new FestivalEditor<Pelicula>(appController, new PeliculaForm(appController, panelPrevio), new PeliculaTableModel(appController));
		setName("Películas");
		setOpaque(false);
		setLayout(new MigLayout("","","15"));
		add(new FestivalLabel("Películas registradas", Color.white, 20f),"span, wrap");
		add(peliculasEditor,"span, growx");
	}
}
