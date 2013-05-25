package proceso;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controlErrores.ErrorApp;

/**
 * Punto de entrada de la aplicación
 * @author Ruben
 *
 */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run()
			{
				try
				{
					new AppController();
				}
				catch(ErrorApp e)
				{
					JOptionPane.showMessageDialog(null, e.getMsg());
				}
			}
		});
	}

}
