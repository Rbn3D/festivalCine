package dao;

/**
 * Representa un objeto almacenable y recuperable en base de datos
 * @author Ruben
 *
 */
public abstract class DBObject 
{
	/**
	 * Representa el Id en la base de datos.
	 */
	private long id;
	
	/**
	 * Getter de la propiedad Id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Setter de la propiedad Id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Implementación de hashcode, genera el hash a partir del parametro id (no es necesario reimplementar en las subclases)
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * Implementación de equals, genera el hash a partir del parametro id (no es necesario reimplementar en las subclases)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBObject other = (DBObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
