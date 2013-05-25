package swing.views;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Window listener dedicado a manejar el cierre de la aplicación
 * @author Ruben
 *
 */
public class CloseFestivalListener implements WindowListener {

	public CloseFestivalListener(JFrame view) {
		this.setView(view);
	}
	
	private JFrame view;
	
	public JFrame getView() {
		return view;
	}
	
	public void setView(JFrame view) {
		this.view = view;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(JOptionPane.showConfirmDialog(view, "¿Desea salir de la aplicación?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			view.dispose();
			System.exit(0);
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
