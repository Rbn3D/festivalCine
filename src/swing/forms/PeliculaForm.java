package swing.forms;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import net.miginfocom.swing.MigLayout;
import dao.IFestivalDAO.TipoConsulta;
import datos.Pelicula;
import datos.Usuario;
import proceso.AppController;
import swing.components.FestivalLabel;
import swing.eventos.*;
import swing.views.FestivalAppPanel;

/**
 * Formulario de inserción/Edición de películas
 * @author Ruben
 *
 */
public class PeliculaForm extends NavigableFormOf<Pelicula> 
{	
	/**
	 * Valor actual
	 */
	private Pelicula value;
	/**
	 * Campo de texto con el título de la película
	 */
	private JTextField txtTitulo = new JTextField();
	/**
	 * Campo de texto con el director de la película
	 */
	private JTextField txtDirector = new JTextField();
	/**
	 * Campo de texto multilínea con los actores de la película
	 */
	private JTextArea txtActores = new JTextArea();
	/** 
	 * Panel principal
	 */
	private JPanel pnl = new JPanel(new MigLayout("fillx","150[]150","30"));
	
	public PeliculaForm(AppController controller, FestivalAppPanel panelPrevio) {
		super(controller, panelPrevio);
		this.setPanelPrincipal(pnl);
		pnl.add(new FestivalLabel("Registrar película", Color.white, 50f), "span, growx, wrap");
		pnl.add(new JLabel("Título"), "gap para");
		pnl.add(txtTitulo, "span, growx, wrap");
		pnl.add(new JLabel("Director"), "gap para");
		pnl.add(txtDirector, "span, growx, wrap");
		pnl.add(new JLabel("Actores principales (Escriba uno por línea)"), "gap para");
		pnl.add(txtActores, "span, growx, wrap");
		pnl.add(pnlErrors, "span, growx");
		FormUtilities.fixForm(pnl);
		txtActores.setRows(4);
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

	@Override
	public Pelicula getValue() 
	{
		if(value == null)
			value = new Pelicula();
		value.setTitulo(txtTitulo.getText());
		value.setDirector(txtDirector.getText());
		value.setActoresAsString(txtActores.getText());
		return value;
	}

	@Override
	public void setValue(Pelicula pel) 
	{
		
		if(pel==null) pel = new Pelicula();
		value = pel;
		txtTitulo.setText(value.getTitulo());
		txtDirector.setText(value.getDirector());
		txtActores.setText(value.getActoresAsString());
	}

	/**
	 * Procesa el registro o modificación de la pelicula.
	 * Si los datos son inválidos muestra los errores, en caso contrario realiza el registro/modificación y cierra el formulario
	 */
	protected void procesarRegistro() 
	{
		Pelicula p = getValue();
		boolean valido = isValid(p);
		
		if(valido)
		{
			String msg = null;
			getAppController().getCapaDAO().set(p, tipoConsulta);
			switch (tipoConsulta) {
			case insertar:
					msg = "Pelicula insertada con éxito.";
				break;
			case modificar:
					msg = "Pelicula modificada con éxito";
				break;
			}
			JOptionPane.showMessageDialog(this, msg);
			getBackButton().doClick(); // volver al área de login
		}
		else
			UpdateErrorLabels();
		
		fireFormListener(new FormChangeEventHandler<Pelicula>(this, p, valido));
	}
	
	@Override
	public boolean isValid(Pelicula value) {
		errors.clear();
		boolean valido = true;
		if(getAppController().getCapaDAO().existePelicula(value.getTitulo()) && tipoConsulta == TipoConsulta.insertar)
		{
			valido = false;
			errors.add("Ya existe una película con el mismo título");
		}
		if((value.getTitulo() == null || value.getTitulo().trim().length() < 2))
		{
			valido = false;
			errors.add("Debe especificar un título para la película con al menos 2 carácteres.");
		}
		if((value.getDirector() == null || value.getDirector().trim().length() < 4))
		{
			valido = false;
			errors.add("Debe especificar un director para la película con al menos 4 carácteres.");
		}
		
		return valido;
	}

	@Override
	protected void fireFormListener(FormChangeEventHandler<Pelicula> handler) 
	{
		for(FormChangeListener<Pelicula> f : formListeners)
		{
			f.FormChanged(handler);
		}
	}

}
