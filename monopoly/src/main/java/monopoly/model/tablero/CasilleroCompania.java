package monopoly.model.tablero;

import java.io.Serializable;

import monopoly.model.tarjetas.TarjetaCompania;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
public class CasilleroCompania extends Casillero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5668511368096614440L;

	private String nombreCompania;
	private TarjetaCompania tarjetaCompania;

	public CasilleroCompania(int numeroCasillero,
			TarjetaCompania tarjetaCompania) {
		super(numeroCasillero,tarjetaCompania.getNombre(), TipoCasillero.C_COMPANIA);
		this.nombreCompania = tarjetaCompania.getNombre();
		this.tarjetaCompania = tarjetaCompania;
		this.tarjetaCompania.setCasillero(this);
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public TarjetaCompania getTarjetaCompania() {
		return tarjetaCompania;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Casillero Compañia [nombre compañia: "
				+ this.nombreCompania + ", ");
		sb.append((this.tarjetaCompania != null) ? this.tarjetaCompania
				.toString() : "<NO EXISTE LA TARJETA>");
		sb.append("] }");

		return sb.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		CasilleroCompania casillero = (CasilleroCompania) object;
		if (this.getNumeroCasillero() != casillero.getNumeroCasillero())
			return false;

		return true;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNumeroCasillero();
	}
}
