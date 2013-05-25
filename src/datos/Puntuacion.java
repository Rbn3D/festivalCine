package datos;

import java.util.HashMap;

import dao.DBObject;

/**
 * Representa la puntuación que un usuario da a una película en una determinada votación
 * @author Rubén
 */
public class Puntuacion extends DBObject
{
	/**
	 * Crea una puntuación sin ningún parametro por defecto
	 */
	public Puntuacion() 
	{		
	}
	
	/**
	 * Crea una puntuación, a la que se le asigna la película especificada 
	 * @param p La película
	 */
	public Puntuacion(Pelicula p)
	{
		this.pelicula = p;
	}
	
	/**
	 * Crea una puntuación, a la que se le asigna la película y el usuario especificados
	 * @param p La película
	 * @param usuario El usuario
	 */
	public Puntuacion(Pelicula p, Usuario usuario)
	{
		this(p);
		this.usuario = usuario;
	}
	
	/**
	 * Crea una puntuación, a la que se le asigna la película y la puntuación especificadas
	 * @param p La película
	 * @param puntuacion
	 */
	public Puntuacion(Pelicula p, int puntuacion)
	{
		this(p);
		this.puntuacion = new Integer(puntuacion);
	}
	
	/**
	 * Película puntuada
	 */
	private Pelicula pelicula = null;
	/**
	 * Usuario autor de la puntuación
	 */
	private Usuario usuario = null;
	/**
	 * La puntuación, debe estar comprendida entre 1 y 5
	 */
	private Integer puntuacion = null;
	/**
	 * Votación a la que está asocidada esta puntuación
	 */
	private Votacion votacion = null;
	
	/**
	 * Crea una puntuación, a la que se le asigna la película, el usuario especificados y la puntuación especificados
	 * @param p La película
	 * @param usuario El usuario
	 * @param puntuacion La puntuación
	 */
	public Puntuacion(Pelicula p, Usuario usuario, int puntuacion)
	{
		this(p, puntuacion);
		this.usuario = usuario;
	}
	
	/**
	 * Getter de la propiedad pelicula
	 */
	public Pelicula getPelicula() {
		return pelicula;
	}

	/**
	 * Getter de la propiedad puntuacion
	 */
	public Integer getPuntuacion() {
		return puntuacion;
	}

	/**
	 * Setter de la propiedad pelicula
	 */
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	/**
	 * Setter de la propiedad puntuacion
	 */
	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	/**
	 * Getter de la propiedad usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Setter de la propiedad usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Getter de la propiedad votacion
	 */
	public Votacion getVotacion() {
		return votacion;
	}
	
	/**
	 * Setter de la propiedad votacion
	 */
	public void setVotacion(Votacion votacion) {
		this.votacion = votacion;
	}
	
	
	
}
