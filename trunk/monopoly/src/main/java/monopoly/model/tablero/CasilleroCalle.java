package monopoly.model.tablero;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroCalle extends Casillero {

	private String nombreCalle;
	private int nroCasas;
	private TarjetaPropiedad tarjetaCalle;

	public CasilleroCalle(int numeroCasillero, String nombreCalle,
			Tablero tablero, TarjetaPropiedad tarjetaCalle) {
		super(numeroCasillero, TipoCasillero.C_CALLE, tablero);
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

	public TarjetaCalle comprarCalle(Jugador jugador) {
		// TODO: implementar el metodo que realiza la compra de la calle.
		if (this.getTablero().getBanco()
				.venderPropiedad(jugador, this.getTarjetaCalle()))
			return (TarjetaCalle) tarjetaCalle;
		return null;
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
