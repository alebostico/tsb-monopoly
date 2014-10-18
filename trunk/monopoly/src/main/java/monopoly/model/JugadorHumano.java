/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

import javax.persistence.Transient;

import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class JugadorHumano extends Jugador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private Usuario usuario;

	public JugadorHumano(String nombre, Ficha ficha, Juego juego,
			Usuario usuario) {
		super(nombre, ficha, juego);
		this.usuario = usuario;
		GestorLogs.registrarLog("Nuevo jugador humano '" + nombre + "'");
		GestorLogs.registrarDebug(this.toString());
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Jugador Humano [");
		sb.append((Jugador) this != null ? super.toString() : "<SIN JUGADOR> ");
		sb.append((this.usuario != null) ? ", " + this.usuario.toString()
				: "<SIN USUARIO> ");
		sb.append("] }");
		return sb.toString();
	}
}
