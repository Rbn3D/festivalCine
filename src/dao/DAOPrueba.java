package dao;

import java.util.ArrayList;

import proceso.AppController;

import datos.Dni;
import datos.ETipoUsuario;
import datos.Pelicula;
import datos.Puntuacion;
import datos.Resultado;
import datos.Usuario;
import datos.Votacion;

/**
 * 
 * @author Ruben
 *
 *	Implementación de prueba de la capa DAO, con propósitos de depuración. Trabaja con Listas almacenadas en memoria en lugar de Bases de Datos.
 *
 */

public class DAOPrueba implements IFestivalDAO {

	private AppController controller;
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	private ArrayList<Dni> dnis = new ArrayList<Dni>();
	private ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
	private ArrayList<Votacion> votaciones = new ArrayList<Votacion>();
	
	public DAOPrueba(AppController controller)
	{
		this.controller = controller;
		dnis.add(new Dni("28565343A"));
		usuarios.add(new Usuario("admin", "admin", ETipoUsuario.administrador));
		usuarios.add(new Usuario("jurado", "jurado", new Dni("28565343A"), ETipoUsuario.jurado));
		usuarios.add(new Usuario("espectador", "espectador", ETipoUsuario.espectador));
		peliculas.add(new Pelicula("Titanic", "James Cameron", "Leonardo DiCaprio"));
		peliculas.add(new Pelicula("Avatar", "James Cameron", "Blablabla"));
		peliculas.add(new Pelicula("Indiana Jones", "Steven Spillberg", "Harrison Ford"));
		peliculas.add(new Pelicula("Mision Imposible", "I don't know", "Tom Cruise"));
		peliculas.add(new Pelicula("Embrujada", "James Cameron", "Leonardo DiCaprio"));
		peliculas.add(new Pelicula("Tarzan", "James Cameron", "Blablabla"));
		peliculas.add(new Pelicula("Ben Hur", "Steven Spillberg", "Harrison Ford"));
		peliculas.add(new Pelicula("Los ángeles de Charlie", "I don't know", "Tom Cruise"));
		peliculas.add(new Pelicula("Ocean's eleven", "Steven Spillberg", "Harrison Ford"));
		peliculas.add(new Pelicula("The Italian Job", "I don't know", "Tom Cruise"));
		peliculas.add(new Pelicula("Tomb Raider", "I don't know", "Tom Cruise"));
		peliculas.add(new Pelicula("Pulp Fiction", "I don't know", "Tom Cruise"));
		Votacion v;
		votaciones.add(v = new Votacion("Votación de prueba", peliculas));
	}
	
	@Override
	public Usuario login(String login, String clave) {
		Usuario user = null;
		for(Usuario u : usuarios)
		{
			if(u.getLogin().equals(login) && u.getPassword().equals(clave))
				user = u;
		}
		return user;
	}
	
	@Override
	public ArrayList<Usuario> getUsuarios() {
		return usuarios; 
	}

	@Override
	public ArrayList<Usuario> getUsuarios(ETipoUsuario... filtroTipo) {
		ArrayList<Usuario> uf = new ArrayList<Usuario>();
		for(Usuario u : usuarios)
		{
			for(ETipoUsuario t: filtroTipo)
			{
				if(u.getTipoUsuario().equals(t))
					uf.add(u);
			}
		}
		return uf;
	}

	@Override
	public Usuario getUsuario(String login) {
		Usuario r = null;
		for(Usuario u : usuarios)
		{
			if(login.equals(u.getLogin()))
				r = u;
		}
		return r;
	}

	@Override
	public boolean existeUsuario(String login) {
		boolean r = false;
		for(Usuario u : usuarios)
		{
			if(login.equals(u.getLogin()))
				r = true;
		}
		return r;
	}
	
	@Override
	public ArrayList<Dni> getDnis() {
		return dnis;
	}

	@Override
	public boolean existeDNI(String dni) {
		//return this.dni.contains(dni);
		boolean existe = false;
		for(Dni d : this.dnis)
		{
			if(d.getValue().equals(dni))
				existe = true;
		}
		return existe;
	}

	@Override
	public ArrayList<Pelicula> getPeliculas() {
		return peliculas;
	}

	@Override
	public Pelicula getPelicula(String titulo) {
		Pelicula r = null;
		for(Pelicula p : peliculas)
		{
			if(titulo.equals(p.getTitulo()))
				r = p;
		}
		return r;
	}
	
	@Override
	public boolean existePelicula(String titulo) {
		boolean r = false;
		for(Pelicula p : peliculas)
		{
			if(titulo.equals(p.getTitulo()))
				r = true;
		}
		return r;
	}

