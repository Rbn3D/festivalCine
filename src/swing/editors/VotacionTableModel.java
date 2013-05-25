package swing.editors;

import proceso.AppController;
import datos.Votacion;

/**
 * TableModel dedicado a mostrar las votaciones de la aplicación
 * @author Ruben
 *
 */
public class VotacionTableModel extends FestivalListTableModel<Votacion> {

	public VotacionTableModel(AppController appController)
	{
		setAppController(appController);
		updateData();
	}
	
	private String[] columnNames = new String[] { "Nombre", "Finalizada", "Votación activa", "Fecha de creación", "Fecha de finalización" };
	
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
	public int getRowCount() 
	{
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) 
	{
		Votacion v = data.get(arg0);
		switch (arg1) {
		case 0:
			return v.getNombre();
		case 1:
			return (v.isPermitirVotos()?"No finalizada":"Finalizada");
		case 2:
			return (v.isVotacionActiva()?"Activa":"Inactiva");
		case 3:
			return v.getFechaCreacion();
		case 4:
			return v.getFechaDeFin();
		default:
			return null;
		}
	}

	@Override
	public void updateData() 
	{
		data = getAppController().getCapaDAO().getVotaciones();
		fireTableDataChanged();
	}

}
