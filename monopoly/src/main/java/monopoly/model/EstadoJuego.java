package monopoly.model;

import java.io.Serializable;

public class EstadoJuego implements Serializable{

	private static final long serialVersionUID = 332038651618517929L;

	private String nombre;

	private static EstadoJuego instance;

	/**
	 * @param nombre
	 */
	public EstadoJuego(String nombre) {
		super();
		this.nombre = nombre;
	}

	/**
	 * 
	 */
	public EstadoJuego() {
		super();
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static EstadoJuego getInstance() {
		if (instance == null)
			instance = new EstadoJuego();

		return instance;
	}
}
