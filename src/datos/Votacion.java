package datos;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;

import dao.DBObject;

/**
 * Representa una votación en la aplicación
 * @author  Rubén
 */
public class Votacion extends DBObject
{
	/**
	 * Crea una votación sin valores por defecto
	 */
	public Votacion() { }
	
	/**
	 * Crea una votación con los siguientes valores
	 * @param nombre El nombre de la votación
	 */
	public Votacion(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Crea una votación con los siguientes valores
	 * @param nombre El nombre de la votación
	 * @param peliculas Las películas a concurso
	 */
	public Votacion(String nombre, ArrayList<Pelicula> peliculas) {
		this.nombre = nombre;
		this.peliculas = peliculas;
	}
	
	/**
	 * Crea una votación con los siguientes valores
	 * @param nombre El nombre de la votación
	 * @param peliculas Las películas a concurso
	 */
	public Votacion(String nombre, Pelicula ... peliculas)
	{
		this(nombre, new ArrayList<Pelicula>(Arrays.asList(peliculas)));
	}
	
	/**
	 * Nombre de la votación
	 */
	private String nombre;
	/**
	 * Las películas a concurso
	 */
	private ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
	/**
	 * Resultado asociado a ésta votacion
	 */
	private Resultado resultado = new Resultado(this);
	/**
	 * ¿Es ésta la votación activa?
	 */
	private boolean votacionActiva = true;
	/**
	 * ¿Están abiertas las votaciones?
	 */
	private boolean permitirVotos = true;
	/**
	 * Fecha de creación de la votación
	 */
	private Date fechaCreacion = new Date(new java.util.Date().getTime());
	/**
	 * Fecha de fin de la votación
	 */
	private Date fechaDeFin = null;
	
	/**
	 * Cierra la votación y establece la fecha de fin
	 * @return true si se ha cerrado la votación, false si ya estaba cerrada
	 */
	public boolean terminarVotacion()
	{
		if(isTermindao())
		{
			fechaDeFin = new Date(new java.util.Date().getTime());
			permitirVotos = false;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Indica si el usuario u ha votado en esta votación
	 * @param u El usuario
	 * @return true si ha votado, false en caso contrario
	 */
	public boolean haVotado(Usuario u)
	{
		return resultado.haVotado(u);
	}
	
	/**
	 * Getter de permitirVotos
	 */
	public boolean isPermitirVotos() {
		return permitirVotos;
	}
	/**
	 * Setter de permitirVotos
	 */
	public void setPermitirVotos(boolean permitirVotos) {
		this.permitirVotos = permitirVotos;
	}
	
	/**
	 * Indica si la votación esta cerrada
	 * @return true si esta cerrada, false en caso contrario
	 */
	public boolean isTermindao() 
	{
		return !permitirVotos;
	}

	/**
	 * Getter de nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Setter de nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * 
	 * Getter de peliculas
	 */
	public ArrayList<Pelicula> getPeliculas() {
		return peliculas;
	}
	/**
	 * Setter de peliculas
	 */
	public void setPeliculas(ArrayList<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}
	/**
	 * Getter de votación activa
	 */
	public boolean isVotacionActiva() {
		return votacionActiva;
	}
	/**
	 * Getter de resultado
	 */
	public Resultado getResultado() {
		return resultado;
	}
	/**
	 * Getter de fecha de creación
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * Setter de fecha de creación
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * Getter de fechaDeFin
	 */
	public Date getFechaDeFin() {
		return fechaDeFin;
	}
	/**
	 * Setter de fechaDeFin
	 */
	public void setFechaDeFin(Date fechaDeFin) {
		this.fechaDeFin = fechaDeFin;
	}
	
	/**
	 * Setter de votación activa
	 */
	public void setVotacionActiva(boolean votacionActiva) {
		this.votacionActiva = votacionActiva;
	}
}
