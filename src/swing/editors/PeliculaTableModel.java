package swing.editors;

import java.util.ArrayList;

import proceso.AppController;
import datos.Pelicula;

/**
 * TableModel dedicado a la visualización de películas
 * @author Ruben
 *
 */
public class PeliculaTableModel extends FestivalListTableModel<Pelicula> {

	public PeliculaTableModel(AppController appController)
	{
		setAppController(appController);
		updateData();
	}
	
	private String[] columnNames = {"Título", "Director", "Actores principales"};
	
	@Override
	public int getColumnCount() 
	{
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) 
	{
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) 
	{
		Pelicula p = data.get(arg0);
		switch (arg1) {
		case 0:
			return p.getTitulo();
		case 1:
			return p.getDirector();
		case 2:
			return MiscUtilities.ArrayListToString(p.getActores());
		}
		return null;
	}

	@Override
	public void updateData() 
	{
		data = getAppController().getCapaDAO().getPeliculas();
		fireTableDataChanged();
	}

}
