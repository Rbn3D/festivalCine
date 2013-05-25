package swing.editors;

import java.util.ArrayList;

/**
 * Utilidades misceláneas
 * @author Ruben
 *
 */
public class MiscUtilities 
{
	/**
	 * Devuelve un string con los valores de un Arraylist concatenados y separados por comas
	 * @param r El arrayList
	 * @return 
	 */
	public static String ArrayListToString(ArrayList r)
	{
		String str = "";
		
		for(int i = 0;i<r.size();i++)
		{
			if(i == 0)
				str+=r.get(i);
			else
				str+=", " + r.get(i);
		}
			
		return str;
	}
}
