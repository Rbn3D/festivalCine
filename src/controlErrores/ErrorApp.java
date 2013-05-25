package controlErrores;


/**
 * Excepción base de la aplicación. Utilizada en el mecanismo centralizado de gestión de excepciones.
 * @author Ruben
 *
 */
public class ErrorApp extends Exception 
{
	/**
	 * Mensaje asociado al error
	 */
	private String msg;
	/**
	 * Excepción original
	 */
	private Exception exception;
	/**
	 * Crea una instancia de ErrorApp sin ningún parámetro
	 */
	public ErrorApp()
	{
		super();
	}
	/**
	 * Crea una instancia de ErrorApp con un mensaje de error
	 * @param msg El mensaje
	 */
	public ErrorApp(String msg)
	{
		super(msg);
		setMsg(msg);
	}
	/**
	 * Crea una instancia de ErrorApp con un mensaje de error y una excepción asociada
	 * @param msg El mensaje
	 * @param exception La excepción original
	 */
	public ErrorApp(String msg, Exception exception)
	{
		this(msg);
		setException(exception);
	}
	
	/**
	 * Getter de msg
	 * @return la propiedad msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * Getter de exception
	 * @return la propiedad exception
	 */
	public Exception getException() {
		return exception;
	}
	/**
	 * Setter de msg
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * Setter de exception
	 * @param exception
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	private static final long serialVersionUID = 1L;

}
