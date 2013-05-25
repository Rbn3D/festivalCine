package swing.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import proceso.AppController;
import datos.ETipoUsuario;
import datos.Pelicula;
import datos.Puntuacion;
import datos.Votacion;

/**
 * TableModel dedicado a mostrar los resultados de una votación
 * @author Ruben
 *
 */
public class ResultadoTableModel extends FestivalSingleTableModel<Votacion> {

	public ResultadoTableModel(AppController controller, Votacion votacion, ETipoUsuario tipoUsuario) 
	{
		setAppController(controller);
		data = votacion;
		this.tipoUsuario = tipoUsuario;
		updateData();
	}
	
	private ArrayList<NodoResultado> resultado = new ArrayList<NodoResultado>();
	private String[] columnNames = new String[] { "Posición", "Película", "Puntuación Total" };
	private ETipoUsuario tipoUsuario;
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
		return resultado.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) 
	{
		NodoResultado nodo = resultado.get(arg0);
		switch (arg1) {
		case 0:
			return arg0 + 1; // Puesto de la película
		case 1:
			return nodo.getPelicula();
		case 2:
			return nodo.getPuntuacion();
		}
		return null;
	}

	/**
	 * Actualiza el resultado y lo ordena de mayor a menor puntuación
	 */
	@Override
	public void updateData() 
	{
		resultado.clear();
		
		for(Pelicula p : data.getPeliculas())
		{
			resultado.add(new NodoResultado(p, data.getResultado().getPuntuacionTotalPelicula(p, tipoUsuario)));
		}
		Collections.sort(resultado);
		fireTableDataChanged();
	}
	
	/**
	 * Clase interna que raliciona a una pelicula con su puntuación total
	 * @author Ruben
	 *
	 */
	private class NodoResultado implements Comparable<NodoResultado>
	{
		public NodoResultado(Pelicula pelicula, int puntuacion) {
			super();
			this.pelicula = pelicula;
			this.puntuacion = puntuacion;
		}
		private Pelicula pelicula;
		private int puntuacion;
		
		public Pelicula getPelicula() {
			return pelicula;
		}
		public int getPuntuacion() {
			return puntuacion;
		}
		public void setPelicula(Pelicula pelicula) {
			this.pelicula = pelicula;
		}
		public void setPuntuacion(int puntuacion) {
			this.puntuacion = puntuacion;
		}
		@Override
		public int compareTo(NodoResultado o) 
		{
			return o.puntuacion - puntuacion;
		}
	}
}
