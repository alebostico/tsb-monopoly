/**
 * 
 */
package monopoly.util;

import java.util.Comparator;

import monopoly.model.tablero.Casillero;

/**
 * Clase para ordenar las propiedades según su valor de menor a mayor.
 * <ul>
 * <li>Para ordenar las propiedades de menor a mayor:<br>
 * {@code Collections.sort(casilleros, new CasilleroComparator());}
 * <li>Para ordenarlas de mayor a menor:<br>
 * {@code Collections.sort(casilleros,
 * Collections.reverseOrder(new CasilleroComparator()));}
 * </ul>
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CasilleroComparator implements Comparator<Casillero> {

	@Override
	public int compare(Casillero casillero1, Casillero casillero2) {
		return (casillero1.getNumeroCasillero() - casillero2
				.getNumeroCasillero());
	}

}
