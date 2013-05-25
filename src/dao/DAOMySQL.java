package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import controlErrores.ErrorApp;

import datos.Dni;
import datos.ETipoUsuario;
import datos.Pelicula;
import datos.Puntuacion;
import datos.Resultado;
import datos.Usuario;
import datos.Votacion;

/**
 * Implementación en MySQL de la interfaz de Capa DAO IFestivalDAO. Es la capa de acceso a datos de la aplicación
 * @author Ruben
 *
 */
public class DAOMySQL implements IFestivalDAO {
	/**
	 * BeanDAO utilizado para crear y obtener la conexión
	 */
	private BeanDAO mysqlCon;
	/**
	 * Objeto conecction utilizado para las consultas
	 */
	private Connection conexion;
	/**
	 * Crea una instancia a partir de las propiedades de conexión establecidas en bd.properties
	 * @throws ErrorApp 
	 * -Si existe error al leer el fichero de propiedades
	 * -Si existe error al conectar con la base de datos
	 * -Si existe error al obtener el driver de la base de datos
	 */
	public DAOMySQL() throws ErrorApp
	{
		try {
			mysqlCon = new BeanDAO();
			conexion = mysqlCon.getConexion();
		} catch (IOException e) {
			throw new ErrorApp("Error al leer las propiedades de conexión a base de datos.", e);
		} catch (SQLException e) {
			throw new ErrorApp("Error al conectar con el servidor de base de datos.", e);
		} catch (ClassNotFoundException e) {
			throw new ErrorApp("Error al determinar driver de conexión a base de datos.", e);
		}
	}
	
	/**
	 * Devuelve un usuario a partir de su login y contraseña
	 * @param login El nombre de usuario
	 * @param clave La contraseña
	 * @return Un usuario en caso de que los datos coincidan, null en caso contrario.
	 */
	@Override
	public Usuario login(String login, String clave) 
	{
		PreparedStatement ps = null;
		try{
		ps = conexion.prepareStatement("SELECT idUsuarios FROM usuarios WHERE login = ? AND password = ?");
		ps.setString(1, login);
		ps.setString(2, clave);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		ps.close();
		return getUsuario(id);
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}
	
	/**
	 * Devuelve un usuario a partir de su id en la Base de Datos
	 * @param id El id del usuario
	 * @return El usuario correspondiente, o null si no existe
	 */
	private Usuario getUsuario(int id) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT * FROM usuarios WHERE idUsuarios = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Usuario u = new Usuario();
			u.setId(id);
			u.setLogin(rs.getString("Login"));
			u.setPassword(rs.getString("Password"));
			u.setTipoUsuario(ETipoUsuario.valueOf(rs.getString("TipoUsuario")));
			u.setDni(getDni(rs.getInt("DNIs_idDNIs")));
			ps.close();
			return u;
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}