	@Override
	public Votacion getVotacionActual() {
		Votacion r = null;
		for(Votacion v : votaciones)
		{
			if(v.isVotacionActiva())
				r = v; 
		}
		return r;
	}
	
	@Override
	public Votacion getVotacion(String nombre) {
		Votacion r = null;
		for(Votacion v : votaciones)
		{
			if(nombre.equals(v.getNombre()))
				r = v;
		}
		return r;
	}
	
	@Override
	public boolean existeVotacion(String nombre) {
		boolean r = false;
		for(Votacion v : votaciones)
		{
			if(nombre.equals(v.getNombre()))
				r = true;
		}
		return r;
	}

	@Override
	public ArrayList<Votacion> getVotaciones() {
		return votaciones;
	}

	@Override
	public boolean setUsuario(Usuario u, TipoConsulta tipo) {
		boolean r = false;
		switch (tipo) {
		case insertar:
				if(!existeUsuario(u.getLogin()))
				{
					usuarios.add(u);
					r= true;
				}
			break;
		case modificar:
				if(existeUsuario(u.getLogin()))
				{
					usuarios.set(usuarios.indexOf(getUsuario(u.getLogin())), u);
					r = true;
				}
			break;
		}
		return r;
	}

	@Override
	public boolean setPelicula(Pelicula p, TipoConsulta tipo) {
		boolean r = false;
		switch (tipo) {
		case insertar:
				if(!existePelicula(p.getTitulo()))
				{
					peliculas.add(p);
					r= true;
				}
			break;
		case modificar:
				if(existePelicula(p.getTitulo()))
				{
					peliculas.set(peliculas.indexOf(getPelicula(p.getTitulo())), p);
					r = true;
				}
			break;
		}
		return r;
	}

	@Override
	public boolean setVotacion(Votacion v, TipoConsulta tipo) {
		boolean r = false;
		switch (tipo) {
		case insertar:
				if(!existeVotacion(v.getNombre()))
				{
					votaciones.add(v);
					r= true;
				}
			break;
		case modificar:
				if(existeVotacion(v.getNombre()))
				{
					votaciones.set(votaciones.indexOf(getVotacion(v.getNombre())), v);
					r = true;
				}
			break;
		}
		return r;
	}
	
	@Override
	public void setVotacionActiva(Votacion vActiva) 
	{
		for(Votacion v : getVotaciones())
		{
			if(!v.equals(vActiva))
			{
				v.setVotacionActiva(false);
				v.setPermitirVotos(false);
			}
		}
		vActiva.setVotacionActiva(true);
	}
	
	@Override
	public boolean setDni(Dni dni, TipoConsulta tipo) {
		switch (tipo) {
		case insertar:
				if(this.dnis.contains(dni))
					return false;
				else
				{
					this.dnis.add(dni);
					return true;
				}
		case modificar:
		{
			if(!this.dnis.contains(dni))
				return false;
			else
			{
				this.dnis.set(this.dnis.indexOf(dni), dni);
				return true;
			}
		}
		default:
			return false;
		}
		
	}
	
	@Override
	public void setPuntuacion(Votacion votacionActual,
			ArrayList<Puntuacion> puntuaciones) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean set(DBObject dbo, TipoConsulta tipo) 
	{
		if(dbo instanceof Usuario)
			return setUsuario((Usuario) dbo, tipo);
		else if(dbo instanceof Pelicula)
			return setPelicula((Pelicula) dbo, tipo);
		else if(dbo instanceof Votacion)
			return setVotacion((Votacion) dbo, tipo);
		else if(dbo instanceof Dni)
			return setDni((Dni) dbo, tipo);
		else
			return false;
	}
	
	@Override
	public boolean borrarUsuario(Usuario u) 
	{
		if(!u.equals(controller.getUsuarioActual()))
			return usuarios.remove(u);
		else
			return false;
	}

	@Override
	public boolean borrarPelicula(Pelicula p) {
		return peliculas.remove(p);
	}

	@Override
	public boolean borrarVotacion(Votacion v) {
		return votaciones.remove(v);
	}

	@Override
	public boolean borrarDni(Dni dni) {
		if(!this.dnis.contains(dni))
			return false;
		else
		{
			this.dnis.remove(dni);
			return true;
		}
	}
	
	@Override
	public boolean borrar(DBObject dbo) 
	{
		if(dbo instanceof Usuario)
			return borrarUsuario((Usuario) dbo);
		else if(dbo instanceof Pelicula)
			return borrarPelicula((Pelicula) dbo);
		else if(dbo instanceof Votacion)
			return borrarVotacion((Votacion) dbo);
		else if(dbo instanceof Dni)
			return borrarDni((Dni) dbo);
		else
			return false;
	}

}
