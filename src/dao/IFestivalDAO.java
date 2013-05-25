package dao;
import java.util.ArrayList;

import datos.*;

/**
 * 
 * @author Ruben
 *
 * Interfaz destinada a abstraer el comportamiento de la capa DAO de la aplicación, permitiendo crear multiples implementaciones de la misma. 
 *
 */
public interface IFestivalDAO
{
	//-------- Obtención ---------//
	
	public Usuario login(String login, String clave);
	public ArrayList<Usuario> getUsuarios();
	public ArrayList<Usuario> getUsuarios(ETipoUsuario ... filtroTipo);
	public Usuario getUsuario(String login);
	public boolean existeUsuario(String login);
	public ArrayList<Dni> getDnis();
	public boolean existeDNI(String dni);
	public ArrayList<Pelicula> getPeliculas();
	public Pelicula getPelicula(String titulo);
	public boolean existePelicula(String titulo);
	public Votacion getVotacionActual(); 
	public Votacion getVotacion(String nombre);
	public boolean existeVotacion(String nombre);
	public ArrayList<Votacion> getVotaciones();
	
	//-------- Insercción/Modificación ---------//
	public enum TipoConsulta{ insertar, modificar }; // Indica operación a realizar en los métodos setUsuario, setPelicula y setVotacion
	
	public boolean setUsuario(Usuario u, TipoConsulta tipo);
	public boolean setPelicula(Pelicula p, TipoConsulta tipo);
	public boolean setVotacion(Votacion v, TipoConsulta tipo);
	public void setVotacionActiva(Votacion v);
	public boolean setDni(Dni dni, TipoConsulta tipo);
	public void setPuntuacion(Votacion votacionActual, ArrayList<Puntuacion> puntuaciones);
	public boolean set(DBObject dbo, TipoConsulta tipo);
	
	//-------- Eliminación ---------//
		
	public boolean borrarUsuario(Usuario u);
	public boolean borrarPelicula(Pelicula p);
	public boolean borrarVotacion(Votacion v);
	public boolean borrarDni(Dni dni);
	public boolean borrar(DBObject dbo);
	
}