	/**
	 * Devuelve un DNI a partir de su id en la Base de Datos
	 * @param id El id del DNI
	 * @return El DNI correspondiente, o null si no existe
	 */
	private Dni getDni(int id) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT * FROM dnis WHERE idDNIs = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Dni dni = new Dni();
			dni.setId(id);
			dni.setValue(rs.getString("DNI"));
			ps.close();
			return dni;
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}
	
	/**
	 * Devuelve un objeto DNI a partir del valor del mismo
	 * @param dni El valor del DNI
	 * @return El objeto DNI, o null si no existe
	 */
	private Dni getDni(String dni) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT * FROM dnis WHERE DNI = ?");
			ps.setString(1, dni);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Dni dnir = new Dni();
			dnir.setId(rs.getInt(1));
			dnir.setValue(rs.getString("DNI"));
			ps.close();
			return dnir;
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}

	/**
	 * Devuelve todos los usuarios registrados en la aplicación
	 */
	@Override
	public ArrayList<Usuario> getUsuarios() 
	{
		Statement st = null;
		try {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ArrayList<Usuario> users = new ArrayList<Usuario>();
			st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT idUsuarios FROM usuarios");
		while(rs.next())
			ids.add(new Integer(rs.getInt(1)));
		
		for(Integer id : ids)
			users.add(getUsuario(id));
		st.close();
		return users;
		} catch (SQLException e) {
		}
		finally
		{
			if(st!=null)
				try {
					st.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}
	/**
	 * Devuelve todos los usuarios que coincidan con el/los tipos de usuarios especificados
	 */
	@Override
	public ArrayList<Usuario> getUsuarios(ETipoUsuario... filtroTipo) 
	{
		ArrayList<Usuario> users = getUsuarios();
		ArrayList<Usuario> userFiltro = new ArrayList<Usuario>();
		for(ETipoUsuario tipo : filtroTipo)
		{
			for(Usuario u : users)
				if(u.getTipoUsuario() == tipo)
					userFiltro.add(u);
		}
		return userFiltro;
	}
	/**
	 * Devuelve un usuario a partir de su login. Null en caso de que no exista
	 */
	@Override
	public Usuario getUsuario(String login) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT * FROM usuarios WHERE Login = ?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Usuario u = new Usuario();
			u.setId(rs.getInt("idUsuarios"));
			u.setLogin(rs.getString("login"));
			u.setPassword(rs.getString("password"));
			u.setTipoUsuario(ETipoUsuario.valueOf(rs.getString("TipoUsuario")));
			u.setDni(getDni(rs.getInt("DNIs_idDNIs")));
			ps.close();
			return u;
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}
	
	/**
	 * Devuelve true si existe un usuario con el login especificado, false en caso contrario
	 */
	@Override
	public boolean existeUsuario(String login) 
	{	
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE Login = ?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			ps.close();
			if(count == 0)
				return false;
			else
				return true;
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return false;
	}
	
	/**
	 * Devuelve todos los DNIs registrados en la aplicación
	 */
	@Override
	public ArrayList<Dni> getDnis() 
	{
		Statement st = null;
		try {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<Dni> dnis = new ArrayList<Dni>();
				st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM dnis");
			while(rs.next())
				ids.add(new Integer(rs.getInt(1)));
			
			for(Integer id : ids)
				dnis.add(getDni(id));
			st.close();
			return dnis;
			} catch (SQLException e) {
			}
		finally
		{
			if(st!=null)
				try {
					st.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}

	/**
	 * Devuelve true en caso de que exista un DNI en la aplicación a partir del valor especificado, false en caso contrario
	 */
	@Override
	public boolean existeDNI(String dni) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT COUNT(*) FROM dnis WHERE DNI = ?");
			ps.setString(1, dni);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			ps.close();
			if(count == 0)
				return false;
			else
				return true;
		}
		catch(SQLException e)
		{
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
				}
		}
		return false;
	}

	/**
	 * Devuelve todas las películas de la aplicación
	 */
	@Override
	public ArrayList<Pelicula> getPeliculas() 
	{
		Statement  st = null;
		try {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
				st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT idPeliculas FROM peliculas");
			while(rs.next())
				ids.add(new Integer(rs.getInt(1)));
			
			for(Integer id : ids)
				peliculas.add(getPelicula(id.intValue()));
			return peliculas;
			} catch (SQLException e) {
			}
		finally
		{
			if(st!=null)
				try {
					st.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}

	/**
	 * Devuelve una película a partir de su id
	 * @param id el ID
	 * @return la pelicula que coincida con el id o null en caso de que no exista
	 */
	private Pelicula getPelicula(int id) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT * FROM peliculas WHERE idPeliculas = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Pelicula p = new Pelicula();
			p.setId(id);
			p.setTitulo(rs.getString("Titulo"));
			p.setDirector(rs.getString("Director"));
			p.setActoresAsString(rs.getString("Actores"));
			return p;
		}
		catch(SQLException e)
		{
			return null;
		}
	}
	
	/**
	 * Devuelve una película a partir de su título o null en caso de que no exista
	 */
	@Override
	public Pelicula getPelicula(String titulo) 
	{
		try
		{
			PreparedStatement ps = conexion.prepareStatement("SELECT * FROM peliculas WHERE Titulo = ?");
			ps.setString(1, titulo);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Pelicula p = new Pelicula();
			p.setId(rs.getInt(1));
			p.setTitulo(rs.getString("Titulo"));
			p.setDirector(rs.getString("Director"));
			p.setActoresAsString(rs.getString("Actores"));
			return p;
		}
		catch(SQLException e)
		{
			return null;
		}
	}
	
	/**
	 * Devuelve true si existe una película con el título especificado, false en caso contrario
	 */
	@Override
	public boolean existePelicula(String titulo) 
	{
		try
		{
			PreparedStatement ps = conexion.prepareStatement("SELECT COUNT(*) FROM peliculas WHERE Titulo = ?");
			ps.setString(1, titulo);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			if(count == 0)
				return false;
			else
				return true;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	
	/**
	 * Devuelve la votación establecida como votación actual, null en caso de que no exista ninguna
	 */
	@Override
	public Votacion getVotacionActual() 
	{
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT idVotaciones FROM votaciones WHERE VotacionActiva = 1");
			if(rs.next())
				return getVotacion(rs.getInt(1));
			else
				return null;
			} catch (SQLException e) {
				return null;
			}
	}

	/**
	 * Devuelve una votación a partir de su id en base de datos
	 * @param id El id
	 * @return La votación correspondiente, o null si no existe
	 */
	private Votacion getVotacion(int id) 
	{
		try
		{
			PreparedStatement ps = conexion.prepareStatement("SELECT * FROM votaciones WHERE idVotaciones = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Votacion v = new Votacion();
			v.setId(id);
			v.setNombre(rs.getString("Nombre"));
			v.setFechaCreacion(rs.getDate("FechaCreacion"));
			v.setFechaDeFin(rs.getDate("FechaFin"));
			v.setPermitirVotos(rs.getBoolean("PermitirVotos"));
			v.setVotacionActiva(rs.getBoolean("VotacionActiva"));
			v.setPeliculas(getPeliculas(v));
			v.getResultado().setPuntuaciones(GetPuntuaciones(v));
			return v;
		}
		catch(SQLException e)
		{
			return null;
		}
	}

	/**
	 * Devuelve las peliculas a consurso de la votacion v
	 * @param v La votación
	 * @return Las películas asociadas a la votación
	 */
	private ArrayList<Pelicula> getPeliculas(Votacion v) 
	{
		try {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
			PreparedStatement st = conexion.prepareStatement("SELECT peliculas_idPeliculas FROM votaciones_has_peliculas " +
					"WHERE Votaciones_idVotaciones = ?");
			st.setInt(1, (int)v.getId());
			ResultSet rs = st.executeQuery();
			while(rs.next())
				ids.add(new Integer(rs.getInt(1)));
			
			for(Integer id : ids)
				peliculas.add(getPelicula(id.intValue()));
			return peliculas;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	/**
	 * Devuelve las puntuaciones asociadas a la votación v
	 * @param v La votación
	 * @return Devuelve las puntuaciones encontradas, un ArrayList vacio en caso de que no se encuentre ninguna
	 */
	private ArrayList<Puntuacion> GetPuntuaciones(Votacion v) 
	{
		try
		{
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();
			PreparedStatement st = conexion.prepareStatement("SELECT idVoto FROM voto WHERE Votaciones_idVotaciones = ?");
			st.setInt(1, (int)v.getId());
			ResultSet rs = st.executeQuery();
			while(rs.next())
				ids.add(new Integer(rs.getInt(1)));
			
			for(Integer id : ids)
				puntuaciones.add(getPuntuacion(id.intValue(), v));
			return puntuaciones;
			} 
			catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<Puntuacion>();
			}
	}
	
	/**
	 * Devuelve una puntuacion a partir de su id, y se le asigna la votación v
	 * @param id
	 * @param v
	 * @return La puntuación correspondiente
	 */
	private Puntuacion getPuntuacion(int id, Votacion v) 
	{
		try
		{
			PreparedStatement ps = conexion.prepareStatement("SELECT * FROM voto WHERE idVoto = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Puntuacion p = new Puntuacion();
			p.setId(id);
			p.setPelicula(getPelicula(rs.getInt("peliculas_idPeliculas")));
			p.setVotacion(v);
			p.setUsuario(getUsuario(rs.getInt("usuarios_idUsuarios")));
			p.setPuntuacion(new Integer(rs.getInt("puntuacion")));
			return p;
		}
		catch(SQLException e)
		{
			return null;
		}
	}

	/**
	 * Devuelve una votación a partir de su nombre, null en caso de que no exista
	 */
	@Override
	public Votacion getVotacion(String nombre) {
		try
		{
			PreparedStatement ps = conexion.prepareStatement("SELECT idVotaciones FROM votaciones WHERE Nombre = ?");
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return getVotacion(rs.getInt(1));
		}
		catch(SQLException e)
		{
			return null;
		}
	}

	/**
	 * Devuelve true en caso de que exista una votación con el nombre especificado, false en caso contrario
	 */
	@Override
	public boolean existeVotacion(String nombre) 
	{
		try
		{
			PreparedStatement ps = conexion.prepareStatement("SELECT COUNT(*) FROM votaciones WHERE Titulo = ?");
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			if(count == 0)
				return false;
			else
				return true;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	
	/**
	 * Devuelve todas las votaciones de la aplicación
	 */
	@Override
	public ArrayList<Votacion> getVotaciones() 
	{
		try {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<Votacion> votaciones = new ArrayList<Votacion>();
				Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT idVotaciones FROM votaciones");
			while(rs.next())
				ids.add(new Integer(rs.getInt(1)));
			
			for(Integer id : ids)
				votaciones.add(getVotacion(id.intValue()));
			return votaciones;
			} catch (SQLException e) {
				return null;
			}
	}
	
	/**
	 * Asigna un usuario a la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar)
	 */
	@Override
	public boolean setUsuario(Usuario u, TipoConsulta tipo) 
	{
		try
		{
			boolean set = true;
			switch (tipo) {
			case insertar:
				if(!existeUsuario(u.getLogin()))
				{
					if(u.getTipoUsuario().equals(ETipoUsuario.jurado) && !existeDNI(u.getDni().getValue()))
					{
						set = false;
					}
					else
					{
						PreparedStatement ps = null;
						if(u.getTipoUsuario().equals(ETipoUsuario.jurado))
							ps = conexion.prepareStatement("INSERT INTO usuarios (`Login`, `Password`, `TipoUsuario`, `DNIs_idDNIs`) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
						else
							ps  = conexion.prepareStatement("INSERT INTO usuarios (`Login`, `Password`, `TipoUsuario`) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, u.getLogin());
						ps.setString(2, u.getPassword());
						ps.setString(3, u.getTipoUsuario().name());
						if(u.getTipoUsuario().equals(ETipoUsuario.jurado))
							ps.setInt(4, (int) getDni(u.getDni().getValue()).getId());
						ps.executeUpdate();
				        ResultSet rs = ps.getGeneratedKeys();
				        if (rs.next())
				            u.setId(rs.getLong(1));

					}
					return set;
				}
				else
					return false;
			case modificar:
				String query;
				if(u.getTipoUsuario().equals(ETipoUsuario.jurado))
					query = "UPDATE usuarios SET Login = ?, Password = ?, TipoUsuario = ?, DNIs_idDNIs = ? WHERE idUsuarios = ?";
				else
					query = "UPDATE usuarios SET Login = ?, Password = ?, TipoUsuario = ? WHERE idUsuarios = ?";
				PreparedStatement ps = conexion.prepareStatement(query);
				ps.setString(1, u.getLogin());
				ps.setString(2, u.getPassword());
				ps.setString(3, u.getTipoUsuario().name());
				if(u.getTipoUsuario().equals(ETipoUsuario.jurado))
				{
					ps.setInt(4, (int) getDni(u.getDni().getValue()).getId());
					ps.setInt(5, (int) u.getId());
				}
				else
					ps.setInt(4, (int) u.getId());
				ps.executeUpdate();
				return true;
			}
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Asigna una película a la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar)
	 */
	@Override
	public boolean setPelicula(Pelicula p, TipoConsulta tipo) 
	{
		try
		{
			switch (tipo) {
			case insertar:
				if(!existePelicula(p.getTitulo()))
				{
					PreparedStatement ps = conexion.prepareStatement("INSERT INTO peliculas(Titulo, Director, Actores) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, p.getTitulo());
					ps.setString(2, p.getDirector());
					ps.setString(3, p.getActoresAsString());
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					rs.next();
					p.setId(rs.getLong(1));
				}
				break;
			case modificar:
				PreparedStatement ps = conexion.prepareStatement("UPDATE peliculas SET Titulo = ?, Director = ?, ACTORES = ? WHERE idPeliculas = ?");
				ps.setString(1, p.getTitulo());
				ps.setString(2, p.getDirector());
				ps.setString(3, p.getActoresAsString());
				ps.setInt(4, (int) p.getId());
				ps.executeUpdate();
				break;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Asigna una votación a la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar)
	 */
	@Override
	public boolean setVotacion(Votacion v, TipoConsulta tipo) 
	{
		try
		{
			switch (tipo) {
			case insertar:
				if(!existeVotacion(v.getNombre()))
				{
					PreparedStatement ps = conexion.prepareStatement("INSERT INTO votaciones(Nombre, FechaCreacion, FechaFin, VotacionActiva, PermitirVotos) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, v.getNombre());
					ps.setDate(2, v.getFechaCreacion());
					ps.setDate(3, v.getFechaDeFin());
					ps.setBoolean(4, (v.isVotacionActiva()));
					ps.setBoolean(5, (v.isPermitirVotos()));
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					rs.next();
					v.setId(rs.getLong(1));
					setPeliculasVotacion(v, v.getPeliculas());
				}
				break;
			case modificar:
				PreparedStatement ps = conexion.prepareStatement("UPDATE votaciones SET Nombre = ?, FechaCreacion = ?, FechaFin = ?, VotacionActiva = ?, PermitirVotos = ? WHERE idVotaciones = ?");
				ps.setString(1, v.getNombre());
				ps.setDate(2, v.getFechaCreacion());
				ps.setDate(3, v.getFechaDeFin());
				ps.setBoolean(4, (v.isVotacionActiva()));
				ps.setBoolean(5, (v.isPermitirVotos()));
				ps.setInt(6, (int) v.getId());
				ps.executeUpdate();
				setPeliculasVotacion(v, v.getPeliculas());
				break;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}

	/**
	 * Asigna las películas del parametro películas a la votación v
	 * @param v La votación
	 * @param peliculas Las peliculas a asignar a la votación
	 */
	private void setPeliculasVotacion(Votacion v, ArrayList<Pelicula> peliculas) 
	{
		try
		{
			PreparedStatement ps = conexion.prepareStatement("DELETE FROM votaciones_has_peliculas WHERE Votaciones_idVotaciones = ?");
			ps.setInt(1, (int) v.getId());
			ps.executeUpdate();
			for(Pelicula p : peliculas)
			{
				try
				{
					ps = conexion.prepareStatement("INSERT INTO votaciones_has_peliculas(Votaciones_idVotaciones, peliculas_idPeliculas) VALUES (?, ?)");
					ps.setInt(1, (int) v.getId());
					ps.setInt(2, (int) p.getId());
					ps.executeUpdate();
				}
				catch (Exception e) // Saltará en caso de que ya esté esta película asociada a este voto (ignoramos)
				{
				}
			}
		}
		catch(Exception e)
		{
		}
	}

	/**
	 * Asigna la votación v como la votación activa
	 */
	@Override
	public void setVotacionActiva(Votacion v) 
	{
		try
		{
		String sql = "UPDATE votaciones SET VotacionActiva = 0 WHERE idVotaciones != ?";
		String sql2 = "UPDATE votaciones SET PermitirVotos = 0 WHERE idVotaciones != ?";
		PreparedStatement ps = conexion.prepareStatement(sql);
		ps.setInt(1, (int) v.getId());
		ps.executeUpdate();
		PreparedStatement ps2 = conexion.prepareStatement(sql2);
		ps2.setInt(1, (int) v.getId());
		ps2.executeUpdate();
		v.setVotacionActiva(true);
		setVotacion(v, TipoConsulta.modificar);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Asigna un DNI a la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar)
	 */
	@Override
	public boolean setDni(Dni dni, TipoConsulta tipo) 
	{
		try
		{
			switch (tipo) {
			case insertar:
				if(!existeDNI(dni.getValue()))
				{
					PreparedStatement ps = conexion.prepareStatement("INSERT INTO dnis(DNI) VALUES (?)",Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, dni.getValue());
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					rs.next();
					dni.setId(rs.getLong(1));
				}
				else
					return false;
				break;
			case modificar:
				PreparedStatement ps = conexion.prepareStatement("UPDATE dnis SET DNI = ? WHERE idDNIs = ?");
				ps.setString(1, dni.getValue());
				ps.setInt(2, (int) dni.getId());
				ps.executeUpdate();
				break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Asigna puntuaciones a la votación v
	 */
	@Override
	public void setPuntuacion(Votacion votacion, ArrayList<Puntuacion> puntuaciones) 
	{
		ArrayList<Puntuacion> puntExistentes = GetPuntuaciones(votacion);
		for(Puntuacion p : puntuaciones)
		{
			p.setVotacion(votacion);
			setVoto(p);
		}
	}
	
	/**
	 * Guarda una puntuación en la base de datos
	 * @param p La puntuación
	 */
	private void setVoto(Puntuacion p)
	{
		if(existeVoto(p))
		{
			setVoto(p, TipoConsulta.modificar);
		}
		else
			setVoto(p, TipoConsulta.insertar);
	}

	/**
	 * Comprueba si existe una puntuación en la base de datos
	 * @param p La puntuación
	 * @return true si existe, false en caso contrario
	 */
	private boolean existeVoto(Puntuacion p) 
	{
		PreparedStatement ps = null;
		try
		{
			ps = conexion.prepareStatement("SELECT COUNT(*) FROM voto WHERE Votaciones_idVotaciones = ? AND peliculas_idPeliculas = ? AND usuarios_idUsuarios = ?");
			ps.setInt(1, (int) p.getVotacion().getId());
			ps.setInt(2, (int) p.getPelicula().getId());
			ps.setInt(3, (int) p.getUsuario().getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			if(count == 0)
				return false;
			else
				return true;
		}
		catch(SQLException e)
		{
			return false;
		}
		finally
		{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) 
				{
				}
		}
		
	}

	/**
	 * Asigna una puntuación a la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar)
	 */
	private void setVoto(Puntuacion p, TipoConsulta tipo) 
	{
		try
		{
			switch (tipo) {
			case insertar:
				PreparedStatement ps = conexion.prepareStatement("INSERT INTO voto(Votaciones_idVotaciones, peliculas_idPeliculas, usuarios_idUsuarios, puntuacion) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, (int) (p.getVotacion().getId()));
				ps.setInt(2, (int) (p.getPelicula().getId()));
				ps.setInt(3, (int) (p.getUsuario().getId()));
				ps.setInt(4, p.getPuntuacion().intValue());
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				p.setId(rs.getLong(1));
				break;
			case modificar:
				PreparedStatement ps2 = conexion.prepareStatement("UPDATE voto SET Votaciones_idVotaciones = ?, peliculas_idPeliculas = ?, usuarios_idUsuarios = ?, puntuacion = ? WHERE idVoto = ?");
				ps2.setInt(1, (int) (p.getVotacion().getId()));
				ps2.setInt(2, (int) (p.getPelicula().getId()));
				ps2.setInt(3, (int) (p.getUsuario().getId()));
				ps2.setInt(4, p.getPuntuacion().intValue());
				ps2.setInt(5, (int) p.getId());
				ps2.executeUpdate();
				break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna cualquier DBObject a la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar). 
	 * Éste método set encarga de llamar al 'set' específico del DBobject especificado, ofreciendo una implementación genérica
	 */
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

	/**
	 * Elimina al usuario u de la base de datos
	 * @return true si se ha eliminado, false en caso contrario
	 */
	@Override
	public boolean borrarUsuario(Usuario u) 
	{
		try
		{
			String sql = "DELETE FROM usuarios WHERE idUsuarios = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, (int) u.getId());
			return ps.executeUpdate() > 0;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/**
	 * Elimina la película p de la base de datos
	 * @return true si se ha eliminado, false en caso contrario
	 */
	@Override
	public boolean borrarPelicula(Pelicula p) {
		try
		{
			String sql = "DELETE FROM peliculas WHERE idPeliculas = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, (int) p.getId());
			return ps.executeUpdate() > 0;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/**
	 * Elimina la votación v de la base de datos
	 * @return true si se ha eliminado, false en caso contrario
	 */
	@Override
	public boolean borrarVotacion(Votacion v) 
	{
		try
		{
			String sql = "DELETE FROM votaciones WHERE idVotaciones = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, (int) v.getId());
			return ps.executeUpdate() > 0;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/**
	 * Elimina el Dni dni de la base de datos
	 * @return true si se ha eliminado, false en caso contrario
	 */
	@Override
	public boolean borrarDni(Dni dni) 
	{
		try
		{
			String sql = "DELETE FROM dnis WHERE idDNIs = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, (int) dni.getId());
			return ps.executeUpdate() > 0;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/**
	 * Elimina cualquier DBObject de la base de datos. La operacion requerida se especifica en el parametro tipo (insetar o modificar). 
	 * Éste método set encarga de llamar al 'borrar' específico del DBobject especificado, ofreciendo una implementación genérica
	 */
	@Override
	public boolean borrar(DBObject dbo) {
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
