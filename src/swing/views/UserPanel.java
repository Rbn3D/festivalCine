package swing.views;


import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.jdesktop.swingx.JXTable;

import net.miginfocom.swing.MigLayout;

import dao.IFestivalDAO.TipoConsulta;
import datos.*;
import proceso.AppController;
import swing.components.FestivalLabel;
import swing.editors.*;

/**
 * Formulario de usuario, donde se vota y se ven los resultados
 * @author Ruben
 *
 */
public class UserPanel extends FestivalAppPanel 
{
	private Votacion votacionActual;
	private String titulo;
	private JXTable tbl;
	private VotarTableModel mdl;
	private JScrollPane scroll;
	private FestivalLabel lblTit = new FestivalLabel("", Color.white, 40f);
	private FestivalLabel lblDetalles = new FestivalLabel("", Color.white, 20f);
	private JButton btnVotar = new JButton("¡Votar ahora!");
	private JButton btnUpdate = new JButton("Refrescar");
	public UserPanel(AppController controller)
	{
		setAppController(controller);
		votacionActual = getAppController().getCapaDAO().getVotacionActual();
		tbl = new JXTable(mdl = new VotarTableModel(controller, votacionActual));
		tbl.setDefaultEditor(Integer.class, new VotacionCellEditor());
		scroll = new JScrollPane(tbl);
		scroll.setMinimumSize(new Dimension(scroll.getMinimumSize().width, 250));
		setLayout(new MigLayout("fillx","150[]150","30"));
		inicializar();
		btnVotar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				procesarVotacion();
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inicializar();
			}
		});
	}
	/**
	 * Se procesa la votación.
	 * En caso de que no se hayan valorado todas las películas se avisa al usuario.
	 */
	private void procesarVotacion() 
	{
		ArrayList<Puntuacion> p = mdl.getPuntuaciones();
		boolean completo = true;
		
		for(Puntuacion punt : p)
			if(punt.getPuntuacion() == null)
				completo = false;
		if(!completo)
			JOptionPane.showMessageDialog(getAppController().getMainView(), "¡Debe dar una puntuación a todas las películas en concurso!");
		else
		{
			votacionActual.getResultado().puntuar(getAppController().getUsuarioActual(), p);
			getAppController().getCapaDAO().setPuntuacion(votacionActual, votacionActual.getResultado().getPuntuaciones());
			JOptionPane.showMessageDialog(getAppController().getMainView(), "¡Votación registrada con éxito! Debes esperar a que la votacion termine para ver los resultados.");
			inicializar();
		}
	}
	
	/**
	 * Inicializa (o actualiza) el panel, dependiendo de cual sea la votación abierta 
	 * y de si el usuario ha votado o no, el panel será distinto
	 */
	private void inicializar() 
	{
		votacionActual = getAppController().getCapaDAO().getVotacionActual();
		this.removeAll();
		add(lblTit, "span, growx, wrap");
		add(lblDetalles, "span, growx, wrap");
		if(votacionActual == null)
		{
			lblTit.setText("Ninguna votación abierta");
			lblDetalles.setText("En este momento ninguna de las votaciones están abiertas. Prueba de nuevo en unos minutos.");
			add(btnUpdate, "wrap");
		}
		else if(!votacionActual.isPermitirVotos())
		{
			lblTit.setText("Votación Actual: " + votacionActual.getNombre());
			lblDetalles.setText("La votación está cerrada, colsulta los resultados:");
			mostrarResultados();
		}
		else if(votacionActual.haVotado(getAppController().getUsuarioActual()))
		{
			lblTit.setText("Votación Actual: " + votacionActual.getNombre());
			lblDetalles.setText("Ya has votado, debes esperar a que la votación termine para ver los resultados.");
			add(btnUpdate, "wrap");
		}
		else
		{
			lblTit.setText("Votación Actual: " + votacionActual.getNombre());
			lblDetalles.setText("Aún no has votado, ¡Hazlo ahora!");
			add(scroll, "span, growx, wrap");
			add(btnVotar, "span, growx, wrap");
			mdl.updateData();
		}
	}
	/**
	 * Muestra los resultados de la votación del usuario actual.
	 */
	private void mostrarResultados() 
	{
		JXTable tblResJurado = new JXTable(new ResultadoTableModel(getAppController(), votacionActual, ETipoUsuario.jurado));
		JXTable tblResEspectador = new JXTable(new ResultadoTableModel(getAppController(), votacionActual, ETipoUsuario.espectador));
		JScrollPane scrollEsp = new JScrollPane(tblResEspectador);
		JScrollPane scrollJur = new JScrollPane(tblResJurado);
		Dimension minumunSize = new Dimension(scrollEsp.getMinimumSize().width, 250);
		scrollEsp.setMinimumSize(minumunSize);
		scrollJur.setMinimumSize(minumunSize);
		add(new FestivalLabel("Resultado: Miembros del jurado:", Color.white, 25f), "span, growx, wrap");
		add(scrollJur, "span, growx, wrap");
		add(new FestivalLabel("Resultado: Espectadores:", Color.white, 25f), "span, growx, wrap");
		add(scrollEsp, "span, growx, wrap");
	}
}
