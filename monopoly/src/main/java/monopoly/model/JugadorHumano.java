/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

import javax.persistence.Transient;

import monopoly.model.tablero.Casillero;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class JugadorHumano extends Jugador implements Serializable{

	private static final long serialVersionUID = -6192046659268955353L;

	@Transient
	private Usuario usuario;

	@Transient
	private int senderID;

	public JugadorHumano(String nombre, Ficha ficha, Juego juego, Casillero casilleroActual,
			Usuario usuario, int senderID) {
		super(nombre, ficha, juego, casilleroActual);
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
	 * @param senderId
	 *            the senderId to set
	 */
	public void setSenderID(int senderId) {
		this.senderID = senderId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.model.Jugador#toString()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.model.Jugador#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		return this.getNombre().toLowerCase()
				.equals(((JugadorHumano) object).getNombre().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNombre().toLowerCase().hashCode();
	}

}
