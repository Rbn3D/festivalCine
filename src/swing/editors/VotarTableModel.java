package swing.editors;

import java.util.ArrayList;

import datos.Puntuacion;
import datos.Resultado;
import datos.Votacion;
import proceso.AppController;

/**
 * TableModel destinado a proceso de votación
 * @author Ruben
 *
 */
public class VotarTableModel extends FestivalSingleTableModel<Votacion>
{
	
	public VotarTableModel(AppController controller, Votacion votacion)
	{
		setAppController(controller);
		data = votacion;
		if(votacion == null)
			puntuaciones = new ArrayList<Puntuacion>();
		else
			puntuaciones = votacion.getResultado().getPuntuacionesDeUsuario(controller.getUsuarioActual());
	}
	
	private boolean permitirEdicion = true;
	public boolean isPermitirEdicion() {
		return permitirEdicion;
	}

	public void setPermitirEdicion(boolean permitirEdicion) {
		this.permitirEdicion = permitirEdicion;
	}

	private ArrayList<Puntuacion> puntuaciones;
	private String[] columns = new String[] { "Película", "Puntuación"};
	
	public ArrayList<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}
	
	@Override
	public String getColumnName(int column) 
	{
		return columns[column];
	}
	
	@Override
	public int getColumnCount() 
	{
		return columns.length;
	}
	@Override
	public int getRowCount() 
	{
		return puntuaciones.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (permitirEdicion? columnIndex == 1: false);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
	{
		Puntuacion p = puntuaciones.get(rowIndex);
		if(columnIndex == 1)
		{
			double val = (Double) aValue;
			p.setPuntuacion((int) val);
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{	
		return (columnIndex==1? Integer.class: super.getColumnClass(columnIndex));
	}
	
	@Override
	public Object getValueAt(int arg0, int arg1) 
	{
		Puntuacion p = puntuaciones.get(arg0);
		switch (arg1) {
		case 0:
			return p.getPelicula();
		case 1:
			return p.getPuntuacion();
		}
		return null;
	}
	@Override
	public void updateData() 
	{
		fireTableStructureChanged();
		fireTableDataChanged();
	}
	
}
