package swing.editors;

import java.util.ArrayList;

import proceso.AppController;

import datos.Usuario;

/**
 * TableModel dedicado a mostrar los usuarios de la aplicación
 * @author Ruben
 *
 */
public class UsuarioTableModel extends FestivalListTableModel<Usuario> {

	private String[] columnNames = new String[] { "Nombre de usuario", "Contraseña", "DNI", "Tipo de usuario" }; 
	
	public UsuarioTableModel(AppController controller)
	{
		this.appController = controller;
		updateData();
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Usuario u;
		try{
		u = data.get(arg0);
		}
		catch (Exception e) 
		{
			return null;
		}
		if(u!=null)
		{
			if(arg1 == 0)
				return u.getLogin();
			else if(arg1 == 1)
				return "******";
			else if(arg1 == 2)
				return u.getDni();
			else if(arg1 == 3)
				return u.getTipoUsuario();
		}
		return null;
	}

	@Override
	public void updateData() 
	{
		data = appController.getCapaDAO().getUsuarios();
		fireTableDataChanged();
	}

}
