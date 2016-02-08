package monopoly.model.tablero;

import java.io.Serializable;

import monopoly.model.tarjetas.TarjetaCalle;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
public class CasilleroCalle extends Casillero implements Serializable {

	private static final long serialVersionUID = 7775981623039342062L;

	private String nombreCalle;
	private int nroCasas;
	private TarjetaCalle tarjetaCalle;

	public CasilleroCalle(int numeroCasillero, TarjetaCalle tarjetaCalle) {
		super(numeroCasillero, tarjetaCalle.getNombre(), TipoCasillero.C_CALLE);
		this.nombreCalle = tarjetaCalle.getNombre();
		this.tarjetaCalle = tarjetaCalle;
		this.nroCasas = 0;
		this.tarjetaCalle.setCasillero(this);
	}

	public String getNombreCalle() {
		return nombreCalle;
	}

	public TarjetaCalle getTarjetaCalle() {
		return tarjetaCalle;
	}

	/**
	 * Devuelve la cantidad de edificios construídos:
	 * <ul>
	 * <li>0 - No tiene nada construído</li>
	 * <li>1 a 4 - La cantdidad de casas</li>
	 * <li>5 - Un hotel</li>
	 * </ul>
	 * 
	 * @return La cantidad de casas u hoteles construídos
	 */
	public int getNroCasas() {
		return nroCasas;
	}

	/**
	 * Ingresa un nuevo valor de casas/hoteles
	 * 
	 * @param nroCasas
	 *            La cantidad de edificios construidos (de 1 a 4 son Casas; 5 es
	 *            un hotel; 0, no hay nada construído).
	 */
	public void setNroCasas(int nroCasas) {
		this.nroCasas = nroCasas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.model.tablero.Casillero#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Casillero Calle [nombre calle: " + this.nombreCalle + ", ");
		sb.append((this.tarjetaCalle != null) ? this.tarjetaCalle.toString()
				: "<NO EXISTE LA TARJETA>");
		sb.append(", número de casas: " + this.nroCasas);
		sb.append("] }");

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.model.tablero.Casillero#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		CasilleroCalle casillero = (CasilleroCalle) object;
		if (this.getNumeroCasillero() != casillero.getNumeroCasillero())
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNumeroCasillero();
	}

}
