/**
 * 
 */
package monopoly.util;

import java.util.Comparator;

import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * Clase para ordenar las propiedades según su valor de menor a mayor.
 * <ul>
 * <li>Para ordenar las propiedades de menor a mayor:<br/>
 * {@code Collections.sort(propiedades, new TarjetaPropiedadComparator());}
 * <li>Para ordenarlas de mayor a menor: <br/>
 * {@code Collections.sort(propiedades,
 * 			Collections.reverseOrder(new TarjetaPropiedadComparator()));}
 * </ul>
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TarjetaPropiedadComparator implements Comparator<TarjetaPropiedad> {

	@Override
	public int compare(TarjetaPropiedad tp1, TarjetaPropiedad tp2) {
		return (tp1.getValorPropiedad() - tp2.getValorPropiedad());
	}

}
