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

	String nombreCalle;
	TarjetaPropiedad tarjetaCalle;
	int nroCasas;
	

	public CasilleroCalle(int numeroCasillero, String nombreCalle, Tablero tablero,
			TarjetaPropiedad tarjetaCalle) {
		super(numeroCasillero, Casillero.CASILLERO_CALLE,tablero);
		this.nombreCalle = nombreCalle;
		this.tarjetaCalle = tarjetaCalle;
		this.nroCasas=0;
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
		if(this.getTablero().getBanco().venderPropiedad(jugador, this.getTarjetaCalle()))
				return (TarjetaCalle) tarjetaCalle;
		return null;
	}
	
	
	

	public String toString() {
		return super.toString()
				+ "\n\tCasilleroCalle [nombreCalle="
				+ this.nombreCalle
				+ ", TarjetaCalle {\n"
				+ ((this.tarjetaCalle != null) ? this.tarjetaCalle.toString()
						: "<NO EXISTE LA TARJETA>") + "\n}]";
	}

}
