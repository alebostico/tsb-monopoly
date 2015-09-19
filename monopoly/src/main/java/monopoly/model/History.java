/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class History implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849291093285656834L;

	private String fecha;

	private String usuario;

	private String mensaje;

	public History() {

	}

	public History(String fecha, String usuario, String mensaje) {
		this.fecha = fecha;
		this.usuario = usuario;
		this.mensaje = mensaje;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the mensage
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	@Override
	public String toString(){
		return fecha + " - "  + usuario + " - " + mensaje;
	}
	
	public String toChatString(){
		StringBuffer sb = new StringBuffer();
		sb.append("(" + fecha.substring(fecha.length() - 5, fecha.length()) + ")");
		sb.append(" ");
		sb.append(usuario);
		sb.append(":\n");
		sb.append(mensaje);
		return sb.toString();
	}

}
