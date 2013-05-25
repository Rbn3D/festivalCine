package datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import dao.DBObject;

/**
 * Representa el resultado de una votación
 * @author  Rubén
 */
public class Resultado extends DBObject
{
	/**
	 * Votación a la que está asociada éste resultado
	 */
	private Votacion votacion;
	/**
	 * Lista de puntuaciones del resultado
	 */
	private ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();
	
	/**
	 * Crea un resultado sin parámetros por defecto
	 */
	public Resultado()
	{
		
	}
	
	/**
	 * Crea un resultado asociado a la votación especificada
	 * @param votacion La votación cuyo resultado queremos representar
	 */
	public Resultado(Votacion votacion) {
		this.votacion = votacion;
		if(votacion!=null)
			for(Pelicula p : votacion.getPeliculas())
				puntuaciones.add(new Puntuacion(p));
	}
	
	/**
	 * Crea un resultado con la votación y las puntuaciones especificadas
	 * @param votacion La votación cuyo resultado queremos representar
	 * @param puntuaciones Las puntuaciones a asignar
	 */
	public Resultado(Votacion votacion, ArrayList<Puntuacion> puntuaciones) {
		this.votacion = votacion;
		this.puntuaciones = puntuaciones;
	}
	
	/**
	 * Comprueba si el usuario u ha participado en la votación asociada a este resultado
	 * @param u El usuario
	 * @return True si ha votado, false en caso contrario
	 */
	public boolean haVotado(Usuario u)
	{
		boolean votado = false;
		for(Puntuacion p : puntuaciones)
			if(p.getUsuario().equals(u))
				votado = true;
		return votado;
	}

	/**
	 * Getter de la propiedad votación
	 */
	public Votacion getVotacion() {
		return votacion;
	}

	/**
	 * Getter de la propiedad puntuaciones
	 */
	public ArrayList<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}
	/**
	 * Obtiene las puntuaciones del usuario u en ésta votación
	 * @param u El usuario
	 * @return Un ArrayList con las puntuaciones, que estará vacío en caso de que no exista ninguna
	 */
	public ArrayList<Puntuacion> getPuntuacionesDeUsuario(Usuario u) 
	{
		ArrayList<Pelicula> peliculasSinPuntuar = new ArrayList<Pelicula>(votacion.getPeliculas());
		ArrayList<Puntuacion> puntUser = new ArrayList<Puntuacion>();
		for(Puntuacion pun : puntuaciones)
		{
			if(pun.getUsuario().equals(u))
			{
				puntUser.add(pun);
				peliculasSinPuntuar.remove(pun.getPelicula());
			}
		}
		
		for(Pelicula pel : peliculasSinPuntuar)
		{
			puntUser.add(new Puntuacion(pel, u));
		}
		
		return puntUser;
	}
	
	/**
	 * Obtiene la puntuación total de una película en ésta votación para el tipo de usuario especificado
	 * @param p La película
	 * @param tipoUsuario El tipo de usuario
	 * @return La suma de las puntuaciones a la película en ésta votación
	 *  que hayan sido emitidos por el tipo de usuario especificado
	 */
	public int getPuntuacionTotalPelicula(Pelicula p, ETipoUsuario tipoUsuario)
	{
		int puntos = 0;
		for(Puntuacion punt : puntuaciones)
			if(punt.getPuntuacion()!=null && punt.getUsuario()!=null && punt.getUsuario().getTipoUsuario().equals(tipoUsuario) && punt.getPelicula() != null && punt.getPelicula().equals(p))
				puntos+=punt.getPuntuacion().intValue();
		return puntos;
	}

	/**
	 * Setter de la propiedad votación
	 */
	public void setVotacion(Votacion votacion) {
		this.votacion = votacion;
	}
	
	/**
	 * Setter de la propiedad puntuaciones
	 */
	public void setPuntuaciones(ArrayList<Puntuacion> puntuaciones) {
		this.puntuaciones = puntuaciones;
	}
	
	/**
	 * Asigna las puntuaciones punt en ésta votación y las asigna como emitidas por el usuario usuario
	 * @param usuario El usuario emisor de las puntuaciones
	 * @param punt Las puntuaciones
	 */
	public void puntuar(Usuario usuario, ArrayList<Puntuacion> punt) 
	{
		ArrayList<Puntuacion> puntsDeUsuario = new ArrayList<Puntuacion>();
		for(Puntuacion p : getPuntuaciones())
			if(p.getUsuario().equals(usuario))
				puntsDeUsuario.add(p);
		getPuntuaciones().removeAll(puntsDeUsuario);
		for(Puntuacion pUsuario : punt)
		{
			pUsuario.setVotacion(votacion);
			pUsuario.setUsuario(usuario);
		}
		getPuntuaciones().addAll(punt);
	}


}
