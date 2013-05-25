package swing.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import datos.Pelicula;

import proceso.AppController;

/**
 * TableModel dedidado a mostrar peliculas y permitir seleccionarlas 
 * por medio de checkBoxes (Usado para seleccionar peliculas a concurso en el formulario de votaciones)
 * @author Ruben
 *
 */
public class SelectPeliculaTableModel extends PeliculaTableModel {

	public SelectPeliculaTableModel(AppController appController) 
	{
		super(appController);
		for(Pelicula p : data)
			selectData.put(p, Boolean.valueOf(false));
	}
	
	private HashMap<Pelicula, Boolean> selectData = new HashMap<Pelicula, Boolean>();
	
	@Override
	public String getColumnName(int column) {
		if(column == 0)
			return "Seleccione";
		else
			return super.getColumnName(column-1);
	}
	
	@Override
	public Object getValueAt(int arg0, int arg1) 
	{
		Pelicula p = data.get(arg0);
		Boolean b = selectData.get(p);
		if(arg1 == 0)
			return b;
		else
			return super.getValueAt(arg0, arg1-1);
	}

	@Override
	public int getColumnCount() 
	{
		return super.getColumnCount()+1;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0)
			return Boolean.class;
		return super.getColumnClass(columnIndex);
	}
	
	public ArrayList<Pelicula> getSelected()
	{
		ArrayList<Pelicula> selected = new ArrayList<Pelicula>();
		for(Entry<Pelicula, Boolean> p : selectData.entrySet())
			if(p.getValue().booleanValue())
				selected.add(p.getKey());
		return selected;
	}
	
	public void setSelected(ArrayList<Pelicula> peliculas)	
	{
		toggleSelection(false);
		for(Pelicula p : peliculas)
		{
			selectData.put(p, Boolean.valueOf(true));
		}
		fireTableDataChanged();
	}
	
	public void toggleSelection(boolean value)
	{
		for(Pelicula p : data)
		{
			selectData.put(p, Boolean.valueOf(value));
		}
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return true;
		else
			return super.isCellEditable(rowIndex, columnIndex-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex == 0)
		{
			Pelicula sel = data.get(rowIndex);
			for(Entry<Pelicula, Boolean> p : selectData.entrySet())
				if(p.getKey() == sel)
					p.setValue((Boolean) aValue);
		}
		else
			super.setValueAt(aValue, rowIndex, columnIndex+1);
	}
}
