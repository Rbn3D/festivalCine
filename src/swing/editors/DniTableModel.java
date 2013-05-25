package swing.editors;

import java.util.ArrayList;

import datos.Dni;

import proceso.AppController;

/**
 * TableModel para representar DNIs
 * @author Ruben
 *
 */
public class DniTableModel extends FestivalListTableModel<Dni> {

	public DniTableModel(AppController controller)
	{
		setAppController(controller);
		updateData();
	}
	
	@Override
	public String getColumnName(int column) 
	{
		return "DNI";
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex);
	}

	@Override
	public void updateData() 
	{
		data = appController.getCapaDAO().getDnis();
		fireTableDataChanged();
	}

}
