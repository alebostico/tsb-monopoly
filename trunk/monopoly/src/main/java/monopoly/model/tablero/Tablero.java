/**
 * 
 */
package monopoly.model.tablero;

import java.io.Serializable;

import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

/**
 * Clase que representa al tablero del juego. Mantiene una inastancia de cada
 * casillero.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Tablero implements Serializable {
	
	private static final long serialVersionUID = 6332031540679671323L;

	// CONSTANTES
	private int cantCasilleros = 40;

	private Casillero[] casillerosList;

	/**
	 * Constructor por defecto.
	 */
	public Tablero(Casillero[] casillerosList){
		this.casillerosList = casillerosList; // Cargar los casilleros.
		GestorLogs.registrarLog("Tablero cargado");
	}

	/**
	 * @return the casillerosList
	 */
	public Casillero[] getCasillerosList() {
		return casillerosList;
	}

	/**
	 * @param casillerosList the casillerosList to set
	 */
	public void setCasillerosList(Casillero[] casillerosList) {
		this.casillerosList = casillerosList;
	}

	public String toString() {

		StringBuffer toReturn = new StringBuffer();
		toReturn.append("\n===============================================================================================\n");
		toReturn.append("|                                         TABLERO                                             |\n");
		toReturn.append("===============================================================================================\n");
		toReturn.append("| Nro |   Tipo Casillero    |        Nombre Casillero        |         Nombre Tarjeta         |\n");
		toReturn.append("|-----|---------------------|--------------------------------|--------------------------------|\n");

		for (int i = 0; i < this.cantCasilleros; i++) {
			// Nro =================================================
			toReturn.append("| ");
			toReturn.append(String.format("%02d",
					this.casillerosList[i].getNumeroCasillero()));
			toReturn.append("  ");

			// Tipo Casillero ======================================
			toReturn.append("| ");
			toReturn.append(String.format("%1$19s",
					this.casillerosList[i].getTipoCasillero().getNombreTipoCasillero()));
			toReturn.append(" ");

			// Nombre Casillero ====================================
			switch (this.casillerosList[i].getTipoCasillero()) {
			case C_CALLE:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s",
						((CasilleroCalle) this.casillerosList[i])
								.getNombreCalle()));
				toReturn.append(" ");
				break;

			case C_ESTACION:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s",
						((CasilleroEstacion) this.casillerosList[i])
								.getNombreEstacion()));
				toReturn.append(" ");
				break;

			case C_COMPANIA:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s",
						((CasilleroCompania) this.casillerosList[i])
								.getNombreCompania()));
				toReturn.append(" ");
				break;

			default:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s", ""));
				toReturn.append(" ");
				break;
			}

			// Nombre Tarjeta ======================================
			TarjetaPropiedad tarjeta;

			switch (this.casillerosList[i].getTipoCasillero()) {
			case C_CALLE:
				tarjeta = ((CasilleroCalle) this.casillerosList[i])
						.getTarjetaCalle();

				if (tarjeta != null) {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((TarjetaCalle) tarjeta).getNombre()));
					toReturn.append(" ");
				} else {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							"<ERROR: TARJETA NOT FOUND>"));
					toReturn.append(" ");
				}
				break;
			case C_ESTACION:
				tarjeta = ((CasilleroEstacion) this.casillerosList[i])
						.getTarjetaEstacion();

				if (tarjeta != null) {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((TarjetaEstacion) tarjeta).getNombre()));
					toReturn.append(" ");
				} else {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							"<ERROR: TARJETA NOT FOUND>"));
					toReturn.append(" ");
				}
				break;
			case C_COMPANIA:
				tarjeta = ((CasilleroCompania) this.casillerosList[i])
						.getTarjetaCompania();

				if (tarjeta != null) {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((TarjetaCompania) tarjeta).getNombre()));
					toReturn.append(" ");
				} else {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							"<ERROR: TARJETA NOT FOUND>"));
					toReturn.append(" ");
				}
				break;

			default:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s", ""));
				toReturn.append(" ");
				break;
			}

			toReturn.append("|\n");
		}

		toReturn.append("===============================================================================================\n");

		return toReturn.toString();
	}

}
