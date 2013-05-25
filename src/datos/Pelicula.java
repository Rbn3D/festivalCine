package datos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.google.common.base.Joiner;

import dao.DBObject;

/**
 * Representa una película en la aplicación
 * @author  alumno
 */
public class Pelicula extends DBObject
{
	/**
	 * Construye una película sin valores por defecto
	 */
	public Pelicula()
	{
	}
	
	/**
	 * Construye una película con las propiedades especificadas
	 * @param titulo Título de la película
	 * @param director Director de la película
	 * @param actores Actores principales de la película
	 */
	public Pelicula(String titulo, String director, ArrayList<String> actores) {
		this.titulo = titulo;
		this.director = director;
		this.actores = actores;
	}
	
	/**
	 * Construye una película con las propiedades especificadas
	 * @param titulo Título de la película
	 * @param director Director de la película
	 * @param actores Actores principales de la película
	 */
	public Pelicula(String titulo, String director, String ... actores) {
		this(titulo, director, new ArrayList<String>(Arrays.asList(actores)));
	}
	
	/**
	 * Título de la película
	 * @uml.property  name="titulo"
	 */
	private String titulo;
	/**
	 * Director de la película
	 * @uml.property  name="director"
	 */
	private String director;
	/**
	 * Actores principales de la película
	 * @uml.property  name="actores"
	 */
	private ArrayList<String> actores = new ArrayList<String>();
	
	/**
	 * Getter de la propiedad Titulo
	 * @uml.property  name="titulo"
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Setter de la propiedad título
	 * @uml.property  name="titulo"
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Getter de la propiedad Director
	 * @uml.property  name="director"
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * Setter de la propiedad Director
	 * @uml.property  name="director"
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	/**
	 * Getter de la propiedad Actores
	 * @uml.property  name="actores"
	 */
	public ArrayList<String> getActores() {
		return actores;
	}
	
	/**
	 * Devuelve los actores concatenados y separados por un salto de línea
	 * @return Un String con los actores
	 */
	public String getActoresAsString() {
		return Joiner.on("\n").join(getActores());
	}
	/**
	 * Setter de la propiedad Actores
	 * @uml.property  name="actores"
	 */
	public void setActores(ArrayList<String> actores) {
		this.actores = actores;
	}
	
	/**
	 * Setter adicional para la propiedad actores
	 * @param actores Un String conteniendo los actores, que deben estar separados por un salto de línea
	 */
	public void setActoresAsString(String actores)
	{
		ArrayList<String> lst = new ArrayList<String>(actores.length());
		for(String str : actores.split("\n"))
			lst.add(str);
		setActores(lst);
	}
	
	@Override
	public String toString() {
		return titulo;
	}
	
}
