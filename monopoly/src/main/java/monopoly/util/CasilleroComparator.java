/**
 * 
 */
package monopoly.util;

import java.util.Comparator;

import monopoly.model.tablero.Casillero;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * Clase para ordenar las propiedades según su valor de menor a mayor. Para
 * ordenar las propiedades de menor a mayor:
 * {@code Collections.sort(casilleros, new CasilleroComparator());}
 * Para ordenarlas de mayor a menor:
 * {@code Collections.sort(casilleros,
 * 			Collections.reverseOrder(new CasilleroComparator()));}
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CasilleroComparator implements Comparator<Casillero> {

	@Override
	public int compare(Casillero casillero1, Casillero casillero2) {
		return (casillero1.getNumeroCasillero() - casillero2.getNumeroCasillero());
	}

}
