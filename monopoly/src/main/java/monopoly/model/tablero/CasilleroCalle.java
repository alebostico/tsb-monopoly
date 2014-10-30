package monopoly.model.tablero;

import java.io.Serializable;

import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroCalle extends Casillero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7775981623039342062L;

	private String nombreCalle;
	private int nroCasas;
	private TarjetaPropiedad tarjetaCalle;

	public CasilleroCalle(int numeroCasillero, String nombreCalle, TarjetaPropiedad tarjetaCalle) {
		super(numeroCasillero, TipoCasillero.C_CALLE);
		this.nombreCalle = nombreCalle;
		this.tarjetaCalle = tarjetaCalle;
		this.nroCasas = 0;
	}

	public String getNombreCalle() {
		return nombreCalle;
	}

	public TarjetaPropiedad getTarjetaCalle() {
		return tarjetaCalle;
	}

	public int getNroCasas() {
		return nroCasas;
	}

	public void setNroCasas(int nroCasas) {
		this.nroCasas = nroCasas;
	}

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
}
