package swing.forms;

import java.awt.*;
import java.util.regex.Pattern;

import javax.swing.*;

import datos.Dni;

/**
 * Utilidades misceláneas para formularios
 * @author Ruben
 *
 */
public class FormUtilities 
{
	/**
	 * Adapta los componentes del JComponent especificado, cambiando su color y su tamaño
	 * @param form
	 */
	public static void fixForm(JComponent form)
	{
		form.setOpaque(false);
		for(Component c : form.getComponents())
			if(c instanceof JTextField)
			{
				((JTextField) c).setColumns(30);
			}
			else if(c instanceof JLabel || c instanceof JCheckBox)
			{
				c.setForeground(Color.white);
			}
	}
	
	/**
	 * Comprueba si un dni es válido
	 * @param dni Dni a comprobar
	 * @return true si es correcto el dni, false en caso contrario
	 */
	public static boolean isValidDni(Dni dni)
	{
		String regex = "\\d{8}[A-z]$";
		Pattern pt = Pattern.compile(regex);
		if(dni != null && dni.getValue() != null)
			return pt.matcher(dni.getValue()).find() && isValidLetraDNI(dni);
		else
			return false;
		
	}
	
	/**
	 * Comprueba si la letra de un dni es válido
	 * @param dni Dni a comprobar
	 * @return true si la letra del DNI es correcta, false en caso contrario
	 */
	public static boolean isValidLetraDNI(Dni dni)
	{
		String cadena="TRWAGMYFPDXBNJZSQVHLCKET";
        int posicion = Integer.valueOf(dni.getValue().substring(0, dni.getValue().length()-1)) % 23;
        String letra = cadena.substring(posicion,posicion+1);
        if(dni.getValue().toUpperCase().endsWith(letra))
        	return true;
        else
        	return false;
	}
}
