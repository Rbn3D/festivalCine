package swing.views;

import proceso.*;
import swing.components.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

/**
 * Panel principal de administración
 * @author Ruben
 *
 */
public class AdminPanelPrincipal extends FestivalAppPanel 
{
	/**
	 * Label con el texto "Administración"
	 */
	private FestivalLabel lblTitle1 = new FestivalLabel("Administración", Color.white, 50f);
	/**
	 * JList con los elementos de administración
	 */
	private JList listAdm;
	/**
	 * Panel de administración actual
	 */
	private JPanel panelActual;
	
	public AdminPanelPrincipal(AppController controller)
	{
		setAppController(controller);
		FestivalAppPanel[] admPanels = new FestivalAppPanel[] { new AdmUsuariosPanel(this.getAppController(), this), new AdmPeliculasPanel(this.getAppController(), this), new AdmVotacionesPanel(this.getAppController(), this) };
		listAdm = new JList<FestivalAppPanel>(admPanels);
		setLayout(new MigLayout("fill","100[]100",""));
		add(lblTitle1, "span, growx, wrap");
		listAdm.setPreferredSize(new Dimension(175,1000));
		listAdm.addListSelectionListener(new ListSelectionListener() 
		{	
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{	
				setPanelActual((JPanel)listAdm.getSelectedValue());
				AdminPanelPrincipal.this.revalidate();
				AdminPanelPrincipal.this.repaint();
			}
		});
		add(listAdm, "growy");
		listAdm.setSelectedIndex(0);
	}
	
	public JPanel getPanelActual() {
		return panelActual;
	}
	/**
	 * Asigna el panel actual y lo muestra al usuario
	 */
	public void setPanelActual(JPanel nuevoPanel) 
	{
		if(panelActual!=null)
			this.remove(panelActual);
		this.panelActual = nuevoPanel;
		this.add(nuevoPanel,"growy");
	}
}
