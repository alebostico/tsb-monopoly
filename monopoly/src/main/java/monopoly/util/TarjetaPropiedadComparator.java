/**
 * 
 */
package monopoly.util;

import java.util.Comparator;

import monopoly.model.tarjetas.TarjetaPropiedad;

/**
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
 