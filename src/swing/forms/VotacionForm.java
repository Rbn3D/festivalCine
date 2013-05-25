package swing.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import proceso.AppController;
import swing.components.FestivalLabel;
import swing.editors.SelectPeliculaTableModel;
import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;
import swing.views.FestivalAppPanel;
import datos.Pelicula;
import datos.Votacion;
/**
 * Formulario de insercción/modificación de votaciones
 * @author Ruben
 *
 */
public class VotacionForm extends NavigableFormOf<Votacion> 
{
	/**
	 * Campo de nombre de la votación
	 */
	private JTextField txtNombre = new JTextField();
	/**
	 * Checkbox que indica si la votación debe ser marcada como la votación por defecto automáticamente
	 */
	private JCheckBox chkActiva = new JCheckBox("Activar votación (la votación se convertirá en la votación actual automáticamente)", true);
	/**
	 * Tabla donde han de elegirse las películas a concurso
	 */
	private JXTable peliculasSelectTable;
	/**
	 * ScrollPanel para la tabla de películas
	 */
	private JScrollPane tableScroll;
	/**
	 * Panel principal
	 */
	private JPanel pnl = new JPanel(new MigLayout("fillx","150[]150","30"));
	/**
	 * Valor actual
	 */
	private Votacion value = null;
	/**
	 * TableModel para mostrar las películas a concurso
	 */
	private SelectPeliculaTableModel tModel;
	
	public VotacionForm(AppController controller, FestivalAppPanel panelPrevio) {
		super(controller, panelPrevio);
		chkActiva.setOpaque(false);
		tModel = new SelectPeliculaTableModel(controller);
		peliculasSelectTable = new JXTable(tModel);
		peliculasSelectTable.setSortable(true);
		peliculasSelectTable.addHighlighter(HighlighterFactory.createSimpleStriping());
		peliculasSelectTable.setShowGrid(false, true);
		tableScroll = new JScrollPane(peliculasSelectTable);
		tableScroll.setMinimumSize(new Dimension(tableScroll.getMinimumSize().width, 300));
		this.setPanelPrincipal(pnl);
		pnl.add(new FestivalLabel("Votación", Color.white, 50f), "span, growx, wrap");
		pnl.add(new JLabel("Nombre"), "gap para");
		pnl.add(txtNombre, "span, growx, wrap");
		pnl.add(new JLabel("Películas a concurso"), "gap para");
		pnl.add(tableScroll, "span, growx, wrap");
		pnl.add(new JLabel("Activar votación automáticamente"), "gap para");
		pnl.add(chkActiva, "span, growx, wrap");
		pnl.add(pnlErrors, "span, growx");
		FormUtilities.fixForm(pnl);
		getActionButton().setText("Registrar");
		getActionButton().addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				procesarRegistro();
			}
		});
	}

	protected void procesarRegistro() 
	{
		Votacion v = getValue();
		boolean valido = isValid(v);
		if(valido)
		{
			String msg = null;
			getAppController().getCapaDAO().set(v, tipoConsulta);
			switch (tipoConsulta) {
			case insertar:
					msg = "Votacion insertada con éxito.";
				break;
			case modificar:
					msg = "Votación modificada con éxito";
				break;
			}
			if(v.isVotacionActiva())
				getAppController().getCapaDAO().setVotacionActiva(v);
			JOptionPane.showMessageDialog(this, msg);
			getBackButton().doClick(); // volver al área de login
		}
		else
			UpdateErrorLabels();
		
		fireFormListener(new FormChangeEventHandler<Votacion>(this, v, valido));
	}

	@Override
	public Votacion getValue() 
	{
		if(value == null)
			value = new Votacion();
		value.setNombre(txtNombre.getText());
		value.setPeliculas(tModel.getSelected());
		value.setVotacionActiva(chkActiva.isSelected());
		value.setPermitirVotos(chkActiva.isSelected());
		value.setFechaCreacion(new Date(Calendar.getInstance().getTimeInMillis()));
		return value;
	}

	@Override
	public void setValue(Votacion vot) 
	{
		if(vot == null) vot = new Votacion();
		value = vot;
		txtNombre.setText(vot.getNombre());
		tModel.setSelected(vot.getPeliculas());
		chkActiva.setSelected(vot.isVotacionActiva());
	}

	@Override
	public boolean isValid(Votacion value) 
	{
		boolean valido = true;
		errors.clear();
		if(value == null)
			valido = false;
		else
		{
			if(value.getNombre() == null || value.getNombre().trim().length() == 0)
			{
				valido = false;
				errors.add("Debe especificar un nombre para la votación.");
			}
			else
			{
				if(value.getNombre().trim().length() < 4)
				{
					valido = false;
					errors.add("El nombre debe tener un mínimo de cuatro carácteres.");
				}
				else if(tipoConsulta == tipoConsulta.insertar && getAppController().getCapaDAO().existeVotacion(value.getNombre()))
				{
					valido = false;
					errors.add("Ya existe una votación con el mismo nombre, especifique otro.");
				}
			}
			if(value.getPeliculas() == null || value.getPeliculas().size() < 10)
			{
				valido = false;
				errors.add("Debe elegir un mínimo de 10 películas.");
			}
		}
		return valido;
	}

	@Override
	protected void fireFormListener(FormChangeEventHandler<Votacion> handler) 
	{
		for(FormChangeListener<Votacion> lst : formListeners)
			lst.FormChanged(handler);
	}

}
