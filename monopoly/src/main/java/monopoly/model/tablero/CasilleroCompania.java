package monopoly.model.tablero;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCompania;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroCompania extends Casillero {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5668511368096614440L;
	private String nombreCompania;
	private TarjetaCompania tarjetaCompania;

	public CasilleroCompania(int numeroCasillero, String nombreCompania, Tablero tablero,
			TarjetaCompania tarjetaCompania) {
		super(numeroCasillero, TipoCasillero.C_COMPANIA, tablero);
		this.nombreCompania = nombreCompania;
		this.tarjetaCompania = tarjetaCompania;
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public TarjetaCompania getTarjetaCompania() {
		return tarjetaCompania;
	}

	public TarjetaCompania comprarCompania(Jugador jugador) {
		// TODO: implementar el metodo que realiza la compra de la Compania.
		if(this.getTablero().getBanco().venderPropiedad(jugador, this.getTarjetaCompania()))
			return (TarjetaCompania) tarjetaCompania;
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Casillero Compañia [nombre compañia: " + this.nombreCompania + ", ");
		sb.append((this.tarjetaCompania != null) ? this.tarjetaCompania.toString()
				: "<NO EXISTE LA TARJETA>");
		sb.append("] }");
		
		return sb.toString();
	}
}
