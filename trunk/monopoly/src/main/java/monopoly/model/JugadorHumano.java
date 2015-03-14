/**
 * 
 */
package monopoly.model;

import javax.persistence.Transient;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class JugadorHumano extends Jugador {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private Usuario usuario;
	
	@Transient 
	private int senderID;

	public JugadorHumano(String nombre, Ficha ficha, Juego juego,
			Usuario usuario, int senderID) {
		super(nombre, ficha, juego);
		this.usuario = usuario;
		this.senderID = senderID;
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

	/**
	 * @return the senderId
	 */
	public int getSenderID() {
		return senderID;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderID(int senderId) {
		this.senderID = senderId;
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
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		JugadorHumano jugador = (JugadorHumano) object;
		if (this.getNombre() != jugador.getNombre())
			return false;

		return true;
	}
}